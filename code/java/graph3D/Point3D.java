package graph3D;
/**
 * 3D vector
 * @author kenny cason
 * http://www.ken-soft.com
 * 2009 January 8
 */
public class Point3D {
 
	private double[] p;
	
	public Point3D(double x, double y, double z) {
		p = new double[3];
		p[0] = x;
		p[1] = y;
		p[2] = z;
	}	
	
	public Point3D(Point3D p) {
		this.p[0] = p.getX();
		this.p[1] = p.getY();
		this.p[2] = p.getZ();
	}
	
	public Point3D(double[] podouble) {
		p = podouble;
	}	
	
	public void set(double[] podouble) {
		p = podouble;
	}
	
	public void set(Point3D p) {
		this.p[0] = p.getX();
		this.p[1] = p.getY();
		this.p[2] = p.getZ();
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
	
	public double getX() {
		return p[0];
	}

	public double getY() {
		return p[1];
	}
	
	public double getZ() {
		return p[2];
	}
	
	public void vectorMultiply(Point3D point) {
		p[0] = p[0] * point.getX();
		p[1] = p[1] * point.getY();
		p[2] = p[2] * point.getZ();
	}
}
