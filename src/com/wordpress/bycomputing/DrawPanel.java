package com.wordpress.bycomputing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLUE);
		
		for (int y = 55; y < 400; y += 12)
		    g2d.drawLine(20, y, getWidth() - 10, y);
		for (int x = 20; x < 630; x += 12)
			g2d.drawLine(x, 55, x, 391);
		
	}

}
