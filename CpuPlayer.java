import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class CpuPlayer extends Player{

	CpuPlayer(Board b) {
		super(b);
		super.myUnits = new ArrayList<Unit>();
		super.opponentUnits = new ArrayList<Unit>();
		for(Unit u : b.units){
			if(u.owner.equals("cpu")){
				super.myUnits.add(u);
			}else{
				super.opponentUnits.add(u);
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
		
		//Board temp = getDeepCopy(super.board);
		
		for(Unit u : super.myUnits){
			Hex[] hexes = u.getPosition().getUnOccupiedNeighbours();
			if(hexes.length > 0){
				index = rnd.nextInt(hexes.length);
				u.move(hexes[index]);
			}
		}
		
		/*
		 * Move phase:
		 * - important measures: distance to (closest) target, weaponSkillModifier
		 * - when maximizing wsm that distance probably increases
		 * - when minimizing distance to target, wsm probably decreases.
		 * 
		 * Conclusion:
		 * - 
		 */
		
		/*
		 * Attack phase:
		 * - focus attacks on minimal number of targets
		 * - focus on targets with low hp
		 */
		
	}
	
	private Board getDeepCopy(Board orig){
		Object copy = null;
		long start = System.currentTimeMillis();
		 try {
	            // Write the object out to a byte array
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            ObjectOutputStream out = new ObjectOutputStream(bos);
	            out.writeObject(orig);
	            out.flush();
	            out.close();

	            // Make an input stream from the byte array and read
	            // a copy of the object back in.
	            ObjectInputStream in = new ObjectInputStream(
	                new ByteArrayInputStream(bos.toByteArray()));
	            copy = in.readObject();
	        }
	        catch(IOException e) {
	            e.printStackTrace();
	        }
	        catch(ClassNotFoundException cnfe) {
	            cnfe.printStackTrace();
	        }
		 long end = System.currentTimeMillis();
		System.out.println("Deepcopy created in: " + (end-start));
		return (Board) copy;
	}

}