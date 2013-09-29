#include "Sprite.h"
using namespace std;
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

Sprite::Sprite() {
    sprite = NULL;
    loaded = false;
    width = 0;
    height = 0;
    speed = 0;
    run = false;
    loopToBeginning = true;
    indexIterator = 0;
    index = 0;
}

Sprite::Sprite(string file, int frames, int speed) {
    SDL_Surface *temp = SDL_LoadBMP(file.c_str());
    sprite = SDL_DisplayFormat(temp);
    SDL_FreeSurface(temp);
    if(sprite == NULL) {
        cout << "failed to load sprite " << file << endl;
        loaded = false;
        width = 0;
        height = 0;
        this->speed = 0;
    } else {
        cout << "successfully loaded sprite " << file << endl;
        loaded = true;
        width = sprite->w/frames;
        height = sprite->h;
        this->speed = speed;
        run = true;
    }
    maxFrames = frames;
    if(maxFrames > 1) {
        run = true;
    } else {
        run = false;
    }
    lastAnimated = timer;
    index = 0;
    indexIterator = 1;
    loopToBeginning = true;
}

Sprite::Sprite(SDL_Surface* surface, int frames, int speed) {
    if(surface == NULL) {
        cout << "failed to load sprite" << endl;
        sprite = NULL;
        loaded = false;
        width = 0; height = 0;
        this->speed = 0;
    } else {
        cout << "successfully loaded sprite" << endl;
        // create a new surface
        if(surface->flags & SDL_SRCCOLORKEY) {
            sprite = SDL_CreateRGBSurfaceFrom(surface->pixels, surface->w, surface->h, surface->format->BitsPerPixel, surface->pitch,
                surface->format->Rmask, surface->format->Gmask, surface->format->Bmask, 0 );
        } else {
            sprite = SDL_CreateRGBSurfaceFrom(surface->pixels, surface->w, surface->h, surface->format->BitsPerPixel, surface->pitch,
                surface->format->Rmask, surface->format->Gmask, surface->format->Bmask, surface->format->Amask );
        }
        if(surface->flags & SDL_SRCCOLORKEY) {
            SDL_SetColorKey(sprite, SDL_RLEACCEL|SDL_SRCCOLORKEY, surface->format->colorkey );
        }
        loaded = true;
        width = sprite->w/frames;
        height = sprite->h;
        this->speed = speed;
    }
    maxFrames = frames;
    if(maxFrames > 1) {
        run = true;
    } else {
        run = false;
    }
    lastAnimated = timer;
    indexIterator = 1;
    index = 0;
    loopToBeginning = true;
}

void Sprite::draw(SDL_Surface* buffer, int x, int y) {
     if(!isSprite()) {
         cout << "Failed to draw, Sprite not initialized!"<< endl;
         return;
     }
    SDL_Rect dstrect;
    dstrect.x = x;
    dstrect.y = y;
    // this blits the current frame from the sprite sheet
    SDL_Rect animRect;
    animRect.x = width*index;
    animRect.y = 0;
    animRect.w = width;
    animRect.h = height;
    SDL_BlitSurface(sprite, &animRect, buffer,&dstrect);
}

