---
title: C++ Dynamic Memory Allocation (2D and 3D)
author: Kenny Cason
tags: allocation, C++, Development, memory, Programming
---

This snipped of code is for allocating memory when using a multidimensional array. The following examples are for 2D ([][]) and 3D ([][][]) structures.

First:
to allocate memory for a two dimensional structure: (i.e. int[][] array)

```c
int** array;    // 2D array definition;
// begin memory allocation
array = new int*[dimX];
for(int x = 0; x < dimX; ++x) {
     array[x] = new int[dimY];
     for(int y = 0; y < dimY; ++y) { // initialize the values, this is optional, but recommended
          array[x][y] = 0;
     }
}

```

Next, to allocate memory for a three dimensional structure: (i.e. int[][][] array)

```c
int*** array;    // 3D array definition;
// begin memory allocation
array = new int**[dimX];
for(int x = 0; x < dimX; ++x) {
    array[x] = new int*[dimY];
    for(int y = 0; y < dimY; ++y) {
        array[x][y] = new int[dimZ];
        for(int z = 0; z < dimZ; ++z) { // initialize the values, again, not necessary, but recommended
            array[x][y][z] = 0;
        }
    }
}

```