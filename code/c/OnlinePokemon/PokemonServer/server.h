#ifndef _SERVER_H_
#define _SERVER_H_
#include "../PokemonCommon/common.h"
long timer;
//#define HTMLFILE "C:/Progra~1/Apache~1/Apache/htdocs/status.html"
using namespace std;

class Server {
public:
    Server();

private:
    bool initMySQL();
    bool connectMySQL();
    void drawText(TTF_Font* font, char* txt, int x, int y, int r, int g, int b);
    void draw();
    int handlespecialaction(int,int, int);
    int tryMove(int,int);
    void writeHTMLStatus(char*, int);
    void UpdateAllPlayers();
    void UpdateAllBattles();
    void disconnect_client(int);
    void handleServer();
    void handleClient(int);
    int loadPokedex();
    //int loadPokemon(int which); // users pokemon
    int loadItems();
    void loadMaps();
    int loadMap(int area);
    int loadAbilities();
    int loadLearning();
    void log(string msg, int level);
    void cleanup();


    TTF_Font* fntCourier;
    TCPsocket servsock;
    IPaddress serverIP;
    SDLNet_SocketSet socketset;

    struct {
        TCPsocket sock;
        IPaddress peer;
        int gamestate;
    } client[MAX_CLIENTS];

    Pokemon pokedex[MAX_POKEMON];


    struct battlez {
        BYTE inprogress;
        int monid;
        int moncharge;
        BYTE monspeed;
        int monhp;
        int monmp;
        int mondef;
        BYTE state;   // 0 - nobody doing anything 1-monster attacks 2-player attacks
        BYTE anim; // animation keyframe - this syncs everything
        BYTE atype; // animation type
    } battle [MAX_CLIENTS];


    //This holds character information
    ServerPlayerz player[MAX_CLIENTS];

    SDL_Surface *screen;
    Sprite image;


    int logging;
    int numPlayers;
    SDLMappy* maps[MAX_MAPS];
    long numMonsters;
   // Text txt;
    char text[128];
    char HTMLFILE[128];

    string dbhost;    /* server host (default=localhost) */
    string dbuser;    /* username (default=login name) */
    string dbpass;     /* password (default=none) */
    unsigned int dbportnum; /* port number (use built-in value) */
    string dbsocketname;  /* socket name (use built-in value) */
    string dbname;      /* database name (default=none) */
    unsigned int dbflags;    /* connection flags (none) */
    MYSQL *con;                   /* pointer to connection handler */
    bool MySQLConnected;
    MYSQL_RES *res;
    MYSQL_ROW row;
};
#endif /* End of _SERVER_H_ */
