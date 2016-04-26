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
public class GameOfLife extends JFrame implements ActionListener {
	
	private int speed = 1, generations = 100;
	private final String[] BUTTONS = { "Start", "Clear","Gen",
			                           "Speed", "Quit",
			                           "Generations #" + generations };
	JLabel label;
	DrawPanel drawPanel;
	SliderDialogs sliderDialogs;

	public GameOfLife() {
		super();
		setTitle("Game of Life");
		setSize(640, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
				
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel buttonsPanel = new JPanel();
		drawPanel = new DrawPanel();
		sliderDialogs = new SliderDialogs(this);
		
		for (int i = 0; i < BUTTONS.length; i++) {
			if (i == BUTTONS.length - 1) {
				label = new JLabel(BUTTONS[i]);
				buttonsPanel.add(label);
			} else {
			    JButton button = new JButton(BUTTONS[i]);
		        button.addActionListener(this);
		        buttonsPanel.add(button);
			}
		}
		
		mainPanel.add(drawPanel);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		add(mainPanel);		
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Start":
			//System.out.println("Starting...");
			break;
		case "Clear":
			drawPanel.clearWorld();
			break;
		case "Gen":
			JOptionPane.showMessageDialog(null, sliderDialogs.newGensDialog());
			break;
		case "Speed":
			JOptionPane.showMessageDialog(null, sliderDialogs.newSpeedDialog());
			break;
		case "Quit":
			int response = JOptionPane.showConfirmDialog(
					this, "Do you really want to quit?", "Message", JOptionPane.YES_NO_OPTION);
			switch (response) {
			case JOptionPane.YES_OPTION:
				System.exit(0);
			case JOptionPane.NO_OPTION:
				break;
			default:
				break;
		    }
		default:
			break;
		};		
	}
	
	static class LinkableCell implements Linkable
	{
		int x, y;
		Linkable next;
		public LinkableCell(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public Linkable getNext() {
			return next;
		}
		@Override
		public void setNext(Linkable node) {
			next = node;				
		}
		
		public String toString() {
			return x + " " + y;
		}
		
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof LinkableCell)) return false;
			if (((LinkableCell) o).x == this.x && ((LinkableCell) o).y == this.y) return true;
			return false;
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GameOfLife life = new GameOfLife();
            life.setVisible(true);
		});		                  
	}	
}
