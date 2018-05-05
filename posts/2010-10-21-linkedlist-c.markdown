---
title: LinkedList - C
author: Kenny Cason
tags: data structure, c
---

This is a simple Doubly connected LinkedList written in C. It hasn't been thoroughly tested, but should be fun to play around with :)

<a target="_blank" href="/code/c/ll/linkedlist.h">linkedlist.h</a><br/>
<a target="_blank" href="/code/c/ll/main.c">main.c</a><br/>

<b>linkedlist.h</b>

```cpp
#ifndef __C__LINKEDLIST__H__
#define __C__LINKEDLIST__H__

typedef struct {
    unsigned int value;
} NodeData;

 typedef struct Node {
    NodeData data;
    struct Node* next;
    struct Node* prev;
} Node;

typedef struct {
    unsigned int size;
    Node* head;
    Node* tail;
} LinkedList;

void ll_init(LinkedList* ll);       /* init linked list */
void ll_destroy(LinkedList* ll);    /* free all the memory */

void ll_addFirst(LinkedList* ll, NodeData data);    /* prepend data to list O(1) */
void ll_addLast(LinkedList* ll, NodeData data);     /* append data to list O(1) */
void ll_add(LinkedList* ll, unsigned int i, NodeData data);     /* insert data into list O(n) */

void ll_set(LinkedList* ll, unsigned int i, NodeData data);     /* set data in list O(n) */
void ll_setFirst(LinkedList* ll, NodeData data);    /* set first data in list O(1) */
void ll_setLast(LinkedList* ll, NodeData data);     /* set last data in list O(1) */

Node* ll_get(LinkedList* ll, unsigned int i);    /* get data in list O(n) */
Node* ll_getFirst(LinkedList* ll);    /* get first data in list O(1) */
Node* ll_getLast(LinkedList* ll);     /* get last data in list O(1) */

Node* ll_remove(LinkedList* ll, unsigned int i);    /* remove data from list O(n) */
void ll_clear(LinkedList* ll);  /* clear the list O(n) */
int ll_isEmpty(LinkedList* ll); /* return 1 if list is empty, else 0 */
int ll_size(LinkedList* ll);    /* return the number of elements in the list */


void ll_init(LinkedList* ll) {
    NodeData data;
    data.value = 0;

    Node* head = (Node*)malloc(sizeof(Node));
    head->next = NULL;
    head->prev = NULL;

    ll->head = head;
    ll->tail = head;

    ll->size = 0;
}

Node* ll_getHead(LinkedList* ll) {
    return ll->head;
}

Node* ll_getTail(LinkedList* ll) {
    return ll->tail;
}

int ll_isEmpty(LinkedList* ll) {
    return ll->size == 0;
}

int ll_size(LinkedList* ll) {
    return ll->size;
}

void ll_addLast(LinkedList* ll, NodeData data) {
    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->data = data;

    newNode->prev = ll->tail;
    ll->tail->next = newNode;

    newNode->next = NULL;
    ll->tail = newNode;
    ll->size++;
}

void ll_addFirst(LinkedList* ll, NodeData data) {
    ll_add(ll, 0, data); /* ll_add() is O(n), however when passed 0, behaves as O(1) */
}


void ll_add(LinkedList* ll, unsigned int i,  NodeData data) {
    int j;
    Node* n = ll->head;
    for(j = 0; j < i && j < ll->size; j++) {
        n = n->next;
    }

    Node* next = n->next; /* node after n */

    Node* newNode = (Node*)malloc(sizeof(Node));
    newNode->data = data;

    n->next = newNode;
    newNode->prev = n;

    if(next != NULL) {
        next->prev = newNode;
        newNode->next = next;
    } else { /* the node deleted was the last node, update last */
        ll->tail = newNode;
        newNode->next = NULL;
    }
    ll->size++;
}

void ll_set(LinkedList* ll, unsigned int i,  NodeData data) {
    int j;
    Node* n = ll->head->next;
    for(j = 0; j < i && j < ll->size; j++) {
        n = n->next;
    }
    n->data = data;
}

void ll_setFirst(LinkedList* ll, NodeData data) {
    ll->head->data = data;
}

void ll_setLast(LinkedList* ll, NodeData data) {
    ll->tail->data = data;
}

Node* ll_get(LinkedList* ll, unsigned int i) {
    int j;
    Node* n = ll->head->next;
    for(j = 0; j < i && j < ll->size; j++) {
        n = n->next;
    }
    return n;
}

Node* ll_getFirst(LinkedList* ll) {
    return ll->head->next;
}

Node* ll_getLast(LinkedList* ll) {
    return ll->tail;
}

Node* ll_remove(LinkedList* ll, unsigned int i) {
    int j;
    Node* n = ll->head->next;
    for(j = 0; j < i && j < ll->size; j++) {
        n = n->next;
    }
    Node* prev = n->prev; /* node before n */
    Node* next = n->next; /* node after n */
    prev->next = next;
    if(next != NULL) {
        next->prev = prev;
    } else { /* the node deleted was the last node, update last */
        ll->tail = prev;
    }
    ll->size--;
    return n;
}

void ll_clear(LinkedList* ll) {
    Node* n = ll->tail;
    while(n != ll->head) {
        n = n->prev; /* first get the previous node */
        free(n->next); /* then delete the node */
    }
    ll->head->next = NULL;
    ll->head->prev = NULL;

    ll->head = ll->head;

    ll->tail = ll->head;

    ll->size = 0;
}

void ll_destroy(LinkedList *ll) {
    ll_clear(ll);
    free(ll);
}


void ll_print(LinkedList *ll) {
    printf("[ ");
    Node* n = ll->head;
    for(;;) {
        n = n->next;
        if(n == NULL) {
            break;
        }
        printf("%d ",n->data.value);
    }
    printf("]\n");
}

#endif
```
