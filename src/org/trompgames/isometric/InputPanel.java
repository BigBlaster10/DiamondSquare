package org.trompgames.isometric;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.trompgames.diamondsquare.DiamondSquare;

public class InputPanel extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1205505933227330482L;
	
	
	private IsoFrame frame;
	
	public InputPanel(IsoFrame frame){
		this.frame = frame;
		
		setLayout(new GridLayout(0,2));

		
		this.setBorder(BorderFactory.createLineBorder(Color.black));

		
		DiamondSquare square = frame.getDiamondSquare();
		
		JButton genButton = new JButton("Generate");
		genButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				frame.getIsoPanel().generateNewMap();
			}			
		});
		
		JSlider smoothness = new JSlider(JSlider.HORIZONTAL,
                1, 20, (int) square.getSmoothness());
		
		smoothness.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {				
				square.setSmoothness(smoothness.getValue());
				square.generate(square.getSeed());

			}			
		});
		
		smoothness.setMajorTickSpacing(1);
		smoothness.setMinorTickSpacing(1);
		smoothness.setPaintTicks(true);
		smoothness.setPaintLabels(true);
		
		JSlider heightRand = new JSlider(JSlider.HORIZONTAL,
                1, 200, (int) frame.getDiamondSquare().getHeightRand());

		
		heightRand.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {				
				square.setHeightRand(heightRand.getValue());
				square.generate(square.getSeed());
			}			
		});
		
		heightRand.setMajorTickSpacing(10);
		heightRand.setMinorTickSpacing(1);
		heightRand.setPaintTicks(true);
		heightRand.setPaintLabels(true);
		
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		this.add(new JLabel(" "));
		this.add(genButton);
		this.add(smoothness);
		this.add(heightRand);
		
	}
	
	
	
	

}
