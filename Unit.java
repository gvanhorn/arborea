import java.awt.Point;


public abstract class Unit {
	int hitpoints;
	int weaponSkill;
	Point imageOffset;
	String name;
	String owner;
		
	Unit(){
		
	}
	
	Unit(int hp, int ws, String n, String o){
		hitpoints = hp;
		weaponSkill = ws;
		name = n;
		owner = o;
	}
	
	public void print(){
		System.out.println("Name: " + name + ", hp: " + hitpoints + ", ws: " + weaponSkill);
	}
	
	public String getName(){
		return name;
	}
	
	public Point getOffset(){
		return imageOffset;
	}

}