SDL_Surface* Sprite::getRect(int x, int y, int w, int h) {
    if(!isSprite()) {
         cout << "Failed to get Rectangle, Sprite not initialized!"<< endl;
         return NULL;
     }
    SDL_Surface* newrect = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        newrect = SDL_CreateRGBSurface(SDL_SWSURFACE, w, h, sprite->format->BitsPerPixel,
            sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        newrect = SDL_CreateRGBSurface(SDL_SWSURFACE, w, h, sprite->format->BitsPerPixel,
            sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }

    Sprite tmp = Sprite(newrect,1,0);
    for(int j = 0; j < h; j++) {
        for(int i = 0; i < w; i++) {
            tmp.setPixel(i,j,getPixel(x+i,y+j));
        }
    }
    //Copy color key
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    return tmp.getSurface();
}

void Sprite::blitSprite(Sprite blitrect, int x, int y) {
    if(!isSprite()) {
         cout << "Failed to set Rectangle, Sprite not initialized!"<< endl;
         return;
     }
    for(int j = 0; j < blitrect.getHeight() && j < height; j++) {
        for(int i = 0; i < blitrect.getWidth() && i < width; i++) {
            if(!blitrect.isTransparentPixel(i,j)) { // if its not transparent, draw it
                setPixel(x,y,blitrect.getPixel(i,j));
            }
        }
    }
    SDL_Rect rect;
    rect.x = x;
    rect.y = y;
    rect.w = blitrect.getWidth();
    rect.h = blitrect.getHeight();
    SDL_BlitSurface(blitrect.getSurface(),NULL,sprite,&rect);

}

void Sprite::blitSurface(SDL_Surface* surface, int x, int y) {
    if(!isSprite()) {
         cout << "Failed to blit Surface, Sprite not initialized!"<< endl;
         return;
     }
     SDL_Rect dest;
     dest.x = x;
     dest.y = y;
     SDL_BlitSurface(surface,NULL,sprite,&dest);
}

void Sprite::setTransparency(int r, int g, int b) {
    if(!isSprite()) {
         cout << "Failed to set Transparency, Sprite not initialized!"<< endl;
         return;
     }
    SDL_SetColorKey(sprite, SDL_SRCCOLORKEY,SDL_MapRGB(sprite->format, r, g, b));
}

void Sprite::setTransparency(Uint32 colorkey) {
    SDL_SetColorKey(sprite, SDL_SRCCOLORKEY, colorkey);
}

void Sprite::setSpeed(int i) {
    speed = i;
}

void Sprite::start() {
    run = true;
}

void Sprite::restart() {
    if(run) {
        index = 0;
        lastAnimated = timer;
    }
}


void Sprite::animate() {
    if(run) {
        if(timer - lastAnimated > speed) {
            lastAnimated = timer;
            index += indexIterator;
            if(index >= maxFrames) {
                if(loopToBeginning) {
                    indexIterator = 1;
                    index = 0;
                } else {
                    indexIterator = -1;
                    index = maxFrames - 1;
                }
            } else if(index < 0) {
                indexIterator = 1;
                index = 0;
            }
        }
    }
}

void Sprite::setLoopToBegin(bool loop) {
    loopToBeginning = loop;
}

bool Sprite::running() {
   return run;
}

void Sprite::stop() {
    run = false;
    index = 0;
}

bool Sprite::isSprite() {
    return loaded;
}


bool Sprite::rectCollide(int thisX, int thisY, Sprite test, int testX, int testY) {
    /*
       test
	    testX,testY____ testX+width
		|   	       |
		| sprite	   |
		|  thisX,thisY |__ thisX+width
		|  |	       |  |
		|__|___________|  |
		testY+height	  |
			|_____________|
			thisY+height
    */
    if(!isSprite() || !test.isSprite()) {
         cout << "Failed to perfrom Rectangle Collision test, Sprite not initialized!"<< endl;
         return false;
    }
    if((thisX >= testX && thisX <= testX + test.getWidth()) || (testX >= thisX && testX <= thisX + width)) {

         if((thisY >= testY && thisY <= testY + test.getHeight()) || (testY >= thisY && testY <= thisY + height)) {
             return true;
         }
    }
    return false;
}

bool Sprite::pixelCollide(int thisX, int thisY, Sprite test, int testX, int testY) {
	/*check if bounding boxes intersect*/
	if((thisX + width < testX) || (testX + test.getWidth() < thisX)) {
		return false;
	}
	if((thisY + height < testY) || (testY + test.getHeight() < thisY)) {
		return false;
	}
    // get the overlaping box
	int inter_x0 = SPRITE_MAX(testX,thisX);
	int inter_x1 = SPRITE_MIN(testX+test.getWidth(),thisX+width);

	int inter_y0 = SPRITE_MAX(testY,thisY);
	int inter_y1 = SPRITE_MIN(testY+test.getHeight(),thisY+height);

	for(int y = inter_y0 ; y <= inter_y1 ; y++) {
		for(int x = inter_x0 ; x <= inter_x1 ; x++) {
		    /*compute offsets for surface, but dont forget to account for the current animation*/
            if((!test.isTransparentPixel(x-testX + test.index * test.getWidth() , y-testY))
			&& (!isTransparentPixel(x-thisX + index * width, y-thisY))) {/*before pass to isTransparentPixel*/
				return true;
			}
		}
	}
    return false;
}

void Sprite::flip(int val) {
    if(!isSprite()) {
         cout << "Failed to flip, Sprite not initialized!"<< endl;
         return;
     }
    SDL_Surface* flipped = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        flipped = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w, sprite->h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        flipped = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w, sprite->h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    // check to see if the surface must be locked
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(flipped,maxFrames,speed);
    Uint32* fPixels = (Uint32*)tmp.getSurface()->pixels;

    for(int y = 0; y < sprite->h; y++) {
        for(int x = 0; x < sprite->w; x++) {
            if(val == FLIP_HORIZONTAL) {
                tmp.setPixel(sprite->w - 1 - x, y, getPixel(x,y));
            } else if(val == FLIP_VERTICAL) {
                tmp.setPixel(x, sprite->h - 1 - y, getPixel(x,y));
            }
        }
    }
    flipped->pixels = fPixels;
    //Copy color key
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(flipped, SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    sprite = flipped;
}

void Sprite::flipVertical() {
    flip(FLIP_VERTICAL);
}

void Sprite::flipHorizontal() {
    flip(FLIP_HORIZONTAL);
}

Sprite Sprite::rotateX(int theta) {
    if(!isSprite()) {
         cout << "Failed to rotate animation, Sprite not initialized!"<< endl;
         return Sprite();
     }
    SDL_Surface* rotated = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * width, height, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * width, height, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(rotated,maxFrames,speed);
    tmp.index = index;
    // to ensure rotation around the center of the sprite, calculate the centers of each sprite
    int centerY = height/2;
    // rotation matrix to rotate around Y
    //  [(1          ,0),
    //   (0          ,cos(theta))]
    double radians = theta * M_PI / 180;
    for(int y = 0; y < height; y++) {
        for(int x = 0; x < width; x++) {
            int newX = x;
            int newY = (y - centerY) * cos(radians) + centerY;
            // rotate each frame based on the newX and newY
            if(newY > 0 && newY < tmp.getSpriteHeight()) {
                for(int i = 0; i < maxFrames; i++) {
                    tmp.setPixel(tmp.getWidth() * i + newX, newY,getPixel((i * width) + x,y));
                }
            }
        }
    }
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    return tmp;
}


Sprite Sprite::rotateY(int theta) {
    if(!isSprite()) {
         cout << "Failed to rotate animation, Sprite not initialized!"<< endl;
         return Sprite();
     }
    SDL_Surface* rotated = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * width, height, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * width, height, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(rotated,maxFrames,speed);
    tmp.index = index;
    // to ensure rotation around the center of the sprite, calculate the centers of each sprite
    int centerX = width/2;
    // rotation matrix to rotate around Y
    //  [(cos(theta) ,0),
    //   (0          ,1)]
    double radians = theta * M_PI / 180;
    for(int y = 0; y < height; y++) {
        for(int x = 0; x < width; x++) {
            int newX = (x - centerX) * cos(radians) + centerX;
            int newY = y;
            // ensure every pixel is transparent first
            // rotate each frame based on the newX and newY
            if( newX > 0 && newX < tmp.getSpriteWidth()) {
                for(int i = 0; i < maxFrames; i++) {
                    tmp.setPixel(tmp.getWidth() * i + newX, newY,getPixel((i * width) + x,y));
                }
            }
        }
    }
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    return tmp;
}

/*
Sprite Sprite::rotateZ(int theta) {
    if(!isSprite()) {
         cout << "Failed to rotate animation, Sprite not initialized!"<< endl;
         return Sprite();
     }
     int diagonal = sqrt(width*width + height*height);
    // when rotating, the width must sometimes be adjusted to fit the sprite.
    int rotWidth = width + (diagonal - width) * 2;
    // find the maximum height that the new sprite can be by calculating the diagonal of the sprite
    int rotHeight = height + (diagonal - height) * 2;
    SDL_Surface* rotated = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * rotWidth, rotHeight, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, maxFrames * rotWidth, rotHeight, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    // check to see if the surface must be locked
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(rotated,maxFrames,speed);
    // to ensure rotation around the center of the sprite, calculate the centers of each sprite
    int rotCenterX = tmp.getWidth()/2;
    int rotCenterY = tmp.getHeight()/2;
    int centerX = width/2;
    int centerY = width/2;
    // rotation matrix to rotate around Z
    //  [(cos(theta),-sin(theta)),
    //   (sin(theta),cos(theta)]
    double radians = theta * M_PI / 180;
    for(int y = 0; y < height; y++) {
        for(int x = 0; x < width; x++) {
            int newX = (x - centerX) * cos(radians) + (y - centerX) * -sin(radians);
            int newY = (x - centerY) * sin(radians) + (y - centerY) * cos(radians);
            // rotate each frame based on the newX and newY
            if(newX + rotCenterX >= 0 && newX + rotCenterX < tmp.getWidth()
                && newY + rotCenterY > 0 && newY + rotCenterY < tmp.getHeight()) {
                for(int i = 0; i < maxFrames; i++) {
                    tmp.setPixel(tmp.getWidth() * i + newX + rotCenterX, newY+ rotCenterY,getPixel((i*width)+x,y));
                }
            }
        }
    }
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    return tmp;
}
*/

Sprite Sprite::rotate90() {
    return rotate(90);
}

Sprite Sprite::rotate180() {
    return rotate(180);
}

Sprite Sprite::rotate270() {
    return rotate(270);
}

Sprite Sprite::rotate(int deg) {
    if(!isSprite()) {
         cout << "Failed to rotate animation, Sprite not initialized!"<< endl;
         return Sprite();
     }
    SDL_Surface* rotated = NULL;
    int w,h;
    if(deg == 90 || deg == 270) {
        w = height * maxFrames;
        h = width;
    } else if(deg == 180) {
        w = width * maxFrames;
        h = height;
    } else {
        return Sprite();
    }
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, w, h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        rotated = SDL_CreateRGBSurface(SDL_SWSURFACE, w, h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(rotated,maxFrames,speed);
    tmp.index = index;
    for(int i = 0; i < maxFrames; i++) {
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(deg == 90) {
                    tmp.setPixel(tmp.getWidth() * i + tmp.getWidth() - y - 1, x, getPixel((i * width) + x,y));
                } else if(deg == 180) {
                    tmp.setPixel(tmp.getWidth() * i + tmp.getWidth() - x - 1, tmp.getHeight() - y - 1, getPixel((i * width) + x,y));
                } else if(deg == 270) {
                    tmp.setPixel(tmp.getWidth() * i + y, tmp.getHeight() - x - 1, getPixel((i * width) + x,y));
                } else {
                    return Sprite();
                }
            }
        }
    }
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    return tmp;
}

Sprite Sprite::stretchX(float stretchX) {
    return stretch(stretchX, 100);
}

Sprite Sprite::stretchY(float stretchY) {
    return stretch(100, stretchY);
}

Sprite Sprite::zoom(float zoom) {
    return stretch(zoom,zoom);
}


Sprite Sprite::stretch(float stretchX, float stretchY) {
    if(!isSprite()) {
         cout << "Failed to zoom, Sprite not initialized!"<< endl;
         return Sprite();
     }
    SDL_Surface* zoomed = NULL;
    if(stretchX < 1 || stretchY < 1) {
        cout << "Failed to zoom, value must be greater than zero!"<< endl;
        return Sprite();
    }
    stretchX /= 100;
    stretchY /= 100;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        zoomed = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w*stretchX, sprite->h*stretchY, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0 );
    } else {
        zoomed = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w*stretchX, sprite->h*stretchY, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(zoomed,maxFrames,speed);
    tmp.index = index;
    for(int y = 0; y < tmp.getHeight(); y++) {
        for(int x = 0; x < tmp.getWidth(); x++) {
            // iterate over each animation as opposed to the whole sprite, to ensure that each animation is resized properly
            for(int i = 0; i < maxFrames; i++) {
                tmp.setPixel((tmp.getWidth() * i) + x, y, getPixel((width * i) + (int)(x/stretchX),(int)(y/stretchY) ));
            }
        }
    }
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    return tmp;
}

