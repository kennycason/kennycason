#include <iostream>
#include "stdlib.h"
#include "Network.h"
#include "math.h"
#include "BackErrorPropagation.h"

/*
ABOUT: Train a back error propagation neural network to be able to perform Logical AND, three inputs
AUTHOR: Kenny Cason
WEBSITE: kennycason.com
EMAIL: kenneth.cason@gmail.com
DATE: 12-1-2009
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
