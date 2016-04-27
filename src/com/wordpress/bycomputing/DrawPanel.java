package com.wordpress.bycomputing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {
	
	private static final int MAXCOL = 50, MAXROW = 28, ALIVE = 1, DEAD = 0;
	private int row, col;
	private static int[][] world, nums;
	private static Graphics2D g2d;
			
	public DrawPanel() {
		world = new int[MAXROW][MAXCOL];
		row = col = -1;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void clearWorld() {
		world = new int[MAXROW][MAXCOL];
		repaint();		
	}

	@Override
	public void mouseClicked(MouseEvent e) { addPoint(e); }	

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { addPoint(e); }
	
	@Override
	public void mouseDragged(MouseEvent e) { addPoint(e); }

	@Override
	public void mouseMoved(MouseEvent e) {}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {		
		g2d = (Graphics2D) g;		
		g2d.setColor(Color.BLUE);
		
		for (int y = 55; y < 400; y += 12)
		    g2d.drawLine(20, y, getWidth() - 10, y);
		for (int x = 20; x < 630; x += 12)
			g2d.drawLine(x, 55, x, 391);
		
		for (int r = 0; r < MAXROW; ++r)
			for (int c = 0; c < MAXCOL; ++c)
				if (world[r][c] == ALIVE) {
					g2d.setPaint(Color.RED);
					g2d.draw(new Ellipse2D.Double(c * 12 + 23, r * 12 + 57, 7, 7));
					g2d.setPaint(Color.GREEN);
			        g2d.fill(new Ellipse2D.Double(c * 12 + 23, r * 12 + 57, 7, 7));
		        }
	}
	
	private void addPoint(MouseEvent e) {
		if ((e.getX() > 20) && (e.getX() < 620) &&
				(e.getY() > 56) && (e.getY() < 390)) {			
				col = (e.getX() - 20) / 12;
				row = (e.getY() - 56) / 12;
				if (world[row][col] == DEAD) {				
					world[row][col] = ALIVE;
					repaint();
				}
			}		
	}	
}
