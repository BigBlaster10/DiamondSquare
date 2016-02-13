package org.trompgames.isometric;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
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
		
		JLabel smoothnessLabel = new JLabel("" + (square.getSmoothness()));
		JLabel heightRandLabel = new JLabel("" + square.getHeightRand());
		
		JButton save = new JButton("Save to file");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				square.writeToFile(new File("test.txt"));
			}			
		});
		

		JTextField seed = new JTextField(18);
		seed.setText("" + square.getSeed());
		seed.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent event) {				
				if(seed.getText().equals("" + square.getSeed()) || seed.getText().trim().equals("")) return;
				try{
					Integer.parseInt(seed.getText());
				}catch(Exception e){return;}
				square.generate(Integer.parseInt(seed.getText()));			
			}			
		});
		
		JButton genButton = new JButton("Generate");
		genButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				frame.getIsoPanel().generateNewMap();
				seed.setText("" + square.getSeed());
			}			
		});
		
		JSlider smoothness = new JSlider(JSlider.HORIZONTAL,
                1, 500, (int) (square.getSmoothness() * 100));
		//200
		smoothness.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {				
				square.setSmoothness(1.0 * smoothness.getValue()/100);
				square.generate(square.getSeed());
				smoothnessLabel.setText("" + 1.0 * smoothness.getValue()/100);
			}			
		});
		
		smoothness.setMajorTickSpacing(1);
		smoothness.setMinorTickSpacing(1);
		smoothness.setPaintTicks(true);
		//smoothness.setPaintLabels(true);
		
		JSlider heightRand = new JSlider(JSlider.HORIZONTAL,
                1, 500, (int) (frame.getDiamondSquare().getHeightRand() * 100));
		//100
		
		heightRand.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {				
				square.setHeightRand(1.0 * heightRand.getValue()/100);
				square.generate(square.getSeed());
				heightRandLabel.setText("" + 1.0 * heightRand.getValue()/100);
			}			
		});
		
		heightRand.setMajorTickSpacing(9);
		//heightRand.setMinorTickSpacing(17);
		heightRand.setPaintTicks(true);
		//heightRand.setPaintLabels(true);
		
		


		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        
        add(genButton, gbc);
        gbc.gridx++;

        add(seed, gbc);
        gbc.gridx++;

        add(save, gbc);
        gbc.gridy++;
        
        gbc.gridx = 0;
        
        add(new JLabel("Smoothness:"), gbc);
        gbc.gridy++;
        add(new JLabel("HeightRand:"), gbc);

        gbc.gridx++;
        gbc.gridy = 1;
        add(smoothness, gbc);
        gbc.gridx++;
        
        add(smoothnessLabel, gbc);
        gbc.gridx--;
        
        
        gbc.gridy++;
        add(heightRand, gbc);
        gbc.gridx++;

        add(heightRandLabel, gbc);
		
	}
	
	
	public IsoFrame getIsoFrame(){
		return frame;
	}
	
	
	/*
	 * public InputPanel(IsoFrame frame){
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
		
		JButton save = new JButton("Save to file");
		save.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				square.writeToFile(new File("test.txt"));
			}			
		});
		

		JTextField seed = new JTextField(10);
		seed.setText("" + square.getSeed());
		seed.addCaretListener(new CaretListener(){
			@Override
			public void caretUpdate(CaretEvent arg0) {
				System.out.println("???");
			}
			
		});
		seed.setPreferredSize( new Dimension( 10, 24 ) );
		
		JSlider smoothness = new JSlider(JSlider.HORIZONTAL,
                1, 200, (int) square.getSmoothness()*10);
		
		smoothness.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent event) {				
				square.setSmoothness(1.0 * smoothness.getValue()/10);
				square.generate(square.getSeed());

			}			
		});
		
		smoothness.setMajorTickSpacing(1);
		smoothness.setMinorTickSpacing(1);
		smoothness.setPaintTicks(true);
		//smoothness.setPaintLabels(true);
		
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
		this.add(save);
		this.add(seed);
	}
	 */

}
