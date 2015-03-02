import javax.swing.JFrame;



public class Arborea {
	
	public static void main(String[] args){		
		
		Unit[] units = {new Goblin("human"), new Swordsman("cpu")};
		
		Player human = new Player();
		Player cpu = new Player();
		
		int[] screensize = {800, 800};
		JFrame frame = new JFrame("Main screen");
		frame.setSize(screensize[0],screensize[1]);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Board board = new Board(screensize, units);
		board.createHexGridGraphics();
		
		Screen screen = new Screen(board, screensize);
		frame.setContentPane(screen);
		
		ScreenListener listener = new ScreenListener();
		frame.addWindowListener(listener);
		screen.addMouseListener(listener);
		
		frame.setVisible(true);
		
//		
	}



}
