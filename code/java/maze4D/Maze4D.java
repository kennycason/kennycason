

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Random;

/**
 * a 4D maze
 * @author Kenny Cason
 * http://kennycason.com
 *  2009 January 14
 */
public class Maze4D {

    private Rect4D maze;
    private Room4D start;
    private Room4D end;


    public static void main(String[] args) throws IOException {
        int dimX = 0;
        int dimY = 0;
        int dimZ = 0;
        int dimU = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        if(args.length == 1) {
            try {
                dimX = Integer.parseInt(args[0]);
                dimY = Integer.parseInt(args[0]);
                dimZ = Integer.parseInt(args[0]);
                dimU = Integer.parseInt(args[0]);
            } catch(Exception e) {
                System.out.println("Error occured while inputting dimensions, Default = 1x1x1x1");
                dimX = 1; dimY = 1; dimZ = 1; dimU = 1;
            }
        } else if(args.length == 4) {
            try {
                dimX = Integer.parseInt(args[0]);
                dimY = Integer.parseInt(args[1]);
                dimZ = Integer.parseInt(args[2]);
                dimU = Integer.parseInt(args[3]);
            } catch(Exception e) {
                System.out.println("Error occured while inputting dimensions, Default = 1x1x1x1");
                dimX = 1; dimY = 1; dimZ = 1; dimU = 1;
            }
        } else { // assume nothing was supplied
            try {
                System.out.println("Enter DimX of Hyper Maze");
                dimX = Integer.parseInt(in.readLine());
                System.out.println("Enter DimY of Hyper Maze");
                dimY = Integer.parseInt(in.readLine());
                System.out.println("Enter DimZ of Hyper Maze");
                dimZ = Integer.parseInt(in.readLine());
                System.out.println("Enter DimU of Hyper Maze");
                dimU = Integer.parseInt(in.readLine());
            } catch(Exception e) {
                System.out.println("Error occured while inputting dimensions, Default = 1x1x1x1");
                dimX = 1; dimY = 1; dimZ = 1; dimU = 1;
            }
        }

        Maze4D maze4D = new Maze4D(dimX,dimY,dimZ,dimU);
        maze4D.setMazeStart((Room4D)maze4D.getMaze().get(0,0,0,0));
        maze4D.setMazeEnd((Room4D)maze4D.getMaze().get(dimX-1,dimY-1,dimZ-1,dimU-1));
        Random r = new Random();
        maze4D.generateMaze4D(new Room4D(r.nextInt(dimX),r.nextInt(dimY),r.nextInt(dimZ),r.nextInt(dimU)),
                maze4D.getMazeEnd());
        maze4D.unCheckAllRooms();
        System.out.println("Finished Maze Generation");
        Room4D current = maze4D.getMazeStart();
        int changeRoom = 1;
        boolean running = true;
        while(running) {
            if(changeRoom == 1) {
                if(current == maze4D.getMazeEnd()) {
                    System.out.println("Congradulations! You found the end of the Hyper Maze!");
                }
                if(current.isWalked()) {
                    System.out.println("Walked here before!");
                }
                System.out.println("------------------------------");
                System.out.println(current.toString());
                System.out.println(current.toStringConnectingRooms());
            }
            current.setWalked(true);
            String s = in.readLine();
            if( s.equalsIgnoreCase("EXIT")) {
                running = false;
            } else if(s.equalsIgnoreCase("X")) {
                if(current.getRoomX() != null) {
                    current = current.getRoomX();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("-X")) {
                if(current.getRoomMinusX() != null) {
                    current = current.getRoomMinusX();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("Y")) {
                if(current.getRoomY() != null) {
                    current = current.getRoomY();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("-Y")) {
                if(current.getRoomMinusY() != null) {
                    current = current.getRoomMinusY();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("Z")) {
                if(current.getRoomZ() != null) {
                    current = current.getRoomZ();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("-Z")) {
                if(current.getRoomMinusZ() != null) {
                    current = current.getRoomMinusZ();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("U")) {
                if(current.getRoomU() != null) {
                    current = current.getRoomU();
                    changeRoom = 1;
                }
            } else if(s.equalsIgnoreCase("-U")) {
                if(current.getRoomMinusU() != null) {
                    current = current.getRoomMinusU();
                    changeRoom = 1;
                }
            }
        }
    }


    public Maze4D(int dimX, int dimY, int dimZ, int dimU) {
        maze = new Rect4D(dimX, dimY, dimZ, dimU);
        for(int u = 0; u < dimU; u++) {
            for(int z = 0; z < dimZ; z++) {
                for(int y = 0; y < dimY; y++) {
                    for(int x = 0; x < dimX; x++) {
                        maze.set(x, y, z, u, new Room4D(x,y,z,u));
                    }
                }
            }
        }
    }

    public void setMaze(Rect4D maze) {
        this.maze = maze;
    }

    public Rect4D getMaze() {
        return maze;
    }

    public void generateMaze4D(Room4D start, Room4D end) {
        setMazeEnd(end);
        buildRoom(start);
        unCheckAllRooms();
    }

    public void buildRoom(Room4D start) {
    //    System.out.println("Build Room:");
    //    System.out.println(start.toString());
        start.setWalked(true);
        if(start == getMazeEnd()) {
            return;
        }
        Random r = new Random();
        LinkedList<Room4D> rooms = new LinkedList<Room4D>();
        // add all unwalked neighbor rooms that have no connected neighbors
        if(((Room4D)maze.get(start.getPosX()+1,start.getPosY(),
                start.getPosZ(),start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX()+1,start.getPosY(),
                    start.getPosZ(),start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX()+1,start.getPosY(),
                        start.getPosZ(),start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX()-1,start.getPosY(),
                start.getPosZ(),start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX()-1,start.getPosY(),
                    start.getPosZ(),start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX()-1,start.getPosY(),
                        start.getPosZ(),start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY()+1,
                start.getPosZ(),start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY()+1,
                    start.getPosZ(),start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY()+1,
                        start.getPosZ(),start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY()-1,
                start.getPosZ(),start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY()-1,
                    start.getPosZ(),start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY()-1,
                        start.getPosZ(),start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                start.getPosZ()+1,start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY(),
                    start.getPosZ()+1,start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                        start.getPosZ()+1,start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                start.getPosZ()-1,start.getPosU())) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY(),
                    start.getPosZ()-1,start.getPosU())).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                        start.getPosZ()-1,start.getPosU())));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                start.getPosZ(),start.getPosU()+1)) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY(),
                    start.getPosZ(),start.getPosU()+1)).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                        start.getPosZ(),start.getPosU()+1)));
            }
        }
        if(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                start.getPosZ(),start.getPosU()-1)) != null) {
            if(!((Room4D)maze.get(start.getPosX(),start.getPosY(),
                    start.getPosZ(),start.getPosU()-1)).isWalked()) {
                rooms.add(((Room4D)maze.get(start.getPosX(),start.getPosY(),
                        start.getPosZ(),start.getPosU()-1)));
            }
        }
        while(rooms.size() > 0) {
            int i = r.nextInt(rooms.size());

            if(rooms.get(i).isWalked()) {
                return;
            }
        //    System.out.println(rooms.get(i).isWalked());
            int x1 = start.getPosX();
            int y1 = start.getPosY();
            int z1 = start.getPosZ();
            int u1 = start.getPosU();
        //    System.out.println("rooms = " + rooms.size() + " Rooms.get(i) = " + rooms.get(i));
            int x2 = rooms.get(i).getPosX();
            int y2 = rooms.get(i).getPosY();
            int z2 = rooms.get(i).getPosZ();
            int u2 = rooms.get(i).getPosU();
            // System.out.println("x1 "+x1+" y1 "+y1+" z1 "+z1+" u1 "+u1+" x2 "+x2+" y2 "+y2+" z2 "+z2+" u2 "+u2);
            // find which room to connect to based on x,y,z,u coords
            if(x1 == x2 && y1 == y2 && z1 == z2) {
                if(u2 > u1) {
                    start.setRoomU(rooms.get(i));
                    rooms.get(i).setRoomMinusU(start);
                } else if(u1 > u2) {
                    start.setRoomMinusU(rooms.get(i));
                    rooms.get(i).setRoomU(start);
                }
                buildRoom(rooms.get(i));
                rooms.remove(i);
            }
            if(x1 == x2 && y1 == y2 && u1 == u2) {
                if(z2 > z1) {
                    start.setRoomZ(rooms.get(i));
                    rooms.get(i).setRoomMinusZ(start);
                } else if(z1 > z2) {
                    start.setRoomMinusZ(rooms.get(i));
                    rooms.get(i).setRoomZ(start);
                }
                buildRoom(rooms.get(i));
                rooms.remove(i);
            }
            if(x1 == x2 && z1 == z2 && u1 == u2) {
                if(y2 > y1) {
                    start.setRoomY(rooms.get(i));
                    rooms.get(i).setRoomMinusY(start);
                } else if(y1 > y2) {
                    start.setRoomMinusY(rooms.get(i));
                    rooms.get(i).setRoomY(start);
                }
                buildRoom(rooms.get(i));
                rooms.remove(i);
            }
            if(y1 == y2 && z1 == z2 && u1 == u2) {
                if(x2 > x1) {
                    start.setRoomX(rooms.get(i));
                    rooms.get(i).setRoomMinusX(start);
                } else if(x1 > x2) {
                    start.setRoomMinusX(rooms.get(i));
                    rooms.get(i).setRoomX(start);
                }
                buildRoom(rooms.get(i));
                rooms.remove(i);
            }

        }
    }

    public void unCheckAllRooms() {
        for(int u = 0; u < maze.getDimU(); u++) {
            for(int z = 0; z < maze.getDimZ(); z++) {
                for(int y = 0; y < maze.getDimY(); y++) {
                    for(int x = 0; x < maze.getDimX(); x++) {
                        ((Room4D)maze.get(x, y, z, u)).setWalked(false);
                    }
                }
            }
        }
    }

    public Room4D getFirstUnWalkedRoom() {
        for(int u = 0; u < maze.getDimU(); u++) {
            for(int z = 0; z < maze.getDimZ(); z++) {
                for(int y = 0; y < maze.getDimY(); y++) {
                    for(int x = 0; x < maze.getDimX(); x++) {
                        if(!((Room4D)maze.get(x, y, z, u)).isWalked()) {
                            return (Room4D)maze.get(x, y, z, u);
                        }
                    }
                }
            }
        }
        return null;
    }

    public void setMazeStart(Room4D start) {
        this.start = start;
    }

    public void setMazeEnd(Room4D end) {
        this.end = end;
    }

    public Room4D getMazeStart() {
        return start;
    }

    public Room4D getMazeEnd() {
        return end;
    }

    public String toString() {
        String s = "";
        return s;
    }
}
