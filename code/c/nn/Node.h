#ifndef __NODE_H__
#define __NODE_H__

/*
ABOUT: The Node class represents a simple Neuron with the most basic of functionality. Links can be added and removed.
AUTHOR: Kenny Cason
WEBSITE: Ken-Soft.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
*/

#include <iostream>
#include "stdlib.h"
#include <vector>

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

    std::vector<Node*> getLinks();
    Node* getLink(int i);
    int getNumLinks();

    std::vector<double> getWeights();
    double getWeight(int i);
    void setWeights(std::vector<double> weights);
    void setWeight(int i, double weight);

    void setValue(double value);
    double getValue();

    double learningRate;

private:
    std::vector<Node*> links;
    std::vector<double> weights;

    double value;
    double threshold;
};
#endif
