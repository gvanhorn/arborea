import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.WindowConstants;



public class Arborea {


	
	public static void main(String[] args){
		int[] screensize = {800, 800}; 
		Board board = new Board(screensize);
		board.createHexGridGraphics();
		
		Player human = new Player();
		Player cpu = new Player();
		
		JFrame frame = new JFrame("Main screen");
		frame.setSize(screensize[0],screensize[1]);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Screen screen = new Screen(board, screensize);
		
		frame.setContentPane(screen);
		

		ScreenListener listener = new ScreenListener();
		frame.addWindowListener(listener);
		screen.addMouseListener(listener);
		
		
		

//		
	}



}
