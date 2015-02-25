

public class Arborea {
	
	public static void main(String[] args){
		Board board = new Board();
		
		//board.print();		
//		board.getHex(0,0).placeUnit(sword);
		
		//board.print();
		//board.getHex(0, 0).getUnit().print();
		Screen view = new Screen(board);
<<<<<<< HEAD
		ScreenListener listener = new ScreenListener();
		view.addWindowListener(listener);
		view.addMouseListener(listener);
=======
		view.addWindowListener(new ScreenListener());
>>>>>>> b43af689a26d9bd59d69f270c4cc3786918fc1df
//		
	}

}
