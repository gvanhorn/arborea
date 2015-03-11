import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JLayeredPane{
	int screenWidth, screenHeight;

	JPanel hexPanel, unitPanel, infoPanel, endTurnPanel;
	JLabel unitNameLabel, hitPointsLabel, weaponSkillLabel;
	JButton endTurnButton;
	BufferedImage goblinImg, swordsmanImg, orcImg, generalImg;

	double hexSize;
	Board board;
	Player human;
	/*
	 * 
	 */
	Screen(Board providedBoard, int[] screensize, Player p){
		board = providedBoard;
		human = p;
		Dimension dim = new Dimension(screensize[0], screensize[1]);
		setupPanels(dim);
		loadImages();
	}


	public void loadImages(){

		try{
			goblinImg = ImageIO.read(new File("images/Goblin.png"));
			swordsmanImg = ImageIO.read(new File("images/Swordsman.png"));
			orcImg = ImageIO.read(new File("images/Orc.png"));
			generalImg = ImageIO.read(new File("images/General.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public void setupPanels(Dimension dim){

		// Create all JPanels
		hexPanel = new hexPanel();
		unitPanel = new unitPanel();
		infoPanel = new JPanel();
		endTurnPanel = new JPanel();
		

		// Create unit info labels
		unitNameLabel = new JLabel("", SwingConstants.LEFT);
		hitPointsLabel = new JLabel("", SwingConstants.LEFT);
		weaponSkillLabel = new JLabel("", SwingConstants.LEFT);
		
		// Set unit name font 
		Font font = new Font("default", Font.BOLD,14);
		unitNameLabel.setFont(font);
		
		// Set dimensions of panels
		this.setPreferredSize(dim);
		hexPanel.setPreferredSize(dim);
		unitPanel.setPreferredSize(dim);
		endTurnPanel.setPreferredSize(dim);
		
		
		hexPanel.setBounds(0, 0, dim.width, dim.height);
		unitPanel.setBounds(0, 0, dim.width, dim.height);
		infoPanel.setBounds(10, 10, 150, 100);
		endTurnPanel.setBounds(0, 0, dim.width, dim.height);
		
		// Set translucency of panels
		unitPanel.setOpaque(false);
		hexPanel.setOpaque(true);
		infoPanel.setOpaque(true);
		endTurnPanel.setOpaque(false);
		
		// Create layout for unit info label

		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.ipady = 10;
		c.ipadx = 10;
		c.gridx = 0;
		c.gridy = 0;
		infoPanel.add(unitNameLabel, c);
		
		c.gridy = 1;
		infoPanel.add(hitPointsLabel, c);
		
		c.gridy = 2;
		infoPanel.add(weaponSkillLabel, c);
		
		// Set background color of panels
		infoPanel.setBackground(board.palette.lightGreen);
		hexPanel.setBackground(board.palette.white);

		//create end turn button
		endTurnButton = new JButton("End turn");
		endTurnButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e){
				System.out.println("End turn clicked");
				human.setTurn(false);
			}
		});
		endTurnButton.setLocation(dim.width - (100 + 40), dim.height - (60 + 40));
		// endTurnButton.setBounds(630, 680, dim.width - 20, dim.height - 20);
		endTurnButton.setLayout(null);
		endTurnButton.setSize(100, 60);
		endTurnPanel.setLayout(null);
		endTurnPanel.add(endTurnButton);
		
		// add panels to layeredPane
		this.add(hexPanel, JLayeredPane.DEFAULT_LAYER);
		this.add(unitPanel, JLayeredPane.PALETTE_LAYER);
		this.add(infoPanel, JLayeredPane.MODAL_LAYER);
		this.add(endTurnPanel, JLayeredPane.DRAG_LAYER);
		
		// Enable double buffering
		this.setDoubleBuffered(true);	
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



	public void updateLabel(){
		if (board.selectedHex != null) {
			Hex h = board.selectedHex;
			unitNameLabel.setText(h.unit.name);
			hitPointsLabel.setText("HP: " + Integer.toString(h.unit.hitpoints));
			if (h.unit.weaponSkillModifier >= 0){
				weaponSkillLabel.setText("Weapon skill: " + Integer.toString(h.unit.weaponSkill) + " + " + Integer.toString(h.unit.weaponSkillModifier));
			} else {
				weaponSkillLabel.setText("Weapon skill: " + Integer.toString(h.unit.weaponSkill) + " " + Integer.toString(h.unit.weaponSkillModifier));
			}
		}
		else{
			unitNameLabel.setText("");
			hitPointsLabel.setText("");
			weaponSkillLabel.setText("");
		}
	}
	
}
