import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Hex {
	Polygon shape;
	Point euclCoord;
	Point axialCoord;
	Hex[] neighbours;
	boolean occupied;
	Unit unit;
	Color color;
	
	
	Hex(){
		axialCoord = new Point();
		occupied = false;
	}
	
	Hex(int q, int r, Color c){
		axialCoord = new Point(q, r);
		euclCoord = new Point();
		occupied = false;
		color = c;
	}
		
	public void setEuclCoord(int centerX, int centerY){
		euclCoord.x = centerX;
		euclCoord.y = centerY;
	}
	
	public boolean setCoord(int q, int r){
		axialCoord = new Point(q, r);
		return true;
	}
	
	public Point getAxialCoord(){
		return axialCoord;
	}
	
	public boolean setShape(Polygon p){
		if(shape == null){
			shape = p;
			return true;
		}else{
			shape = p;
			return true;
		}
	}
		
	public boolean placeUnit(Unit newUnit){
		if(!occupied){
			unit = newUnit;
			occupied = true;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean removeUnit(){
		if(occupied){
			occupied = false;
			return true;
		}else{
			return false;
		}
		
	}
	
	public Unit getUnit(){
		return unit;
	}
	
	public Hex[] getNeighbours(){
		return neighbours;
	}
	

	
	public String toString(){
		if(occupied){
			return axialCoord.toString() + ": " + unit.name;
		}else{
			return "[r=" + axialCoord.x + ",q=" + axialCoord.y + "]: " + "empty";
		}
		
	}
	
	public void print(){
		System.out.printf("occupied: " + occupied);
	}
	


}
