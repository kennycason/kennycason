---
title: BrainF*** (Programming Language) – minor varient
author: Kenny Cason
tags: C++, Programming
---

Earlier this year I was playing around with making simple programming languages and came across <a href="http://en.wikipedia.org/wiki/Brainfuck" target="_blank">"BrainFuck"</a>. I, being bored decided to write my own BrainFuck Interpreter and of course made a few changes along the way (only in I/O functions, added integer support since BrainFuck only deals with characters). If interested, I recommend reading more information about BrainFuck on Wikipedia.

The Full download including Source and examples can be found <a href="http://ken-soft.com/code/c/bf/bf.zip" target="_blank">HERE</a>.

Here are the basic rules of BrainFuck with some added I/O Functions.<br/>
<table class="table" border="1">
<tbody><tr>
<th align="center">brainfuck command</th>
<th align="left">C equivalent</th>
</tr><tr>
<td style="text-align: center;">&gt;</td>
<td>++CP;</td>
</tr><tr>
<td style="text-align: center;">&lt;</td>
<td>--CP;</td>
</tr><tr>
<td style="text-align: center;">+</td>
<td>++*CP;</td>
</tr>
<tr>
<td style="text-align: center;">-</td>
<td>--*CP;</td>
</tr><tr>
<td style="text-align: center;">[</td>
<td>while (*CP) {</td>
</tr><tr>
<td style="text-align: center;">]</td>
<td>}</td>
</tr><tr>
<td style="text-align: center;">c</td>
<td>printf("%c",(char)*CP)</td>
</tr><tr>
<td style="text-align: center;">d</td>
<td>printf("%u",*CP);</td>
</tr><tr>
<td style="text-align: center;">C</td>
<td>while (scanf("%c", CP) != 1);</td>
</tr><tr>
<td style="text-align: center;">D</td>
<td>while (scanf("%u", CP) != 1);</td>
</tr><tr>
<td style="text-align: center;">M</td>
<td>Print out Cell and Pointers</td>
</tr><tr>
<td style="text-align: center;">I</td>
<td>Print out Instruction Pointer</td>
</tr>
</tbody></table>
<br/>
<b>CP</b> is the Cell Pointer<br/>
<b>Below are various scripts written in BrainFuck<b/>
<a href="http://ken-soft.com/code/c/bf/helloworld.bf" target="_blank">Hello World</a><br/>
<pre lang="bf" line="1">
+++++ +++++
[
    > +++++ ++
    > +++++ +++++
    > +++
    > +

    <<<< -
]
> ++ c
> + c
+++++ ++ c
c
+++ c
> ++ c
<< +++++ +++++ +++++ c
> c
+++ c
----- - c
----- --- c
> + c
> c
M

```

<a href="http://ken-soft.com/code/c/bf/helloworld2.bf" target="_blank">Hello World Version 2</a><br/>
<pre lang="bf" line="1">
+++++
[
    > +++++ +++++ +++
    < -
]
+++++ +++++
[
    >> +++++ ++++
    << -
]
>> +++++ ++

< +++++ ++ c ----- --
> ++++ c  +++++ ++ cc +++ c ----- ----- ----

<< +++
[
    >>> +++++ +++++
    <<< -
]
>>> ++ c

<< +++++ +++++ +++++ +++++ ++ c ----- ----- ----- ----- --
> +++++ +++++ ++++ c +++ c ------ c -------- c ---
> + c

M

```

<a href="http://ken-soft.com/code/c/bf/nestedloop.bf" target="_blank">3 Nested While loops</a><br/>
<pre lang="bf" line="1">
+++
M
[
    > +++
    [
        > +++
        [
            -
            M
        ]
        < -
    ]
    < -
]
M

```

<b>Below is the source for the BrainFuck Interpreter</b>

```c
#include <stdio.h>
#include <stdlib.h>

#define DEFAULT_IN_FILE "in.bf"
#define DEFAULT_MEMORY_SIZE 8

char inFile[256] = DEFAULT_IN_FILE;

/* function definitions */
void runInstructions();
int loadFile(char inFile[256]);
void printInstructions();
void clearDataBuffer();
void printDataBuffer();

unsigned int* cell; /* cell buffer, all program cell is stored in here */
unsigned int cellSize = 0;

unsigned int IP; // instruction pointer

unsigned char* instructions; /* the Instructions loaded from input file */
unsigned int instructionsSize = 0;

