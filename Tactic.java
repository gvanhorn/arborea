import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public abstract class Tactic {
	
	protected Board board;
	MoveList movelist;
	int[][] distances;

	// Any subclass of Tactic must be able to create a tactic
	abstract void createTactic();
	
	// Retrieve the board for which a suitable tactic must be made
	Tactic(Board b){
		board = b;
		board.updatePlayerUnits();
		
		// Initialize an array that contains distances from all friendly units to all enemy units.
		distances = new int[board.cpuUnits.size()][board.humanUnits.size()];
		for(int i=0; i<board.cpuUnits.size();i++){
			for(int j=0; j<board.humanUnits.size();j++){
				distances[i][j] = board.cpuUnits.get(i).getPosition().distanceTo(board.humanUnits.get(j).getPosition());
			}
		}
	}
	
	// Returns the closest enemy unit.
	public Unit getClosestEnemyUnit(Unit from){
		Unit to = null;
		int lowestDistance = board.radius*2 + 1;
		
		int j=0;
		int i = board.cpuUnits.indexOf(from);
		for(int distance: distances[i]){
			if(distance < lowestDistance){
				to = board.humanUnits.get(j);
				lowestDistance = distance;
			}
			j++;
		}
		return to;
	}
	
	// A* algorithm for finding the shortest path to a goal hexagon from the start hexagon.
	public List<Hex> aStar(Hex start, Hex goal){
		
		// Initialize the closed and open lists
		List<Hex> closedList = new ArrayList<Hex>();
		List<Hex> openList = new ArrayList<Hex>();
		
		// Initialize the map that will keep track of the route
		Map<Hex, Hex> came_from = new HashMap<Hex, Hex>();
		
		// Initialize the Hashmaps for storing f and g scores. 
		// F-scores are the sum of heuristic and G-score 
		// G-scores that the distance traveled to the Hexagon from start
		Map<Hex, Integer> fscores = new HashMap<Hex, Integer>();
		Map<Hex, Integer> gscores = new HashMap<Hex, Integer>();
		
		// Add initial values to the created lists and maps
		openList.add(start);
		gscores.put(start, 0);
		fscores.put(start, cost(start, goal));
		int score, lowest;
		
		// initialize some variables.
		int currentPathLength;
		Hex currentHex = start;
		
		// While the openlist is not empty, expand the frontier
		while(!openList.isEmpty()){
			
			lowest = 99999;
			// Set the currentHex to be the node in openList with the lowest f-score
			for(Entry<Hex, Integer> e : fscores.entrySet()){
				score = e.getValue();
				if(openList.contains(e.getKey()) && score < lowest){
					currentHex = e.getKey();
					lowest = score;
				}
			}
			
			// remove it from the openlist and add it to closedList
			openList.remove(currentHex);
			closedList.add(currentHex);
			
			// Check if we reached the goal
			if(currentHex.axialCoord == goal.axialCoord){
				return reconstructPath(came_from, goal);
			}
			
			// Loop over all neighbours of the current hex
			for(Hex neighbour : currentHex.getUnOccupiedNeighbours()){	
				
				// If the current neighbour is in the closedList, go to the next neighbour
				if(!closedList.contains(neighbour)){
					
					// Set the path length to this neighbour
					currentPathLength = gscores.get(currentHex) + 1;
					if(!openList.contains(neighbour) || (currentPathLength < fscores.get(neighbour))){
						
						//Set the previous node for this hex, compute and add scores to respctive maps.
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

	//Reconstruct the path that was implicitly created by A* and return an ordered list.
	public List<Hex> reconstructPath(Map<Hex, Hex> came_from, Hex current) {
		List<Hex> totalPath = new ArrayList<Hex>();
		totalPath.add(current);
		while(came_from.containsKey(current)){
			current = came_from.get(current);
			totalPath.add(current);
		}
		return totalPath;
	}
	
	//Define cost for A*, left separate for possible extension
	public int cost(Hex from, Hex to){
		return from.distanceTo(to);
	}
	
	//Utility function for debugging, prints the distances between friendly units and all enemy units.
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
}
