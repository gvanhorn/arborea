import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JLayeredPane{
	int screenWidth, screenHeight;
	JPanel hexPanel, unitPanel;
	BufferedImage goblinImg, swordsmanImg;
	double hexSize;
	Board board;
	
	/*
	 * 
	 */
	Screen(Board providedBoard, int[] screensize){
		board = providedBoard;
		Dimension dim = new Dimension(screensize[0], screensize[1]);
		
		hexPanel = new hexPanel();
		unitPanel = new unitPanel();
		
		this.setPreferredSize(dim);
		
		hexPanel.setPreferredSize(dim);
		unitPanel.setPreferredSize(dim);
		
		hexPanel.setBounds(0, 0, screensize[0], screensize[1]);
		unitPanel.setBounds(0, 0, screensize[0], screensize[1]);
		
		this.add(hexPanel, this.DEFAULT_LAYER);
		this.add(unitPanel, this.PALETTE_LAYER);
		this.setDoubleBuffered(true);		
		
		unitPanel.setOpaque(false);
		hexPanel.setOpaque(true);
		
		hexPanel.setBackground(board.palette.white);
		try{
			goblinImg = ImageIO.read(new File("images/Goblin.png"));
			swordsmanImg = ImageIO.read(new File("images/Swordsman.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	private class hexPanel extends JPanel{
		
		@Override
		public void paintComponent(Graphics g){
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
		}
		
	}
	
	private class unitPanel extends JPanel{
	
		@Override
		public void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D) g;
			for(Hex[] row : board.board){
				for(Hex hex : row){
					//hex.paint(g);
					paintUnit(g2, hex);
				}
			}
		}
		
		private void paintUnit(Graphics2D g2, Hex h){
			if(h.occupied){
				switch (h.unit.getName()){
					case "Goblin":
						g2.drawImage(goblinImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					case "Swordsman":
						g2.drawImage(swordsmanImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					default:
						System.out.println("No image for this unit");
						break;
				
				}
			}
		}
	}
}
