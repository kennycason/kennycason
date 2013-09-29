
package rc;
/**
 * @author kenny cason
 * @date july 26
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RubiksCube {

	public static void main(String[] args) throws IOException {
		Cube3D cube = new Cube3D();

	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    String s;
	    boolean printColor = false;
    	System.out.println("\n\n\n\n");
    	System.out.println("If Color is not available type \"C\" to toggle Color on/off");
    	System.out.println("COMMANDS:, UNDO, RESET, S - Scramble, C - Color on/off, X - Exit");
    	System.out.println("ROTATIONS: U, U', F, F', R, R', B, B', L, L', D, D',");
    	System.out.println("MOVES: "+cube.getMoveCount()+" | SOLVED: "+cube.isSolved());
    	System.out.println(cube.toString());
	    boolean exit = false;
	    while(!exit) {
	    	s = in.readLine();
	    	if(s.equalsIgnoreCase("X") || s.equalsIgnoreCase("EXIT")) {
	    		exit = true;
	    	} else if(s.equalsIgnoreCase("U")) {
	    		cube.U();
	    	} else if(s.equalsIgnoreCase("U'")) {
	    		cube.UPrime();
	    	} else if(s.equalsIgnoreCase("F")) {
	    		cube.F();
	    	} else if(s.equalsIgnoreCase("F'")) {
	    		cube.FPrime();
	    	} else if(s.equalsIgnoreCase("R")) {
	    		cube.R();
	    	} else if(s.equalsIgnoreCase("R'")) {
	    		cube.RPrime();
	    	} else if(s.equalsIgnoreCase("B")) {
	    		cube.B();
	    	} else if(s.equalsIgnoreCase("B'")) {
	    		cube.BPrime();
	    	} else if(s.equalsIgnoreCase("L")) {
	    		cube.L();
	    	} else if(s.equalsIgnoreCase("L'")) {
	    		cube.LPrime();
	    	} else if(s.equalsIgnoreCase("D")) {
	    		cube.D();
	    	} else if(s.equalsIgnoreCase("D'")) {
	    		cube.DPrime();
	    	} else if(s.equalsIgnoreCase("C")) {
	    		if(printColor) {
	    			printColor = false;
	    		} else {
	    			printColor = true;
	    		}
	    	} else if(s.equalsIgnoreCase("UNDO")) {
		    		cube.undo();
	    	} else if(s.equalsIgnoreCase("RESET")) {
	    		cube.reset();
	    	} else if(s.equalsIgnoreCase("S") || s.equalsIgnoreCase("SCRAMBLE")) {
	    		cube.scramble(100);
	    	}
	    	for(int i = 0; i < 50; i++) {
	    		System.out.println("");
	    	}
    		System.out.println("C - COMMANDS, UNDO, RESET, S - Scramble, X - Exit");
    		System.out.println("COMMANDS:, UNDO, RESET, S - Scramble, C - Color on/off, X - Exit");
	    	System.out.println("MOVES: "+cube.getMoveCount()+", SOLVED: "+cube.isSolved()+", % SOLVED: "+(int)(cube.percentSolved()*100));
	    	if(printColor) {
	    		System.out.println(cube.toStringColor());
	    	} else {
	    		System.out.println(cube.toString());    		
	    	}
		}
	}
}
