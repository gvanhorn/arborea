import java.awt.event.*;

import javax.swing.JButton;

public class ScreenListener implements WindowListener, WindowFocusListener, WindowStateListener, MouseListener, ActionListener{
	
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


	// When the mouse is clicked, determine which hex was clicked, and perform necessary action.
	// Which hex was clicked is determined by looping over all hexes, and checking if the hex contians the click location 
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Screen screen = (Screen) e.getSource();
		Board b = screen.board;
		
		for(Hex[] row: b.board){
			for(Hex h: row){

				//check which hex is clicked
				if(h.shape.contains(x,y)){
				
					//If the selected hex is occupied and no hex was selected before, select the hex
					Hex selectedHex = b.selectedHex;
					Hex clickedHex = h;

					if (selectedHex != null)	{
						if(screen.selectOtherHex(selectedHex, clickedHex)){}
						else if(screen.attackHex(selectedHex, clickedHex)){}
						else if(screen.move(selectedHex, clickedHex)){}
						else if(screen.deselect(selectedHex, clickedHex)){}
					}
					else if(screen.selectHex(selectedHex, clickedHex)){}
				}
			}
		}
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

	// Listener for the end turn button
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		Screen s = (Screen) source.getParent().getParent();
		
		if(e.getActionCommand().equals("End turn")){
			s.board.human.setTurn(false);
		}
	}

}
