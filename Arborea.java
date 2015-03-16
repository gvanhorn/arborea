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
		
		gameLoop(players, screen);
	}



	public static void gameLoop(Player[] players, Screen screen){
		boolean gameStillPlaying;
		System.out.println("Game started");
		Player currentPlayer;
		gameStillPlaying = true;

		// Give human turn
		currentPlayer = players[0];
		currentPlayer.setTurn(true);

		while (gameStillPlaying){
			//Human can make turns here using mouselisteners
			currentPlayer.performTurn();
			// When human turn ends, give cpu the turn
			System.out.print("Checkvoor getturn"); 
			// System.out.println(currentPlayer.getTurn());

			if (currentPlayer.getTurn() == false){
				System.out.println("IN DIE STATEMENT");

				// Check for win state
				if (Victory(players)){
					gameStillPlaying = false;
				} else {
					// Give cpu the turn
					currentPlayer = players[1];
					currentPlayer.setTurn(true);
					
					// Let CPU perform moves
					currentPlayer.performTurn();
					screen.repaint();
				}

				if (Victory(players)){
					gameStillPlaying = false;
				} else { 
					// Give human turn
					currentPlayer = players[0];
					currentPlayer.setTurn(true);	
				}
			}
		}
	}


	// public static void gameLoop(Player[] players, Screen screen){
	// 	boolean gameStillPlaying = true;
	// 	System.out.println("Game started");
	// 	Player currentPlayer;

	// 	currentPlayer = players[0];
	// 	currentPlayer.setTurn(true);
		
	// 	while(gameStillPlaying){
	// 		// Player is allowed to make moves
	// 		currentPlayer.performTurn();
			
	// 		if (!currentPlayer.getTurn()){
				
	// 			// Reset player turn
	// 			currentPlayer.resetTurn();
				
	// 			// Give turn to CPU
	// 			currentPlayer = players[1];
	// 			currentPlayer.setTurn(true);
	// 			System.out.println("Starting cpu turn");
	// 			currentPlayer.performTurn();
	// 			screen.repaint();

	// 			// Check for winstate
	// 			if (!checkWinState(players)){
	// 				gameStillPlaying = true;

	// 				// Stop CPU turn
	// 				currentPlayer.setTurn(false);
	// 				currentPlayer.resetTurn();	
 
	// 				System.out.print("IGAME STILLS PLAYING ");
	// 				System.out.println(!gameStillPlaying);
					
	// 				// Start player turn
	// 				currentPlayer = players[0];
	// 				currentPlayer.setTurn(true);
	// 			}
	// 		} 
	// 	}

		
	// }
	
	public static boolean Victory(Player[] players){
		return false;
	}


}
