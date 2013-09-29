/*  Intended to handle all actions that are immediate to just the player struct */
#ifndef _PLAYER_H_
#define _PLAYER_H_

#include "Sprite.h"
#include "Pokemon.h"

#define BYTE  unsigned char

struct ClientPlayerz {
      char FirstName[32];
      char LastName[32];
      char Email[64];
      char Passowrd[32];
      int Gender;
      int BirthYear;
      int BirthMonth;
      int BirthDay;

      BYTE action;
      BYTE area;
      BYTE matX;   // position in the matrix
      BYTE matY;
      BYTE image;

      // client only stuff
      int x;
      int y;
      int face;

      Sprite currentImage;
      Sprite upstand;
      Sprite upwalk;
      Sprite downstand;
      Sprite downwalk;
      Sprite leftstand;
      Sprite leftwalk;
      Sprite rightstand;
      Sprite rightwalk;

      BYTE items[60];

};

struct ServerPlayerz {
      char FirstName[32];
      char LastName[32];
      char Email[64];
      char Password[32];
      int Gender;
      int BirthYear;
      int BirthMonth;
      int BirthDay;
      bool LoggedIn;
      int ID;

      BYTE action;
      BYTE area;
      BYTE matX;   // position in the matrix
      BYTE matY;
      BYTE image;


      BYTE items[60];

      Pokedex pokedex[MAX_POKEMON];
      Pokemon team[6];

};

#endif /* End of _PLAYER_H_ */
