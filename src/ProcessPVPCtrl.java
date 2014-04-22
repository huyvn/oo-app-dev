import java.util.*;

public class ProcessPVPCtrl{
	private Character character;
	private CharacterManager charMgr;
	private static int attackerTotalDamage;
	private static int defenderTotalDamage;

	public ProcessPVPCtrl(Character character, CharacterManager charMgr){
		this.character= character;
		this.charMgr = charMgr;
		attackerTotalDamage = 0;
		defenderTotalDamage =0;
	}

	public int getAttackerTotalDamage(){
		return attackerTotalDamage;
	}

	public int getDefenderTotalDamage(){
		return defenderTotalDamage;
	}

	public ArrayList<Character> retrieveAvailableCharacter(){
		ArrayList<Character> characterList = charMgr.retrieveAll();
		ArrayList<Character> listToReturn = new ArrayList<Character>();
		for (int i=0; i<characterList.size();i++){
			Character c = characterList.get(i);
			int lvl = character.getLvl();
			int lvlAnother = c.getLvl();
			int difference = Math.abs(lvl - lvlAnother);
			if (c != character && difference <= 5 && c.getShip() != null && c.getShip().getRealHP() > 0){
				listToReturn.add(c);
			}
		}
		return listToReturn;
	}

	public Character retrieveCharacter(int position){
		ArrayList<Character> list = retrieveAvailableCharacter();
		Character characterToReturn = null;
		if (position >0 && position <= list.size()){
			characterToReturn = list.get(position-1);
		}
		return characterToReturn;
	}

	public double getTimeToCross(Character another, int distanceMark){
		int speedAnother = another.getShip().getSpeed();
		double time = ((double)(distanceMark - 1000)/speedAnother);
		return time;
	}

	public double getTimeToFire(Character another, int maxRange){
		int attackerSpeed = character.getShip().getSpeed();
		int defenderSpeed = another.getShip().getSpeed();
		double time = (double)(maxRange-1000)/(defenderSpeed- attackerSpeed);
		return time;
	}

	public double getDistance(Character another, int distanceMark){
		double time = getTimeToCross(another,distanceMark);
		double attackerPosition = character.getShip().getSpeed() * time;
		double defenderPosition = distanceMark;
		double difference = defenderPosition - attackerPosition;
		if (difference < 50){
			return 50;
		}
		else {
			return difference;
		}
	}

	public int getDamageDealt(Weapon w){
		int maxDamage = w.getMaxDamage();
		int minDamage = w.getMinDamage();
		int interval = Math.abs(maxDamage - minDamage);
		Random rdmGenerator = new Random();
		int damageDealt = minDamage + rdmGenerator.nextInt(interval +1);
		return damageDealt;
	}

	private double getAttackerMultiplier(Character defender){
		int lvlDifference = character.getLvl() - defender.getLvl();
		if (lvlDifference >= -2 && lvlDifference <=2){
			return 1.0;
		}
		else {
			double multiplier = 0;
			switch (lvlDifference){
				case -5:
					multiplier =1.3;
					break;
				case -4 :
					multiplier =1.2;
					break;
				case -3 :
					multiplier =1.1;
					break;
				case 3 :
					multiplier =0.9;
					break;
				case 4 :
					multiplier = 0.8;
					break;
				case 5:
					multiplier =0.7;
					break;
			}
			return multiplier;
		}
	}

	private double getDefenderMultiplier(Character defender){
		double attackerMultiplier = getAttackerMultiplier(defender);
		double defenderMultiplier = 2.0 - attackerMultiplier;
		return defenderMultiplier;
	}

	public int getAttackerCombatEXP(Character another, int totalDamage){
		double multiplier = getAttackerMultiplier(another);
		int combatEXP = (int)Math.round((totalDamage/300.0)*character.getLvl()*5*multiplier);
		return combatEXP;
	}

	public int getDefenderCombatEXP(Character another, int totalDamage){
		double multiplier = getDefenderMultiplier(another);
		int combatEXP = (int)Math.round((totalDamage/300.0)*another.getLvl()*5*multiplier);
		return combatEXP;
	}

	public int getAttackerCombatGold(Character another, int totalDamage){
		double multiplier = getAttackerMultiplier(another);
		int combatGold = (int)Math.round((totalDamage/300.0)*character.getLvl()*multiplier);
		return combatGold;
	}

	public int getDefenderCombatGold(Character another, int totalDamage){
		double multiplier = getDefenderMultiplier(another);
		int combatGold = (int)Math.round((totalDamage/300.0)*another.getLvl()*multiplier);
		return combatGold;
	}


	public int doPVP(Character another, int distanceMark) throws DataException{
		attackerTotalDamage =0;
		defenderTotalDamage =0;
		double time;
		double distance = getDistance(another,distanceMark);
		Ship defendShip = another.getShip();
		Ship attackShip = character.getShip();
		int maxRange = attackShip.getMaxRange();
		if (maxRange < distance){
			time = getTimeToFire(another,maxRange);
		} else {
			time = getTimeToCross(another,distanceMark);
		}
		double attackerPosition = attackShip.getSpeed() * Math.abs(time);
		double defenderPosition = defendShip.getSpeed() * Math.abs(time);
		if (defenderPosition >= (distanceMark + 2500)){
			return 0;
		}
		if (attackerPosition >= defenderPosition){
			attackerPosition = defenderPosition - 50;
		}
		ArrayList<Weapon> attackerWpns = attackShip.getWeaponList();
		ArrayList<Weapon> defenderWpns = defendShip.getWeaponList();
		for (int i=0;i<attackerWpns.size();i++){
			Weapon w = attackerWpns.get(i);
			if (w.getRange() >= maxRange){
				int damageDealt = getDamageDealt(w);
				attackerTotalDamage += damageDealt;
				System.out.println(character.getUsername() + " attacks with " + w.getName() + " at " + Math.round(attackerPosition)
					+ " m (" + damageDealt+" damage)" );
			}
		}
		for (int i =0; i<defenderWpns.size(); i++){
			Weapon w = defenderWpns.get(i);
			if (w.getRange() >= maxRange){
				int damageDealt = getDamageDealt(w);
				defenderTotalDamage += damageDealt;
				System.out.println(another.getUsername() + " attacks with " + w.getName() + " at " + Math.round(defenderPosition)
					+ " m (" + damageDealt+" damage)" );
			}
		}
		System.out.println();
		attackShip.getDamaged(defenderTotalDamage);
		defendShip.getDamaged(attackerTotalDamage);
		charMgr.update(character);
		charMgr.update(another);
		if (attackShip.isSunk() && !defendShip.isSunk()){
			return -1;
		}
		else if (!attackShip.isSunk() && defendShip.isSunk()){
			return 1;
		}
		else {
			return 0;
		}
	}


	public void updateCharacter(Character character) throws DataException{
		charMgr.update(character);
	}
}