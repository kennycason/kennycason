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
