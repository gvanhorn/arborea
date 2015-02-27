import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

/*
 * www.redblobgames.com/grids/hexagons/
 */


public class Board {
	Hex[][] board;
	int radius;
	int range;
	Hex selectedHex;
	Palette palette;
	int[] screensize;
	
	Board(int[] screensize){
		this.screensize = screensize;
		palette = new Palette();
		radius = 4;
		initBoard();
		
	}
	
	private void initBoard(){
		board = new Hex[2*radius+1][];
		int rows = radius*2 + 1;
		int counter = 1;
		int rowLength;
		int r, q;
		for(int i = 0; i < rows; i++){
			//Axial coordinate r stays the same the entire row
			r = i - radius;
			
			rowLength = radius + counter;
			//System.out.println(rowLength);
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
				//System.out.println("q and r are resp.: " + q + "," + r);
				//Create new hex with axialcoordinates
				board[i][j] = new Hex(q, r, palette.green);
			}
		}
		
		
		
		//Put references to neighbors in each hex
		int i;
		Point[] neighbourCoords;
		Hex[] neighbours;
		
		for(Hex[] row : board){
			for(Hex h: row){
				
				neighbourCoords = h.getAdjacent();
				neighbours = new Hex[6];
				i=0;
				
				for(Point p : neighbourCoords){
					
					try{
						neighbours[i] = this.getHex(p.x, p.y);
					}catch(Exception e){
						System.out.println(e.toString());}
					i++;
				}
				
				h.neighbours = neighbours;
			}
		}
		System.out.println(getHex(0,0).neighbours[0]);
	}
	
	public Hex[][] getHexArray(){
		return board;
	}
	
	//q and r are array indexes and returns the hex at that array index. 
	public Hex getHex(int q, int r){
		return board[r + radius][q + radius + Math.min(0, r)];
	}
	
	public void setSelected(Hex h){
		if(selectedHex != null){
			selectedHex.color = palette.green;
			reColorHexGroup(selectedHex.neighbours, palette.green);
		}
		if(h != null){
			selectedHex = h;
			selectedHex.color = palette.orange;	
			reColorHexGroup(h.neighbours, palette.lightOrange);
		}
		if(h == null){
			selectedHex = null;
		}
		
	}
	
	public void reColorHexGroup(Hex[] group, Color c){
		for(Hex h : group){
			if(h != null){
				h.color = c;
			}
		}
	}

	public void print(){
		for(Hex[] row : board){
			printRow(row);
		}
	}
	
	public void printRow(Hex[] row){
		for(Hex hex : row){
			System.out.printf(hex.toString() + ", ");
		}
		System.out.printf("\n");
	}
	
	public void createHexGridGraphics(){
		int screenWidth = screensize[0];
		int screenHeight = screensize[1];
		
		double hexSize = Math.floor((Math.min(screenWidth, screenHeight) - 20) / (board.length * 2));
		double hexWidth = 2 * hexSize;
		double hexHeight = Math.sqrt(3)/2 * hexWidth;
		double horizontalDistance = (double)hexWidth * 3/4 + 3;
		double verticalDistance = hexHeight + 3; 
		
		System.out.println(horizontalDistance);
		
		Point center = new Point(screenWidth/2, screenHeight/2);
		int u,v,x,y;
		
		for(Hex[] row : board){
			for(Hex hex: row){
				
				u = hex.axialCoord.x;
				v = hex.axialCoord.y;
				x = center.x + (int)Math.floor(horizontalDistance * u);
				y = center.y + (int)Math.floor(verticalDistance * (u*.5+v));

				Polygon p = createHexagon(x, y, hexSize);
							
				hex.setShape(p);
			}
		}
	}
	
	/*
	 * Creates a hexagon around the coordinates given with a certain size.
	 */
	public Polygon createHexagon(int x, int y, double size){
		Polygon hex = new Polygon();
		
		for(int i = 0; i < 6; i ++){
			
			int xCoord = (int)Math.round(x + size * Math.cos(i * 2 * Math.PI / 6));
			int yCoord = (int)Math.round(y +  size * Math.sin(i * 2 * Math.PI / 6));
			hex.addPoint(xCoord,yCoord); 
							
		}
		return hex;
		
	}

}
