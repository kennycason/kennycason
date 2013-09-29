#ifndef _CLIENT_H_
#define _CLIENT_H_

#include "../PokemonCommon/common.h"
#include "game.h"

SDL_Surface *screen;

int gamestate = GS_STARTMENU;
Mix_Music *music;   /* SDL_Mixer stuff here */

struct ClientPlayerz player;

SDLMappy* map;

long timer;
Text SDLtext;

#endif /* End of _CLIENT_H_ */
