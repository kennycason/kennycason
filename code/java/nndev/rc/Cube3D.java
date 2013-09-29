package rc;

import java.util.LinkedList;
import java.util.Random;

/**
 * Rubik's Cube - 3x3x3 rubik's cube (later version will extend to NxNxN cubes)
 * @author kenny cason
 * @date july 26
 */
public class Cube3D {
	
	/**
	 * cube orientation
	 * 
	 *   y(0,n,0) 
	 *   ↑　　 　      z(0,0,n)
	 *   |      　　／
	 *   |     　／
	 *   | 　／ 
	 *   |／
	 * (0,0,0)------→ x(n,0,0)
	 *    (0, 1, ... n)   
	 */
	
	private final int TOP = 1;
	private final int FRONT = 2;
	private final int RIGHT = 3;
	private final int BACK = 4;
	private final int LEFT = 5;
	private final int BOTTOM = 6;
	
 	private final int WHITE = 1;
 	private final int GREEN = 2;
 	private final int RED = 3;
 	private final int BLUE = 4;
 	private final int ORANGE = 5;
 	private final int YELLOW = 6;
 	
 	private final int U = 1;
	private final int UPRIME = 2;
	private final int F = 3;
	private final int FPRIME = 4;
	private final int R = 5;
	private final int RPRIME = 6;
	private final int B = 7;
	private final int BPRIME = 8;
	private final int L = 9;
	private final int LPRIME = 10;
	private final int D = 11;
	private final int DPRIME = 12;
	
	private int size = 3;
	
	private Cubit[][][] cube;
	
	private LinkedList<Integer> prevMoves;
	private int moveCount;
	
	/**
	 * Constructor for rubik's cube
	 */
	public Cube3D() {
		// create a new 3x3x3 cube
		cube = new Cubit[size][size][size]; 
		for(int z = 0; z < size; z++) {
			for(int y = 0; y < size; y++) {
				for(int x = 0; x < size; x++) {
					cube[x][y][z] = new Cubit();
				}
			}
		}
		prevMoves = new LinkedList<Integer>();
		moveCount = 0;
	}
	
	/**
	 * returns the dimensions of the rubik's cube (for now its always 3).
	 * @return int - size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * returns the number of moves
	 * @return int - move count
	 */
	public int getMoveCount() {
		return moveCount;
	}
	
	/**
	 * returns the previous move
	 * @return int - lastMove
	 */
	public int getPrevMove() {
		return prevMoves.getLast();
	}
	
	/**
	 * returns all previous moves
	 * @return LinkedList<Integer> - lastMoves
	 */
	public LinkedList<Integer> getPrevMoves() {
		return prevMoves;
	}
	
	/**
	 * reset
	 */
	public void reset() {
		cube = new Cubit[size][size][size]; 
		for(int z = 0; z < size; z++) {
			for(int y = 0; y < size; y++) {
				for(int x = 0; x < size; x++) {
					cube[x][y][z] = new Cubit();
				}
			}
		}
		prevMoves.clear();
		moveCount = 0;
	}
	
	/**
	 * isSolved - check to see if the cube is solved
	 * @Return boolean - true if in solved state, else false
	 */
	public boolean isSolved() {
        for(int z = 0; z < size; z++) {
        	for(int y = 0; y < size; y++) {
        		for(int x = 0; x < size; x++) {
        			if(cube[x][y][z].getTop() != WHITE ||
        			   cube[x][y][z].getFront() != GREEN ||
        			   cube[x][y][z].getRight() != RED ||
        			   cube[x][y][z].getBack() != BLUE ||
        			   cube[x][y][z].getLeft() != ORANGE ||
        			   cube[x][y][z].getBottom() != YELLOW) {
        				return false;
        			}
        		}
        	}
        }
		return true;
	}	
	
