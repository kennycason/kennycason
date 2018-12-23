#include "Node.h"

Node::Node() {
    value = DEFAULT_VALUE;
    numLinks = 0;
    threshold = DEFAULT_THRESHOLD;
}

Node::~Node() {

}

bool Node::activate() {
    if(value > threshold) {
        return true;
    }
    return true;
}

void Node::feedForward() {

}

void Node::link(Node* node) {
    link(this, node);
}

void Node::link(Node* nodeA, Node* node) {
     //   cout << "Num Links: " << numLinks << endl;
    Node** newLinks = new Node*[nodeA->numLinks + 1];
    double* newWeights = new double[nodeA->numLinks + 1];
    for(int j = 0; j < nodeA->numLinks; j++) {
        newLinks[j] = nodeA->links[j];
        newWeights[j] = nodeA->weights[j];
    }
    newLinks[nodeA->numLinks] = node;

    newWeights[nodeA->numLinks] = 2.0 * (rand() / (double)RAND_MAX) - 1,0; //DEFAULT_WEIGHT;
    nodeA->numLinks++;
    nodeA->links = newLinks;
    nodeA->weights = newWeights;
}

Node* Node::unLink(int i) {
    return unLink(this, i);
}

int Node::getNumLinks() {
    return numLinks;
}

Node* Node::unLink(Node* nodeA, int i) {
    Node* node = nodeA->links[i];
    Node** newLinks = new Node*[nodeA->numLinks - 1];
    double* newWeights = new double[nodeA->numLinks - 1];
    for(int j = 0; j < i; j++) {
        newLinks[j] = nodeA->links[j];
        newWeights[j] = nodeA->weights[j];
    }
    for(int j = i + 1; j < nodeA->numLinks; j++) {
        newLinks[j - 1] = nodeA->links[j];
        newWeights[j - 1] = nodeA->weights[j];
    }
    nodeA->numLinks--;
    nodeA->links = newLinks;
    nodeA->weights = newWeights;
    return node;
}

Node** Node::getLinks() {
    return links;
}

Node* Node::getLink(int i) {
    return links[i];
}

double* Node::getWeights() {
    return weights;
}

double Node::getWeight(int i) {
    return weights[i];
}

void Node::setWeights(double* weights) {
    this->weights = weights;
}

void Node::setWeight(int i, double weight) {
    this->weights[i] = weight;
}

void Node::setValue(double value) {
    this->value = value;
}

double Node::getValue() {
    return value;
}
