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
					
					if(selectHex(selectedHex, clickedHex, screen)){}
					else if(selectOtherHex(selectedHex, clickedHex, screen)){}
					else if(attackHex(selectedHex, clickedHex, screen)){}
					else if(move(selectedHex, clickedHex, screen)){}
					else if(deselect(selectedHex, clickedHex, screen)){}
				}
				
			}
		}
		//long end = System.currentTimeMillis();
		
		//System.out.println("Mouse click event took: " +  (end-start) +  " milliseconds");
		//screen.repaint();
	}
	
	//If the selected hex is occupied, a hex was already selected and they are not the same and have the same owner, select it
	public Boolean selectHex(Hex selected, Hex clicked, Screen s){
		if(clicked.occupied && selected ==null){
			s.board.setSelected(clicked);
			s.updateLabel();
			s.hexPanel.repaint();
			System.out.println("hex selected");
			return true;
		}else{
			return false;
		}
		
	}
	
	//If the selected hex is occupied, a hex was already selected, they are not the same, and haves a different owner, attack it
	public Boolean selectOtherHex(Hex selected, Hex clicked, Screen s){
		if(clicked.getUnit() != null){
			if((clicked.occupied && clicked != selected && sameOwner(selected, clicked))
					|| (selected.getUnit().owner.equals("cpu")
						&& clicked.getUnit().owner.equals("human"))
					|| (selected.getUnit().owner.equals("human")
						&& !selected.adjacentTo(clicked))){
				s.board.setSelected(clicked);
				s.updateLabel();
				s.hexPanel.repaint();
				System.out.println("New hex selected");
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	//If the selected hex is not occupied, a hex was already selected, they are not the same and the previously selected is owned by the human player, move it.
	public Boolean attackHex(Hex selected, Hex clicked, Screen screen){
		//System.out.println("Attempt at attacking");
		if(clicked.occupied && selected != null 
				&&  !sameOwner(selected, clicked)
				&& screen.board.human.getTurn()
				&& selected.unit.owner.equals("human")
				&& selected.adjacentTo(clicked)){
			
			selected.getUnit().attack(clicked.getUnit());
			screen.board.setSelected(null);
			screen.repaint();
			System.out.println("Unit attacked and hex deselected");
			return true;
		}else{
			return false;
		}
	}
	
	public boolean move(Hex selected, Hex clicked, Screen screen){
		if(!clicked.occupied && selected != null && clicked != selected
				&& selected.getUnit().owner.equals("human")
				&& screen.board.human.turn
				&& selected.unit.owner.equals("human")){
			
			selected.getUnit().move(clicked);
			screen.board.setSelected(null);
			screen.unitPanel.repaint();
			System.out.println("Unit moved and hex deselected");
			return true;
			
		}else{
			return false;
		}
	}
	
	//If the selected hex is occupied, a hex was already selected and they are the same, deselect it.
	public boolean deselect(Hex selected, Hex clicked, Screen screen){
		if(clicked.occupied && selected!=null && clicked == selected){
			screen.board.setSelected(null);
			screen.hexPanel.repaint();
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
