package org.trompgames.diamondsquare;

import java.util.Random;

public class DiamondSquare {

	
	//http://stackoverflow.com/questions/892811/drawing-isometric-game-worlds
	
	public static void main(String[] args) {
		
		Location loc1 = new Location(0, 0);
		Location loc2 = new Location(10, 10);
		
		Location lerped = Location.lerp(loc1, loc2, 0.5);
		//System.out.println("Lerp: " + lerped);
		DiamondSquare square = new DiamondSquare(4, 1);
		square.generate();
		//System.out.println(square.toString());
		
		for(int i = 0; i < 10; i++){
			System.out.println("I: " + square.seedRandom());
		}
		
	}
	
	private int[][] map;
	private int size;
	private int width;
	private int height;
	private int seed;
	private Random rand;
	
	public DiamondSquare(int size, int seed){
		this.width = (int) Math.pow(2, size) + 1;
		this.height = (int) Math.pow(2, size) + 1;
		this.size = size;
		this.seed = seed;
		rand = new Random(seed);
		map = new int[width][height];		
		map[0][0] = 1;
		map[0][height-1] = 1;
		map[width-1][0] = 1;
		map[width-1][height-1] = 1;
	}
	
	public double seedRandom(){
		return rand.nextInt();
	}
	
	public void generate(){
		int step = 1;
		int h = 0;
		
		for(int i = 1; i <= 6; i++){
			step(i,h+i);
		}		
	}
	
	
	
	
	public void step(int step, int h){		
		Location minLoc = new Location(0,0);
		Location maxLoc = new Location(1.0 * ((width-1)/step), 1.0 * ((height-1)/step));
		
		Location p1 = minLoc;
		Location p2 = new Location(0, maxLoc.getY());
		Location p3 = new Location(maxLoc.getX(), 0);
		Location p4 = maxLoc;		
		
		int xDist = (int) (maxLoc.getX() - minLoc.getX());
		int yDist = (int) (maxLoc.getY() - minLoc.getY());
		
		for(int x = 0; x <= width; x += maxLoc.getX()){
			for(int y = 0; y <= height; y += maxLoc.getY()){
				p1 = new Location(x, y);
				p2 = new Location(x, y+yDist);
				p3 = new Location(x+xDist, y);
				p4 = new Location(x+xDist, y+yDist);
				if(p1.getX() >= width || p2.getX() >= width || p3.getX() >= width || p4.getX() >= width
						|| p1.getY() >= height || p2.getY() >= height || p3.getY() >= height || p4.getY() >= height){
				
					continue;
				}			
								
				Location mid = new Location(p1.getX() + p2.getX() + p3.getX() + p4.getX(), p1.getY() + p2.getY() + p3.getY() + p4.getY());
				mid = mid.multiply(0.25);				
				map[(int) mid.getX()][(int) mid.getY()] = h;			
				
				p1 = new Location(mid.getX()-xDist/2, mid.getY());
				p2 = new Location(mid.getX()+xDist/2, mid.getY());
				p3 = new Location(mid.getX(), mid.getY()+yDist/2);
				p4 = new Location(mid.getX(), mid.getY()-yDist/2);
				
				//if(!(p1.getX() >= width || p1.getX() < 0 || p1.getY() >= height || p1.getY() < 0)) map[(int) p1.getX()][(int) p1.getY()] = h;			
				//if(!(p2.getX() >= width || p2.getX() < 0 || p2.getY() >= height || p2.getY() < 0)) map[(int) p2.getX()][(int) p2.getY()] = h;			
				//if(!(p3.getX() >= width || p3.getX() < 0 || p3.getY() >= height || p3.getY() < 0)) map[(int) p3.getX()][(int) p3.getY()] = h;			
				//if(!(p4.getX() >= width || p4.getX() < 0 || p4.getY() >= height || p4.getY() < 0)) map[(int) p4.getX()][(int) p4.getY()] = h;			

				if(map[(int) p1.getX()][(int) p1.getY()] == 0) map[(int) p1.getX()][(int) p1.getY()] = h;			
				if(map[(int) p2.getX()][(int) p2.getY()] == 0) map[(int) p2.getX()][(int) p2.getY()] = h;			
				if(map[(int) p3.getX()][(int) p3.getY()] == 0) map[(int) p3.getX()][(int) p3.getY()] = h;			
				if(map[(int) p4.getX()][(int) p4.getY()] == 0) map[(int) p4.getX()][(int) p4.getY()] = h;			

				
				
			}			
		}	
	}
	
	
	
	@Override
	public String toString(){
		String s = "";
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				s += map[x][y] + " ";
			}
			s += "\n";
		}		
		return s;		
	}
	
	
	
	
	
	
	
	
}
