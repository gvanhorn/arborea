import java.awt.Point;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Board implements java.io.Serializable{
	Hex[][] board;
	List<Unit> units;
	int radius;
	int range;
	Hex selectedHex;
	Palette palette;
	int[] screensize;
	Player human, cpu;
	List<Unit> humanUnits;
	List<Unit> cpuUnits;
	
	//Constructor for the board, screensize is the size of the window
	Board(int[] screensize, String unitFile){
		this.screensize = screensize;
		palette = new Palette();
		radius = 4;
		initBoard(unitFile);	
	}
	
	//Initialize the board
	private void initBoard(String unitFile){
		board = new Hex[2*radius+1][];
		units = new ArrayList<Unit>();
		setupHexes();
		setupNeighbors();
		setupUnits(unitFile);
	}
	
	//Create the Hex objects and fill the board array
	private void setupHexes(){
		int rows = radius*2 + 1;
		int counter = 1;
		int rowLength;
		int r, q;
		
		//Make hexes to fill the board
		for(int i = 0; i < rows; i++){
			//Axial coordinate r stays the same the entire row
			r = i - radius;
			rowLength = radius + counter;
			
			//initialize row with correct length
			if(i <= radius-1){	
				board[i] = new Hex[rowLength];
				counter++;
			}else{
				counter--;
				board[i] = new Hex[rowLength];
			}
			
			//fill row with hexes and their axial coordinates
			for(int j = 0; j < rowLength; j ++){
			
				//calculate axial coordinate q from array indexes.
				q = -radius - Math.min(0, r) + j;

				//Create new hex with axialcoordinates
				board[i][j] = new Hex(q, r, palette.green);
			}
		}
	}
	
	//Setup an array (length 6) of neighbors for each hex, if there is no neighbor, put null. 
	private void setupNeighbors(){
		int i;
		Point[] neighbourCoords;
		Hex[] neighbours;
		//Put references to neighbors in each hex
		for(Hex[] row : board){
			for(Hex h: row){
				
				//Get the axial coordinates of the neighbors for the current hex
				neighbourCoords = getAdjacent(h);
				neighbours = new Hex[6];
				
				i=0;
				for(Point p : neighbourCoords){
					//If there is a neighbor, put it in the array, else leave it as null.
					try{
						neighbours[i] = this.getHex(p.x, p.y);
					}catch(Exception e){
						//System.out.println(e.toString());
					}
					i++;
				}
				
				//add the neighbor array to the hex
				h.neighbours = neighbours;
			}
		}
	}
	
	
	//Read the initialUnits file and place them on the board.
	private void setupUnits(String unitFile){
		//Set up the initial units as specified by the file initialUnits.txt
		try{
			File f = new File(unitFile);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String currentLine;
			String type, owner;
			int x, y;
			while((currentLine = br.readLine()) != null){
				String[] stuff = currentLine.split(", ");
				type = stuff[0];
				owner = stuff[1];
				x = Integer.parseInt(stuff[2]);
				y = Integer.parseInt(stuff[3]);
				Hex h = getHex(x,y);
				Unit u = null;
				
				
				switch (type){
					case "Goblin":
						u = new Goblin(owner);
						break;
					case "Swordsman":
						u = new Swordsman(owner);
						break;
					case "Orc":
						u = new Orc(owner);
						break;
					case "General":
						u = new General(owner);
						break;
					default:
						System.out.println("Did not recognize the type: "  + type + " when placing units on the board.");		
				}
				
				if(u != null){
					u.setPosition(h);
					h.placeUnit(u);
					units.add(u);
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		setWeaponSkillModifiers();

		// Create lists to easily keep track of human and cpu units, this to prevent looping over all hexes
		humanUnits = new ArrayList<Unit>();
		cpuUnits = new ArrayList<Unit>();
		
		// Add units to lists
		for(Unit u : this.units){
			if(u.owner.equals("cpu")){
				cpuUnits.add(u);
			}else{
				humanUnits.add(u);
			}
		}
	}
	
	public void addPlayers(Player h, Player c){
		human = h;
		cpu = c;
	}
	
	// Gets hexes adjacent to the provided hex, return a point array with the adjacent hex coordinates
	public Point[] getAdjacent(Hex h){
		int[][] directions = {{1, 0}, {1, -1}, {0, -1},{-1, 0},{-1, 1}, {0, 1}};
		Point[] adjacent = new Point[6];
		int i=0;
		for(int[] direction : directions){
			Point tmp = new Point(h.axialCoord.x + direction[0], h.axialCoord.y + direction[1]);
			adjacent[i] = tmp;
			i++;
		}
		
		return adjacent;
	}
	
	public void setWeaponSkillModifiers(){
		for(Unit u: units){
			int wsm = 0;
			Unit adj; 
			for(Hex h : u.getPosition().getNeighbours()){
				if(h != null){
					if(h.occupied){
						adj = h.getUnit();
						if(adj.owner.equals(u.owner)){
							if(adj instanceof General || adj instanceof Orc){
								wsm += 2;
							}else{
								wsm++;
							}
						}else{
							if(adj instanceof General || adj instanceof Orc){
								wsm -= 2;
							}else{
								wsm--;
							}
						}
					}
				}
			}
			u.updateModifier(wsm);
		}
	}
	
	//q and r are array indexes and returns the hex at that array index. 
	public Hex getHex(int q, int r){
		return board[r + radius][q + radius + Math.min(0, r)];
	}

	
	//Prints the board to console
	public void print(){
		for(Hex[] row : board){
			printRow(row);
		}
	}
	
	//prints a row to console
	public void printRow(Hex[] row){
		for(Hex hex : row){
			System.out.printf(hex.toString() + ", ");
		}
		System.out.printf("\n");
	}
	
	//Create the initial graphics for the board
	public void createHexGridGraphics(){
		int screenWidth = screensize[0];
		int screenHeight = screensize[1];
		
		//calculate the size of the hex according to the size of the window, 
		double hexRadius = Math.floor((Math.min(screenWidth, screenHeight) - 20) / (board.length * 2));
		double hexWidth = 2 * hexRadius;
		double hexHeight = Math.sqrt(3)/2 * hexWidth;
		double horizontalDistance = (double)hexWidth * 3/4 + 3;
		double verticalDistance = hexHeight + 3; 
		
		Point center = new Point(screenWidth/2, screenHeight/2);
		int u,v,x,y;
		
		//Create a hexagon shape for each hexagon.
		for(Hex[] row : board){
			for(Hex hex: row){
				
				u = hex.axialCoord.x;
				v = hex.axialCoord.y;
				x = center.x + (int)Math.floor(horizontalDistance * u);
				y = center.y + (int)Math.floor(verticalDistance * (u*.5+v));
				
				Polygon p = createHexagon(x, y, hexRadius);
				hex.setEuclCoord(x, y);			
				hex.setShape(p);
			}
		}
	}
	
	//Creates a hexagon around the coordinates given with a certain size.
	public Polygon createHexagon(int x, int y, double size){
		Polygon hex = new Polygon();
		
		for(int i = 0; i < 6; i ++){
			
			int xCoord = (int)Math.round(x + size * Math.cos(i * 2 * Math.PI / 6));
			int yCoord = (int)Math.round(y +  size * Math.sin(i * 2 * Math.PI / 6));
			hex.addPoint(xCoord,yCoord); 
							
		}
		return hex;
		
	}

	public Hex getSelectedHex() {
		return selectedHex;
	}


	// Updates unit lists
	public void updatePlayerUnits() {
		humanUnits = new ArrayList<Unit>();
		cpuUnits = new ArrayList<Unit>();
		units = new ArrayList<Unit>();
		
		for(Hex[] row : board){
			for(Hex h : row){
				if(h.occupied){
					if(h.getUnit().owner.equals("cpu")){
						cpuUnits.add(h.getUnit());
					}else{
						humanUnits.add(h.getUnit());
					}
					units.add(h.getUnit());
				}
			}
		}
	}

	// Check if one of the players has no more units left
	public boolean victory(){
		if (humanUnits.size() == 0 || cpuUnits.size() == 0){
			return true;
		} else {
			return false;
		}
	}

	// Removes units with 0 hitpoints from the board and lists
	public void removeDead(Unit u) {
		if(u.hitpoints == 0){
			if (u.owner.equals("human")){
				humanUnits.remove(u);
			} else {
				cpuUnits.remove(u);
			}
			u.getPosition().removeUnit(); 
		}
	}
	

}
