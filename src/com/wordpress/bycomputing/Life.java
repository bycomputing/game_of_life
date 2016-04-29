package com.wordpress.bycomputing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.wordpress.bycomputing.LinkedList.Linkable;

@SuppressWarnings("serial")
public class Life extends JFrame {
	
	private int speed = 60, gens = 100;
	private Thread game;
	JButton startButn, clearButn, gensButn, speedButn, quitButn;
	JLabel gensLabel;		

	public Life() {
		super();
		initGUI();				
	}
	
	private void initGUI() {
		this.setTitle("Game of Life");
		this.setSize(640, 480);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
				
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		GameBoard gameBoard = new GameBoard(this);
		
		SliderDialogs dialogs = new SliderDialogs(this);
		
		startButn = new JButton("Start");
		clearButn = new JButton("Clear");
		gensButn = new JButton("Gens");
		speedButn = new JButton("Speed");
		quitButn = new JButton("Quit");
		gensLabel = new JLabel(String.format("Generation #%5d%n", gens));
		
		buttonsPanel.add(startButn);
		buttonsPanel.add(clearButn);
		buttonsPanel.add(gensButn);
		buttonsPanel.add(speedButn);
		buttonsPanel.add(quitButn);
		buttonsPanel.add(gensLabel);
		
		startButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "Start":
					startButn.setText("Stop");
					game = new Thread(gameBoard);
					game.start();
					break;
				case "Stop":
					startButn.setText("Start");
					gameBoard.stop();
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
				JOptionPane.showMessageDialog(
						null, dialogs.newGensDialog());				
			}
		});
		
		speedButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(
						null, dialogs.newSpeedDialog());
				
			}
		});
		
		quitButn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog(
						null, "Do you really want to quit?",
						"Message", JOptionPane.YES_NO_OPTION);
				switch (response) {
				case JOptionPane.YES_OPTION:
					System.exit(0);
				case JOptionPane.NO_OPTION:
					break;
				default:
					break;
			    }				
			}
		});	
		
		mainPanel.add(gameBoard);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		this.add(mainPanel);		
	}

	public int getSpeed() { return speed; }

	public void setSpeed(int speed) { this.speed = speed; }

	public int getGenerations() { return gens; }

	public void setGenerations(int gens) { this.gens = gens; }
	
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Life life = new Life();
            life.setVisible(true);
		});		                  
	}	
}
