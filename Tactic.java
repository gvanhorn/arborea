import java.awt.Point;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;


public abstract class Tactic {

	protected Board board;
	MoveList movelist;
	int[][] distances;

	abstract MoveList getMoveList();
	//abstract void createMoves();
	abstract void createTactic();
	
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
	
	public List<Hex> aStar(Hex start, Hex goal){
		System.out.println("A-star planning path from : " + start.toString() + " to: " + goal.toString());
		
		List<Hex> closedList = new ArrayList<Hex>();
		List<Hex> openList = new ArrayList<Hex>();
		Map<Hex, Hex> came_from = new HashMap<Hex, Hex>();
		
		Comparator<Entry<Hex, Integer>> valueCompare = new Comparator<Entry<Hex, Integer>>() {
		    public int compare(Entry<Hex, Integer> entry1, Entry<Hex, Integer> entry2) {
		        return entry1.getValue().compareTo(entry2.getValue());
		    }
		};
		openList.add(start);
		
		Map<Hex, Integer> fscores = new HashMap<Hex, Integer>();
		Map<Hex, Integer> gscores = new HashMap<Hex, Integer>();
		
		gscores.put(start, 0);
		fscores.put(start, cost(start, goal));
		int currentPathLength, lowestFscore;
		Hex currentHex;
		
		while(!openList.isEmpty()){
			//currentHex = Collections.min(fscores.entrySet(), valueCompare).getKey();
			
			int score;
			int lowest = 99999;
			currentHex = start;
			for(Entry<Hex, Integer> e : fscores.entrySet()){
				score = e.getValue();
				if(openList.contains(e.getKey()) && score < lowest){
					currentHex = e.getKey();
					lowest = score;
				}
			}
			
			
			openList.remove(currentHex);
			closedList.add(currentHex);
			
//			
//			System.out.println("Starting point is: " + start.toString());
//			System.out.println("Target is: " + goal.toString());
//			System.out.println("CurrentHex is: " + currentHex.toString());
//			System.out.println("Fscores: " + fscores.toString());
//			System.out.println("Gscores: " + gscores.toString());
//			System.out.println("Came from: " + came_from.toString());
			
			if(currentHex.axialCoord == goal.axialCoord){
				return reconstructPath(came_from, goal);
			}
			
			
			
			for(Hex neighbour : currentHex.getUnOccupiedNeighbours()){	
				
				//System.out.println(neighbour.toString());
				if(!closedList.contains(neighbour)){
					currentPathLength = gscores.get(currentHex) + 1;
					if(!openList.contains(neighbour) || (currentPathLength < fscores.get(neighbour))){
						
						came_from.put(neighbour, currentHex);
						gscores.put(neighbour, currentPathLength);
						fscores.put(neighbour, gscores.get(neighbour) + cost(neighbour, goal));
						
						if(!openList.contains(neighbour)){
							openList.add(neighbour);
						}
					}
				}
				
				
				
				
				
				
			}
			
		}
		return null;
	}

	public List<Hex> reconstructPath(Map<Hex, Hex> came_from, Hex current) {
		List<Hex> totalPath = new ArrayList<Hex>();
		totalPath.add(current);
		while(came_from.containsKey(current)){
			current = came_from.get(current);
			totalPath.add(current);
		}
		return totalPath;
	}
	
	public int cost(Hex from, Hex to){
		return from.distanceTo(to);
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
	void createMoves() {
		// TODO Auto-generated method stub
		
	}
}
