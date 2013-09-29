package graph4D;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
 
/** 
 * Graph4D - Rotates 4D points around the XY, YZ, XZ, XU, YU, and ZU axises, 
 * then projects them onto a 2D plane.
 * @author kenny cason 
 * http://www.ken-soft.com
 * 2009 January 8
 *
 */
public class Graph4D extends JFrame {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -514363092944040524L;
	private Transform4D matOps;
	
	public static void main (String[] args) {
		new Graph4D();
	}  //main
 
	public Graph4D() {
		super("Points");
		Point4D[] pts4D = new Point4D[16];
		
		
		pts4D[0] = new Point4D(-40,-40,-40,-40);
		pts4D[1] = new Point4D(-20,-20,-20,20);
		pts4D[2] = new Point4D(-40,-40,40,-40);
		pts4D[3] = new Point4D(-20,-20,20,20);
		pts4D[4] = new Point4D(-40,40,-40,-40);
		pts4D[5] = new Point4D(-20,20,-20,20);
		pts4D[6] = new Point4D(-40,40,40,-40);
		pts4D[7] = new Point4D(-20,20,20,20); 
		
		pts4D[8] = new Point4D(40,-40,-40,-40);
		pts4D[9] = new Point4D(20,-20,-20,20);
		pts4D[10] = new Point4D(40,-40,40,-40);
		pts4D[11] = new Point4D(20,-20,20,20);
		pts4D[12] = new Point4D(40,40,-40,-40);
		pts4D[13] = new Point4D(20,20,-20,20);
		pts4D[14] = new Point4D(40,40,40,-40);
		pts4D[15] = new Point4D(20,20,20,20);
		
		/*
		pts4D[0] = new Point4D(-40,-40,-40,-40);
		pts4D[1] = new Point4D(-40,-40,-40,40);
		pts4D[2] = new Point4D(-40,-40,40,-40);
		pts4D[3] = new Point4D(-40,-40,40,40);
		pts4D[4] = new Point4D(-40,40,-40,-40);
		pts4D[5] = new Point4D(-40,40,-40,40);
		pts4D[6] = new Point4D(-40,40,40,-40);
		pts4D[7] = new Point4D(-40,40,40,40); 
		
		pts4D[8] = new Point4D(40,-40,-40,-40);
		pts4D[9] = new Point4D(40,-40,-40,40);
		pts4D[10] = new Point4D(40,-40,40,-40);
		pts4D[11] = new Point4D(40,-40,40,40);
		pts4D[12] = new Point4D(40,40,-40,-40);
		pts4D[13] = new Point4D(40,40,-40,40);
		pts4D[14] = new Point4D(40,40,40,-40);
		pts4D[15] = new Point4D(40,40,40,40);
		*/
		
		
		// set up GUI
		// can't have this in an applet
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new Draw(pts4D)); 
		setSize(210,220);
		setLocation(100,100);
		validate();
		setVisible(true);
		boolean exit = false;
		Draw draw = new Draw(pts4D);
		getContentPane().add(draw);
	    long time = System.currentTimeMillis();

	    matOps = new Transform4D();


		while (!exit) {
			if(System.currentTimeMillis() - time > 30) {
				time = System.currentTimeMillis();
				//System.out.println("GRAPHING");
				for(int n = 0; n < pts4D.length; n++) {
					draw.setPoint(n, matOps.rotateXY(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateYZ(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateXZ(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateXU(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateYU(draw.get(n), 1));
					draw.setPoint(n, matOps.rotateZU(draw.get(n), 1));
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

		private Point4D[] pts4D;
		
		private int xOrig = 100;
		private int yOrig = 100;
		private int graphWidth = xOrig * 2;
		private int graphHeight = yOrig * 2;
		
		private BufferedImage image; 
		
		public Draw(Point4D[] inpts4D) {
			java.net.URL imgURL1 = Graph4D.class.getResource("neuron.GIF");
			try {
				image = ImageIO.read(imgURL1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			pts4D = inpts4D;
		}
		
		public void setPoints(Point4D[] inpts4D) {
			pts4D = inpts4D;
		}
		
		public void setPoint(int i, Point4D inpt4D) {
			pts4D[i] = inpt4D;
		}
		
		public Point4D get(int i) {
			return pts4D[i];
		}
		
		
		public void paintComponent(Graphics _g) {
			Graphics2D g = (Graphics2D) _g;
 
			drawAxises(g);

			drawLine(g,pts4D[0],pts4D[1]);
			drawLine(g,pts4D[0],pts4D[2]);
			drawLine(g,pts4D[0],pts4D[4]);
			drawLine(g,pts4D[0],pts4D[8]);
			drawLine(g,pts4D[1],pts4D[3]);
			drawLine(g,pts4D[1],pts4D[5]);
			drawLine(g,pts4D[1],pts4D[9]);
			drawLine(g,pts4D[2],pts4D[3]);
			drawLine(g,pts4D[2],pts4D[6]);
			drawLine(g,pts4D[2],pts4D[10]);
			drawLine(g,pts4D[3],pts4D[2]);
			drawLine(g,pts4D[3],pts4D[7]);
			drawLine(g,pts4D[3],pts4D[11]);
			drawLine(g,pts4D[4],pts4D[5]);
			drawLine(g,pts4D[4],pts4D[6]);
			drawLine(g,pts4D[4],pts4D[12]);
			drawLine(g,pts4D[5],pts4D[7]);
			drawLine(g,pts4D[5],pts4D[13]);
			drawLine(g,pts4D[6],pts4D[7]);	
			drawLine(g,pts4D[6],pts4D[14]);
			drawLine(g,pts4D[7],pts4D[15]);
			drawLine(g,pts4D[8],pts4D[9]);
			drawLine(g,pts4D[8],pts4D[10]);
			drawLine(g,pts4D[8],pts4D[12]);
			drawLine(g,pts4D[9],pts4D[11]);
			drawLine(g,pts4D[9],pts4D[13]);
			drawLine(g,pts4D[10],pts4D[11]);
			drawLine(g,pts4D[10],pts4D[14]);
			drawLine(g,pts4D[11],pts4D[15]);
			drawLine(g,pts4D[12],pts4D[13]);
			drawLine(g,pts4D[12],pts4D[14]);
			drawLine(g,pts4D[13],pts4D[15]);
			drawLine(g,pts4D[14],pts4D[15]);
			
			for (int n=0;n<pts4D.length;n++) {
				drawPoint(g,pts4D[n]);
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
 
		public void drawLine(Graphics2D g, Point4D p1, Point4D p2) {
			g.drawLine(xOrig + (int)p1.getX(), yOrig + (int)p1.getY(),
					xOrig + (int)p2.getX(),yOrig + (int)p2.getY());
		}
		
		public void drawPoint(Graphics2D g, Point4D p) {
			int x = xOrig + (int)p.getX();
			int y = yOrig + (int)p.getY();
			g.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
		}  
	} 
	
}  

