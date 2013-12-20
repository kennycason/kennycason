---
title: Haskell Understanding Folds
author: Kenny Cason
tags: haskell, folds, functional programming, λ\=
---

This post is to show a few visuals to help understand foldr and foldl in Haskell. 

foldr and foldl using (+). For the (+) function both foldl and foldr are the same, that is because addition is commutative. The '0' after (+) is because the (+) function takes two arguments (+ a b), as such 0 serves as an initial numer to start adding from. 
```{.haskell .numberLines startFrom="1"}
(foldl (+) 0 [1..5]) -- equals 15
-- expands to
(((((0 + 1) + 2) + 3) + 4) + 5) 

(foldr (+) 0 [1..5]) -- also equals 15
-- expands to
(1 + (2 + (3 + (4 + (5 + 0)))))
```

foldr and foldl using (-). Subtraction in folds can be a bit harder to imagine without seeing how they expand, but should be easy to understand afterwards.
```{.haskell .numberLines startFrom="1"}
(foldl (-) 0 [1..5]) -- equals -15
-- expands to
(((((0 - 1) - 2) - 3) - 4) - 5)

(foldr (-) 0 [1..5]) -- equals 3 
-- expands to
(1 - (2 - (3 - (4 - (5 - 0)))))
```

Another example using a function other than (+), but will expand the same
```{.haskell .numberLines startFrom="1"}
let f x y = x^2 + y^2
(foldr (f) 0 [1..5]) -- equals 28503768830187227146817
-- expands to
(f 1 (f 2 (f 3 (f 4 (f 5 0)))))

(foldl (f) 0 [1..5]) -- equals 1373609
-- expands to
(f (f (f (f (f 0 1) 2) 3) 4) 5) 
```
