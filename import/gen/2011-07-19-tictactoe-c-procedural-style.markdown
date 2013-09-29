---
title: TicTacToe - C (procedural style)
author: Kenny Cason
tags: c++, computer, game, programming
---

This is a simple version of TicTacToe written in C. <br/>
I wrote this primarily to help a friend of mine. It has virtually no error checking (primarily in regards to the scanf functions, so don't mess up while inputting!)

<pre lang="c" line="0">
#include <stdio.h>

char board[3][3];

char turn = 'X';

void init(char board[3][3]);
void draw(char board[3][3]);
void move(char board[3][3], char turn);
int isGameOver(char board[3][3]);


int main() {
    init(board);
    while(!isGameOver(board)) {
        draw(board);
        move(board, turn);
        // change turn
        if(turn == 'X') {
            turn = 'O';
        } else {
            turn = 'X';
        }
    }
}

void init(char board[3][3]) {
    int x, y;
    for(y = 0; y < 3; y++) {
        for(x = 0; x < 3; x++) {
            board[x][y] = ' ';
        }
    }
}

void move(char board[3][3], char turn) {
    int x, y, moved = 0;
    printf("%c's Turn:\n", turn);
    while(moved == 0) {
        printf("Input column (0-2):\n");
        scanf("%d", &x);
        printf("Input row (0-2):\n");
        scanf("%d", &y);
        if(x >= 0 && x <= 2 && y >= 0 && y <= 2) {
            if(board[x][y] == ' ') {
                board[x][y] = turn;
                moved = 1;
            } else {
                printf("You can not go there!\n");
            }
        } else {
            printf("Row or Column invalid!\n");
        }
    }
}

int isGameOver(char board[3][3]) {
    int x, y;
    // check rows
    for(y = 0; y < 3; y++) {
        if(board[0][y] != ' ') {
            if(board[0][y] == board[1][y] && board[1][y] == board[2][y]) {
                printf("Player %c Wins!\n", board[0][y]);
                return 1;
            }
        }
    }
    // check columns
    for(x = 0; x < 3;x++) {
        if(board[x][0] != ' ') {
            if(board[x][0] == board[x][1] && board[x][1] == board[x][2]) {
                printf("Player %c Wins!\n", board[x][0]);
                return 1;
            }
        }
    }
    // check diagonals
    if(board[0][0] != ' ') {
        if(board[x][0] == board[1][1] && board[1][1] == board[2][2]) {
            printf("Player %c Wins!\n", board[0][0]);
            return 1;
        }
    }
    if(board[2][0] != ' ') {
        if(board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
            printf("Player %c Wins!\n", board[2][0]);
            return 1;
        }
    }
    // check cat game
    if(board[0][0] != ' ' && board[1][0] != ' ' && board[2][0] != ' ' &&
       board[0][1] != ' ' && board[1][1] != ' ' && board[2][1] != ' ' &&
       board[0][2] != ' ' && board[1][2] != ' ' && board[2][2] != ' ') {
        printf("Cat Game!");
        return 1;
    }
    // the game is still going
    return 0;
}

void draw(char board[3][3]) {
    int x, y;
    for(y = 0; y < 3; y++) {
        for(x = 0; x < 3; x++) {
            printf("%c", board[x][y]);
            if(x < 2) {
                printf("|");
            }
        }
        printf("\n");
        if(y < 2) {
            printf("-----\n");
        }
    }
}

```