	/**
	 * percentSolved - check to see what percentage of the cube is solved
	 * @Return double - return the percentage solved (0.0 - 1.00)
	 */
	public double percentSolved() {
		double numPiecesSolved = 0;
		double totNumPieces = 26; // not including the very center.
		if (cube[0][0][0].getFront() == GREEN && cube[0][0][0].getLeft() == ORANGE && cube[0][0][0].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[1][0][0].getFront() == GREEN && cube[1][0][0].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[2][0][0].getFront() == GREEN && cube[2][0][0].getRight() == RED && cube[2][0][0].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[0][1][0].getFront() == GREEN && cube[0][1][0].getLeft() == ORANGE) 
			numPiecesSolved ++;
		if (cube[1][1][0].getFront() == GREEN) 
			numPiecesSolved ++;
		if (cube[2][1][0].getFront() == GREEN && cube[2][1][0].getRight() == RED) 
			numPiecesSolved ++;
		if (cube[0][2][0].getFront() == GREEN && cube[0][2][0].getLeft() == ORANGE && cube[0][2][0].getTop() == WHITE ) 
			numPiecesSolved ++;
		if (cube[1][2][0].getFront() == GREEN && cube[1][2][0].getTop() == WHITE) 
			numPiecesSolved ++;
		if (cube[2][2][0].getFront() == GREEN && cube[2][2][0].getTop() == WHITE && cube[2][2][0].getRight() == RED) 
			numPiecesSolved ++;
		if (cube[0][0][1].getLeft() == ORANGE && cube[0][0][1].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[1][0][1].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[2][0][1].getRight() == RED && cube[2][0][1].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[0][1][1].getLeft() == ORANGE) 
			numPiecesSolved ++;
		// skip the center most piece
		if (cube[2][1][1].getRight() == RED) 
			numPiecesSolved ++;
		if (cube[0][2][1].getLeft() == ORANGE && cube[0][2][1].getTop() == WHITE) 
			numPiecesSolved ++;
		if (cube[1][2][1].getTop() == WHITE) 
			numPiecesSolved ++;
		if (cube[2][2][1].getTop() == WHITE && cube[2][2][1].getRight() == RED) 
			numPiecesSolved ++;
		if (cube[0][0][2].getBack() == BLUE && cube[0][0][2].getLeft() == ORANGE && cube[0][0][2].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[1][0][2].getBack() == BLUE && cube[1][0][2].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[2][0][2].getBack() == BLUE && cube[2][0][2].getRight() == RED && cube[2][0][2].getBottom() == YELLOW) 
			numPiecesSolved ++;
		if (cube[0][1][2].getBack() == BLUE && cube[0][1][2].getLeft() == ORANGE) 
			numPiecesSolved ++;
		if (cube[1][1][2].getBack() == BLUE) 
			numPiecesSolved ++;
		if (cube[2][1][2].getBack() == BLUE && cube[2][1][2].getRight() == RED) 
			numPiecesSolved ++;
		if (cube[0][2][2].getBack() == BLUE && cube[0][2][2].getLeft() == ORANGE && cube[0][2][2].getTop() == WHITE) 
			numPiecesSolved ++;
		if (cube[1][2][2].getBack() == BLUE && cube[1][2][2].getTop() == WHITE) 
			numPiecesSolved ++;
		if (cube[2][2][2].getBack() == BLUE && cube[2][2][2].getTop() == WHITE && cube[2][2][2].getRight() == RED) 
			numPiecesSolved ++;
	//	System.out.println("Number Pieces Solved "+numPiecesSolved);
		return numPiecesSolved / totNumPieces;
	}

	/**
	 * scramble - scramble the cube
	 * @Param int - number of times to rotate the cube
	 */
	public void scramble(int n) {
		Random r = new Random();
		for(int i = 0; i < n; i++) {
			int rot = Math.abs(r.nextInt() % 12 + 1);
			rotateFace(rot);
		}
		moveCount = 0;
		prevMoves.clear();
	}
	
	
	/**
	 * getPiece - returns one of the pieces
	 * designed to support any size cube
	 */
	public Cubit getPiece(int x, int y, int z) {
		// ensure the values are in the range of the cube
		if(x >= 0 && x < size && y >= 0 && y < size && z >= 0 && z < size) {
			// check to see if cube has a center
			if(size > 2) {
				// see if desired piece is in the center
				if(x > 0 && x < size - 1 && y > 0 && y < size - 1 && z > 0 && z < size - 1) {
					// don't return it if it is
					return null;
				}
			}
			return cube[x][y][z];
		}
		return null;
	}
	
	
	
