package arborea;

public class Board {
	Hex[][] board;
	int offset;
	
	Board(){
		offset = 4;
		
		board = new Hex[9][9];
		for(int i = 0; i <10; i++){
			for(int j=0; j <10; j++){
				board[i][j] = new Hex();
			}
			
		}
		
	}
	
	public void print(){
		for(int i = 0; i <10; i++){
			for(int j=0; j <10; j++){
				System.out.println(i + "," + j + "occupied: ");
				board[i][j].print();
			}
		}
	}
}
