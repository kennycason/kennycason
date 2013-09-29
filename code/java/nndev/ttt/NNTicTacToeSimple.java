package ttt;

import nn.NeuralNetworkTool;

public class NNTicTacToeSimple {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NNTicTacToeSimple();

	}
	
	private TicTacToe ttt;
	private NeuralNetworkTool nntool;

	public NNTicTacToeSimple() {
		

		nntool = new NeuralNetworkTool();
		nntool.addTrainData("traindata/tictactoe.xml");
		nntool.initToTrainDataSpecifications(0);
		nntool.trainNNWithReinforcement();
		System.out.println("***************************************************");
		System.out.println(nntool.toStringInput());
		System.out.println("***************************************************");
		System.out.println(nntool.toStringOutput());

		
		int maxCycles = 5000;
		 
		ttt = new TicTacToe();
		int count = 0;
		int move = -2;

	    move = -2;
	    count = 0;
		while(/*error > desiredError &&*/ count < maxCycles) {

			ttt.newGame();
			System.out.println("New Game");
			while(ttt.checkWin() == -2) {
				if(ttt.getTurn() == 1) {
					//System.out.println("Neural Networks TURN");
					for(int i = 0; i < nntool.getNN().getInputLayer().getNumNeurons(); i++) {
					//	System.out.println("ttt.getBoard()[i] ="+ttt.getBoard()[i]);
						nntool.getNN().setInput(i, ttt.getBoard()[i]);
					}
					move = getMove();
					if(move > -1) {
						ttt.getBoard()[move] = ttt.P1VALUE;
						nntool.getNN().setInput(move, ttt.P1VALUE);
					}
					ttt.setTurn(2);
					ttt.repaint();
				}
			}

			if(ttt.checkWin() == ttt.P1WIN) {
				System.out.println("Neural Network Wins!");
			} else if(ttt.checkWin() == ttt.P2WIN) {
				System.out.println("You Win!");/*
				if(move > -1) {
					System.out.println("NN learning from mistake");
					//System.out.println("Confidence in last move ["+nntool.getNN().getOutput(move)+"]");
					nntool.getNN().getOutputLayer().setNeuronError(move,1);
					for(int i = 0; i < nntool.getNN().getOutputLayer().getNumNeurons(); i++) {
						//	System.out.println("ttt.getBoard()[i] ="+ttt.getBoard()[i]);
						nntool.getNN().setTeacherSignal(i, 0);
					}
					nntool.getNN().setTeacherSignal(ttt.getP2LastMove(), 1);
					nntool.getNN().backPropagate();
				}*/
			} else if(ttt.checkWin() == 0) {
				System.out.println("Draw!");
			}
		//	error = 0;
			count++;
			ttt.newGame();	

		}

	}
	
	/**
	 * find the move that the neural network most likely wants to do
	 * @return
	 */
	public int getMove() {
		int move = -1;
		double tendancy = -2;/*
		for(int i = 0; i < nn.getInputLayer().getNumNeurons(); i++) {
			nn.setInput(i, ttt.getBoard()[i]);
		//	System.out.println("input "+ttt.getBoard()[i]);
		}
		nn.feedForward();
		for(int i = 0; i < nn.getOutputLayer().getNumNeurons(); i++) {
			System.out.println("output "+i+" : "+nn.getOutput(i));
			if(Math.abs(ttt.getBoard()[i] - nn.getOutput(i)) > 0.2) { 
				if(ttt.getBoard()[i] == 0) {
					move = i;
				}
				
			}
		}	*/
		//System.out.println("Neural network picks "+move);
		
		for(int i = 0; i < nntool.getNN().getInputLayer().getNumNeurons(); i++) {
			nntool.getNN().setInput(i, ttt.getBoard()[i]);
		}
		nntool.getNN().feedForward();
		for(int i = 0; i < nntool.getNN().getInputLayer().getNumNeurons(); i++) {
			if(ttt.getBoard()[i] == 0) { // space is empty
				//System.out.println("output "+i+" "+nntool.getNN().getOutput(i));
				
				if(nntool.getNN().getOutput(i) > tendancy) {
					tendancy = nntool.getNN().getOutput(i);
					move = i;
				}
				//nn.setInput(i, 0.0); // re empty the value
			}
		}
		return move;
	}
	

}
