package com.wordpress.bycomputing.Conway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import com.wordpress.bycomputing.Conway.LinkedList.Linkable;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {	
	private static final int BOXSIZE = 12;
	private Dimension gameBoardSize = null;
	private static Graphics2D g2d;
	LinkedList cells;				
	
	public GameBoard() {
		cells = new LinkedList();
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);		
	}
	
	public void clearWorld() {
		cells = new LinkedList();
		repaint();		
	}
	
	public void addLinkableCell(int col, int row) {
		if ((col >= 0) && (col < gameBoardSize.width) && (row >= 0) && (row < gameBoardSize.height)) {				
			cells.remove(new LinkableCell(col, row));
			cells.insertAtHead(new LinkableCell(col, row));
			repaint();
	    }		
	}

	public void simulate() {
        boolean[][] world = new boolean[gameBoardSize.width + 2][gameBoardSize.height + 2];		
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
	
	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		gameBoardSize = new Dimension(getWidth() / BOXSIZE - 2, getHeight() / BOXSIZE - 2);		
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) { addCell(e); }	

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { addCell(e); }
	
	@Override
	public void mouseDragged(MouseEvent e) { addCell(e); }

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
		
		for (int i = 0; i <= gameBoardSize.width; i++)
			g2d.drawLine(((i * BOXSIZE)+BOXSIZE), BOXSIZE,
					     ((i * BOXSIZE) + BOXSIZE), (int) (BOXSIZE + (
					    	   BOXSIZE * gameBoardSize.getHeight())));
		
		for (int i = 0; i <= gameBoardSize.height; i++)
			g2d.drawLine(BOXSIZE, ((i * BOXSIZE) + BOXSIZE),
			            (int) (BOXSIZE * (gameBoardSize.getWidth() + 1)),
			            ((i * BOXSIZE) + BOXSIZE));
		
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) {			
			g2d.setPaint(Color.RED);			
			g2d.draw(new Ellipse2D.Double(l.getX() * BOXSIZE + BOXSIZE + 1, l.getY() * BOXSIZE + BOXSIZE + 1, 10, 10));			
			g2d.setPaint(Color.GREEN);			
	        g2d.fill(new Ellipse2D.Double(
	        		l.getX() * BOXSIZE + BOXSIZE + 1, l.getY() * BOXSIZE + BOXSIZE + 1, 10, 10));
		}
	}
	
	private void addCell(MouseEvent e) {
		int col = e.getX() / BOXSIZE - 1;
		int row = e.getY() / BOXSIZE - 1;
		addLinkableCell(col, row);				
	}
	
	static class LinkableCell implements Linkable
	{		
		int x, y;
		Linkable next;
		
		public LinkableCell(int x, int y) { this.x = x; this.y = y; }
		
		public int getX() { return x; }
		
		public int getY() { return y; }
		
		public String toString() { return x + " " + y; }
		
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof LinkableCell)) return false;
			if (((LinkableCell) o).x == this.x
					&& ((LinkableCell) o).y == this.y) return true;
			return false;
		}
		
		@Override
		public Linkable getNext() { return next; }
		
		@Override
		public void setNext(Linkable node) { next = node; }		
	}
}
