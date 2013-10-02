--------------------------------------------------------------------------------
{-# LANGUAGE OverloadedStrings #-}
import Hakyll
import Control.Applicative ((<$>),(<|>))
import Control.Arrow ((>>>),(>>^))
import Control.Monad.Trans
import Codec.Binary.UTF8.String (encodeString)
import Data.List (isPrefixOf,isSuffixOf,isInfixOf)
import Data.Monoid (mappend,(<>))
import Data.Text (pack,unpack,replace,empty)
import Data.Char (toLower)
import System.Random
import System.FilePath (takeFileName,takeBaseName,splitFileName,takeDirectory, (</>))

-- http://qnikst.github.io/posts/2013-02-04-hakyll-latex.html
--------------------------------------------------------------------------------
main :: IO ()
main = hakyll $ do

    -- Build tags
    tags <- buildTags "posts/*" (fromCapture "tags/*.html")

    -- copy resources
    match ("images/**" 
            .||. "js/**" 
            .||. "marine/**" 
            .||. "tictactoe/**" 
            .||. "resume/**"
            .||. "pdf/**"
            .||. "code/**") $ do
        route   idRoute
        compile copyFileCompiler


    match "css/*.css" $ do
        route   idRoute
        compile compressCssCompiler


    match "css/*.scss" $ do
        route   $ setExtension "css"
        compile $ getResourceString >>=
                  withItemBody (unixFilter "scss" ["--trace"]) >>=
                  return . fmap compressCss


    match (fromList ["about.markdown"
                    ,"contact.markdown"
                    ,"games.markdown"
                    ,"euler.markdown"]) $ do
        route   $ setExtension "html"
--        route $ niceRoute
        compile $ pandocCompiler
            >>= loadAndApplyTemplate "templates/default.html" 
                (tagsCtx tags)
            >>= relativizeUrls
--            >>= cleanIndexUrls

    match "posts/*" $ do
        route $ setExtension "html"
--        route $ niceRoute
        compile $ pandocCompiler
            >>= loadAndApplyTemplate "templates/post.html"    (tagsCtx tags)
            >>= (externalizeUrls $ feedRoot feedConfiguration)
            >>= saveSnapshot "content"
            >>= (unExternalizeUrls $ feedRoot feedConfiguration)
            >>= loadAndApplyTemplate "templates/default.html" (tagsCtx tags)
            >>= relativizeUrls
--            >>= cleanIndexUrls

    create ["archive.html"] $ do
        route idRoute
        compile $ do
            posts <- recentFirst =<< loadAll "posts/*"
            let archiveCtx =
                    listField "posts" postCtx (return posts) `mappend`
                    constField "title" "Archives"            `mappend`
                    defaultContext
            makeItem ""
                >>= loadAndApplyTemplate "templates/archive.html" archiveCtx
                >>= loadAndApplyTemplate "templates/default.html" archiveCtx
                >>= relativizeUrls

    match "index.html" $ do
        route idRoute
        compile $ do
            posts <- recentFirst =<< loadAll "posts/*"
            let indexCtx =
                    listField "posts" postCtx (return posts) `mappend`
                    constField "title" "Home"                `mappend`
                    (tagCloudField "tagcloud" 100  240 tags) `mappend`
                    defaultContext

            getResourceBody
                >>= applyAsTemplate indexCtx
                >>= loadAndApplyTemplate "templates/default.html" indexCtx
                >>= relativizeUrls

    -- Post tags
    tagsRules tags $ \tag pattern -> do
        let title = "Posts tagged '" ++ tag ++ "'"
        route idRoute
        compile $ do
            list <- postList tags pattern recentFirst
            makeItem ""
                >>= loadAndApplyTemplate "templates/posts.html"
                        (constField "title" title `mappend`
                            constField "body" list `mappend`
                            defaultContext)
                >>= loadAndApplyTemplate "templates/default.html"
                        (constField "title" title `mappend`
                            defaultContext)
                >>= relativizeUrls

    -- Render RSS feed
    create ["rss.xml"] $ do
        route idRoute
        compile $ do
            loadAllSnapshots "posts/*" "content"
                >>= recentFirst
                >>= renderRss feedConfiguration feedCtx 

    match "templates/*" $ compile templateCompiler




-- Utils


-- Routes

-- replace a foo/bar.md by foo/bar/index.html
-- this way the url looks like: foo/bar in most browsers
niceRoute :: Routes
niceRoute = customRoute createIndexRoute
  where
    createIndexRoute ident = takeDirectory p </> takeBaseName p </> "index.html" where p=toFilePath ident

-- replace url of the form foo/bar/index.html by foo/bar
{-removeIndexHtml :: Item String -> Compiler (Item String)
removeIndexHtml item = return $ fmap (withUrls removeIndexStr) item
  where
    removeIndexStr :: String -> String
    removeIndexStr url = case splitFileName url of
        (dir, "index.html") | islocal dir -> dir
        _                                 -> dir
        where islocal uri = not (isInfixOf "://" uri)-}

cleanIndexUrls :: Item String -> Compiler (Item String)
cleanIndexUrls = return . fmap (withUrls clean)
      where
        idx = "index.html"
        clean url
            | idx `isSuffixOf` url = take (length url - length idx) url
            | otherwise            = url 

-- Contexts

postCtx :: Context String
postCtx =
    dateField "date" "%B %e, %Y" `mappend`
    (defaultContext <> metaKeywordCtx)

tagsCtx :: Tags -> Context String
tagsCtx tags =
    tagsField "prettytags" tags  `mappend`
    (tagCloudField "tagcloud" 100  240 tags) `mappend`
    postCtx

feedCtx :: Context String
feedCtx =
    bodyField "description" `mappend`
    postCtx

-- metaKeywordContext will return a Context containing a String
metaKeywordCtx :: Context String
-- can be reached using $metaKeywords$ in the templates
-- Use the current item (markdown file)
metaKeywordCtx = field "metaKeywords" $ \item -> do
  -- tags contains the content of the "tags" metadata
  -- inside the item (understand the source)
  tags <- getMetadataField (itemIdentifier item) "tags"
  -- if tags is empty return an empty string
  -- in the other case return
  --   <meta name="keywords" content="$tags$">
  return $ maybe "" showMetaTags tags
    where
      showMetaTags t = "<meta name=\"keywords\" content=\""
                       ++ t ++ "\">\n"

----------------------------------------------

config :: Configuration
config = defaultConfiguration { 
    deployCommand = "rsync -avz --delete --checksum _site/* kenny@kennycason.com:/www/kennycason/"
}

-- Feed configuration

feedConfiguration :: FeedConfiguration
feedConfiguration = FeedConfiguration
    { feedTitle = "Kenny Cason's Blog - RSS feed"
    , feedDescription = "Software, Math, and Language"
    , feedAuthorName = "Kenny Cason"
    , feedAuthorEmail = "kenneth.cason@gmail.com"
    , feedRoot = "http://www.kennycason.com"
    }


-- Auxiliary compilers

externalizeUrls :: String -> Item String -> Compiler (Item String)
externalizeUrls root item = return $ fmap (externalizeUrlsWith root) item

externalizeUrlsWith :: String -- ^ Path to the site root
                    -> String -- ^ HTML to externalize
                    -> String -- ^ Resulting HTML
externalizeUrlsWith root = withUrls ext
  where
    ext x = if isExternal x then x else root ++ x

unExternalizeUrls :: String -> Item String -> Compiler (Item String)
unExternalizeUrls root item = return $ fmap (unExternalizeUrlsWith root) item

unExternalizeUrlsWith :: String -- ^ Path to the site root
                      -> String -- ^ HTML to unExternalize
                      -> String -- ^ Resulting HTML
unExternalizeUrlsWith root = withUrls unExt
  where
    unExt x = if root `isPrefixOf` x then unpack $ replace (pack root) empty (pack x) else x

postList :: Tags -> Pattern -> ([Item String] -> Compiler [Item String])
         -> Compiler String
postList tags pattern preprocess' = do
    postItemTpl <- loadBody "templates/postitem.html"
    posts <- preprocess' =<< loadAll pattern
    applyTemplateList postItemTpl (tagsCtx tags) posts