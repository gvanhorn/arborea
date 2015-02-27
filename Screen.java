import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JPanel{
	int screenWidth, screenHeight;
	double hexSize;
	Board board;
	
	
	Screen(Board providedBoard, int[] screensize){
		board = providedBoard;
		
		this.setDoubleBuffered(true);		
		this.setBackground(Color.WHITE);

	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for(Hex[] row : board.board){
			for(Hex hex : row){
				hex.paint(g);
			}
		}
	}

}
