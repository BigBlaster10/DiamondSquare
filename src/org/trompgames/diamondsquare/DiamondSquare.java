package org.trompgames.diamondsquare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Random;
import java.util.stream.DoubleStream;

import org.trompgames.isometric.IsoFrame;

public class DiamondSquare {

	
	//http://stackoverflow.com/questions/892811/drawing-isometric-game-worlds
	
	public static void main(String[] args) {
		
		Location loc1 = new Location(0, 0);
		Location loc2 = new Location(10, 10);
		
		Location lerped = Location.lerp(loc1, loc2, 0.5);
		//System.out.println("Lerp: " + lerped);
		DiamondSquare square = new DiamondSquare(8);
		
		System.out.println("Starting generation");
		square.generate();
		System.out.println("Finished generating");
		
		//System.out.println(square.exelTest());
		

		for(int i = 0; i < 20; i++){
			//System.out.println("Rand: " + (int) (1.0 * square.seedRandom()*255));
		}
		
		long time = System.currentTimeMillis();
		//System.out.println("Started Writing");
		
		//File file = new File("test.txt");
		
	    //square.writeToFile(file);
		
		
		//System.out.println("Done " + (System.currentTimeMillis()-time)/1000 + "s");
		
		//System.out.println(square.toString());
		
		for(int i = 0; i < 10; i++){
			//System.out.println("I: " + square.seedRandom());
		}
		
		
		new IsoFrame(1920, 540, square);
		
		
	}
	
	private double[][] map;
	private int width;
	private int height;
	private Random rand;
	private long mapSeed;
	
	private double smoothness = 1.75;
	private double heightRand = 0.33;
	
	private double max = Integer.MAX_VALUE;
	private double min = Integer.MIN_VALUE;
	
	public DiamondSquare(int size, double smoothness, double heightRand){
		this.width = (int) Math.pow(2, size) + 1;
		this.height = (int) Math.pow(2, size) + 1;
		this.smoothness = smoothness;
		this.heightRand = heightRand;
		rand = new Random();
		map = new double[width][height];
	}
	
	public DiamondSquare(int size){
		this.width = (int) Math.pow(2, size) + 1;
		this.height = (int) Math.pow(2, size) + 1;

		rand = new Random();
		map = new double[width][height];
	}
	

