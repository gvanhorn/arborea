import java.util.ArrayList;
import java.util.List;


public abstract class Player {
boolean turn;
List<Unit> units;

	Player(Board b){
		turn = false;
	}

	public void setTurn(boolean t){
		System.out.println("Player set to turn: " + t);
		turn = t;
		resetTurn();
	}

	public void resetTurn() {
		
		for(Unit u : units){
			u.moved = false;
			u.attacked = false;
		}
		
	}
	
	public void printUnits(){
		for(Unit u : units){
			System.out.println(u.name);
		}
	}
	
	public boolean getTurn(){
		return turn;
	}
	
	abstract void performTurn();
}
