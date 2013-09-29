/* menu.c - menu, title and credits screens */

#include "menu.h"

Sprite menu;

extern SDL_Surface *screen;
extern TTF_Font *systemfont;
extern SDL_Color textcolor;
extern int gamestate;
int menuoverlayD, startmenumode = 1;

extern Mix_Music *music;   /* SDL_Mixer stuff here */


void inittitle() {
     menu = Sprite("tiles/ui/title.bmp",1,0);
     menuoverlayD = SDL_GetTicks();
     /* This is the music to play. */
	 music = Mix_LoadMUS("sound/1.mid");
     Mix_PlayMusic(music, -1);
}

void destroytitle() {
    menu.destroy();
}

void drawtitle() {
	SDL_BlitSurface(menu.getSurface(),NULL,screen,NULL);
    /* Make sure everything is displayed on screen */
    int temp = SDL_GetTicks();
    if(temp - menuoverlayD > 10000) // if wait to long go to credits
        gamestate = GS_CREDITS;
    SDL_Flip (screen);
    /* Don't run too fast */
    SDL_Delay (1);
}

int handletitle() {
    SDL_Event event;
    int retval=0;

    /* Check for events */
    while (SDL_PollEvent (&event)) {
        switch (event.type) {
        case SDL_KEYUP:
             gamestate = GS_STARTMENU;
            break;
        case SDL_MOUSEBUTTONUP:
             gamestate = GS_STARTMENU;
             break;
        case SDL_QUIT:
            retval = 1;
            break;
        default:
            break;
        }
    }
    return retval;
}

void initmenu() {
     menu = Sprite("tiles/ui/menu.bmp",1,0);
}

void destroymenu() {
    menu.destroy();
     Mix_HaltMusic();
     Mix_FreeMusic(music);
}

void drawmenu() {
	SDL_BlitSurface(menu.getSurface(),NULL,screen,NULL);
    /* Make sure everything is displayed on screen */
    SDL_Flip (screen);
    /* Don't run too fast */
    SDL_Delay (1);
}

int handlemenu() {
    SDL_Event event;
    int retval=0;

    /* Check for events */
    while (SDL_PollEvent (&event)) {
        switch (event.type) {
        case SDL_KEYUP:
            if (event.key.keysym.sym == SDLK_ESCAPE) {
                retval=1;
            }
            break;
        case SDL_MOUSEBUTTONUP:
             if (event.button.y > 392 && event.button.y < 424 &&
                     event.button.x > 100 && event.button.x < 220) {
                gamestate = GS_LOGIN;

             } else if (event.button.y > 392 && event.button.y < 424 &&
                     event.button.x > 255 && event.button.x < 410) {
                gamestate = GS_CREDITS;

             } else if (event.button.y > 392 && event.button.y < 424 &&
                     event.button.x > 445 && event.button.x < 530) {
                retval=1;
             }
             break;
        case SDL_QUIT:
            retval = 1;
            break;
        default:
            break;
        }
    }
    return retval;
}

void initcredits() {
    menu = Sprite("tiles/ui/credits.bmp",1,0);
}

void destroycredits() {
    menu.destroy();
	// SDL_FreeSurface(menu.getSurface());
}

void drawcredits() {
	SDL_BlitSurface(menu.getSurface(),NULL,screen,NULL);
    /* Make sure everything is displayed on screen */
    SDL_Flip (screen);
    /* Don't run too fast */
    SDL_Delay (1);
}

int handlecredits() {
    SDL_Event event;
    int retval=0;

    /* Check for events */
    while (SDL_PollEvent (&event)) {
        switch (event.type) {
        case SDL_KEYUP:
            gamestate = GS_STARTMENU;
            break;
        case SDL_MOUSEBUTTONUP:
             gamestate=GS_STARTMENU;
             break;
        case SDL_QUIT:
            retval = 1;
            break;
        default:
            break;
        }
    }
    return retval;
}

void initstartscreen() {
    menu = Sprite("tiles/ui/start.bmp",1,0);
}

void destroystartscreen() {
//	 SDL_FreeSurface(menu.getSurface());
     menu.destroy();
     Mix_HaltMusic();
     Mix_FreeMusic(music);
}

