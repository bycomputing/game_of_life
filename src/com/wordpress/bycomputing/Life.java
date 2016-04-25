package com.wordpress.bycomputing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.wordpress.bycomputing.LinkedList.Linkable;

@SuppressWarnings("serial")
public class Life extends JFrame implements ActionListener {	
	
	public Life() {
		super();
		this.setTitle("Game of Life");
		this.setSize(640, 480);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
			
		String[] buttons = {"Start", "Clear", "Gen", "Speed", "Quit",
				            "Number of generations #100"};
				
		JPanel mainPanel = new JPanel(new BorderLayout());
		DrawPanel drawPanel = new DrawPanel();
		JPanel buttonsPanel = new JPanel();				
		
		for (int i = 0; i < buttons.length; i++) {
			if (i == buttons.length -1) {
				JLabel label = new JLabel(buttons[i]);
				buttonsPanel.add(label);
			} else {
			    JButton button = new JButton(buttons[i]);
		        button.addActionListener(this);
		        buttonsPanel.add(button);
			}
		}
		mainPanel.add(drawPanel);
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		add(mainPanel);		
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
			Life life = new Life();
            life.setVisible(true);
		});		                  
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Start":
			System.out.println("Starting...");
			break;
		case "Clear":
			System.out.println("Clearing...");
			break;
		case "Gen":
			System.out.println("Changing number of generations...");
			break;
		case "Speed":
			System.out.println("Changing speed...");
			break;
		case "Quit":
			System.exit(0);
		default:
			break;
		};		
	}
}
