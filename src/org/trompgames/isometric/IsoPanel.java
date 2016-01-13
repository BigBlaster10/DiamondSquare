package org.trompgames.isometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.trompgames.diamondsquare.DiamondSquare;

public class IsoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506940375901345375L;

	private DiamondSquare square;
	public IsoPanel(DiamondSquare square){
		this.square = square;
	}
	
	
	int i = 0;
	
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if(i % 20 == 0){
    		square = new DiamondSquare(3, (int) (Math.random() * 20));
    		square.generate();
    		i = 0;
        }
        i++;
        
        
        int[][] map = square.getMap();
        
        int size = 48;
        
        for(int x = 0; x < map.length; x++){
        	for(int y = 0; y < map[0].length; y++){
        		
        		double c = map[x][y];
        		if(c > 255) c = 254;
        		if(c < 0) c = 0; 
        		g2d.setColor(new Color(0,(int) c, (int) c));

                g2d.fillRect(x*size, y*size, size, size);

        	}
        }
        
        
        

        
        
        
        
	}
	
	
	
	
	
	
}
