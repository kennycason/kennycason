package nn; 
/**
 * ANDの学習: teaching of Logical AND
 * @author kenneth cason
 * (Original Source - http://javagame.main.jp/index.php)
 * 平成2008年3月13日
 */
public class NeuralNetworkTest {
	/**
	 * @param args 
	 */
	public static void main(String[] args) {
		NeuralNetwork nn = new NeuralNetwork();
		int cLayers = 1, cNodes = 2; 
		double lRate = 0.5;
		if(args.length == 3) {
			cLayers = Integer.parseInt(args[0]);
			cNodes = Integer.parseInt(args[1]);
			lRate = Double.parseDouble(args[2]);
		} 
		nn.init(2, cLayers, cNodes, 1); //　入力層のノード数、中間層数、中間層のノード数、出力層のノード数: number of nodes in input layaer, number of center layers, number of nodes in center layaer, number of nodes in output layer
		nn.setLearningRate(lRate);
		
		// 訓練データの作成: setting up of training data
		double[][] trainingSet = new double[4][3];
		// 訓練データ０: training data 1
		trainingSet[0][0] = 0;	// 入力１: input 1
		trainingSet[0][1] = 0;	// 入力２: input 2
		trainingSet[0][2] = 0;	// 教師信号: training value
		// 訓練データ2: training data 2
		trainingSet[1][0] = 0;
		trainingSet[1][1] = 1;
		trainingSet[1][2] = 0;
		// 訓練データ3: training data 3
		trainingSet[2][0] = 1;
		trainingSet[2][1] = 0;
		trainingSet[2][2] = 0;
		// 訓練データ4: training data 4
		trainingSet[3][0] = 1;
		trainingSet[3][1] = 1;
		trainingSet[3][2] = 1;
		
		// 訓練データを学習する: teach the training data
		double error = 1.0;
		int count = 0;
		while (error > 0.0001 ) {
			error = 0;
			count++;
			// 各訓練データを誤差が小さくなるまで学習する: teach each piece of training data until the error reaches desired minimal value.
			for(int i=0; i < 4; i++) {
				// 入力層に値を設定する: set the input layers values
				nn.setInput(0, trainingSet[i][0]);
				nn.setInput(1, trainingSet[i][1]);
				// 教師信号を設定する: set the training signal
				nn.setTeacherSignal(0, trainingSet[i][2]);
				// 学習開始: begin teaching
				nn.feedForward();
				error += nn.calculateError();
				nn.backPropagate();
			}
			error /= 4.0;
			System.out.println(count + "\t" + error);
		}
		// 学習完了: Teaching finished
		nn.setInput(0, 0);	// 入力１: input 1
		nn.setInput(1, 0);	// 入力２: input 2
		nn.feedForward();	// 出力を計算する: calculate the output layer
		System.out.println("0 & 0 = "+nn.getOutput(0));
		nn.setInput(0, 0);
		nn.setInput(1, 1);
		nn.feedForward();
		System.out.println("0 & 1 = "+nn.getOutput(0));	
		nn.setInput(0, 1);
		nn.setInput(1, 0);
		nn.feedForward();
		System.out.println("1 & 0 = "+nn.getOutput(0));
		nn.setInput(0, 1);
		nn.setInput(1, 1);
		nn.feedForward();
		System.out.println("1 & 1 = "+nn.getOutput(0));		
	}
}
