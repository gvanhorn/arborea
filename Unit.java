import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public abstract class Unit implements java.io.Serializable{
	int hitpoints;
	int weaponSkill;
	int weaponSkillModifier;
	Point imageOffset;
	String name;
	String owner;
	private Hex position;
	boolean moved, attacked;

		
	Unit(){
	}
	
	Unit(int hp, int ws, String n, String o){
		hitpoints = hp;
		weaponSkill = ws;
		weaponSkillModifier = 0;
		name = n;
		owner = o;
		imageOffset = new Point(0,0);
		attacked = false;
		moved = false;
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
	
	public boolean attack(Unit u){
		
		double hitChance =  1/(1+Math.pow(Math.E, (-0.4* ((weaponSkill + weaponSkillModifier)-(u.weaponSkill + u.weaponSkillModifier)))));
		System.out.println(hitChance);
		Random rnd = new Random();
		attacked = true;
		if(rnd.nextFloat()<hitChance){
			u.hitpoints--;
			System.out.println("Attack Hit!");
			if(u.hitpoints == 0){
				u.getPosition().removeUnit();
			}
			return true;
		}else{
			System.out.println("Attack Missed!");
			return false;
		}
	}
		
	public void move(Hex to){
		if(!to.occupied && Arrays.asList(position.neighbours).contains(to) && !moved){
			position.removeUnit();
			
			for(Hex adj : position.getOccupiedNeighbours()){
				adj.getUnit().updateModifier();
			}
			
			to.placeUnit(this);
			this.setPosition(to);
			this.updateModifier();
			
			for(Unit u : getAdjacentUnits()){
				u.updateModifier();
			}
			moved = true;
			System.out.println("Unit moved");
		}else{
			System.out.println("Illegal move!");
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
