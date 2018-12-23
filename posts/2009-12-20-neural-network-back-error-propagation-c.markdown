---
title: Neural Network - Back-Error Propagation - C++
author: Kenny Cason
tags: machine learning, neural network, c , ニューラルネット, 機械学習, 誤差逆伝播法
---

Here is yet another simple Neural Network that implements Back-Error Propagation as a form of Reinforced Learning.
The entire project is written in C++ and requires no special libraries to compile and run.
main.cpp contains code to train both a 2 and 3-input Logical AND gate.
The zipped source code can be downloaded <a href="/code/c/nn01/NeuralNetwork.zip">here</a>
A Linux executable is already compiled and included in the zip, but feel free to recompile it. A Code::Blocks Project file is also included.

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
Trained in 10000 trails within an error of 1.03127e-05
0 & 0 = 4.63117e-05
0 & 1 = 0.00349833
1 & 0 = 0.00290835
1 & 1 = 0.995469
Train Logical AND 2 Inputs Demo End
Neural Network Connections Inited
Training...
Trained in 5584 trails within an error of 9.99977e-06
0 & 0 & 0 = 3.62242e-05
0 & 0 & 1 = 0.00194301
0 & 1 & 0 = 0.000102096
0 & 1 & 1 = 0.00344352
1 & 0 & 0 = 0.000142368
1 & 0 & 1 = 0.00333881
1 & 1 & 0 = 0.0035418
1 & 1 & 1 = 0.993633
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
}

Network::Network(int numInputNodes, int numCenterLayers, int numCenterNodes, int numOutputNodes) {
    this->numInputNodes = numInputNodes;
    this->numCenterLayers = numCenterLayers;
    this->numCenterNodes = numCenterNodes;
    this->numOutputNodes = numOutputNodes;
}

Network::~Network() {

}

void Network::init() {
    setOutputLayer(new Layer(getNumOutputNodes()));
    setCenterLayers(new Layer*[getNumCenterLayers()]);
    for(int i = 0; i < getNumCenterLayers(); i++) {
        getCenterLayers()[i] = new Layer(getNumCenterNodes());
    }
    setInputLayer( new Layer(getNumInputNodes()));

    for(int i = getNumCenterLayers() - 1; i >= 0; i--) {
        if(i < getNumCenterLayers() - 1) {
            getCenterLayer(i)->setChild(getCenterLayer(i + 1));
        } else {
            getCenterLayer(i)->setChild(getOutputLayer());
            getOutputLayer()->setParent(getCenterLayer(i));
        }
        if(i > 0) {
            getCenterLayer(i)->setParent(getCenterLayer(i - 1));
        } else {
            getCenterLayer(i)->setParent(getInputLayer());
        }
    }
    getInputLayer()->setChild(getCenterLayer(0));
    std::cout << "Neural Network Connections Inited" << std::endl;
}

double Network::calculateOutputError() {
    double error = 0.0;
    for (int i = 0; i < getOutputLayer()->getNumNodes(); i++) {
        error += pow(getOutputLayer()->getNode(i)->getValue() - teachingSignals[i], 2);
    }
    error /= getOutputLayer()->getNumNodes();
    return error;
}

void Network::backPropagate() {
    Layer* layer = getOutputLayer();
    while(layer != NULL) {
        if(layer == getOutputLayer()) { // 出力層: output layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                layer->getErrors()[i] =
                    (teachingSignals[i] - layer->getNode(i)->getValue()) * layer->getNode(i)->getValue() * (1.0 - layer->getNode(i)->getValue() );
            }
        } else if (layer == getInputLayer()) { // 入力層: input layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                layer->getErrors()[i] = 0.0;
                for (int j = 0; j < layer->getNode(i)->getNumLinks(); j++) {
                    layer->getNode(i)->setWeight(j, layer->getNode(i)->getWeight(j)
                        + learningRate * layer->getChild()->getErrors()[j] * layer->getNode(i)->getValue());
                }
            }
        } else { // 中間層: middle layer
            for (int i = 0; i < layer->getNumNodes(); i++) {
                double sum = 0.0;
                for (int j = 0; j < layer->getNode(i)->getNumLinks(); j++) {
                    sum += layer->getChild()->getErrors()[j] * layer->getNode(i)->getWeight(j);
                    layer->getNode(i)->setWeight(j, layer->getNode(i)->getWeight(j)
                        + learningRate * layer->getChild()->getErrors()[j] * layer->getNode(i)->getValue());
                }
                layer->getErrors()[i] = sum * layer->getNode(i)->getValue() * (1.0 - layer->getNode(i)->getValue());
            }
        }
        layer = layer->getParent();
    }
}


