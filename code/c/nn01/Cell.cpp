#include "Cell.h"

Cell::Cell() {
    numInputNodes = DEFAULT_NUM_INPUT_NODES;
    numCenterLayers = DEFAULT_NUM_CENTER_LAYERS;
    numCenterNodes = DEFAULT_NUM_CENTER_NODES;
    numOutputNodes = DEFAULT_NUM_OUTPUT_NODES;
    numLayers = numCenterLayers + 2;
}

Cell::Cell(int numInputNodes, int numCenterLayers, int numCenterNodes, int numOutputNodes) {
    this->numInputNodes = numInputNodes;
    this->numCenterLayers = numCenterLayers;
    this->numCenterNodes = numCenterNodes;
    this->numOutputNodes = numOutputNodes;
    numLayers = numCenterLayers + 2;
}

Cell::~Cell() {

}

Layer* Cell::getInputLayer() {
    return inputLayer;
}

Layer** Cell::getCenterLayers() {
    return centerLayers;
}

Layer* Cell::getCenterLayer(int i) {
    return centerLayers[i];
}

Layer* Cell::getOutputLayer() {
    return outputLayer;
}

void Cell::setInputLayer(Layer* layer) {
    this->inputLayer = layer;
}

void Cell::setCenterLayer(Layer* layer, int i) {
    this->centerLayers[i] = layer;
}

void Cell::setCenterLayers(Layer** layer) {
    this->centerLayers = layer;
}

void Cell::setOutputLayer(Layer* layer) {
    this->outputLayer = layer;
}

int Cell::getNumInputNodes() {
    return numInputNodes;
}

int Cell::getNumCenterLayers() {
    return numCenterLayers;
}

int Cell::getNumCenterNodes() {
    return numCenterNodes;
}

int Cell::getNumOutputNodes() {
    return numOutputNodes;
}

int Cell::getNumLayers() {
    return numLayers;
}