Sprite Sprite::fade(float fade) {
     fade = 0;
    return Sprite();
}

void Sprite::reverseAnimation() {
    if(!isSprite()) {
         cout << "Failed to reverse animation, Sprite not initialized!" << endl;
         return;
    }
    SDL_Surface* reversed = NULL;
    // create a new surface
    if(sprite->flags & SDL_SRCCOLORKEY) {
        reversed = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w, sprite->h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, 0);
    } else {
        reversed = SDL_CreateRGBSurface(SDL_SWSURFACE, sprite->w, sprite->h, sprite->format->BitsPerPixel,
        sprite->format->Rmask, sprite->format->Gmask, sprite->format->Bmask, sprite->format->Amask);
    }
    // check to see if the surface must be locked
    if(SDL_MUSTLOCK(sprite)) {
        SDL_LockSurface(sprite);
    }
    Sprite tmp = Sprite(reversed,maxFrames,speed);
    for(int f = 0; f < maxFrames; f++) {
        for(int y = 0; y < sprite->h; y++) {
            for(int x = 0; x < width; x++) {
                tmp.setPixel((maxFrames - f - 1)*width + x , y, getPixel(f*width+x,y));
            }
        }
    }
    //Copy color key
    if(sprite->flags & SDL_SRCCOLORKEY) {
        SDL_SetColorKey(tmp.getSurface(), SDL_RLEACCEL|SDL_SRCCOLORKEY, sprite->format->colorkey );
    }
    if(SDL_MUSTLOCK(sprite)) {
        SDL_UnlockSurface(sprite);
    }
    sprite = tmp.getSurface();
}

