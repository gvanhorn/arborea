import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JFrame{
	Board board;

	int screenWidth, screenHeight;
	double hexSize;
	
	Screen(Board providedBoard){
		super("Main screen");
		board = providedBoard;
		setSize(800,800);
		setVisible(true);

		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);  
        
		Polygon[] hexGrid = createHexGrid();
		
		Graphics2D g2 = (Graphics2D) g;
		for(Polygon hex : hexGrid){
			g2.draw(hex);        
		}

	}
	
	public Polygon[] createHexGrid(){
		Polygon[] hexGrid = new Polygon[61];
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
				hexGrid[i] = createHexagon(x, y, hexSize);
				i++;
			}
		}
		
		return hexGrid;
	}
	
	
	/*
	 * Creates a hexagon around the coordinates given with a certain size.
	 */
	public Polygon createHexagon(int x, int y, double size){
		Polygon hex = new Polygon();
		
		for(int i = 0; i < 6; i ++){
			
			int xCoord = (int)Math.round(x + size * Math.cos(i * 2 * Math.PI / 6));
			int yCoord = (int)Math.round(y +  size * Math.sin(i * 2 * Math.PI / 6));
			hex.addPoint(xCoord,yCoord); 
							
		}
		return hex;
		
	}
}
