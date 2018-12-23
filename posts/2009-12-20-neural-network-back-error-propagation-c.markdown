---
title: Neural Network - Back-Error Propagation - C++
author: Kenny Cason
tags: machine learning, neural network, c , ニューラルネット, 機械学習, 誤差逆伝播法
---

Here is yet another simple Neural Network that implements Back-Error Propagation as a form of Reinforced Learning.
The entire project is written in C++ and requires no special libraries to compile and run.
main.cpp contains code to train both a 2 and 3-input Logical AND gate.

The zipped source code can be downloaded <a href="/code/c/nn/NeuralNetwork.zip">here</a>

Note: I wrote this a long time ago. This implementation is iterative but helped me grasp neural networks early on. Matrix implementations are much faster.

### Compile

```bash
g++ *.cpp -o NeuralNetwork</code>
```

It will output an executable name "NeuralNetwork"

### Run

open a terminal and type:

```bash
./NeuralNetwork
```

### Sample Output

```bash
Neural Network Connections Inited
Trained in 9920 trails within an error of 9.99919e-06
0 & 0 = 2.30953e-05
0 & 1 = 0.00301058
1 & 0 = 0.00310933
1 & 1 = 0.995391
Train Logical AND 2 Inputs Demo End
Neural Network Connections Inited
Training...
Trained in 5418 trails within an error of 9.99904e-06
0 & 0 & 0 = 2.44803e-05
0 & 0 & 1 = 0.000274742
0 & 1 & 0 = 0.00196879
0 & 1 & 1 = 0.00369145
1 & 0 & 0 = 3.49642e-06
1 & 0 & 1 = 0.00343857
1 & 1 & 0 = 0.0032112
1 & 1 & 1 = 0.993662
Logical AND 3 Inputs Demo End
```

### Code

Network.cpp
```cpp
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
```

Network.h
```cpp
#ifndef __NETWORK_H__
#define __NETWORK_H__

/*
In this implementation a "Network" represents a collection of layers.
This implementation assumes there is an input layer, center layer(s), and an output layer.
Though there is nothing preventing the output layer to feed back into the input layer or any layer connecting to a previous layer (recurrent neural networks).
*/

#include "Layer.h"
#include "Node.h"
#include "math.h"


#define DEFAULT_NUM_INPUT_NODES 10
#define DEFAULT_NUM_OUTPUT_NODES 1
#define DEFAULT_NUM_CENTER_LAYERS  3
#define DEFAULT_NUM_CENTER_NODES 10


using namespace std;

class Network {
public:
    Network();
    Network(int numInputNodes, int numCenterLayers, int numCenterNodes, int numOutputNodes);
    ~Network();


    Layer* getInputLayer();
    Layer** getCenterLayers();
    Layer* getCenterLayer(int i);
    Layer* getOutputLayer();

    void setInputLayer(Layer* layer);
    void setCenterLayer(Layer* layer, int i);
    void setCenterLayers(Layer** layer);
    void setOutputLayer(Layer* layer);

    int getNumInputNodes();
    int getNumCenterLayers();
    int getNumCenterNodes();
    int getNumOutputNodes();
    int getNumLayers();

private:
    Layer* inputLayer;
    Layer** centerLayers;
    Layer* outputLayer;


    int numInputNodes;
    int numCenterLayers;
    int numCenterNodes;
    int numOutputNodes;
    int numLayers;

};
#endif
```

Layer.cpp
```cpp
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
```

Layer.h
```cpp
#ifndef __LAYER_H__
#define __LAYER_H__

/*
A Layer is a collection of Nodes.
Though this layers are not necessary, it provides nice organization.
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
```


Node.cpp
```cpp
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
```

Node.h
```cpp
#ifndef __NODE_H__
#define __NODE_H__

/*
The Node class represents a simple Neuron with the most basic of functionality.
Links can be added and removed.
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
```

