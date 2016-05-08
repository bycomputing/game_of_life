package com.wordpress.bycomputing.Conway;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MyCustomDialogs {
	private int speed = 0, generations = 0;
	public static final String rules = "\n" + 
			"1.    Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n" + 
			"2.    Any live cell with two or three live neighbours lives on to the next generation.\n" + 
			"3.    Any live cell with more than three live neighbours dies, as if by over-population.\n" + 
			"4.    Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n" + 
			"";
	public static final String about = "\n" +
	        "Conway's Game of Life in Java Swing";
	
	public void setSpeed(int speed) { this.speed = speed; }

	public void setGenerations(int generations) { this.generations = generations; }

	public int getSpeed() { return speed; }

	public int getGenerations() { return generations; }

	public SpeedDialog newSpeedDialog() { return new SpeedDialog(); }
	
	public GensDialog newGensDialog() { return new GensDialog(); }

	class SpeedDialog extends JPanel implements ChangeListener {
		JSlider slider;
		JLabel label;
		
		public SpeedDialog() {
			UIPack();			
		}

		protected void UIPack() {
			JPanel panel = new JPanel(new GridLayout(1, 2));
			slider = new JSlider(0, 100, speed);
			slider.addChangeListener(this);
			label = new JLabel("Speed: " + speed);
			panel.add(slider);
			panel.add(label);
			add(panel);
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			speed = slider.getValue();			
			label.setText("Speed: " + speed);			
		}		
	}
	
	class GensDialog extends SpeedDialog {

		@Override
		protected void UIPack() {
			JPanel panel = new JPanel(new GridLayout(1, 2));
			slider = new JSlider(0, 10000, generations);
			slider.addChangeListener(this);
			label = new JLabel("No. of generation(s): " + generations);
			panel.add(slider);
			panel.add(label);
			add(panel);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			generations = slider.getValue();
			label.setText("No. of generation(s): " + generations);
		}		
	}
}
