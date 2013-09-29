/*
 * File:   Pokemon.h
 * Author: Kenny Cason
 *
 * Created on March 2, 2009
 */
#ifndef _POKEMON_H
#define	_POKEMON_H

#ifdef __cplusplus
    #include <cstdlib>
#else
    #include <stdlib.h>
#endif

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class Pokemon {
public:
    Pokemon();
    Pokemon(int PokedexNumber,
            int PokedexImage, int BattleImageOpponent, int BattleImageSelf,
             string Name, string JapanName,
             int Height, int Weight,
             int Type1, int Type2,
             int BaseHP, int BaseAttack, int BaseDefense,
             int BaseSpecialAttack, int BaseSpecialDefense, int BaseSpeed);
    ~Pokemon();

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
#endif	/* _POKEMON_H */