void drawstartscreen(ClientPlayerz play) {
    char status[30];

	SDL_BlitSurface(menu.getSurface(),NULL,screen,NULL);

    if(startmenumode == 1) {
       sprintf(status,"STATS");
       drawText(fntCourier, status,  184,30, 255, 0, 255);
       sprintf(status,"NAME %s",play.FirstName);
       drawText(fntCourier, status,  184,50, 255, 0, 255);
    /*   sprintf(status,"HP %d / %d",play.hp,play.hpm);
       drawText(fntCourier, status,  184,70, 255, 0, 255);
       sprintf(status,"MP %d / %d",play.mp,play.mpm);
       drawText(fntCourier, status,  184,90, 255, 0, 255);
       sprintf(status,"LVL  %d",play.level);
       drawText(fntCourier, status,  184,120, 255, 0, 255);
       sprintf(status,"XP   %d",play.xp);
       drawText(fntCourier, status,  250,120, 255, 0, 255);
       sprintf(status,"GOLD %d",play.gold);
       drawText(fntCourier, status,  184,140, 255, 0, 255);
       sprintf(status,"STR %d",play.str);
       drawText(fntCourier, status,  184,170, 255, 0, 255);
       sprintf(status,"INT %d",play.iq);
       drawText(fntCourier, status,  250,170, 255, 0, 255);
       sprintf(status,"DEX %d",play.dex);
       drawText(fntCourier, status,  184,190, 255, 0, 255);
       sprintf(status,"WIS %d",play.wis);
       drawText(fntCourier, status,  250,190, 255, 0, 255);
       sprintf(status,"CON %d",play.con);
       drawText(fntCourier, status,  184,210, 255, 0, 255);
       sprintf(status,"CHA %d",play.cha);
       drawText(fntCourier, status,  250,210, 255, 0, 255);
*/
    } else if(startmenumode == 2) {
       sprintf(status,"EQUIPMENT");
       drawText(fntCourier, status,  184,30, 255, 0, 255);
       sprintf(status,"WEAPON");
       drawText(fntCourier, status,  184,50, 255, 0, 255);
        for(int i=0; i<5; i++) {
          //sprintf(status,"%d: %s",i,getWpnName(play.weapon[i]));
            sprintf(status,"%d: ",i); //TEMP
            drawText(fntCourier, status,  184,70+20*i+1, 255, 0, 255);
        }
       sprintf(status,"SHIELD");
       drawText(fntCourier, status,  184,170, 255, 0, 255);
       sprintf(status,"HELMET");
       drawText(fntCourier, status,  360,170, 255, 0, 255);
       for(int i=0; i<5; i++){
          //sprintf(status,"%d: %s",i,getArmName(play.armor[i]));
          sprintf(status,"%d: ",i); //TEMP
          drawText(fntCourier, status,  184,190+20*i+1, 255, 0, 255);}
       for(int i=0; i<5; i++){
          //sprintf(status,"%d: %s",i,getShdName(play.shield[i]), 255, 0, 255);
          sprintf(status,"%d: ",i); //TEMP
          drawText(fntCourier, status,  360,190+20*i+1, 255, 0, 255);}
       sprintf(status,"HELMET");
       drawText(fntCourier, status,  184,300, 255, 0, 255);
       sprintf(status,"ACCESSORY");
       drawText(fntCourier, status,  360,300, 255, 0, 255);
       for(int i=0; i<5; i++){
          //sprintf(status,"%d: %s",i,getHelName(play.helmet[i]), 255, 0, 255);
          sprintf(status,"%d: ",i+1); //TEMP
          drawText(fntCourier, status,  184,320+20*i, 255, 0, 255);}
       for(int i=0; i<5; i++){
          //sprintf(status,"%d: %s",i,getAccName(play.acces[i]));
          sprintf(status,"%d: ",i+1); //TEMP
          drawText(fntCourier, status,  360,320+20*i, 255, 0, 255);}
    }else if(startmenumode == 3){
       sprintf(status,"ABILITIES");
       drawText(fntCourier, status,  184,30, 255, 0, 255);
 /*      for(int i=0; i<12; i++){
         sprintf(status,"%d: %d",i+1,play.abilities[i]);
         drawText(fntCourier, status,  184,50+i*20, 255, 0, 255); }
       for(int i=12; i<24; i++){
         sprintf(status,"%d: %d",i+1,play.abilities[i]);
         drawText(fntCourier, status,  320,50+(i-12)*20, 255, 0, 255); }*/
    }else if(startmenumode == 4){
       sprintf(status,"ITEMS");
       drawText(fntCourier, status,  184,30, 255, 0, 255);
       for(int i=0; i<20; i++){
         sprintf(status,"%d: %d",i+1,play.items[i]);
         drawText(fntCourier, status,  184,50+i*20, 255, 0, 255); }
       for(int i=20; i<40; i++){
         sprintf(status,"%d: %d",i+1,play.items[i]);
         drawText(fntCourier, status,  334,50+(i-20)*20, 255, 0, 255); }
       for(int i=40; i<60; i++){
         sprintf(status,"%d: %d",i+1,play.items[i]);
         drawText(fntCourier, status,  494,50+(i-40)*20, 255, 0, 255); }
    }

    /* Make sure everything is displayed on screen */
    SDL_Flip (screen);
    /* Don't run too fast */
    SDL_Delay (1);

}

int handlestartscreen() {
    SDL_Event event;
    int retval=0;

    /* Check for events */
    while (SDL_PollEvent (&event))
    {
        switch (event.type)
        {
        case SDL_KEYUP:
            if (event.key.keysym.sym == SDLK_ESCAPE)
                retval=1;
            break;
        case SDL_MOUSEBUTTONUP:
             if (event.button.x>30 && event.button.x<110 &&event.button.y>25 && event.button.y<50){
                startmenumode=1;
             }else  if (event.button.x>30 && event.button.x<110 &&event.button.y>64 && event.button.y<84){
                startmenumode=2;
             }else  if (event.button.x>30 && event.button.x<110 &&event.button.y>98 && event.button.y<118){
                startmenumode=3;
             }else  if (event.button.x>30 && event.button.x<110 &&event.button.y>128 && event.button.y<158){
                startmenumode=4;
             }else  if (event.button.x>30 && event.button.x<110 &&event.button.y>168 && event.button.y<188){
                startmenumode=1;
                gamestate = GS_GAME;
             }
             break;
        case SDL_QUIT:
            retval = 1;
            break;
        default:
            break;
        }
    }
    return retval;
}
