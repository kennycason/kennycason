#include <cstdlib>
#include <SDL/SDL.h>
#include "Keyboard.h"


int main ( int argc, char** argv ) {
    freopen("CONOUT$", "wt", stdout);

    // initialize SDL video
    if ( SDL_Init( SDL_INIT_VIDEO ) < 0 ) {
        printf( "Unable to init SDL: %s\n", SDL_GetError() );
        return 1;
    }

    // make sure SDL cleans up before exit
    atexit(SDL_Quit);

    // create a new window
    SDL_Surface* screen = SDL_SetVideoMode(640, 480, 16, SDL_HWSURFACE|SDL_DOUBLEBUF);
    if ( !screen ) {
        printf("Unable to set 640x480 video: %s\n", SDL_GetError());
        return 1;
    }

    // load an image
    SDL_Surface* bmp = SDL_LoadBMP("cb.bmp");
    if (!bmp) {
        printf("Unable to load bitmap: %s\n", SDL_GetError());
        return 1;
    }

    // centre the bitmap on screen
    SDL_Rect dstrect;
    dstrect.x = (screen->w - bmp->w) / 2;
    dstrect.y = (screen->h - bmp->h) / 2;

    Keyboard* k = new Keyboard();


    std::vector<int> comboExit;
    comboExit.push_back(SDLK_LCTRL);
    comboExit.push_back(SDLK_LALT);
    comboExit.push_back(SDLK_g);

    std::vector<int> sequentialCombo;
    sequentialCombo.push_back(SDLK_1);
    sequentialCombo.push_back(SDLK_2);
    sequentialCombo.push_back(SDLK_3);

    // program main loop
    bool done = false;
    while (!done) {
        k->listen();
        if(k->keyDown(SDLK_ESCAPE)) {
            done = true;
        }

        if(k->keyDown(SDLK_RIGHT)) {
            dstrect.x+=2;
        }
        if(k->keyDown(SDLK_LEFT)) {
            dstrect.x-=2;
        }
        if(k->keyDown(SDLK_UP)) {
            dstrect.y-=2;
        }
        if(k->keyDown(SDLK_DOWN)) {
            dstrect.y+=2;
        }

        if(k->isCombo(comboExit)) {
            done = true;
        }

        if(k->isSequentialCombo(sequentialCombo)) {
            done = true;
        }
        // clear screen
        SDL_FillRect(screen, 0, SDL_MapRGB(screen->format, 0, 0, 0));
        SDL_BlitSurface(bmp, 0, screen, &dstrect);
        SDL_Flip(screen);
    } // end main loop

    // free loaded bitmap
    SDL_FreeSurface(bmp);

    // all is well ;)
    printf("Exited cleanly\n");
    return 0;
}
