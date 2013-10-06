---
title: Haskell - Introduction
author: Kenny Cason
tags: haskell, functional programming, λ\=
---

I have spent the past 4-6 months playing with Haskell on the side. While I've yet come to the conclusion that one paradigm of programming is inherently better than the other, I have at least found it very intellectually rewarding and exciting to take a step back, and try to implement algorithms, data structures, and other hackings using a functional programmer's mindset. I can't say that I've fully grasped everything but I imagine it's just a function of time. 

First, I highly recommend the following book: "Learn You a Haskell for Great Good"

<a href="http://learnyouahaskell.com/" target="_new"><img src="/images/learnyouahaskell.jpg" width="100px"/></a>

I uploaded most of my Haskell examples to GitHub, found <a href="https://github.com/kennycason/haskell" target="_new">here</a>

I chose a few random examples to demonstrate some of the cool examples that made me enjoy Haskell

Simple Functions

```{.haskell .numberLines startFrom="1"}
f x y = x*x + y*y
g y = f 0 y

main = do
    print (f 2 3)
    print (g 3)
```

Use of Gaurds

```{.haskell .numberLines startFrom="1"}
guess x
            | x > 27 = "Too high!"
            | x < 27 = "Too low!"
            | otherwise = "Correct!"

main = do
print (guess 100)
print (guess 0)
print (guess 27)
```

Filters

```{.haskell .numberLines startFrom="1"}
main = do
    print (filter even [1..10])
    print (filter (>5) [1..10])
```

List Comprehensions

```{.haskell .numberLines startFrom="1"}
-- ∀x∈S, p(x)
-- [ x | x <- s, p x ]

p = [ x+5 | x <- [1,2,3] ]
f x y = x + y

main = do
    print p
    print ([ x+5 | x <- [1,2,3] ])
    print ([ x | x <- [2..10], 10 `mod` x == 0])
    print ([ team ++ " " ++ player |
             team <- ["red", "blue"],
             player <- ["soldier", "pyro", "scout"] ])

    print ([ x | x<-[1..10], even x ])

    print ([ f x y
                | x <- [1..10]
                , y <- [1..10]])
```

Function Composition

```{.haskell .numberLines startFrom="1"}
main = do
    -- f(g(h(k(x)))) - ugly
    -- (f.g.h.k)(x) - pretty
    print ((not.odd) 4)
    print ((length.head.words) "University of Arkansas")
    
    let f x = 2 * x + 3
    let g x = x * x
    let h x = sin x

    print ((f.g.h) 3)
    print (f (g (h 3)))
```

Simple, Traditional Fibonacci Sequence Implementation

```{.haskell .numberLines startFrom="1"}
-- simple, naive implementation
slow_fib :: Int -> Integer
slow_fib 0 = 1
slow_fib 1 = 1
slow_fib n = slow_fib (n-2) + slow_fib (n-1)

-- a memoized version. The magic lies in the "map fib [0 ..]" which is 
-- the fibonacci sequence mapped to an infinite list! ^_^ which is only
-- possible due to the lazy nature of Haskell.
memoized_fib :: Int -> Integer
memoized_fib = (map fib [0 ..] !!)
   where fib 0 = 1
         fib 1 = 1
         fib n = memoized_fib (n-2) + memoized_fib (n-1)
```

This was my first attempt at implementing a Binary Tree. 
I have since found more eloquent solutions :)

```{.haskell .numberLines startFrom="1"}
data Tree = Node { value::Int, left::Tree, right::Tree } | Null deriving (Show, Eq)

total :: Tree -> Int
total Null = 0
total node = value node + total (left node) + total(right node)

size :: Tree -> Int
size Null = 0
size node = 1 + size (left node) + size (right node)

add :: Int -> Tree -> Tree
add v Null = (Node v Null Null)
add v node
    | v > (value node) = Node (value node) (left node) (add v (right node)) -- insert into right subtree
    | v < (value node) = Node (value node) (add v (left node)) (right node) -- insert into left subtree
    | otherwise = node -- value is(value node)new so add it
```

