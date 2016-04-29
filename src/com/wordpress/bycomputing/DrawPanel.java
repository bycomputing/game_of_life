package com.wordpress.bycomputing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import com.wordpress.bycomputing.Life.LinkableCell;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, Runnable {
	
	private static final int MAXCOL = 50, MAXROW = 28;
	private int gens = 0;
	private static Graphics2D g2d;
	private Life game;
	LinkedList cells;			
	
	public DrawPanel(Life game) {
		this.game = game;
		cells = new LinkedList();
		addMouseListener(this);
		addMouseMotionListener(this);		
	}
	
	public void clearWorld() {
		cells = new LinkedList();
		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) { addLinkableCell(e.getX(), e.getY()); }	

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { addLinkableCell(e.getX(), e.getY()); }
	
	@Override
	public void mouseDragged(MouseEvent e) { addLinkableCell(e.getX(), e.getY()); }

	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {		
		g2d = (Graphics2D) g;		
		g2d.setColor(Color.DARK_GRAY);
		
		for (int y = 55; y < 400; y += 12)
		    g2d.drawLine(20, y, getWidth() - 10, y);
		for (int x = 20; x < 630; x += 12)
			g2d.drawLine(x, 55, x, 391);
		
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) {			
			g2d.setPaint(Color.RED);			
			g2d.draw(new Ellipse2D.Double(
					l.getX() * 12 + 23,l.getY() * 12 + 57, 7, 7));			
			g2d.setPaint(Color.GREEN);			
	        g2d.fill(new Ellipse2D.Double(
	        		l.getX() * 12 + 23, l.getY() * 12 + 57, 7, 7));
		}
	}
	
	private void addLinkableCell(int x, int y) {
		if ((x > 20) && (x < 620) && (y > 56) && (y < 390)) {			
				int col = (x - 20) / 12;
				int row = (y - 56) / 12;
				cells.remove(new LinkableCell(col, row));
				cells.insertAtHead(new LinkableCell(col, row));
				repaint();
			}		
	}

	@Override
	public void run() {
		if (gens < game.getGenerations()) {
			simulate();
			++gens;
			game.label.setText("Generations #" + gens);
			try {
				Thread.sleep(1000/game.getSpeed());
				run();
			} catch (Exception e) {}
		}
		gens = 0;		
	}

	private void simulate() {
        boolean[][] world = new boolean[MAXCOL+2][MAXROW+2];		
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) {
			world[l.getX()+1][l.getY()+1] = true;			
		}
		
		LinkedList survivors = new LinkedList();
		for (int i = 1; i < world.length - 1; i++)
			for (int j = 1; j < world[0].length - 1; j++) {
				int neighbors = 0;
				if (world[i-1][j-1]) { neighbors++; }
                if (world[i-1][j])   { neighbors++; }
                if (world[i-1][j+1]) { neighbors++; }
                if (world[i][j-1])   { neighbors++; }
                if (world[i][j+1])   { neighbors++; }
                if (world[i+1][j-1]) { neighbors++; }
                if (world[i+1][j])   { neighbors++; }
                if (world[i+1][j+1]) { neighbors++; }
                if (world[i][j]) {                     
                    if ((neighbors == 2) || (neighbors == 3)) 
                        survivors.insertAtHead(new LinkableCell(i-1,j-1));                    
                } else {
                    if (neighbors == 3)                     	
                        survivors.insertAtHead(new LinkableCell(i-1,j-1));                    
                }
			}
		cells = new LinkedList();
		for (LinkableCell l = (LinkableCell) survivors.getHead(); l != null; l = (LinkableCell) l.getNext()) {
			cells.insertAtHead(new LinkableCell(l.getX(), l.getY()));
		}
		repaint();		
	}			
}