	public double getMin(){
		double min = Integer.MAX_VALUE;
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(map[x][y] < min) min = map[x][y];
			}
		}		
		return min;
	}
	

	
	OfDouble streamDouble;
	public void generate(){	
		randCalls = 0;
		this.setNeg();
		this.h = heightRand;
		
		mapSeed = (long) (Math.random()*5000);
		setNeg();
		
		rand.setSeed(mapSeed);
		
		DoubleStream stream = rand.doubles(width * height * 2);
		streamDouble = stream.parallel().iterator();
		
		map[0][0] = (seedRandom());
		map[0][height-1] =  (seedRandom());
		map[width-1][0] =  (seedRandom());
		map[width-1][height-1] = (seedRandom());
		
		
		for (int sideLength = width - 1; sideLength >= 2; sideLength /= 2){
			step(sideLength);			
		}
		
		clampTo01();
		
		
		double max = 0;
		double min = 1;
		
		for(int y = 0; y < map[0].length; y++){
			for(int x = 0; x < map.length; x++){
				if(map[x][y] < min) min = map[x][y];
				if(map[x][y] > max) max = map[x][y];
			}
		}
		
		//System.out.println("Max: " + max);
		//System.out.println("Min: " + min);	
		System.out.println("RandCalls: " + randCalls);
		System.out.println("Width: " + width);
	}
	
	public void generate(long seed){	
		this.setNeg();
		this.h = heightRand;
		
		mapSeed = seed;

		rand.setSeed(mapSeed);

		DoubleStream stream = rand.doubles(width * height * 2);
		streamDouble = stream.parallel().iterator();
		
		setNeg();
		map[0][0] = (seedRandom());
		map[0][height-1] = (seedRandom());
		map[width-1][0] =  (seedRandom());
		map[width-1][height-1] = (seedRandom());
		
		
		for (int sideLength = width - 1; sideLength >= 2; sideLength /= 2){
			step(sideLength);			
		}
		clampTo01();
	}
	
	public void clampTo01(){
		double max = 0;               
		double min = 1;
		
		for(int y = 0; y < map[0].length; y++){
			for(int x = 0; x < map.length; x++){
				if(map[x][y] < min) min = map[x][y];
				if(map[x][y] > max) max = map[x][y];
			}
		}
		for(int y = 0; y < map[0].length; y++){
			for(int x = 0; x < map.length; x++){
				map[x][y] = (map[x][y] - min)/(max-min);
			}
		}
		
	}
	
	public long getSeed(){
		return mapSeed;
	}
	
	double h = heightRand; // 75   35   hex:20?
	public double rand(double avg){
		return (avg + ((2.0 * this.seedRandom()*h) - h));
	}
	
	public double getSmoothness(){
		return this.smoothness;
	}
	
	public void setSmoothness(double smoothness){
		this.smoothness = smoothness;
	}
	
	private void step(int dist){		
		(new Thread(new TestRun(dist, this))).start();
			
		for(int x = 0; x < width; x += dist){
			for(int y = 0; y < height; y += dist){
				//System.out.println("I: " + i);
				
				Location p1;
				Location p2;
				Location p3;
				Location p4;
				
				p1 = new Location(x, y);
				p2 = new Location(x, y+dist);
				p3 = new Location(x+dist, y);
				p4 = new Location(x+dist, y+dist);
				if(p1.getX() >= width || p2.getX() >= width || p3.getX() >= width || p4.getX() >= width
						|| p1.getY() >= height || p2.getY() >= height || p3.getY() >= height || p4.getY() >= height){
					//System.out.println("broke");
					continue;
				}			
								
				Location mid = new Location(p1.getX() + p2.getX() + p3.getX() + p4.getX(), p1.getY() + p2.getY() + p3.getY() + p4.getY());
				mid = mid.multiply(0.25);				
				
				double avg = map[(int) p1.getX()][(int) p1.getY()] + map[(int) p2.getX()][(int) p2.getY()] + map[(int) p3.getX()][(int) p3.getY()] + map[(int) p4.getX()][(int) p4.getY()];
				avg /= 4;
				
				map[(int) mid.getX()][(int) mid.getY()] = rand(avg);		
				//if(map[(int) mid.getX()][(int) mid.getY()] == -1) map[(int) mid.getX()][(int) mid.getY()] = rand(avg);		

				

				p1 = new Location(mid.getX()-dist/2, mid.getY());
				p2 = new Location(mid.getX()+dist/2, mid.getY());
				p3 = new Location(mid.getX(), mid.getY() + dist/2);
				p4 = new Location(mid.getX(), mid.getY()-dist/2);
				
				map[(int) p1.getX()][(int) p1.getY()] = rand(getHeight(p1, 1.0 * dist/2));		
				map[(int) p2.getX()][(int) p2.getY()] = rand(getHeight(p2, 1.0 * dist/2));			
				map[(int) p3.getX()][(int) p3.getY()] = rand(getHeight(p3, 1.0 * dist/2));			
				map[(int) p4.getX()][(int) p4.getY()] = rand(getHeight(p4, 1.0 * dist/2));	
			}			
		}
		
		
		h /= smoothness;
	}
	
	
	public static class TestRun implements Runnable{

		private int dist;
		private DiamondSquare square;
		
		public TestRun(int dist, DiamondSquare square){
			this.dist = dist;
			this.square = square;
		}
		
			
		
		@Override
		public void run() {
			
			
			int width = square.getWidth();
			int height = square.getHeight();
			double[][] map = square.getMap();
			
			
			
		}
		
		
		
	}
	
	
	
	public double getHeight(Location loc, double dist){
		double totHeight = 0;
		int found = 0;
		dist = Math.round(dist);
		Location p1 = loc.add(dist, 0);
		Location p2 = loc.add(-dist, 0);
		Location p3 = loc.add(0, dist);
		Location p4 = loc.add(0, -dist);

		if(isInMap(p1) && map[(int) p1.getX()][(int) p1.getY()] != -1){
			totHeight += map[(int) p1.getX()][(int) p1.getY()];
			found++;
		}if(isInMap(p2) && map[(int) p2.getX()][(int) p2.getY()] != -1){
			totHeight += map[(int) p2.getX()][(int) p2.getY()];
			found++;
		}if(isInMap(p3) && map[(int) p3.getX()][(int) p3.getY()] != -1){
			totHeight += map[(int) p3.getX()][(int) p3.getY()];
			found++;
		}if(isInMap(p4) && map[(int) p4.getX()][(int) p4.getY()] != -1){
			totHeight += map[(int) p4.getX()][(int) p4.getY()];
			found++;
		}
		if(found == 0){
			System.out.println("000000000");
			return 0;
		}
		return totHeight / found;		
	}
	
	
	public void writeToFile(File file){
		PrintWriter out;
		try {
			out = new PrintWriter(file);			
			
			double[][] map = getMap();
			
			
			for(int y = 0; y < map[0].length; y++){
				String s = "";
				for(int x = 0; x < map.length; x++){
					s += map[x][y] + " ";
				}
				out.println(s);
			}		
			
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public double[][] getMap(){
		return map;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setHeightRand(double heightRand){
		this.heightRand = heightRand;
	}
	
	public double getHeightRand(){
		return heightRand;
	}

	int randCalls = 0;
	public double seedRandom(){
		randCalls++;
		//return rand.nextDouble();
		return streamDouble.nextDouble();
	}
	
	public String exelTest(){
		String s = "";
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				s += "" + x + " " + map[x][y] + " " + y + "\n";
			}
		}	
		return s;
	}
	

	
	public boolean isFull(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				if(map[x][y] == -1) return false;
			}
		}		
		return true;
	}
	
	public void setNeg(){
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				map[x][y] = -1;
			}
		}
	}
	
	//int h = 2;
	
	
	
	public boolean isInMap(Location loc){
		int x = (int) loc.getX();
		int y = (int) loc.getY();
		
		if(x < 0 || y < 0 || x >= width || y >= height) return false;
		return true;
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
	
	public String toString(int scale){
		String s = "";	
		for(int y = 0; y < height; y++){
			String last = "";
			for(int x = 0; x < width; x++){
				for(int i = 0; i < scale; i++)
					last += map[x][y] + " ";
			}
			for(int i = 0; i < scale; i++){
				s += last + "\n";
			}
		}		
		return s;		
	}
	
	
	
	
	
	
}
