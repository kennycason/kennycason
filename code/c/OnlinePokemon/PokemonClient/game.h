/* game.h - game header goes in here! */

#ifndef _GAME_H_
#define _GAME_H_

#include "../PokemonCommon/common.h"

#define MAXCHAT 15
#define MAXFULLCHAT 28

int drawmapmenu();
int addToChat(char*);

void initmapscreen();
void destroymapscreen();
void drawmapscreen();
int handlemapscreen(TCPsocket);
void drawChatbox();
int loadMap(int area);

int do_net_mapupdate(SDLNet_SocketSet,TCPsocket);

void drawText(TTF_Font* font, char* txt, int x, int y, int r, int g, int b);
void drawText(TTF_Font* font, char* txt, int x, int y, SDL_Color);

extern SDLMappy* map;
extern SDL_Surface *screen;
extern int gamestate;
extern Mix_Music *music;   /* SDL_Mixer stuff here */

extern struct ClientPlayerz player;
extern BYTE eimg;
extern Text SDLtext;

#endif /* End of _GAME_H_ */
