

public class Arborea {

	Board board;
	Player human;
	Player cpu;
	
	public static void main(String[] args){
		
		Arborea game = new Arborea();
		
		Palette palette = new Palette();
		Screen view = new Screen(game.board);

		ScreenListener listener = new ScreenListener();
		view.addWindowListener(listener);
		view.addMouseListener(listener);

//		
	}
	
	Arborea(){
		board = new Board();
		human = new Player();
		cpu = new Player();
	}


}
