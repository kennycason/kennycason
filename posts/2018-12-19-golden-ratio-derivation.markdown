---
title: Golden Ratio Derivation
author: Kenny Cason
tags: math
---

A few years ago I was reading various derivations of %%\pi%%, I stumbled upon continued fractions and a some bizarre series by [*Ramanujan*](https://en.wikipedia.org/wiki/Srinivasa_Ramanujan).

I was very curious how one would stumble upon them and became interested in what sorts of other patterns I could find in my own explorations.
I randomly began scribbling various continued fractions that I frankly was not very sure how to extract any meaningful information from.
As such, I fell back to the simple method of evaluating them to various degrees of precision.

One fraction stood out and was simple enough, so I gave it a try:

$$
\begin{aligned}
x = 1+\frac{1}{1+\frac{1}{1+\frac{1}{1+\frac{1}{1 + ...}}}}
\end{aligned}
$$

At this point I had no idea what %%x%% was.

Below are the results of iterating the fraction to various "depths."

$$
\begin{aligned}
&& depth(1)  &= 1+\frac{1}{1+1} = 1\frac{1}{2} &= \frac{3}{2}\\\\
\\\\
&& depth(2)  &= 1+\frac{1}{1+\frac{1}{1+1}} = 1\frac{1}{1+\frac{1}{2}} = 1\frac{1}{\left(\frac{3}{2}\right)} = 1\frac{2}{3} &= \frac{5}{3}\\\\
\\\\
&& depth(3)  &= 1+\frac{1}{1+\frac{1}{1+\frac{1}{1+1}}} = 1+\frac{1}{1+\frac{1}{1+\frac{1}{2}}} = 1+\frac{1}{1+\left(\frac{2}{3}\right)} = 1+\frac{1}{\left(\frac{5}{3}\right)} = 1\frac{3}{5} &= \frac{8}{5}\\\\
\\\\
&& depth(4)  &= 1+\frac{1}{1+\frac{1}{1+\frac{1}{1+\frac{1}{1+1}}}} = 1+\frac{1}{1+\frac{3}{5}} = 1+\frac{1}{\left(\frac{8}{5}\right)} = 1\frac{5}{8} &= \frac{13}{8}\\\\
\\\\
&& depth(5)  &= 1+\frac{1}{1+\frac{5}{8}} = 1+\frac{1}{\left(\frac{13}{8}\right)} = 1\frac{8}{13} &= \frac{21}{13}\\\\
\\\\
&& depth(6)  &= 1+\frac{1}{1+\frac{8}{13}} = 1+\frac{1}{\left(\frac{21}{13}\right)} = 1\frac{13}{21} &= \frac{34}{21}\\\\
\\\\
&& depth(7)  &= 1+\frac{1}{1+\frac{13}{21}} = 1+\frac{1}{\left(\frac{34}{21}\right)} = 1\frac{21}{34} &= \frac{55}{34}\\\\
\end{aligned}
$$

From %%depth(5)%% we can see a clear pattern in the continued fraction and we just substitute the previous result as we iterate.

After evaluating a few iterations another pattern emerges between the numerator and denominator of the result.

Notably that the ratio is: %%\frac{Fibonacci_{n}}{Fibonacci_{n-1}}%%

This alone was an interesting discovery for me as I did not expect the Fibonacci sequence to show itself.
My next step was to explore the pattern that lies in the ratio of two Fibonacci numbers.

To do so, I decided to simply divide the fractions out and observe the resulting decimal form.

The results:

| Fn / Fn-1 | result          |
| --------- | --------------- |
| 3/2       | 1.5             |
| 5/3       | 1.666...        |
| 8/5       | 1.6             |
| 13/8      | 1.625           |
| 21/13     | 1.61538         |
| 34/21     | 1.61904         |
| 55/34     | 1.61764         |
| ...       | ...             |
| F101/F100 | 1.6180339887    |

Of course, upon seeing this I immediately recognized it as the Golden Ratio, %%φ%%!

Or, more concisely:

$$
\begin{aligned}
φ = \lim_{n\to\infty}\frac{Fibonacci_{n}}{Fibonacci_{n-1}}
\end{aligned}
$$

How did I not already know the relationship between %%φ%% and the Fibonacci sequence? I do not know.

I was still curious about what we can do with that continued fraction.
I saw an obvious, but interesting pattern in the continued fraction:

$$
\begin{aligned}
φ = 1+\frac{1}{1+\frac{1}{1+\frac{1}{1+\frac{1}{1 + ...}}}}
\end{aligned}
$$

It was the all-to-familiar pattern of [*Self-Similarity*](https://en.wikipedia.org/wiki/Self-similarity) present in the continuation.

That is, we could take denominator of the fraction and it is.... equal to itself!

Lets subtract 1 both sides to make the denominator a bit more visible on the right side of the equation.

$$
\begin{aligned}
φ - 1 = \frac{1}{1+\frac{1}{1+\frac{1}{1+\frac{1}{1 + ...}}}}
\end{aligned}
$$

Now we can more clearly see that the denominator is exactly what we defined as %%φ%% above.

Thus we can re-write the right side of the equation as:

$$
\begin{aligned}
φ - 1 = \frac{1}{φ}
\end{aligned}
$$

The result is that we have converted the continued fraction to a recurrence relation.
While this is not novel, this was the first time I truly made the connection of converting between continued fractions and recurrence relations.

Previously, with the hairy notation of the infinitely continued fraction, it was very hard to apply algebraic rules to the equation.
However, now that we have figured out how to exploit it and transform it into a recurrence relation, we can more easily apply operations and analysis to it.


An example from Wikipedia that I found afterwards: We can re-arrange the expression as an 2nd degree polynomial and then apply quadratic formula to solve:

Original expression:
$$
\begin{aligned}
φ - 1 = \frac{1}{φ} \\\\
\end{aligned}
$$

Multiply both sides by %%φ%%:
$$
\begin{aligned}
φ^{2} - φ = 1 \\\\
\end{aligned}
$$

Subtract 1 both sides:
$$
\begin{aligned}
φ^{2} - φ - 1 = 0 \\\\
\end{aligned}
$$

Applying the [Quadratic Formula](https://en.wikipedia.org/wiki/Quadratic_formula) for %%φ%% yields:
$$
\begin{aligned}
φ = \frac{1 + \sqrt{5}}{2} \\\\
\end{aligned}
$$

I had always wondered how in the world %%\sqrt{5}%% would find it's way into equations, and now I know. :)

<br/>
<div style="text-align: center; font-size:72px; width:100%;">φ</div>
<br/>