BackErrorPropagation.cpp
```cpp
#include "BackErrorPropagation.h"

/**
 * connection for a Back Error Propagation Network
 */
void BackErrorPropagation::initNetwork(Network* cell) {
    cell->setOutputLayer(new Layer(cell->getNumOutputNodes()));
    cell->setCenterLayers(new Layer*[cell->getNumCenterLayers()]);
    for(int i = 0; i < cell->getNumCenterLayers(); i++) {
        cell->getCenterLayers()[i] = new Layer(cell->getNumCenterNodes());
    }
    cell->setInputLayer( new Layer(cell->getNumInputNodes()));

    for(int i = cell->getNumCenterLayers() - 1; i >= 0; i--) {
        if(i < cell->getNumCenterLayers() - 1) {
            cell->getCenterLayer(i)->setChild(cell->getCenterLayer(i + 1));
        } else {
            cell->getCenterLayer(i)->setChild(cell->getOutputLayer());
            cell->getOutputLayer()->setParent(cell->getCenterLayer(i));
        }
        if(i > 0) {
            cell->getCenterLayer(i)->setParent(cell->getCenterLayer(i - 1));
        } else {
            cell->getCenterLayer(i)->setParent(cell->getInputLayer());
        }
    }
    cell->getInputLayer()->setChild(cell->getCenterLayer(0));
    cout << "Neural Network Connections Inited" << endl;
}


double BackErrorPropagation::calculateOutputError(Network *cell, struct parameters &params) {
    double error = 0.0;
    for (int i = 0; i < cell->getOutputLayer()->getNumNodes(); i++) {
        error += pow(cell->getOutputLayer()->getNode(i)->getValue() - params.teachingSignals[i], 2);
    }
    error /= cell->getOutputLayer()->getNumNodes();
    return error;
}


void BackErrorPropagation::backPropagate(Network *cell, struct parameters &params) {
    Layer* layer = cell->getOutputLayer();
    while(layer != NULL) {
        if(layer == cell->getOutputLayer()) { // 出力層: output layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                layer->getErrors()[i] =
                    (params.teachingSignals[i] - layer->getNode(i)->getValue()) * layer->getNode(i)->getValue() * (1.0 - layer->getNode(i)->getValue() );
            }
        } else if (layer == cell->getInputLayer()) { // 入力層: input layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                layer->getErrors()[i] = 0.0;
                for (int j = 0; j < layer->getNode(i)->getNumLinks(); j++) {
                    layer->getNode(i)->setWeight(j, layer->getNode(i)->getWeight(j)
                        + params.learningRate * layer->getChild()->getErrors()[j] * layer->getNode(i)->getValue());
                }
            }
        } else { // 中間層: middle layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                double sum = 0.0;
                for (int j = 0; j < layer->getNode(i)->getNumLinks(); j++) {
                    sum += layer->getChild()->getErrors()[j] * layer->getNode(i)->getWeight(j);
                    layer->getNode(i)->setWeight(j, layer->getNode(i)->getWeight(j)
                        + params.learningRate * layer->getChild()->getErrors()[j] * layer->getNode(i)->getValue());
                }
                layer->getErrors()[i] = sum * layer->getNode(i)->getValue() * (1.0 - layer->getNode(i)->getValue());
            }
        }
        layer = layer->getParent();
    }
}

void BackErrorPropagation::feedForward(Network *cell) {
    double sum  = 0.0;
    Layer* layer = cell->getInputLayer();
    while(layer != NULL) {
        if(layer->getChild() != NULL) {
            for(int j = 0; j < layer->getNumNodes(); j++) {
                if(layer->getParent() != NULL) { // dont need to run values of inputlayer through function
                    layer->getNode(j)->setValue( BackErrorPropagation::activate( layer->getNode(j)->getValue()) );
                }
                for(int k = 0; k < layer->getNode(j)->getNumLinks(); k++) {
                    sum = 0.0;
                    sum += layer->getNode(j)->getValue() * layer->getNode(j)->getWeight(k);
                    layer->getNode(j)->getLink(k)->setValue( layer->getNode(j)->getLink(k)->getValue() + sum);
                }
            }
        } else { // is the output layer, so just run the values through the sigmoid function
            for(int j = 0; j < layer->getNumNodes(); j++) {
                layer->getNode(j)->setValue( BackErrorPropagation::activate( layer->getNode(j)->getValue()) );
            }
        }
        layer = layer->getChild();
    }
}


double BackErrorPropagation::activate(double val) {
    return 1.0 / (1.0 + exp(-val))  ;
}
```

