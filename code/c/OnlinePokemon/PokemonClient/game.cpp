#include "game.h"

extern SDL_Surface *screen;
extern int gamestate;
extern Mix_Music *music; /* SDL_Mixer stuff here */
extern TTF_Font *systemfont;
extern struct ClientPlayerz player;
extern BYTE eimg;

TTF_Font* fntCourier;
char* text = new char[128];
int chatbox = 0, startmenu = 0;

char outgoingmessage[80] = {0};

/* Client-specific structures */
struct OtherPlayers {
    BYTE actualImage;
    BYTE matX;
    BYTE matY;
    BYTE moveX;
    BYTE moveY;
} otherplayers[MAX_CLIENTS];

Sprite mapmenu;

char meMoveX = 0, meMoveY = 0;
unsigned char hilited = 255;
int scopeX, scopeY, camX = 0, camY = 0;
int mapnumber, othersInArea = 0;

char chatwin[MAXCHAT][20];
char chatfull[MAXFULLCHAT][80];

double ticksLastUpdate = 0;

int addToChat(char* message) {
    for (int i = 1; i < MAXCHAT; i++) {
        strcpy(chatwin[i - 1], chatwin[i]);
    }
    strncpy(chatwin[MAXCHAT - 1], message, 19);
    for (int i = 1; i < MAXFULLCHAT; i++) {
        strcpy(chatfull[i - 1], chatfull[i]);
    }
    strncpy(chatfull[MAXFULLCHAT - 1], message, 79);
    return 0;
}

void initmapscreen() {
    player.x = 224;
    player.y = 216;
    player.face = FACE_DOWN;
    chatbox = 0;

    player.area = 0;
    loadMap(player.area);

    Sprite tilesheet = Sprite("tiles/chars/1.bmp", 1, 0);
    tilesheet.setTransparency(255, 0, 255);
    // load the animations of the tile sheet
    player.downstand = Sprite(tilesheet.getRect(0, 0, 16 , 32), 1, 0);
    player.upstand = Sprite(tilesheet.getRect(16 * 6, 0, 16 , 32), 1, 0);
    player.leftstand = Sprite(tilesheet.getRect(16 * 3, 0, 16 , 32), 1, 0);
    player.rightstand = Sprite(tilesheet.getRect(16 * 9, 0, 16 , 32), 1, 0);

    player.downwalk = Sprite(tilesheet.getRect(16, 0, 32 , 32), 2, 200);
    player.upwalk = Sprite(tilesheet.getRect(16 * 6 + 16, 0, 32 , 32), 2, 200);
    player.leftwalk = Sprite(tilesheet.getRect(16 * 3 + 16, 0, 32 , 32), 2, 200);
    player.rightwalk = Sprite(tilesheet.getRect(16 * 9 + 16, 0, 32 , 32), 2, 200);

  //  player.down = Sprite(tilesheet.getRect(0, 0, 32, 16), 2, 200);
  //  player.up = Sprite(tilesheet.getRect(0, 16, 32, 16), 2, 200);
  //  player.right = Sprite(tilesheet.getRect(0, 32, 32, 16), 2, 200);
 //   player.left = Sprite(tilesheet.getRect(0, 48, 32, 16), 2, 200);

    player.currentImage = player.downstand;
    /* This is the music to play. */
    music = Mix_LoadMUS("sound/1.mid");
    Mix_PlayMusic(music, -1);
    mapmenu = Sprite("tiles/ui/mapmenu.bmp", 1, 0);
    mapmenu.setTransparency(255, 0, 255);
    fntCourier = TTF_OpenFont("fonts/coure.fon", 10);
    SDLtext = Text("fonts/coure.fon", 10);
}

void destroymapscreen() {
    Mix_HaltMusic();
    Mix_FreeMusic(music);
}

