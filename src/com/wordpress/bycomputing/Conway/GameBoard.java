/*******************************************************************************
 * John Conway's Game of Life in Java Swing using linked lists
 * Copyright (C) 2016  Ryan Salvador
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *******************************************************************************/

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
import java.util.Random;

import javax.swing.JPanel;
import com.wordpress.bycomputing.Conway.LinkedList.Linkable;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {	
	private static final int BOXSIZE = 12;
	private Dimension gameBoardSize = null;
	private Graphics2D g2d;
	Color gridColor = Color.DARK_GRAY, outlineColor = Color.RED, fillColor = Color.GREEN;
	Random randomGenerator = new Random();
	LinkedList cells;	
	
	public Color getGridColor() { return gridColor; }

	public Color getOutlineColor() { return outlineColor; }

	public Color getFillColor() { return fillColor; }
	
	public void setGridColor(Color gridColor) { this.gridColor = gridColor; }

	public void setOutlineColor(Color outlineColor) { this.outlineColor = outlineColor;	}

	public void setFillColor(Color fillColor) { this.fillColor = fillColor; }

	public GameBoard() { 
		this(new LinkedList());
	}
	
	public GameBoard(LinkedList cells) {
		this.cells = cells;
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		gameBoardSize = new Dimension(getWidth() / BOXSIZE - 2, getHeight() / BOXSIZE - 2);
	}
	
	public void randomSeed(int percent) {
		for (int i = 0; i < gameBoardSize.width; i++)
			for (int j = 0; j < gameBoardSize.height; j++) 
				if (randomGenerator.nextInt(100) < percent)
					addCell(i, j);				
	}

	public void clearWorld() {
		cells = new LinkedList();
		repaint();		
	}
	
	public void simulate() {
        boolean[][] world = new boolean[gameBoardSize.width + 2][gameBoardSize.height + 2];		
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) {
			world[l.x+1][l.y+1] = true;			
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
			cells.insertAtHead(new LinkableCell(l.x, l.y));
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
		updateLinkedList();
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) { addClickedCell(e); }	

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) { addClickedCell(e); }
	
	@Override
	public void mouseDragged(MouseEvent e) { addClickedCell(e); }

	@Override
	public void mouseMoved(MouseEvent e) {}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {		
		g2d = (Graphics2D) g;		
		g2d.setColor(gridColor);
		
		for (int i = 0; i <= gameBoardSize.width; i++)
			g2d.drawLine(((i * BOXSIZE)+BOXSIZE), BOXSIZE,
					     ((i * BOXSIZE) + BOXSIZE), (int) (BOXSIZE + (
					    	   BOXSIZE * gameBoardSize.getHeight())));
		
		for (int i = 0; i <= gameBoardSize.height; i++)
			g2d.drawLine(BOXSIZE, ((i * BOXSIZE) + BOXSIZE),
			            (int) (BOXSIZE * (gameBoardSize.getWidth() + 1)),
			            ((i * BOXSIZE) + BOXSIZE));
		
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) {			
			g2d.setPaint(outlineColor);			
			g2d.draw(new Ellipse2D.Double(l.x * BOXSIZE + BOXSIZE + 1, l.y * BOXSIZE + BOXSIZE + 1, 10, 10));			
			g2d.setPaint(fillColor);			
	        g2d.fill(new Ellipse2D.Double(
	        		l.x * BOXSIZE + BOXSIZE + 1, l.y * BOXSIZE + BOXSIZE + 1, 10, 10));
		}
	}
	
	private void addClickedCell(MouseEvent e) {
		int col = e.getX() / BOXSIZE - 1;
		int row = e.getY() / BOXSIZE - 1;
		addCell(col, row);				
	}
	
	private void addCell(int col, int row) {
		if ((col >= 0) && (col < gameBoardSize.width) && (row >= 0) && (row < gameBoardSize.height)) {				
			cells.remove(new LinkableCell(col, row));
			cells.insertAtHead(new LinkableCell(col, row));
			repaint();
	    }		
	}
	
	private void updateLinkedList() {
		for (LinkableCell l = (LinkableCell) cells.getHead(); l != null; l = (LinkableCell) l.getNext()) 
			if ((l.x > gameBoardSize.width - 1) || (l.y > gameBoardSize.height - 1))
				cells.remove(new LinkableCell(l.x, l.y));		
		repaint();		
	}

	static class LinkableCell implements Linkable
	{		
		int x, y;
		Linkable next;
		
		public LinkableCell(int x, int y) { this.x = x; this.y = y; }
				
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
	
	public static LinkableCell newLinkableCell(int x, int y) {
		return new LinkableCell(x, y);
	}
}
