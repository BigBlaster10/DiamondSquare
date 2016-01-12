package org.trompgames.diamondsquare;

public class Location {

	private double x;
	private double y;
	
	public Location(double x, double y){
		this.x = x;
		this.y = y;		
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public Location add(Location loc){
		return new Location(loc.x  + x, loc.y + y);
	}
	
	public Location minus(Location loc){
		return new Location(x - loc.x, y - loc.y);
	}
	
	public Location add(double x, double y){
		return new Location(this.x + x, this.y + y);
	}
	
	public Location multiply(double i){
		return new Location(x * i, y * i);
	}
	
	
	public static Location lerp(Location loc1, Location loc2, double alpha){
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;
		return loc1.multiply(1-alpha).add(loc2.multiply(alpha));
	}
	
	@Override
	public String toString(){
		return "X: " + x + " Y: " + y;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
