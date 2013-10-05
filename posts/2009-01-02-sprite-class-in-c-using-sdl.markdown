---
title: SDL Sprite Class in C++
author: Kenny Cason
tags: 2d, animation, c++, class, game, graphics, library, programming, sdl, sprite
---

This is a simple Sprite class that I use for game development in C++ using SDL (<a href="http://www.libsdl.org/" target="_blank">www.libsdl.org</a>).
Features include sprite animation, rotation, stretching, transparencies, and other commonly used sprite functions.

I hope this is somewhat useful. The source is available and should be fairly simple to work with.
Feel free to modify it however you want. please comment about any bugs are suggestions that you have. Thanks.

<strong>Clone from GitHub</strong> <a href="https://github.com/kennycason/sdl_sprite"><span style="font-size:18px">HERE</span></a><br/>

<a href="/code/c/SDL/Sprite/sshot.PNG" target="_blank" ><img src="/code/c/SDL/Sprite/sshot.PNG" width="250" alt="SDL Sprite C++"/></a>

To view a demo of some of the Sprites classes features, and have access to some sample Bitmap images used with the Sprite library,  download the following zip file. It also contains the project settings that I used in Dev C++ and SDL 1.2.12 to compile.


All examples use these sprites
<table><tr><td><a href="/code/c/SDL/Sprite/samus_normal_run.bmp" target="_blank" ><img width="90%" src="/code/c/SDL/Sprite/samus_normal_run.bmp"  alt="SDL Sprite C++"/></a></td>
<td><a href="/code/c/SDL/Sprite/character.bmp" target="_blank" ><img width="90%" src="/code/c/SDL/Sprite/character.bmp"  alt="SDL Sprite C++"/></a></td></tr></table>

<b>Demo using special effects </b>


```{.cpp .numberLines startFrom="1"}
// Initialize SDL, etc
...
...
Sprite* s1 = new Sprite("sprites/samus_normal_run.bmp",10,60); // load a BMP that contains 10 frames
                                                // set the animation speed to 60 milliseconds
// set RGB(255,0,255) as transparent, rotate 180 degrees, flip horizontal and reverse animation
s1->setTransparency(255,0,255)->rotate180()->flipHorizontal()->reverseAnimation();
// etc
...
...

// Main loop
// clear background to black, RGB(0,0,0)
SDL_FillRect(screen, 0, SDL_MapRGB(screen->format, 0, 0, 0));
// animate and draw the sprite
s1->animate()->draw(screen,0,0);
SDL_Flip(screen);
```