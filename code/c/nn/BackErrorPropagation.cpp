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
