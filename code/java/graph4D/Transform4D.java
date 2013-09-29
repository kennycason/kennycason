package graph4D;
/**
 * this class contains transformation functions for a 4D vector
 * @author kenny cason
 * http://www.ken-soft.com
 * 2009 January 8
 */
public class Transform4D {
	
	public Transform4D() {
	}
	 
	/**
	 * [(cos(a), -sin(a), 0, 0),
	 *  (sin(a), cos(a) , 0, 0),
	 *  (0     , 0      , 1, 0),
	 *  (0	   , 0		, 0, 1)]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateXY(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX() * Math.cos(theta) + point.getY() * -Math.sin(theta);
	    p[1] = point.getX() * Math.sin(theta) + point.getY() * Math.cos(theta);
	    p[2] = point.getZ();
	    p[3] = point.getU();
	    point.set(p);
		return point;
	}
	
	/**
	 * [(1, 0      , 0     , 0),
	 *  (0, cos(a) , sin(a), 0),
	 *  (0, -sin(a), cos(a), 0),
	 *  (0,        , 0     , 1)]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateYZ(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX();
	    p[1] = point.getY() * Math.cos(theta) + point.getZ() * Math.sin(theta);
	    p[2] = point.getY() * -Math.sin(theta) + point.getZ() * Math.cos(theta);
	    p[3] = point.getU();
	    point.set(p);
		return point;
	}

	/**
	 * [(cos(a), 0, -sin(a), 0),
	 *  (0     , 1, 0      , 0),
	 *  (sin(a), 0, cos(a) , 0),
	 *  (0     , 0, 0      , 1)]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateXZ(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX() * Math.cos(theta) + point.getZ() * -Math.sin(theta);
	    p[1] = point.getY();
	    p[2] = point.getX() * Math.sin(theta) + point.getZ() * Math.cos(theta);
	    p[3] = point.getU();
	    point.set(p);
		return point;
	}
	
	
	/**
	 * [(cos(a) , 0, 0, sin(a)),
	 *  (0      , 1, 0, 0),
	 *  (0      , 0, 1, 0),
	 *  (-sin(a), 0, 0, cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateXU(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
		p[0] = point.getX() * Math.cos(theta) + point.getU() * Math.sin(theta);
	    p[1] = point.getY();
	    p[2] = point.getZ();
	    p[3] = point.getX() * -Math.sin(theta) + point.getU() * Math.cos(theta);
	  //  System.out.println("X("+p[0]+") Y("+p[1]+") Z("+p[2]+")");
	    point.set(p);
		return point;
	}
	
	/**
	 * [(1, 0     , 0, 0),
	 *  (0, cos(a), 0, -sin(a)),
	 *  (0, 0     , 1, 0),
	 *  (0, sin(a),	0, cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateYU(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX();
	    p[1] = point.getY() * Math.cos(theta) + point.getU() * -Math.sin(theta);
	    p[2] = point.getZ();
	    p[3] = point.getY() * Math.sin(theta) + point.getU() * Math.cos(theta);
	    point.set(p);
		return point;
	}
	
	/**
	 * [(1, 0, 0     , 0),
	 *  (0, 1, 0     , 0),
	 *  (0, 0, cos(a), -sin(a)),
	 *  (0, 0, sin(a), cos(a))]
	 * @param point
	 * @param theta
	 * @return
	 */
	public Point4D rotateZU(Point4D point, double theta) {
		double[] p = new double[point.get().length];
		theta = Math.toRadians(theta);
	    p[0] = point.getX();
	    p[1] = point.getY();
	    p[2] = point.getZ() * Math.cos(theta) + point.getU() * -Math.sin(theta);
	    p[3] = point.getZ() * Math.sin(theta) + point.getU() * Math.cos(theta);
	    point.set(p);
		return point;
	}
	
	/**
	 * [(1, 0, 0),
	 *  (0, 1, 0),
	 *  (0, 0 ,0)]
	 * @param point
	 * @return
	 */
	public Point2D project4Dto2D(Point4D point) {
		Point2D p = new Point2D(0,0);
		p.setX(point.getX());
		p.setY(point.getY());
		return p;
	}
	
	/**
	 * 
	 * @param point
	 * @param cameraLoc
	 * @return
	 */
	public Point4D perspective4D(Point4D point, double[] cameraLoc) {
		point.setX(point.getX() - cameraLoc[0]);
		point.setY(point.getY() - cameraLoc[1]);
		point.setZ(point.getZ() - cameraLoc[2]);
		point.setU(point.getU() - cameraLoc[3]);
		return point;
	}
	
	
}
