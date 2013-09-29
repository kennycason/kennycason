#include "Layer.h"


Layer::Layer(int numNodes) {
    this->numNodes = numNodes;
    nodes = new Node*[numNodes];
    errors = new double[numNodes];
    for(int i = 0; i < numNodes; i++) {
            nodes[i] = new Node();
            errors[i] = 0.0;
    }
    this->parent = NULL;
    this->child = NULL;
}


Layer::~Layer() {

}

Node** Layer::getNodes() {
    return nodes;
}

Node* Layer::getNode(int i) {
    return nodes[i];
}

int Layer::getNumNodes() {
    return numNodes;
}

double* Layer::getErrors() {
    return errors;
}

Layer* Layer::getChild() {
    return child;
}

Layer* Layer::getParent() {
    return parent;
}

void Layer::setChild(Layer* child) {
    this->child = child;
    if(this->child  != NULL) {
        for(int i = 0; i < numNodes; i++) {
            for(int j = 0; j < this->child->getNumNodes(); j++) {
                nodes[i]->link(this->child->getNode(j));
            }
        }
    }
}

void Layer::setParent(Layer* parent) {
    this->parent = parent;
}
