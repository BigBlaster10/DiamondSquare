package org.trompgames.isometric;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.trompgames.diamondsquare.DiamondSquare;

public class IsoFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2683761727813466357L;

	
	private IsoPanel isoPanel;
	private InputPanel inputPanel;
	private DiamondSquare square;
	
	public IsoFrame(int width, int height, DiamondSquare square){
		this.square = square;

		this.isoPanel = new IsoPanel(square, this);
		this.inputPanel = new InputPanel(this);
		
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		setLayout(new GridLayout(0,2));
		
		this.add(isoPanel);		
		this.add(inputPanel);
		
		
		this.setVisible(true);

		(new Thread(new Update(this))).start();
	}
	
	public DiamondSquare getDiamondSquare(){
		return square;
	}
	
	public IsoPanel getIsoPanel(){
		return isoPanel;
	}
	
	
	public static class Update implements Runnable{

		
		private static final double fps = 20;
		private static final double msBetween = (1/fps) * 1000;
		
		private IsoFrame frame;
		
		public Update(IsoFrame frame){
			this.frame = frame;
		}
		
		@Override
		public void run() {
			
			long lastTime = System.currentTimeMillis();
			while(true){
				if(!(System.currentTimeMillis() >= lastTime + msBetween)) continue;
				frame.repaint();
				lastTime = System.currentTimeMillis();			
			}
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
}
