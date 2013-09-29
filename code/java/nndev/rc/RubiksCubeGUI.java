package rc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
	 * 
	 * @author kenny cason
	 *
	 */
public class RubiksCubeGUI extends JFrame{
	
		private Cube3D rc;
		
	 	private final int WHITE = 1;
	 	private final int GREEN = 2;
	 	private final int RED = 3;
	 	private final int BLUE = 4;
	 	private final int ORANGE = 5;
	 	private final int YELLOW = 6;
		
	 	private final int U = 1;
		private final int UPRIME = 2;
		private final int F = 3;
		private final int FPRIME = 4;
		private final int R = 5;
		private final int RPRIME = 6;
		private final int B = 7;
		private final int BPRIME = 8;
		private final int L = 9;
		private final int LPRIME = 10;
		private final int D = 11;
		private final int DPRIME = 12;
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -5002173823861123982L;
		
		private int winX;
		private int winY;
		//private int origX;
		//private int origY;
		
		/**
		 * 
		 * @param args
		 */
		public static void main(String[] args) {
			new RubiksCubeGUI();
		}
		
		/**
		 * 
		 */
		public RubiksCubeGUI() {
			super("RubiksCubeGUI");
			
			
			rc = new Cube3D();
			
			winX = 390;
			winY = 250;

		//	origX = winX/2;
		//	origY = winY/2;

			// set up GUI
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setSize(winX,winY);
			setLocation(0,0);
			Container container = getContentPane();
			
			//GUIKeyboard keyboard = new GUIKeyboard();
			//GUIMouse mouse = new GUIMouse();
			Buttons buttons = new Buttons();
			drawRC draw = new drawRC();
			
			JSplitPane splitPaneRight = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, draw, buttons);
			splitPaneRight.setDividerLocation(winX - 130);
			
			container.add(splitPaneRight);