void drawmapscreen() {
    //printf("entering draw map screen\n");
    double ticksDiff;
    int xoff, yoff;
    ticksDiff=SDL_GetTicks()-ticksLastUpdate;
    xoff = (int)((double)meMoveX*ticksDiff/16.0); // smooth screen scrolling
    yoff = (int)((double)meMoveY*ticksDiff/16.0);

    Uint32 color;
    color = SDL_MapRGB(screen->format, 0, 0, 0); // make a black background
    SDL_FillRect(screen, NULL, color);

    map->MapUpdateAnims();
   // printf("Before Map Move\n");
/*
    if(xoff == 0) {
    	if(player.face == FACE_LEFT) {
    		player.currentImage = player.leftstand;
    	} else if(player.face == FACE_RIGHT) {
    		player.currentImage = player.rightstand;
    	}
    }
    if(yoff == 0) {
    	if(player.face == FACE_UP) {
    		player.currentImage = player.upstand;
    	} else if(player.face == FACE_DOWN) {
    		player.currentImage = player.downstand;
    	}
    }*/
    map->MapMoveTo(-224 + player.matX * 16+xoff, -216 + player.matY * 16+yoff);
  //    map->MapMoveTo(0 , 0);

    //map->DrawParallax(screen);
    map->MapChangeLayer(0);
    map->MapDrawBGT(screen);


    player.currentImage.animate();
    player.currentImage.draw(screen, player.x, player.y-16);
    //map->MapChangeLayer(2);
   // map->MapDrawBGT(screen);
  //  printf("Drew layer 1\n");
    //     Sprite test = Sprite("tiles/chars/1.bmp",1,0);
    //    test.draw(screen,player.x*16, player.y*16);
    int i;
    for(i = 0; i < MAX_CLIENTS; i++) {
            if (otherplayers[i].actualImage != 0) {
					player.downstand.animate();
                    player.downstand.draw(screen,
                            player.x - (player.matX - otherplayers[i].matX) * 16 - xoff,
                            player.y - (player.matY - otherplayers[i].matY) * 16 - yoff-16);
                    if(i == 0 ) {
                    	//printf("OTHER CLIENT %d X=%d, y=%d\n",i, otherplayers[i].matX, otherplayers[i].matY);
                    	//printf("OTHER CLIENT %d X=%d, y=%d\n",i, otherplayers[i].matX, otherplayers[i].matY);
                    }
            }
    }
    //  player.currentImage.draw(screen,player.matX*16, player.matY*16);
    sprintf(text, "%d", SDLtext.getFontSize());
    //  drawText(fntCourier, text , 0, 0, 10,255,10);
   // SDLtext.drawText(screen,"Hello", 0,0,255,255,255);


    map->MapChangeLayer(1);
    map->MapDrawBGT(screen);

    mapmenu.draw(screen,520,360);
    if (chatbox == 1) {
        drawChatbox();
    }

    SDL_Flip(screen); // Make sure everything is displayed on screen
    SDL_Delay(1); // Don't run too fast

}

void drawChatbox() {
    SDL_Rect fillable;

    fillable.x = 12;
    fillable.y = 444;
    fillable.w = 456;
    fillable.h = 24;
    SDL_FillRect(screen, &fillable, SDL_MapRGB(screen->format, 128, 128, 128));
    fillable.x = 16;
    fillable.y = 448;
    fillable.w = 448;
    fillable.h = 16;
    SDL_FillRect(screen, &fillable, SDL_MapRGB(screen->format, 192, 192, 192));

    for (int i = 0; i < MAXFULLCHAT; i++) {
        if (strlen(chatfull[i]) > 0) {
            drawText(fntCourier, chatfull[i], 18, 18 + (15 * i), 10, 10, 10);
        }
    }
    if (outgoingmessage[0] != '\0') {
        drawText(fntCourier, outgoingmessage, 18, 450, 10, 10, 10);
    }
}


