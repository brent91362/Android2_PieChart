package com.example.balanceteampie;

public class ColorCustom {
	private int red = 0;
	private int blue = 0;
	private int green = 0;
	private int count = 1;
	
	public int getR(){
		return (red / count);
	}
	public int GetG(){
		return (green / count);
	}
	
	public int GetB(){
		return (blue / count);
	}
	
	public int getCount(){
		return count;
	}
	
	public int getSumRGB(){
		return this.red + this.green +this.blue;
	}
	
	public void setR(int r){
		this.red = r;
	}
	
	public void setG(int g){
		this.green = g;
	}
	
	public void setB(int b){
		this.blue = b;
	}
	
	public void setRGB(int r, int g, int b){
		this.red = r;
		this.green = g;
		this.blue = b;
	}
	
	public void addRGB(int r, int g, int b){
		this.red = r + this.red;
		this.green = g + this.green;
		this.blue = b+ this.blue;
		count++;
	}
}