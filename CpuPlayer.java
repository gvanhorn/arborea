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
	}


	@Override
	void performTurn() {
		
	}

	public Tactic getTactic(Board board){
		Board temp = getDeepCopy(board);
		Tactic tact = new AggroTactic(temp);
		
		tact.createTactic();

		return tact;
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

	@Override
	public void resetTurn() {
		for(Unit u : super.board.cpuUnits){
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
