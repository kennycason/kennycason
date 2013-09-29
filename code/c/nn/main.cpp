#include <iostream>
#include "stdlib.h"
#include "Network.h"
#include "math.h"

/*
ABOUT: Train a back error propagation neural network to be able to perform Logical AND, three inputs
AUTHOR: Kenny Cason
WEBSITE: Ken-Soft.com
EMAIL: kenneth.cason@gmail.com
DATE: 7-15-2011
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




/*
 Another Test
 */
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
