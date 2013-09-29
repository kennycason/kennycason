/* 
 * File:   Text.h - A simple Text library written in SDL
 * Author: kenny cason
 * Created on March 1, 2009
 */
#ifndef _TEXT_H
#define	_TEXT_H

#ifdef __cplusplus
    #include <cstdlib>
#else
    #include <stdlib.h>
#endif

#include <iostream>
#include <fstream>
#include <string>

#ifdef __APPLE__
    #include <SDL/SDL.h>
    #include <SDL/SDL_ttf.h>
#else
    #include <SDL/SDL.h>
    #include <SDL/SDL_ttf.h>
#endif

using namespace std;

class Text {
public:
    ~Text();
    Text();
    Text(string fontFile, int size);
    bool loadFont(string fontFile, int size);
    int getFontSize();
    TTF_Font*  getFont();
    bool isFont();
    void setColor(int r, int g, int b);
    void setColor(SDL_Color clrFG);
    SDL_Color getColor();
    void drawText(SDL_Surface* screen, string text, int x, int y);
    void drawText(SDL_Surface* screen, string text, int x, int y, int r, int g, int b);
    void drawText(SDL_Surface* screen, string text, int x, int y, SDL_Color clrFG);
    void freeFont();

private:
    TTF_Font* font;
    SDL_Color clrFG;
};
#endif /* End of _TEXT_H */

