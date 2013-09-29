package nn;

public class NNTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NeuralNetworkTool nntool = new NeuralNetworkTool();
		nntool.addTrainData("traindata/logicalAND.xml");
		//nntool.addTrainData("traindata/logicalOR.xml");
		//nntool.initToTrainDataSpecifications(0);
		nntool.initToCustomSpefications(45, 1, 30, 15, 0.0001, 100000, 5.5,false);
		nntool.trainNNWithReinforcement();
	//	System.out.println("-------------------------");
		System.out.println(nntool.toStringInput());
	//	System.out.println("-------------------------");
		System.out.println(nntool.toStringOutput());
	//	System.out.println("-------------------------");
	//	nntool.saveNN("nnsave/nnSAV01.xml");
		
		/*
		//inputNodes, centerLayers, centerNodes, outputNodes, error, cycles, learnRate
		nntool.initToCustomSpefications(45, 4, 10, 15, 0.0001, 100000, 4.5);
		nntool.trainNNIteratively();
		System.out.println("-------------------------");
		System.out.println(nntool.toStringInput());
		System.out.println("-------------------------");
		System.out.println(nntool.toStringOutput());
		System.out.println("-------------------------");
		*/
	}

}