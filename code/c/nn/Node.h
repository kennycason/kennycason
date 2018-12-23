#ifndef __NODE_H__
#define __NODE_H__

/*
ABOUT: The Node class represents a simple Neuron with the most basic of functionality. Links can be added and removed.
AUTHOR: Kenny Cason
WEBSITE: kennycason.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
*/

#include <iostream>
#include "stdlib.h"

#define DEFAULT_WEIGHT  0.5
#define DEFAULT_VALUE   0.0
#define DEFAULT_THRESHOLD 0.0

using namespace std;

class Node {
public:
    Node();
    ~Node();

    bool activate();
    void feedForward();

    void link(Node *node);

    Node* unLink(int i);

    Node** getLinks();
    Node* getLink(int i);
    int getNumLinks();

    double* getWeights();
    double getWeight(int i);
    void setWeights(double* weights);
    void setWeight(int i, double weight);

    void setValue(double value);
    double getValue();

private:
    Node** links;
    int numLinks;
    double* weights;

    double value;
    double threshold;

    static void link(Node* nodeA, Node *node);
    static Node* unLink(Node* nodeA, int i);
};
#endif
