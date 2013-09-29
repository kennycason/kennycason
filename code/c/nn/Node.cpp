#include "Node.h"

Node::Node() {
    value = DEFAULT_VALUE;
    threshold = DEFAULT_THRESHOLD;
    links.resize(0);

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
    links.push_back(node);
    weights.push_back(2.0 * (rand() / (double)RAND_MAX) - 1.0);
}

Node* Node::unLink(int i) {
    links.erase(links.begin() + i);
    weights.erase(weights.begin() + i);
}

int Node::getNumLinks() {
    return links.size();
}

std::vector<Node*> Node::getLinks() {
    return links;
}

Node* Node::getLink(int i) {
    return links[i];
}

std::vector<double> Node::getWeights() {
    return weights;
}

double Node::getWeight(int i) {
    return weights[i];
}

void Node::setWeights(std::vector<double> weights) {
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
