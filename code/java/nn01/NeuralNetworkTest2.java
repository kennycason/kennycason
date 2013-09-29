package nn; 
/**
 * IQテストの質問
 * @author kenneth cason
 * 平成2008年3月13日
 */
public class NeuralNetworkTest2 {
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		NeuralNetwork nn = new NeuralNetwork();
		int cLayers = 1, cNodes = 10; 
		double lRate = 0.5;
		if(args.length == 3) {
			cLayers = Integer.parseInt(args[0]);
			cNodes = Integer.parseInt(args[1]);
			lRate = Double.parseDouble(args[2]);
		} 
		nn.init(2, cLayers, cNodes, 1); //　入力層のノード数、中間層数、中間層のノード数、出力層のノード数: number of nodes in input layaer, number of center layers, number of nodes in center layaer, number of nodes in output layer
		nn.setLearningRate(lRate);
		// 000　　000
		// 101　　101
		// 000　　000
		// 011　　001
		// 000　　000
		
		
		// 訓練データの作成
		double[] trainingSet = new double[15];
		double[] desiredOutputs = new double[15];
		// 訓練データ0  (0＝１　1＝０）
		// 000
		// 101
		// 000
		// 011
		// 000
		trainingSet[0] = 1;
		trainingSet[1] = 1;
		trainingSet[2] = 1;
		trainingSet[3] = 0;
		trainingSet[4] = 1;
		trainingSet[5] = 0;
		trainingSet[6] = 1;
		trainingSet[7] = 1;
		trainingSet[8] = 1;
		trainingSet[9] = 1;
		trainingSet[10] = 0;
		trainingSet[11] = 0;
		trainingSet[12] = 1;
	    trainingSet[13] = 1;
	    trainingSet[14] = 1;
		// 000
	    // 101
		// 000
		// 001
		// 000
	    desiredOutputs[0] = 1;
	    desiredOutputs[1] = 1;
	    desiredOutputs[2] = 1;
	    desiredOutputs[3] = 0;
	    desiredOutputs[4] = 1;
	    desiredOutputs[5] = 0;
	    desiredOutputs[6] = 1;
	    desiredOutputs[7] = 1;
	    desiredOutputs[8] = 1;
	    desiredOutputs[9] = 1;
	    desiredOutputs[10] = 1;
	    desiredOutputs[11] = 0;
	    desiredOutputs[12] = 1;
	    desiredOutputs[13] = 1;
	    desiredOutputs[14] = 1;			
		
		// 訓練データを学習する
		double error = 1.0;
		int count = 0;
		// 入力層に値を設定する
		for(int j = 0; j < trainingSet.length; j++ ) {
			nn.setInput(j, trainingSet[j]);
		}
		// 教師信号を設定する
		for(int j = 0; j < desiredOutputs.length; j++ ) {
			nn.setTeacherSignal(j, desiredOutputs[j]);
		}
		// 訓練データを誤差が小さくなるまで学習する
		while (error > 0.0001 ) { 
			error = 0;
			count++;
				// 学習開始
				nn.feedForward();
				error += nn.calculateError();
				nn.backPropagate();
			System.out.println(count + "\t" + error);
		}
		// 000
		// 101
		// 000
		// 011
		// 000
		nn.setInput(0, 1);
		nn.setInput(1, 1); 
		nn.setInput(2, 1);
		nn.setInput(3, 0);
		nn.setInput(4, 1);
		nn.setInput(5, 0);
		nn.setInput(6, 1);
		nn.setInput(7, 1);
		nn.setInput(8, 1);
		nn.setInput(9, 1);
		nn.setInput(10, 0);
		nn.setInput(11, 0);
		nn.setInput(12, 1);
		nn.setInput(13, 1);
		nn.setInput(14, 1); 
		nn.feedForward();
		System.out.print("Actual Result");
		for(int i = 0; i < nn.getOutput().length; i++) {
			//System.out.println(i+" = "+nn.getOutput(i));
			if(i % 3 == 0) {
				System.out.println("");
			}
			if(nn.getOutput(i) > 0.7 && nn.getOutput(i) <= 1.0) {
				System.out.print("0");
			} 
			else if(nn.getOutput(i) < 0.03 ){
				System.out.print("1");
			}
			//System.out.println(nn.getOutput(i));
		}
		System.out.println("\nExpected Result");
		System.out.println("000\n101\n000\n001\n000");
	}
}
