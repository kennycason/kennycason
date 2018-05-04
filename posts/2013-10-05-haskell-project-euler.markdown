---
title: Haskell - Project Euler
author: Kenny Cason
tags: haskell, functional programming, project euler, λ\=
---

Currently I have completed <a href="/euler.html" target="_new">75 Project Euler problems in Java</a> so I decided to give it a go in Haskell. With the help of tutorials and good ol' fashion trial and error here are the first 10 problems.

This project's GitHub page can be found <a href="https://github.com/kennycason/euler_haskell" target="_new">here</a>

***1. Multiples of 3 and 5***

```haskell
import Euler

main = print (sum (filter (divisible [3,5]) [1..999]))
```

***2. Even Fibonacci numbers***

```haskell
import Euler

main = print (sum (filter even (fibm 4000000)))
```

***3. Largest prime factor***

```haskell
import Euler

main = print (last [x | x <- factors 600851475143, isPrime x])
```

***4. Largest palindrome product***

```haskell
import Euler

main = print (maximum 
                [z | y<-[1..999], x<-[y..999], 
                     let z = x * y, 
                     let r = reversei z, r == z])
```

***5. Smallest multiple***

```haskell
import Euler

{--
full range: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
optimize array:
if n % 20 == 0, then n % 10 == 0 and n % 5 == 0 and n % 2 == 0,
therefore cut out 10, 5, and 2
[11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
--}
divides1to10 :: Int -> Bool
divides1to10 n = let list = [11..20]
                 in all (\i -> (mod n i) == 0) list
                 
divideLoop :: Int -> Int
divideLoop n | divides1to10 n = n
             | otherwise = divideLoop (n + 1)

main = print (divideLoop 2520)
```

***6. Sum square difference***

```haskell
import Euler

sumOfSquares :: Int -> Int
sumOfSquares n = sum (map (\i -> i^2) [1..n])

squareOfSums :: Int -> Int
squareOfSums n = (sum [1..n])^2

diff :: Int -> Int
diff n = (squareOfSums n) - (sumOfSquares n)

main = print (diff 100)
```

***7. 10001st prime***

```haskell
import Euler

nthPrime :: Int -> Int -> Int
nthPrime n k | k == 10001 = n - 1
             | isPrime n = nthPrime (n + 1) (k + 1)
             | otherwise = nthPrime (n + 1) k

main = print (nthPrime 1 0)
```

***8. Largest product in a series***

```haskell
import Euler
import Data.Char (digitToInt)
import Data.List (tails)

s = "7316717653133062491922511967442657474235534919493496983520312774506326239578318016984801869478851843858615607891129494954595017379583319528532088055111254069874715852386305071569329096329522744304355766896648950445244523161731856403098711121722383113622298934233803081353362766142828064444866452387493035890729629049156044077239071381051585930796086670172427121883998797908792274921901699720888093776657273330010533678812202354218097512545405947522435258490771167055601360483958644670632441572215539753697817977846174064955149290862569321978468622482839722413756570560574902614079729686524145351004748216637048440319989000889524345065854122758866688116427171479924442928230863465674813919123162824586178664583591245665294765456828489128831426076900422421902267105562632111110937054421750694165896040807198403850962455444362981230987879927244284909188845801561660979191338754992005240636899125607176060588611646710940507754100225698315520005593572972571636269561882670428252483600823257530420752963450"

main = do
    let n = map digitToInt s
    print (maximum (map product [x | x <- map (take 5) (tails n), length x == 5]))
                
```

***9. Special Pythagorean triplet***

```haskell
import Euler

triplets limit = [ a * b * c
                      | c <- [1..limit]
                      , b <- [1..(c-1)]
                      , a <- [1..(b-1)]
                      , a + b + c == limit
                      , a^2 + b^2 == c^2]

main = print (head (triplets 1000))               
```

***10. Summation of primes***

```haskell
import Euler

main = print (sum (primeSieve 2000000))              
```

***Euler.hs***

This is where I place common functions that may be used in other Project Euler Problems

```haskell
module Euler
    (divisible
    ,fib
    ,fibs
    ,fibm
    ,isPrime
    ,factors
    ,reversei
    ,toDigits
    ,fromDigits
    ,primeSieve
    ,minusl
)
where

import Data.Bits


-- divisible()
divisible :: [Int] -> Int -> Bool
divisible divisors n = any (\divisor -> (mod n divisor) == 0) divisors


-- fib() - fibonacci sequence, generate n-th term
fib :: Int -> Integer
fib = (map fib [0 ..] !!)
   where fib 0 = 1
         fib 1 = 1
         fib n = fib (n-2) + fib (n-1)
   
-- fibs() - fibonacci sequence, generate n terms      
fibs :: Int -> [Integer]
fibs terms = [a | (a,b) <- take 
                               terms 
                               (iterate 
                                     (\(a,b) -> (b, a+b)) 
                                     (0,1))]
                               
-- fibm() - fibonacci sequence max terms, generate terms up to max term
fibm :: Int -> [Integer]
fibm max = [a | (a,b) <- takeWhile 
                                (\(a,b) -> a <= fromIntegral(max)) 
                                (iterate 
                                      (\(a,b) -> (b, a+b)) 
                                      (0,1))]                   
 
 
-- isPrime()
isPrime :: Int -> Bool
isPrime n | n <= 1 = False
          | otherwise = let root = sqrt (fromIntegral n)
                        in not (any 
                                   (\i -> (mod n i) == 0) 
                                   [2..truncate(root)])
                                   
                                   
-- primeSieve() - Sieve of Eratosthense
-- works but needs to go from 1 to root(n), not root(n) to 2
primeSieveSlow :: Int -> [Int]
primeSieveSlow n = let upper = truncate (sqrt (fromIntegral n))
               in siever ([2] ++ [3,5..n]) upper
               where siever set 1 = []
                     siever set 2 = set
                     siever set n | isPrime (head set) = siever (filterMultiples set n) (n - 1)
                                  | otherwise = filterMultiples set n
                                  where filterMultiples set n = filter (\x -> x == n || (mod x n) /= 0) set
    

primeSieve m = 2 : sieve [3,5..m]
             where sieve [] = []
                   sieve (p:xs) = p : sieve (minusl xs [p * p, p * p + 2 * p.. m])
                   
                                                    
-- factors()
factors :: Int -> [Int]
factors n = [x | x <- [2..s], (mod n x) == 0]
    where s = floor (sqrt (fromIntegral n))
    
    
-- toDigits()
toDigits :: Int -> [Int]
toDigits 0 = []
toDigits x = toDigits (div x 10) ++ [mod x 10]


-- fromDigits()
fromDigits = foldl addDigit 0
                where addDigit num d = 10 * num + d


-- reversei()
reversei :: Int -> Int
reversei n = fromDigits (reverse (toDigits n))


-- listSubtract
minusl :: Ord a => [a] -> [a] -> [a]
minusl [] _ = []
minusl xs [] = xs
minusl l1@(x:xs) l2@(y:ys)
    | x > y = minusl l1 ys
    | x < y = x : minusl xs l2
    | otherwise = minusl xs l2
```