import javax.swing.JFrame;



public class Arborea {
	
	public static void main(String[] args){		

		int[] screensize = {800, 800};
		JFrame frame = new JFrame("Main screen");
		frame.setSize(screensize[0],screensize[1]);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Board board = new Board(screensize);
		board.createHexGridGraphics();
		Player human = new HumanPlayer(board);
		Player cpu = new Player(board);
		
		Screen screen = new Screen(board, screensize, human);
		frame.setContentPane(screen);
		
		ScreenListener listener = new ScreenListener();
		frame.addWindowListener(listener);
		screen.addMouseListener(listener);
		
		frame.setVisible(true);
		
		
		gameLoop(human, cpu);
//		
	}

	public static void gameLoop(Player human, Player cpu){
		human.setTurn(true);
		System.out.println("Game started");
		
		while(true){
			//human player loop
			human.setTurn(true);
			while(human.turn){
				//System.out.println("Human turn entered");
			}
			System.out.println("Human turn ended");
			human.resetTurn();
			//cpu player loop			
			cpu.setTurn(true);
			while(cpu.turn){
				System.out.println("CPU turn entered");
				cpu.turn= false;
			}
			
		}
		
	}


}
