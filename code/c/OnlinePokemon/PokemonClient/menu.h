/* menu.h - menu, title and credits screens */

#ifndef _MENU_H_
#define _MENU_H_

#include "../PokemonCommon/common.h"

void inittitle();
void destroytitle();
void drawtitle();
int handletitle();

void initmenu();
void destroymenu();
void drawmenu();
int handlemenu();

void initcredits();
void destroycredits();
void drawcredits();
int handlecredits();

void initstartscreen();
void destroystartscreen();
void drawstartscreen(ClientPlayerz);
int handlestartscreen();

extern void drawText(TTF_Font* font, char* txt, int x, int y, int r, int g, int b);
extern void drawText(TTF_Font* font, char* txt, int x, int y, SDL_Color);
extern TTF_Font* fntCourier;

#endif /* End of _MENU_H_ */
