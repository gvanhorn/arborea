import java.util.ArrayList;
import java.util.List;

/*
 * A general class for a player
 */

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
	
	public boolean getTurn(){
		return turn;
	}
	
}
