
public abstract class Unit {
	int hitpoints;
	int weaponSkill;
	String name;
	
	Unit(){
		name = "xxXXmegaTronFinalBoss420BitchXXxx";
		hitpoints = 10000;
		weaponSkill = 1000;
	}
	
	Unit(int hp, int ws, String n){
		hitpoints = hp;
		weaponSkill = ws;
		name = n;
	}
	
	public void print(){
		System.out.println("Name: " + name + ", hp: " + hitpoints + ", ws: " + weaponSkill);
	}

}
