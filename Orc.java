import java.awt.Point;


public class Orc extends Unit{
	
	Orc(String o){
		super.hitpoints = 10;
		super.weaponSkill = 8;
		super.name = "Orc";
		super.owner = o;
		super.imageOffset = new Point(-33, -45);
	}

}
