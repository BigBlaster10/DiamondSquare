package org.trompgames.isometric;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.trompgames.diamondsquare.DiamondSquare;
import org.trompgames.diamondsquare.Location;

public class IsoPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506940375901345375L;

	private IsoFrame frame;
	private boolean generate = false;
	private DiamondSquare square;
	
	private Location mapOffset = new Location(0,0);
	private int mapScale = 1;
	
	private ArrayList<TerrainColor> terrainColors = new ArrayList<>();
	
	public IsoPanel(DiamondSquare square, IsoFrame frame){
		this.square = square;
		this.frame = frame;
		
		MouseListener listener = new MouseListener(this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		//this.setSize(100, 100);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		terrainColors.add(new TerrainColor("Water", new Color(0, 59, 143), 0.3));
		terrainColors.add(new TerrainColor("Water", new Color(0, 84, 201), 0.4));
		
		terrainColors.add(new TerrainColor("Sand", new Color(150, 138, 0), 0.41));

		terrainColors.add(new TerrainColor("Ground", new Color(99,69,0), 0.48));
		terrainColors.add(new TerrainColor("Ground2", new Color(54,44,0), 0.53));

		terrainColors.add(new TerrainColor("Grass", new Color(31,153,0), 0.59));
		terrainColors.add(new TerrainColor("Grass", new Color(18,89,0), 0.63));

		
		terrainColors.add(new TerrainColor("Mountain", Color.gray, 0.8));
		terrainColors.add(new TerrainColor("Mountain2", Color.darkGray, 0.85));
		terrainColors.add(new TerrainColor("Snow", Color.WHITE, 1));
	}
	
	public Location getMapOffset(){
		return mapOffset;
	}
	
	public void setMapOffset(Location mapOffset){
		this.mapOffset = mapOffset;
	}
	
	public int getMapScale(){
		return mapScale;
	}
	
	public void setMapScale(int mapScale){
		this.mapScale = mapScale;
	}
	
	public void generateNewMap(){
		this.generate = true;
	}
	
	
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
        
        double[][] map = square.getMap();
        
        
        int displayed = 0;
        
       // BufferedImage image = new BufferedImage(square.getWidth(), square.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        for(int x = 0; x < map.length; x++){
        	if((int) (mapOffset.getX()) + x*mapScale < 0) continue;
        	if((int) (mapOffset.getX()) + x*mapScale > this.getSize().getWidth()) continue;

        	//System.out.println("Width: " + this.getSize().getWidth());
        	for(int y = 0; y < map[0].length; y++){
        		
        		if((int) (mapOffset.getY()) + y*mapScale < 0) continue;
            	if((int) (mapOffset.getY()) + y*mapScale > this.getSize().getHeight()) continue;
            	
        		//g2d.setColor(colorLerp(Color.white, Color.black, map[x][y]));
            	
            	
            	//image.setRGB(x, y, getColor(map[x][y]).getRGB());
        		g2d.setColor(getColor(map[x][y]));
        		g2d.fillRect((int) (mapOffset.getX()) + x*mapScale, (int) (mapOffset.getY()) + y*mapScale, mapScale, mapScale);
        		displayed++;
        	}        	
        }
        
        //g2d.drawImage(image, null, (int) (mapOffset.getX()), (int) (mapOffset.getY()));
        
        //System.out.println("Displayed: " + displayed);
	}
	
	
	
	public Color getColor(double height){
		int i = 0;
		for(TerrainColor color : terrainColors){
			if(height <= color.getHeight()){
				if(i == 0){
					return colorLerp(terrainColors.get(0).getColor(), color.getColor(), height);
				}
				return colorLerp(terrainColors.get(i-1).getColor(), color.getColor(), height);
				//return color.getColor();
			}
			i++;
		}
		return Color.black;
	}
	
	public Color colorLerp(Color c1, Color c2, double alpha){
		double r = lerp(c1.getRed(), c2.getRed(), alpha);
		double g = lerp(c1.getGreen(), c2.getGreen(), alpha);
		double b = lerp(c1.getBlue(), c2.getBlue(), alpha);
		return new Color((int) r, (int) g, (int) b); 
	}
	
	public double lerp(double x1, double x2, double alpha){
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;
		//return loc1.multiply(1-alpha).add(loc2.multiply(alpha));
		return (x1 * (1-alpha)) + (x2 * alpha);
	}
	
	public static class TerrainColor{
		
		private String name;
		private double height;
		private Color color;
		
		public TerrainColor(String name, Color color, double height){
			this.name = name;
			this.color = color;
			this.height = height;
		}
		
		public String getName(){
			return name;
		}
		
		public double getHeight(){
			return height;
		}
		
		public Color getColor(){
			return color;
		}
		
	}
	
	public void drawIso(Graphics2D g2d){
		double[][] map = square.getMap();
        
        
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
