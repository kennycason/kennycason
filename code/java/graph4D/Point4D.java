package graph4D;
/**
 * 4D vector
 * @author kenny cason
 * http://www.kennycason.com
 * 2009 January 8
 */
public class Point4D {
 
	private double[] p;
	
	public Point4D(double x, double y, double z, double u) {
		p = new double[4];
		p[0] = x;
		p[1] = y;
		p[2] = z;
		p[3] = u;
	}	
	
	public Point4D(double[] podouble) {
		p = podouble;
	}	
	
	public void set(double[] podouble) {
		p = podouble;
	}
	
	public double[] get() {
		return p;
	}
	
	public void setX(double x) {
		p[0] = x;
	}
	
	public void setY(double y) {
		p[1] = y;
	}
	
	public void setZ(double z) {
		p[2] = z;
	}
	
	public void setU(double u) {
		p[3] = u;
	}
	
	public double getX() {
		return p[0];
	}

	public double getY() {
		return p[1];
	}
	
	public double getZ() {
		return p[2];
	}
	
	public double getU() {
		return p[3];
	}
	
}
