import java.util.ArrayList;


public class HumanPlayer extends Player{

	HumanPlayer(Board b) {
		super(b);
		super.myUnits = new ArrayList<Unit>();
		super.opponentUnits = new ArrayList<Unit>();
		for(Unit u : b.units){
			if(u.owner.equals("human")){
				super.myUnits.add(u);
			}else{
				super.opponentUnits.add(u);
			}
		}
		
		
		
	}

	@Override
	void performTurn() {
		
		}

}
