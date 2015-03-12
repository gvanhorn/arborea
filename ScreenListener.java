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

	@Override
	public void mouseClicked(MouseEvent e) {
		//long start = System.currentTimeMillis();
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);
		Screen screen = (Screen) e.getSource();
		Board b = screen.board;
		System.out.println(e.getSource().getClass());
		
		for(Hex[] row: b.board){
			for(Hex h: row){
				//check which hex is clicked
				if(h.shape.contains(x,y)){
					//If the selected hex is occupied and no hex was selected before, select the hex
					Hex selectedHex = b.selectedHex;
					Hex clickedHex = h;
					
					if(screen.selectHex(selectedHex, clickedHex)){}
					else if(screen.selectOtherHex(selectedHex, clickedHex)){}
					else if(screen.attackHex(selectedHex, clickedHex)){}
					else if(screen.move(selectedHex, clickedHex)){}
					else if(screen.deselect(selectedHex, clickedHex)){}
				}
				
			}
		}
		//long end = System.currentTimeMillis();
		
		//System.out.println("Mouse click event took: " +  (end-start) +  " milliseconds");
		//screen.repaint();
	}
	
	
	//If the selected hex is occupied, a hex was already selected, they are not the same, and haves a different owner, attack it


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

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		Screen s = (Screen) source.getParent().getParent();
		
		if(e.getActionCommand().equals("End turn")){
			System.out.println("WE BE ENDING TURNS");
			s.board.human.setTurn(false);
			s.board.cpu.performTurn();
			s.board.cpu.resetTurn();
			s.repaint();
			s.board.human.setTurn(true);
		}
		
	}

}