void Sprite::setTransparentPixel(int x, int y) {
    setPixel(x, y, sprite->format->colorkey);
}

bool Sprite::isTransparentPixel(int x, int y) {
    if(!isSprite()) {
         cout << "Failed to test pixel transparency, Sprite not initialized!"<< endl;
         return false;
    }
	int bpp = sprite->format->BytesPerPixel;
	//*p is the address to the pixel we want to retrieve
	Uint8 *p = (Uint8 *)sprite->pixels + y * sprite->pitch + x * bpp;
	Uint32 pixelcolor;
	switch(bpp)
	{
		case(1):
			pixelcolor = *p;
		break;
		case(2):
			pixelcolor = *(Uint16 *)p;
		break;
		case(3):
			if(SDL_BYTEORDER == SDL_BIG_ENDIAN)
				pixelcolor = p[0] << 16 | p[1] << 8 | p[2];
			else
				pixelcolor = p[0] | p[1] << 8 | p[2] << 16;
		break;
		case(4):
			pixelcolor = *(Uint32 *)p;
		break;
	}
	//test whether pixels color == color of transparent pixels for that surface
	return (pixelcolor == sprite->format->colorkey);
}

void Sprite::setPixel(int x, int y, int r, int g, int b) {
    Uint32 color;
    color += b*256*256;
    color += g*256;
    color += r;
    setPixel(x,y,color);
}