	/**
	 * undo
	 */
	public void undo() {
		if(prevMoves.size() > 0) {
			switch (prevMoves.getLast()) {
				case U:
					UPrime();
					break;
				case UPRIME:
					U();
					break;
				case F:
					FPrime();
					break;
				case FPRIME:
					F();
					break;
				case R:
					RPrime();
					break;
				case RPRIME:
					R();
					break;
				case B:
					BPrime();
					break;
				case BPRIME:
					B();
					break;
				case L:
					LPrime();
					break;
				case LPRIME:
					L();
					break;
				case D:
					DPrime();
					break;
				case DPRIME:
					D();
					break;
				default:
					break;
			}	
		}
	}
	
	/**
	 * rotateFace - rotate a face supplying an integer from 1 - 12.
	 * @param int
	 */
	public void rotateFace(int rot) {
		switch (rot) {
			case U:
				U();
				break;
			case UPRIME:
				UPrime();
				break;
			case F:
				F();
				break;
			case FPRIME:
				FPrime();
				break;
			case R:
				R();
				break;
			case RPRIME:
				RPrime();
				break;
			case B:
				B();
				break;
			case BPRIME:
				BPrime();
				break;
			case L:
				L();
				break;
			case LPRIME:
				LPrime();
				break;
			case D:
				D();
				break;
			case DPRIME:
				DPrime();
				break;
			default:
				break;
		}		
	}
	
	// top face
	public void U() {
		rotateY(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != UPRIME) {
			moveCount++;
			prevMoves.add(U);
		} else {
			prevMoves.removeLast();
			moveCount--;
		
		} 
	}
	
