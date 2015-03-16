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
		Player cpu = new CpuPlayer(board);
		board.addPlayers(human, cpu);
		
		Screen screen = new Screen(board, screensize);
		frame.setContentPane(screen);
		
		ScreenListener listener = new ScreenListener();
		frame.addWindowListener(listener);
		screen.addMouseListener(listener);
		screen.endTurnButton.addActionListener(listener);
		
		frame.setVisible(true);
		
		Player[] players = {human, cpu};
		
		gameLoop(players);
	}

	public static void gameLoop(Player[] players){
		boolean gameStillPlaying = true;
		System.out.println("Game started");
		Player currentPlayer;
		
		while(gameStillPlaying){
			
			currentPlayer = players[0];
			currentPlayer.setTurn(true);
			System.out.println("About to start human turn");
			currentPlayer.performTurn();
			currentPlayer.setTurn(false);
			
			currentPlayer = players[1];
			currentPlayer.setTurn(true);
			System.out.println("About to start cpu turn");
			currentPlayer.performTurn();
			currentPlayer.setTurn(false);
			
			gameStillPlaying = checkWinState(players);
		}

		
	}
	
	public static boolean checkWinState(Player[] players){
		return false;
	}


}
