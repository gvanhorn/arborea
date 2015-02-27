import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JComponent;


@SuppressWarnings("serial")
public class Hex extends JComponent{
	Polygon shape;
	Point axialCoord;
	Hex[] neighbours;
	boolean occupied;
	Unit unit;
	Color color;
	
	
	Hex(){
		axialCoord = new Point();
		occupied = false;
	}
	
	Hex(int q, int r, Color c){
		axialCoord = new Point(q, r);
		occupied = false;
		color = c;
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(this.color);
		g2.draw(this.shape);
		g2.fill(this.shape);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.paint(g);
	}
	
	public boolean setCoord(int q, int r){
		axialCoord = new Point(q, r);
		return true;
	}
	
	public boolean setShape(Polygon p){
		if(shape == null){
			shape = p;
			return true;
		}else{
			shape = p;
			return true;
		}
	}
		
	public boolean placeUnit(Unit newUnit){
		if(!occupied){
			unit = newUnit;
			occupied = true;
			return true;
		}else{
			return false;
		}
	}
	
	public boolean removeUnit(){
		if(occupied){
			occupied = false;
			return true;
		}else{
			return false;
		}
		
	}
	
	public Unit getUnit(){
		return unit;
	}
	
	public Point[] getAdjacent(){
		int[][] directions = {{1, 0}, {1, -1}, {0, -1},{-1, 0},{-1, 1}, {0, 1}};
		Point[] adjacent = new Point[6];
		int i=0;
		for(int[] direction : directions){
			Point tmp = new Point(axialCoord.x + direction[0], axialCoord.y + direction[1]);
			adjacent[i] = tmp;
			i++;
		}
		
		return adjacent;
	}
	
	public String toString(){
		if(occupied){
			return axialCoord.toString() + ": " + unit.name;
		}else{
			return "[r=" + axialCoord.x + ",q=" + axialCoord.y + "]: " + "empty";
		}
		
	}
	
	public void print(){
		System.out.printf("occupied: " + occupied);
	}
	


}
