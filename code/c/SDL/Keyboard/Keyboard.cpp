#include "Keyboard.h"



Keyboard::Keyboard() {
    clear();
}

Keyboard::~Keyboard() {

}

void Keyboard::listen() {
    SDL_Event event;
    while (SDL_PollEvent(&event)) {
        switch (event.type) {
            case SDL_KEYDOWN:
                keys[event.key.keysym.sym] = true;
                keysPressedTime[event.key.keysym.sym] = SDL_GetTicks();
                break;
            case SDL_KEYUP:
                keys[event.key.keysym.sym] = false;
                keysPressedTime[event.key.keysym.sym] = 0;
                break;
        }
    }
}

bool Keyboard::keyDown(int key) {
    if(keys[key]) {
        return true;
    }
    return false;
}

void Keyboard::clear() {
    for(int i = 0; i < KEYBOARD_MAX_KEYS; i++ ) {
        keys[i] = false;
        keysPressedTime[i]= 0;
    }
}

bool Keyboard::isCombo(std::vector<int> keys) {
    for(Uint16 i = 0; i < keys.size(); i++) {
        if(!keyDown(keys[i])) {
            return false;
        }
    }
    return true;
}

bool Keyboard::isSequentialCombo(std::vector<int> keys) {
    Uint32 lastKeyTime = 0;
    for(Uint16 i = 0; i < keys.size(); i++) {
        if(!(keyDown(keys[i]) && keysPressedTime[keys[i]] > lastKeyTime)) {
            return false;
        }
        lastKeyTime = keysPressedTime[keys[i]];
    }
    return true;
}
