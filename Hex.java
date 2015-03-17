import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Hex implements java.io.Serializable{
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

	public Point getEuclCoord(){
		return euclCoord;
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
	
	public int distanceTo(Hex h){
		int cubex1 = axialCoord.x;
		int cubez1 = axialCoord.y;
		int cubey1 = -cubex1 - cubez1;
		
		int cubex2 = h.axialCoord.x;
		int cubez2 = h.axialCoord.y;
		int cubey2 = - cubex2 - cubez2;
		
		return (Math.abs(cubex1 - cubex2) + Math.abs(cubey1 - cubey2) + Math.abs(cubez1 - cubez2)) / 2;
	}
	
	//return an array of neighbouring hexes for this hex that are not occupied.
	public Hex[] getUnOccupiedNeighbours(){
		List<Hex> unoccupied = new ArrayList<Hex>();
		for(Hex n : neighbours){
			if(n != null && !n.occupied){
				unoccupied.add(n);
			}
		}
		Hex[] array =  unoccupied.toArray(new Hex[unoccupied.size()]);
		return array;
	}
	
	//return an array of neighbouring hexes for h that are occupied.	
	public Hex[] getOccupiedNeighbours(){
		List<Hex> occupied = new ArrayList<Hex>();
		for(Hex n : neighbours){
			if(n != null && n.occupied){
				occupied.add(n);
			}
		}
		Hex[] array =  occupied.toArray(new Hex[occupied.size()]);
		return array;
	}
	
	public Hex[] getEnemyOccupiedNeighbours(){
		List<Hex> occupied = new ArrayList<Hex>();
		for(Hex n : neighbours){
			if(n != null && n.occupied && !n.getUnit().owner.equals(unit.owner)){
				occupied.add(n);
			}
		}
		Hex[] array =  occupied.toArray(new Hex[occupied.size()]);
		return array;
	}

	public boolean adjacentTo(Hex h){
		for(Hex n : neighbours){
			if(h == n){
				return true;
			}
		}
		return false;
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
