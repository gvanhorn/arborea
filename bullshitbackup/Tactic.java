import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Queue;


public abstract class Tactic {

	private Board board;
	MoveList movelist;
	int[][] distances;
	List<Unit> myUnits;
	List<Unit> opponentUnits;
	
	Tactic(Board b){
		board = b;
		
		Player cpu = b.cpu;
		myUnits = cpu.getMyUnits();
		opponentUnits = cpu.getOpponentUnits();
		distances = new int[myUnits.size()][opponentUnits.size()];
		
		for(int i=0; i<myUnits.size();i++){
			for(int j=0; j<opponentUnits.size();j++){
				distances[i][j] = myUnits.get(i).getPosition().distanceTo(opponentUnits.get(j).getPosition());
			}
		}
	}
	
	public Unit getClosestEnemyUnit(Unit from){
		Unit to = null;
		int i = myUnits.indexOf(from);
		int lowestDistance = board.radius*2;
		int j=0;
		for(int distance: distances[i]){
			
			if(distance < lowestDistance){
				to = opponentUnits.get(j);
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
		System.out.println("Number of moves planned: " + totalmoves);
		int i;
		for(i=0; i < totalmoves; i++){
			Hex[] move = moves.poll();
			String type = types.poll();
			Point fromCoord = new Point(move[0].axialCoord.x, move[0].axialCoord.y);
			Point toCoord = new Point(move[1].axialCoord.x, move[1].axialCoord.y);
			
			if(type.equals("move")){
				Unit u = b.getHex(fromCoord.x, fromCoord.y).getUnit();
				u.move(b.getHex(toCoord.x, toCoord.y));
			}
			
		}
		
		System.out.println("Number of moves done:" + i);
	}
	
	public void printDistances(){
		int i=0,j=0;
		for(Unit u : myUnits){
			for(Unit u2 : opponentUnits){
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
		
		for(int i=0; i<hexlist.size();i++){
			Hex[] hexes = hexlist.remove();
			System.out.println(stringlist.remove() + " from: " + hexes[0].toString() + " to: " + hexes[1].toString());
		}
			
	}
	
	abstract MoveList getMoveList();
	abstract void createTactic();
	
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
