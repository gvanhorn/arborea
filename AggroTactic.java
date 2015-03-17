import java.util.List;




public class AggroTactic extends Tactic{

	
	
	AggroTactic(Board b) {
		super(b);
	}

	
	@Override
	public MoveList getMoveList() {
		return null;		
	}

	@Override
	void createMoves() {
		super.movelist = new MoveList();
		System.out.println("Creating moves");
		
		//loop for planning the walking moves
		for(Unit u: super.board.cpuUnits){
			
			//System.out.println("planning move for unit: " + u.toString());
			Unit target = super.getClosestEnemyUnit(u);
			//System.out.println("Going to target: " + target.toString());
			
			Hex from = u.getPosition();
			u.moveTowards(target);
			Hex to = u.getPosition();
			super.movelist.addToHexList(from, to);
			super.movelist.addToTypeList("move");
			
			//System.out.println("moving to hex: " + to.axialCoord.toString());
		}
		
		//loop for planning attacks
		for(Unit u : super.board.cpuUnits){
			//System.out.println("Planning attack for unit: " + u.toString());
			List<Unit> adjacentEnemies = u.getAdjacentEnemies();
			
			Unit target;
			
			if(adjacentEnemies.size() >= 1){
				target = adjacentEnemies.get(0);
				
				
				if(adjacentEnemies.size() > 1){
					
					//System.out.println("Choosing between multiple targets");
					
					for(Unit t : adjacentEnemies){
						if((t.weaponSkill + t.weaponSkillModifier) < (target.weaponSkill + target.weaponSkillModifier)){
							target = t;
						}
					}
					
					super.movelist.addToHexList(u.getPosition(), target.getPosition());
					super.movelist.addToTypeList("attack");
				}
				//System.out.println("TARGET is: " + target.toString());
			}
		}


	
	}
}
