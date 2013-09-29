#ifndef Sprite_h
#define Sprite_h

#ifdef __cplusplus
    #include <cstdlib>
#else
    #include <stdlib.h>
#endif

#include <iostream>
#include <fstream>
#include <string>
#include <math.h>

#ifdef __APPLE__
    #include <SDL/SDL.h>
#else
    #include <SDL/SDL.h>
#endif

/*
    Sprite library for SDL - using bitmaps
    This library was put together by Kenny Cason and is designed to
    be easily implemented into any C++ program using SDL
    Feel free to do what ever you want with it. enjoy!
    Please report any bugs
    kenneth [DOT] cason [AT] gmail [DOT] com
    v1.0
    2009 Jan 7
*/


#define SPRITE_MAX(a,b)	((a > b) ? a : b)
#define SPRITE_MIN(a,b)	((a < b) ? a : b)

#define FLIP_HORIZONTAL 1
#define FLIP_VERTICAL   2

extern long timer; //milliseconds since SDL has been initialized

using namespace std;

class Sprite {
public:
    Sprite();
    Sprite(string fileName,int maxFrames, int animationSpeed);
    Sprite(SDL_Surface* surface, int maxFrames,int animationSpeed);
    void draw(SDL_Surface* buffer, int x, int y);
    SDL_Surface* getRect(int x, int y, int width, int height);
    void blitSurface(SDL_Surface* surface, int x, int y);
    void blitSprite(Sprite sprite, int x, int y);
    void setTransparency(Uint32 color);
    void setTransparency(int red, int green, int blue);
    void setSpeed(int animationSpeed);
    void start();
    void restart();
    void animate();
    void setLoopToBegin(bool loop);
    bool running();
    void stop();
    bool isSprite();
    bool rectCollide(int x1, int y1, Sprite test, int x2, int y2);
    bool pixelCollide(int x1, int y1, Sprite test, int x2, int y2);
    void flipHorizontal();
    void flipVertical();
    Sprite rotateX(int theta);
    Sprite rotateY(int theta);
   // Sprite rotateZ(int theta); <- in development
    Sprite rotate90();
    Sprite rotate180();
    Sprite rotate270();
    Sprite zoom(float x);  // percentage to zoom in
    Sprite stretchX(float x); // percentage to stretchheight
    Sprite stretchY(float y);  // percentage to stretchwidth
    Sprite stretch(float x,float y); // percentage to strech X and Y
    Sprite skewX(float x); // degrees to skew X  <- in development
    Sprite skewY(float y); // degrees to skew X  <- in development
    Sprite fade(float fade); // fade from 0 to 100%
    void reverseAnimation();
    bool isTransparentPixel(int x, int y);
    void setTransparentPixel(int x, int y);
    Uint32 getPixel(int x, int y);
    void setPixel(int x, int y, Uint32 color);
    void setPixel(int x, int y, int red, int green, int blue);
    Uint8 getPixel8(int x, int y);
    void setPixel8(int x, int y, Uint8 color);
    Uint16 getPixel16(int x, int y);
    void setPixel16(int x, int y, Uint16 color);
    Uint32 getPixel32(int x, int y);
    void setPixel32(int x, int y, Uint32 color);
    int getFrame();
    int getFrameWidth();
    int getFrameHeight();
    int getWidth();
    int getSpriteWidth();
    int getHeight();
    int getSpriteHeight();
    bool equals(Sprite compare);
    SDL_Surface* getSurface();
    void setSurface(SDL_Surface* surface);
    void destroy();

    bool loaded;
    bool run;
    int speed;
    int width;
    int height;
    int index;
    int indexIterator;
    bool loopToBeginning;  // if loop = true iterate through animations from 0 to N, then reset to 0
                // if loop = false iterate through animations from 0 to N, then from N to 0, and repeat
    int maxFrames;
    int lastAnimated;

    SDL_Surface* sprite;

private:
    Sprite rotate(int); // helper function for rotate90(),rotate180(), and rotate270()
    void flip(int);  // helper function for flipHorizontal() and flipVeritcal()
};
#endif
