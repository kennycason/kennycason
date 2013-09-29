#include "client.h"
#include "menu.h"


int main (int argc, char *argv[]) {

    if(argc > 1) {
        int i;
        for(i = 0; i < argc - 1; i++) {
            printf("%s",argv[i]);
        }
    }
    char ack;
    char username[32],password[32],initbuf[7]; // couple of buffers
	int read;//,i; // other info
    int done=0;

   	IPaddress serverIP;    // networking information
	TCPsocket tcpsock=NULL;
	SDLNet_SocketSet set=NULL;
//    FILE *fp;

    int audio_rate = 44100;              // audio stuff
    Uint16 audio_format = AUDIO_S16;
    int audio_channels = 2;
    int audio_buffers = 4096;

    gamestate = GS_STARTMENU;

	//We must first initialize the SDL video component, and check for success
	if (SDL_Init(SDL_INIT_AUDIO | SDL_INIT_VIDEO | SDL_INIT_TIMER) != 0) {
		printf("Unable to initialize SDL VIDEO: %s\n", SDL_GetError());
		return ERROR_SDL_INIT;
	}
	atexit(SDL_Quit);  //When this program exits, SDL_Quit must be called

    // BEGIN SETUP OF NETWORK CONNECTIONS
    if (SDLNet_Init() ==-1) {
		printf("Unable to initialize SDL_Net: %s\n", SDLNet_GetError());
		return ERROR_SDL_NET;
	}
    // this is where the old login info was.

    screen = SDL_SetVideoMode(SCREEN_X, SCREEN_Y, 16, SDL_HWSURFACE | SDL_DOUBLEBUF );//| SDL_FULLSCREEN);
	if (screen == NULL) {
		printf("Unable to set video mode: %s\n", SDL_GetError());
		return ERROR_SDL_SETVIDEOMODE;
	}

    if(Mix_OpenAudio(audio_rate, audio_format, audio_channels, audio_buffers)) {
      printf("Unable to open audio!\n");
      exit(ERROR_SDL_AUDIO);
    }
    Mix_QuerySpec(&audio_rate, &audio_format, &audio_channels);

    if (TTF_Init()==-1) {
        printf("TTF_Init: %s\n", TTF_GetError());
        exit(ERROR_SDL_TTF);
    }


    SDL_EnableKeyRepeat(100, 100);

    while (!done) {
        if(gamestate == GS_STARTSCREEN) {
              inittitle();
               while (!done && gamestate == GS_STARTSCREEN) {
                    timer = SDL_GetTicks();
                    done=handletitle();
                    drawtitle();
               }
               destroytitle();
        } else if (gamestate == GS_STARTMENU) {
            initmenu();
            while (!done && gamestate == GS_STARTMENU) {
                timer = SDL_GetTicks();
                done = handlemenu();
                drawmenu();
            }
            destroymenu();
        } else if (gamestate == GS_CREDITS){

            initcredits();
            while (!done && gamestate == GS_CREDITS) {
                timer = SDL_GetTicks();
                done=handlecredits();
                drawcredits();
            }
            destroycredits();
        } else if (gamestate == GS_LOGIN) {
                #if defined(WIN32)
                   //	read=InputBox(NULL,"Enter the name or IP address of the server","mine,130.184.85.192",username,31);
                 //  strcpy(username,"draconis.game-server.cc");
                   strcpy(username,"localhost");
                   //strcpy(username,"130.184.85.192");
                   read=InputBox(NULL, "Enter your screen name.","MMO", player.email, 31);
                   read=InputBox(NULL, "Enter your password.","MMO", password, 31);
                #else
                   printf("Setting up Login Details\n");
	              // strncpy(username,argv[1],31);
	              // strncpy(player.name,argv[2],31);
	             //  strncpy(password,argv[3],31);
                  //  char username[32];
                    sprintf(username,"localhost");
                    sprintf(player.Email,"kenny@uark.edu");
                  //  sprintf(player.Email,"kcason@uark.edu");
                  //  sprintf(player.email,"kenneth.cason@gmail.com");
                  //  char password[32];
                    sprintf(password,"password");
                #endif

                if(username==NULL || SDLNet_ResolveHost(&serverIP,username,5009) == -1) {
                   printf("SDLNet_ResolveHost: %s\n", SDLNet_GetError());
                   exit(ERROR_SDL_NET);
                }
                tcpsock=SDLNet_TCP_Open(&serverIP);
                if(!tcpsock) {
                   printf("SDLNet_TCP_Open: %s\n", SDLNet_GetError());
                   exit(ERROR_SDL_NET);
                }
                set = SDLNet_AllocSocketSet(1);
                if (!set) {
                    printf("SDLNet_AllocSocketSet: %s\n", SDLNet_GetError());
                    exit(ERROR_SDL_NET); //most of the time this is a major error, but do what you want.
                }
                if (SDLNet_TCP_AddSocket(set,tcpsock) == -1) {
                    printf("SDLNet_TCP_AddSocket: %s\n", SDLNet_GetError());
                    exit(ERROR_SDL_NET); //most of the time this is a major error, but do what you want.
                }
                SDLNet_TCP_Recv(tcpsock, &ack, 1);
                if (ack != 'K') {
                   done = 1;
                } else {
                    printf("Logging in. Sending Email address. Waiting for Server's Response\n");
                    read = SDLNet_TCP_Send(tcpsock, player.Email, 32);
                    printf("Logging in. Sending Password. Waiting for Server's Response\n");
                    read = SDLNet_TCP_Send(tcpsock, password, 32);
                    printf("Waiting for Login data\n");
                    SDLNet_TCP_Recv(tcpsock, &initbuf, 50);
                    printf("Received Login data\n");
                    if (initbuf[0] == 'F')   {         // invalid character
                        printf("Failed Login!\n");
                        gamestate = GS_STARTMENU;
                      //done=1;
                    } else if (initbuf[0] == 'I')  {     // initial data: load map and tiles
                       printf("Receiving Initial Data\n");
               	     // player.area =initbuf[1];
                      player.area =initbuf[1];
                	  player.matX =initbuf[2];
                	  player.matY =initbuf[3];
               // 	  player.startImage =
                      //player.actualImage=player.startImage;

                	//  for(int i = 0; i < 24; i++) {}
                	 //   player.abilities[i]=initbuf[26+i];

                    //  load_Map(player.area);
                    //  load_Tiles(tileset);
                      gamestate = GS_GAME;
                          printf("Finished Logging in!\n");
                   }else
                      done=1;
                }
                printf("Attempted connect, received %c from server.\n",ack);
        }else if (gamestate == GS_PAUSE){
            initstartscreen();
            printf("Succeeded at initstartscreen.\n");
            while (!done && gamestate == GS_PAUSE) {
                timer = SDL_GetTicks();
                done=handlestartscreen();
                drawstartscreen(player);
                do_net_mapupdate(set,tcpsock); // get info
            }
            destroystartscreen();
            printf("Succeeded at destroystartscreen.\n");
        }else if (gamestate == GS_GAME){ //MAIN
            initmapscreen();
            printf("Succeeded at initmapscreen.\n");
            while (!done && gamestate == GS_GAME) {
                //printf("LOOPING 0\n");
                timer = SDL_GetTicks();
                done=handlemapscreen(tcpsock);
               // printf("LOOPING 1\n");
                drawmapscreen();
               // printf("LOOPING 2\n");
                do_net_mapupdate(set,tcpsock); // get info
              //  printf("LOOPING 3\n");
            }
            destroymapscreen();
            printf("Succeeded at destroymapscreen.\n");
        } else if (gamestate == GS_BATTLE){
    //        initbattlescreen();
            printf("Succeeded at initbattlescreen.\n");
            while (!done && gamestate == GS_BATTLE) {
   //          done=handlebattlescreen(tcpsock);
     //        drawbattlescreen();
    //       do_net_battleupdate(set,tcpsock); // get info
            }
//          destroybattlescreen();
            printf("Succeeded at destroybattlescreen.\n");
        } else {
            done=1;           /* should not reach this point. */
        }
    }

    Mix_HaltMusic();
    Mix_FreeMusic(music);
    Mix_CloseAudio();

    return 0;
}
