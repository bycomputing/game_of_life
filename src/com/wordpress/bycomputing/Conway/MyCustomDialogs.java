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

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class MyCustomDialogs {
	private int speed = 0, generations = 0, percentage = 0;
	public static final String rules = "\n" + 
			"1.    Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n" + 
			"2.    Any live cell with two or three live neighbours lives on to the next generation.\n" + 
			"3.    Any live cell with more than three live neighbours dies, as if by over-population.\n" + 
			"4.    Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n" + 
			"";
	public static final String about = "John H. Conway's Game of Life in Java Swing using linked lists\n" + 
			"Copyright (C) 2016  Ryan Salvador        \n" + 
			"\n" + 
			"This program is free software; you can redistribute it and/or\n" + 
			"modify it under the terms of the GNU General Public License\n" + 
			"as published by the Free Software Foundation; either version 2\n" + 
			"of the License, or (at your option) any later version.\n" + 
			"\n" + 
			"This program is distributed in the hope that it will be useful,\n" + 
			"but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + 
			"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" + 
			"GNU General Public License for more details.\n" + 
			"\n" + 
			"You should have received a copy of the GNU General Public License\n" + 
			"along with this program; if not, write to the Free Software\n" + 
			"Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA\n" + 
			"02110-1301, USA.\n" + 
			"\n" + 
			"bycomputing.wordpress.com - email: salvadorrye@gmail.com";
	
	public int getSpeed() { return speed; }
	
	public void setSpeed(int speed) { this.speed = speed; }
	
	public int getGenerations() { return generations; }

	public void setGenerations(int generations) { this.generations = generations; }	

	public int getPercentage() { return percentage; }

	public void setPercentage(int percentage) { this.percentage = percentage; }

	public SpeedDialog newSpeedDialog() { return new SpeedDialog(); }
	
	public GensDialog newGensDialog() { return new GensDialog(); }
	
	public RandDialog newRandDialog() { return new RandDialog(); }

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
			label = new JLabel(generations + " generation(s)");
			panel.add(slider);
			panel.add(label);
			add(panel);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			generations = slider.getValue();
			label.setText(generations + " generation(s)");
		}		
	}
	
	class RandDialog extends SpeedDialog {

		@Override
		protected void UIPack() {
			JPanel panel = new JPanel(new GridLayout(1, 2));
			slider = new JSlider(0, 100, percentage);
			slider.addChangeListener(this);
			label = new JLabel("Seed " + percentage + "%");
			panel.add(slider);
			panel.add(label);
			add(panel);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			percentage = slider.getValue();
			label.setText("Seed " + percentage + "%");
		}	
	}
}
