

public class Arborea {
	
	public static void main(String[] args){
		Board board = new Board();
		
		//board.print();		
//		board.getHex(0,0).placeUnit(sword);
		
		//board.print();
		//board.getHex(0, 0).getUnit().print();
		Screen view = new Screen(board);

		ScreenListener listener = new ScreenListener();
		view.addWindowListener(listener);
		view.addMouseListener(listener);

//		
	}

}