void Network::feedForward() {
    double sum  = 0.0;
    Layer* layer = getInputLayer();//->getChild();
    while(layer != NULL) {
        if(layer->getChild() != NULL) {
            for(int j = 0; j < layer->getNumNodes(); j++) {
                if(layer->getParent() != NULL) { // dont need to run values of inputlayer through function
                    layer->getNode(j)->setValue( activate( layer->getNode(j)->getValue()) );
                }
                for(int k = 0; k < layer->getNode(j)->getNumLinks(); k++) {
                    sum = 0.0;
                    sum += layer->getNode(j)->getValue() * layer->getNode(j)->getWeight(k);
                    layer->getNode(j)->getLink(k)->setValue( layer->getNode(j)->getLink(k)->getValue() + sum);
                }
            }
        } else { // is the output layer, so just run the values through the sigmoid function
            for(int j = 0; j < layer->getNumNodes(); j++) {
                layer->getNode(j)->setValue( activate( layer->getNode(j)->getValue()) );
            }
        }
        layer = layer->getChild();
    }
}

double Network::activate(double val) {
    return 1.0 / (1.0 + exp(-val))  ;
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
    return numCenterLayers + 2;
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
    Network(int numInputNodes, int numCenterLayers, int numCenterNodes, int numOutputNodes); // traditional constructor
    ~Network();

    void init();
    void backPropagate();
    double calculateOutputError();
    void feedForward();
    double activate(double val);

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

    int learningRate; // the default learningRate
    bool useBias;
    double* teachingSignals;

private:
    Layer* inputLayer;
    Layer** centerLayers;
    Layer* outputLayer;

    int numInputNodes;
    int numCenterLayers;
    int numCenterNodes;
    int numOutputNodes;

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

    double* teachingSignals;

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
```

main.cpp
```cpp
#include <iostream>
#include "stdlib.h"
#include "Network.h"
#include "math.h"

/*
Train a back error propagation neural network to be able to perform Logical AND, three inputs
*/
void trainLogicalAND2Inputs();
void trainLogicalAND3Inputs();

int main() {
    trainLogicalAND2Inputs();
    trainLogicalAND3Inputs();
}

void trainLogicalAND2Inputs() {
    int numInputNodes = 2;
    int numOutputNodes = 1;
    int numCenterLayers = 1;
    int numCenterNodes = 10;

    Network* network = new Network(numInputNodes, numCenterLayers, numCenterNodes, numOutputNodes);

    network->learningRate = 5.5;
    network->useBias = false;

    int numTrainingSets = 4;
    int currentTrainingSet = 0;

    double** trainingInputs;
    double** trainingOutputs;

    trainingInputs = new double*[numTrainingSets];
    for(int i = 0; i < numTrainingSets; i++) {
        trainingInputs[i] = new double[numInputNodes];
    }
    trainingOutputs = new double*[numTrainingSets];
    for(int i = 0; i < numTrainingSets; i++) {
        trainingOutputs[i] = new double[numOutputNodes];
    }
    network->teachingSignals = new double[numOutputNodes];

    // 訓練データの作成 // Create Trainig Data
    // 訓練データ０     // Training Set 0
    trainingInputs[0][0] = 0; // 入力１     // Input 1
    trainingInputs[0][1] = 0; // 入力２     // Input 2
    trainingOutputs[0][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ1      // Training Set 1
    trainingInputs[1][0] = 0; // 入力１     // Input 1
    trainingInputs[1][1] = 1; // 入力２     // Input 2
    trainingOutputs[1][0] = 0; // 教師信号   / Teacher Signal
    // 訓練データ2     // Training Set 2
    trainingInputs[2][0] = 1; // 入力１     // Input 1
    trainingInputs[2][1] = 0; // 入力２     // Input 2
    trainingOutputs[2][0] = 0; // 教師信号
    // 訓練データ3     // Training Set 3
    trainingInputs[3][0] = 1; // 入力１     // Input 1
    trainingInputs[3][1] = 1; // 入力２     // Input 2
    trainingOutputs[3][0] = 1; // 教師信号  // Teacher Signal

    network->init();

    // 訓練データを学習する // Teach the Training Data
    double error = 1.0;
    int count = 0;
    while(error > 0.00001 && count < 10000) {
        count++;
        error = 0.0;
        for(currentTrainingSet = 0; currentTrainingSet < numTrainingSets; currentTrainingSet++) {

            network->getInputLayer()->getNode(0)->setValue(trainingInputs[currentTrainingSet][0]);
            network->getInputLayer()->getNode(1)->setValue(trainingInputs[currentTrainingSet][1]);
            network->teachingSignals[0] = trainingOutputs[currentTrainingSet][0];
         /*   std::cout << " IDEAL " << network->getInputLayer()->getNode(0)->getValue() << " & " << network->getInputLayer()->getNode(1)->getValue()
                 << " = " << network->teachingSignals[0] << std::endl;*/

            // feed forward all at once
            network->feedForward();
        /*    std::cout << " ACTUAL " << network->getInputLayer()->getNode(0)->getValue() << " & " << network->getInputLayer()->getNode(1)->getValue()
                    << " = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl; */
            error += (network->calculateOutputError() / (double)numTrainingSets);

            // Back propagate all at once
            network->backPropagate();

        }
     //   std::cout << count << ": Error = " << error << std::endl; // runs about 100x faster if this is commented out I/O is slow
    }
    std::cout << "Trained in " << count << " trails within an error of " << error << std::endl;
    // print output
    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->feedForward();
    std::cout << "0 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->feedForward();
    std::cout << "0 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->feedForward();
    std::cout << "1 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->feedForward();
    std::cout << "1 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    std::cout << "Train Logical AND 2 Inputs Demo End" << std::endl;

}


void trainLogicalAND3Inputs() {

    int numInputNodes = 3;
    int numOutputNodes = 1;
    int numCenterLayers = 1;
    int numCenterNodes = 10;

    Network* network = new Network(numInputNodes, numCenterLayers, numCenterNodes, numOutputNodes);

    network->learningRate = 5.5;
    network->useBias = false;

    int numTrainingSets = 8;
    int currentTrainingSet = 0;

    double** trainingInputs;
    double** trainingOutputs;

    trainingInputs = new double*[numTrainingSets];
    for(int i = 0; i < numTrainingSets; i++) {
        trainingInputs[i] = new double[numInputNodes];
    }
    trainingOutputs = new double*[numTrainingSets];
    for(int i = 0; i < numTrainingSets; i++) {
        trainingOutputs[i] = new double[numOutputNodes];
    }
    network->teachingSignals = new double[numOutputNodes];

    // Logical OR
    // 訓練データの作成 // Create Trainig Data
    // 訓練データ０     // Training Set 0
    trainingInputs[0][0] = 0; // 入力１     // Input 1
    trainingInputs[0][1] = 0; // 入力２     // Input 2
    trainingInputs[0][2] = 0; // 入力3      // Input 3
    trainingOutputs[0][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ1      // Training Set 1
    trainingInputs[1][0] = 0; // 入力１     // Input 1
    trainingInputs[1][1] = 0; // 入力２     // Input 2
    trainingInputs[1][2] = 1; // 入力3      // Input 3
    trainingOutputs[1][0] = 0; // 教師信号   / Teacher Signal
    // 訓練データ2     // Training Set 2
    trainingInputs[2][0] = 0; // 入力１     // Input 1
    trainingInputs[2][1] = 1; // 入力２     // Input 2
    trainingInputs[2][2] = 0; // 入力3      // Input 3
    trainingOutputs[2][0] = 0; // 教師信号
    // 訓練データ3     // Training Set 3
    trainingInputs[3][0] = 0; // 入力１     // Input 1
    trainingInputs[3][1] = 1; // 入力２     // Input 2
    trainingInputs[3][2] = 1; // 入力3      // Input 3
    trainingOutputs[3][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ4     // Training Set 4
    trainingInputs[4][0] = 1; // 入力１     // Input 1
    trainingInputs[4][1] = 0; // 入力２     // Input 2
    trainingInputs[4][2] = 0; // 入力3      // Input 3
    trainingOutputs[4][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ5     // Training Set 5
    trainingInputs[5][0] = 1; // 入力１     // Input 1
    trainingInputs[5][1] = 0; // 入力２     // Input 2
    trainingInputs[5][2] = 1; // 入力3      // Input 3
    trainingOutputs[5][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ6     // Training Set 6
    trainingInputs[6][0] = 1; // 入力１     // Input 1
    trainingInputs[6][1] = 1; // 入力２     // Input 2
    trainingInputs[6][2] = 0; // 入力3      // Input 3
    trainingOutputs[6][0] = 0; // 教師信号  // Teacher Signal
    // 訓練データ7     // Training Set 7
    trainingInputs[7][0] = 1; // 入力１     // Input 1
    trainingInputs[7][1] = 1; // 入力２     // Input 2
    trainingInputs[7][2] = 1; // 入力3      // Input 3
    trainingOutputs[7][0] = 1; // 教師信号  // Teacher Signal

    network->init();


    std::cout << "Training..." << std::endl;
    // 訓練データを学習する // Teach the Training Data
    double error = 1.0;
    int count = 0;
    while(error > 0.00001 && count < 50000) {
        count++;
        error = 0.0;
        for(currentTrainingSet = 0; currentTrainingSet < numTrainingSets; currentTrainingSet++) {

            network->getInputLayer()->getNode(0)->setValue(trainingInputs[currentTrainingSet][0]);
            network->getInputLayer()->getNode(1)->setValue(trainingInputs[currentTrainingSet][1]);
            network->getInputLayer()->getNode(2)->setValue(trainingInputs[currentTrainingSet][2]);

            network->teachingSignals[0] = trainingOutputs[currentTrainingSet][0];

            network->feedForward();
            error += (network->calculateOutputError() / (double)numTrainingSets);
            network->backPropagate();

        }
     //   std::cout << count << ": Error = " << error << std::endl; // runs about 100x faster if this is commented out I/O is slow
    }
    std::cout << "Trained in " << count << " trails within an error of " << error << std::endl;
    // print output
    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->getInputLayer()->getNode(2)->setValue(0);
    network->feedForward();
    std::cout << "0 & 0 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->getInputLayer()->getNode(2)->setValue(1);
    network->feedForward();
    std::cout << "0 & 0 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->getInputLayer()->getNode(2)->setValue(0);
    network->feedForward();
    std::cout << "0 & 1 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(0);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->getInputLayer()->getNode(2)->setValue(1);
    network->feedForward();
    std::cout << "0 & 1 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->getInputLayer()->getNode(2)->setValue(0);
    network->feedForward();
    std::cout << "1 & 0 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(0);
    network->getInputLayer()->getNode(2)->setValue(1);
    network->feedForward();
    std::cout << "1 & 0 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->getInputLayer()->getNode(2)->setValue(0);
    network->feedForward();
    std::cout << "1 & 1 & 0 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;

    network->getInputLayer()->getNode(0)->setValue(1);
    network->getInputLayer()->getNode(1)->setValue(1);
    network->getInputLayer()->getNode(2)->setValue(1);
    network->feedForward();
    std::cout << "1 & 1 & 1 = " << network->getOutputLayer()->getNode(0)->getValue() << std::endl;
    std::cout << "Logical AND 3 Inputs Demo End" << std::endl;
}
```

### Resources

[About Neural Networks (English)](/posts/2008-12-24-neural-networks-simple-models.html)

[About Neural Networks (Japanese/日本語)](/posts/2008-12-24-neural-network-jp.html)

[Java Implementation of a Neural Network](/posts/2008-12-25-neural-network-back-error-propagation-java.html)
