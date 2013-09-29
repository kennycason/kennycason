---
title: John Conway's Game of Life + Mutation (C/C++)
author: Kenny Cason
tags: C++, cellular automata, evolution, John Conways Game of Life, Mutation, self-organization, Simulation
---

I've always been interested in AI, evolution simulations, and other interesting problems. But I will never forget one of my all time favorite classics, <a href="http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life" target="_blank">John Conway's Game of Life.</a>.
This simulation implements a few just a few simple rules, yet relatively complex structures emerge.
The rules are:<br/>
<b>1.</b> Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.<br/>
<b>2.</b> Any live cell with more than three live neighbors dies, as if by overcrowding.<br/>
<b>3.</b> Any live cell with two or three live neighbors lives on to the next generation.<br/>
<b>4.</b> Any dead cell with exactly three live neighbors becomes a live cell.<br/>

Below are some of the patterns that I found and thought were interesting:
<table><tr><td><a href="/code/c/Life/Life-SimplePatterns.png" target="_blank"><img src="/code/c/Life/Life-SimplePatterns.png" alt="Game of Life Simple Patterns" /></td>
<td><a href="/code/c/Life/Life-ComplexPatterns.png" target="_blank"><img src="/code/c/Life/Life-ComplexPatterns.png" alt="Game of Life Complex Patterns" /></td></tr></table>

After watching many trails I noticed one thing immediately; The simulation always eventually "dies down", or reaches some equilibrium state and it is usually comprised of a bunch of simple structures. So I decided to add a <b>mutation</b> factor to the simulation such that upon mutation, A living cell dies and a dead cell comes to life. This significantly increased the life of the simulation. In fact with the right mutation rate the simulation will continue endlessly. 

In my quick little simulation I also introduced a "wrap around" feature so that structures can move infinitely in any direction.

The demo is written in C and uses the <a href="http://www.libsdl.org"  target="_blank">SDL Library</a> for drawing the points and is in 640x480 resolution. The source can be downloaded <a href="/code/c/Life/Life.zip">here</a>.
To Compile:
<code>g++ main.cpp -o LIFE -lSDL</code>
To Run:
<code>./LIFE</code>

Every thing from mutation rate to cell generation at startup is configurable. Read the <b>ReadMe.txt</b> file included to see what's configurable.

Screenshots:
<table><tr><td><a href="/code/c/Life/Life-1.png" target="_blank"><img src="/code/c/Life/Life-1.png" alt="Life Screenshot"  width="175"/></td>
<td><a href="http://ken-soft.com/code/c/Life/Life-2.png" target="_blank"><img src="/code/c/Life/Life-2.png" alt="Life Screenshot"  width="175" /></td><td><a href="/code/c/Life/Life-ChangedRules.png" target="_blank"><img src="/code/c/Life/Life-ChangedRules.png" alt="Life Screenshot Changed Rule"  width="175" /></td></tr></table>

Notice that the 3rd Image is a result of changing rule 4 to " Any dead cell with <b>two or three</b> live neighbors becomes a live cell." (I made this typo when first writing the program and was surprised by the result :)) Try changing the rules and see what results you find. 
I hope to release a more complex version in the future.