import java.awt.Point;



public class Hex {
	Point axialCoord;
	boolean occupied;
	Unit unit;
	
	Hex(){
		axialCoord = new Point();
		occupied = false;
	}
	
	Hex(int q, int r){
		axialCoord = new Point(q, r);
		occupied = false;
	}
	
	public boolean setCoord(int q, int r){
		axialCoord = new Point(q, r);
		return true;
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
