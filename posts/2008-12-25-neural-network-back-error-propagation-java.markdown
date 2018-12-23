---
title: Neural Network - Back-Error Propagation - Java
author: Kenny Cason
tags: machine learning, neural network, java, ニューラルネット, 機械学習, 誤差逆伝播法
---

This Neural Network is a command line implementation that uses the Back-Error Propagation learning algorithm. As well, The number of center layers, neurons per center layer, and learning rate are all changeable. The provided Test file teach Logial AND, however other test files can easily be created.

Source can be found on <a href="https://github.com/kennycason/neuralnetwork/" title="GitHub" target="new">GitHub</a>


### Run Jar
The Jar file can be downloaded here: <a href="/code/java/nn01/NN.jar">NN.jar</a>

Below is the command for running NN.jar in the terminal:
```bash
java -jar NN.jar [num center layers] [num center layer neurons] [learning rate]</code>
```
e.g.
```bash
java -jar NN.jar 2 10 0.5
```

Which specifies a neural network with 2 center layers, 10 neurons per center layer, and a learning rate of 0.5.
The result should be similar to below:

```bash
...
3861    1.0020269731147729E-4
3862    1.0017096523909656E-4
3863    1.0013925207545037E-4
3864    1.0010755780406228E-4
3865    1.0007588240848108E-4
3866    1.0004422587226843E-4
3867    1.0001258817900544E-4
3868    9.998096931229542E-5
0 & 0 = 1.96549547328256E-4
0 & 1 = 0.00973058647568516
1 & 0 = 0.009698851371915184
1 & 1 = 0.9854894359859551
```

### Code

The below code is an iterative implementation of a Neural Network trained via Back-Error Propagation.
I wrote this many years ago and it's a bit *verbose*. Matrix implementations run much faster.

That said, I found this approach more easy to conceptually grasp.

