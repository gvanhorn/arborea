import java.util.ArrayList;
import java.util.Random;


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
	void performTurn() {
		//Perform random move
		//int numberOfUnits = super.units.size();
		Random rnd = new Random();
		//int index = rnd.nextInt(numberOfUnits);
		int index;
		
		for(Unit u : super.units){
			Hex[] hexes = u.getPosition().getUnOccupiedNeighbours();
			index = rnd.nextInt(hexes.length);
			u.move(hexes[index]);
		}
	}

}
