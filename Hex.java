import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Hex implements java.io.Serializable{
	Polygon shape;
	Point euclCoord;
	Point axialCoord;
	Hex[] neighbours;
	boolean occupied;
	Unit unit;
	Color color;
	
	// Empty constructor
	Hex(){
		axialCoord = new Point();
		occupied = false;
	}
	
	// Construct a hex with a color
	Hex(int q, int r, Color c){
		axialCoord = new Point(q, r);
		euclCoord = new Point();
		occupied = false;
		color = c;
	}
		
	// Set the euclidean coordinates for this hexagon, (position on the screen).
	public void setEuclCoord(int centerX, int centerY){
		euclCoord.x = centerX;
		euclCoord.y = centerY;
	}

	// return the euclidean coordinate of this hexagon
	public Point getEuclCoord(){
		return euclCoord;
	}
	
	// set the (axial) coords of this hexagon 
	public boolean setAxialCoord(int q, int r){
		axialCoord = new Point(q, r);
		return true;
	}
	
	// get the (axial) coords of this hexagon
	public Point getAxialCoord(){
		return axialCoord;
	}
	
	// Set the shape for this hexagon
	public boolean setShape(Polygon p){
		if(shape == null){
			shape = p;
			return true;
		}else{
			shape = p;
			return true;
		}
	}
	
	// place a unit on this Hexagon, return true if it succeeded, false if already occupied
	public boolean placeUnit(Unit newUnit){
		if(!occupied){
			unit = newUnit;
			occupied = true;
			return true;
		}else{
			return false;
		}
	}
	
	// Remove a unit from this Hexagon by simply setting occupied to false.
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
	
	/*
	 *  Returns the distance from this hexagon to the specified hexagon. 
	 *  	- first converts the axial coordinates to cube coordinates
	 *  	- Then returns the euclidean distance within the cube divided by 2
	 */
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
	
	// Return an array of neighbouring hexes that are occupied by enemies.
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

	// Returns true when this Hex is adjacent to another.
	public boolean adjacentTo(Hex h){
		for(Hex n : neighbours){
			if(h == n){
				return true;
			}
		}
		return false;
	}
	
	// Returns a string object with axial coordinates and the unit occupying this hex.
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
