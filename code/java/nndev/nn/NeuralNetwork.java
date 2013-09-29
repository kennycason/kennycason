package nn;


/** 
 * 多数中間層のニューラルネットのクラス（誤差逆伝播法とTD法）: multi-center layer neural network (back-error propagation and Temporal Difference (TD) algorithm)
 * @author kenneth cason

 */
public class NeuralNetwork {
	public Layer inputLayer;	// 入力層: input layer
	private Layer[] centerLayer;  // 中間層: middle layers
	private Layer outputLayer;  // 出力層: output layer

	private int inputWidth, inputHeight;
	private int outputWidth, outputHeight;
	
	private double learningRate;		// 学習率: learning rate
	
	private boolean useBias;			// バイアスを使うかどうか
	
	
	// used in TD(lambda)
	private double discountFactor;  	// ラムダ：　lambda - discount factor
	private double[] rewards;			// 報酬: rewards
	
	
	public NeuralNetwork() {
		inputLayer = null;
		centerLayer = null;
		outputLayer = null;
		inputWidth = 0;
		inputHeight = 0;
		outputWidth = 0;
		outputHeight = 0;
		useBias = false;
		learningRate = 0;
		discountFactor = 0;
		rewards = null;
	}
	
	/**
	 * ニューラルネットを初期化する: initialize the neural network
	 * @param numInputneurons  入力層のノード数: number of neurons in the input layer
	 * @param numCL 中間層数: number of center layers
	 * @param numCenterneurons 中間層のノード数: number of neurons in the center layer
	 * @param numOuputneurons  出力層のノード数: number of neurons in the output layer
	 * @Param useBias
	 */
	public void init(int numInputneurons,int numCenterLayers, int numCenterneurons, int numOutputneurons, boolean bias) {
		useBias = bias;
		inputLayer = new Layer();
		centerLayer = new Layer[numCenterLayers];
		for(int i = 0; i < numCenterLayers; i++) {
			centerLayer[i] = new Layer();
		}
		outputLayer = new Layer();
		
		// 出力層: output layer
		//System.out.println("numInputneurons "+numInputneurons);
		//System.out.println("numOutputneurons "+numOutputneurons);
		//System.out.println("numCenterneurons "+numCenterneurons);
		outputLayer.init(numOutputneurons, centerLayer[numCenterLayers-1], null,false);
		//System.out.println("OUTPUT INITED");
		// 中間層: middle layer
		for(int i = numCenterLayers - 1; i >= 0; i--) {
			if(i == 0) {
				if(numCenterLayers == 1) {// もし中間層の最後の層だったら、出力層と繋がる; if it is the last of the center layers, connect to the output layer
					centerLayer[i].init(numCenterneurons, inputLayer, outputLayer,false); // 中間層数が一だから、親層＝入力層、子層＝出力層: because there is only one center layer, parent layer = input layer, child layer = outputlayer
				} else { 
					centerLayer[i].init(numCenterneurons, inputLayer, centerLayer[i+1],false);
				}	
			} else { // 前層は入力層ではない: previous layer does not have an input layer
				if(i == numCenterLayers - 1) {// もし中間層の最後の層だったら、出力層と繋がる; if it is the last of the center layers, connect to the output layer
					centerLayer[i].init(numCenterneurons, centerLayer[i-1], outputLayer,false);
				} else { 
					centerLayer[i].init(numCenterneurons, centerLayer[i-1], centerLayer[i+1],false);
				}	
			}
		}
		// 入力層: input layer
		inputLayer.init(numInputneurons, null, centerLayer[0],false);
	}
	
	/**
	 * 入力層から出力層まで前向きを伝播する: propagate from the input layer to the output layer
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
		for(int i = centerLayer.length - 1; i >= 0 ; i--) {
			centerLayer[i].calculateErrors();
			centerLayer[i].adjustWeights();
		}
		inputLayer.adjustWeights();
	}
	
	/**
	 * 入力層から出力層まで向きに伝播する: forward propagate from the input layer to the output layer
	 */
	public void forwardPropagate() {
		inputLayer.calculateErrorsForward();
		for(int i = 0; i < centerLayer.length; i++) {
			centerLayer[i].calculateErrorsForward();
			centerLayer[i].adjustWeightsForward();
		}
		outputLayer.adjustWeightsForward();
	}
	
