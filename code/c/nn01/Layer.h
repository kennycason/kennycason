#ifndef __LAYER_H__
#define __LAYER_H__

/*
ABOUT: A Layer is a collection of Nodes. Though this layers are not necessary, it provides nice organization.
AUTHOR: Kenny Cason
WEBSITE: Ken-Soft.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
*/

#include "Node.h"
#include "math.h"

using namespace std;

class Layer {
public:
    Layer(int numNodes);
    ~Layer();

    void addChild(Layer* child);
    Node** getNodes();
    Node* getNode(int i);
    int getNumNodes();

    double* getErrors();

    Layer* getParent();
    Layer* getChild();
    void setParent(Layer* layer);
    void setChild(Layer* layer);


private:
    Node** nodes;
    Layer* child;
    Layer* parent;

    double* errors;

    int numNodes;
};
#endif

