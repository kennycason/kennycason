---
title: Is Prime Number Algorithm
author: Kenny Cason
tags: algorithm, factor, mathematics, number, prime
---

I was randomly surfing around the internet when I stumbled upon Google Labs Aptitude Test (GLAT)<a href="http://cruftbox.com/blog/archives/001031.html">(Found here)</a>. I can't really remember which links I followed from there but I stumbled into a question that asks to find the first 10 consecutive digits of E that are prime. While definitely not a hard task I wrote a small program in C to test whether or not a number is prime. 

The core function isPrime() is below

```c
bool isPrime(unsigned long long n) {
    // prime numbers are natural by definition
    if(n <= 1) {
        return false;
    } 
    unsigned long long root = sqrtl(n);
    // is the number divisible by n, such that n >= 2 and n <= sqrt(number)?
    // if so then the number is composite
    for(unsigned long long i = 2; i <= root; i++) {
        if(n % i == 0) {
            return false;
        }
    }
    return true; // it is prime!
}

```
<b>note:</b> I use long long just to allow the calculation of bigger numbers. Though if the number gets to large enough this algorithm can still slow down a bit, as it could potentially have to iterate from 3 to the square root of the number being tested. For most cases this algorithm executes plenty fast enough and it is definitely better than iterating over every number between 1 and N, as many sites do.