void Sprite::setPixel(int x, int y, Uint32 pixel) {
    if(!isSprite()) {
         cout << "Failed to set pixel, Sprite not initialized!"<< endl;
         return;
     }
    int bpp = sprite->format->BytesPerPixel;
    /* p is the address to the pixel we want to set */
    Uint8 *p = (Uint8 *)sprite->pixels + y * sprite->pitch + x * bpp;
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

Uint32 Sprite::getPixel(int x, int y) {
    if(!isSprite()) {
         cout << "Failed to get pixel, Sprite not initialized!"<< endl;
         return 0;
    }
    int bpp = sprite->format->BytesPerPixel;
    /* p is the address to the pixel we want to retrieve */
    Uint8 *p = (Uint8 *)sprite->pixels + y * sprite->pitch + x * bpp;
    switch(bpp) {
        case 1:
            return *p;
        case 2:
            return *(Uint16 *)p;
        case 3:
            if(SDL_BYTEORDER == SDL_BIG_ENDIAN) {
                return p[0] << 16 | p[1] << 8 | p[2];
            } else {
                return p[0] | p[1] << 8 | p[2] << 16;
            }
            break;
        case 4:
            return *(Uint32 *)p;
        default:
            return 0;
    }
}

Uint8 Sprite::getPixel8(int x, int y) {
    if(!isSprite()) {
         cout << "Failed to get pixel, Sprite not initialized!"<< endl;
         return 0;
     }
    Uint8* pixels = (Uint8*)sprite->pixels;
    return pixels[y * sprite->w + x];
}

void Sprite::setPixel8(int x, int y, Uint8 pixel) {
    if(!isSprite()) {
         cout << "Failed to set pixel, Sprite not initialized!"<< endl;
         return;
     }
    Uint8* pixels = (Uint8*)sprite->pixels;
    pixels[y * sprite->w + x] = pixel;
}

Uint16 Sprite::getPixel16(int x, int y) {
    if(!isSprite()) {
         cout << "Failed to get pixel, Sprite not initialized!"<< endl;
         return 0;
     }
    Uint16* pixels = (Uint16*)sprite->pixels;
    return pixels[y * sprite->w + x];
}

void Sprite::setPixel16(int x, int y, Uint16 pixel) {
    if(!isSprite()) {
         cout << "Failed to set pixel, Sprite not initialized!"<< endl;
         return;
     }
    Uint16* pixels = (Uint16*)sprite->pixels;
    pixels[y * sprite->w + x] = pixel;
}

Uint32 Sprite::getPixel32(int x, int y) {
    if(!isSprite()) {
         cout << "Failed to get pixel, Sprite not initialized!"<< endl;
         return 0;
     }
    Uint32* pixels = (Uint32*)sprite->pixels;
    return pixels[y * sprite->w + x];
}

void Sprite::setPixel32(int x, int y, Uint32 pixel) {
    if(!isSprite()) {
         cout << "Failed to set pixel, Sprite not initialized!"<< endl;
         return;
     }
    Uint32* pixels = (Uint32*)sprite->pixels;
    pixels[y * sprite->w + x] = pixel;
}


int Sprite::getFrame() {
    return index;
}

int Sprite::getFrameWidth() { // width of each frame
    return width;
}

int Sprite::getFrameHeight() { // height of each frame, in this implementation it is the sprites actual height
    return height;
}

int Sprite::getWidth() { // this is most likely the width a user will need to know
    return getFrameWidth();
}

int Sprite::getSpriteWidth() { // sprites Actual width
    return sprite->w;
}

int Sprite::getHeight() { // in this Class the frame height is the same as the sprites Actual height
    return sprite->h;
}

int Sprite::getSpriteHeight() { // sprites Actual height
    return sprite->h;
}

bool Sprite::equals(Sprite cmp) {
     if(sprite == cmp.getSurface()) {
         return true;
     }
     return false;
}

SDL_Surface* Sprite::getSurface() {
     return sprite;
}

void Sprite::setSurface(SDL_Surface* surface) {
    sprite = surface;
}

void Sprite::destroy() {
 SDL_FreeSurface(sprite);
}
