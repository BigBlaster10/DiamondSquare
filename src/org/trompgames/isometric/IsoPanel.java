package org.trompgames.isometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.trompgames.diamondsquare.DiamondSquare;

public class IsoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506940375901345375L;

	private IsoFrame frame;
	private boolean generate = false;
	private DiamondSquare square;
	public IsoPanel(DiamondSquare square, IsoFrame frame){
		this.square = square;
		this.frame = frame;
		
		//this.setSize(100, 100);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
	}
	
	public void generateNewMap(){
		this.generate = true;
	}
	
	int i = 15;
	
	int step = 1;
	@Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        if(generate){
    		
    		square.generate();        	
    		
    		//double min = square.getMin();
    		
    		double[][] map = square.getMap();
    		
    		for(int y = 0; y < map[0].length; y++){
    			for(int x = 0; x < map.length; x++){
    				//map[x][y] = map[x][y] + min;
    			}
    		}
    		
    		File file = new File("test.txt");
    		
    	    square.writeToFile(file);
    		
    	    generate = false;
        }
        i++;
        
        
        
        double[][] map = square.getMap();
        
        int size = 15;
        
        for(int x = 0; x < map.length; x++){
        	//for(int y = 0; y < map[0].length; y++){
        	for(int y = map[0].length - 1; y >= 0; y--){
	
        		double c = map[x][y] * 5;
        		if(c > 255) c = 254;
        		if(c < 0) c = 0; 
        		g2d.setColor(new Color((int) c,(int) c, (int) 0));
        		
        		c = map[x][y];
        		
        		int tile_width = 1;

        		int tile_height = 1;

        		int screenX = (x * tile_width  / 2) + (y * tile_width  / 2);
        	    int screenY = (y * tile_height / 2) - (x * tile_height / 2);
        	    
        	    if(screenY >= this.getWidth()) continue;
        	    
        	    screenX += this.getWidth()/2 - square.getWidth()/2 - 30;
        	    screenY += this.getWidth()/2 + square.getHeight()/2;
        	    
        	    
        		//40
        	    
                //g2d.fillRect((int) (250 + screenX), (int) (500 + screenY/c * 20), tile_width, tile_height);
                g2d.fillRect((int) (screenX), (int) (1.0 * screenY/c * 2), tile_width, tile_height);

                //g2d.fillRect(800 + x*size, y*size, size, size);
                
                
        	}
        }
        
        
        

        
        
        
        
	}
	
	
	
	
	
	
}
