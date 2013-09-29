package nn;
/** 
 * 多数中間層のニューラルネットのクラス（誤差逆伝播法）: multi-center layer neural network (back-error propagation algorithm)
 * @author kenneth cason
 * (Original Source - http://javagame.main.jp/index.php)
 * 平成2008年3月13日
 */
public class NeuralNetwork {
	private Layer inputLayer;	// 入力層: input layer
	private Layer[] centerLayer;  // 中間層: middle layers
	private Layer outputLayer;  // 出力層: output layaer
	
	public NeuralNetwork() {
		inputLayer = null;
		outputLayer = null;
	}
	
	/**
	 * ニューラルネットを初期化する: initialize the neural network
	 * @param numInputNodes  入力層のノード数: number of nodes in the input layer
	 * @param numCL 中間層数: number of center layers
	 * @param numCenterNodes 中間層のノード数: number of nodes in the center layer
	 * @param numOuputNodes  出力層のノード数: number of nodes in the output layer
	 */
	public void init(int numInputNodes,int numCenterLayers, int numCenterNodes, int numOutputNodes) {
		inputLayer = new Layer();
		centerLayer = new Layer[numCenterLayers];
		for(int i = 0; i < numCenterLayers; i++) {
			centerLayer[i] = new Layer();
		}
		outputLayer = new Layer();
		// 入力層: input layer
		inputLayer.numNodes = numInputNodes;
		inputLayer.numChildNodes = numCenterNodes;
		inputLayer.numParentNodes = 0; // 入力層は親層がない: the input layer has no parent layer
		inputLayer.init(numInputNodes, null, centerLayer[0]);
		inputLayer.setRandomWeights();
		// 中間層: middle layer
		for(int i = 0; i < numCenterLayers; i++) {
			centerLayer[i].numNodes = numCenterNodes;
			if(i == 0) {
				centerLayer[i].numParentNodes = numInputNodes;
				if(numCenterLayers == 1) {// もし中間層の最後の層だったら、出力層と繋がる; if it is the last of the center layers, connect to the output layer
					centerLayer[i].numChildNodes = numOutputNodes;
					centerLayer[i].init(numCenterNodes, inputLayer, outputLayer); // 中間層数が一だから、親層＝入力層、子層＝出力層: because there is only one center layer, parent layer = input layer, child layer = outputlayer
				}
				else { 
					centerLayer[i].numChildNodes = numCenterNodes;
					centerLayer[i].init(numCenterNodes, inputLayer, centerLayer[i+1]);
				}	
			}
			else { // 前層は入力層ではない: previous layer does not have an input layer
				centerLayer[i].numParentNodes = numCenterNodes;
				if(i == numCenterLayers - 1) {// もし中間層の最後の層だったら、出力層と繋がる; if it is the last of the center layers, connect to the output layer
					centerLayer[i].numChildNodes = numOutputNodes;
					centerLayer[i].init(numCenterNodes, centerLayer[i-1], outputLayer);
				}
				else { 
					centerLayer[i].numChildNodes = numCenterNodes;
					centerLayer[i].init(numCenterNodes, centerLayer[i-1], centerLayer[i+1]);
				}	
			}
			centerLayer[i].setRandomWeights();
		}
		// 出力層: output layer
		outputLayer.numNodes = numOutputNodes;
		outputLayer.numChildNodes = 0; // 出力層は子層がない: the output layer does not have a child layer
		outputLayer.numParentNodes = numCenterNodes;
		outputLayer.init(numOutputNodes, centerLayer[numCenterLayers-1], null);
		outputLayer.setRandomWeights();
	}
	
	/**
	 *　入力層への一つの入力を設定する: set one value in the input layer
	 * @param i ノード番号: node number
	 * @param value 値: value
	 */
	public void setInput(int i, double value) {
		if (i >= 0 && i < inputLayer.numNodes) {
			inputLayer.neuronValues[i] = value;
		}
	}

	/**
	 *　入力層への各入力を設定する: set all values in the input layer
	 * @param values 値: value
	 */
	public void setInputs( double[] values) {
		if(inputLayer.neuronValues.length == values.length) {
			inputLayer.neuronValues = values;
		}
	}
	
	/**
	 * 出力層への一つの出力を得る: get a value from the output layer
	 * @param i ノード番号: node number
	 * @param value 値: value
	 */
	public double getOutput(int i) {
		if (i >= 0 && i < outputLayer.numNodes) {
			return outputLayer.neuronValues[i];
		}
		return Double.MAX_VALUE; // エラー: ERROR
	}
	
	/**
	 * 出力層への各出力を得る: get all output values
	 * @return values 値: values
	 */
	public double[] getOutput() {
		return outputLayer.neuronValues;
	}	
	
	/**
	 * 教師信号を設定する: set the teacher signal
	 * @param i ノード番号: node number 
	 * @param value 教師信号の値: teacher signal value
	 */
	public void setTeacherSignal(int i, double value) {
		if( i >= 0 && i < outputLayer.numNodes) {
			outputLayer.teacherSignals[i] = value;
 		}
	}
	
	/**
	 * 全ての教師信号を設定する: set all teacher signal values
	 * @param values 全ての教師信号の値: all of the teacher signal values
	 */
	public void setTeacherSignals(double[] values) {
		if (outputLayer.teacherSignals.length == values.length) {
			outputLayer.teacherSignals = values;
		}
	}	
	
	/**
	 * 入力層から出力層まで前向きを伝播する: propogate from the input layer to the output layer
	 */
	public void feedForward() {
		inputLayer.calculateNeuronValues();
		for(int i = 0; i < centerLayer.length; i++) {
			centerLayer[i].calculateNeuronValues();
		}
		outputLayer.calculateNeuronValues();
	}
	
	/**
	 * 出力層から入力層まで逆向きに伝播する: back-propagate from the output layer to the input layer
	 */
	public void backPropagate() {
		outputLayer.calculateErrors();
		for(int i =  centerLayer.length - 1; i >= 0 ; i--) {
			centerLayer[i].calculateErrors();
			centerLayer[i].adjustWeights();
		}
		inputLayer.adjustWeights();
	}
	
	/**
	 * 出力と教師信号の平均２乗誤差を計算する: calculate the average squared error between the output layer and teacher signal
	 * @return 平均２乗誤差
	 */
	public double calculateError() {
		double error = 0;
		for (int i = 0; i < outputLayer.numNodes; i++) {
			error += Math.pow(outputLayer.neuronValues[i] - outputLayer.teacherSignals[i], 2);
		}
		error /=outputLayer.numNodes;
        return error;
	}
	
	/**
	 * 学習率を設定する: set the learning rate
	 * @param rate 学習率: learning rate
	 */
	public void setLearningRate(double rate) {
		inputLayer.learningRate = rate;
		for(int i = 0; i < centerLayer.length; i++) {
			centerLayer[i].learningRate = rate;
		}
		outputLayer.learningRate = rate;
	}
	
}