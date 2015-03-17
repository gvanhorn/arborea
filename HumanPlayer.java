import java.util.ArrayList;


public class HumanPlayer extends Player{

	HumanPlayer(Board b) {
		super(b);		
	}

	@Override
	public void resetTurn() {
		for(Unit u : super.board.humanUnits){
			u.moved = false;
			u.attacked = false;
		}
		
	}

	@Override
	public void printUnits() {
		for(Unit u : super.board.cpuUnits){
			System.out.println(u.name);
		}
		
	}

}
