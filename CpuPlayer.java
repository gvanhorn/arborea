import java.util.ArrayList;


public class CpuPlayer extends Player{

	CpuPlayer(Board b) {
		super(b);
		super.units = new ArrayList<Unit>();
		for(Unit u : b.units){
			if(u.owner.equals("cpu")){
				super.units.add(u);
			}
		}
	}

	@Override
	void perFormTurn() {
		
		
	}

}
