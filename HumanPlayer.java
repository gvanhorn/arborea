import java.util.ArrayList;


public class HumanPlayer extends Player{

	HumanPlayer(Board b) {
		super(b);
		super.units = new ArrayList<Unit>();
		for(Unit u : b.units){
			if(u.owner.equals("human")){
				super.units.add(u);
			}
		}
		
	}

	@Override
	void performTurn() {
		
		while(super.turn){
			
		}
		
	}

}
