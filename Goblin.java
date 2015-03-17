import java.awt.Point;



@SuppressWarnings("serial")
public class Goblin extends Unit{

	Goblin(String o){
		super.hitpoints = 3;
		super.weaponSkill = 4;
		super.name = "Goblin";
		super.owner = o;
		super.imageOffset = new Point(-33, -45);
	}
}
