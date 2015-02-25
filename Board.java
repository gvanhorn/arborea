/*
 * www.redblobgames.com/grids/hexagons/
 */


public class Board {
	Hex[][] board;
	int radius;
	int range;
	
	Board(){
		radius = 4;
		initBoard();
	}
	
	private void initBoard(){
		board = new Hex[2*radius+1][];
		int counter = 1;
		int rowLength;
		int r, q;
		for(int i = 0; i <9; i++){
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
				board[i][j] = new Hex(q, r);
			}	
		}
	}
	
	
	
	public Hex getHex(int q, int r){
		return board[r][q + radius + Math.min(0, r)];
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

}