int handlemapscreen(TCPsocket tcpsock) {
    BYTE data[2];
    char chatmsg[257];
    int retval = 0, x, y;
    SDL_Event event;

    /* Check for events */
    while (SDL_PollEvent(&event)) {
        switch (event.type) {
            case SDL_KEYDOWN:
            {
                if (chatbox != 1) {
                    if (event.key.keysym.sym == SDLK_RETURN) {
                        startmenu = 1;
                        gamestate = GS_PAUSE;
                    }
                }
                map->MapChangeLayer(2);
                if (event.key.keysym.sym == SDLK_UP) {
                 //   player.currentImage = player.upwalk;
                    if (player.matY - 1 >= 0 && map->MapGetTile(player.matX, player.matY - 1) != TILE_COLLISION) {
                        data[0] = 'A';
                        data[1] = MOVE_UP;
                        printf("Tying to move up!\n");
                        SDLNet_TCP_Send(tcpsock, data, 2);
                        player.face = FACE_UP;

                    }
                } else if (event.key.keysym.sym == SDLK_RIGHT) {
                 //   player.currentImage = player.rightwalk;
                    if (player.matX + 1 < map->GetMapWidth() && map->MapGetTile(player.matX + 1, player.matY)!=TILE_COLLISION) {// faster to check this after the fact its been established whether a key has been pressed

                    	data[0] = 'A';
                        data[1] = MOVE_RIGHT;
                        printf("Tying to move right!\n");
                        SDLNet_TCP_Send(tcpsock, data, 2);
                        player.face = FACE_RIGHT;
                    }
                } else if (event.key.keysym.sym == SDLK_DOWN) {
                 //   player.currentImage = player.downwalk;
                    if (player.matY + 1 < map->GetMapHeight() && map->MapGetTile(player.matX, player.matY + 1) != TILE_COLLISION) {// faster to check this after the fact its been established whether a key has been pressed
                        data[0] = 'A';
                        data[1] = MOVE_DOWN;
                        printf("Tying to move down!\n");
                        SDLNet_TCP_Send(tcpsock, data, 2);
                        player.face = FACE_DOWN;
                    }
                } else if (event.key.keysym.sym == SDLK_LEFT && map->MapGetTile(player.matX - 1, player.matY) != TILE_COLLISION) {
                  //  player.currentImage = player.leftwalk;
                    if (player.matX - 1 >= 0) {// faster to check this after the fact its been established whether a key has been pressed
                        data[0] = 'A';
                        data[1] = MOVE_LEFT;
                        printf("Tying to move left!\n");
                        SDLNet_TCP_Send(tcpsock, data, 2);
                        player.face = FACE_LEFT;
                    }
                } else if (chatbox == 1) {
                    if (event.key.keysym.sym == SDLK_RETURN) {
                        if (strlen(outgoingmessage) > 0) {
                            chatmsg[0] = 'C';
                            chatmsg[1] = strlen(outgoingmessage) - 1;
                            int len = strlen(outgoingmessage);
                            for (x = 0; x < len; x++) {
                                chatmsg[x + 2] = outgoingmessage[x];
                            }
                            SDLNet_TCP_Send(tcpsock, chatmsg, chatmsg[1] + 3);
                            memset(outgoingmessage, '\0', 80);
                            memset(chatmsg, '\0', 257);
                        }
                    } else if (event.key.keysym.sym == SDLK_BACKSPACE) {
                        outgoingmessage[strlen(outgoingmessage) - 1] = '\0';
                    } else if (strlen(outgoingmessage) < 55 && (event.key.keysym.sym > 31) && (event.key.keysym.sym < 127)) {
                        outgoingmessage[strlen(outgoingmessage)] = (char) (event.key.keysym.sym); // && 0x7F);
                    }
                }
                break;
            }
            case SDL_KEYUP:
            {
                if (event.key.keysym.sym == SDLK_ESCAPE) {
                    if (chatbox == 1) {
                        chatbox = 0;
                    } else {
                        data[0] = 'A';
                        data[1] = 0xFF;
                        SDLNet_TCP_Send(tcpsock, data, 2);
                        gamestate = GS_STARTMENU;
                    }
                }
                break;
            }
            case SDL_MOUSEBUTTONUP:
            {
                if (event.button.x < 480) {
                    x = (event.button.x / 32) + player.matX - 7;
                    y = (event.button.y / 32) + player.matY - 7;
                    hilited = 255;
                    for (int i = 0; i < MAX_CLIENTS; i++) {
                        if (otherplayers[i].actualImage != 0) {
                            if (otherplayers[i].matX == x && otherplayers[i].matY == y) {
                                hilited = i;
                            }
                        }
                    }
                    if (hilited != 255) {
                        data[0] = 'Q';
                        data[1] = (unsigned char) hilited;
                        SDLNet_TCP_Send(tcpsock, data, 2);
                    } else {
                        //     updateSelDisp("Nothing selected.");
                    }
                } else if (event.button.x > 520 && event.button.x < 580 && event.button.y > 360 && event.button.y < 420) {
                    if (chatbox == 0) {
                        chatbox = 1;
                    } else {
                        memset(outgoingmessage, '\0', 80);
                        chatbox = 0;
                    }
                } else if (event.button.x > 520 && event.button.x < 580 && event.button.y > 420 && event.button.y < 480) {
                    gamestate = GS_PAUSE;
                }
                break;
            }
            case SDL_QUIT:
            {
                data[0] = 'A';
                data[1] = 0xFF;
                SDLNet_TCP_Send(tcpsock, data, 2);
                retval = 1;
                break;
            }
            default:
                break;
        }
    }
    data[0] = 0;
    data[1] = 0;

    return retval;

}

