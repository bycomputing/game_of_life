package com.wordpress.bycomputing;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SliderDialogs {
	
	private Life game;
	
	public SliderDialogs(Life game) {
		this.game = game;
	}

	public SpeedDialog newSpeedDialog() {
		return new SpeedDialog();
	}
	
	public GensDialog newGensDialog() {
		return new GensDialog();
	}

	class SpeedDialog extends JPanel implements ChangeListener {
		JSlider slider;
		JLabel label;
		
		public SpeedDialog() {
			UIPack();			
		}

		protected void UIPack() {
			JPanel panel = new JPanel(new GridLayout(1, 2));
			slider = new JSlider(0, 10, game.getSpeed());
			slider.addChangeListener(this);
			label = new JLabel("Speed #" + game.getSpeed());
			panel.add(slider);
			panel.add(label);
			add(panel);
		}
		
		@Override
		public void stateChanged(ChangeEvent e) {
			int value = slider.getValue();
			game.setSpeed(value);			
			label.setText("Speed #" + value);			
		}		
	}
	
	class GensDialog extends SpeedDialog {

		@Override
		protected void UIPack() {
			JPanel panel = new JPanel(new GridLayout(1, 2));
			slider = new JSlider(0, 10000, game.getGenerations());
			slider.addChangeListener(this);
			label = new JLabel("Generations #" + game.getGenerations());
			panel.add(slider);
			panel.add(label);
			add(panel);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			int value = slider.getValue();
			game.setGenerations(value);
			game.label.setText("Generations #" + value);
			label.setText("Generations #" + value);
		}		
	}
}
