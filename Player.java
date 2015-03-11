import java.util.ArrayList;
import java.util.List;


public class Player {
boolean turn;
List<Unit> units;

	Player(Board b){

	}

	public void setTurn(boolean t){
		turn = t;
	}

	public void resetTurn() {
		for(Unit u : units){
			u.moved = false;
			u.attacked = false;
		}
		
	}
}
