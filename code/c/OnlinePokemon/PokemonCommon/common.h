// Common include file for server and client.
// Contains structures such as player info, inventory, etc.

#ifndef _COMMON_H_
#define _COMMON_H_


#define PORT        5009
#define MAX_CLIENTS     50

#define BYTE            unsigned char

#define SCREEN_X 640
#define SCREEN_Y 480

/* error codes used for debuging */
#define ERROR_MYSQL_CONNECT                 0
#define ERROR_MYSQL_INIT                    2
#define ERROR_MYSQL_BADQUERY                4
#define ERROR_SDL_FAILED_TO_OPEN_SOCKETS    10
#define ERROR_SDL_INIT                      12
#define ERROR_SDL_VIDEO                     14
#define ERROR_SDL_TIMER                     16
#define ERROR_SDL_AUDIO                     18
#define ERROR_SDL_SETVIDEOMODE              20
#define ERROR_SDL_TTF                       22
#define ERROR_SDL_NET                       24

#define ERROR_FONT_NOT_LOADED               30

#define LOG_ERROR       0
#define LOG_INFO        1
#define LOG_DEBUG       2

/* Action Constants */
#define ACTION_TRY_MOVE     1

#define TILE_COLLISION      68
#define TILE_WATER          69

/* Server to client messages go here */
#define SERVER_ERROR         0
#define SERVER_ACK           1
#define SERVER_KICK          255

/* Client to server messages go here */
#define CLIENT_SEND_USERNAME 0
#define CLIENT_SEND_PASSWORD 1
#define CLIENT_DISCONNECT    255

/* client states */
#define STATE_LOGIN_USERNAME 0
#define STATE_LOGIN_PASSWORD 1
#define STATE_PLAYING        10

#define GS_STARTSCREEN  1
#define GS_STARTMENU    2
#define GS_CREDITS      3
#define GS_LOGIN        4
#define GS_VERIFYLOGIN  5
#define GS_EXIT         6
#define GS_PAUSE        7
#define GS_GAME         8
#define GS_BATTLE       9

#define MOVE_UP         1
#define MOVE_DOWN       2
#define MOVE_LEFT       3
#define MOVE_RIGHT      4

#define FACE_UP         1
#define FACE_DOWN       2
#define FACE_LEFT       3
#define FACE_RIGHT      4

#define max(a,b)	((a > b) ? a : b)
#define min(a,b)	((a < b) ? a : b)

#define MAX_MAPS 10
#define MAX_POKEMON 151

#include <time.h>
#include "stdlib.h"
#include "stdio.h"
#include "string.h"
#include <SDL/SDL.h>
#include <SDL/SDL_net.h>
#include <SDL/SDL_ttf.h>
#include <SDL/SDL_mixer.h>
#include <SDL/SDL_image.h>
#include "Sprite.h"
#include "Text.h"
#include "Pokedex.h"
#include "Pokemon.h"
#include "SDLMappy.h"
#include "mysql/mysql.h"

#include "player.h"
#endif /* End of _COMMON_H_ */