int do_net_mapupdate(SDLNet_SocketSet set, TCPsocket tcpsock) {
    int otherinfo, tempincoming;
    BYTE incoming[2], flags;
    BYTE dataR[1500], data[2];

    int done = 0, who;

    if (SDLNet_CheckSockets(set, 0)) {
        if (SDLNet_SocketReady(tcpsock)) {
            SDLNet_TCP_Recv(tcpsock, incoming, 2);
            if (incoming[0] == 'I') {

            } else if (incoming[0] == 'R') {
                SDLNet_TCP_Recv(tcpsock, dataR, incoming[1]);
                dataR[incoming[1] + 1] = '\0';
                // updateSelDisp((char *)dataR);
            } else if (incoming[0] == 'C') {
                SDLNet_TCP_Recv(tcpsock, dataR, incoming[1]);
                printf("ADDED TO CHAT\n");
                addToChat((char*) dataR);
            } else if (incoming[0] == 'U') {
                SDLNet_TCP_Recv(tcpsock, dataR, incoming[1]);

                int origArea = player.area; // Test to see if a new map needs to be loaded
                player.area = dataR[0];

                player.matX = player.matX + meMoveX;
                player.matY = player.matY + meMoveY;
                if (origArea != player.area) {
                    loadMap(player.area);
               //     player.currentImage = player.downstand;
                    player.matX = dataR[1];
                    player.matY = dataR[2];
                    meMoveX = 0;
                    meMoveY = 0;

                } else {
                    // smooth scrolling
                    tempincoming = dataR[1];
                    if (player.matX + 1 == tempincoming) {
                        if(meMoveX != 1) {
                            player.currentImage = player.rightwalk;
                        }
                        meMoveX = 1;
                    } else if (player.matX - 1 == tempincoming) {
                        if(meMoveX != -1) {
                            player.currentImage = player.leftwalk;
                        }
                        meMoveX = -1;
                    } else {
                        meMoveX = 0;
                        player.matX = tempincoming;
                    }
                    tempincoming = dataR[2];
                    if (player.matY + 1 == tempincoming) {
                        if(meMoveY != 1) {
                            player.currentImage = player.downwalk;
                        }
                        meMoveY = 1;
                    } else if (player.matY - 1 == tempincoming) {
                        if(meMoveY != -1) {
                            player.currentImage = player.upwalk;
                        }
                        meMoveY = -1;
                    } else {
                        meMoveY = 0;
                        player.matY = tempincoming;
                    }
                }
                for (otherinfo = 3; otherinfo < incoming[1]; otherinfo += 5) {
                    if (dataR[otherinfo + 4] == 0xFF) {
                        //printf("Trying to get other players data!\n");
                        othersInArea++;
                        who = dataR[otherinfo];
                        otherplayers[who].matX = otherplayers[who].matX + otherplayers[who].moveX;
                        otherplayers[who].matY = otherplayers[who].matY + otherplayers[who].moveY;
                        otherplayers[who].moveX = 0;
                        otherplayers[who].moveY = 0;

                        tempincoming = dataR[otherinfo + 1];
                        if (otherplayers[who].matX == tempincoming - 1) {
                            otherplayers[who].moveX = 1;
                        } else if (otherplayers[who].matX == tempincoming + 1) {
                            otherplayers[who].moveX = -1;
                        } else {
                            otherplayers[who].matX = tempincoming;
                        }
                        tempincoming = dataR[otherinfo + 2];
                        if (otherplayers[who].matY == tempincoming - 1) {
                            otherplayers[who].moveY = 1;
                        } else if (otherplayers[who].matY == tempincoming + 1) {
                            otherplayers[who].moveY = -1;
                        } else {
                            otherplayers[who].matY = tempincoming;
                        }
                        otherplayers[who].actualImage = dataR[otherinfo + 3];

                        flags = dataR[otherinfo + 4];
                    }
                    if(who == 0) {
                    	printf("Received Other Player's Data: X=%d, Y%d",otherplayers[who].matX,otherplayers[who].matY);
                    }
                }
                ticksLastUpdate = SDL_GetTicks();
            } else if(incoming[0] == 'P'/* && incoming[1] == 'D' && incoming[2] == 'X'*/) {
                printf("Pokedex Update\n");
            } else {
                printf("Server error!  Disconnecting!");
                data[0] = 'A';
                data[1] = 0xFF;
                SDLNet_TCP_Send(tcpsock, data, 2);
                done = 1;
            }
        } else {
            printf("I think some error occurred");
            done = 1;
        }
    }
    return done;
}

