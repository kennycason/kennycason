/* 
 * File:   Text.cpp - A simple Text library written in SDL
 * Author: kenny cason
 * Created on March 1, 2009,
 */

#include "Text.h"

using namespace std;
Text::~Text() {
    freeFont();
    TTF_Quit();
}

Text::Text() {
    if (TTF_Init() == -1) {
        printf("Unable to initialize SDL_ttf: %s \n", TTF_GetError());
    } else {
         font = NULL;
    }
    clrFG.r = 0;
    clrFG.g = 0;
    clrFG.b = 0;
}

Text::Text(string fontFile, int size) {
    if (TTF_Init() == -1) {
        printf("Unable to initialize SDL_ttf: %s \n", TTF_GetError());
        font = NULL;
    } else {
        loadFont(fontFile, size);
    }
    clrFG.r = 0;
    clrFG.g = 0;
    clrFG.b = 0;
}

bool Text::loadFont(string fontFile, int size){
    font = TTF_OpenFont(fontFile.c_str(), size);
    if (font == NULL){
        printf("Unable to load font: %s %s \n", fontFile.c_str(), TTF_GetError());
        return false;
     }
    clrFG.r = 0;
    clrFG.g = 0;
    clrFG.b = 0;
    return true;
}

int Text::getFontSize(){
    if(font != NULL) {
        return TTF_FontHeight(font);
    }
    return -1;
}

TTF_Font*  Text::getFont(){
    return font;
}

bool Text::isFont(){
    if(font == NULL) {
        return false;
    }
    return true;
}

void Text::setColor(int r, int g, int b){
    if(r >= 0 && r <= 255 && g >= 0 && g <= 255 && g >= 0 && g <= 255) {
        clrFG.r = r;
        clrFG.g = g;
        clrFG.b = b;
    } else {
        printf("RGB values must range from 0 - 255!\n");
    }
}

void Text::setColor(SDL_Color clrFG){
    this->clrFG = clrFG;
}

SDL_Color Text::getColor(){
    return clrFG;
}

void Text::drawText(SDL_Surface* screen, string text, int x, int y){
    SDL_Surface* sText = TTF_RenderText_Solid(font, text.c_str(), clrFG );
    SDL_Rect rcDest;
    rcDest.x = x;
    rcDest.y = y;
    SDL_BlitSurface( sText,NULL, screen,&rcDest );
    SDL_FreeSurface( sText );
}

void Text::drawText(SDL_Surface* screen, string text, int x, int y, int r, int g, int b) {
    SDL_Color clrFG;
    clrFG.r = r;
    clrFG.g = g;
    clrFG.b = b;
    clrFG.unused = 0;
    SDL_Surface* sText = TTF_RenderText_Solid(font, text.c_str(), clrFG );
    SDL_Rect rcDest;
    rcDest.x = x;
    rcDest.y = y;
    SDL_BlitSurface( sText,NULL, screen,&rcDest );
    SDL_FreeSurface( sText );
}

void Text::drawText(SDL_Surface* screen, string text, int x, int y, SDL_Color clrFG) {
    SDL_Surface *sText = TTF_RenderText_Solid( font, text.c_str(), clrFG );
    SDL_Rect rcDest;
    rcDest.x = x;
    rcDest.y = y;
    SDL_BlitSurface( sText,NULL, screen,&rcDest );
    SDL_FreeSurface( sText );
}

void Text::freeFont() {
    TTF_CloseFont(font);
}
