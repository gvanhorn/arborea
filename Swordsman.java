import java.awt.Point;

@SuppressWarnings("serial")
public class Swordsman extends Unit{
	
	Swordsman(String o){
		super.hitpoints = 4;
		super.weaponSkill = 6;
		super.name = "Swordsman";
		super.owner = o;
		super.imageOffset = new Point(-33, -45);
	}

}
