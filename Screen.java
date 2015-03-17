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

	JPanel hexPanel, unitPanel, infoPanel, popUpPanel, endTurnPanel;
	JLabel unitNameLabel, hitPointsLabel, weaponSkillLabel, hitMissLabel, winLoseLabel;
	JLabel[] hitChanceLabels;
	JButton endTurnButton;
	BufferedImage goblinImg, swordsmanImg, orcImg, generalImg;
	ActionListener hitMisslistener;
	Timer hitMissTimer;

	Palette palette;
	double hexSize;
	Board board;
	
	/*
	 * 
	 */
	Screen(Board providedBoard, int[] screensize){
		board = providedBoard;
		Dimension dim = new Dimension(screensize[0], screensize[1]);
		screenWidth = screensize[0];
		screenHeight = screensize[1];
		palette = new Palette();
		setupPanels(dim);
		loadImages();
		setupTimeListeners();
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

	public void initHitChanceLabels(){
		hitChanceLabels = new JLabel[6];

		for (int i = 0; i < 6; i++){
			hitChanceLabels[i] = new JLabel("", SwingConstants.LEFT);
			hitChanceLabels[i].setPreferredSize(new Dimension(10, 15));
			hitChanceLabels[i].setBounds(0, 0, 100, 20);
			// Font hitChanceFont = new Font("default", Font.PLAIN, 20);
			// Label.setFont(hitChanceFont);
			hitChanceLabels[i].setForeground(Color.black);
			popUpPanel.add(hitChanceLabels[i]);

		}
	}

	public void setupPanels(Dimension dim){

		// Create all JPanels
		hexPanel = new hexPanel();
		unitPanel = new unitPanel();
		infoPanel = new JPanel();
		popUpPanel = new JPanel(null);
		endTurnPanel = new JPanel();


		// Create unit info labels
		unitNameLabel = new JLabel("", SwingConstants.LEFT);
		hitPointsLabel = new JLabel("", SwingConstants.LEFT);
		weaponSkillLabel = new JLabel("", SwingConstants.LEFT);
		hitMissLabel = new JLabel("", SwingConstants.LEFT);
		winLoseLabel = new JLabel("", SwingConstants.LEFT);
		
		// Set unit name font 
		Font font = new Font("default", Font.BOLD,14);
		unitNameLabel.setFont(font);
	
		// font = new Font("default", Font.BOLD, 10);	
		// hitMissLabel.setFont(font);
		
		// Set dimensions of panels
		this.setPreferredSize(dim);
		hexPanel.setPreferredSize(dim);
		unitPanel.setPreferredSize(dim);
		popUpPanel.setPreferredSize(dim);
		endTurnPanel.setPreferredSize(dim);
		
		hexPanel.setBounds(0, 0, dim.width, dim.height);
		unitPanel.setBounds(0, 0, dim.width, dim.height);
		infoPanel.setBounds(10, 10, 150, 100);
		popUpPanel.setBounds(0, 0, dim.width, dim.height);
		endTurnPanel.setBounds(0, 0, dim.width, dim.height);
		
		// Set translucency of panels
		unitPanel.setOpaque(false);
		hexPanel.setOpaque(true);
		infoPanel.setOpaque(true);
		popUpPanel.setOpaque(false);
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
		//endTurnButton.addActionListener(screen);
		endTurnButton.setLocation(dim.width - (100 + 40), dim.height - (60 + 40));
		// endTurnButton.setBounds(630, 680, dim.width - 20, dim.height - 20);
		endTurnButton.setLayout(null);
		endTurnButton.setSize(100, 60);
		endTurnPanel.setLayout(null);
		endTurnPanel.add(endTurnButton);


		// Make hitMissLabel
		hitMissLabel.setPreferredSize(new Dimension(10, 15));
		hitMissLabel.setBounds(0, 0, 100, 20);
		Font hitMissFont = new Font("default", Font.PLAIN, 20);
		hitMissLabel.setFont(hitMissFont);
		hitMissLabel.setForeground(Color.white);
		popUpPanel.add(hitMissLabel);

		// Make hitChanceLabel
		// hitChanceLabel
		// winLoseLabel.setPreferredSize(new Dimension(10, 15));
		int wlLabelWidth = 600;
		int wlLabelHeight = 130;
		winLoseLabel.setBounds(0, 0, wlLabelWidth, wlLabelHeight);
		// winLoseLabel.setLocation(screenWidth / 2,400);
		winLoseLabel.setLocation((screenWidth /2) - (wlLabelWidth / 2) , (screenHeight / 2) - 200);

		popUpPanel.add(winLoseLabel);

		// add panels to layeredPane
		this.add(hexPanel, JLayeredPane.DEFAULT_LAYER);
		this.add(unitPanel, JLayeredPane.PALETTE_LAYER);
		this.add(infoPanel, JLayeredPane.MODAL_LAYER);
		this.add(popUpPanel, JLayeredPane.POPUP_LAYER);
		this.add(endTurnPanel, JLayeredPane.DRAG_LAYER);

		initHitChanceLabels();
		
		// Enable double buffering
		this.setDoubleBuffered(true);
	}

	public void setupTimeListeners(){
		hitMisslistener = new ActionListener(){
  			public void actionPerformed(ActionEvent event){
    			hitMissLabel.setText("");
    			clearPercentages();
    			hitMissTimer.stop();
  			}
  		};	
		hitMissTimer = new Timer(1000, hitMisslistener);
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


	//If the selected hex is occupied, a hex was already selected and they are not the same and have the same owner, select it
	public Boolean selectHex(Hex selected, Hex clicked){
		if(clicked.occupied && selected ==null){
			this.setSelected(clicked);	
			this.updateLabel();
			this.hexPanel.repaint();
			System.out.println("hex selected");
			return true;
		}else{
			return false;
		}
		
	}


	public void setSelected(Hex h){
		Hex[] adjacentEnemies;

		//recolor the previously selected hex to default colors
		if (board.selectedHex != null){
			board.selectedHex.color = palette.green;
			reColorHexGroup(board.selectedHex.neighbours, palette.green);
		}
		
		//Color the newly selected hex and its neighbours
		if(h != null){
			board.selectedHex = h;
			board.selectedHex.color = palette.darkGreen;

			// With human check included

			if(!h.getUnit().moved && board.human.getTurn() && h.getUnit().owner.equals("human")){
				reColorHexGroup(board.selectedHex.getUnOccupiedNeighbours(), palette.lightOrange);
			}
			if(!h.getUnit().attacked && board.human.getTurn()&& h.getUnit().owner.equals("human")){
		
				adjacentEnemies = board.selectedHex.getEnemyOccupiedNeighbours();
				paintPercentages(adjacentEnemies, h);
				reColorHexGroup(adjacentEnemies, palette.red);
			}


			// Without human check 

			// if(!h.getUnit().moved && board.human.getTurn() && h.getUnit().owner.equals("human")){
			// 	reColorHexGroup(board.selectedHex.getUnOccupiedNeighbours(), palette.lightOrange);
			// }
			// if(!h.getUnit().attacked){
		
			// 	adjacentEnemies = board.selectedHex.getEnemyOccupiedNeighbours();
			// 	paintPercentages(adjacentEnemies, h);
			// 	reColorHexGroup(adjacentEnemies, palette.red);
			// }
		}
		
		//Deselect a hex
		if(h == null){
			board.selectedHex = null;
		}
	}


	public void paintPercentages(Hex[] enemies, Hex selectedUnit){
		clearPercentages();
		Point coords;
		double hitChance;
		int prettyChance;
		int counter = 0;
		for (Hex enemy : enemies){
			coords = enemy.getEuclCoord();
			hitChance = 1/(1+Math.pow(Math.E, (-0.4* ((selectedUnit.getUnit().weaponSkill + selectedUnit.getUnit().weaponSkillModifier)-(enemy.getUnit().weaponSkill + enemy.getUnit().weaponSkillModifier)))));
			prettyChance = (int) Math.round(hitChance * 100);
			System.out.println("Hitchance: " + prettyChance);
			System.out.println(coords.x);
			hitChanceLabels[counter].setLocation(coords.x -9 , coords.y + 15);
			hitChanceLabels[counter].setText(prettyChance + "%");
			counter++;
		}
	}

	public void clearPercentages(){
		for (int i = 0; i < 6; i++){
			hitChanceLabels[i].setText("");
		}
	}
	
	public void paintwinLose(){
		if (board.humanUnits.size() == 0){
			Font winLoseFont = new Font("Lucida Blackletter", Font.PLAIN, 152);
			winLoseLabel.setFont(winLoseFont);
			winLoseLabel.setText("You Lose");
			winLoseLabel.setForeground(palette.bloodRed);
			System.out.println("YOU LOSE");
		} else if (board.cpuUnits.size() == 0){
			Font winLoseFont = new Font("Lucida Blackletter", Font.PLAIN, 67);
			winLoseLabel.setFont(winLoseFont);
			System.out.println("YOU WIN!!");
			winLoseLabel.setForeground(palette.gold);
			winLoseLabel.setText("Thou Art Victorious");
			// winLoseLabel.setLocation(( screenWidth / 2 ) - (winLoseLabel.getPreferredSize().width / 2), ( screenHeight / 2 ) - (winLoseLabel.getPreferredSize().height / 2) );
		}
	}


	public Boolean selectOtherHex(Hex selected, Hex clicked){
		System.out.print(clicked != selected);
		if (clicked.getUnit() != null && clicked != selected) {
			if((clicked.occupied && sameOwner(selected, clicked))
					|| (selected.occupied && selected.getUnit().owner.equals("cpu")
						&& clicked.getUnit().owner.equals("human"))
					|| (selected.occupied && selected.getUnit().owner.equals("human")
						&& !selected.adjacentTo(clicked) )) {


				this.setSelected(clicked);
				this.updateLabel();
				this.hexPanel.repaint();
				System.out.println("New hex selected");
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}
	
	//If the selected hex is not occupied, a hex was already selected, they are not the same and the previously selected is owned by the human player, move it.
	public Boolean attackHex(Hex selected, Hex clicked){

		System.out.println("Attempt at attacking");
		if(clicked.occupied && selected != null 
				&&  !sameOwner(selected, clicked)
				&& this.board.human.getTurn()
				&& selected.unit.owner.equals("human")
				&& selected.adjacentTo(clicked)
				&& !selected.unit.attacked){
			
			Boolean hit = selected.getUnit().attack(clicked.getUnit());
			paintHitMiss(clicked, hit);	
			board.removeDead(clicked.getUnit());
			this.setSelected(null);
			this.repaint();

			if (board.victory()){
				paintwinLose();
			}

			System.out.println("Unit attacked and hex deselected");
			return true;
		}else{
			return false;
		}
	}

	public Boolean cpuAttackHex(Hex selected, Hex clicked){
		System.out.println("Attempt at attacking");
		Boolean hit = selected.getUnit().attack(clicked.getUnit());
		board.removeDead(clicked.getUnit());
		paintHitMiss(clicked, hit);
		this.setSelected(null);
		this.repaint();

		if (board.victory()){
				paintwinLose();
		}
		System.out.println("Unit attacked and hex deselected");
		return true;
	}


	public void paintHitMiss(Hex h, Boolean hit){
		Point euclCoord = h.getEuclCoord();
		if (hit){
			// hitMissLabel.setForeground(Color.red);
			// hitMissLabel.setLocation(euclCoord.x - (hitMissLabel.getPreferredSize().width -10), euclCoord.y + (hitMissLabel.getPreferredSize().height));
			hitMissLabel.setLocation(euclCoord.x - (hitMissLabel.getPreferredSize().width) -6 , euclCoord.y - 8);
			if (hitMissTimer.isRunning()) {
          		hitMissTimer.stop();
          		hitMissLabel.setText("Hit!");
          		hitMissTimer.start();
        	} else {
        		hitMissLabel.setText("Hit!");
          		hitMissTimer.start();
          	}
		} else {
			// hitMissLabel.setForeground(Color.white);
			hitMissLabel.setLocation(euclCoord.x - (hitMissLabel.getPreferredSize().width) -10 , euclCoord.y - 8);
			if (hitMissTimer.isRunning()) {
          		hitMissTimer.stop();
          		hitMissLabel.setText("Miss");
          		hitMissTimer.start();
        	} else {
        		hitMissLabel.setText("Miss!");
          		hitMissTimer.start();
          	}
		}

	}

	
	public boolean move(Hex selected, Hex clicked){
		if(!clicked.occupied && selected != null && clicked != selected
				&& selected.getUnit().owner.equals("human")
				&& this.board.human.turn
				&& selected.unit.owner.equals("human")){
			
			selected.getUnit().move(clicked);
			this.setSelected(null);
			this.unitPanel.repaint();
			System.out.println("Unit moved and hex deselected");
			return true;
			
		}else{
			return false;
		}
	}
	
	//If the selected hex is occupied, a hex was already selected and they are the same, deselect it.
	public boolean deselect(Hex selected, Hex clicked){
		clearPercentages();
		if(clicked.occupied && selected!=null && clicked == selected){
			this.setSelected(null);
			this.hexPanel.repaint();
			System.out.println("Hex deselected");
			return true;
		}else{
			return false;
		}
	}

	public Boolean sameOwner(Hex h1, Hex h2){
		if (h1.getUnit().owner.equals(h2.getUnit().owner)){
			return true;
		} else{
			return false;
		}
	}

	//Give a group of hexes a new color
	public void reColorHexGroup(Hex[] group, Color c){
		for(Hex h : group){
			if(h != null){
				h.color = c;
			}
		}
	}

	

	
}
