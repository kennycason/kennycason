package nn;
 
import java.util.Random;

/**
 * ニューラルネット層のクラス: Neural Network class
 * @author kenneth cason
 * (Original Source - http://javagame.main.jp/index.php)
 * 平成2008年3月13日
 */
public class Layer {
	int numNodes;			// ノード数: number of nodes
	int numParentNodes;     // 親層のノード数: number of parent nodes
	int numChildNodes;		// 子層のノード数: number of child nodes 
	double[][] weights;		// この層と子層の結合荷重: weights between this laye nad child layer
	double[] neuronValues;	// ノードの活性値: node's functional value
	double[] teacherSignals;// 教師信号: teacher signal
	double[] errors;		// 誤差: error
	double[] biasValues;	// バイアス値（閾値）: bias value 
	double[] biasWeights;	// バイアスの重み: bias weights
	double learningRate;	// 学習率: learning rate
	
	Layer parentLayer;		// 親層: parent layer
	Layer childLayer;		// 子層: child layer
	
	Random rand;				// 層を初期化する時、重みをランダムに設定する: Set weights randomly upon initialization of the layer
	
	public Layer() {
		parentLayer = null;
		childLayer = null;
		rand = new Random();
	}
	
	/**
	 * 層を初期化する: initialize the layer
	 * @param numNodes　この層のノード数: the number of nodes in this layer
	 * @param parent　親層: parent layer
	 * @param child　子層: child layer
	 */
	public void init(int numNodes, Layer parent, Layer child) {
		neuronValues = new double[numNodes];
		teacherSignals = new double[numNodes];
		errors = new double[numNodes];
		if (parent != null) {
			parentLayer = parent;
		}
		if (child != null) {
			childLayer = child;
			System.out.println("seeting up Weights: numNodes ="+numNodes+"numChildNodes ="+numChildNodes);
			weights = new double[numNodes][numChildNodes];
			biasValues = new double[numChildNodes];
			biasWeights = new double[numChildNodes];
		} else {
			weights = null;
			biasValues = null;
			biasWeights = null;
		}
		// 重みを初期化する: initialize the weights
		for (int i = 0; i < numNodes; i++) {
			neuronValues[i] = 0;
			teacherSignals[i] = 0;
			errors[i] = 0;
			if (child != null) {
				for (int j = 0; j < numChildNodes; j++) {
					weights[i][j] = 0;
				}
			}
		}
		// バイアス値を初期化する: initialize the bias values
		if (child != null) {
			for (int i = 0; i < numChildNodes; i++) {
				biasValues[i] = -1;
				biasWeights[i] = 0;
			}
		}
	}
	
	
	/**
	 * 重みをランダムに設定する: randomly set the weights
	 */
	public void setRandomWeights() {
		rand.setSeed(System.currentTimeMillis());
		// 重みは-1.0〜1.0: the weights value ranges from -1.0 to 1.0
		System.out.println("setting weights");
		System.out.println("numNodes" + numNodes);
		System.out.println("numChildNodes"+numChildNodes);
		for (int i = 0; i < numNodes; i++) {
			for (int j = 0; j < numChildNodes; j++) {
				weights[i][j] = rand.nextInt(200) / 100.0 - 1;
			}
		}
		// バイアスも-1.0〜1.0: the bias value als ranges from -1.0 to 1.0
		for (int i = 0; i < numChildNodes; i++) {
			biasWeights[i] = rand.nextInt(200) / 100.0 - 1;
		}
	}
	
	/**
	 * 誤差を計算する: calculate the error
	 */
	public void calculateErrors() {
		if (childLayer == null) { // 出力層: output layer
			for (int i = 0; i < numNodes; i++) {
				errors[i] = (teacherSignals[i] - neuronValues[i]) * neuronValues[i] * (1.0 - neuronValues[i]);
			}
		} else if (parentLayer == null) { // 入力層: input layer
			for (int i = 0; i < numNodes; i++) {
				errors[i] = 0.0;
			}
		} else { // 中間層: middle layer
			for (int i = 0; i < numNodes; i++) {
				double sum = 0;
				for (int j = 0; j < numChildNodes; j++) {
					sum += childLayer.errors[j] * weights[i][j];
				}
				errors[i] = sum * neuronValues[i] * (1.0 - neuronValues[i]);
			}
		}
	}
	
	/**
	 * 誤差によると、結合荷重を調整する: depending on the error, adjust the weights
	 */
	public void adjustWeights() {
		if (childLayer != null) {
			// 重みを調整する: adjust the wegihts
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numChildNodes; j++) {
					weights[i][j] += learningRate * childLayer.errors[j] * neuronValues[i];
				}
			}
			// バイアスも調整する: also adjust the bias values.
			for (int i = 0; i < numChildNodes; i++) {
				biasWeights[i] += learningRate * childLayer.errors[i] * biasValues[i];
			}
		}
	}
	
	/**
	 * この層の各ニューロンの活性値を計算する: calculate the neuron value for every neuron in this layer
	 */
	public void calculateNeuronValues() {
		double sum;
		if (parentLayer != null) {
			for (int i = 0; i < numNodes; i++) {
				sum = 0.0;
				// 親層の出力値と重みをかけて足す: multiply the parent layers output value and weight, then sum the values
				for (int j = 0; j < numParentNodes; j++) {
					sum += parentLayer.neuronValues[j] * parentLayer.weights[j][i];
				}
				// バイアス: bias
				sum += parentLayer.biasValues[i] * parentLayer.biasWeights[i];
				// シグモイド関数を通す: input the sum into the sigmoid function
				neuronValues[i] = sigmoid(sum);
			}
		}
	}
	
	/**
	 * シグモイド関数: sigmoid function
	 */
	private double sigmoid(double x) {
		return 1.0 / (1 + Math.exp(-x));
	}
}
