#ifdef __cplusplus
    #include <cstdlib>
#else
    #include <stdlib.h>
#endif

#include "Pokedex.h"
#include <iostream>
#include <fstream>
#include <string>

Pokedex::Pokedex() {
    this->PokedexNumber = 0;
    this->PokedexImage = 0;
    this->BattleImageOpponent = 0;
    this->BattleImageSelf = 0;
    this->Name = "";
    this->JapanName = "";
    this->Height = 0;
    this->Weight = 0;
    this->Type1 = 0;
    this->Type2 = 0;
    this->BaseHP = 0;
    this->BaseAttack = 0;
    this->BaseDefense = 0;
    this->BaseSpecialAttack = 0;
    this->BaseSpecialDefense = 0;
    this->BaseSpeed = 0;
}

Pokedex::Pokedex(int PokedexNumber,
             int PokedexImage, int BattleImageOpponent, int BattleImageSelf,
             string Name, string JapanName,
             int Height, int Weight,
             int Type1, int Type2,
             int BaseHP, int BaseAttack, int BaseDefense,
             int BaseSpecialAttack, int BaseSpecialDefense, int BaseSpeed) {
    this->PokedexNumber = PokedexNumber;
    this->PokedexImage = PokedexImage;
    this->BattleImageOpponent = BattleImageOpponent;
    this->BattleImageSelf = BattleImageSelf;
    this->Name = Name;
    this->JapanName = JapanName;
    this->Height = Height;
    this->Weight = Weight;
    this->Type1 = Type1;
    this->Type2 = Type2;
    this->BaseHP = BaseHP;
    this->BaseAttack = BaseAttack;
    this->BaseDefense = BaseDefense;
    this->BaseSpecialAttack = BaseSpecialAttack;
    this->BaseSpecialDefense = BaseSpecialDefense;
    this->BaseSpeed = BaseSpeed;
}

Pokedex::~Pokedex() {

}