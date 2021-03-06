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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.Box;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.wordpress.bycomputing.Conway.GameBoard;
import com.wordpress.bycomputing.Conway.LinkedList;
import com.wordpress.bycomputing.Conway.LinkedList.Linkable;
import com.wordpress.bycomputing.Conway.MyCustomDialogs;

@SuppressWarnings("serial")
public class LifeApplet extends JApplet {
	private static Game life;
	private boolean running;
	private Thread game;	
	private GameBoard gameBoard;
	private MyCustomDialogs dialogs;
	JButton startButn, clearButn, gensButn, speedButn;
	JLabel gensLabel;

	@Override
	public void init() {
		life = new Game();

		LinkedList ll = new LinkedList();
		for (int i = 0; i < welcome.length; ++i)
			ll.insertAtHead(GameBoard.newLinkableCell(welcome[i][0], welcome[i][1]));
		
		gameBoard = new GameBoard(ll);		
		dialogs = new MyCustomDialogs();
		dialogs.setSpeed(60);
		dialogs.setGenerations(100);
		dialogs.setPercentage(25);
		
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        
        JMenu color = new JMenu("Color");
        color.setMnemonic(KeyEvent.VK_C);
        
        JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem random = new JMenuItem("Random seed");
        random.setMnemonic(KeyEvent.VK_R);
		JMenuItem background = new JMenuItem("Background");
		background.setMnemonic(KeyEvent.VK_B);
		JMenuItem fill = new JMenuItem("Fill");
		fill.setMnemonic(KeyEvent.VK_F);
		JMenuItem grid = new JMenuItem("Grid");
		grid.setMnemonic(KeyEvent.VK_G);
		JMenuItem outline = new JMenuItem("Outline");
		outline.setMnemonic(KeyEvent.VK_O);
		JMenuItem about = new JMenuItem("About");
		about.setMnemonic(KeyEvent.VK_A);
		JMenuItem rules = new JMenuItem("Rules of Life");
		rules.setMnemonic(KeyEvent.VK_U);
		JMenuItem source = new JMenuItem("Source code");
		source.setMnemonic(KeyEvent.VK_S);
		
		gameMenu.add(random);
		color.add(background);
		color.add(fill);
		color.add(grid);
		color.add(outline);
		help.add(about);
		help.add(rules);
		help.add(source);
		
		menuBar.add(gameMenu);
		menuBar.add(color);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(help);
		
		this.setJMenuBar(menuBar);
		
		JPanel bottomPanel = new JPanel();
		startButn = new JButton("Start");
		clearButn = new JButton("Clear");
		gensButn = new JButton("Gens");
		speedButn = new JButton("Speed");
		gensLabel = new JLabel(String.format("Generation #%5d%n", dialogs.getGenerations()));		
		
		bottomPanel.add(startButn);
		bottomPanel.add(clearButn);
		bottomPanel.add(gensButn);
		bottomPanel.add(speedButn);
		bottomPanel.add(gensLabel);
		
		Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
		pane.add(gameBoard, BorderLayout.CENTER);
		pane.add(bottomPanel, BorderLayout.SOUTH);
				
		random.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(gameMenu, dialogs.newRandDialog(),
						"Percentage to Seed", JOptionPane.INFORMATION_MESSAGE);
				gameBoard.randomSeed(dialogs.getPercentage());
				
			}
		});
		
		background.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						gameBoard, "Choose background color", gameBoard.getBackground());
				gameBoard.setBackground(color);
			}
		});
		
		fill.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						gameBoard, "Choose fill color", gameBoard.getFillColor());
				gameBoard.setFillColor(color);
				gameBoard.repaint();
			}
		});
		
        grid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						gameBoard, "Choose grid color", gameBoard.getGridColor());
				gameBoard.setGridColor(color);
				gameBoard.repaint();
			}
		});
		
		outline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(
						gameBoard, "Choose outline color", gameBoard.getOutlineColor());
				gameBoard.setOutlineColor(color);
				gameBoard.repaint();				
			}
		});
		
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(help, MyCustomDialogs.about,
						"About", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
		
		rules.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(help, MyCustomDialogs.rules,
						"Rules of Life", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
		
		source.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI("https://github.com/bycomputing/game_of_life"));
					} catch (IOException | URISyntaxException e2) {
						e2.printStackTrace();
					}					
				}				
			}
		});
		
		startButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Start":
					startButn.setText("Stop");
					game = new Thread(life);
					game.start();
					break;
				case "Stop":
					startButn.setText("Start");
					life.stopRunning();
				default:
					break;
				}					
			}
		});
		
		clearButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameBoard.clearWorld();				
			}
		});
		
		gensButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(gensButn, dialogs.newGensDialog(),
						"Number of Generations", JOptionPane.INFORMATION_MESSAGE);
				gensLabel.setText(String.format("Generation #%5d%n", dialogs.getGenerations()));
			}
		});
		
		speedButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(speedButn, dialogs.newSpeedDialog(),
						"Simulation Speed", JOptionPane.INFORMATION_MESSAGE);				
			}
		});
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
	
	class Game implements Runnable {

		@Override
		public void run() {
			int gens = 0;
			if (dialogs.getGenerations() == 0) startButn.setText("Start");
			else running = true;
			while (running) {
				if (gens  == dialogs.getGenerations() - 1) stopRunning();
				gameBoard.simulate();
				++gens;
				gensLabel.setText(String.format("Generation #%5d%n", gens));
				try {
					Thread.sleep(1000/dialogs.getSpeed());				
				} catch (Exception e) {}
			}		
		}	

		private void stopRunning() {
			startButn.setText("Start");
			running = false;		
		}
	}
	
	private static final int[][] welcome = { {24, 23}, {23, 23}, {25, 22}, {24, 21},
			{23, 21}, {22, 22}, {24, 25}, {23, 25}, {22, 23}, {22, 24}, {18, 27}, 
			{19, 27}, {20, 26}, {20, 25}, {20, 24}, {20, 23}, {20, 22}, {19, 25},
			{18, 25}, {17, 22}, {17, 23}, {17, 24}, {15, 25}, {14, 24}, {14, 23},
			{13, 23}, {15, 22}, {15, 21}, {14, 20}, {13, 20}, {41, 10}, {12, 20},
			{12, 21}, {12, 22}, {12, 25}, {12, 24}, {12, 23}, { 7, 27}, { 8, 27},
			{ 9, 26}, { 9, 25}, { 9, 24}, { 9, 23}, { 9, 22}, { 8, 25}, { 7, 25},
			{ 6, 22}, { 6, 23}, { 6, 24}, { 3, 25}, { 2, 25}, { 4, 24}, { 4, 23},
			{ 3, 22}, { 2, 22}, { 4, 21}, { 3, 20}, { 2, 20}, { 1, 25}, { 1, 24},
			{ 1, 23}, { 1, 22}, { 1, 21}, { 1, 20}, {46, 13}, {47, 13}, {48, 12},
			{47, 11}, {46, 11}, {45, 12}, {45, 14}, {47, 15}, {46, 15}, {45, 13},
			{43, 11}, {42, 10}, {41, 11}, {41, 12}, {42, 13}, {41, 13}, {40, 13},
			{41, 14}, {41, 15}, {38, 11}, {38, 13}, {38, 14}, {38, 15}, {36, 15},
			{35, 15}, {34, 10},	{34, 11}, {34, 12}, {34, 13}, {34, 14}, {34, 15},
			{31, 11}, {30, 10}, {29, 10}, {29, 11}, {29, 12}, {29, 15}, {29, 14},
			{30, 13}, {29, 13}, {28, 13}, {24, 12}, {25, 12}, {26, 13}, {26, 14},
			{25, 15}, {24, 15}, {23, 14}, {23, 13}, {18, 13}, {19, 13}, {20, 12},
			{19, 11}, {18, 11}, {17, 12}, {17, 13}, {17, 14}, {19, 15}, {18, 15},
			{15, 13}, {15, 14}, {15, 15}, {14, 12}, {13, 13}, {13, 14}, {13, 15},
			{12, 12}, {11, 13}, {11, 14}, {11, 15}, { 7, 11}, { 8, 11}, { 9, 12},
			{ 9, 13}, { 9, 14}, { 8, 15}, { 7, 15}, { 8, 13}, { 7, 13}, { 6, 14},
			{ 3, 13}, { 4, 13}, { 4, 14}, { 4, 11}, { 3, 10}, { 2, 10}, { 3, 15},
			{ 2, 15}, { 1, 14}, { 1, 13}, { 1, 12}, { 1, 11}, {35,  6}, {36,  6},
			{37,  6}, {38,  5}, {38,  2}, {37,  2}, {36,  2}, {37,  4}, {36,  4},
			{35,  3}, {32,  1}, {33,  1}, {33,  2}, {32,  3}, {28,  8}, {29,  8},
			{30,  7}, {30,  6}, {30,  5}, {30,  4}, {30,  3}, {29,  6}, {28,  6},
			{27,  5}, {27,  4}, {27,  3}, {23,  2}, {24,  2}, {25,  3}, {25,  4},
			{25,  5}, {24,  4}, {23,  4}, {24,  6}, {23,  6}, {22,  5}, {20,  3},
			{20,  4}, {20,  5}, {19,  6}, {18,  4}, {18,  5}, {17,  6}, {16,  5},
			{16,  4}, {16,  3}, {14,  4}, {14,  5}, {14,  6}, {13,  3}, {12,  4},
			{11,  3}, {11,  4}, {11,  5}, {11,  6}, { 8,  3}, { 7,  3}, { 9,  5},
			{ 9,  4}, { 8,  6}, { 7,  6}, { 6,  5}, { 6,  4}, { 4,  5}, { 4,  2},
			{ 3,  1}, { 2,  1}, { 3,  6}, { 2,  6}, { 1,  5}, { 1,  4}, { 1,  3},
			{ 1,  2} };
	
}
