package org.trompgames.isometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.trompgames.diamondsquare.DiamondSquare;

public class IsoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506940375901345375L;

	private boolean click = false;
	private DiamondSquare square;
	public IsoPanel(DiamondSquare square){
		this.square = square;
	
		this.addMouseListener(new MouseAdapter() {
		     @Override
		     public void mousePressed(MouseEvent e) {
		        click = true;
		     }
		  });
	}
	
	
	int i = 15;
	
	int step = 1;
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if(click){
    		//square = new DiamondSquare(4, (int) (Math.random() * 20000));
    		//square.generate();
    		//i = 0;
        	square = new DiamondSquare(10, (int) (Math.random() * 20000));
    		square.generate();        	
    		//square.step(step);
        	//step++;
        	click = false;
        }
        i++;
        
        
        
        int[][] map = square.getMap();
        
        int size = 15;
        
        for(int x = 0; x < map.length; x++){
        	//for(int y = 0; y < map[0].length; y++){
        	for(int y = map[0].length - 1; y >= 0; y--){
	
        		double c = map[x][y];
        		if(c > 255) c = 254;
        		if(c < 0) c = 0; 
        		g2d.setColor(new Color((int) c,(int) c, (int) 0));

        		int tile_width = 1;
        		int tile_height = 1;
        		
        		int screenX = (x * tile_width  / 2) + (y * tile_width  / 2);
        	    int screenY = (y * tile_height / 2) - (x * tile_height / 2);
        		
                g2d.fillRect((int) (200 + screenX), (int) (400 + screenY/c*80), tile_height, tile_height);

                //g2d.fillRect(800 + x*size, y*size, size, size);
                
                
        	}
        }
        
        
        

        
        
        
        
	}
	
	
	
	
	
	
}
