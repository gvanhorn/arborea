

public class Hex {
	boolean occupied;
	Unit unit;
	
	Hex(){
		occupied = false;
	}
	
	public void print(){
		System.out.printf("occupied: " + occupied);
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

}
