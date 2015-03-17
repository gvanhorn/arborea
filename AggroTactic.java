import java.util.Arrays;
import java.util.List;




public class AggroTactic extends Tactic{

	AggroTactic(Board b) {
		super(b);
	}

	@Override
	void createTactic() {
		super.movelist = new MoveList();
		System.out.println("Creating moves");
		
		//loop for planning the walking moves
		for(Unit u: super.board.cpuUnits){
			
			//select a target to move to, the closest enemy unit.
			System.out.println("planning move for unit: " + u.toString());
			Unit target = super.getClosestEnemyUnit(u);
			System.out.println("Going to target: " + target.toString());
			
			//Get own and enemy position
			Hex from = u.getPosition();
			Hex targetHex = target.getPosition();
			
			//Only plan a move if you are not already next to your target and there is space next to it.
			if((!Arrays.asList(targetHex.neighbours).contains(from)) && (targetHex.getUnOccupiedNeighbours().length>0)){
				
				//Move to the closest unoccupied neighbour of the target.
				Hex to = targetHex.getUnOccupiedNeighbours()[0];
				int bestDist = from.distanceTo(to);
				for(Hex possibleTo : targetHex.getUnOccupiedNeighbours()){
					if(from.distanceTo(possibleTo) < bestDist){
						bestDist = from.distanceTo(possibleTo);
						to = possibleTo;
					}	
				}
				
				//Retrieve the path planned by A-star
				List<Hex> path = super.aStar(from, to);
				
				//If there is a path, move to the first position in the path (found in the second to last place of the list)
				if(path != null){
					System.out.println("PATH IS: " + path.toString());
					u.move(path.get(path.size()-2));
					super.movelist.addToHexList(from, path.get(path.size()-2));
					super.movelist.addToTypeList("move");
				}
			}
			
		}
		
		//loop for planning attacks
		for(Unit u : super.board.cpuUnits){
			
			//Get any adjacent enemies
			List<Unit> adjacentEnemies = u.getAdjacentEnemies();
			Unit target;
			
			//If there is at least one adjacent enemy, select one to attack
			if(adjacentEnemies.size() > 0){
				target = adjacentEnemies.get(0);
				double bestHitChance = getHitChance(u, target);
				double hitChance = getHitChance(u, target);
				
				//Select the enemy that you have the highest chance of hitting
				for(Unit t : adjacentEnemies){
					hitChance = getHitChance(u, t);
					if(hitChance > bestHitChance){
						bestHitChance = hitChance;
					}
				}
				
				//Add the attack to the movelist, to be executed later this turn.
				super.movelist.addToHexList(u.getPosition(), target.getPosition());
				super.movelist.addToTypeList("attack");
			}
		}
	}
	
	//Get the chance of attacker hitting the target.
	public double getHitChance(Unit attacker, Unit target){
		return 1/(1+Math.pow(Math.E, (-0.4* ((attacker.weaponSkill + attacker.weaponSkillModifier)-(target.weaponSkill + target.weaponSkillModifier)))));
	}
}
