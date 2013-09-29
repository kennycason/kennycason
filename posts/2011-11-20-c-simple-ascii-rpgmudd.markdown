---
title: C - Simple ASCII RPG/MUDD
author: Kenny Cason
tags: ASCII, C++, MUDD, RPG
---

This is a very simple RPG that uses ASCII art. It's not balanced nor complete, just meant to serve as a tutorial of some procedural coding in C.


```c
#include <iostream>
#include <string>
#include <cstdlib>

using namespace std;

// function declarations
void newGame();
void initPlayer();
void printPlayer();
void initArea();
void generateRandomEnemies();
void printArea();
void printMenu();
void getPlayerInput();
bool isAlive();
int isEnemy();
void fight(struct Enemy& enemy);
void gameOver();
void levelUp();
int dice(int sides);


// common structures
struct Location {
    int x;      // the x position in the area
    int y;      // the y position in the area
};

struct Weapon {
    string name;    // name of weapon
    int damage;  // max damage
    int crit;   // critical hit range (1 to 20)
};

struct Armor {
    string name;    // name of armor
    int defence;  // defense modifier
};

struct Shield {
    string name;    // name of armor
    int defence;  // defense modifier
};

struct Player {
    string name;// name of player
    int job;    // job (class) of the player

    int hp;     // current hp
    int hpm;    // hp max

    int str;    // strength
    int dex;    // dexterity
    int con;    // constitution
    int iq;     // intelligence
    int wis;    // widsom
    int cha;    // charisma

    int bab;    // base attack bonus
    int fort;   // fortitude
    int reflex; // reflex
    int will;   // will

    int ac;     // armor class

    int level;  // level
    int xp;     // current xp
    int gold;   // amount of gold

    struct Weapon weapon;
    struct Armor armor;
    struct Shield shield;

    struct Location location;
};

struct Enemy {
    string name;// enemy name
    int hp;     // current hp
    int str;    // strength
    int dex;    // dexterity
    int ac;     // armor class
    int gold;   // amount of gold enemy is carrying
    struct Location location;
};


// create a simple area default 30x30
int AREA_WIDTH = 20;
int AREA_HEIGHT = 12;
int** area; // 2 dimensional array for the area

const int AREA_WALKABLE = 0;
const int AREA_WALL = 1;

// default player structure
Player player;

// default array of enemies (for now lets assume 10 enemies per area)
Enemy* enemies;
const int ENEMIES_MAX_NUM = 100;

// main function
int main() {
    srand(time(0)); // generate new random seed (for use with rand() in the dice() function)
    newGame();

    while(isAlive()) {
        printArea();
        printMenu();
        getPlayerInput();

        int enemyIndex = isEnemy();
        if(enemyIndex> -1) {
            fight(enemies[enemyIndex]);
        }
    }

    gameOver();
    return 0;
}

// function definitions
bool isAlive() {
    if(player.hp > 0) {
        return true;
    }
    return false;
}


void gameOver() {
    cout << "Thou Art Dead" << endl;
}


void newGame() {
    initArea();
    generateRandomEnemies();
    initPlayer();
    printPlayer();
}

void initArea() {
    area = new int*[AREA_WIDTH];
    for(int x = 0; x < AREA_WIDTH; ++x) {
         area[x] = new int[AREA_HEIGHT];
         for(int y = 0; y < AREA_HEIGHT; ++y) { // initialize the values, this is optional, but recommended
             if(dice(4) >= 2) {
                area[x][y] =  AREA_WALKABLE;
             } else {
                area[x][y] =  AREA_WALL;
             }
         }
    }
}

void generateRandomEnemies() {
    enemies = new Enemy[ENEMIES_MAX_NUM];
    for(int i = 0; i < ENEMIES_MAX_NUM; i++) {
        enemies[i].name = "slime";
        enemies[i].hp = 4 + dice(4);
        enemies[i].str = 12 + dice(4);
        enemies[i].dex = 10 + dice(4);
        enemies[i].ac = 10 + dice(4);
        enemies[i].gold = dice(10);
        enemies[i].location.x = dice(AREA_WIDTH) - 1;
        enemies[i].location.y = dice(AREA_HEIGHT) - 1;
    }
}

void initPlayer() {
    player.name = "Kenny";
    player.hpm = 20 + dice(10);
    player.hp = player.hpm;
    player.str = 8 + dice(6);
    player.dex =  8 + dice(6);
    player.con =  8 + dice(6);
    player.iq  =  8 + dice(6);
    player.wis =  8 + dice(6);
    player.cha =  8 + dice(6);

    player.bab = 0;
    player.fort = dice(4);
    player.reflex = dice(4);
    player.will = dice(4);

    player.ac = 10;
    player.level = 1;
    player.xp = 0;
    player.gold = 30 + dice(100);

    player.weapon.name = "Wood Sword";
    player.weapon.damage = 4;
    player.weapon.crit = 19;

    player.armor.name = "Leather";
    player.armor.defence = 1;

    player.shield.name = "Wood";
    player.shield.defence = 1;

    player.location.x = 5;
    player.location.y = 5;

}

void printPlayer() {
    cout << player.name << endl;
    cout << "HP " << player.hp << "/" << player.hpm << endl;
    cout << "XP " << player.xp << "\t Lvl " << player.level << endl;
    cout << "STR " << player.str << "\t INT " << player.iq << endl;
    cout << "DEX " << player.dex << "\t WIS " << player.wis << endl;
    cout << "CON " << player.con << "\t CHA " << player.cha << endl;
    cout << "BAB +" << player.bab << "\tAC " << player.ac << endl;
    cout << "FORT +" << player.fort << "\tREFLEX +" << player.reflex << "\tWILL +" << player.will << endl;
    cout << "GOLD " << player.gold << endl;
    cout << "WEAPON " << player.weapon.name << ": 1D" << player.weapon.damage << " crit (" << player.weapon.crit << "-20) x2" << endl;
    cout << "ARMOR " << player.armor.name << ": +" << player.armor.defence << endl;
    cout << "SHIELD " << player.shield.name << ": +" << player.shield.defence << endl;
}


void printArea() {
    for(int y = 0; y < AREA_HEIGHT; ++y) {
        for(int x = 0; x < AREA_WIDTH; ++x) {
            if(player.location.x == x && player.location.y == y) {
                cout << "@";
            } else {
                if(area[x][y] == AREA_WALKABLE) {
                    cout << " ";
                } else if(area[x][y] == AREA_WALL) {
                    cout << "#";
                }
            }

        }
        cout << endl;
    }
}

void printMenu() {
    cout << "Commands" << endl;
    cout << "L - move left\tR - move right" << endl;
    cout << "U - move up\tD - move down" << endl;
    cout << "S - print stats\tH - Heal (30gp)" << endl;
    cout << "X - exit game" << endl;
    cout << ">";
}

void getPlayerInput() {
    char input;
    cin >> input;
    cout << endl; // go to the next line
    if(input == 'U' || input == 'u') { // move up
        if(player.location.y - 1 >= 0
                    && area[player.location.x][player.location.y - 1] != AREA_WALL) {
            player.location.y--;
        } else {
            cout << "Can not move there!" << endl;
        }
    } else if(input == 'D' || input == 'd') { // move down
        if(player.location.y + 1 < AREA_HEIGHT
                    && area[player.location.x][player.location.y + 1] != AREA_WALL) {
            player.location.y++;
        } else {
            cout << "Can not move there!" << endl;
        }
    } else if(input == 'L' || input == 'l') { // move left
        if(player.location.x - 1 >= 0
                    && area[player.location.x - 1][player.location.y] != AREA_WALL) {
            player.location.x--;
        } else {
            cout << "Can not move there!" << endl;
        }
    } else if(input == 'R' || input == 'r') { // move right
        if(player.location.x + 1 < AREA_WIDTH
                    && area[player.location.x + 1][player.location.y] != AREA_WALL) {
            player.location.x++;
        } else {
            cout << "Can not move there!" << endl;
        }
    } else if(input == 'X' || input == 'x') {
        gameOver();
    } else if(input == 'S' || input == 's') {
        printPlayer();
    } else if(input == 'H' || input == 'h') {
        if(player.gold >= 30) {
            player.gold -= 30;
            int heal = dice(20);
            cout << "Healed " << heal << "hp" << endl;
            player.hp += heal;
            if(player.hp > player.hpm) {
                player.hp = player.hpm;
            }
        } else {
            cout << "Thou does not have enough gold to pay healing potion!" << endl;
        }
    }
}

int isEnemy() {
    for(int i = 0; i < ENEMIES_MAX_NUM; i++) {
        if(enemies[i].location.x == player.location.x &&
                    enemies[i].location.y == player.location.y) {
            return i; // the index of the enemy array
        }
    }
    return -1; // no enemy
}

void fight(struct Enemy& enemy) {
    cout << "You just got in a fight with " << enemy.name << endl;
    bool turn = 0; // enemies turn
    if(dice(20) + (player.dex-10)/2 > dice(20) + (enemy.dex-10)/2) {
        turn = 1;
    }
    while(true) {
        if(player.hp <= 0) {
            cout << "Thou has been slain by " << enemy.name << endl;
            break;
        }
        if(enemy.hp <= 0) {
            cout << "Thou has killed " << enemy.name << endl;
            break;
        }

        if(turn) { // players turn
            cout << "It is thou turn" << endl;
            int attackRoll = dice(20);
            if(attackRoll + player.bab + (player.str-10)/2 >= enemy.ac) { // hit
                int damage = dice(player.weapon.damage) + (player.str-10)/2;
                if(attackRoll >= player.weapon.crit) {
                    cout << "Critical hit!" << endl;
                    damage *= 2;
                }
                cout << "Attacked " << enemy.name << " for " << damage << " damage!" << endl;
                enemy.hp -= damage;
            } else {
                cout << "Attack missed!" << endl;
            }
            turn = 0;
        } else {
            cout << enemy.name << "'s attack!" << endl;
            int attackRoll = dice(20);
            if(attackRoll + (enemy.str-10)/2 > player.ac + player.armor.defence + player.shield.defence) { // hit
                int damage = (enemy.str-10)/2;
                if(attackRoll >= 19) {
                    cout << "Critical hit!" << endl;
                    damage *= 2;
                }
                cout << "Thou received " << damage << " damage!" << endl;
                player.hp -= damage;
            } else {
                cout << "Attack missed!" << endl;
            }
            turn = 1;
        }
    }

    if(player.hp > 0) {
        player.xp += 2 + dice(4);
        player.gold += enemy.gold;
        levelUp();
    }
    // revive the dead enemy
    enemy.hp = 4 + dice(6);
}

void levelUp() {
    bool levelup = false;
    if(player.xp > 16 && player.level == 1) {
        levelup = true;
    } else if(player.xp > 40 && player.level == 2) {
        levelup = true;
    } else if(player.xp > 75 && player.level == 3) {
        levelup = true;
    } else if(player.xp > 120 && player.level == 4) {
        levelup = true;
    } else if(player.xp > 170 && player.level == 5) {
        levelup = true;
    } else if(player.xp > 230 && player.level == 6) {
        levelup = true;
    } else if(player.xp > 300 && player.level == 7) {
        levelup = true;
    } else if(player.xp > 400 && player.level == 8) {
        levelup = true;
    } else if(player.xp > 550 && player.level == 9) {
        levelup = true;
    }
    if(levelup) {
        player.level++;
        player.hpm += dice(8) + (player.con-10)/2;
        player.hp = player.hpm;
        player.str += dice(2);
        player.dex += dice(2);
        player.con += dice(2);
        player.iq += dice(2);
        player.wis += dice(2);
        player.cha += dice(2);
        if(player.level % 3 == 0) {
            player.bab += dice(2);
        }
        cout << "Thou has leveled up!" << endl;
        if(player.level == 10) {
            cout << "Congratulations! Thou has reached max level!" << endl;
        }
        printPlayer();
    }
}


int dice(int sides) {
    int value = rand()%sides+1;
    return value;
}

```