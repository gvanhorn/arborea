import java.awt.Point;


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
