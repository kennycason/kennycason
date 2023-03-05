---
title: John Conway's Game of Life - ChatGPT
author: Kenny Cason
tags: chatgpt, cellular automata
---



### ChatGPT Prompt

> Using the programming language PROCESSING (java mode), write john conway's game of life and then simplify the code and remove code comments

<img src="https://github.com/kennycason/chatgpt/blob/main/output/game_of_life_running.png?raw=true" width="100%" />

The code worked after minor edits and asking ChatGPT to write specific functions separately due to the length of the program.
This surprisingly worked quite well, and I only needed reference ChatGPT in order to write this program.
Additionally, this is my first project to use Processing. (I have since learned you can just tell ChatGPT to "Continue on".)

[Video](https://v.usetapes.com/h4XFbxRH9F)



### ChatGPT Dialogue

<img src="https://github.com/kennycason/chatgpt/blob/main/output/game_of_life01.png?raw=true" width="50%"/><img src="https://github.com/kennycason/chatgpt/blob/main/output/game_of_life02.png?raw=true" width="50%"/><img src="https://github.com/kennycason/chatgpt/blob/main/output/game_of_life03.png?raw=true" width="50%"/><img src="https://github.com/kennycason/chatgpt/blob/main/output/game_of_life04.png?raw=true" width="50%"/>



### The Code

```python
int rows = 50;
int cols = 50;
boolean[][] grid = new boolean[rows][cols];

void setup() {
    size(500, 500);
    randomizeGrid();
    background(0);
    frameRate(10);
}

void draw() {
    background(0);
    fill(255);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int x = i * width/rows;
            int y = j * height/cols;
            if (grid[i][j]) {
                rect(x, y, width/rows, height/cols);
            }
        }
    }
    boolean[][] next = new boolean[rows][cols];
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            int neighbors = countNeighbors(grid, i, j);
            if (grid[i][j]) {
                next[i][j] = neighbors == 2 || neighbors == 3;
            } else {
                next[i][j] = neighbors == 3;
            }
        }
    }
    grid = next;
}

# I then asked: Using the programming language PROCESSING (java mode), write the john conway's game of life and
# then simplify the code and remove code comments and only show the countNeighbors function
int countNeighbors(boolean[][] grid, int x, int y) {
    int sum = 0;
    for (int i = -1; i < 2; i++) {
        for (int j = -1; j < 2; j++) {
            int col = (x + i + cols) % cols;
            int row = (y + j + rows) % rows;
            sum += grid[col][row] ? 1 : 0;
        }
    }
    sum -= grid[x][y] ? 1 : 0;
    return sum;
}

# Using the programming language PROCESSING (java mode), write the john conway's game of life and
# then show a function to randomize the grid
void randomizeGrid() {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            grid[i][j] = random(2) < 1;
        }
    }
}
```