	public void UPrime() {
		rotateYPrime(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != U) {
			moveCount++;
			prevMoves.add(UPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	// bottom face
	public void D() {
		rotateYPrime(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != DPRIME) {
			moveCount++;
			prevMoves.add(D);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}	
	
	public void DPrime() {
		rotateY(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != D) {
			moveCount++;
			prevMoves.add(DPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	// left face
	public void L() {
		rotateXPrime(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != LPRIME) {
			moveCount++;
			prevMoves.add(L);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	public void LPrime() {
		rotateX(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != L) {
			moveCount++;
			prevMoves.add(LPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	// right face
	public void R() {
		rotateX(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != RPRIME) {
			moveCount++;
			prevMoves.add(R);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	public void RPrime() {
		rotateXPrime(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != R) {
			moveCount++;
			prevMoves.add(RPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	// back face
	public void B() {
		rotateZPrime(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != BPRIME) {
			moveCount++;
			prevMoves.add(B);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	public void BPrime() {
		rotateZ(2);
		if(prevMoves.size() == 0 || prevMoves.getLast() != B) {
			moveCount++;
			prevMoves.add(BPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	// front face
	public void F() {
		rotateZ(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != FPRIME) {
			moveCount++;
			prevMoves.add(F);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}
	
	public void FPrime() {
		rotateZPrime(0);
		if(prevMoves.size() == 0 || prevMoves.getLast() != F) {
			moveCount++;
			prevMoves.add(FPRIME);
		} else {
			prevMoves.removeLast();
			moveCount--;
		} 
	}

	// rotations
	// private methods are helper methods to eliminate code redundancy.
 
	/* 
	 * rotations over layer n around the Y axis  
	 * +---+---+---+
	 * |0n2|1n2|2n2|
	 * +---+---+---+
	 * |0n1|1n1|2n1|
	 * +---+---+---+
	 * |0n0|1n0|2n0|
	 * +---+---+---+
	 * the 3 numbers represent x,y,z coords
	 */
	private void rotateY(int n) {
		// rotate corner pieces
	    Cubit tempPiece =  cube[0][n][0];
	    cube[0][n][0] = cube[2][n][0]; 
	    cube[2][n][0] = cube[2][n][2]; 
	    cube[2][n][2] = cube[0][n][2]; 
	    cube[0][n][2] = tempPiece;
	    cube[0][n][0].rotateY(1);
	    cube[0][n][2].rotateY(1);
	    cube[2][n][2].rotateY(1);
	    cube[2][n][0].rotateY(1);
	    // rotate edges
	    tempPiece =  cube[1][n][0];
	    cube[1][n][0] = cube[2][n][1]; 
	    cube[2][n][1] = cube[1][n][2]; 
	    cube[1][n][2] = cube[0][n][1]; 
	    cube[0][n][1] = tempPiece;
	    cube[1][n][0].rotateY(1);
	    cube[0][n][1].rotateY(1);
	    cube[1][n][2].rotateY(1);
	    cube[2][n][1].rotateY(1); 
	    // now rotate center pieces
	    cube[1][n][1].rotateY(1);
	}
	
	private void rotateYPrime(int n) {
		// rotate corner pieces
	    Cubit tempPiece =  cube[0][n][0];
	    cube[0][n][0] = cube[0][n][2]; 
	    cube[0][n][2] = cube[2][n][2]; 
	    cube[2][n][2] = cube[2][n][0]; 
	    cube[2][n][0] = tempPiece;
	    cube[0][n][0].rotateY(-1);
	    cube[0][n][2].rotateY(-1);
	    cube[2][n][2].rotateY(-1);
	    cube[2][n][0].rotateY(-1);
	    // rotate edges
	    tempPiece =  cube[1][n][0];
	    cube[1][n][0] = cube[0][n][1]; 
	    cube[0][n][1] = cube[1][n][2]; 
	    cube[1][n][2] = cube[2][n][1]; 
	    cube[2][n][1] = tempPiece;
	    cube[1][n][0].rotateY(-1);
	    cube[0][n][1].rotateY(-1);
	    cube[1][n][2].rotateY(-1);
	    cube[2][n][1].rotateY(-1); 
	    // now rotate center pieces
	    cube[1][n][1].rotateY(-1);	
	}
	
	/* 
	 * rotations over layer n around X axis 
	 * +---+---+---+
	 * |n20|n21|n22| 
	 * +---+---+---+
	 * |n10|n11|n12| 
	 * +---+---+---+
	 * |n00|n01|n02|
	 * +---+---+---+  
	 */
	private void rotateX(int n) {
		// rotate corner pieces
	    Cubit tempPiece =  cube[n][0][0];
	    cube[n][0][0] = cube[n][0][2]; 
	    cube[n][0][2] = cube[n][2][2]; 
	    cube[n][2][2] = cube[n][2][0]; 
	    cube[n][2][0] = tempPiece;
	    cube[n][0][0].rotateX(1);
	    cube[n][2][0].rotateX(1);
	    cube[n][2][2].rotateX(1);
	    cube[n][0][2].rotateX(1);
	    // rotate edges
	    tempPiece =  cube[n][0][1];
	    cube[n][0][1] = cube[n][1][2]; 
	    cube[n][1][2] = cube[n][2][1]; 
	    cube[n][2][1] = cube[n][1][0]; 
	    cube[n][1][0] = tempPiece;
	    cube[n][0][1].rotateX(1);
	    cube[n][1][0].rotateX(1);
	    cube[n][2][1].rotateX(1);
	    cube[n][1][2].rotateX(1); 
	    // now rotate center pieces
	    cube[n][1][1].rotateX(1);
	}

	private void rotateXPrime(int n) {
		// rotate corner pieces
	    Cubit tempPiece =  cube[n][0][0];
	    cube[n][0][0] = cube[n][2][0]; 
	    cube[n][2][0] = cube[n][2][2]; 
	    cube[n][2][2] = cube[n][0][2]; 
	    cube[n][0][2] = tempPiece;
	    cube[n][0][0].rotateX(-1);
	    cube[n][2][0].rotateX(-1);
	    cube[n][2][2].rotateX(-1);
	    cube[n][0][2].rotateX(-1);
	    // rotate edges
	    tempPiece =  cube[n][0][1];
	    cube[n][0][1] = cube[n][1][0]; 
	    cube[n][1][0] = cube[n][2][1]; 
	    cube[n][2][1] = cube[n][1][2]; 
	    cube[n][1][2] = tempPiece;
	    cube[n][0][1].rotateX(-1);
	    cube[n][1][0].rotateX(-1);
	    cube[n][2][1].rotateX(-1);
	    cube[n][1][2].rotateX(-1); 
	    // now rotate center pieces
	    cube[n][1][1].rotateX(-1);
	}
	
	/* 
	 * rotations over layer n around Z axis
	 * +---+---+---+
	 * |02n|12n|22n| 
	 * +---+---+---+
	 * |01n|11n|21n| 
	 * +---+---+---+
	 * |00n|10n|20n|
	 * +---+---+---+  
	 */
	private void rotateZ(int n) {
 		// rotate corner pieces
	    Cubit tempPiece =  cube[0][0][n];
	    cube[0][0][n] = cube[2][0][n]; 
	    cube[2][0][n] = cube[2][2][n]; 
	    cube[2][2][n] = cube[0][2][n]; 
	    cube[0][2][n] = tempPiece;
	    cube[0][0][n].rotateZ(1);
	    cube[0][2][n].rotateZ(1);
	    cube[2][2][n].rotateZ(1);
	    cube[2][0][n].rotateZ(1);
	    // rotate edges
	    tempPiece =  cube[1][0][n];
	    cube[1][0][n] = cube[2][1][n]; 
	    cube[2][1][n] = cube[1][2][n]; 
	    cube[1][2][n] = cube[0][1][n]; 
	    cube[0][1][n] = tempPiece;
	    cube[1][0][n].rotateZ(1);
	    cube[0][1][n].rotateZ(1);
	    cube[1][2][n].rotateZ(1);
	    cube[2][1][n].rotateZ(1); 
	    // now rotate center pieces
	    cube[1][1][n].rotateZ(1);
	}

	private void rotateZPrime(int n) {
 		// rotate corner pieces
	    Cubit tempPiece =  cube[0][0][n];
	    cube[0][0][n] = cube[0][2][n]; 
	    cube[0][2][n] = cube[2][2][n]; 
	    cube[2][2][n] = cube[2][0][n]; 
	    cube[2][0][n] = tempPiece;
	    cube[0][0][n].rotateZ(-1);
	    cube[0][2][n].rotateZ(-1);
	    cube[2][2][n].rotateZ(-1);
	    cube[2][0][n].rotateZ(-1);
	    // rotate edges
	    tempPiece =  cube[1][0][n];
	    cube[1][0][n] = cube[0][1][n]; 
	    cube[0][1][n] = cube[1][2][n]; 
	    cube[1][2][n] = cube[2][1][n]; 
	    cube[2][1][n] = tempPiece;
	    cube[1][0][n].rotateZ(-1);
	    cube[0][1][n].rotateZ(-1);
	    cube[1][2][n].rotateZ(-1);
	    cube[2][1][n].rotateZ(-1); 
	    // now rotate center pieces
	    cube[1][1][n].rotateZ(-1);
	}
	
	/**
	 * toString - draw a face
	 */
	public String toString(int face) {
		String s = "";
		if(face == TOP) {
			s += "TOP FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][2].getTop()+" | "+cube[1][2][2].getTop()+" | "+cube[2][2][2].getTop()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][1].getTop()+" | "+cube[1][2][1].getTop()+" | "+cube[2][2][1].getTop()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][0].getTop()+" | "+cube[1][2][0].getTop()+" | "+cube[2][2][0].getTop()+" |\n";
			s += "+---+---+---+\n";			
		} else if(face == FRONT) {
			s += "FRONT FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][0].getFront()+" | "+cube[1][0][0].getFront()+" | "+cube[2][0][0].getFront()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][1][0].getFront()+" | "+cube[1][1][0].getFront()+" | "+cube[2][1][0].getFront()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][0].getFront()+" | "+cube[1][2][0].getFront()+" | "+cube[2][2][0].getFront()+" |\n";
			s += "+---+---+---+\n";
		} else if(face == RIGHT) {
			s += "RIGHT FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[2][2][0].getRight()+" | "+cube[2][2][1].getRight()+" | "+cube[2][2][2].getRight()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[2][1][0].getRight()+" | "+cube[2][1][1].getRight()+" | "+cube[2][1][2].getRight()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[2][0][0].getRight()+" | "+cube[2][0][1].getRight()+" | "+cube[2][0][2].getRight()+" |\n";
			s += "+---+---+---+\n";		
		} else if(face == BACK) {
			s += "BACK FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][2].getBack()+" | "+cube[1][0][2].getBack()+" | "+cube[2][0][2].getBack()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][1][2].getBack()+" | "+cube[1][1][2].getBack()+" | "+cube[2][1][2].getBack()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][2].getBack()+" | "+cube[1][2][2].getBack()+" | "+cube[2][2][2].getBack()+" |\n";
			s += "+---+---+---+\n";
		} else if(face == LEFT) {
			s += "LEFT FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][2][0].getLeft()+" | "+cube[0][2][1].getLeft()+" | "+cube[0][2][2].getLeft()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][1][0].getLeft()+" | "+cube[0][1][1].getLeft()+" | "+cube[0][1][2].getLeft()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][0].getLeft()+" | "+cube[0][0][1].getLeft()+" | "+cube[0][0][2].getLeft()+" |\n";
			s += "+---+---+---+\n";		
		} else if(face == BOTTOM) {
			s += "BOTTOM FACE\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][2].getBottom()+" | "+cube[1][0][2].getBottom()+" | "+cube[2][0][2].getBottom()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][1].getBottom()+" | "+cube[1][0][1].getBottom()+" | "+cube[2][0][1].getBottom()+" |\n";
			s += "+---+---+---+\n";
			s += "| "+cube[0][0][0].getBottom()+" | "+cube[1][0][0].getBottom()+" | "+cube[2][0][0].getBottom()+" |\n";
			s += "+---+---+---+\n";			
		}
		return s;
	}
	
	public String toString() {
		String s = "";
		s += "            +---+---+---+\n";
		s += "            | "+cube[0][0][2].getBack()+" | "+cube[1][0][2].getBack()+" | "+cube[2][0][2].getBack()+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+cube[0][1][2].getBack()+" | "+cube[1][1][2].getBack()+" | "+cube[2][1][2].getBack()+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+cube[0][2][2].getBack()+" | "+cube[1][2][2].getBack()+" | "+cube[2][2][2].getBack()+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+cube[0][0][2].getLeft()+" | "+cube[0][1][2].getLeft()+" | "+cube[0][2][2].getLeft()+" | "+cube[0][2][2].getTop()+" | "+cube[1][2][2].getTop()+" | "+cube[2][2][2].getTop()+" | "+cube[2][2][2].getRight()+" | "+cube[2][1][2].getRight()+" | "+cube[2][0][2].getRight()+" | "+cube[2][0][2].getBottom()+" | "+cube[1][0][2].getBottom()+" | "+cube[0][0][2].getBottom()+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+cube[0][0][1].getLeft()+" | "+cube[0][1][1].getLeft()+" | "+cube[0][2][1].getLeft()+" | "+cube[0][2][1].getTop()+" | "+cube[1][2][1].getTop()+" | "+cube[2][2][1].getTop()+" | "+cube[2][2][1].getRight()+" | "+cube[2][1][1].getRight()+" | "+cube[2][0][1].getRight()+" | "+cube[2][0][1].getBottom()+" | "+cube[1][0][1].getBottom()+" | "+cube[0][0][1].getBottom()+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+cube[0][0][0].getLeft()+" | "+cube[0][1][0].getLeft()+" | "+cube[0][2][0].getLeft()+" | "+cube[0][2][0].getTop()+" | "+cube[1][2][0].getTop()+" | "+cube[2][2][0].getTop()+" | "+cube[2][2][0].getRight()+" | "+cube[2][1][0].getRight()+" | "+cube[2][0][0].getRight()+" | "+cube[2][0][0].getBottom()+" | "+cube[1][0][0].getBottom()+" | "+cube[0][0][0].getBottom()+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "            | "+cube[0][2][0].getFront()+" | "+cube[1][2][0].getFront()+" | "+cube[2][2][0].getFront()+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+cube[0][1][0].getFront()+" | "+cube[1][1][0].getFront()+" | "+cube[2][1][0].getFront()+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+cube[0][0][0].getFront()+" | "+cube[1][0][0].getFront()+" | "+cube[2][0][0].getFront()+" |\n";
		s += "            +---+---+---+\n";
		return s;
	}
	
	
	/**
	 * prints colored squares in linux, DOES NOT work in Windows
	 * @return
	 */
	public String toStringColor() {
		String s = "";
		s += "            +---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][0][2].getBack())+" | "+printColorSqr(cube[1][0][2].getBack())+" | "+printColorSqr(cube[2][0][2].getBack())+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][1][2].getBack())+" | "+printColorSqr(cube[1][1][2].getBack())+" | "+printColorSqr(cube[2][1][2].getBack())+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][2][2].getBack())+" | "+printColorSqr(cube[1][2][2].getBack())+" | "+printColorSqr(cube[2][2][2].getBack())+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+printColorSqr(cube[0][0][2].getLeft())+" | "+printColorSqr(cube[0][1][2].getLeft())+" | "+printColorSqr(cube[0][2][2].getLeft())+" | "+printColorSqr(cube[0][2][2].getTop())+" | "+printColorSqr(cube[1][2][2].getTop())+" | "+printColorSqr(cube[2][2][2].getTop())+" | "+printColorSqr(cube[2][2][2].getRight())+" | "+printColorSqr(cube[2][1][2].getRight())+" | "+printColorSqr(cube[2][0][2].getRight())+" | "+printColorSqr(cube[2][0][2].getBottom())+" | "+printColorSqr(cube[1][0][2].getBottom())+" | "+printColorSqr(cube[0][0][2].getBottom())+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+printColorSqr(cube[0][0][1].getLeft())+" | "+printColorSqr(cube[0][1][1].getLeft())+" | "+printColorSqr(cube[0][2][1].getLeft())+" | "+printColorSqr(cube[0][2][1].getTop())+" | "+printColorSqr(cube[1][2][1].getTop())+" | "+printColorSqr(cube[2][2][1].getTop())+" | "+printColorSqr(cube[2][2][1].getRight())+" | "+printColorSqr(cube[2][1][1].getRight())+" | "+printColorSqr(cube[2][0][1].getRight())+" | "+printColorSqr(cube[2][0][1].getBottom())+" | "+printColorSqr(cube[1][0][1].getBottom())+" | "+printColorSqr(cube[0][0][1].getBottom())+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "| "+printColorSqr(cube[0][0][0].getLeft())+" | "+printColorSqr(cube[0][1][0].getLeft())+" | "+printColorSqr(cube[0][2][0].getLeft())+" | "+printColorSqr(cube[0][2][0].getTop())+" | "+printColorSqr(cube[1][2][0].getTop())+" | "+printColorSqr(cube[2][2][0].getTop())+" | "+printColorSqr(cube[2][2][0].getRight())+" | "+printColorSqr(cube[2][1][0].getRight())+" | "+printColorSqr(cube[2][0][0].getRight())+" | "+printColorSqr(cube[2][0][0].getBottom())+" | "+printColorSqr(cube[1][0][0].getBottom())+" | "+printColorSqr(cube[0][0][0].getBottom())+" |\n";
		s += "+---+---+---+---+---+---+---+---+---+---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][2][0].getFront())+" | "+printColorSqr(cube[1][2][0].getFront())+" | "+printColorSqr(cube[2][2][0].getFront())+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][1][0].getFront())+" | "+printColorSqr(cube[1][1][0].getFront())+" | "+printColorSqr(cube[2][1][0].getFront())+" |\n";
		s += "            +---+---+---+\n";
		s += "            | "+printColorSqr(cube[0][0][0].getFront())+" | "+printColorSqr(cube[1][0][0].getFront())+" | "+printColorSqr(cube[2][0][0].getFront())+" |\n";
		s += "            +---+---+---+\n";
		return s;
	}
	
	private String printColorSqr(int i) {
		String s = "";
		if(i == 1) {
			s = "\033[37m■\033[0m";  // WHITE
		} else if(i == 2) {
			s = "\033[34m■\033[0m"; // BLUE
		} else if(i == 3) {
			s = "\033[31m■\033[0m"; // RED
		} else if(i == 4) {
			s = "\033[32m■\033[0m"; // GREEN
		} else if(i == 5) {
			s = "\033[35m■\033[0m"; // PINKISH
		} else if(i == 6) {
			s = "\033[33m■\033[0m"; // YELLOW
		}
		return s;
	}
}
