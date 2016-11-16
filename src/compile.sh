#!/bin/sh
rm site
# -package haskell98
ghc -threaded -o site Main.hs
rm -f *.hi
rm -f *.o
