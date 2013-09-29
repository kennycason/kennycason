package nn; 

import java.util.LinkedList;
 

/**
 * ANDの学習: teaching of Logical AND using the XML trainData files
 * @author kenneth cason
 */
public class NNTrainAthenBTest {
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		
		/*
		 * traindata/logicalORSimple.xml
		 * traindata/logicalANDSimple.xml
		 * traindata/logicalOR.xml
		 * raindata/logicalAND.xml
		 * traindata/numbers.xml
		 */
		LinkedList<TrainData> train = new LinkedList<TrainData>();
		train.add(new TrainData("traindata/logicalOR.xml"));
		train.add(new TrainData("traindata/logicalAND.xml"));
		System.out.println("***************************************************");
		System.out.println("TEST MEMORY RETENTION: TEACH A THEN B");
		System.out.println("***************************************************");
		
		System.out.println("Number of trainData XML files being loaded: "+train.size());

		/*
		// use the informations in the first trainData file to initialize the neuralnetwork
		// for later cases, the neural network will be defined
		NeuralNetwork nn = new NeuralNetwork();
		nn.init(train.get(0).getNumInputNeurons(), train.get(0).getNumCenterLayers(), 
				train.get(0).getNumCenterNeurons(), train.get(0).getNumOutputNeurons()); 
		//　入力層のノード数、中間層数、中間層のノード数、出力層のノード数: number of nodes in input layer, number of center layers, number of nodes in center layaer, number of nodes in output layer
		/nn.setLearningRate(train.get(0).getLearningRate());
		*/
		NeuralNetwork nn = new NeuralNetwork();
		nn.init(train.get(0).getNumInputNodes(), 7, 20, train.get(0).getNumOutputNodes(),false); 
		//　入力層のノード数、中間層数、中間層のノード数、出力層のノード数: number of nodes in input layer, number of center layers, number of nodes in center layaer, number of nodes in output layer
		nn.setLearningRate(2.0);
		
		/* Displays the input */
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			System.out.println("\nTRAINING SET ["+xmlFile+"]");
			for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
				System.out.println("");
				for(int j = 0; j < train.get(xmlFile).getNumInputNodes(); j++) {
					if(j%3 == 0) {
						System.out.print(" ");
					}
					if(j%train.get(xmlFile).getInputWidth() == 0) {
						System.out.print("\n");
					}
					if(train.get(xmlFile).getTrainingSet()[i][j] > 0.80) {
						System.out.print("■ ");
					} else {
						System.out.print("□ ");	
					}
				}
			}
			System.out.print("\n");
		}
		
		// 訓練データを学習する: teach the training data
		double maxCycles = 150000;
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			int count = 0;
			double desiredError = train.get(xmlFile).getError();
			double error = desiredError + 1; // make it greater than the desiredError
			while (error > desiredError && count < maxCycles) {
				error = 0;
				count++;
				// 各訓練データを誤差が小さくなるまで学習する: teach each piece of training data until the error reaches desired minimal value.
				for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
					// 入力層に値を設定する: set the input layers values
					for(int j = 0; j < train.get(xmlFile).getNumInputNodes(); j++) {
						nn.setInput(j,train.get(xmlFile).getTrainingSet()[i][j]);
					}
					// 教師信号を設定する: set the training signal
					for(int j = 0; j < train.get(xmlFile).getNumOutputNodes(); j++) {
						nn.setTeacherSignal(j,train.get(xmlFile).getDesiredOutputs()[i][j]);
					}
					// 学習開始: begin teaching
					nn.feedForward();
					error += nn.calculateError();
					nn.backPropagate();
				}
				error /= train.get(xmlFile).getNumData();
				//System.out.println(count + "\t" + error);
			}
			System.out.println("Finished Training Input: "+xmlFile);
		}
		
		// 学習完了: Teaching finished
		System.out.println("OUTPUT");
		for(int xmlFile = 0; xmlFile < train.size(); xmlFile++) {
			System.out.println("\nTRAINING SET ["+xmlFile+"]");
			for(int i = 0; i < train.get(xmlFile).getNumData(); i++) {
				System.out.println("\nDATA ["+i+"]");
				for(int j = 0; j < train.get(0).getNumInputNodes(); j++) {
					nn.setInput(j,train.get(xmlFile).getTrainingSet()[i][j]);
				}
				nn.feedForward();	// 出力を計算する: calculate the output layer
				for(int j = 0; j < train.get(0).getNumOutputNodes(); j++) {
					if(j%train.get(xmlFile).getOutputWidth() == 0) {
						System.out.print("\n");
					}
					if(nn.getOutput(j) > 0.75) {
						System.out.print("■ ");
					} else {
						System.out.print("□ ");	
					}
				}
			}
		}
		
	}
}