BackErrorPropagation.h
```cpp
#ifndef __BACK_ERROR_PROPAGATION_H__
#define __BACK_ERROR_PROPAGATION_H__

/*
This class contains all the overhead needed to initialize and train a neural network using Back error propagation.
*/

#include "Network.h"
#include "Node.h"
#include "math.h"

struct parameters {
    int numLayers;
    int numInputNodes;
    int inputDimX;
    int inputDimY;
    int numOutputNodes;
    int outputDimX;
    int outputDimY;

    int numCenterLayers;
    int numCenterNodes;
    bool useBias;

    double learningRate;

    int numTrainingSets;
    int currentTrainingSet;
    double** trainingInputs;
    double** trainingOutputs;

    double* teachingSignals;
};
using namespace std;

class BackErrorPropagation {
public:

    /**
     * シグモイド関数: Sigmoid function
     */
    static double activate(double val);

    /**
     * 出力層から入力層まで逆向きに伝播する: back-propagate from the output layer to the input layer
     */
    static void backPropagate(Network *cell, struct parameters &params);
    static void backPropagate(Network *cell, struct parameters &params, int i);

    /**
     * 入力層から出力層まで前向きを伝播する: propagate from the input layer to the output layer
     */
    static void feedForward(Network *cell);
    static void feedForward(Network *cell, int i);

    /**
     * 出力と教師信号の平均２乗誤差を計算する: calculate the average squared error between the output layer and teacher signal
     * @return 平均２乗誤差
     */
    static double calculateOutputError(Network *cell, struct parameters &params);

    static void initNetwork(Network* cell);

};


#endif
```

