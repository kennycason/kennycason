package nn;
 
import java.util.Random;

/**
 * ニューラルネット層のクラス: Neural Network class
 * @author kenneth cason
 * (Original Source - http://javagame.main.jp/index.php)
 * 2008年3月13日
 */
public class Layer {

	private int layerID;
	private int numNeurons;	// 神経数: number of neurons
	private Neuron[] neurons;

	
	private double[] teacherSignals;// 教師信号: teacher signal
	private double[] errors;		// 誤差: error
	private double learningRate;	// 学習率: learning rate
	
	private boolean useBias = false;
	private double[] biasValues; 	// バイアス値（閾値）: bias value bias value 
	private double[] biasWeights; 	// バイアスの重み:bias weights	
	
	Layer parentLayer;		// 親層: parent layer
	Layer childLayer;		// 子層: child layer
	

	//Randomizer rand;			// 層を初期化する時、重みをランダムに設定する: Set weights randomly upon initialization of the layer
	Random rand;
	
	public Layer() {
		parentLayer = null;
		childLayer = null;
		//rand = new Randomizer(System.currentTimeMillis());
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
		//	System.out.println("INITING LAYER: numNeurons ="+numNeurons);
		//	System.out.println("INITING LAYER: this ="+this);
		//	System.out.println("INITING LAYER: childLayer ="+childLayer);
		//	System.out.println("INITING LAYER: childLayer num neurons ="+childLayer.neurons.length);
			// connect each Neuron to each Neuron in the child layer
		    for(Neuron Neuron : neurons) {
		//    	System.out.println("NUM CHILD LAYER NODES: "+childLayer.numNeurons);
		    	for(int i = 0; i < childLayer.numNeurons; i++) {
		    		Neuron.connect(childLayer.neurons[i]);
		    		Neuron.getSynapse(i).setWeight(rand.nextInt(200) / 100.0 - 1); // 重みとバイアス重みを初期化する: initialize the weights
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
			biasWeights = null;
			biasValues = null;
		}
		
		for (int i = 0; i < numNeurons; i++) {
			neurons[i].setValue(0);
			teacherSignals[i] = 0;
			errors[i] = 0;
		}
		if(childLayer != null) {

			for(Neuron Neuron : neurons) {
			//	System.out.println("WALKING OVER NODE, NUM of WEIGHTS :"+Neuron.getWeights().size());
	    		for(int i = 0; i < Neuron.getAllSynapses().size(); i++) {
	    			//System.out.println("WEIGHT: "+Neuron.getWeight(i));
	    			Neuron.getSynapse(i).setWeight(rand.nextInt(200) / 100.0 - 1); // 重みとバイアス重みを初期化する: initialize the weights 
	    		}
	    	}
		}
	}
	
	/**
	 * 誤差を計算する: calculate the error
	 */
	public void calculateErrors() {
		if (childLayer == null) { // 出力層: output layer
			for (int i = 0; i < numNeurons; i++) {
				// marginal error
				errors[i] = (teacherSignals[i] - neurons[i].getValue()) * neurons[i].getValue() * (1.0 - neurons[i].getValue());
				// all or wrong
				/*
				if(teacherSignals[i] != neurons[i].getValue()) {
					errors[i] = 1.0;
				}*/
			}
		} else if (parentLayer == null) { // 入力層: input layer
			for (int i = 0; i < numNeurons; i++) {
				errors[i] = 0.0;
			}
		} else { // 中間層: middle layer
			for (int i = 0; i < numNeurons; i++) {
				double sum = 0;
				for (int j = 0; j < neurons[i].getAllSynapses().size(); j++) {
					sum += childLayer.errors[j] * neurons[i].getSynapse(j).getWeight();
				}
				errors[i] = sum * neurons[i].getValue() * (1.0 - neurons[i].getValue());
			}
		}
	}

	/**
	 * TD誤差を計算する: calculate the TD error
	 */
	public void calculateTDErrors(double[] rewards, double[] lastPrediction, double discountFactor) {
		if (childLayer == null) { // 出力層: output layer
			for (int i = 0; i < numNeurons; i++) {
				errors[i] =  rewards[i] + discountFactor * neurons[i].getValue() - lastPrediction[i];
			}
		} else if (parentLayer == null) { // 入力層: input layer
			for (int i = 0; i < numNeurons; i++) {
				errors[i] = 0.0;
			}
		} else { // 中間層: middle layer
			for (int i = 0; i < numNeurons; i++) {
				double sum = 0;
				for (int j = 0; j < neurons[i].getAllSynapses().size(); j++) {
					sum += childLayer.errors[j] * neurons[i].getSynapse(j).getWeight();
				}
				errors[i] = sum * neurons[i].getValue() * (1.0 - neurons[i].getValue());
			}
		}
	}
	
	
	
	/**
	 * 誤差によると、結合荷重を調整する: depending on the error, adjust the weights
	 */
	public void adjustWeights() {
		if (childLayer != null) {
			// 重みを調整する: adjust the wegihts
			for (int i = 0; i < numNeurons; i++) {
				for (int j = 0; j < neurons[i].getAllSynapses().size(); j++) {
					neurons[i].getSynapse(j).setWeight(neurons[i].getSynapse(j).getWeight()
							+ learningRate * childLayer.errors[j] * neurons[i].getValue());
				}
			}
			if(useBias) {
				//System.out.println("USING BIAS");
				for(int i = 0; i < childLayer.numNeurons; i++) {
					biasWeights[i] += learningRate * childLayer.errors[i] * biasValues[i];
				}
			}
		}
	}
	
	/**
	 * TD誤差によると、結合荷重を調整する: depending on the TD error, adjust the weights
	 */
	public void adjustWeightsTD() {
		if (childLayer != null) {
			// 重みを調整する: adjust the wegihts
			for (int i = 0; i < numNeurons; i++) {
				for (int j = 0; j < neurons[i].getAllSynapses().size(); j++) {
					neurons[i].getSynapse(j).setWeight(neurons[i].getSynapse(j).getWeight()
							+ learningRate * childLayer.errors[j]);

				}
			}
			if(useBias) {
				//System.out.println("USING BIAS");
				for(int i = 0; i < childLayer.numNeurons; i++) {
					biasWeights[i] += learningRate * childLayer.errors[i] * biasValues[i];
				}
			}
		}
	}
	
	/**
	 * 誤差を計算する: calculate the error
	 */
	public void calculateErrorsForward() {
		if (childLayer == null) { // 出力層: output layer
			for (int i = 0; i < numNeurons; i++) {
				errors[i] = 0.0;
			}
		} else if (parentLayer == null) { // 入力層: input layer
			for (int i = 0; i < numNeurons; i++) {
				// marginal
				errors[i] = (teacherSignals[i] - neurons[i].getValue()) * neurons[i].getValue() * (1.0 - neurons[i].getValue());
			/*	if(teacherSignals[i] != neurons[i].getValue()) {
					errors[i] = 1.0;
				}*/
			}
		} else { // 中間層: middle layer
			for (int i = 0; i < numNeurons; i++) {
				double sum = 0;
				for (int j = 0; j < neurons[i].getPrev().size(); j++) {
					sum += parentLayer.errors[j] * parentLayer.neurons[j].getSynapse(i).getWeight();
				}
				errors[i] = sum * neurons[i].getValue() * (1.0 - neurons[i].getValue());
			}
		}
	}
	
	/**
	 * 誤差によると、結合荷重を調整する: depending on the error, adjust the weights
	 */
	public void adjustWeightsForward() {
		if (childLayer != null) {
			// 重みを調整する: adjust the wegihts
			for (int i = 0; i < numNeurons; i++) {
				for (int j = 0; j < childLayer.numNeurons; j++) {
				//	parentLayer.neurons[j].getAxon(i).setWeight(parentLayer.neurons[j].getAxon(i).getWeight()
				//			+ learningRate * error)
					neurons[i].getSynapse(j).setWeight(neurons[i].getSynapse(j).getWeight()
							+ learningRate * errors[i] * neurons[i].getValue());
				//	if(Math.abs(neurons[i].getAxon(j).getWeight()) < .0001) {
					//	neurons[i].deleteLinkedElement(j);
					//	System.out.println("Link Deleted");
			//		}
				}
			}
			if(useBias) {
				//System.out.println("USING BIAS");
				for(int i = 0; i < parentLayer.numNeurons; i++) {
					biasWeights[i] += learningRate * parentLayer.errors[i] * biasValues[i];
				}
			}
		}
	}
	
	/**
	 * この層の各ニューロンの活性値を計算する: calculate the neuron value for every neuron in this layer
	 */
	public void calculateNeuronValues() {
		double sum;
		if (childLayer != null) {
			for (Neuron thisNeuron : neurons) {
				if(parentLayer != null) { // dont need to run values of inputlayer through function
					thisNeuron.setValue(sigmoid(thisNeuron.getValue()));
				}
				//if(thisNeuron.getValue() > thisNeuron.getThreshold()) {
				for (int i = 0; i < thisNeuron.getAllSynapses().size(); i++) {
				//	if(thisNeuron.getValue() > thisNeuron.getThreshold()) {
						sum = 0.0;
						sum += thisNeuron.getValue() * thisNeuron.getSynapse(i).getWeight();
						if(useBias) {
							sum += biasValues[i] * biasWeights[i];
						}
						thisNeuron.getSynapse(i).getConnected().setValue(thisNeuron.getSynapse(i).getConnected().getValue() + sum);
			//		} else {
					//	System.out.println("Didnt fire");
				//	}
				}	
			} 
		} else { // is the output layer, so just run the values through the sigmoid function
			for (Neuron thisNeuron : neurons) {
				thisNeuron.setValue(sigmoid(thisNeuron.getValue()));
			}
		}
		//System.out.println("Calculating Neuron Values");
	}
	
	/**
	 * シグモイド関数: Sigmoid function
	 */
	private double sigmoid(double x) {
		return (1.0 / (1 + Math.exp(-x)));
	}
	
	/**
	 * printList - prints the contents of the list recursively
	 * @Param Neuron - the Neuron being printed
	 */ 
	public String printList(Neuron neuron, String s, int depth) {
		if(!neuron.getCheck()) {
			neuron.check();
			for(int i = 0; i < neuron.getAllSynapses().size(); i++) {
				if(neuron.getAllSynapses().size() > 0) {
					for(int j = 0; j < depth; j++) {
						s += "\t";
					}
					s += ("\\__["+neuron.getSynapse(i).getConnected().getValue()+"] WGT["+neuron.getSynapse(i).getWeight()+"]\n");
				}
				s = printList(neuron.getSynapse(i).getConnected(), s, depth +1);
			}
		}
		return s;
	} 
	
	/**
	 * この層のニューロンを得る - get the neurons in this layer
	 * @return  ニューロン： neuron
	 */
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	/**
	 * この層のニューロンを一つ得る　- get one neuron from this layer
	 * @param i
	 * @return ニューロン： neuron
	 */
	public Neuron getNeuron(int i) {
		return neurons[i];
	}
	
	/**
	 * この層のニューロンの数を得る　- get the number of neurons in this layer
	 * @return ニューロンの数： number of neurons
	 */
	public int getNumNeurons() {
		return numNeurons;
	}
	
	/**
	 * バイアスを使うかどうか
	 * @return
	 */
	public boolean isUseBias() {
		return useBias;
	}
	
	/**
	 * 学習率を得る - get the learning rate
	 * @return 学習率:　learning rate
	 */
	public double getLearningRate() {
		return learningRate;
	}
	
	/**
	 * 学習率を設定する - set the learning rate
	 * @param 学習率:　learning rate
	 */
	public void setLearningRate(double rate) {
		learningRate = rate;
	}
	
	/**
	 * ニューロンの学習率係数を得る - get the neuron learning rate coefficient
	 * @param i
	 * @return ニューロンの学習率係数:　neuron learning rate coefficient
	 */
	public double getNeuronLearningRateCoefficient(int i) {
		return neurons[i].getLearningRateCoefficient();
	}
	
	/**
	 * ニューロンの学習率係数を設定する - set the neuron learning rate coefficient
	 * @param i
	 * @param ニューロンの学習率係数:　neuron learning rate coefficient
	 */
	public void setNeuronLearningRateCoefficient(int i, double rate) {
		neurons[i].setLearningRateCoefficient(rate);
	}
	
	/**
	 * 一つの学習信号を得る - get teaching signals
	 * @param i
	 * @return 学習信号: teaching signal
	 */
	public double getTeacherSignal(int i) {
		return teacherSignals[i];
	}
	
	/**
	 * 各学習信号を得る - get teaching signals
	 * @return 各学習信号: teaching signals
	 */
	public double[] getTeacherSignals() {
		return teacherSignals;
	}
	
	/**
	 * 一つのニューロンの誤差を得る - get neuron error
	 * @param i
	 * @return 誤差：　error
	 */
	public double getNeuronError(int i) {
		return errors[i];
	}
	
	/**
	 * 各ニューロンの誤差を得る - get neuron error
	 * @return 誤差：　errors
	 */
	public double[] getNeuronErrors() {
		return errors;
	}
	
	/**
	 * 一つのニューロンの誤差を設定する - set neuron error
	 * @param i
	 * @param 誤差：　error
	 */
	public void setNeuronError(int i, double e) {
		errors[i] = e;
	}
	
	/**
	 * 各ニューロンの誤差を設定する - set neuron errors
	 * @param 誤差：　errors
	 */
	public void setNeuronErrors(double[] e) {
		errors = e;
	}
	
	/**
	 * 各学習信号を設定する - set teacher signals
	 * @param signals
	 */
	public void setTeacherSignals(double[] signals) {
		teacherSignals = signals;
	}
	
	/**
	 * 一つの学習信号を設定する - set a teacher signal
	 * @Param i
	 * @param signals
	 */
	
	public void setTeacherSignal(int i, double signal) {
		teacherSignals[i] = signal;
	}
	
	/**
	 * この層のIDを得る
	 * @return　ID
	 */
	public int getLayerID() {
		return layerID;
	}
	
	/**
	 * この層のIDを設定する
	 * @param ID
	 */
	public void setLayerID(int id) {
		layerID = id;
	}
	
	/**
	 * 一つのバイアスの結合荷重を得る - get a bias weight
	 * @param i
	 * @return バイアスの結合荷重: bias weight
	 */
	public double getBiasWeight(int i) {
		return biasWeights[i];
	}
	
	/**
	 * 各バイアスの結合荷重を得る - get the bias weights
	 * @return バイアスの結合荷重: bias weights
	 */
	public double[] getBiasWeights() {
		return biasWeights;
	}
	
	/**
	 * 一つのバイアスの結合荷重を設定する
	 * @param i
	 * @param バイアスの結合荷重: bias weight
	 */
	public void setBiasWeight(int i, double value) {
		biasWeights[i] = value;
	}

	/**
	 * 一つのバイアスの値を得る - get a bias value
	 * @param i
	 * @return バイアスの値: bias value
	 */
	public double getBiasValue(int i) {
		return biasValues[i];
	}
	
	/**
	 * 各バイアスの値を得る　- get bias values
	 * @return バイアスの値: bias values
	 */
	public double[] getBiasValues() {
		return biasValues;
	}
	
	/**
	 * 一つのバイアスの値を設定する - set a bias value
	 * @param i
	 * @param バイアスの値: bias value
	 */
	public void setBiasValue(int i, double value) {
		biasValues[i] = value;
	}
	
	/**
	 * toString
	 */ 
	public String toString() {
		unCheckAllNeurons();
		String s = "";
		for(int i = 0; i < neurons.length; i++) {
			s += "["+neurons[i].getValue()+"]\n";
			s += printList(neurons[i], s, 0) + "\n";
		}
		return s;
	} 
	
	/**
	 * 再帰的に各ニューロンをチェックを消す。相互結合のニューラルネットに応用する
	 * unCheckAllNeurons - uncheck all the neurons recursively from a given root Neuron. Very critical because it prevents 
	 *        the recursive loops in other functions to not get stuck in infinite loops.
	 */
	public void unCheckAllNeurons() {
		for(Neuron n : neurons) {
			unCheckAllNeurons(n);
		}
	}

	/**
	 * 再帰的に各ニューロンをチェックを消す。相互結合のニューラルネットに応用する
	 * unCheckAllNeurons - uncheck all the neurons recursively from a given root Neuron. Very critical because it prevents 
	 *        the recursive loops in other functions to not get stuck in infinite loops.
	 * @Param Neuron - the root Neuron being unchecked
	 */
	public void unCheckAllNeurons(Neuron root) {
		if(!root.getCheck()) {
			return;
		}
		root.unCheck();
		for(int i = 0; i < root.getAllSynapses().size(); i++) {
			unCheckAllNeurons(root.getSynapse(i).getConnected()); 
		}
	}
	
}
