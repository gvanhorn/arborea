import java.awt.Point;
import java.awt.event.*;

import javax.swing.JFrame;

public class ScreenListener implements WindowListener, WindowFocusListener, WindowStateListener, MouseListener{
	
	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		System.out.println("Window opened");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
		Screen screen = (Screen) e.getSource();
		
		for(Hex[] row: screen.board.board){
			for(Hex h: row){
				//check which hex is clicked
				if(h.shape.contains(x,y)){
					//if the hex contains a unit, select or deselect the hex and repaint the hexPanel
					if(h.occupied){
						System.out.println( h.axialCoord);
						if(screen.board.selectedHex != h){
							screen.board.setSelected(h);
						}else{
							screen.board.setSelected(null);
						}
						screen.hexPanel.repaint();
						
					//If it is not occupied and a hex was already selected, move the unit that was on selectedHex 
					//to the new hex and de-select the hex, and repaint the unitPanel.
					}else if(!h.occupied && screen.board.selectedHex != null){
						screen.board.moveUnit(screen.board.selectedHex, h);
						screen.board.setSelected(null);
						screen.unitPanel.repaint();
					}
				}
				
			}
		}
		
		//screen.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
