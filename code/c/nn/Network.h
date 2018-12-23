#ifndef __NETWORK_H__
#define __NETWORK_H__

/*
ABOUT: In my implementation a "Network" represents a collection of layers, the way they are not connect is NOT defined
   because depending on your implementation, you may want to connect them differently. This implementation does assume that their is
   an input layer, center layer(s), and an output layer. Though there is nothing preventing the ouput layer to feed back into the input layer
   or any layer connecting to a previous layer (recurrent neural networks).
AUTHOR: Kenny Cason
WEBSITE: kennycason.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
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
