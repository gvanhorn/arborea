import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Queue;


public abstract class Tactic {

	protected Board board;
	MoveList movelist;
	int[][] distances;

	abstract MoveList getMoveList();
	abstract void createMoves();
	
	Tactic(Board b){
		board = b;
		board.updatePlayerUnits();
		
		
		distances = new int[board.cpuUnits.size()][board.humanUnits.size()];
		for(int i=0; i<board.cpuUnits.size();i++){
			for(int j=0; j<board.humanUnits.size();j++){
				distances[i][j] = board.cpuUnits.get(i).getPosition().distanceTo(board.humanUnits.get(j).getPosition());
			}
		}
	}
	
	public Unit getClosestEnemyUnit(Unit from){
		Unit to = null;
		int i = board.cpuUnits.indexOf(from);
		int lowestDistance = board.radius*2;
		int j=0;
		for(int distance: distances[i]){
			
			if(distance < lowestDistance){
				to = board.humanUnits.get(j);
				lowestDistance = distance;
			}
			j++;
		}
		
		
		return to;
	}
	
	public void executeMoves(Board b){
		Queue<Hex[]> moves = movelist.getOrderedHexList();
		Queue<String> types = movelist.getOrderedTypeList();
		int totalmoves = moves.size();
		//System.out.println("Number of moves planned: " + totalmoves);
		
		
		
		int i;
		for(i=0; i < totalmoves; i++){
			Hex[] move = moves.poll();
			String type = types.poll();
			
			
			//Get the relevant hexes from the board everybody is playing on.
			Hex from = b.getHex(move[0].axialCoord.x, move[0].axialCoord.y);
			Hex to = b.getHex(move[1].axialCoord.x, move[1].axialCoord.y);
			
			if(type.equals("move")){
				from.getUnit().move(to);
			}
			if(type.equals("attack")){
				from.getUnit().attack(to.getUnit());
				b.updatePlayerUnits();
			}
			
		}
		
		System.out.println("Number of moves done:" + i);
	}
	
	public void printDistances(){
		int i=0,j=0;
		for(Unit u : board.cpuUnits){
			for(Unit u2 : board.humanUnits){
				System.out.println("Distance from unit: " + u.toString() + " to unit: " + u2.toString() + "is: " + distances[i][j]);
				j++;
			}
			j=0;
			i++;
		}
	}
	
	public void print(){
		MoveList tmp = getDeepCopy(movelist);
		Queue<Hex[]> hexlist = tmp.getOrderedHexList();
		Queue<String> stringlist = tmp.getOrderedTypeList();
		int size = hexlist.size();
		System.out.println("TACTIC MOVE LENGTH " + size);
		
		for(int i=0; i<size;i++){
			Hex[] hexes = hexlist.remove();
			String s = stringlist.remove();
			System.out.println(s + " from: " + hexes[0].toString() + " to: " + hexes[1].toString());
		}
			
	}
	

	
	private MoveList getDeepCopy(MoveList orig){
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
		return (MoveList) copy;
	}
}