	/**
	 * 出力と教師信号の平均２乗誤差を計算する: calculate the average squared error between the output layer and teacher signal
	 * @return 平均２乗誤差
	 */
	public double calculateError() {
		double error = 0;
		for (int i = 0; i < outputLayer.getNumNeurons(); i++) {
			error += Math.pow(outputLayer.getNeuron(i).getValue() - outputLayer.getTeacherSignal(i), 2);
		}
		error /=outputLayer.getNumNeurons();
        return error;
	}

	
		
	/**
	 * 現在の入力と教師信号の平均２乗誤差を計算する: calculate the average squared error between the input layer and teacher signal
	 * @return 平均２乗誤差
	 */
	public double calculateErrorInputLayer() {
		double error = 0;
		for (int i = 0; i < inputLayer.getNumNeurons(); i++) {
			error += Math.pow(inputLayer.getNeuron(i).getValue() - inputLayer.getTeacherSignal(i), 2);
		}
		error /= inputLayer.getNumNeurons();
        return error;
	}
	
	/**
	 * TD法を使ってニューラルネットを更新する - Using TD update the neural network
	 * @param lastMoveOutput
	 */
	public void TDUpdate(double[] lastMoveOutput) {
		outputLayer.calculateTDErrors(getRewards(), lastMoveOutput, getDiscountFactor());

		for(int i = centerLayer.length - 1; i >= 0 ; i--) {
			centerLayer[i].calculateTDErrors(getRewards(), lastMoveOutput, getDiscountFactor());
			centerLayer[i].adjustWeightsTD();
		}
		inputLayer.adjustWeightsTD();
	}
	
	
	
	/**
	 *　入力層への一つの入力を設定する: set one value in the input layer
	 * @param i ノード番号: node number
	 * @param value 値: value
	 */
	public void setInput(int i, double value) {
		if (i >= 0 && i < inputLayer.getNumNeurons()) {
			inputLayer.getNeuron(i).setValue(value);
		}
	}

	/**
	 *　入力層への各入力を設定する: set all values in the input layer
	 * @param values 値: value
	 */
	public void setInputs( double[] values) {
		if(inputLayer.getNumNeurons() ==  values.length) {
			for(int i = 0; i < inputLayer.getNumNeurons(); i++) {
				inputLayer.getNeuron(i).setValue(values[i]);
			}
		} else {
			System.out.println("The Input dimensions do not match precisely.");
		}
	}
	
	/**
	 *　入力層への各入力を設定する: set all values in the input layer
	 * @param values 値: value
	 */
	public void setInputs( double[][] values) {
		for(int y = 0; y < inputHeight && y < inputLayer.getNumNeurons(); y++) {
			for(int x = 0; x < inputWidth && x < inputLayer.getNumNeurons(); x++) {
				inputLayer.getNeuron(y * inputWidth + x).setValue(values[x][y]);
			}
		}
	}
	
	/**
	 * 出力層への一つの出力を得る: get a value from the output layer
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
	 * 出力層への各出力を得る: get all output values
	 * @return values 値: values
	 */
	public double[] getOutputs() {
		double[] outputs = new double[outputLayer.getNeurons().length];
		for(int i = 0; i < outputs.length; i++) {
			outputs[i] = outputLayer.getNeuron(i).getValue();
		}
		return outputs;
	}	
	
	/**
	 * XとYの出力を得る - get the X,Y output
	 * @param x
	 * @param y
	 * @return - 出力の値： output value
	 */
	public double getOutput(int x, int y) {
		if (x >= 0 && x < outputWidth && x < outputLayer.getNumNeurons() &&
				y >= 0 && y < outputHeight &&y < outputLayer.getNumNeurons()) {
			return outputLayer.getNeuron(y * outputWidth + x).getValue();
		}
		return Double.MAX_VALUE; // エラー: ERROR
	}
	
	/**
	 * 出力層への出力をダブルの二次元配列として得る - get the output as a double array
	 * @return 各出力の値： outputs
	 */
	public double[][] getOutputsXY() {
		double[][] outputs = new double[outputWidth][outputHeight];
		for(int y = 0; y < outputHeight && y < outputLayer.getNeurons().length; y++) {
			for(int x = 0; x < outputWidth && x < outputLayer.getNeurons().length; x++) {
				System.out.println("X "+x+ " Y "+y + " OW "+outputWidth + " OH "+outputHeight);
				outputs[x][y] = outputLayer.getNeuron(y * outputWidth + x).getValue();
			}
		}
		return outputs;
	}	
	
