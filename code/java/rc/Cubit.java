package rc;
/**
 * Cubit - a 3x3x3 sub-unit of the rubik's cube.
 * @author kenny cason
 * @date july 26
 */
public class Cubit {
	
	/*
	 *   y
	 *   ↑　   　 　       z
	 *   |     　　／
	 *   |    　／
	 *   | 　／ 
	 *   |／
	 *    -----−−−−−−→ x
	 */
	
 	private final int WHITE = 1;
 	private final int GREEN = 2;
 	private final int RED = 3;
 	private final int BLUE = 4;
 	private final int ORANGE = 5;
 	private final int YELLOW = 6;
	
	private int[] faces;
	
	public Cubit() {
		faces = new int[6];
		setTop(WHITE);	
		setFront(GREEN);
		setRight(RED);
		setBack(BLUE);
		setLeft(ORANGE);
		setBottom(YELLOW);
	}
	
	public int getFace(int i) {
		return faces[i];
	}
	
	public void setFace(int i, int color) {
		faces[i] = color;
	}
	
	public void setTop(int color) {
		faces[0] = color;
	}

	public void setFront(int color) {
		faces[1] = color;
	}
	
	public void setRight(int color) {
		faces[2] = color;
	}
	
	public void setBack(int color) {
		faces[3] = color;
	}
	
	public void setLeft(int color) {
		faces[4] = color;
	}
	
	public void setBottom(int color) {
		faces[5] = color;
	}
	
	public int getTop() {
		return faces[0];
	}
	
	public int getFront() {
		return faces[1];
	}
	
	public int getRight() {
		return faces[2];
	}
	
	public int getBack() {
		return faces[3];
	}	
	
	public int getLeft() {
		return faces[4];
	}
	
	public int getBottom() {
		return faces[5];
	}

	/**
	 * Rotate around the Right and Left Faces
	 * counterClockwise = -1
	 * clockwise = 1
	 * @param dir
	 */
	public void rotateX(int dir) {
		if(dir == 1) {
			int tempFace = getFront();
			setFront(getBottom());
			setBottom(getBack());
			setBack(getTop());
			setTop(tempFace);
		} else if(dir == -1)  {
			int tempFace = getFront();
			setFront(getTop());
			setTop(getBack());
			setBack(getBottom());
			setBottom(tempFace);
		}
	}

	/**
	 * Rotate around the Front and Back Faces
	 * counterClockwise = -1
	 * clockwise = 1
	 * @param dir
	 */
	public void rotateZ(int dir) {
		if(dir == 1) {
			int tempFace = getTop();
			setTop(getLeft());
			setLeft(getBottom());
			setBottom(getRight());
			setRight(tempFace);
		} else if(dir == -1)  {
			int tempFace = getTop();
		    setTop(getRight());
		    setRight(getBottom());
		    setBottom(getLeft());
		    setLeft(tempFace);
		}
	}
	
	/**
	 * Rotate around the Top and Bottom Faces
	 * counterClockwise = -1
	 * clockwise = 1
	 * @param dir
	 */
	public void rotateY(int dir) {
		if(dir == 1) {
			int tempFace = getFront();
	        setFront(getRight());
	        setRight(getBack());
	        setBack(getLeft());
	        setLeft(tempFace);
		} else if(dir == -1)  {
			int tempFace = getFront();
	        setFront(getLeft());
	        setLeft(getBack());
	        setBack(getRight());
	        setRight(tempFace);
		}
	}
}