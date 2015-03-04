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
	BufferedImage goblinImg, swordsmanImg, orcImg, generalImg;

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
		// infoPanel = new infoPanel();
		
		this.setPreferredSize(dim);
		
		hexPanel.setPreferredSize(dim);
		unitPanel.setPreferredSize(dim);
		// infoPanel.setPreferredSize(new Dimension(200,150));
		
		hexPanel.setBounds(0, 0, screensize[0], screensize[1]);
		unitPanel.setBounds(0, 0, screensize[0], screensize[1]);
		// infoPanel.setBounds(30, 70, 30+200, 70+150);
		
		this.add(hexPanel, this.DEFAULT_LAYER);
		this.add(unitPanel, this.PALETTE_LAYER);
		this.add(unitPanel, this.MODAL_LAYER);
		this.setDoubleBuffered(true);		
		
		unitPanel.setOpaque(false);
		hexPanel.setOpaque(true);
		// infoPanel.setOpaque(false);
		
		hexPanel.setBackground(board.palette.white);
		try{
			goblinImg = ImageIO.read(new File("images/Goblin.png"));
			swordsmanImg = ImageIO.read(new File("images/Swordsman.png"));
			orcImg = ImageIO.read(new File("images/Orc.png"));
			generalImg = ImageIO.read(new File("images/General.png"));
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
			System.out.println("We are painting units");
			if(h.occupied){
				switch (h.unit.getName()){
					case "Goblin":
						g2.drawImage(goblinImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					case "Swordsman":
						g2.drawImage(swordsmanImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					case "Orc":
						g2.drawImage(orcImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					case "General":
						g2.drawImage(generalImg, h.euclCoord.x + h.getUnit().getOffset().x, h.euclCoord.y + h.getUnit().getOffset().y, null);
						break;
					default:
						System.out.println("No image for this unit");
						break;
				}
			}
		}
	}

	// private class infoPanel extends JPanel{
	// 	public void paintComponent(Graphics g){
	// 		super.paintComponent(g);
	// 	}


	// 	private void createInfoPanel(Graphics g, Hex h){
	// 		Graphics2D g2 = (Graphics2D) g;
	// 		g2.drawRect (30, 70, 200, 150);  
	// 		g2.drawString(h.unit.name, 35, 75);
	// 		g2.drawString("HP: " + Integer.toString(h.unit.hitpoints), 35, 100);
	// 		g2.drawString("Weaponskill: " + Integer.toString(h.unit.weaponSkill) + "+ " + Integer.toString(h.unit.weaponSkillModifier), 35, 125);
	// 	}
	// }

}
