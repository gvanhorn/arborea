import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


@SuppressWarnings("serial")
public class HexButton extends JButton implements ActionListener{
	Polygon shape;
	Point axCoord;
	
	
	public HexButton(Polygon pol, Point axialCoord){
		super(axialCoord.toString());
		shape = pol;
		axCoord = axialCoord;
		addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.fillPolygon(shape);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(axCoord);
		// TODO Auto-generated method stub
		
	}

}