			//this.addKeyListener(keyboard);
			//rcgui.addMouseMotionListener(mouse);

			
			validate();
			setVisible(true);
			boolean exit = false;
			while (!exit) {
				repaint();
			}	
		}

		public Cube3D getCube() {
			return rc;
		}
 
		/**
		 * 
		 * @author kenny cason
		 *
		 */
		private class drawRC extends JPanel{
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7169866537591383965L;

			public drawRC() {
				
			}
			
			public void paintComponent(Graphics _g) {
				Graphics2D g = (Graphics2D) _g;
				int x = 5;
				int y = 15;
				g.drawString("Rubiks Cube 3x3", x, y);
				g.drawString("Solved: "+rc.isSolved(),x, y+10);
				g.drawString("Solved %: "+(int)(rc.percentSolved()*100), x, y+20);
				
				int cubeX = 12;
				int cubeY = 40;
				drawSqr(g, cubeX + 36,cubeY, getColor(rc.getPiece(0, 0, 2).getBack()));
				drawSqr(g, cubeX + 48,cubeY, getColor(rc.getPiece(1, 0, 2).getBack()));
				drawSqr(g, cubeX + 60,cubeY, getColor(rc.getPiece(2, 0, 2).getBack()));
				drawSqr(g, cubeX + 36,cubeY + 12, getColor(rc.getPiece(0, 1, 2).getBack()));
				drawSqr(g, cubeX + 48,cubeY + 12, getColor(rc.getPiece(1, 1, 2).getBack()));
				drawSqr(g, cubeX + 60,cubeY + 12, getColor(rc.getPiece(2, 1, 2).getBack()));
				drawSqr(g, cubeX + 36,cubeY + 24, getColor(rc.getPiece(0, 2, 2).getBack()));
				drawSqr(g, cubeX + 48,cubeY + 24, getColor(rc.getPiece(1, 2, 2).getBack()));
				drawSqr(g, cubeX + 60,cubeY + 24, getColor(rc.getPiece(2, 2, 2).getBack()));

				drawSqr(g, cubeX ,cubeY + 36, getColor(rc.getPiece(0, 0, 2).getLeft()));
				drawSqr(g, cubeX + 12,cubeY + 36, getColor(rc.getPiece(0, 1, 2).getLeft()));
				drawSqr(g, cubeX + 24,cubeY + 36, getColor(rc.getPiece(0, 2, 2).getLeft()));
				drawSqr(g, cubeX ,cubeY + 48, getColor(rc.getPiece(0, 0, 1).getLeft()));
				drawSqr(g, cubeX + 12,cubeY + 48, getColor(rc.getPiece(0, 1, 1).getLeft()));
				drawSqr(g, cubeX + 24,cubeY + 48, getColor(rc.getPiece(0, 2, 1).getLeft()));
				drawSqr(g, cubeX ,cubeY + 60, getColor(rc.getPiece(0, 0, 0).getLeft()));
				drawSqr(g, cubeX + 12,cubeY + 60, getColor(rc.getPiece(0, 1, 0).getLeft()));
				drawSqr(g, cubeX + 24,cubeY + 60, getColor(rc.getPiece(0, 2, 0).getLeft()));
				
				drawSqr(g, cubeX + 36,cubeY + 36, getColor(rc.getPiece(0, 2, 2).getTop()));
				drawSqr(g, cubeX + 48,cubeY + 36, getColor(rc.getPiece(1, 2, 2).getTop()));
				drawSqr(g, cubeX + 60,cubeY + 36, getColor(rc.getPiece(2, 2, 2).getTop()));
				drawSqr(g, cubeX + 36,cubeY + 48, getColor(rc.getPiece(0, 2, 1).getTop()));
				drawSqr(g, cubeX + 48,cubeY + 48, getColor(rc.getPiece(1, 2, 1).getTop()));
				drawSqr(g, cubeX + 60,cubeY + 48, getColor(rc.getPiece(2, 2, 1).getTop()));
				drawSqr(g, cubeX + 36,cubeY + 60, getColor(rc.getPiece(0, 2, 0).getTop()));
				drawSqr(g, cubeX + 48,cubeY + 60, getColor(rc.getPiece(1, 2, 0).getTop()));
				drawSqr(g, cubeX + 60,cubeY + 60, getColor(rc.getPiece(2, 2, 0).getTop()));

				drawSqr(g, cubeX + 72,cubeY + 36, getColor(rc.getPiece(2, 2, 2).getRight()));
				drawSqr(g, cubeX + 84,cubeY + 36, getColor(rc.getPiece(2, 1, 2).getRight()));
				drawSqr(g, cubeX + 96,cubeY + 36, getColor(rc.getPiece(2, 0, 2).getRight()));
				drawSqr(g, cubeX + 72,cubeY + 48, getColor(rc.getPiece(2, 2, 1).getRight()));
				drawSqr(g, cubeX + 84,cubeY + 48, getColor(rc.getPiece(2, 1, 1).getRight()));
				drawSqr(g, cubeX + 96,cubeY + 48, getColor(rc.getPiece(2, 0, 1).getRight()));
				drawSqr(g, cubeX + 72,cubeY + 60, getColor(rc.getPiece(2, 2, 0).getRight()));
				drawSqr(g, cubeX + 84,cubeY + 60, getColor(rc.getPiece(2, 1, 0).getRight()));
				drawSqr(g, cubeX + 96,cubeY + 60, getColor(rc.getPiece(2, 0, 0).getRight()));
				

				drawSqr(g, cubeX + 108,cubeY + 36, getColor(rc.getPiece(2, 0, 2).getBottom()));
				drawSqr(g, cubeX + 120,cubeY + 36, getColor(rc.getPiece(1, 0, 2).getBottom()));
				drawSqr(g, cubeX + 132,cubeY + 36, getColor(rc.getPiece(0, 0, 2).getBottom()));
				drawSqr(g, cubeX + 108,cubeY + 48, getColor(rc.getPiece(2, 0, 1).getBottom()));
				drawSqr(g, cubeX + 120,cubeY + 48, getColor(rc.getPiece(1, 0, 1).getBottom()));
				drawSqr(g, cubeX + 132,cubeY + 48, getColor(rc.getPiece(0, 0, 1).getBottom()));
				drawSqr(g, cubeX + 108,cubeY + 60, getColor(rc.getPiece(2, 0, 0).getBottom()));
				drawSqr(g, cubeX + 120,cubeY + 60, getColor(rc.getPiece(1, 0, 0).getBottom()));
				drawSqr(g, cubeX + 132,cubeY + 60, getColor(rc.getPiece(0, 0, 0).getBottom()));
				
				drawSqr(g, cubeX + 36,cubeY + 72, getColor(rc.getPiece(0, 2, 0).getFront()));
				drawSqr(g, cubeX + 48,cubeY + 72, getColor(rc.getPiece(1, 2, 0).getFront()));
				drawSqr(g, cubeX + 60,cubeY + 72, getColor(rc.getPiece(2, 2, 0).getFront()));
				drawSqr(g, cubeX + 36,cubeY + 84, getColor(rc.getPiece(0, 1, 0).getFront()));
				drawSqr(g, cubeX + 48,cubeY + 84, getColor(rc.getPiece(1, 1, 0).getFront()));
				drawSqr(g, cubeX + 60,cubeY + 84, getColor(rc.getPiece(2, 1, 0).getFront()));
				drawSqr(g, cubeX + 36,cubeY + 96, getColor(rc.getPiece(0, 0, 0).getFront()));
				drawSqr(g, cubeX + 48,cubeY + 96, getColor(rc.getPiece(1, 0, 0).getFront()));
				drawSqr(g, cubeX + 60,cubeY + 96, getColor(rc.getPiece(2, 0, 0).getFront()));
				
				g.setColor(Color.black);
				g.drawString("RECENT MOVES",160,15);
				int row = 0;
				int column = 0;
				for(int i = 0; i < 150 && i < rc.getPrevMoves().size(); i++) {
					if (i%10 == 0) {
						row += 10;
						column = 0;
					}
					g.drawString(getMoveName(rc.getPrevMoves().get(rc.getPrevMoves().size() - i - 1)),165+column, 25 + row);
					column += 9;
				}
				
			} 
			
			public String getMoveName(int i) {
					switch (i) {
						case U:
							return "u";
						case UPRIME:
							return "u'";
						case F:
							return "f";
						case FPRIME:
							return "f'";
						case R:
							return "r";
						case RPRIME:
							return "r'";
						case B:
							return "b";
						case BPRIME:
							return "b'";
						case L:
							return "l";
						case LPRIME:
							return "l'";
						case D:
							return "d";
						case DPRIME:
							return "d'";
						default:
							return "unknown";
				}
			}

			public void drawSqr(Graphics2D g, int x, int y, Color color) {
				g.setColor(color);
				g.fillRect(x, y, 10, 10);
			}
			
			public Color getColor(int color) {
				if(color == WHITE) {
					return Color.white;
				} else if(color == YELLOW) {
					return Color.yellow;
				} else if(color == RED) {
					return Color.red;
				} else if(color == ORANGE) {
					return Color.orange;
				} else if(color == BLUE) {
					return Color.blue;
				} else if(color == GREEN) {
					return Color.green;
				}
				return Color.black;
			}
		}
		
		/**
		 * 
		 * @author kenny cason
		 *
		 */
		private class Buttons extends JPanel implements ActionListener {
	
			private static final long serialVersionUID = -9059135647883556109L;

			private JButton reset;
			private JButton scramble;
			private JButton undo;
			
			private JButton b;
			private JButton bprime;
			private JButton f;
			private JButton fprime;
			private JButton l;
			private JButton lprime;
			private JButton r;
			private JButton rprime;
			private JButton u;
			private JButton uprime;
			private JButton d;
			private JButton dprime;
			
			/**
			 * 
			 */
			
			public Buttons() {
				this.setLayout(null);
				reset = new JButton("Reset");
				reset.setBounds(5, 10, 100, 15);
				add(reset);
				reset.addActionListener(this);
				scramble = new JButton("Scramble");
				scramble.setBounds(5, 25, 100, 15);
				add(scramble);
				scramble.addActionListener(this);
				undo = new JButton("Undo");
				undo.setBounds(5, 40, 100, 15);
				add(undo);
				undo.addActionListener(this);
				
				b = new JButton("b");
				b.setBounds(5, 55, 50, 15);
				add(b);
				b.addActionListener(this);
				bprime = new JButton("b'");
				bprime.setBounds(55, 55, 50, 15);
				add(bprime);
				bprime.addActionListener(this);
				f = new JButton("f");
				f.setBounds(5, 70, 50, 15);
				add(f);
				f.addActionListener(this);
				fprime = new JButton("f'");
				fprime.setBounds(55, 70, 50, 15);
				add(fprime);
				fprime.addActionListener(this);
				
				u = new JButton("u");
				u.setBounds(5, 85, 50, 15);
				add(u);
				u.addActionListener(this);
				uprime = new JButton("u'");
				uprime.setBounds(55, 85, 50, 15);
				add(uprime);
				uprime.addActionListener(this);
				d = new JButton("d");
				d.setBounds(5, 100, 50, 15);
				add(d);
				d.addActionListener(this);
				dprime = new JButton("d'");
				dprime.setBounds(55, 100, 50, 15);
				add(dprime);
				dprime.addActionListener(this);
				
				l = new JButton("l");
				l.setBounds(5, 115, 50, 15);
				add(l);
				l.addActionListener(this);
				lprime = new JButton("l'");
				lprime.setBounds(55, 115, 50, 15);
				add(lprime);
				lprime.addActionListener(this);
				r = new JButton("r");
				r.setBounds(5, 130, 50, 15);
				add(r);
				r.addActionListener(this);
				rprime = new JButton("r'");
				rprime.setBounds(55, 130, 50, 15);
				add(rprime);
				rprime.addActionListener(this);
			}
			
			public void actionPerformed(ActionEvent evt) {
				if(evt.getSource() == reset) {
					System.out.println("asdsdf");
					rc.reset();
				} else if(evt.getSource() == scramble) {
					rc.scramble(100);
				}  else if(evt.getSource() == undo) {
					rc.undo();
				} else if(evt.getSource() == b) {
					rc.B();
				} else if(evt.getSource() == bprime) {
					rc.BPrime();
				} else if(evt.getSource() == f) {
					rc.F();
				} else if(evt.getSource() == fprime) {
					rc.FPrime();
				} else if(evt.getSource() == l) {
					rc.L();
				} else if(evt.getSource() == lprime) {
					rc.LPrime();
				} else if(evt.getSource() == r) {
					rc.R();
				} else if(evt.getSource() == rprime) {
					rc.RPrime();
				} else if(evt.getSource() == u) {
					rc.U();
				} else if(evt.getSource() == uprime) {
					rc.UPrime();
				} else if(evt.getSource() == d) {
					rc.D();
				} else if(evt.getSource() == dprime) {
					rc.DPrime();
				}
			}
		}
		
	}