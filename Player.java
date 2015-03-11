import java.util.ArrayList;
import java.util.List;


public abstract class Player implements java.io.Serializable{
boolean turn;
Board board;
List<Unit> myUnits;
List<Unit> opponentUnits;

	Player(Board b){
		turn = false;
		board = b;
	}

	public void setTurn(boolean t){
		System.out.println("Player set to turn: " + t);
		turn = t;
		resetTurn();
	}

	public void resetTurn() {
		
		for(Unit u : myUnits){
			u.moved = false;
			u.attacked = false;
		}
		
	}
	
	public void printUnits(){
		for(Unit u : myUnits){
			System.out.println(u.name);
		}
	}
	
	public boolean getTurn(){
		return turn;
	}
	
	abstract void performTurn();
}