	/**
	 * 入力層への一つの入力を得る: get a value from the input layer
	 * @param i ノード番号: node number
	 * @param value 値: value
	 */
	public double getInput(int i) {
		if (i >= 0 && i < inputLayer.getNumNeurons()) {
			return inputLayer.getNeuron(i).getValue();
		}
		return Double.MAX_VALUE; // エラー: ERROR
	}

	
	/**
	 * 入力層への各入力を得る: get all input values
	 * @return values 値: values
	 */
	public double[] getInputs() {
		double[] inputs = new double[inputLayer.getNeurons().length];
		for(int i = 0; i < inputs.length; i++) {
			inputs[i] = inputLayer.getNeuron(i).getValue();
		}
		return inputs;
	}	
	
	/**
	 * 	 XとYの入力を得る: get a value from the input layer
	 * @param x
	 * @param y
	 * @return　入力の値： input
	 */
	public double getInput(int x, int y) {
		if (x >= 0 && x < inputWidth && x < inputLayer.getNumNeurons() &&
				y >= 0 && y < inputHeight &&y < inputLayer.getNumNeurons()) {
			return inputLayer.getNeuron(y * inputWidth + x).getValue();
		}
		return Double.MAX_VALUE; // エラー: ERROR
	}
	
	/**
	 * 入力層への入力をダブルの二次元配列として得る - return all the inputs in a double[][] array
	 * @return　各入力の値： inputs
	 */
	public double[][] getInputsXY() {
		double[][] inputs = new double[inputWidth][inputHeight];
		for(int y = 0; y < inputHeight && y < inputLayer.getNeurons().length; y++) {
			for(int x = 0; x < inputWidth && x < inputLayer.getNeurons().length; x++) {
				//System.out.println("X "+x+ " Y "+y + " IW "+inputWidth + " IH "+inputHeight);
				inputs[x][y] = inputLayer.getNeuron(y * inputWidth + x).getValue();
			}
		}
		return inputs;
	}	
	
	/**
	 * 出力層の教師信号を設定する: set the teacher signal for the output layer
	 * @param i ノード番号: node number 
	 * @param value 教師信号の値: teacher signal value
	 */
	public void setTeacherSignal(int i, double value) {
		if( i >= 0 && i < outputLayer.getNumNeurons()) {
			outputLayer.setTeacherSignal(i, value);
 		}
	}
	
	/**
	 * 出力層の教師信号を設定する: set teacher signal values in the output layer
	 * @param values 全ての教師信号の値: all of the teacher signal values
	 */
	public void setTeacherSignals(double[] values) {
		if (outputLayer.getTeacherSignals().length == values.length) {
			outputLayer.setTeacherSignals(values);
		}
	}	
	
	/**
	 * 出力層の教師信号を設定する: set teacher signal values in the output layer
	 * @param values 全ての教師信号の値: all of the teacher signal values
	 */
	public void setTeacherSignals(double[][] values) {
		for(int y = 0; y < outputHeight && y < outputLayer.getNumNeurons(); y++) {
			for(int x = 0; x < outputWidth && x < outputLayer.getNumNeurons(); x++) {
				outputLayer.setTeacherSignal(y * outputWidth + x, values[x][y]);
			}
		}
	}
	
	/**
	 * 入力層の教師信号を設定する: set the teacher signal for the input layer
	 * @param i ノード番号: node number 
	 * @param value 教師信号の値: teacher signal value
	 */
	public void setTeacherSignalInputLayer(int i, double value) {
		if( i >= 0 && i < inputLayer.getNumNeurons()) {
			inputLayer.setTeacherSignal(i, value);
 		}
	}
	
	/**
	 * 入力層の教師信号を設定する: set teacher signal values in the input layer
	 * @param values 教師信号の値: teacher signal values
	 */
	public void setTeacherSignalsInputLayer(double[] values) {
		if (inputLayer.getTeacherSignals().length == values.length) {
			inputLayer.setTeacherSignals(values);
		}
	}

	/**
	 * 入力層の教師信号を設定する: set teacher signal values in the input layer
	 * @param values 教師信号の値: teacher signal values
	 */
	public void setTeacherSignalsInputLayer(double[][] values) {
		for(int y = 0; y < inputHeight && y < inputLayer.getNumNeurons(); y++) {
			for(int x = 0; x < inputWidth && x < inputLayer.getNumNeurons(); x++) {
				//System.out.println("x="+ x + "  y="+y+"  inputWidth="+inputWidth+"  inputHeight="+inputHeight + " conversion="+(y * inputWidth + x));
				inputLayer.setTeacherSignal(y * inputWidth + x, values[x][y]);
			}
		}
	}
	
