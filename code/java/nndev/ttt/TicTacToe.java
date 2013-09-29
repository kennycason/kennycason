package ttt;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TicTacToe extends JFrame implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3796771084591745249L;
	
	
	ImageIcon picIconX;
	ImageIcon picIconO;
	ImageIcon picIconBoard;
	Image x;
	Image o;
	Image board;
	
	double[] moveList;
    	

	//int playCount = 1;
	int turn;
	int p1LastMove;
	int p2LastMove;
	
	int xPos = 0;
	int yPos = 0;
	int winner = 0;
	
	int gameOver = 0;
	
	final int P1WIN = -1; //NN
	final int P2WIN = 1;  // CMP
	final double P1VALUE = 0.5;
	final double P2VALUE = 1.0;
	
	final int CATGAME = 0;

	JPanel gamePanel;

	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		
		game.game();
	}
	
	public TicTacToe() {
		java.net.URL imgURL1 = TicTacToe.class.getResource("X.png");
		java.net.URL imgURL2 = TicTacToe.class.getResource("O.png");
		java.net.URL imgURL3 = TicTacToe.class.getResource("board.png");
		try {
			picIconX = new ImageIcon(imgURL1);
			picIconO = new ImageIcon(imgURL2);
			picIconBoard = new ImageIcon(imgURL3);
		} catch(Exception e) {
			e.printStackTrace();
		}
		x = picIconX.getImage();
		o = picIconO.getImage();
		board = picIconBoard.getImage();
	
		this.setTitle("TicTacToe");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gamePanel = new JPanel();
		this.add(gamePanel);
		addMouseListener(this);
		this.setVisible(true);
		
		moveList = new double[9];
		newGame();
	}
	
	
	public void game() {
			while(gameOver == 0) {
				gameOver = checkWin();
				//System.out.println("checking win " + gameOver);
			}

			displayWinner();
			
			exit();			
	}
	
	public void newGame() {
		for(int i = 0; i < 9; i++) {
			moveList[i] = 0;
			//System.out.println("moveList" + i + moveList[i]);
		}	
		turn = 1;
		p1LastMove = -1;
		p2LastMove = -1;
		repaint();
	}
	
	public void paint(Graphics g) {
		g.drawImage(board,50,50, this);
		if(moveList[0] == P1VALUE) { //block 1
			g.drawImage(x, 65, 70, this);
		}
		if(moveList[1] == P1VALUE) { //block 
			g.drawImage(x, 131, 70, this);
		}
		if(moveList[2] == P1VALUE) { //block 3
			g.drawImage(x, 193, 70, this);
		}
		if(moveList[3] == P1VALUE) { //block 4
			g.drawImage(x, 65, 134, this);
		}
		if(moveList[4] == P1VALUE) { //block 5
			g.drawImage(x, 131, 134, this);
		}
		if(moveList[5] == P1VALUE) { //block 6
			g.drawImage(x, 193, 134, this);
		}
		if(moveList[6] == P1VALUE) { //block 7
			g.drawImage(x, 65, 193, this);
		}
		if(moveList[7] == P1VALUE) { //block 8
			g.drawImage(x, 131, 193, this);
		}
		if(moveList[8] == P1VALUE) { //block 9
			g.drawImage(x, 193, 193, this);
		}

		if(moveList[0] == P2VALUE) { //block 1
			g.drawImage(o, 65, 70, this);
		}
		if(moveList[1] == P2VALUE) { //block 2
			g.drawImage(o, 131, 70, this);
		}
		if(moveList[2] == P2VALUE) { //block 3
			g.drawImage(o, 193, 70, this);
		}
		if(moveList[3] == P2VALUE) { //block 4
			g.drawImage(o, 65, 134, this);
		}
		if(moveList[4] == P2VALUE) { //block 5
			g.drawImage(o, 131, 134, this);
		}
		if(moveList[5] == P2VALUE) { //block 6
			g.drawImage(o, 193, 134, this);
		}
		if(moveList[6] == P2VALUE) { //block 7
			g.drawImage(o, 65, 193, this);
		}
		if(moveList[7] == P2VALUE) { //block 8
			g.drawImage(o, 131, 193, this);
		}
		if(moveList[8] == P2VALUE) { //block 9
			g.drawImage(o, 193, 193, this);
		}
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void setTurn(int t) {
		turn = t;
	}
	
	public void move(int x, int y) {
		xPos = x;
		yPos = y;
		//System.out.println("x pos = " + xPos);
		//System.out.println("y pos = " + yPos);

		if(xPos >= 60 && xPos <=110 && yPos >= 60 && yPos<=110) { //block 1
		/*	if(playCount % 2 > 0 && moveList[0] == 0) {
				moveList[0] = 1;
				playCount++;
			} else*/ if(moveList[0]==0) {
				moveList[0] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 0;
			}
		} else if(xPos >= 125 && xPos <=175 && yPos >= 60 && yPos<=110) { //block 2
			/*if(playCount % 2 > 0 && moveList[1] == 0) {
				moveList[1] = 1;
				playCount++;
			} else */if(moveList[1]==0) {
				moveList[1] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 1;
			}		
		} else if(xPos >= 188 && xPos <=240 && yPos >= 60 && yPos<=110) { //block 3
			/*if(playCount % 2 > 0 && moveList[2]==0) {
				moveList[2] = 1;
				playCount++;
			} else*/ if(moveList[2]==0) {
				moveList[2] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 2;
			}
		} else if(xPos >= 60 && xPos <=110 && yPos >= 123 && yPos<=178) { //block 4
			/*if(playCount % 2 > 0 && moveList[3]==0) {
				moveList[3] = 1;
				playCount++;
			} else*/ if(moveList[3]==0) {
				moveList[3] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 3;
			}
		} else if(xPos >= 125 && xPos <=175 && yPos >= 123 && yPos<=178) { //block 5
			/*if(playCount % 2 > 0 && moveList[4] ==0) {
				moveList[4] = 1;
				playCount++;
			} else*/ if(moveList[4]==0) {
				moveList[4] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 4;
			}
		} else if(xPos >= 188 && xPos <=240 && yPos >= 123 && yPos<=178) { //block 6
			/*if(playCount % 2 > 0 && moveList[5] == 0) {
				moveList[5] = 1;
				playCount++;
			} elseI*/ if(moveList[5]==0) {
				moveList[5] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 5;
			}			
		} else if(xPos >= 60 && xPos <=110 && yPos >= 189 && yPos<=242) { //block 7
		/*	if(playCount % 2 > 0 && moveList[6] ==0) {
				moveList[6] = 1;
				playCount++;
			} else*/ if(moveList[6]==0) {
				moveList[6] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 6;
			}
		} else if(xPos >= 125 && xPos <=175 && yPos >= 189 && yPos<=242) { //block 8
		/*	if(playCount % 2 > 0 && moveList[7]==0) {
				moveList[7] = 1;
				playCount++;
			} else */if(moveList[7]==0) {
				moveList[7] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 7;
			}
		} else if(xPos >= 188 && xPos <=240 && yPos >= 189 && yPos<=242) { //block 9
		/*	if(playCount % 2 > 0 && moveList[8]==0) {
				moveList[8] = 1;
				playCount++;
			} else*/ if(moveList[8]==0) {
				moveList[8] = P2VALUE;
				if(checkWin() == -2) {
					turn = 1;
				}
				p2LastMove = 8;
			}
		}
		p2LastMove = -1;
	}
	
	// dont use these so leave them empty 
	public void mousePressed(MouseEvent e){} 
	public void mouseEntered(MouseEvent e){} 
	public void mouseExited(MouseEvent e){} 
	public void mouseReleased(MouseEvent e){}

	public void mouseClicked(MouseEvent e) { 
		xPos = e.getX(); 
    		yPos = e.getY(); 
      		move(xPos, yPos);
      		//System.out.println("Mouse Clicked Event!");
      		// repaint the applet 
      		repaint(); 
   	}

	public int checkWin() {
		if(moveList[0] == P1VALUE && moveList[1] == P1VALUE && moveList[2] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[3] == P1VALUE && moveList[4] == P1VALUE && moveList[5] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[6] == P1VALUE && moveList[7] == P1VALUE && moveList[8] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[0] == P1VALUE && moveList[3] == P1VALUE && moveList[6] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[1] == P1VALUE && moveList[4] == P1VALUE && moveList[7] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[2] == P1VALUE && moveList[5] == P1VALUE && moveList[8] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[0] == P1VALUE && moveList[4] == P1VALUE && moveList[8] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[2] == P1VALUE && moveList[4] == P1VALUE && moveList[6] == P1VALUE)  {
			winner = P1WIN;
			return winner;
		} else if(moveList[0] == P2VALUE && moveList[1] == P2VALUE && moveList[2] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[3] == P2VALUE && moveList[4] == P2VALUE && moveList[5] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[6] == P2VALUE && moveList[7] == P2VALUE && moveList[8] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[0] == P2VALUE && moveList[3] == P2VALUE && moveList[6] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[1] == P2VALUE && moveList[4] == P2VALUE && moveList[7] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[2] == P2VALUE && moveList[5] == P2VALUE && moveList[8] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[0] == P2VALUE && moveList[4] == P2VALUE && moveList[8] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else if(moveList[2] == P2VALUE && moveList[4] == P2VALUE && moveList[6] == P2VALUE) {
			winner = P2WIN;
			return winner;
		} else {
			if(checkCatWin()) {
				return CATGAME;
			}
		}
		return -2;
	}

	public void displayWinner() {
		System.out.println("winner is player" + winner);
		
	}

	public boolean checkCatWin() {
		boolean cat = false;

		if(moveList[0] == 0 || moveList[1] == 0 || moveList[2] == 0 || moveList[3] == 0 || moveList[4] == 0 || moveList[5] == 0 || moveList[6] == 0 || moveList[7] == 0 || moveList[8] == 0) {
			cat = false;
		}
		else {
			cat = true;
		}
		return cat;
	}
	
	public double[] getBoard() {
		return moveList; 
	}

	
	public int getP1LastMove() {
		return p1LastMove;
	}
	
	public int getP2LastMove() {
		return p2LastMove;
	}
	

	public void setP1LastMove(int m) {
		p1LastMove = m;
	}
	
	public void setP2LastMove(int m) {
		p2LastMove = m;
	}
	
	public int getWinner() {
		return winner;
	}

	public void exit() {
		double[] temp = new double[9];
		temp = getBoard();
		for(int i = 0; i < 9; i++) {
			System.out.println(i + " is " + temp[i]);
		}
		System.exit(0);
	}
	
}
