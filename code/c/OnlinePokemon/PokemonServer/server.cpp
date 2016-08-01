#include "server.h"

using namespace std;

Server::Server() {
    bool done = false;
    Uint32 endtime;
    FILE *inifile;
    logging = LOG_DEBUG;
    log("*** MMO Server v0.1 ***\nwww.kennycason.com\n",LOG_DEBUG);
    inifile=fopen("server.ini","r");
    if (inifile==NULL) {
        log("Can't open server.ini - using local directory for HTML status.\nPlease create a server.ini containing the location to write status to, e.g.\nC:/Progra~1/Apache~1/Apache/htdocs/status.html\n",LOG_DEBUG);
        strcpy(HTMLFILE,"status.html");
    } else {
        if(fscanf(inifile,"%s",HTMLFILE)) {
            fclose(inifile);
        }
    }
    writeHTMLStatus(HTMLFILE, 0);

    /* Initialize SDL */
    if (SDL_Init(SDL_INIT_VIDEO) < 0 ) {
        sprintf(text,"Couldn't create socket set: %s\n",SDLNet_GetError());
        log(text,LOG_ERROR);
        exit(ERROR_SDL_VIDEO);
    }
    if (SDL_Init(SDL_INIT_TIMER) < 0 ) {
        sprintf(text,"Couldn't initialize SDL Timer: %s\n",SDL_GetError());
        log(text,LOG_ERROR);
        exit(ERROR_SDL_TIMER);
    }
    atexit(SDL_Quit);

    screen = SDL_SetVideoMode (200, 300, 16, SDL_SWSURFACE | SDL_DOUBLEBUF);
    if(screen == NULL) {
        sprintf(text,"Couldn't set 200x300x16 video mode: %s\n",SDL_GetError());
        log (text,LOG_ERROR);
        exit (ERROR_SDL_SETVIDEOMODE);
    }
    SDL_WM_SetCaption ("MMO SERVER", NULL);

    image = Sprite("server.bmp",1,0);

  /*  text = Text("fonts/coure.fon",10);
    if(!text.isFont()) {
        log("Font could not be loaded!\n",LOG_ERROR);
    } else {
        log("Successfullly loaded font!\n",LOG_INFO);
    }*/
    /* Initialize the network */
    if(SDLNet_Init() < 0) {
        sprintf(text,"Couldn't initialize net: %s\n", SDLNet_GetError());
        log(text,LOG_ERROR);
	exit(ERROR_SDL_NET);
    }
    /* Initialize the channels */
    for(int i = 0; i < MAX_CLIENTS; i++) {
    	client[i].sock = NULL;
    	battle[i].inprogress = 0;
    }
    /* Allocate the socket set */
    socketset = SDLNet_AllocSocketSet(MAX_CLIENTS+1);
    if(socketset == NULL) {
        sprintf("Couldn't create socket set: %s\n", SDLNet_GetError());
	log(text,LOG_ERROR);
        exit(4);
    }
    /* Create the server socket */
    SDLNet_ResolveHost(&serverIP, NULL, PORT);
    sprintf(text,"Server IP: %x, %d\n", serverIP.host, serverIP.port);
    log(text,LOG_DEBUG);
    servsock = SDLNet_TCP_Open(&serverIP);
    if(servsock == NULL) {
	sprintf(text,"Couldn't create server socket: %s\n", SDLNet_GetError());
        log(text,LOG_ERROR);
        cleanup();
	exit(ERROR_SDL_NET);
    }
    SDLNet_TCP_AddSocket(socketset, servsock);

    log("Initing MySQL\n",LOG_DEBUG);
    initMySQL();
    loadPokedex();
    loadMaps();

    /* Loop, waiting for network events */
    endtime = SDL_GetTicks()+250;

    writeHTMLStatus(HTMLFILE, 1);
    while(!done) {
        while(endtime > SDL_GetTicks()) {
            SDL_Event event;
            /* Check for events */
            while (SDL_PollEvent (&event)) {
                switch (event.type) {
                    case SDL_KEYDOWN:

                        break;
                    case SDL_QUIT:
                        done = 1;
                        break;
                    default:
                        break;
                }
            }
            /* Draw to screen */
            draw();
            /* Wait for events */
            SDLNet_CheckSockets(socketset, 0);
            /* Check for new connections */
            if(SDLNet_SocketReady(servsock)) {
                handleServer();
            }

            /* Check for events on existing clients */
            for (int i = 0; i < MAX_CLIENTS; i++) {
                if ( SDLNet_SocketReady(client[i].sock) ) {
                    handleClient(i);
                }
            }
            SDL_Delay(0);
        }
        endtime=SDL_GetTicks()+250;
        UpdateAllPlayers();
        //UPDATEALLENEMIES();
	}
    writeHTMLStatus(HTMLFILE, 0);
    image.destroy();
    exit(0);
}


