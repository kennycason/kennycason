
#include <iostream>
#include <cstdlib>
#include <SDL/SDL.h>

#include "Vector3D.h"

/*
ABOUT: 3D Vector and rotation functions. Rotations about X, Y, Z, and any arbitruary Vector
AUTHOR: Kenny Cason
WEBSITE: kennycason.com
EMAIL: kenneth.cason@gmail.com
DATE: 11-19-2009
*/


using namespace std;

void setPixel(SDL_Surface* surface, int x, int y, Uint32 pixel);
static double sigmoid(double x);
void keyboard();
void handleInput();


bool KEYS[322];  // 322 is the number of SDLK_DOWN events

// DEFAULTS
int xMax = 250;
int yMax = 250;
int pointsX = 400;
int pointsY = 400;
Vector3D** fx;
//Vector3D** dx;
double xaxis = 320;
double yaxis = 240;

double rotSpeed = 0.1;

double thetaX = 0;
double thetaY = 0;
double thetaZ = 0;

int main ( int argc, char** argv ) {
    // initialize SDL video
    if ( SDL_Init( SDL_INIT_VIDEO ) < 0 ) {
        cout <<  "Unable to init SDL: %s\n" << SDL_GetError() << endl;
        return 1;
    }

    // make sure SDL cleans up before exit
    atexit(SDL_Quit);

    // create a new window
    SDL_Surface* screen = SDL_SetVideoMode(640, 480, 16,
                                           SDL_HWSURFACE|SDL_DOUBLEBUF);
    if (!screen) {
        cout << "Unable to set 640x480 video: %s\n" << SDL_GetError() << endl;
        return 1;
    }
    for(int i = 0; i < 322; i++ ) {
        KEYS[i] = false;
    }


    xMax = 250;
    yMax = 250;
    pointsX = 250;
    pointsY = 250;

    fx = new Vector3D*[pointsX];
    for(int x = 0; x < pointsX; ++x) {
        fx[x] = new Vector3D[pointsY];
    }
/*
    dx = new Vector3D*[pointsX];
    for(int x = 0; x < pointsX; ++x) {
        dx[x] = new Vector3D[pointsY];
    }*/

    thetaX = 0;
    thetaY = 0;
    thetaZ = 0;

    for(int y = 0; y < pointsY; y++) {
        for(int x = 0; x < pointsX; x++) {
            fx[x][y].x = x * (xMax*2)/pointsX - xMax;
            fx[x][y].y = y * (yMax*2)/pointsY - yMax;
            /* UN COMMENT ANY ONE OF THE BELOW FUNCTIONS OF X,Y */
       //     fx[x][y].z = 0;
        //   fx[x][y].z = pow(fx[x][y].x/20,2) + pow(fx[x][y].y/20, 2) - 100;  // z = x^2 + y^2;
          //  dx[x][y].z = 2*fx[x][y].x/20 + -6*pow(fx[x][y].y,2)/20;
           // fx[x][y].z = 30*sin(fx[x][y].x/20) + -15*cos(fx[x][y].y/20);  // z = sin(x) - cos(x);
            //fx[x][y].z = pow(fx[x][y].x/20,2) + pow(fx[x][y].y/20, 2);  // z = x^2 + y^2;
            //fx[x][y].z = sqrt(230*230 - pow(fx[x][y].x,2) - pow(fx[x][y].y, 2)); // x^2 + y^2 + z^2 = r^2

        //    fx[x][y].z =   20*log( sqrt(200*200 - pow(fx[x][y].x/2,2)) - 20*log( pow(fx[x][y].y/2,2)) );
         //   fx[x][y].z =   20*log(pow(fx[x][y].x/2,2))- 20*log( pow(fx[x][y].y/2,2));
        //    fx[x][y].z =   (20*log(pow(fx[x][y].x/2,2)+ pow(fx[x][y].y/2,2))) -200;
            //fx[x][y].z =   (pow(fx[x][y].x/80, fx[x][y].x/20) - pow(fx[x][y].y/80, fx[x][y].y/20)) -200;
       //     fx[x][y].z =   (pow(fx[x][y].x/25, fx[x][y].x/25) + pow(fx[x][y].y/25, fx[x][y].y/25)) /*- log(fx[x][y].x + fx[x][y].y)*/ -200;
           //   fx[x][y].z = 20*log(fx[x][y].x)/100 - 20*pow(fx[x][y].y/100, 2);
        //   fx[x][y].z = pow(fx[x][y].x/2,2)+ 1/pow(fx[x][y].y/2,2);
         //  fx[x][y].z = 10*tan(fx[x][y].x/40) + -10*tan(fx[x][y].y/40);  // z = sin(x) - cos(x);
            //fx[x][y].z = 30 / ( pow(fx[x][y].x/20,1) + pow(fx[x][y].y/20, 1));  // z = x^2 + y^2;
         //   fx[x][y].z = 30 / ( pow(fx[x][y].x/20,1) + pow(fx[x][y].y/20, 1));  // z = x^2 + y^2;
       // fx[x][y].z = pow(fx[x][y].x/20,2) - pow(fx[x][y].y/20, 2) + (20*log(pow(fx[x][y].x/2,2)+ pow(fx[x][y].y/2,2))) - 100;
     // fx[x][y].z = 30*sin(fx[x][y].x/40) + -15*cos(fx[x][y].y/40) +  (20*log(pow(fx[x][y].x/2,2)+ pow(fx[x][y].y/2,2))) - 100;
       //   fx[x][y].z = -(pow(fx[x][y].x/20,2) + pow(fx[x][y].y/20, 2) )+  (40*log(pow(fx[x][y].x,2) - pow(fx[x][y].y,2))) - 175;
         //   fx[x][y].z = tan(fx[x][y].x)*tan(fx[x][y].y);
        //    fx[x][y].z = tan(fx[x][y].x)*fx[x][y].x*tan(fx[x][y].y);
         //  fx[x][y].z = tan(fx[x][y].x)*fx[x][y].x*tan(fx[x][y].y)*fx[x][y].y;
       //  fx[x][y].z = sin(fx[x][y].x/30)*fx[x][y].x*cos(fx[x][y].y/30)*fx[x][y].y/100;
        fx[x][y].z = sin(fx[x][y].x/30)*fx[x][y].x*cos(fx[x][y].y/30)*fx[x][y].y/100 +  40*log(pow(fx[x][y].x/2,2) + pow(fx[x][y].y/2,2)) - 200;
         //  fx[x][y].z = abs((long)(sin(fx[x][y].x/30)*fx[x][y].x*cos(fx[x][y].y/30)*fx[x][y].y/100));
         //fx[x][y].z = sin(log(fx[x][y].x/30))*fx[x][y].x*cos(log(fx[x][y].y/30))*fx[x][y].y/100;
     //    fx[x][y].z = 30 * acosf(fx[x][y].x/20) - 30 * asinf(fx[x][y].y/20);
        }
    }

    for(int y = 0; y < pointsY; y++) {
        for(int x = 0; x < pointsX; x++) {
                fx[x][y] = rotateX(fx[x][y], PI/4);
                fx[x][y] = rotateY(fx[x][y], -PI/5);
                fx[x][y] = rotateZ(fx[x][y], 0);
        }
    }


    xaxis = 320;
    yaxis = 240;

    bool done = false;
    while (!done) {
        SDL_Event event;
        while (SDL_PollEvent(&event)) {
            switch (event.type) {
                case SDL_QUIT:
                    done = true;
                    break;
                case SDL_KEYDOWN:
                    KEYS[event.key.keysym.sym] = true;
                    break;
                case SDL_KEYUP:
                    KEYS[event.key.keysym.sym] = false;
                    thetaX = 0;
                    thetaY = 0;
                    thetaZ = 0;
                    break;
            }
        }

        bool rot = false;
        if(KEYS[SDLK_q]) {
            thetaX = -rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_w]) {
            thetaX = rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_a]) {
            thetaY = -rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_s]) {
            thetaY = rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_z]) {
            thetaZ = -rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_x]) {
            thetaZ = rotSpeed;
            rot = true;
        }
        if(KEYS[SDLK_LEFT]) {
            xaxis -= 10;
        }
        if(KEYS[SDLK_RIGHT]) {
            xaxis += 10;
        }
        if(KEYS[SDLK_UP]) {
            yaxis -= 3;
        }
        if(KEYS[SDLK_DOWN]) {
            yaxis += 3;
        }
        if(KEYS[SDLK_ESCAPE]) {
            done = true;
        }

        if(rot) {
            rot = false;
            for(int y = 0; y < pointsY; y++) {
                for(int x = 0; x < pointsX; x++) {
                    fx[x][y] = rotateX(fx[x][y], thetaX);
                    fx[x][y] = rotateY(fx[x][y], thetaY);
                    fx[x][y] = rotateZ(fx[x][y], thetaZ);
                }
            }
        }

        SDL_FillRect(screen, 0, SDL_MapRGB(screen->format, 0, 0, 0));
        for(int y = 0; y < pointsY; y++) {
            for(int x = 0; x < pointsX; x++) {
                setPixel(screen, fx[x][y].x + xaxis, fx[x][y].y + yaxis,  SDL_MapRGB(screen->format, 255,255,255));

            }
        }

        SDL_Flip(screen);
    }
    printf("Exited cleanly\n");
    return 0;
}

/**
 * 0 to 255
 */
static double sigmoid(double x) {
    return 255.0 / (1 + exp(-x));
}

void setPixel(SDL_Surface* surface, int x, int y, Uint32 pixel) {
    if(surface == NULL) {
         cout << "Failed to set pixel, surface not initialized!"<< endl;
         return;
     }
    if(x < 0 || x >= surface->w || y < 0 || y >= surface->h) {
       //  cout << "Pixel not within surface's dimensions"<< endl;
         return;
     }
    int bpp = surface->format->BytesPerPixel;
    /* p is the address to the pixel we want to set */
    Uint8 *p = (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;
    switch(bpp) {
        case 1:
            *p = pixel;
            break;
        case 2:
            *(Uint16 *)p = pixel;
            break;
        case 3:
            if(SDL_BYTEORDER == SDL_BIG_ENDIAN) {
                p[0] = (pixel >> 16) & 0xff;
                p[1] = (pixel >> 8) & 0xff;
                p[2] = pixel & 0xff;
            } else {
                p[0] = pixel & 0xff;
                p[1] = (pixel >> 8) & 0xff;
                p[2] = (pixel >> 16) & 0xff;
            }
            break;
        case 4:
            *(Uint32 *)p = pixel;
            break;
    }
}