	/**
	 * 学習率を設定する: set the learning rate
	 * @param rate 学習率: learning rate
	 */
	public void setLearningRate(double rate) {
		learningRate = rate;
		inputLayer.setLearningRate(rate);
		for(int i = 0; i < centerLayer.length; i++) {
			centerLayer[i].setLearningRate(rate);
		}
		outputLayer.setLearningRate(rate);
	}
	
	/**
	 * 学習率を得る - get the learning rate
	 * @return 学習率：　learning rate
	 */
	public double getLearningRate() {
		return learningRate;
	}
	
	/**
	 * set the discount factor
	 * @param discountFactor
	 */
	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}
	
	/**
	 * set the discount factor
	 * @return
	 */
	public double getDiscountFactor() {
		return discountFactor;
	}
	
	/**
	 * 一つの報酬を設定する - set a single reward
	 * @param i
	 * @param reward
	 */
	public void setReward(int i, double reward) {
		rewards[i] = reward;
	}
	
	/**
	 * 各報酬を設定する - set the rewards
	 * @param rewards
	 */
	public void setRewards(double[] rewards) {
		this.rewards = rewards;
	}
	
	/**
	 * 各報酬を二次元配列として設定する - set the rewards
	 * @param rewards
	 */
	public void setRewards(double[][] rewards) {
		for(int y = 0; y < outputHeight && y < outputLayer.getNumNeurons(); y++) {
			for(int x = 0; x < outputWidth && x < outputLayer.getNumNeurons(); x++) {
				setReward(y * outputWidth + x, rewards[x][y]);
			}
		}
	}
	
	/**
	 * 各報酬を得る　- get all rewards
	 * @return 報酬: rewards
	 */
	public double[] getRewards() {
		return rewards;
	}
	
	/**
	 * 一つの報酬を得る　- get a single reward
	 * @return 報酬: reward
	 */
	public double getReward(int i) {
		return rewards[i];
	}
	
	/**
	 * バイアスを使うかどうか
	 * @return
	 */
	public boolean isUseBias() {
		return useBias;
	}

	/**
	 * 入力層を得る - get the input layer
	 * @return 入力層: input layer
	 */
	public Layer getInputLayer() {
		return inputLayer;
	}
	
	/**
	 * 各中間層を得る - get all center layers
	 * @return 各中間層: center layers
	 */
	public Layer[] getCenterLayers() {
		return centerLayer;
	}
	
	/**
	 * 出力層を得る - get the output layer
	 * @return 出力層: output layer
	 */
	public Layer getOutputLayer() {
		return outputLayer;
	}
	
	/**
	 * 入力層を設定する - set the input layer
	 * @Param 入力層: input layer
	 */
	public void setInputLayer(Layer in) {
		inputLayer = in;
	}
	
	/**
	 * 中間層を設定する - set the center layers
	 * @param 中間層: center layer
	 */
	public void setCenterLayers(Layer[] cl) {
		centerLayer = cl;
	}
	
	/**
	 * 中間層を設定する - set a single center layer
	 * @param 中間層: center layer
	 */
	public void setCenterLayer(int i, Layer cl) {
		centerLayer[i] = cl;
	}
	
	/**
	 * 一つの出力層を設定する - set a single center layer
	 * @param 出力層: center layer
	 */
	public void setOutputLayer(Layer ol) {
		outputLayer = ol;
	}

	/**
	 * set input width
	 * @param width
	 */
	public void setInputWidth(int width) {
		inputWidth = width;
	}
	
	/**
	 * set input height
	 * @param height
	 */
	public void setInputHeight(int height) {
		inputHeight = height;
	}
	
	/**
	 * set output width
	 * @param width
	 */
	public void setOutputWidth(int width) {
		outputWidth = width;
	}

	/**
	 * set output height
	 * @param height
	 */
	public void setOutputHeight(int height) {
		outputHeight = height;
	}
	
	/**
	 * set input width
	 * @return
	 */
	public int getInputWidth() {
		return inputWidth;
	}
	
	/**
	 * get input height
	 * @return
	 */
	public int getInputHeight() {
		return inputHeight;
	}
	
	/**
	 * get output width
	 * @return
	 */
	public int getOutputWidth() {
		return outputWidth;
	}
	
	/**
	 * get output height
	 * @return
	 */
	public int getOutputHeight() {
		return outputHeight;
	}
	
}