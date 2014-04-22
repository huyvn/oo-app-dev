import java.util.*;

public class PVPMenu{
	private Scanner sc;
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private ProcessPVPCtrl pvpCtrl;

	public PVPMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl = gameCtrl;
		this.characterLoggedIn = characterLoggedIn;
		sc = new Scanner(System.in);
		pvpCtrl = gameCtrl.createProcessPVPCtrl(characterLoggedIn);
	}

	public void displayMenu(){
		ArrayList<Character> availableList = pvpCtrl.retrieveAvailableCharacter();
		System.out.println("== BattleStations :: PVP ==");
		if (availableList.size()==0){
			System.out.println("There is no available opponent for you!");
			System.out.println("[R]eturn to main > ");
		}
		else {
			for (int i=0; i<availableList.size(); i++){
				Character c = availableList.get(i);
				String status ="";
				if (c.getShip().getRealHP() < c.getShip().getHP()){
					status = "Damaged";
				}
				else {
					status = "Perfect";
				}
				System.out.println((i+1) + ". " + c.getUsername() + " [" + c.getLvl() + "] - " + status);
			}
			System.out.print("[R]eturn to main | Attack (1-" + availableList.size() + ") > ");
		}
	}

	public void readOption() throws DataException{
		String input = null;
		do {
			displayMenu();
			input = sc.nextLine();
			if (!input.equals("R")){
				try {
					int choice = Integer.parseInt(input);
					Character opponent = pvpCtrl.retrieveCharacter(choice);
					if (opponent == null){
						System.out.println("Please enter a valid number!");
					}
					else {
						System.out.println();
						processPVP(opponent);
					}
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void processPVP(Character opponent)throws DataException{
		Ship attackShip = characterLoggedIn.getShip();
		Ship defendShip = opponent.getShip();
		int distanceMark =0;
		int result = 0;
		int totalAttackerDamage = 0;
		int totalDefenderDamage = 0;
		while ( result ==0 && distanceMark < 7500){
			characterLoggedIn.deductAP(8);
			System.out.println(characterLoggedIn.getUsername() + "'s HP: " + attackShip.getRealHP()+ "/" + attackShip.getHP());
			System.out.println(opponent.getUsername() + "'s HP: " + defendShip.getRealHP()+ "/" + defendShip.getHP());
			distanceMark += 2500;
			result = pvpCtrl.doPVP(opponent,distanceMark);
			totalAttackerDamage += pvpCtrl.getAttackerTotalDamage();
			totalDefenderDamage += pvpCtrl.getDefenderTotalDamage();
			System.out.println();
		}

		int attackerCombatGold = pvpCtrl.getAttackerCombatGold(opponent,totalAttackerDamage);
		int attackerSinkingGold = 0;
		int attackerCombatEXP = pvpCtrl.getAttackerCombatEXP(opponent,totalAttackerDamage);
		int attackerSinkingEXP = 0;
		int defenderCombatGold = pvpCtrl.getDefenderCombatGold(opponent,totalDefenderDamage);
		int defenderSinkingGold = 0;
		int defenderCombatEXP = pvpCtrl.getDefenderCombatEXP(opponent,totalDefenderDamage);
		int defenderSinkingEXP = 0;
		switch (result){
			case 0 :
				System.out.println("It is a draw!");
				break;
			case 1:
				System.out.println("It is your win!");
				characterLoggedIn.wins();
				opponent.loses();
				attackerSinkingGold = (int)Math.round(1.5*attackerCombatGold);
				attackerSinkingEXP = (int)Math.round(1.5*attackerCombatEXP);
				break;
			case -1:
				System.out.println("It is your loss!");
				characterLoggedIn.loses();
				opponent.wins();
				defenderSinkingGold = (int)Math.round(1.5*defenderCombatGold);
				defenderSinkingEXP = (int)Math.round(1.5*defenderCombatEXP);
				break;
		}
		System.out.println(characterLoggedIn.getUsername() + "'s HP:" +attackShip.getRealHP()+ "/" + attackShip.getHP() );
		System.out.println(opponent.getUsername() + "'s HP: " + defendShip.getRealHP()+ "/" + defendShip.getHP());
		System.out.println(characterLoggedIn.getUsername() + " gained " + attackerCombatGold + " gold.");
		System.out.println(opponent.getUsername() + " gained " + defenderCombatGold+ " gold.");
		System.out.println(characterLoggedIn.getUsername() + " gained " + attackerCombatEXP + " exp.");
		System.out.println(opponent.getUsername() + " gained " + defenderCombatEXP+ " exp.");
		characterLoggedIn.gainGold(attackerCombatGold + attackerSinkingGold);
		characterLoggedIn.gainEXP(attackerCombatEXP + attackerSinkingEXP);
		characterLoggedIn.levelUp();
		opponent.gainGold(defenderCombatGold + defenderSinkingGold);
		opponent.gainEXP(defenderCombatEXP + defenderSinkingEXP);
		opponent.levelUp();
		pvpCtrl.updateCharacter(characterLoggedIn);
		pvpCtrl.updateCharacter(opponent);
	}
}