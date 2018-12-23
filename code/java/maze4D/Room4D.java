/*
 * a simple 4D room
 * @author Kenny Cason
 * http://kennycason.com
 * 14 January 2009
 */
public class Room4D {


    private int posX;
    private int posY;
    private int posZ;
    private int posU;

    private Room4D X;
    private Room4D minusX;
    private Room4D Y;
    private Room4D minusY;
    private Room4D Z;
    private Room4D minusZ;
    private Room4D U;
    private Room4D minusU;

    private boolean walked;

    public Room4D(int x, int y, int z, int u) {
        posX = x;
        posY = y;
        posZ = z;
        posU = u;
        walked = false;
        X = null;
        minusX = null;
        Y = null;
        minusY = null;
        Z = null;
        minusZ = null;
        U = null;
        minusU = null;
    }

    public void setWalked(boolean walked) {
        this.walked = walked;
    }

    public boolean isWalked() {
        return walked;
    }

    public Point4D getPos() {
        return new Point4D(posX, posY, posZ, posU);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }

    public int getPosU() {
        return posU;
    }

    public void setRoomX(Room4D r) {
        X = r;
    }

    public void setRoomMinusX(Room4D r) {
        minusX = r;
    }

    public void setRoomY(Room4D r) {
        Y = r;
    }

    public void setRoomMinusY(Room4D r) {
        minusY = r;
    }

    public void setRoomZ(Room4D r) {
        Z = r;
    }

    public void setRoomMinusZ(Room4D r) {
        minusZ = r;
    }

    public void setRoomU(Room4D r) {
        U = r;
    }

    public void setRoomMinusU(Room4D r) {
        minusU = r;
    }

    public Room4D getRoomX() {
        return X;
    }

    public Room4D getRoomMinusX() {
        return minusX;
    }

    public Room4D getRoomY() {
        return Y;
    }

    public Room4D getRoomMinusY() {
        return minusY;
    }

    public Room4D getRoomZ() {
        return Z;
    }

    public Room4D getRoomMinusZ() {
        return minusZ;
    }

    public Room4D getRoomU() {
        return U;
    }

    public Room4D getRoomMinusU() {
        return minusU;
    }


    public String toString() {
        String s = "";
        s += "X = " + Integer.toString(posX) + " Y = " + Integer.toString(posY)
            + " Z = " + Integer.toString(posZ) + " U = " + Integer.toString(posU);
        return s;
    }

    public String toStringConnectingRooms() {
        String s = "";
        if(getRoomX() != null) {
            s += " X ";
        }
        if(getRoomMinusX() != null) {
            s += " -X ";
        }
        if(getRoomY() != null) {
            s += " Y ";
        }
        if(getRoomMinusY() != null) {
            s += " -Y ";
        }
        if(getRoomZ() != null) {
            s += " Z ";
        }
        if(getRoomMinusZ() != null) {
            s += " -Z ";
        }
        if(getRoomU() != null) {
            s += " U ";
        }
        if(getRoomMinusU() != null) {
            s += " -U ";
        }
        s += "\n";
        return s;
    }


}
