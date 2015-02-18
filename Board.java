public class Board {
	Hex[][] board;
	int offset;
	
	Board(){
		offset = 4;
		
		board = new Hex[9][9];
		for(int i = 0; i <9; i++){
			for(int j=0; j <9; j++){
				board[i][j] = new Hex();
			}
			
		}
		
	}
	
	public Hex getHex(int i, int j){
		return board[i][j];
	}
	

	
	public void print(){
		for(int i = 0; i <9; i++){
			for(int j=0; j <9; j++){
				System.out.println(i + "," + j + " ");
				board[i][j].print();
				System.out.println();
			}
		}
	}
	

}