int loadMap(int area) {
    // map loading
    map = new SDLMappy();
    //if (map->LoadMap("maps/Essai_16bits_anims_2layers.FMP", 0, 0, SCREEN_X, SCREEN_Y)==-1) {
    char mapfile[30];
    sprintf(mapfile, "maps/%d.FMP", area);
    if (map->LoadMap(mapfile, 0, 0, SCREEN_X, SCREEN_Y) == -1) {
        printf("Can't load Map file %s.\n", mapfile);
    } else {
        printf("Successfully loaded map: maps/%d.FMP\n", area);
       // map->colorkey = (Uint32)(0xFF00FF);
        map->MapInitAnims();
        map->CreateParallax("parallax.bmp");
    }
    return 0;
}

void drawText(TTF_Font* font, char* txt, int x, int y, int r, int g, int b) {
    SDL_Color clrFG;
    clrFG.r = r;
    clrFG.g = g;
    clrFG.b = b;
    clrFG.unused = 0;
    SDL_Surface* sText = TTF_RenderText_Solid(font, txt, clrFG);
    SDL_Rect rcDest;
    rcDest.x = x;
    rcDest.y = y;
    SDL_BlitSurface(sText, NULL, screen, &rcDest);
    SDL_FreeSurface(sText);
}

void drawText(TTF_Font* font, char* txt, int x, int y, SDL_Color clrFG) {
    SDL_Surface *sText = TTF_RenderText_Solid(font, txt, clrFG);
    SDL_Rect rcDest;
    rcDest.x = x;
    rcDest.y = y;
    SDL_BlitSurface(sText, NULL, screen, &rcDest);
    SDL_FreeSurface(sText);
}
