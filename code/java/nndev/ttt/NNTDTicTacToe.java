package ttt;


import nn.NeuralNetwork;

public class NNTDTicTacToe {

	
	private TicTacToe ttt;
	private NeuralNetwork nn;
	
	private double[] P1LastPrediction;
	private double[] P2LastMove;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NNTDTicTacToe();

	}
	
	
	public NNTDTicTacToe() {
		
		nn = new NeuralNetwork();
		nn.init(9,1,100,2,false);
		nn.setLearningRate(0.01); 
		nn.setDiscountFactor(0.5);
		
	//	P1LastMove = new double[9];
	//	P2LastMove = new double[9];
	    
		P1LastPrediction = new double[2];
		P2LastMove = new double[2];
		
		nn.setRewards(new double[2]);
		
	//	int numP1Wins;
	//	int numP2Wins;
		
		 
		ttt = new TicTacToe();

		boolean computerPlaying = true;
	    int count = 0;
		int maxCycles = 25000;
	    boolean playing = true;
	    
		while(playing) {
			if(computerPlaying) {
				System.out.println("CP vs CP: "+count);
			}
			if(count == maxCycles) {
				computerPlaying = false;
			}
			ttt.newGame();
			System.out.println("New Game");
			while(ttt.checkWin() == -2) {
			
				if(ttt.getTurn() == 1) {
					//System.out.println("Neural Networks TURN");
					for(int i = 0; i < nn.getInputLayer().getNumNeurons(); i++) {
					//	System.out.println("ttt.getBoard()[i] ="+ttt.getBoard()[i]);
						nn.setInput(i, ttt.getBoard()[i]);
					}
					performHighestValuedMove(1);
					
					if(ttt.checkWin() == ttt.P1WIN) {
						System.out.println("P1 (X) Wins!");
						nn.setReward(0,1.0);  // this is a good state for P1
						nn.setReward(1,-1.0); // this is a bad state for P2
						
						nn.TDUpdate(P1LastPrediction);
						nn.setReward(0,0);
						nn.setReward(1,0);
					} else if(ttt.checkWin() == ttt.P2WIN) {
						System.out.println("P2 (O) Wins!");
						nn.setReward(0,-1.0);  // this is a bad state for P1
						nn.setReward(1,1.0); // this is a good state for P2
						
						nn.TDUpdate(P1LastPrediction);

						nn.setReward(0,0);
						nn.setReward(1,0);
					} else {
					//	System.out.println("DRAW!");
						nn.setReward(0,0);
						nn.setReward(1,0);
						nn.TDUpdate(P1LastPrediction);
					}
					ttt.setTurn(2);
					ttt.repaint();
				} else if(computerPlaying && ttt.getTurn() == 2) {
					
						//System.out.println("Neural Networks TURN");
						for(int i = 0; i < nn.getInputLayer().getNumNeurons(); i++) {
						//	System.out.println("ttt.getBoard()[i] ="+ttt.getBoard()[i]);
							nn.setInput(i, ttt.getBoard()[i]);
						}
						performHighestValuedMove(2);
						if(ttt.checkWin() == ttt.P1WIN) {
							System.out.println("P1 (X) Wins!");
							nn.setReward(0,1.0);  // this is a good state for P1
							nn.setReward(1,-1.0); // this is a bad state for P2
							
							nn.TDUpdate(P2LastMove);

							nn.setReward(0,0);
							nn.setReward(1,0);
						} else if(ttt.checkWin() == ttt.P2WIN) {
							System.out.println("P2 (O) Wins!");
							nn.setReward(0,-1.0);  // this is a bad state for P1
							nn.setReward(1,1.0); // this is a good state for P2
							
							nn.setReward(0,0);
							nn.setReward(1,0);
						} else {
						//	System.out.println("DRAW!");
							nn.setReward(0,0);
							nn.setReward(1,0);
							nn.TDUpdate(P2LastMove);
						}

						ttt.setTurn(1);
						ttt.repaint();	
				}
			}

			if(!computerPlaying) {
				if(ttt.checkWin() == ttt.P1WIN) {
					System.out.println("P1 (X) Wins!");
					nn.setReward(0,1.0);  // this is a good state for P1
					nn.setReward(1,-1.0); // this is a bad state for P2
					
					nn.TDUpdate(P2LastMove);

					nn.setReward(0,0);
					nn.setReward(1,0);
				} else if(ttt.checkWin() == ttt.P2WIN) {
					System.out.println("YOU (O) Win!");
					nn.setReward(0,-1.0);  // this is a good state for P1
					nn.setReward(1,1.0); // this is a bad state for P2
					
					nn.TDUpdate(P1LastPrediction);

					nn.setReward(0,0);
					nn.setReward(1,0);
				} if(ttt.checkWin() == 0) { // TIE
					System.out.println("DRAW!");
					nn.setReward(0,0);
					nn.setReward(1,0);
					if(ttt.getTurn() == 2) {
						nn.TDUpdate(P1LastPrediction);
					}
				}
			}
			
			count++;
			ttt.newGame();	

		}

	}
	
	
	
	/**
	 * find the move that the neural network estimates to have the hightest value
	 * @return
	 */
	public int performHighestValuedMove(int turn) {
		int move = 0;
		double highestValue = -1000;
		// set inputs to current state of the board
		for(int i = 0; i < nn.getInputLayer().getNumNeurons(); i++) {
			nn.setInput(i, ttt.getBoard()[i]);
		}
		
		for(int i = 0; i < nn.getInputLayer().getNumNeurons(); i++) {
			if(ttt.getBoard()[i] == 0) { // space is empty
			
				// set the input value
				if(turn == 1) {
					nn.setInput(i, ttt.P1VALUE);
				} else if(turn == 2) {
					nn.setInput(i, ttt.P2VALUE);
				}			
				nn.feedForward();
				// evalute the value of the move against the current best move.
				if(turn == 1) { 
					if(nn.getOutput(0) > highestValue) {
						highestValue = nn.getOutput(0);
						move = i;
					}		
				} else if(turn == 2) { 
					if(nn.getOutput(1) > highestValue) {
						highestValue = nn.getOutput(1);
						move = i;
					}			
				}	
				nn.setInput(i, 0.0); // re empty the value
			}		
		}

		if(turn == 1) {
			nn.setInput(move, ttt.P1VALUE);
			ttt.getBoard()[move] = ttt.P1VALUE;
			for(int j = 0; j < 2; j++) {
				P1LastPrediction[j] = nn.getOutput(j);
			}
		} else if(turn == 2) {
			nn.setInput(move, ttt.P2VALUE);
			ttt.getBoard()[move] = ttt.P2VALUE;
			for(int j = 0; j < 2; j++) {
				P2LastMove[j] = nn.getOutput(j);
			}
		}
		
		nn.feedForward();
		//System.out.println("|val - ouput| "+Math.abs(ttt.P1WIN - nn.getOutput(0)));
		return move;
	}

}