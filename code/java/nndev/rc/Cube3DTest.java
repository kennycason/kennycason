package rc;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author kenny cason
 * @date july 26
 */

public class Cube3DTest extends TestCase{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Cube3DTest test = new Cube3DTest();
		test.runTests();
		test.runVisuals();
	}
	
	public static Test suite(){
		TestSuite suite = new TestSuite();
		suite.addTest(new Cube3DTest("runTests"));
		suite.addTest(new Cube3DTest("runVisuals"));
		return suite;
	}
	
	/**
	 * Constructor for ArrayTest.
	 * @param name
	 */
	public Cube3DTest(String name) {
		super(name);
	}
	
	public Cube3DTest() {
		
	}
	
	public void runTests() {
		Cube3D cube = new Cube3D();
		// test isSolved
		assertTrue(cube.isSolved());
		cube.R();
		assertFalse(cube.isSolved());
		assertEquals(cube.getMoveCount(), 1);
		assertEquals(cube.getPrevMove(), 5); 
		
		cube.reset();
		assertTrue(cube.isSolved());
		cube.scramble(50);
		assertFalse(cube.isSolved());

		cube.reset();
		cube.U();
		assertEquals(cube.getMoveCount(), 1);
		cube.UPrime();
		assertTrue(cube.isSolved());
		assertEquals(cube.getMoveCount(), 0);
		assertEquals(cube.getPrevMoves().size(), 0);
		
		cube.reset();
		cube.U();
		cube.D();
		cube.R();
		cube.L();
		cube.FPrime();
		cube.B();
		cube.DPrime();
		cube.UPrime();
		cube.RPrime();
		cube.LPrime();
		cube.F();
		cube.BPrime();
		assertEquals(cube.getMoveCount(), 12);
		assertEquals(cube.getPrevMoves().size(), 12);
		for(int i = 0; i < 12; i++) {
			cube.undo();
		}
		assertTrue(cube.isSolved());
		assertEquals(cube.getMoveCount(), 0);
		assertEquals(cube.getPrevMoves().size(), 0);
		
	}
	
	public void runVisuals() {
		Cube3D cube = new Cube3D();
		cube.U();
		cube.U();
		cube.DPrime();
		cube.DPrime();
		cube.R();
		cube.R();
		cube.LPrime();
		cube.LPrime();
		cube.BPrime();
		cube.BPrime();
		cube.F();
		cube.F();
		System.out.println(cube.toString());
		for(int i = 0; i < 12; i++) {
			cube.undo();
		}	
		System.out.println(cube.toString());		
		cube.reset();
		cube.scramble(50);
		System.out.println(cube.toString());
	}

}
