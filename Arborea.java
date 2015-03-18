import javax.swing.JFrame;
import java.util.Queue;
import javax.sound.sampled.*;
import java.io.*;



public class Arborea {
	
	public static void main(String[] args){
		String unitFile = "initialUnits";
		if(args.length > 0){
			unitFile = args[0];
		}
		
		
		// Set screen size
		int[] screensize = {800, 800};

		// Initialize Board
		Board board = new Board(screensize, unitFile);
		board.createHexGridGraphics();

		// Create Players and add to board
		Player human = new HumanPlayer(board);
		Player cpu = new CpuPlayer(board);
		Player[] players = {human, cpu};
		board.addPlayers(human, cpu);

		// Initialize screen
		JFrame frame = new JFrame("Main screen");
		frame.setSize(screensize[0],screensize[1]);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Screen screen = new Screen(board, screensize);
		frame.setContentPane(screen);
		
		// Initialize screen listeners
		ScreenListener listener = new ScreenListener();
		frame.addWindowListener(listener);
		screen.addMouseListener(listener);
		screen.endTurnButton.addActionListener(listener);
		frame.setVisible(true);

		// Start song
		// String audioFile = "/Users/Jeroen/arborea/sounds/Mountains.wav";
		// Sound sound = new Sound(audioFile);
		// Sound player = new Sound();
		// player.playSoundStream(player.mainLoop);
		// Start game loop
		gameLoop(players, screen, board);
	}

	public static void gameLoop(Player[] players, Screen screen, Board board){

		// Keeps game running
		boolean gameStillPlaying;
		gameStillPlaying = true;

		// Keeps track of turns
		Player currentPlayer;

		// Start game with human
		currentPlayer = players[0];
		currentPlayer.setTurn(true);

		// Main loop
		while (gameStillPlaying){

			// Human can make turns here using mouselisteners
			// Sleep to reduce processor activity
			sleep(50);


			// When human turn ends, give cpu the turn
			if (currentPlayer.getTurn() == false){

				// Check for win state
				if (board.victory()){
					gameStillPlaying = false;
					break;
				} else {
					// Give cpu the turn
					currentPlayer = players[1];
					currentPlayer.setTurn(true);
					
					// Let CPU perform moves
					cpuTurn((CpuPlayer) currentPlayer, screen, board);
				}

				// Check for win state after cpu turn
				if (board.victory()){
					gameStillPlaying = false;
					break;
				} else { 
					// Give human turn
					currentPlayer = players[0];
					currentPlayer.setTurn(true);	
				}
			}
		}
	}
	
	/* cpuTurn gets a tactic for the current board. The tactic is returned in two lists, 
	 * a move list, and type list. Moves contains which hex should perform an action on which hex, 
	 * types specifies wether this action is a move or an attack.
	 * These moves are then performed, with a pause in between to create an animation of the moves.
	 */

	public static void cpuTurn(CpuPlayer cpu, Screen screen, Board b){
		screen.clearPercentages();

		// Create the tactic
		Tactic tactic = cpu.getTactic(b);
		
		// Retreives to actions from the tactic and stores them in queues
		Queue<Hex[]> moves = tactic.movelist.getOrderedHexList();
		Queue<String> types = tactic.movelist.getOrderedTypeList();

		// Total amount of actions to make
		int totalmoves = moves.size();

		Hex from, to;
		
		for (int i=0; i < totalmoves; i++) {
	
			// Retrieve move from queue
			Hex[] move = moves.poll();
			String type = types.poll();
			
			// Get the relevant hexes from the board
			from = b.getHex(move[0].axialCoord.x, move[0].axialCoord.y);
			to = b.getHex(move[1].axialCoord.x, move[1].axialCoord.y);
			
			// Create visuals for action, and perform action
			if(type.equals("move") && from != to){
				screen.setSelected(from);
				screen.repaint();
				sleep(333);

				// Perform move
				from.getUnit().move(to);
			}

			if(type.equals("attack")){
				screen.setSelected(from);
				screen.repaint();
				sleep(333);

				// perform attack
				screen.cpuAttackHex(from, to);
			}
			screen.repaint();
			sleep(333);
			if (b.victory()){
				break;
			}

		}
		screen.setSelected(null);
		screen.hexPanel.repaint();
	}

	// Pauses execution
	public static void sleep(int milliseconds){
		try {
			Thread.sleep(milliseconds);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

}
