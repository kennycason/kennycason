#include "Network.h"

Network::Network() {
    numInputNodes = DEFAULT_NUM_INPUT_NODES;
    numCenterLayers = DEFAULT_NUM_CENTER_LAYERS;
    numCenterNodes = DEFAULT_NUM_CENTER_NODES;
    numOutputNodes = DEFAULT_NUM_OUTPUT_NODES;
    numLayers = numCenterLayers + 2;
}

Network::Network(int numInputNodes, int numCenterLayers, int numCenterNodes, int numOutputNodes) {
    this->numInputNodes = numInputNodes;
    this->numCenterLayers = numCenterLayers;
    this->numCenterNodes = numCenterNodes;
    this->numOutputNodes = numOutputNodes;
    numLayers = numCenterLayers + 2;
}

Network::~Network() {

}

Layer* Network::getInputLayer() {
    return inputLayer;
}

Layer** Network::getCenterLayers() {
    return centerLayers;
}

Layer* Network::getCenterLayer(int i) {
    return centerLayers[i];
}

Layer* Network::getOutputLayer() {
    return outputLayer;
}

void Network::setInputLayer(Layer* layer) {
    this->inputLayer = layer;
}

void Network::setCenterLayer(Layer* layer, int i) {
    this->centerLayers[i] = layer;
}

void Network::setCenterLayers(Layer** layer) {
    this->centerLayers = layer;
}

void Network::setOutputLayer(Layer* layer) {
    this->outputLayer = layer;
}

int Network::getNumInputNodes() {
    return numInputNodes;
}

int Network::getNumCenterLayers() {
    return numCenterLayers;
}

int Network::getNumCenterNodes() {
    return numCenterNodes;
}

int Network::getNumOutputNodes() {
    return numOutputNodes;
}

int Network::getNumLayers() {
    return numLayers;
}
