import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@SuppressWarnings("serial")
public abstract class Unit implements java.io.Serializable{
	int hitpoints;
	int weaponSkill;
	int weaponSkillModifier;
	Point imageOffset;
	String name;
	String owner;
	private Hex position;
	boolean moved, attacked;

	// empty constructor
	Unit(){
	}
	
	// Create any custom unit, the string owner should be either "human" or "cpu"
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
	
	// Set a units position
	public void setPosition(Hex h){
		position = h;
	}
	
	// Get a units position
	public Hex getPosition(){
		return position;
	}
	
	/*
	 * updateModifier, either with a specified amount or automatically from neighbours.
	 */
	public void updateModifier(int newMod){
		weaponSkillModifier = newMod;
	}
	
	// updateModifier without arguments infers the weapon skill modifier of this unit from its neighbours.
	public void updateModifier(){
		int wsm = 0;
		Unit adj; 
		
		for(Hex h : position.getOccupiedNeighbours()){
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
		this.updateModifier(wsm);
	}
	
	// returns a list of adjacent units.
	public List<Unit> getAdjacentUnits(){
		List<Unit> units = new ArrayList<Unit>();
		for(Hex h : position.neighbours){
			if(h != null && h.occupied){
				units.add(h.getUnit());
			}
		}
		return units;
	}
	
	// returns a list of adjacent enemies
	public List<Unit> getAdjacentEnemies(){
		List<Unit> enemies = new ArrayList<Unit>();
		List<Unit> units = this.getAdjacentUnits();
		for(Unit u : units){
			if(!u.owner.equals(this.owner)){
				enemies.add(u);
				System.out.println("Enemy Adjacent to: " + this.toString() + " is: " + u.toString());
			}
		}
		return enemies;
	}
	
	// attempt to attack a unit u
	public boolean attack(Unit u){
		// Calculate the hit chance
		double hitChance =  1/(1+Math.pow(Math.E, (-0.4* ((weaponSkill + weaponSkillModifier)-(u.weaponSkill + u.weaponSkillModifier)))));
		System.out.println(hitChance);
		Random rnd = new Random();
		attacked = true;
		
		// See if the attack hits
		if(rnd.nextFloat()<hitChance){
			u.hitpoints--;
			if(u.hitpoints == 0){
				// If the victim dies from injuries, remove it from the field.
				u.getPosition().removeUnit();
			}
			return true;
		}else{
			return false;
		}
	}
	
	// Move this unit to the specified hex
	public void move(Hex to){
		
		// Check if it is a legal move
		if(!to.occupied && Arrays.asList(position.neighbours).contains(to) && !moved){
			
			//Remove it from the original spot and update the modifiers for all neighbours
			position.removeUnit();
			for(Hex adj : position.getOccupiedNeighbours()){
				adj.getUnit().updateModifier();
			}
			
			// Place this unit on the next position and update modifiers for this unit and all new neighbours.
			to.placeUnit(this);
			this.setPosition(to);
			this.updateModifier();
			for(Unit u : getAdjacentUnits()){
				u.updateModifier();
			}
			
			moved = true;
		}
	}
	
	// moveTowards returns the Hex that this unit should move to, to get closer to the target, or null, if it next to it.
	public Hex moveTowards(Unit target){
		
		// If we are already next to the target, return current position. 
		if(position.adjacentTo(target.position)){
			return position;
		}
		
		Hex bestmove = null;
		Hex[] possibleHexes = position.getUnOccupiedNeighbours();
		
		// For every non-occupied neighbour, calculate distance to the target.
		if(possibleHexes.length > 0){
			bestmove = possibleHexes[0];
			int dist = bestmove.distanceTo(target.position);
			for(Hex h : possibleHexes){
				int distance = h.distanceTo(target.position);
				if(distance < dist){
					bestmove = h;
					dist = distance;
				}
			}
		}
		
		// If a suitable hexagon to move to was found, return the hex, else, return null.
		if(bestmove!=null){
			System.out.println(bestmove.toString());
			this.move(bestmove);
			return bestmove;
		}else{
			return bestmove;
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
	
	public String toString(){
		String unit = name + " at: " +  position.axialCoord.toString();
		return unit;
	}

}