My more recent implementations demonstrate Back-Error Propagation as well as other concepts such as Deep Learning and Autoencoders. Check it out: [here](http://kennycason.com/posts/2018-05-04-deep-autoencoder-kotlin.html)

Network.java
```java
package nn;

/**
 * 多数中間層のニューラルネットのクラス（誤差逆伝播法）
 * multi-center layer neural network (back-error propagation algorithm)
 *
 * @author kenneth cason
 */
public class NeuralNetwork {

    private Layer inputLayer; // 入力層: input layer

    private Layer[] centerLayers; // 中間層: middle layers

    private Layer outputLayer; // 出力層: output layer

    private NeuralNetworkConfig config;

    public NeuralNetwork(NeuralNetworkConfig config) {
        this.config = config;
        init();
    }

    /**
     * ニューラルネットを初期化する
     * initialize the neural network
     */
    private void init() {
        inputLayer = new Layer();
        centerLayers = new Layer[config.numCenterLayers];
        for (int i = 0; i < config.numCenterLayers; i++) {
            centerLayers[i] = new Layer();
        }
        outputLayer = new Layer();

        // 出力層: output layer
        outputLayer.init(config.numOutputNodes,
                centerLayers[config.numCenterLayers - 1], null, config.bias);
        // 中間層: middle layer
        for (int i = config.numCenterLayers - 1; i >= 0; i--) {
            if (i == 0) {

                /*
                 * もし中間層の最後の層だったら、出力層と繋がる
                 * if it is the last of the center layers, connect to the output layer
                 */
                if (config.numCenterLayers == 1) {
                    /*
                     * 中間層数が一だから、親層＝入力層、子層＝出力層
                     * because there is only  one center layer / parent layer = input layer, child layer = output layer
                     */
                    centerLayers[i].init(config.numCenterNodes, inputLayer, outputLayer, config.bias);
                } else {
                    centerLayers[i].init(config.numCenterNodes, inputLayer,centerLayers[i + 1], config.bias);
                }
            } else { // 前層は入力層ではない: previous layer does not have an input layer
                /*
                 * もし中間層の最後の層だったら、出力層と繋がる
                 * if it is the last of  the center layers, connect to the output layer
                 */
                if (i == config.numCenterLayers - 1) {
                    centerLayers[i].init(config.numCenterNodes, centerLayers[i - 1], outputLayer, config.bias);
                } else {
                    centerLayers[i].init(config.numCenterNodes, centerLayers[i - 1], centerLayers[i + 1], config.bias);
                }
            }
        }
        // 入力層: input layer
        inputLayer.init(config.numInputNodes, null, centerLayers[0],  config.bias);
        setLearningRate(config.learningRate);
    }

    /**
     * 入力層から出力層まで前向きを伝播する
     * propagate from the input layer to the output layer
     */
    public void feedForward() {
        inputLayer.calculateNeuronValues();
        for (Layer l : centerLayers) {
            l.calculateNeuronValues();
        }
        outputLayer.calculateNeuronValues();
    }

    /**
     * 出力層から入力層まで逆向きに伝播する
     *  back-propagate from the output layer to the input layer
     */
    public void backPropagate() {
        outputLayer.calculateErrors();
        for (int i = centerLayers.length - 1; i >= 0; i--) {
            centerLayers[i].calculateErrors();
            centerLayers[i].adjustWeights();
        }
        inputLayer.adjustWeights();
    }

    /**
     * 出力と教師信号の平均２乗誤差を計算する
     * calculate the average squared error between the
     * output layer and teacher signal
     *
     * @return 平均２乗誤差
     */
    public double calculateError() {
        double error = 0;
        for (int i = 0; i < outputLayer.getNumNeurons(); i++) {
            error += Math.pow(
                    outputLayer.getNeuron(i).getValue()
                            - outputLayer.getTeacherSignal(i), 2);
        }
        error /= outputLayer.getNumNeurons();
        return error;
    }

    /**
     * 各層の各ニューロンの値をゼロにする
     * clear each layer's neuron values
     */
    public void clearAllValues() {
        outputLayer.clearAllValues();
        for (int i = centerLayers.length - 1; i >= 0; i--) {
            centerLayers[i].clearAllValues();
        }
        inputLayer.clearAllValues();
    }

    /**
     * 入力層への一つの入力を設定する
     *  set one value in the input layer
     *
     * @param i ノード番号: node number
     * @param value  値: value
     */
    public void setInput(int i, double value) {
        if (i >= 0 && i < inputLayer.getNumNeurons()) {
            inputLayer.getNeuron(i).setValue(value);
        }
    }

    /**
     * 入力層への各入力を設定する
     * set all values in the input layer
     *
     * @param values 値: value
     */
    public void setInputs(double[] values) {
        if (inputLayer.getNumNeurons() == values.length) {
            for (int i = 0; i < inputLayer.getNumNeurons(); i++) {
                inputLayer.getNeuron(i).setValue(values[i]);
            }
        } else {
            System.out.println("The Input dimensions do not match precisely.");
        }
    }

    /**
     * 入力層への各入力を設定する
     * set all values in the input layer
     *
     * @param values 値: value
     */
    public void setInputs(double[][] values) {
        for (int y = 0; y < config.inputHeight
                && y < inputLayer.getNumNeurons(); y++) {
            for (int x = 0; x < config.inputWidth
                    && x < inputLayer.getNumNeurons(); x++) {
                inputLayer.getNeuron(y * config.inputWidth + x).setValue(
                        values[x][y]);
            }
        }
    }


    public double getInput(int i) {
        if (i >= 0 && i < inputLayer.getNumNeurons()) {
            return inputLayer.getNeuron(i).getValue();
        }
        return Double.MAX_VALUE;
    }

    public double[] getInputs() {
        double[] inputs = new double[inputLayer.getNeurons().length];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = inputLayer.getNeuron(i).getValue();
        }
        return inputs;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public double getInput(int x, int y) {
        if (x >= 0 && x < config.inputWidth && x < inputLayer.getNumNeurons()
                && y >= 0 && y < config.inputHeight
                && y < inputLayer.getNumNeurons()) {
            return inputLayer.getNeuron(y * config.inputWidth + x).getValue();
        }
        return Double.MAX_VALUE; // エラー: ERROR
    }

    /**
     *
     * @return
     */
    public double[][] getInputsXY() {
        double[][] inputs = new double[config.inputWidth][config.inputHeight];
        for (int y = 0; y < config.inputHeight
                && y < inputLayer.getNeurons().length; y++) {
            for (int x = 0; x < config.inputWidth
                    && x < inputLayer.getNeurons().length; x++) {
                // System.out.println("X "+x+ " Y "+y + " OW "+config.inputWidth
                // + " OH "+config.inputHeight);
                inputs[x][y] = inputLayer.getNeuron(y * config.inputWidth + x)
                        .getValue();
            }
        }
        return inputs;
    }

    /**
     * 出力層への一つの出力を得る
     * get a value from the output layer
     *
     * @param i ノード番号: node number
     * @param value 値: value
     */
    public double getOutput(int i) {
        if (i >= 0 && i < outputLayer.getNumNeurons()) {
            return outputLayer.getNeuron(i).getValue();
        }
        return Double.MAX_VALUE; // エラー: ERROR
    }

    /**
     * 出力層への各出力を得る
     * get all output values
     *
     * @return values 値: values
     */
    public double[] getOutputs() {
        double[] outputs = new double[outputLayer.getNeurons().length];
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = outputLayer.getNeuron(i).getValue();
        }
        return outputs;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public double getOutput(int x, int y) {
        if (x >= 0 && x < config.outputWidth && x < outputLayer.getNumNeurons()
                && y >= 0 && y < config.outputHeight
                && y < outputLayer.getNumNeurons()) {
            return outputLayer.getNeuron(y * config.outputWidth + x).getValue();
        }
        return Double.MAX_VALUE; // エラー: ERROR
    }

    /**
     *
     * @return
     */
    public double[][] getOutputsXY() {
        double[][] outputs = new double[config.outputWidth][config.outputHeight];
        for (int y = 0; y < config.outputHeight
                && y < outputLayer.getNeurons().length; y++) {
            for (int x = 0; x < config.outputWidth
                    && x < outputLayer.getNeurons().length; x++) {
                // System.out.println("X "+x+ " Y "+y +
                // " OW "+config.outputWidth + " OH "+config.outputHeight);
                outputs[x][y] = outputLayer.getNeuron(
                        y * config.outputWidth + x).getValue();
            }
        }
        return outputs;
    }

    /**
     * 出力層の教師信号を設定する
     * set the teacher signal for the output layer
     *
     * @param i ノード番号: node number
     * @param value 教師信号の値: teacher signal value
     */
    public void setTeacherSignal(int i, double value) {
        if (i >= 0 && i < outputLayer.getNumNeurons()) {
            outputLayer.setTeacherSignal(i, value);
        }
    }

    /**
     * 出力層の教師信号を設定する
     * set teacher signal values in the output layer
     *
     * @param values  全ての教師信号の値: all of the teacher signal values
     */
    public void setTeacherSignals(double[] values) {
        if (outputLayer.getTeacherSignals().length == values.length) {
            outputLayer.setTeacherSignals(values);
        }
    }

    /**
     * 出力層の教師信号を設定する
     * set teacher signal values in the output layer
     *
     * @param values 全ての教師信号の値: all of the teacher signal values
     */
    public void setTeacherSignals(double[][] values) {
        for (int y = 0; y < config.outputHeight
                && y < outputLayer.getNumNeurons(); y++) {
            for (int x = 0; x < config.outputWidth
                    && x < outputLayer.getNumNeurons(); x++) {
                outputLayer.setTeacherSignal(y * config.outputWidth + x, values[x][y]);
            }
        }
    }

    /**
     * 学習率を設定する
     * set the learning rate
     *
     * @param rate 学習率: learning rate
     */
    public void setLearningRate(double rate) {
        config.learningRate = rate;
        inputLayer.setLearningRate(rate);
        for (int i = 0; i < centerLayers.length; i++) {
            centerLayers[i].setLearningRate(rate);
        }
        outputLayer.setLearningRate(rate);
    }

    /**
     * setters and getter methods
     */
    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer[] getCenterLayers() {
        return centerLayers;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public void setInputWidth(int width) {
        config.inputWidth = width;
    }

    public void setInputHeight(int height) {
        config.inputHeight = height;
    }

    public int getInputWidth() {
        return config.inputWidth;
    }

    public int getInputHeight() {
        return config.inputHeight;
    }

    public int getOutputWidth() {
        return config.outputWidth;
    }

    public int getOutputHeight() {
        return config.outputHeight;
    }

    public boolean isUseBias() {
        return config.bias;
    }

    public double getLearningRate() {
        return config.learningRate;
    }

}
```

Layer.java
```java
package nn;

import java.util.Random;

/**
 * ニューラルネット層のクラス: Neural Network class
 * @author kenneth cason
 */
public class Layer {

    private int numNeurons;    // 神経数: number of neurons

    private double[] teacherSignals;// 教師信号: teacher signal

    private double[] errors;        // 誤差: error

    private double learningRate;    // 学習率: learning rate

    private Neuron[] neurons;

    private boolean useBias = false;

    private double[] biasValues;     // バイアス値（閾値）: bias value bias value

    private double[] biasWeights;     // バイアスの重み:bias weights

    Layer parentLayer;        // 親層: parent layer

    Layer childLayer;        // 子層: child layer

    /*
     * 層を初期化する時、重みをランダムに設定する
     * Set weights randomly upon initialization of the layer
     */
    Random rand;

    public Layer() {
        parentLayer = null;
        childLayer = null;
        rand = new Random();
    }

    /**
     * 層を初期化する: initialize the layer
     * @param numNeurons　この層の神経数: the number of neurons in this layer
     * @param parent　親層: parent layer
     * @param child　子層: child layer
     */
    public void init(int _numNeurons, Layer parent, Layer child, boolean bias) {
        useBias = bias;
        numNeurons = _numNeurons;
        neurons = new Neuron[numNeurons];
        teacherSignals = new double[numNeurons];
        errors = new double[numNeurons];
        for(int i = 0; i < numNeurons; i++) {
            neurons[i] = new Neuron(); // init all values
        }

        if (parent != null) {
            parentLayer = parent;
        }
        if (child != null) {
            childLayer = child;
            // connect each node to each node in the child layer
            for(Neuron node : neurons) {

                for(int i = 0; i < childLayer.numNeurons; i++) {
                    node.connectNode(childLayer.neurons[i]);
                    // 重みとバイアス重みを初期化する: initialize the weights
                    node.setWeight(i, rand.nextInt(200) / 100.0 - 1);
                }
            }
            if(useBias) {
                biasValues = new double[childLayer.numNeurons];
                biasWeights = new double[childLayer.numNeurons];
                // バイアスも-1.0〜1.0
                for (int i = 0; i < biasWeights.length; i++) {
                    biasWeights[i] = rand.nextInt(200) / 100.0 - 1;
                }
                for(int i = 0; i < biasValues.length; i++) {
                    biasValues[i] = -1;
                }
            }
        } else {
             for(Neuron node : neurons) {
                 node.setWeights(null);
             }
             biasValues = null;
        }

        for (int i = 0; i < numNeurons; i++) {
            neurons[i].setValue(0);
            teacherSignals[i] = 0;
            errors[i] = 0;
        }
        if(childLayer != null) {

            for(Neuron node : neurons) {
                for(int i = 0; i < node.getAllLinked().size(); i++) {
                    // 重みとバイアス重みを初期化する: initialize the weights
                    node.setWeight(i, rand.nextInt(200) / 100.0 - 1);
                }
            }
        }
    }

    /**
     * 誤差を計算する
     * calculate the error
     */
    public void calculateErrors() {
        if (childLayer == null) { // 出力層: output layer
            for (int i = 0; i < numNeurons; i++) {
                errors[i] = (teacherSignals[i] - neurons[i].getValue()) * neurons[i].getValue() * (1.0 - neurons[i].getValue());
            }
        } else if (parentLayer == null) { // 入力層: input layer
            for (int i = 0; i < numNeurons; i++) {
                errors[i] = 0.0;
            }
        } else { // 中間層: middle layer
            for (int i = 0; i < numNeurons; i++) {
                double sum = 0;
                for (int j = 0; j < neurons[i].getAllLinked().size(); j++) {
                    sum += childLayer.errors[j] * neurons[i].getWeight(j);
                }
                errors[i] = sum * neurons[i].getValue() * (1.0 - neurons[i].getValue());
            }
        }
    }

    /**
     * 誤差によると、結合荷重を調整する
     * depending on the error, adjust the weights
     */
    public void adjustWeights() {
        if (childLayer != null) {
            // 重みを調整する: adjust the wegihts
            for (int i = 0; i < numNeurons; i++) {
                for (int j = 0; j < neurons[i].getAllLinked().size(); j++) {
                    neurons[i].setWeight(j, neurons[i].getWeight(j)
                            + learningRate * childLayer.errors[j] * neurons[i].getValue());
                }
            }

            if(useBias) {
                for(int i = 0; i < childLayer.numNeurons; i++) {
                    biasWeights[i] += learningRate * childLayer.errors[i] * biasValues[i];
                }
            }
        }
    }

    /**
     * この層の各ニューロンの値をゼロにする
     * clear each layer's neuron values
     */
    public void clearAllValues() {
        for (int i = 0; i < numNeurons; i++) {
            neurons[i].setValue(0);
        }
    }

    /**
     * この層の各ニューロンの活性値を計算する
     * calculate the neuron value for every neuron in this layer
     */
    public void calculateNeuronValues() {
        double sum;
        if (childLayer != null) {
            for (Neuron thisNode : neurons) {
                // dont need to run values of input layer through function
                if(parentLayer != null) {
                    thisNode.setValue(sigmoid(thisNode.getValue()));
                }
                for (int i = 0; i < thisNode.getAllLinked().size(); i++) {
                        sum = 0.0;
                        sum += thisNode.getValue() * thisNode.getWeight(i);
                        if(useBias) {
                            sum += biasValues[i] * biasWeights[i];
                        }
                        thisNode.getAllLinked().get(i).setValue(thisNode.getAllLinked().get(i).getValue() + sum);

                }
            }
        } else {
            // is the output layer, so just run the values through the sigmoid function
            for (Neuron thisNode : neurons) {
                thisNode.setValue(sigmoid(thisNode.getValue()));
            }
        }
    }

    /**
     * シグモイド関数: Sigmoid function
     */
    private double sigmoid(double x) {
        return (1.0 / (1 + Math.exp(-x)));
    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public Neuron getNeuron(int i) {
        return neurons[i];
    }

    public int getNumNeurons() {
        return numNeurons;
    }

    public boolean isUseBias() {
        return useBias;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double rate) {
        learningRate = rate;
    }

    public double getNeuronLearningRateCoefficient(int i) {
        return neurons[i].getLearningRateCoefficient();
    }

    public void setNeuronLearningRateCoefficient(int i, double rate) {
        neurons[i].setLearningRateCoefficient(rate);
    }

    public double getTeacherSignal(int i) {
        return teacherSignals[i];
    }

    public double[] getTeacherSignals() {
        return teacherSignals;
    }

    public void setTeacherSignals(double[] signals) {
        teacherSignals = signals;
    }

    public void setTeacherSignal(int i, double signal) {
        teacherSignals[i] = signal;
    }

}
```

Neuron.java
```java
package nn;


import java.util.LinkedList;

/**
 * Neuron
 * @author Kenneth Cason
 * @version 1.0
 */

public class Neuron {

    private double value; // this contains the value of this node

    private double learningRateCoefficient; // learning rate * learning rate coefficient = total learning rate

    private LinkedList<Neuron> links; // the links to other nodes

    private LinkedList<Double> weights; // weights

    public Neuron() {
        value = 0;
        links = new LinkedList<Neuron>();
        weights = new LinkedList<Double>();
    }

    /**
     * getValue - returns the value
     * @Return double - the value
     */
    public double getValue() {
        return value;
    }

    /**
     * setValue - sets the value of this node
     * @Param double - the value
     */
    public void setValue(double val) {
        value = val;
    }

    /**
     *
     * @param rate
     */
    public void setLearningRateCoefficient(double rate) {
        learningRateCoefficient = rate;
    }

    /**
     *
     * @return
     */
    public double getLearningRateCoefficient() {
        return learningRateCoefficient;
    }

    /**
     * setWeight - sets a specific weight
     * @Param int - the specific array
     * @Param double the value of the weights
     */
    public void setWeight(int i, double weight) {
        weights.set(i, weight);
    }

    /**
     * setWeights - set bias weights
     * @Param LinkedList<Double> weights
     */
    public void setWeights(LinkedList<Double> _weights) {
        weights = _weights;
    }

    /**
     * getWeight - returns a specific weight
     * @Param int - the weight to return
     * @Return double - the nodes weight
     */
    public double getWeight(int i) {
        return weights.get(i);
    }

    /**
     * getWeights - returns all the links weights
     * @Return LinkedList<MLLNode> - the integer LinkedList<MLLNode> of weights
     */
    public LinkedList<Double> getWeights() {
        return weights;
    }

    /**
     * connectNode - connect to another node
     * @Param MLLNode -  node
     */
    public void connectNode(Neuron node) {
        links.add(node);
        weights.add(0.0);
    }

    /**
     * getAllLinked - returns all the linked elements
     * @Return LinkedList<MLLNode> - the linked elements
     */
    public LinkedList<Neuron> getAllLinked() {
        return links;
    }

    /**
     * get - returns a specific linked element
     * @Param int - which element to return
     * @Return MLLNode - the specific linked element
     */
    public Neuron get(int i) {
        return links.get(i);
    }

    /**
     * deletedLinkedElement - deletes a specific linked element
     * @Param int - which element to delete
     * @Return double - the linked element being deleted
     */
    public Neuron remove(int i) {
        weights.remove(i);
        return links.remove(i);
    }

    /**
     * deleteAllLinks - deletes all links
     * @Return LinkedList<MLLNode> - returns all the deleted elements
     */
    public LinkedList<Neuron> deleteAllLinks() {
        LinkedList<Neuron> temp = links;
        links.clear();
        weights.clear();
        return temp;
    }

    /**
     * isEqual - compare 2 pieces of value
     * @Para double - the value to compare
     * @Return boolean - returns true if equal, else false
     */
    public boolean isEqual(double value) {
        if (this.value == value) {
            return true;
        }
        return false;
    }

    /**
     * compare - compare 2 pieces of value
     * @Para double - the value to compare
     * @Return double - returns the absolute value of the difference
     * between values
     */
    public double compare(double value) {
        return Math.abs(this.value - value);
    }

}
```

NeuralNetworkConfig.java
```java
package nn;

public class NeuralNetworkConfig {

    /**
     * 入力層のノード数: number of nodes in the input layer
     */
    public int numInputNodes = 1;

    /**
     * 中間層数: number of center layers
     */
    public int numCenterLayers = 1;

    /**
     * 中間層のノード数: number of nodes in the center layer
     */
    public int numCenterNodes = 10;

    /**
     * 出力層のノード数: number of nodes in the output layer
     */
    public int numOutputNodes = 1;

    public boolean bias = false;

    /**
     *
     */
    public double learningRate = 0.1;

    public int inputWidth = 1;

    public int inputHeight = 1;

    public int outputWidth = 1;

    public int outputHeight = 1;

}
```

NeuralNetworkTest.java
```java
package nn;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

public class NeuralNetworkTest {


    @Test
    public void test() {
        NeuralNetworkConfig config = new NeuralNetworkConfig();
        config.bias = false;
        config.numCenterLayers = 1;
        config.numCenterNodes = 20;
        config.numInputNodes = 2;
        config.numOutputNodes = 1;
        config.learningRate = 0.8;
        NeuralNetwork nn = new NeuralNetwork(config);

        int numTrainData = 4;
        double[][] trainData = new double[numTrainData][nn.getInputs().length];
        double[][] teacherSignal = new double[numTrainData][nn.getOutputs().length];

        trainData[0] = new double[]{0.0, 0.0};  // 0 & 0
        trainData[1] = new double[]{0.0, 1.0};  // 0 & 1
        trainData[2] = new double[]{1.0, 0.0};  // 1 & 0
        trainData[3] = new double[]{1.0, 1.0};  // 1 & 1

        teacherSignal[0] = new double[]{0.0};
        teacherSignal[1] = new double[]{0.0};
        teacherSignal[2] = new double[]{0.0};
        teacherSignal[3] = new double[]{1.0};

        // Train
        double maxError = 0.001;
        double error = Double.MAX_VALUE;
        int count = 0;
        System.out.println("Begin trainings");
        while(error > maxError) {
            error = 0;
            for(int i = 0; i < numTrainData; i++) {
                for(int j = 0; j < config.numInputNodes; j++) {
                    nn.setInput(j, trainData[i][j]);
                    nn.setInput(j, trainData[i][j]);
                }
                for(int j = 0; j < config.numOutputNodes; j++) {
                    nn.setTeacherSignal(j, teacherSignal[i][j]);
                }
                nn.feedForward();
                error += nn.calculateError();
                nn.backPropagate();
                nn.clearAllValues();
            }
            count++;
            error /= numTrainData;
            if(count % 100 == 0) {
                System.out.println("[" + count + "] error = " + error);
            }
        }

        // print results
        for(int i = 0; i < numTrainData; i++) {
            nn.clearAllValues();
            System.out.print("[ ");
            for(int j = 0; j < nn.getInputs().length; j++) {
                nn.setInput(j, trainData[i][j]);
                System.out.print(" " + trainData[i][j]);
            }
            System.out.print("] => [ ");
            nn.feedForward();
            for(int j = 0; j < nn.getOutputs().length; j++) {
                nn.setTeacherSignal(j, teacherSignal[i][j]);
                System.out.print(nn.getOutput(j));
            }
            System.out.println("]");
        }
    }

    @Ignore
    public void sanityCheck() {
        NeuralNetworkConfig config = new NeuralNetworkConfig();
        config.bias = false;
        config.numCenterLayers = 1;
        config.numCenterNodes = 3;
        config.numInputNodes = 2;
        config.numOutputNodes = 1;
        config.learningRate = 0.2;
        NeuralNetwork nn = new NeuralNetwork(config);

        int numTrainData = 1;
        double[][] trainData = new double[numTrainData][nn.getInputs().length];
        double[][] teacherSignal = new double[numTrainData][nn.getOutputs().length];

        trainData[0] = new double[]{1.0, 1.0};  // 1 & 1

        teacherSignal[0] = new double[]{1.0};

        for(int i = 0; i < 100; i++) {
            nn.setInput(0, trainData[0][0]);
            nn.setInput(1, trainData[0][1]);

            nn.setTeacherSignal(0, teacherSignal[0][0]);
            nn.feedForward();
            System.out.println("FEED FORWARD");
            System.out.println("V: " + nn.getInputLayer().getNeuron(0).getValue());
            System.out.println("W: " + nn.getInputLayer().getNeuron(0).getWeights());
            System.out.println("V: " + nn.getInputLayer().getNeuron(1).getValue());
            System.out.println("W: " + nn.getInputLayer().getNeuron(1).getWeights());

            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(0).getWeights());
            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(1).getWeights());
            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(2).getWeights());
            System.out.println(Arrays.toString(nn.getOutputs()));
            nn.calculateError();
            nn.backPropagate();

            System.out.println("BACK PROPAGATE");
            System.out.println("V: " + nn.getInputLayer().getNeuron(0).getValue());
            System.out.println("W: " + nn.getInputLayer().getNeuron(0).getWeights());
            System.out.println("V: " + nn.getInputLayer().getNeuron(1).getValue());
            System.out.println("W: " + nn.getInputLayer().getNeuron(1).getWeights());

            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(0).getWeights());
            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(1).getWeights());
            System.out.println("V: " + nn.getCenterLayers()[0].getNeuron(0).getValue());
            System.out.println("W: " + nn.getCenterLayers()[0].getNeuron(2).getWeights());
            System.out.println(Arrays.toString(nn.getOutputs()));

            nn.clearAllValues();
        }

        // System.out.println(sigmoid(1.096587868));
    }


    public double sigmoid(double x) {
        return (1.0 / (1 + Math.exp(-x)));
    }

}
```

### Resources

Below are some links to A couple articles that give a brief overview about neural networks, including some concepts about developing learning algorithms. Hope they are useful.

[About Neural Networks (English)](/posts/2008-12-24-neural-networks-simple-models.html)

[About Neural Networks (Japanese/日本語)](/posts/2008-12-24-neural-network-jp.html)
