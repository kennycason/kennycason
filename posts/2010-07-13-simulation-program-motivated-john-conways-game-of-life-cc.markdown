---
title: Simulation Program (Motivated by John Conway's Game of Life) - C/C++
author: Kenny Cason
tags: john conways game of life, programming, simulation
---

In this little simulation demo I created four simple rules, of which can be activated by uncommenting them out in the source code. Though nothing too complex emerges, I still liked some the resulting behavior.
Particularly, rule1(), results in colors slowly grouping together in various forms. It may be hard to immediately noticed so watch carefully. This was motivated by <a href="/posts/2009-12-22-john-conways-game-of-life-mutation-cc.html">John Conway's Game of Life</a>.
The program was written in C/C++, all graphics are done using <a href="http://www.libsdl.org">SDL</a>
The whole source can be found here <a href="/code/c/Life2/main.cpp">main.cpp</a><br/>

<b>Compile</b><pre>g++ main.cpp -lSDL</pre>
<b>Run</b><pre>./a.out</pre>

<a href="/code/c/Life2/Life-1.png" target="_blank"><img src="/code/c/Life2/Life-1.png" width="400px" alt="Game of Life Patterns" /></td>
<td><a href="/code/c/Life2/Life-4.png" target="_blank"><img src="/code/c/Life2/Life-4.png" width="400px" alt="Game of Life Patterns" /></td></tr>
<tr><td><a href="/code/c/Life2/Life-8.png" target="_blank"><img src="/code/c/Life2/Life-8.png" width="400px" alt="Game of Life Patterns" /></td>
<td><a href="/code/c/Life2/Life-9.png" target="_blank"><img src="/code/c/Life2/Life-9.png" width="400px" alt="Game of Life Patterns" /></td>

Rule1()

```{.php .numberLines startFrom="1"}
for(int x = 0; x < worldWidth; x++) {
    for(int y = 0; y < worldHeight; y++) {
        int r = world[x][y].r;
        int g = world[x][y].g;
        int b = world[x][y].b;
        /*
          o o o
          o x o
          o o o
        */
        // RULES
        if(x > 0 && y > 0) { // top left
            if(world[x - 1][y - 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x - 1][y - 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x - 1][y - 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(y > 0) { // top
            if(world[x][y - 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x][y - 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x][y - 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(x < worldWidth - 1 && y > 0) { // top right
            if(world[x + 1][y - 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x + 1][y - 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x + 1][y - 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(x > 0) { //  left
            if(world[x - 1][y].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x - 1][y].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x - 1][y].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(x < worldWidth - 1) { // right
            if(world[x + 1][y].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x + 1][y].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x + 1][y].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(x > 0 && y < worldHeight - 1) { // bottom left
            if(world[x - 1][y + 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x - 1][y + 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x - 1][y + 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(y < worldHeight - 1) { // bottom
            if(world[x][y + 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x][y + 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x][y + 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(x < worldWidth - 1 && y < worldHeight - 1) { // bottom right
            if(world[x + 1][y + 1].r > 200) {
                r+=2; g--; b--;
            }
            if(world[x + 1][y + 1].g > 200) {
                r--; g+=2; b--;
            }
            if(world[x + 1][y + 1].b > 200) {
                r--; g--; b+=2;
            }
        }
        if(r < 0) {
            r = 0;
        }
        if(g < 0) {
            g = 0;
        }
        if(b < 0) {
            b = 0;
        }
        if(r > 255) {
            r = 255;
        }
        if(g > 255) {
            g = 255;
        }
        if(b > 255) {
            b = 255;
        }
        world[x][y].r = r;
        world[x][y].g = g;
        world[x][y].b = b;
    }
}

```
