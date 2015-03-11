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
		long start = System.currentTimeMillis();
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
		Screen screen = (Screen) e.getSource();
		Board b = screen.board;
		
		for(Hex[] row: b.board){
			for(Hex h: row){
				//check which hex is clicked
				if(h.shape.contains(x,y)){
					//If the selected hex is occupied and no hex was selected before, select the hex
					if(h.occupied && b.getSelectedHex()==null){
						b.setSelected(h);
						screen.updateLabel();
						screen.hexPanel.repaint();
						System.out.println("hex selected");
					//If the selected hex is occupied, a hex was already selected and they are not the same and have the same owner, select it
					}else if(h.occupied && h != b.selectedHex && h.getUnit().owner.equals(b.selectedHex.getUnit().owner)){
						b.setSelected(h);
						screen.updateLabel();
						screen.hexPanel.repaint();
						System.out.println("New hex selected");
					//If the selected hex is occupied, a hex was already selected, they are not the same, and have a different owner, attack it
					}else if(h.occupied && b.selectedHex != null 
						&&  !h.getUnit().owner.equals(b.selectedHex.getUnit().owner)){
						b.selectedHex.getUnit().attack(h.getUnit());
						b.setSelected(null);
						screen.repaint();
						System.out.println("Unit attacked and hex deselected");
					//If the selected hex is not occupied, a hex was already selected, they are not the same and the previously selected is owned by the human player, move it.
					}else if(!h.occupied && b.selectedHex != null && h != b.selectedHex
							&& b.getSelectedHex().getUnit().owner.equals("human")){
						b.moveUnit(screen.board.selectedHex, h);
						b.setSelected(null);
						screen.unitPanel.repaint();
						System.out.println("Unit moved");
					//If the selected hex is occupied, a hex was already selected and they are the same, deselect it.
					}else if(h.occupied && b.selectedHex!=null && h == b.selectedHex){
						b.setSelected(null);
						screen.hexPanel.repaint();
						System.out.println("Hex deselected");
					}
					
					
				}
				
			}
		}
		long end = System.currentTimeMillis();
		
		System.out.println("Mouse click event took: " +  (end-start) +  " milliseconds");
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
