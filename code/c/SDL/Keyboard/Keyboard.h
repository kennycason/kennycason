#ifndef __SDL__KEYBOARD__H__
#define __SDL__KEYBOARD__H__

#include <SDL/SDL.h>
#include <iostream>
#include <vector>

#define KEYBOARD_MAX_KEYS 322

class Keyboard {
public:
    Keyboard();
    virtual ~Keyboard();
    void listen();
    bool keyDown(int key);
    bool isCombo(std::vector<int> keys);
    bool isSequentialCombo(std::vector<int> keys);
    void clear();

private:
    bool keys[KEYBOARD_MAX_KEYS];  // 322 is the number of SDLK_DOWN events, 0 for not pressed, 1 for pressed
    Uint32 keysPressedTime[KEYBOARD_MAX_KEYS];  // the time each key was pressed

};


#endif

