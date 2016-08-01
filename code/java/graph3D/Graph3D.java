package graph3D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
/** 
 * Graph3D - Rotates 3D points around the X,Y, and Z axises, 
 * then projects them onto a 2D plane.
 * @author kenny cason 
 * http://www.kennycason.com
 * 2008 December
 */
public class Graph3D extends JFrame {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -514363092944040524L;
	private Transform3D matOps;
	
	public static void main (String[] args) {
		new Graph3D();
	}  //main
 
	public Graph3D() {
		super("Points");
		Point3D[] pts3D = new Point3D[45];
		
		pts3D[0] = new Point3D(0,80,-40);
		pts3D[1] = new Point3D(0,60,-40);
		pts3D[2] = new Point3D(0,40,-40);
		pts3D[3] = new Point3D(0,20,-40);
		pts3D[4] = new Point3D(0,0,-40);
		pts3D[5] = new Point3D(0,-20,-40);
		pts3D[6] = new Point3D(0,-40,-40); 
		pts3D[7] = new Point3D(0,-60,-40);
		pts3D[8] = new Point3D(0,-80,-40);
		
		pts3D[9] = new Point3D(0,80,-20);
		pts3D[10] = new Point3D(0,60,-20);
		pts3D[11] = new Point3D(0,40,-20);
		pts3D[12] = new Point3D(0,20,-20);
		pts3D[13] = new Point3D(0,0,-20);
		pts3D[14] = new Point3D(0,-20,-20);
		pts3D[15] = new Point3D(0,-40,-20);
		pts3D[16] = new Point3D(0,-60,-20);
		pts3D[17] = new Point3D(0,-80,-20);
		
		pts3D[18] = new Point3D(0,80,0);
		pts3D[19] = new Point3D(0,60,0);
		pts3D[20] = new Point3D(0,40,0);
		pts3D[21] = new Point3D(0,20,0);
		pts3D[22] = new Point3D(0,0,0);
		pts3D[23] = new Point3D(0,-20,0);
		pts3D[24] = new Point3D(0,-40,0);
		pts3D[25] = new Point3D(0,-60,0);
		pts3D[26] = new Point3D(0,-80,0);
	
		pts3D[27] = new Point3D(0,80,20);
		pts3D[28] = new Point3D(0,60,20);
		pts3D[29] = new Point3D(0,40,20);
		pts3D[30] = new Point3D(0,20,20);
		pts3D[31] = new Point3D(0,0,20);
		pts3D[32] = new Point3D(0,-20,20);
		pts3D[33] = new Point3D(0,-40,20);
		pts3D[34] = new Point3D(0,-60,20);
		pts3D[35] = new Point3D(0,-80,20);
		
		pts3D[36] = new Point3D(0,80,40);
		pts3D[37] = new Point3D(0,60,40);
		pts3D[38] = new Point3D(0,40,40);
		pts3D[39] = new Point3D(0,20,40);
		pts3D[40] = new Point3D(0,0,40);
		pts3D[41] = new Point3D(0,-20,40);
		pts3D[42] = new Point3D(0,-40,40);
		pts3D[43] = new Point3D(0,-60,40);
		pts3D[44] = new Point3D(0,-80,40);
		   
		
		
		// set up GUI
		// can't have this in an applet
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new Draw(pts3D)); 
		setSize(210,220);
		setLocation(100,100);
		validate();
		setVisible(true);
		boolean exit = false;
		Draw draw = new Draw(pts3D);
		getContentPane().add(draw);
	    long time = System.currentTimeMillis();
	    int movCamera = 0;
		while (!exit) {
			matOps = new Transform3D();
			double[] camLoc = new double[3];
			camLoc[0] = 30;
			if(movCamera == 0) {
				for(int n = 0; n < pts3D.length; n++) {
				//	draw.setPoint(n, matOps.perspective3D(draw.get(n),camLoc));
					//draw.setPoint(n, matOps.rotateY(draw.get(n),90 ));
				}
				movCamera = 1;
			}
			if(System.currentTimeMillis() - time > 10) {
				time = System.currentTimeMillis();
				//System.out.println("GRAPHING");
				for(int n = 0; n < pts3D.length; n++) {
					draw.setPoint(n, matOps.rotateX(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateY(draw.get(n), -1));
					draw.setPoint(n, matOps.rotateZ(draw.get(n), 1));		
				}
			}
			repaint();
		}
	}
	
	
	private class Draw extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2832523728498871378L;
		private Point3D[] pts3D;
		
		private int xOrig = 100;
		private int yOrig = 100;
		private int graphWidth = xOrig * 2;
		private int graphHeight = yOrig * 2;
		
		private BufferedImage image; 
		
		public Draw(Point3D[] inpts3D) {
			java.net.URL imgURL1 = Graph3D.class.getResource("neuron.GIF");
			try {
				image = ImageIO.read(imgURL1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			pts3D = inpts3D;
		}
		
		public void setPoints(Point3D[] inpts3D) {
			pts3D = inpts3D;
		}
		
		public void setPoint(int i, Point3D inpt3D) {
			pts3D[i] = inpt3D;
		}
		
		public Point3D get(int i) {
			return pts3D[i];
		}
		
		
 
		public void paintComponent(Graphics _g) {
			Graphics2D g = (Graphics2D) _g;
 
			drawAxises(g);

			for (int n=0;n<pts3D.length;n++) {
				drawPoint(g,pts3D[n]);
			} 
		} 
		
		
		public void drawAxises(Graphics2D g) {
			g.drawLine(0,0,0,graphHeight);
			g.drawLine(0,0,graphWidth,0);
			g.drawLine(0,graphHeight,graphWidth,graphHeight);
			g.drawLine(graphWidth,graphHeight,graphWidth,0);
			// X axis
			g.drawLine(0,yOrig,graphWidth,yOrig);
			// Y axis
			g.drawLine(xOrig,0,xOrig,graphHeight);
			g.drawString("x",graphWidth-7, yOrig-2);
			g.drawString("y",xOrig+3, 10);
		}
 
		public void drawSquarePoint(Graphics2D g, Point3D p) {
			int x = xOrig + (int)p.getX();
			int y = yOrig + (int)p.getY();
			g.drawLine(x-1,y-1,x+1,y-1);
			g.drawLine(x+1,y-1,x+1,y+1);
			g.drawLine(x+1,y+1,x-1,y+1);
			g.drawLine(x-1,y+1,x-1,y-1);
		}  
		
		public void drawPoint(Graphics2D g, Point3D p) {
			int x = xOrig + (int)p.getX();
			int y = yOrig + (int)p.getY();
			g.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
		}  
	} 
	
}  

