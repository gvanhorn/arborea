package arborea;

public class Hex {
	boolean occupied;
	Unit unit;
	
	Hex(){
		occupied = false;
	}
	
	public void print(){
		System.out.printf("occupied: " + occupied);
	}

}
