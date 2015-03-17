import java.awt.Point;


@SuppressWarnings("serial")
public class General extends Unit{
	
	General(String o){
		super.hitpoints = 5;
		super.weaponSkill = 8;
		super.name = "General";
		super.owner = o;
		super.imageOffset = new Point(-33, -45);
	}

}
