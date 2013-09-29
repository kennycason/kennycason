/* 
 * File:   Pokedex.h
 * Author: Kenny Cason
 * Created on March 2, 2009
 */
#ifndef _POKEDEX_H
#define	_POKEDEX_H

#ifdef __cplusplus
    #include <cstdlib>
#else
    #include <stdlib.h>
#endif

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class Pokedex {
public:
    Pokedex();
    Pokedex(int PokedexNumber,
            int PokedexImage, int BattleImageOpponent, int BattleImageSelf,
             string Name, string JapanName,
             int Height, int Weight,
             int Type1, int Type2,
             int BaseHP, int BaseAttack, int BaseDefense,
             int BaseSpecialAttack, int BaseSpecialDefense, int BaseSpeed);
    ~Pokedex();

    int PokedexNumber;
    int PokedexImage;
    int BattleImageOpponent;
    int BattleImageSelf;
    string Name;
    string JapanName;
    int Height;
    int Weight;
    int Type1;
    int Type2;
    int BaseHP;
    int BaseAttack;
    int BaseDefense;
    int BaseSpecialAttack;
    int BaseSpecialDefense;
    int BaseSpeed;
private:

};
#endif	/* _POKEDEX_H */

