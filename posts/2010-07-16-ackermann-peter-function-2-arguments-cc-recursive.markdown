---
title: Ackermann–Péter function (2 arguments) - C/C++ - Recursive Implentation
author: Kenny Cason
tags: Ackermann, Ackermann–Péter, function, Mathematics, recursive, total computable, Wilhelm
---

This is an implementation of the 2 argument version of the <a target="blank" href="http://en.wikipedia.org/wiki/Ackermann_function">Ackermann Function</a> (i.e. Ackermann-Péter function). In essence, this is an example of a very simple recursive function is an example of a total computable function that is not primitive recursive. Instead of making the internet even more redundant with unnecessary text, just click the above link to view the entire Wikipedia article.
Function's like these are very fun to play with. I often think about the human brain and how it functions when I see these kinds of functions. Taking input from the environment and processing it, sometimes recursively (like in a thought cycle), then converging to some or many outputs.
C Implentation: 
<b>note</b>This function grows extremely fast, such that it quickly out grows any primitive type in C, including the largest "unsigned long long" type. Though the program will likely crash with a 0xC0000005 error from too much recursion :) 
It should run fine from A(0,0) to A(4,0) and likely crash while computing A(4,1), which is 65533. A(4,0) is 13, so the number of recursive calls jumps up enormously! I will be working on a custom, larger data type as well as a way around the error caused by too much recursion.

```c
/**
 * Ackermann function - recursive implementation
 */
unsigned long long A(unsigned long long m, unsigned long long n) {
    if(m == 0) {
        return n + 1;
    }else if(n == 0) {
        return A(m - 1, 1);
    } else {
        return A(m - 1, A(m, n - 1));
    }
}

```
The entire source implentation can be found <a target="blank" href="http://ken-soft.com/code/c/ackermann/main.cpp">here</a>
I obtained a list of some of the expected values from this site ->     <a target="blank" href="http://www-users.cs.york.ac.uk/susan/cyc/a/ackermnn.htm">http://www-users.cs.york.ac.uk/susan/cyc/a/ackermnn.htm</a>
Here are some of the values for 0 < m < 5, 0 < n < 6
<table  align="left" bgcolor="#222" border="0">
<tbody>
<tr bgcolor="#111"><td></td><td>n=0</td><td>n=1</td><td>n=2</td><td>n=3</td><td>n=4</td><td>n=5</td></tr>
<tr><td bgcolor="#111">m=0</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td></tr>
<tr><td bgcolor="#111">m=1</td><td>2</td><td>3</td><td>4</td><td>5</td><td>6</td><td>7</td></tr>
<tr><td bgcolor="#111">m=2</td><td>3</td><td>5</td><td>7</td><td>9</td><td>11</td><td>13</td></tr>
<tr><td bgcolor="#111">m=3</td><td>5</td><td>13</td><td>29</td><td>61</td><td>125</td><td>253</td></tr>
<tr><td bgcolor="#111">m=4</td><td>13</td><td>65533</td><td>265536-3</td><td>2265536-3</td><td>A(3,2265536-3)</td><td>A(3,A(4,4))</td></tr>
</tbody>
</table>
Some of the patterns formed are very amazing. For example: A(2, n), 0 <= n < infinite, will list odd numbers starting from 3 to infinite.
To demonstrate this just insert the below loop into your code:

```c
for(int n = 0; n < 100; n++) {
      std::cout << "A(2, " << n << ") = " << A(2, n) << std::endl;
}

```