import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JFrame{
	Board board;
	JPanel panel;
	int screenWidth, screenHeight;
	double hexSize;
	HexButton[] hexGrid;
	
	Screen(Board providedBoard){
		super("Main screen");
		
		board = providedBoard;
		setSize(800,800);
		setVisible(true);
		panel = new JPanel();
		panel.setVisible(true);
		this.add(panel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		hexGrid = createHexGrid();
		panel.add(hexGrid[0]);
		
		
	}
	
	public void paint(Graphics g){
		super.paint(g);  
        
		
		
		Graphics2D g2 = (Graphics2D) g;
		
		
		for(HexButton hex : hexGrid){
			g2.draw(hex.shape);
			//panel.add(hex);
		}

	}
	
	public HexButton[] createHexGrid(){
		HexButton[] hexGrid = new HexButton[61];
		int screenWidth = this.getSize().width;
		int screenHeight = this.getSize().height;
		
		double hexSize = Math.floor((Math.min(screenWidth, screenHeight) - 20) / 18);
		double hexWidth = 2 * hexSize;
		double hexHeight = Math.sqrt(3)/2 * hexWidth;
		double horizontalDistance = (double)hexWidth * 3/4 + 3;
		double verticalDistance = hexHeight + 3; 
		
		System.out.println(horizontalDistance);
		
		Point center = new Point(screenWidth/2, screenHeight/2);
		int u,v,x,y;
		int i =0;
		for(Hex[] row : board.board){
			for(Hex hex: row){
				
				u = hex.axialCoord.x;
				v = hex.axialCoord.y;
				x = center.x + (int)Math.floor(horizontalDistance * u);
				y = center.y + (int)Math.floor(verticalDistance * (u*.5+v));
				//System.out.println(horizontalDistance * u);
//				System.out.println(x);
				HexButton button = createHexButton(x, y, hexSize, u, v); 
				hexGrid[i] = button;
				i++;
			}
		}
		
		return hexGrid;
	}
	
	
	/*
	 * Creates a hexagon around the coordinates given with a certain size.
	 */
	public HexButton createHexButton(int x, int y, double size, int u, int v){
		Polygon hex = new Polygon();
		
		for(int i = 0; i < 6; i ++){
			
			int xCoord = (int)Math.round(x + size * Math.cos(i * 2 * Math.PI / 6));
			int yCoord = (int)Math.round(y +  size * Math.sin(i * 2 * Math.PI / 6));
			hex.addPoint(xCoord,yCoord); 
			
							
		}
		
		HexButton button = new HexButton(hex, new Point(u,v));
		return button;
		
	}
}
