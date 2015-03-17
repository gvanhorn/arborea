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
	void createTactic() {
		super.movelist = new MoveList();
		System.out.println("Creating moves");
		
		//loop for planning the walking moves
		for(Unit u: super.board.cpuUnits){
			
			System.out.println("planning move for unit: " + u.toString());
			Unit target = super.getClosestEnemyUnit(u);
			System.out.println("Going to target: " + target.toString());
			
			Hex from = u.getPosition();
			Hex targetHex = target.getPosition();
			
			Hex to = targetHex.getUnOccupiedNeighbours()[0];
			
			int bestDist = from.distanceTo(to);
			for(Hex possibleTo : targetHex.getUnOccupiedNeighbours()){
				if(from.distanceTo(possibleTo) < bestDist){
					bestDist = from.distanceTo(possibleTo);
					to = possibleTo;
				}	
			}
			

			List<Hex> path = super.aStar(from, to);
			if(path != null){
			System.out.println("PATH IS: " + path.toString());
			u.move(path.get(path.size()-2));
			//System.out.println("moving to hex: " + path.get(0));
			super.movelist.addToHexList(from, path.get(path.size()-2));
			super.movelist.addToTypeList("move");
			}
			
		}
		
		//loop for planning attacks
		for(Unit u : super.board.cpuUnits){
			//System.out.println("Planning attack for unit: " + u.toString());
			List<Unit> adjacentEnemies = u.getAdjacentEnemies();
			
			Unit target;
			
			if(adjacentEnemies.size() > 0){
				target = adjacentEnemies.get(0);
				double bestHitChance = getHitChance(u, target);
				double hitChance = getHitChance(u, target);
				for(Unit t : adjacentEnemies){
					hitChance = getHitChance(u, t);
					if(hitChance > bestHitChance){
						bestHitChance = hitChance;
					}
				}
				
				super.movelist.addToHexList(u.getPosition(), target.getPosition());
				super.movelist.addToTypeList("attack");
			}
			//System.out.println("TARGET is: " + target.toString());
		}
	}
	
	public double getHitChance(Unit attacker, Unit target){
		return 1/(1+Math.pow(Math.E, (-0.4* ((attacker.weaponSkill + attacker.weaponSkillModifier)-(target.weaponSkill + target.weaponSkillModifier)))));
	}
}
