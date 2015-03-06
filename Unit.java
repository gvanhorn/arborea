import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public abstract class Unit {
	int hitpoints;
	int weaponSkill;
	int weaponSkillModifier;
	Point imageOffset;
	String name;
	String owner;
	private Hex position;

		
	Unit(){
	}
	
	Unit(int hp, int ws, String n, String o){
		hitpoints = hp;
		weaponSkill = ws;
		weaponSkillModifier = 0;
		name = n;
		owner = o;
		imageOffset = new Point(0,0);
	}
	
	public void setPosition(Hex h){
		position = h;
	}
	
	public Hex getPosition(){
		return position;
	}
	
	public void updateModifier(int newMod){
		weaponSkillModifier = newMod;
	}
	
	public void updateModifier(){
		int wsm = 0;
		Unit adj; 
		for(Hex h : position.getNeighbours()){
			if(h != null){
				if(h.occupied){
					adj = h.getUnit();
					if(adj.owner.equals(this.owner)){
						if(adj instanceof General || adj instanceof Orc){
							wsm += 2;
						}else{
							wsm++;
						}
					}else{
						if(adj instanceof General || adj instanceof Orc){
							wsm -= 2;
						}else{
							wsm--;
						}
					}
				}
			}
		}
		this.updateModifier(wsm);
	}
	
	public List<Unit> getAdjacentUnits(){
		List<Unit> units = new ArrayList<Unit>();
		for(Hex h : position.neighbours){
			if(h != null && h.occupied){
				units.add(h.getUnit());
			}
		}
		return units;
	}
	
	public void attack(Unit u){
		double hitChance =  1/(1+Math.pow(Math.E, (-0.4* ((weaponSkill + weaponSkillModifier)-(u.weaponSkill - u.weaponSkillModifier)))));
		Random rnd = new Random();
		if(rnd.nextFloat()<hitChance){
			u.hitpoints--;
			System.out.println("Attack Hit!");
			if(u.hitpoints == 0){
				u.getPosition().removeUnit();
			}
		}else{
			System.out.println("Attack Missed!");
		}
		
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
