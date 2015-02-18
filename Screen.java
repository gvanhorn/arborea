import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Screen extends JFrame{
	
		
	Screen(){
		
		super("Main screen");
		setSize(400,400);
		setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
	public void paint(Graphics g){
		super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        //Line2D lin = new Line2D.Float(100, 100, 250, 260);
        //g2.draw(lin);
		
		Point[] hexPoints = calcHexPoints(200, 200, 40);
        //drawHex(hexPoints, g);
		
        for(int i = 0; i < 6; i++){
        	Point punt1 = hexPoints[i];
        	Point punt2 = hexPoints[(i+1) % 5];
        	System.out.println(punt1 + ", " + punt2);
        	Line2D lin = new Line2D.Float(punt1.x, punt1.y, punt2.x, punt2.y);
        	
        	g2.draw(lin);
        }
	}
	
//	public Line2D drawHex(Point[] hexPoints, Graphics g){
//		
//			
//			
//			
//		
//	}
	
	public Point[] calcHexPoints(int x, int y, int size){
		Point[] punten = new Point[6];
		
		for(int i = 0; i < 6; i ++){
			double angle = (2* Math.PI ) / (6 * (i+1));
			System.out.println(angle);
			System.out.println(Math.cos(angle));
			
			
			punten[i] = new Point((int)Math.round(x + size * Math.cos(angle)),
								(int)Math.round(y + size * Math.sin(angle)));
			
		}
		return punten;
	}
}
