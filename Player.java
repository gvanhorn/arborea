import java.util.ArrayList;
import java.util.List;


public abstract class Player implements java.io.Serializable{
boolean turn;
Board board;


	Player(Board b){
		turn = false;
		board = b;
	}

	public void setTurn(boolean t){
		System.out.println("Player set to turn: " + t);
		turn = t;
		resetTurn();
	}

	public abstract void resetTurn();
	
	public abstract void printUnits();
	
	abstract void performTurn();
	
	public boolean getTurn(){
		return turn;
	}
	
	
}
