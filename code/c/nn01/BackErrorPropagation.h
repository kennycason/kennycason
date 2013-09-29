#ifndef __BACK_ERROR_PROPAGATION_H__
#define __BACK_ERROR_PROPAGATION_H__

/*
ABOUT: This class contains all the overhead needed to initialize and train a neural network using Back error propagation.
AUTHOR: Kenny Cason
WEBSITE: Ken-Soft.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
*/

#include "Cell.h"
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
    static void backPropagate(Cell *cell, struct parameters &params);
    static void backPropagate(Cell *cell, struct parameters &params, int i);

    /**
     * 入力層から出力層まで前向きを伝播する: propagate from the input layer to the output layer
     */
    static void feedForward(Cell *cell);
    static void feedForward(Cell *cell, int i);

    /**
     * 出力と教師信号の平均２乗誤差を計算する: calculate the average squared error between the output layer and teacher signal
     * @return 平均２乗誤差
     */
    static double calculateOutputError(Cell *cell, struct parameters &params);

    static void initNetwork(Cell* cell);

};


#endif

