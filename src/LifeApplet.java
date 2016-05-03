

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.wordpress.bycomputing.Conway.GameBoard;
import com.wordpress.bycomputing.Conway.LinkedList.Linkable;
import com.wordpress.bycomputing.Conway.SliderDialogs;

@SuppressWarnings("serial")
public class LifeApplet extends JApplet {
	private static Game life;
	private boolean running;
	private Thread game;	
	private GameBoard gameBoard;
	private SliderDialogs dialogs;
	JButton startButn, clearButn, gensButn, speedButn, quitButn;
	JLabel gensLabel;

	@Override
	public void init() {
		life = new Game();
		JPanel bottomPanel = new JPanel();
		gameBoard = new GameBoard();		
		dialogs = new SliderDialogs();
		dialogs.setSpeed(60);
		dialogs.setGenerations(100);
		
		startButn = new JButton("Start");
		clearButn = new JButton("Clear");
		gensButn = new JButton("Gens");
		speedButn = new JButton("Speed");
		quitButn = new JButton("Quit");
		gensLabel = new JLabel(String.format("Generation #%5d%n", dialogs.getGenerations()));
		
		bottomPanel.add(startButn);
		bottomPanel.add(clearButn);
		bottomPanel.add(gensButn);
		bottomPanel.add(speedButn);
		bottomPanel.add(quitButn);
		bottomPanel.add(gensLabel);
		
		Container pane = getContentPane();
        pane.setLayout(new BorderLayout());
		pane.add(gameBoard, BorderLayout.CENTER);
		pane.add(bottomPanel, BorderLayout.SOUTH);
		
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
				JOptionPane.showMessageDialog(
						null, dialogs.newGensDialog());
				gensLabel.setText(String.format("Generation #%5d%n", dialogs.getGenerations()));
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
			public void actionPerformed(ActionEvent e) {
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
	
}
