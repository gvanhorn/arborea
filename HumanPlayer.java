

/*
 *  A subclass for the human player of player
 */
@SuppressWarnings("serial")
public class HumanPlayer extends Player{

	// Constructor that passes a board with which the player played.
	HumanPlayer(Board b) {
		super(b);		
	}

	// Resets the turn by setting moved and attacked in humanUnits on the board to false.
	@Override
	public void resetTurn() {
		for(Unit u : super.board.humanUnits){
			u.moved = false;
			u.attacked = false;
		}
		
	}

	// Utility function that prints the units owned by the player.
	@Override
	public void printUnits() {
		for(Unit u : super.board.humanUnits){
			System.out.println(u.name);
		}
		
	}

}