main.cpp
```cpp
#include <iostream>
#include "stdlib.h"
#include "Network.h"
#include "math.h"
#include "BackErrorPropagation.h"

/*
Train a back error propagation neural network to be able to perform Logical AND, three inputs
*/

using namespace std;
void trainLogicalAND2Inputs();
void trainLogicalAND3Inputs();

int main() {
    trainLogicalAND2Inputs();
    trainLogicalAND3Inputs();
}

void trainLogicalAND2Inputs() {
    parameters params;
    params.numInputNodes = 2;
    params.inputDimX = 2;
    params.inputDimY = 1;
    params.numOutputNodes = 1;
    params.outputDimX = 1;
    params.outputDimY = 1;
    params.numCenterLayers = 1;
    params.numCenterNodes = 10;
    params.numLayers = params.numCenterLayers + 2;
    params.useBias = false;

    params.learningRate = 5.5;

    params.numTrainingSets = 4;
    params.currentTrainingSet = 0;

    params.trainingInputs = new double*[params.numTrainingSets];
    for(int i = 0; i < params.numTrainingSets; i++) {
        params.trainingInputs[i] = new double[params.numInputNodes];
    }
    params.trainingOutputs = new double*[params.numTrainingSets];
    for(int i = 0; i < params.numTrainingSets; i++) {
        params.trainingOutputs[i] = new double[params.numOutputNodes];
    }
    params.teachingSignals = new double[params.numOutputNodes];

    // 訓練データの作成 // Create Trainig Data
    // 訓練データ０     // Training Set 0
    params.trainingInputs[0][0] = 0; // 入力１     // Input 1
    params.trainingInputs[0][1] = 0; // 入力２     // Input 2
    params.trainingOutputs[0][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ1      // Training Set 1
    params.trainingInputs[1][0] = 0; // 入力１     // Input 1
    params.trainingInputs[1][1] = 1; // 入力２     // Input 2
    params.trainingOutputs[1][0] = 0; // 教師信号   / Teacher Signal
    // 訓練データ2     // Training Set 2
    params.trainingInputs[2][0] = 1; // 入力１     // Input 1
    params.trainingInputs[2][1] = 0; // 入力２     // Input 2
    params.trainingOutputs[2][0] = 0; // 教師信号
    // 訓練データ3     // Training Set 3
    params.trainingInputs[3][0] = 1; // 入力１     // Input 1
    params.trainingInputs[3][1] = 1; // 入力２     // Input 2
    params.trainingOutputs[3][0] = 1; // 教師信号  // Teacher Signal

    Network* cell = new Network(params.numInputNodes, params.numCenterLayers, params.numCenterNodes, params.numOutputNodes);
    BackErrorPropagation::initNetwork(cell);


    // 訓練データを学習する // Teach the Training Data
    double error = 1.0;
    int count = 0;
    while(error > 0.00001 && count < 10000) {
        count++;
        error = 0.0;
        for(params.currentTrainingSet = 0; params.currentTrainingSet < params.numTrainingSets; params.currentTrainingSet++) {

            cell->getInputLayer()->getNode(0)->setValue(params.trainingInputs[params.currentTrainingSet][0]);
            cell->getInputLayer()->getNode(1)->setValue(params.trainingInputs[params.currentTrainingSet][1]);
            params.teachingSignals[0] = params.trainingOutputs[params.currentTrainingSet][0];
         /*   cout << " IDEAL " << cell->getInputLayer()->getNode(0)->getValue() << " & " << cell->getInputLayer()->getNode(1)->getValue()
                 << " = " << params.teachingSignals[0] << endl;*/

            // feed forward all at once
            BackErrorPropagation::feedForward(cell);
        /*    cout << " ACTUAL " << cell->getInputLayer()->getNode(0)->getValue() << " & " << cell->getInputLayer()->getNode(1)->getValue()
                    << " = " << cell->getOutputLayer()->getNode(0)->getValue() << endl; */
            error += (BackErrorPropagation::calculateOutputError(cell, params) / (double)params.numTrainingSets);

            // Back propagate all at once
            BackErrorPropagation::backPropagate(cell, params);

        }
     //   cout << count << ": Error = " << error << endl; // runs about 100x faster if this is commented out I/O is slow
    }
    cout << "Trained in " << count << " trails within an error of " << error << endl;
    // print output
    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cout << "Train Logical AND 2 Inputs Demo End" << endl;
}

/*
 Another Test
 */
void trainLogicalAND3Inputs() {

    parameters params;
    params.numInputNodes = 3;
    params.inputDimX = 3;
    params.inputDimY = 1;
    params.numOutputNodes = 1;
    params.outputDimX = 1;
    params.outputDimY = 1;
    params.numCenterLayers = 1;
    params.numCenterNodes = 10;
    params.numLayers = params.numCenterLayers + 2;
    params.useBias = false;

    params.learningRate = 5.5;

    params.numTrainingSets = 8;
    params.currentTrainingSet = 0;

    params.trainingInputs = new double*[params.numTrainingSets];
    for(int i = 0; i < params.numTrainingSets; i++) {
        params.trainingInputs[i] = new double[params.numInputNodes];
    }
    params.trainingOutputs = new double*[params.numTrainingSets];
    for(int i = 0; i < params.numTrainingSets; i++) {
        params.trainingOutputs[i] = new double[params.numOutputNodes];
    }
    params.teachingSignals = new double[params.numOutputNodes];

    // Logical OR
    // 訓練データの作成 // Create Trainig Data
    // 訓練データ０     // Training Set 0
    params.trainingInputs[0][0] = 0; // 入力１     // Input 1
    params.trainingInputs[0][1] = 0; // 入力２     // Input 2
    params.trainingInputs[0][2] = 0; // 入力3      // Input 3
    params.trainingOutputs[0][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ1      // Training Set 1
    params.trainingInputs[1][0] = 0; // 入力１     // Input 1
    params.trainingInputs[1][1] = 0; // 入力２     // Input 2
    params.trainingInputs[1][2] = 1; // 入力3      // Input 3
    params.trainingOutputs[1][0] = 0; // 教師信号   / Teacher Signal
    // 訓練データ2     // Training Set 2
    params.trainingInputs[2][0] = 0; // 入力１     // Input 1
    params.trainingInputs[2][1] = 1; // 入力２     // Input 2
    params.trainingInputs[2][2] = 0; // 入力3      // Input 3
    params.trainingOutputs[2][0] = 0; // 教師信号
    // 訓練データ3     // Training Set 3
    params.trainingInputs[3][0] = 0; // 入力１     // Input 1
    params.trainingInputs[3][1] = 1; // 入力２     // Input 2
    params.trainingInputs[3][2] = 1; // 入力3      // Input 3
    params.trainingOutputs[3][0] = 0; // 教師信号  // Teacher Signal

    params.trainingInputs[4][0] = 1; // 入力１     // Input 1
    params.trainingInputs[4][1] = 0; // 入力２     // Input 2
    params.trainingInputs[4][2] = 0; // 入力3      // Input 3
    params.trainingOutputs[4][0] = 0; // 教師信号  // Teacher Signal

    params.trainingInputs[5][0] = 1; // 入力１     // Input 1
    params.trainingInputs[5][1] = 0; // 入力２     // Input 2
    params.trainingInputs[5][2] = 1; // 入力3      // Input 3
    params.trainingOutputs[5][0] = 0; // 教師信号  // Teacher Signal

    params.trainingInputs[6][0] = 1; // 入力１     // Input 1
    params.trainingInputs[6][1] = 1; // 入力２     // Input 2
    params.trainingInputs[6][2] = 0; // 入力3      // Input 3
    params.trainingOutputs[6][0] = 0; // 教師信号  // Teacher Signal

    params.trainingInputs[7][0] = 1; // 入力１     // Input 1
    params.trainingInputs[7][1] = 1; // 入力２     // Input 2
    params.trainingInputs[7][2] = 1; // 入力3      // Input 3
    params.trainingOutputs[7][0] = 1; // 教師信号  // Teacher Signal



    Network* cell = new Network(params.numInputNodes, params.numCenterLayers, params.numCenterNodes, params.numOutputNodes);
    BackErrorPropagation::initNetwork(cell);


    cout << "Training..." << endl;
    // 訓練データを学習する // Teach the Training Data
    double error = 1.0;
    int count = 0;
    while(error > 0.00001 && count < 50000) {
        count++;
        error = 0.0;
        for(params.currentTrainingSet = 0; params.currentTrainingSet < params.numTrainingSets; params.currentTrainingSet++) {

            cell->getInputLayer()->getNode(0)->setValue(params.trainingInputs[params.currentTrainingSet][0]);
            cell->getInputLayer()->getNode(1)->setValue(params.trainingInputs[params.currentTrainingSet][1]);
            cell->getInputLayer()->getNode(2)->setValue(params.trainingInputs[params.currentTrainingSet][2]);

            params.teachingSignals[0] = params.trainingOutputs[params.currentTrainingSet][0];

            BackErrorPropagation::feedForward(cell);
            error += (BackErrorPropagation::calculateOutputError(cell, params) / (double)params.numTrainingSets);
            BackErrorPropagation::backPropagate(cell, params);

        }
     //   cout << count << ": Error = " << error << endl; // runs about 100x faster if this is commented out I/O is slow
    }
    cout << "Trained in " << count << " trails within an error of " << error << endl;
    // print output
    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(0);
    cell->getInputLayer()->getNode(2)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 0 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(0);
    cell->getInputLayer()->getNode(2)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 0 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(1);
    cell->getInputLayer()->getNode(2)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 1 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(0);
    cell->getInputLayer()->getNode(1)->setValue(1);
    cell->getInputLayer()->getNode(2)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "0 & 1 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(0);
    cell->getInputLayer()->getNode(2)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 0 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(0);
    cell->getInputLayer()->getNode(2)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 0 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(1);
    cell->getInputLayer()->getNode(2)->setValue(0);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 1 & 0 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;

    cell->getInputLayer()->getNode(0)->setValue(1);
    cell->getInputLayer()->getNode(1)->setValue(1);
    cell->getInputLayer()->getNode(2)->setValue(1);
    BackErrorPropagation::feedForward(cell);
    cout << "1 & 1 & 1 = " << cell->getOutputLayer()->getNode(0)->getValue() << endl;
    cout << "Logical AND 3 Inputs Demo End" << endl;
}
```

### Resources

[About Neural Networks (English)](/posts/2008-12-24-neural-networks-simple-models.html)

[About Neural Networks (Japanese/日本語)](/posts/2008-12-24-neural-network-jp.html)

[Java Implementation of a Neural Network](/posts/2008-12-25-neural-network-back-error-propagation-java.html)
