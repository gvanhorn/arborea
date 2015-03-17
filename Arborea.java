import javax.swing.JFrame;
import java.util.List;
import java.util.Queue;


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
		
		gameLoop(players, screen, board);
	}



	public static void gameLoop(Player[] players, Screen screen, Board board){
		boolean gameStillPlaying;
		System.out.println("Game started");
		Player currentPlayer;
		gameStillPlaying = true;

		// Give human turn
		currentPlayer = players[0];
		currentPlayer.setTurn(true);

		while (gameStillPlaying){
			//Human can make turns here using mouselisteners
			// currentPlayer.performTurn();
			try {
				Thread.sleep(50);                 //1000 milliseconds is one second.
			} catch(InterruptedException ex) {
    			Thread.currentThread().interrupt();
			}
			// When human turn ends, give cpu the turn
			// System.out.print("Checkvoor getturn"); 
			// System.out.println(currentPlayer.getTurn());

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

	public static void cpuTurn(CpuPlayer cpu, Screen screen, Board b){
		screen.clearPercentages();
		Tactic tactic = cpu.getTactic(b);
		Queue<Hex[]> moves = tactic.movelist.getOrderedHexList();
		Queue<String> types = tactic.movelist.getOrderedTypeList();
		int totalmoves = moves.size();
		System.out.println("Number of moves planned: " + totalmoves);
		int i;
		Hex from, to;
		
		for(i=0; i < totalmoves; i++){
			Hex[] move = moves.poll();
			String type = types.poll();
			
			//Get the relevant hexes from the board everybody is playing on.
			from = b.getHex(move[0].axialCoord.x, move[0].axialCoord.y);
			to = b.getHex(move[1].axialCoord.x, move[1].axialCoord.y);
			
			if(type.equals("move") && from != to){
				// Animate selection
				screen.setSelected(from);
				screen.repaint();
				
				try {
					Thread.sleep(200);
				} catch(InterruptedException ex) {
	    			Thread.currentThread().interrupt();
				}
				// Perform move
				from.getUnit().move(to);
			}

			if(type.equals("attack")){
				
				// Animate selection
				screen.setSelected(from);
				screen.repaint();
				
				try {
					Thread.sleep(200);              
				} catch(InterruptedException ex) {
	    			Thread.currentThread().interrupt();
				}
				// perform attack
				// from.getUnit().attack(to.getUnit());
				screen.cpuAttackHex(from, to);
			}
			
			screen.repaint();

			try {
				Thread.sleep(100);                 
			} catch(InterruptedException ex) {
    			Thread.currentThread().interrupt();
			}
			
		}
		screen.setSelected(null);
		screen.hexPanel.repaint();

	}

}