unsigned int* CP = NULL; /* cell Pointer */


int main(int argc, char *argv[]) {
    int i;
    for(i = 1; i < argc; i++) { /* handle parameters */
        if(strcmp(argv[i], "-i") == 0) { /* input file */
            if( i + 1 < argc) {
                sprintf(inFile, "%s", argv[i + 1]);
            } else {  /* improper format */
                return -1;
            }
        } else if(strcmp(argv[i], "-size") == 0) { /* size of cell buffer */
            if( i + 1 < argc) {
                cell = (unsigned int*) malloc( atoi(argv[i + 1]) * sizeof(unsigned int) );
                cellSize = atoi(argv[i + 1]);
            } else {  /* improper format */
                return -1;
            }
        }
    }
    if(cellSize == 0) {
        cell = (unsigned int*) malloc( DEFAULT_MEMORY_SIZE * sizeof(unsigned int) );
        cellSize = DEFAULT_MEMORY_SIZE;
    }
    CP = cell;

    loadFile(inFile);
    clearDataBuffer();
    runInstructions();
    return 0;
}

void runInstructions() {
    int i; // used in for loops
    int numCloseBrackets = 0;
    int numOpenBrackets = 0;

    for(IP = 0; IP < instructionsSize; IP++) {
        switch(instructions[IP]) {
            case '>': /* INC/DEC */
                ++CP;

                break;
            case '<':
                --CP;
                break;
            case '+': /* INC/DEC POINTER */
                ++(*CP);
                break;
            case '-':
                --(*CP);
                break;
            case '[': /* WHILE LOOP */
                if(*CP == 0) { // if CP == 0, then skip to the next matching ']'
                    numOpenBrackets = 0;
                    for(i = IP + 1; i < instructionsSize; i++) {
                        if(instructions[i] == '[') {
                            numOpenBrackets++;
                        } else if(instructions[i] == ']') {
                            if(numOpenBrackets == 0) {
                                IP = i;
                                break;
                            } else {
                                numOpenBrackets--;
                            }
                        }
                    }
                }
                break;
            case ']': /* END WHILE*/
                if(*CP != 0) { // if CP != 0, loopback to the previous matching'['
                    numCloseBrackets = 0;
                    for(i = IP - 1; i >= 0; i--) {
                        if(instructions[i] == ']') {
                            numCloseBrackets++;
                        }else if(instructions[i] == '[') {
                            if(numCloseBrackets == 0) {
                                IP = i;
                                break;
                            } else {
                                numCloseBrackets--;
                            }
                        }
                    }
                }
                break;
            case 'c':  /* OUTPUT */
                printf("%c",(char)*CP);
                break;
            case 'd':
                printf("%u",*CP);
                break;
            case 'C': /* INPUT */
                while (scanf("%c", CP) != 1);
                break;
            case 'D':
                while (scanf("%u", CP) != 1);
                break;
            case 'M': /* print Data Buffer */
                printDataBuffer();
                break;
            case 'I': /* print Instructions as is*/
                printInstructions();
                break;
            default:
                /* unknown command, ignore it */
                break;
        }
    }
}


/*
 *
 */
int loadFile(char inFile[256]) {
    FILE *fp;
    fp=fopen(inFile, "r");
    if(fp == NULL) {
        return -1;
    }
    fseek(fp, 0, SEEK_END); // seek to end of file
    instructionsSize = ftell(fp); // get current file pointer
    rewind (fp);
    instructions = (char*) malloc( instructionsSize );
    int i;
    for(i = 0; i < instructionsSize; i++) {
        instructions[i] = ' ';
    }
    if(instructions == NULL) {
        return -1;
    }
    fread (instructions,1,instructionsSize,fp);
    fclose(fp);
    return 0;
}

void printInstructions() {
    int i;
    for(i = 0; i < instructionsSize; i++) {
        printf("%c", instructions[i]);
    }
    printf("\n");
}

void clearDataBuffer() {
    int i;
    for(i = 0; i < cellSize; i++) {
        cell[i] = 0;
    }
}

void printDataBuffer() {
    int i;
    printf("\n");
    for(i = 0; i < cellSize; i++) {
        printf("[%d]=%d", i, cell[i]);
        if(CP == &cell[i]) {
            printf(" <-- (CP)");
        }
        printf("\n");
    }
    printf("\nIP = %d\n", IP);
}

```
