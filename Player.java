

public class Player {
Hex selected;
boolean turn;

	Player(){
		selected = null;
	}
	
	public void setSelected(Hex hex){
		selected = hex;
	}
	
	public void setTurn(boolean t){
		turn = t;
	}
}
