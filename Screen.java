import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JPanel{
	int screenWidth, screenHeight;
	BufferedImage goblinImg;
	double hexSize;
	Board board;
	
	/*
	 * 
	 */
	Screen(Board providedBoard, int[] screensize){
		board = providedBoard;
		this.setDoubleBuffered(true);		
		this.setBackground(Color.WHITE);
		try{
			goblinImg = ImageIO.read(new File("images/Goblin.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		for(Hex[] row : board.board){
			for(Hex hex : row){
				//hex.paint(g);
				paintHex(g2, hex);
			}
		}
	}
	
	private void paintHex(Graphics2D g2, Hex h){
		g2.setPaint(h.color);
		g2.draw(h.shape);
		g2.fill(h.shape);
		
		if(h.occupied){
			switch (h.unit.getName()){
				case "Goblin":
					g2.drawImage(goblinImg, h.euclCoord.x, h.euclCoord.y, null);
					break;
				default:
					System.out.println("No image for this unit");
					break;
			
			}
		}

	}
}