bool Server::initMySQL() {
        //dbhost = "97.74.31.26";
        dbhost = "pokemon3.db.3027741.hostedresource.com";
        dbuser = "pokemon3";
        dbpass = "KkBDjSh2432";
        dbname = "pokemon3";
        dbportnum = 0;
        dbsocketname = "";
        dbflags = 0;
        // initialize connection handler
        con = mysql_init(NULL);
        if (con == NULL) {
            log("mysql_init() failed (probably out of memory)\n",LOG_ERROR);
            return false;
        }
}

bool Server::connectMySQL() {
        // connect to server
        if (mysql_real_connect(con, dbhost.c_str(), dbuser.c_str(), dbpass.c_str(), dbname.c_str(),
            dbportnum, dbsocketname.c_str(), dbflags) == NULL) {
            log("mysql_real_connect() failed\n",LOG_ERROR);
            mysql_close (con);
            return false;
        } else {
            mysql_select_db(con,dbname.c_str());
            log("Successfully connected to MySQL Database!\n",LOG_DEBUG);
        }
    return true;
}

int Server::loadPokedex() {
    log("Loading Pokedex\n",LOG_DEBUG);
    if(connectMySQL() == false) {
        log("Failed to Connect to MySQL Database\n",LOG_ERROR);
        exit(ERROR_MYSQL_CONNECT);
    }
    char sql[128] = {0};
    sprintf(sql, "SELECT * FROM Pokedex");
    mysql_query(con, sql);
    log("Finished query\n",LOG_DEBUG);
    res = mysql_store_result(con);
    if (res != NULL) {
        log("Found pokemon!\n",LOG_DEBUG);
        row = mysql_fetch_row(res);
        pokedex[atoi(row[0])].PokedexNumber = atoi(row[0]);
        sprintf(text, "PokedexNumber = %d\n", atoi(row[0]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].PokedexImage = atoi(row[1]);
        sprintf(text, "PokedexImage = %d\n", atoi(row[1]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BattleImageOpponent = atoi(row[2]);
        sprintf(text, "BattleImageOpponent = %d\n", atoi(row[2]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BattleImageSelf = atoi(row[3]);
        sprintf(text, "BattleImageSelf = %d\n", atoi(row[3]));
        log(text,LOG_DEBUG);
        sprintf(text, "Name = %s\n", row[4]);
        pokedex[atoi(row[0])].Name.assign(row[5]);
        log(text,LOG_DEBUG);
        sprintf(text, "JapanName = %s\n", row[5]);
        pokedex[atoi(row[0])].JapanName.assign(row[5]);
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].Height = atoi(row[6]);
        sprintf(text, "Height = %d\n", atoi(row[6]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].Weight = atoi(row[7]);
        sprintf(text, "Weight = %d\n", atoi(row[7]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].Type1 = atoi(row[8]);
        sprintf(text, "Type1 = %d\n", atoi(row[8]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].Type2 = atoi(row[9]);
        sprintf(text, "Type2 = %d\n", atoi(row[9]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseHP = atoi(row[10]);
        sprintf(text, "BaseHP = %d\n", atoi(row[10]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseAttack = atoi(row[11]);
        sprintf(text, "BaseAttack = %d\n", atoi(row[11]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseDefense = atoi(row[12]);
        sprintf(text, "BaseDefense = %d\n", atoi(row[12]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseSpecialAttack = atoi(row[13]);
        sprintf(text, "BaseSpecialAttack = %d\n", atoi(row[13]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseSpecialDefense = atoi(row[14]);
        sprintf(text, "BaseSpecialDefense = %d\n", atoi(row[14]));
        log(text,LOG_DEBUG);
        pokedex[atoi(row[0])].BaseSpeed = atoi(row[15]);
        sprintf(text, "BaseSpeed = %d\n", atoi(row[15]));
        log(text,LOG_DEBUG);
        log("Loaded Pokemon\n", LOG_DEBUG);
    }
    mysql_free_result(res);
    return 0;
}

int Server::loadItems() {

}

int Server::loadAbilities() {

}

int Server::loadLearning() {

}

void Server::loadMaps() {
    // init all maps
    for(int i = 0; i < MAX_MAPS; i++) {
        loadMap(i);
    }
}

int Server::loadMap(int area) {
    maps[area] = new SDLMappy();
    //if (map->LoadMap("maps/Essai_16bits_anims_2layers.FMP", 0, 0, SCREEN_X, SCREEN_Y)==-1) {
    char mapfile[30];
    sprintf(mapfile, "maps/%d.FMP", area);
    if (maps[area]->LoadMap(mapfile, 0, 0, SCREEN_X, SCREEN_Y) == -1) {
        printf("Can't load Map file %s.\n", mapfile);
    } else {
        printf("Successfully loaded map: maps/%d.FMP\n", area);
     //   maps[0]->MapInitAnims();
     //   maps[0]->CreateParallax("parallax.bmp");
    }
    return 0;
}

void Server::UpdateAllPlayers() {
     int success,i,j;
     unsigned char output[1500]={'\0'};

     //process client actions
     for(i = 0; i < MAX_CLIENTS; i++){
		if(client[i].sock != NULL && player[i].action != 0) {
             if (client[i].gamestate == GS_GAME) {
                switch(player[i].action) {
                    case MOVE_UP: {
                        sprintf(text,"Client attempted a move %d.\n",player[i].action);
                        log(text,LOG_DEBUG);
                        success=tryMove(player[i].action,i);
                        break;
                    }
                    case MOVE_DOWN: {
                        sprintf(text,"Client attempted a move %d.\n",player[i].action);
                        log(text,LOG_DEBUG);
                        success=tryMove(player[i].action,i);
                        break;
                    }
                    case MOVE_LEFT: {
                        sprintf(text,"Client attempted a move %d.\n",player[i].action);
                        log(text,LOG_DEBUG);
                        success=tryMove(player[i].action,i);
                        break;
                    }
                    case MOVE_RIGHT: {
                        sprintf(text,"Client attempted a move %d.\n",player[i].action);
                        log(text,LOG_DEBUG);
                        success=tryMove(player[i].action,i);
                        break;
                    }
                    default: {
                        sprintf(text,"Unknown action from %d! Action %d ", i, player[i].action);
                        log(text,LOG_ERROR);
                        disconnect_client(i);
                        break;
                    }
                }
                player[i].action=0;
            } else if (client[i].gamestate == GS_BATTLE) {
                if (player[i].action>25) {
                  //  printf("Unknown action from %d!  ",i);
                    disconnect_client(i);
                }else{
                //    printf("Player is attacking with %d\n",player[i].action);
                    battle[i].state = 2;
                    battle[i].atype = player[i].action;
                    player[i].action = 0;
                //    player[i].charge = 0;
                    // add battle equations later
                    int dam = 10 - battle[i].mondef;
                    if(dam < 1) {
                        dam = 1;
                    }
                    battle[i].monhp -= dam;
                }
            }
        }
    }

     // update players with new locations
     for(i = 0; i < MAX_CLIENTS; i++) {
	if ( client[i].sock != NULL && client[i].gamestate == GS_GAME) {
            output[0]='U';
            output[1]=3;
            output[2]=player[i].area;
            output[3]=player[i].matX;
            output[4]=player[i].matY;
            for ( j = 0; j < MAX_CLIENTS; j++ ) {
             	if ( i!=j && client[j].gamestate == GS_GAME && client[j].sock != NULL && player[j].area == player[i].area) {
                   // printf("Other Characters in Area\n");
                    output[output[1]+2]=j;
                    output[output[1]+3]=player[j].matX;
                    output[output[1]+4]=player[j].matY;
                    output[output[1]+5]=player[j].image;
                    output[output[1]+6]=0xFF;
                    output[1] = output[1] + 5;
                }
            }

            success = SDLNet_TCP_Send(client[i].sock, output, output[1]+2);
            // ensure that all BYTES of data were sent
            if (success == output[1]+2) {
                sprintf(text,"I told the %d client %c",i,output[0]);
                log(text,LOG_DEBUG);
                for(int q = 1; q < output[1] + 2; q++) {
                    sprintf(text,"%d ",output[q]);
                    log(text,LOG_DEBUG);
                }
                log("\n",LOG_DEBUG);
            } else {
                sprintf(text,"Send failed - client %d must have dropped.\n",i);
                log(text,LOG_ERROR);
                disconnect_client(i);
            }
        }
    }
}


void Server::writeHTMLStatus(char* filename, int up) {
     int pcount=0,i;
     FILE * htmlfile;

     htmlfile=fopen(filename,"w");
     if(htmlfile!=NULL) {
         fprintf(htmlfile,"<html><head><title>Pokemon Online Server Status</title></head>\n");
         fprintf(htmlfile,"<body>Server: ");
         if(!up) {
             fprintf(htmlfile,"<font color=\"#FF0000\"><b>DOWN</b></font>\n");
         } else {
                fprintf(htmlfile,"<font color=\"#00FF00\"><b>UP</b></font>.\n");
             //   fprintf(htmlfile,"<br>Server IP: <b>%d</b>\n",serverIP);
                for ( i=0; i<MAX_CLIENTS; i++ ) {
                    if ( client[i].sock != NULL ) {
                        pcount++;
                    }
                }
                fprintf(htmlfile,"<br>Players connected: <b>%d</b>\n",pcount);
         }
         fprintf(htmlfile,"</body></html>\n");
         fclose(htmlfile);
     }
}


void Server::handleServer() {
	TCPsocket newsock;
	int which;
	char ack;
	newsock = SDLNet_TCP_Accept(servsock);
	if ( newsock == NULL ) {
            log("problem accepting new socket?\n",LOG_ERROR);
            return;
	}

	/* Look for unconnected person slot */
	for (which = 0; which < MAX_CLIENTS; which++) {
		if (!client[which].sock) {
			break;
		}
	}

	if(which == MAX_CLIENTS) {
		/* No more room... */
		ack='F';
		SDLNet_TCP_Send(newsock, &ack, 1);
		SDLNet_TCP_Close(newsock);
  	    log("Connection refused -- server full\n",LOG_ERROR);
	} else {
		ack='K';
		/* Add socket as an inactive person */
		client[which].sock = newsock;
		client[which].peer = *SDLNet_TCP_GetPeerAddress(newsock);
		//client[which].state=STATE_LOGIN_USERNAME;
		SDLNet_TCP_AddSocket(socketset, client[which].sock);
		SDLNet_TCP_Send(newsock, &ack, 1);
	    sprintf(text,"New socket %d\n", which);
            log(text,LOG_DEBUG);
	    client[which].gamestate = GS_LOGIN;
	    numPlayers++;
     }
    writeHTMLStatus(HTMLFILE, 1);
}


void Server::handleClient(int which) {
    int i;
    //unsigned char ack;
    unsigned char data[2] = {0};
    char message[257] = {0};
    char relay[80] = {0};
    FILE *fp;
    /* Has the connection been closed? */
    if (client[which].gamestate == GS_GAME) {
        SDLNet_TCP_Recv(client[which].sock, data, 2);
        switch (data[0]) { // C = command, two bytes- C+number
            case 0:
                break;
            case 'A':
            {
                //fprintf(stdout, "an action command %c-%c-\n", data[0], data[1]);
                sprintf(text, "Client told me %c %d\n", data[0], data[1]);
                log(text, LOG_DEBUG);
                if (data[1] == 0xFF) {
                    sprintf(text, "Got disconnect message from %d!  \n", which);
                    log(text, LOG_DEBUG);
                    disconnect_client(which);
                } else {
                    player[which].action = data[1];
                }
                break;
            }
            case 'C':
            {
                SDLNet_TCP_Recv(client[which].sock, message, data[1] + 1);
                memset(relay, '\0', 80);
                relay[0] = 'C';
                relay[1] = 1;
                strcat(relay, player[which].FirstName);
                relay[strlen(relay)] = '>';
                strncat(relay, message, 57 - strlen(relay));
                relay[1] = strlen(relay) - 1;
                sprintf(text, "{%s}\n", relay);
                log(text, LOG_DEBUG);
                for (i = 0; i < MAX_CLIENTS; i++) {
                    if (client[i].sock != NULL && client[i].gamestate == GS_GAME) {
                        SDLNet_TCP_Send(client[i].sock, relay, strlen(relay) + 1);
                    }
                }
                break;
            }
            case 'Q':
            {

            	sprintf(message, "R%s", player[data[1]].FirstName);
            	SDLNet_TCP_Send(client[which].sock, message, strlen(message));

                message[1] = '-';
                sprintf(text, "The %d client asked what %d was: I told them %s\n", which, data[1], message);
                log(text, LOG_DEBUG);
                break;
            }
            default:
            {
                sprintf(text, "Got a bad message from %d!  ", which);
                log(text, LOG_DEBUG);
                disconnect_client(which);
                break;
            }
        }
    } else if (client[which].gamestate == GS_BATTLE) {
        SDLNet_TCP_Recv(client[which].sock, data, 2);
        switch (data[0]) { // C = command, two bytes- C+number
            case 0:
                break;
            case 'A':
            {
                sprintf(text, "Client told me %d %d\n", data[0], data[1]);
                log(text, LOG_DEBUG);
                if (data[1] == 0xFF) {
                    sprintf(text, "Got disconnect message from %d!  \n", which);
                    log(text, LOG_DEBUG);
                    disconnect_client(which);
                } else {
                    player[which].action = data[1];
                }
                break;
            }
            case 'C':
            {
                SDLNet_TCP_Recv(client[which].sock, message, data[1] + 1);
                log("a Chat command\n", LOG_DEBUG);
                break;
            }
            default:
            {
                sprintf(text, "Got a bad message from %d!  ", which);
                log(text, LOG_DEBUG);
                disconnect_client(which);
                break;
            }
        }
    } else if (client[which].gamestate == GS_LOGIN) {
        SDLNet_TCP_Recv(client[which].sock, player[which].Email, 32);
        client[which].gamestate = GS_VERIFYLOGIN;
        sprintf(text, "*****player %d identified self as %s\n", which, player[which].Email);
        log(text, LOG_DEBUG);
    } else if (client[which].gamestate == GS_VERIFYLOGIN) {
        //		ack='K';
        log("Verifying Login\n", LOG_DEBUG);
        SDLNet_TCP_Recv(client[which].sock, message, 32);
        bool success = false;
        if (connectMySQL() == false) {
            log("Failed to Connect to MySQL Database\n", LOG_ERROR);
            exit(ERROR_MYSQL_CONNECT);
        }
        char sql[128] = {0};
        sprintf(sql, "SELECT * FROM Account WHERE Email = '%s' AND Password = '%s'", player[which].Email, message);
        mysql_query(con, sql);
        // printf("Finished query\n");
        res = mysql_store_result(con);
        if (res != NULL) {
            log("Log in Success!\n", LOG_DEBUG);
            success = true;
            //res = NULL; row = NULL;
            row = mysql_fetch_row(res);
            // printf("FirstName = %s", row[0]);
            strcpy(player[which].FirstName, row[0]);
            // printf("LastName = %s", row[1]);
            strcpy(player[which].LastName, row[1]);
            // row[2]  contains the email
            player[which].BirthYear = atoi(row[4]);
            player[which].BirthMonth = atoi(row[5]);
            player[which].BirthDay = atoi(row[6]);
            //    player[which].Gender = row[7];
            //    player[which].LoggedIn = atoi(row[6]);
            player[which].ID = atoi(row[9]);
            //   printf("Players ID = %d\n", player[which].ID);
            mysql_free_result(res);
            // The Account Exists so lets now get the Player Data from the 'Players' DB
            sprintf(sql, "SELECT * FROM Players WHERE ID = '%d'\n", player[which].ID);
            sprintf(text, "About to query \n%s\n", sql);
            log(text, LOG_DEBUG);
            mysql_query(con, sql);
            // printf("Finished query\n");
            res = mysql_store_result(con);
            // printf("Stored results of the query");
            row = mysql_fetch_row(res);
            player[which].area = atoi(row[0]);
            player[which].matX = atoi(row[1]);
            player[which].matY = atoi(row[2]);
            player[which].image = atoi(row[3]);
            mysql_free_result(res);
        }
        log("finished processing log in info!\n", LOG_DEBUG);
        if(success) {
            log("preparing to send initial client data.\n", LOG_DEBUG);
            client[which].gamestate = GS_GAME;
            message[0] = 'I';
            message[1] = player[which].area;
            message[2] = player[which].matX;
            message[3] = player[which].matY;

            SDLNet_TCP_Send(client[which].sock, message, 4);
/*
            message[0] = 'P';// message[1] = 'D'; message[2] = 'X';int PokedexNumber,
            for(int i = 0; i < 6; i++) {
             //   message[i*2 + 1] = pokede[i].
            }
            */
           //  SDLNet_TCP_Send(client[which].sock, message, 151);
            for (i = 0; i < MAX_CLIENTS; i++) {
                if (client[i].sock != NULL && client[i].gamestate == GS_GAME && i != which) {
                    sprintf(message, "C %s connected.", player[which].Email);
                    message[1] = strlen(message) - 1;
                    message[strlen(message)] = 0;
                    SDLNet_TCP_Send(client[i].sock, message, strlen(message) + 1);
                }
            }

        } else { // supplied bad password!
            message[0] = 'F';
            SDLNet_TCP_Send(client[which].sock, message, 1);
            disconnect_client(which);
            log("Client supplied bad password!\n", LOG_DEBUG);
        }
    } else { // unknown username;
        message[0] = 'F';
        SDLNet_TCP_Send(client[which].sock, message, 1);
        disconnect_client(which);
        log("Unknown username!\n", LOG_DEBUG);
    }
}

void Server::disconnect_client(int which) {
    // SAVE CLIENT
     sprintf(text,"Closing socket %d\n", which);
     log(text,LOG_DEBUG);
     /* probably Notify all active clients at this point */
     SDLNet_TCP_DelSocket(socketset, client[which].sock);
     SDLNet_TCP_Close(client[which].sock);
     client[which].sock = NULL;
     writeHTMLStatus(HTMLFILE, 1);
     // tell all other players about disconnect?
     int i;
     char message[64];
     for(i=0; i<MAX_CLIENTS; i++ ) {
	if(client[i].sock != NULL && client[i].gamestate == GS_GAME && i!=which) {
             sprintf(message,"C %s dropped.",player[which].FirstName);
             message[1]=strlen(message)-1;
             message[strlen(message)]=0;
             SDLNet_TCP_Send(client[i].sock, message, strlen(message)+1);
        }
     }
}

void Server::cleanup() {
    if( servsock != NULL ) {
	SDLNet_TCP_Close(servsock);
	servsock = NULL;
    }
    if( socketset != NULL ) {
        SDLNet_FreeSocketSet(socketset);
	socketset = NULL;
    }
    writeHTMLStatus(HTMLFILE, 0);
    SDLNet_Quit();
}

int Server::tryMove(int dir, int which) {
    BYTE x = player[which].matX;
    BYTE y = player[which].matY;
    maps[player[which].area]->MapChangeLayer(2);
    if( dir == MOVE_UP && y>0) {//trying to move up
         if(maps[player[which].area]->MapGetTile(x,y-1) != TILE_COLLISION) {
            y--;
         }
     } else if( dir == MOVE_RIGHT && x<255) {//trying to move right
         if(maps[player[which].area]->MapGetTile(x+1,y) != TILE_COLLISION) {
            x++;
         }
     } else if( dir == MOVE_DOWN && y<255) { //trying to move down
         if(maps[player[which].area]->MapGetTile(x,y+1) != TILE_COLLISION) {
            y++;
         }
     } else if( dir == MOVE_LEFT && x>0) {//trying to move left
         if(maps[player[which].area]->MapGetTile(x-1,y) != TILE_COLLISION) {
            x--;
         }
     }
     player[which].matX = x;
     player[which].matY = y;
     //   handlespecialaction(player[which].area, which, mapData[player[which].area][x][y] & 0x1F ); // handle action at specified line
         return 0;
}

void Server::draw() {
   // printf("Drawing\n");
    SDL_Rect rcDest;
    rcDest.x = 0;
    rcDest.y = 0;
    SDL_BlitSurface(image.getSurface(),NULL,screen,&rcDest);
   //     printf("Debug -1\n");
  //  text.setColor(255,255,255);
   //     printf("Debug 0\n");
  //  text.drawText(screen,"TEST!",0,0,255,255,255);
    SDL_Flip (screen);
    SDL_Delay (1);
}

void Server::log(string msg, int level) {
 //   printf("LOG: %s",msg.c_str());
  //  printf("LOG LEVEL: %d <=? LOGGING %d",level,logging);
    if(level <= logging) {
        printf(msg.c_str());
    }
}

int main(int argc, char *argv[]) {
    if(argc > 1) {
        for(int i = 0; i < argc - 1; i++) {
            printf("%s",argv[i]);
        }
    }
    Server s = Server();
}
