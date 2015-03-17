import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


@SuppressWarnings("serial")
public class CpuPlayer extends Player{

	CpuPlayer(Board b) {
		super(b);
	}
	
	// GetTactic returns a tactic that has been developed on a deepcopy of the board.
	public Tactic getTactic(Board board){
		Board temp = getDeepCopy(board);
		Tactic tact = new AggroTactic(temp);
		tact.createTactic();
		return tact;
	}
	
	/* 
	 * getDeepCopy returns a (reference to, of course) deepcopy of the board.
	 * The code is copied from: http://javatechniques.com/blog/faster-deep-copies-of-java-objects/
	 */
	private Board getDeepCopy(Board orig){
		Object copy = null;
		
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
		 // long end = System.currentTimeMillis();
		// System.out.println("Deepcopy created in: " + (end-start));
		return (Board) copy;
	}

	// Reset turn sets the booleans for moved and attacked in all the units the cpu owns to false.
	@Override
	public void resetTurn() {
		for(Unit u : super.board.cpuUnits){
			u.moved = false;
			u.attacked = false;
		}
		
	}

	// Utility function for viewing a players units.
	@Override
	public void printUnits() {
		for(Unit u : super.board.cpuUnits){
			System.out.println(u.name);
		}
	}

}
