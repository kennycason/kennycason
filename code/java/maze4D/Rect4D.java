
/**
 * Rect4D - A 4 dimensional rectangle of Objects
 * @author kenny cason
 * http://www.kennycason.com
 * 2009 January 9
 *
 */
public class Rect4D {

    private int dimX;
    private int dimY;
    private int dimZ;
    private int dimU;

    private Object[][][][] structure;

    /**
     * create a 4D rectangle
     * @param dimX
     * @param dimY
     * @param dimZ
     * @param dimU
     */
    public Rect4D(int dimX, int dimY, int dimZ, int dimU) {
        this.dimX = dimX;
        this.dimY = dimY;
        this.dimZ = dimZ;
        this.dimU = dimU;
        structure = new Object[dimX][dimY][dimZ][dimU];
    }

    /**
     * set value of 4D structure at (x,y,z,u)
     * @param x
     * @param y
     * @param z
     * @param u
     * @param object
     */
    public void set(int x, int y, int z, int u, Object object) {
        structure[x][y][z][u] = object;
    }

    /**
     * get object of 4D structure at (x,y,z,u)
     * @param x
     * @param y
     * @param z
     * @param u
     * @return object
     */
    public Object get(int x, int y, int z, int u) {
        if(x >= 0 && x < dimX && y >= 0 && y < dimY && z >= 0 && z < dimZ && u >= 0 && u < dimU) {
            return structure[x][y][z][u];
        }
        return null;
    }

    /**
     * get object of 4D structure at Point4D
     * @param pt
     * @return
     */
    public Object get(Point4D pt) {
        return structure[(int)pt.getX()][(int)pt.getY()][(int)pt.getZ()][(int)pt.getU()];
    }

    /**
     * is there an object at (x,y,z,u)
     * @param x
     * @param y
     * @param z
     * @param u
     * @return boolean
     */
    public boolean isObject(int x, int y, int z, int u) {
        return (get(x,y,z,u) == null);
    }

    /**
     * is there an object at at Point4D
     * @param pt
     * @return
     */
    public boolean isObject(Point4D pt) {
        return (get((int)pt.getX(),(int)pt.getY(),(int)pt.getZ(),(int)pt.getU()) == null);
    }

    /**
     * get length of dimension X
     * @return
     */
    public int getDimX() {
        return dimX;
    }

    /**
     * get length of dimension Y
     * @return
     */
    public int getDimY() {
        return dimY;
    }

    /**
     * get length of dimension Z
     * @return
     */
    public int getDimZ() {
        return dimZ;
    }

    /**
     * get length of dimension U
     * @return
     */
    public int getDimU() {
        return dimU;
    }

    /**
     * returns the Distance between two 4D points
     * @param pt1
     * @param pt2
     * @return
     */
    public double getDistance(Point4D pt1, Point4D pt2) {
        return (Math.sqrt(Math.pow(pt1.getX() - pt2.getX(), 2) + Math.pow(pt1.getY() - pt2.getY(), 2)
                    + Math.pow(pt1.getZ() - pt2.getZ(), 2) + Math.pow(pt1.getU() - pt2.getU(), 2)));
    }

    /**
     * returns the bulk
     * @return
     */
    public int getBulk() {
        return dimX * dimY * dimZ * dimU;
    }

    /**
     * returns the surcell Volume
     * @return
     */
    public int getSurcellVolume() {
        return 8 * dimX * dimY * dimZ;
    }

}
