import java.util.*;

public class HangarMenu{
	private Scanner sc;
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private ProcessEquipCtrl equipCtrl;
	private ProcessRepairCtrl repairCtrl;

	public HangarMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl=gameCtrl;
		this.characterLoggedIn = characterLoggedIn;
		equipCtrl = gameCtrl.createProcessEquipCtrl(characterLoggedIn);
		repairCtrl = gameCtrl.createProcessRepairCtrl(characterLoggedIn);
		sc = new Scanner(System.in);
	}

	public void displayMenu(Ship s){
		System.out.println();
		System.out.println("== BattleStations :: Hangar ==");
		if (s.isSunk()){
			System.out.println("Your ship has been sunk. You need 10 AP to salvage.");
		}
		System.out.println();
		System.out.println("Parts");

		System.out.print("[F]igurehead: ");
		Part figurehead = s.getFigurehead();
		if (figurehead != null){
			System.out.println("L"+figurehead.getLvlReq() + " - " + figurehead.getName());
		} else {
			System.out.println();
		}
		System.out.print("[S]ail: ");
		Part sail = s.getSail();
		if (sail != null){
			System.out.println("L"+sail.getLvlReq() + " - " + sail.getName());
		} else {
			System.out.println();
		}
		System.out.print("S[t]abilizer: ");
		Part stabilizer = s.getStabilizer();
		if (stabilizer != null){
			System.out.println("L"+stabilizer.getLvlReq() + " - " + stabilizer.getName());
		} else {
			System.out.println();
		}
		System.out.print("[H]ull: ");
		Part hull = s.getHull();
		if (hull != null){
			System.out.println("L"+hull.getLvlReq() + " - " + hull.getName());
		} else {
			System.out.println();
		}
		System.out.print("[E]ngine: ");
		Part engine = s.getEngine();
		if (engine != null){
			System.out.println("L"+engine.getLvlReq() + " - " + engine.getName());
		} else {
			System.out.println();
		}
		System.out.println();
		System.out.println("Weapon");
		ArrayList<Weapon> wpnList = s.getWeaponList();
		for (int i=0; i<s.getSlot(); i++){
			System.out.print("[W" +(i+1)+"]");
			if (i<wpnList.size()){
				Weapon w = wpnList.get(i);
				System.out.println("L" + w.getLvlReq() + " - " + w.getName());
			}
			else {
				System.out.println();
			}
		}

		System.out.println("Capacity: " + s.getRealCapacity() + "/" + s.getCapacity());
		System.out.println("Speed: "+ s.getSpeed());
		System.out.println("HP: " + s.getRealHP() + "/" + s.getHP());
		System.out.print("[R]eturn to main | [E]quip | [U]nequip | R[e]pair > ");
	}

	public void readOption() throws DataException {
		Ship s = characterLoggedIn.getShip();
		if (s == null){
			System.out.println("You don't have a ship yet!");
		}
		else {
			char choice = 0;
			do {
				displayMenu(s);
				try {
					String input = sc.next();
					sc.nextLine();
				 	choice = input.trim().charAt(0);
	                if (input.length() != 1){
	                	System.out.println("Invalid input!");
	                } else{
		                switch (choice) {
		                    case 'R':
		                        break;
		                    case 'E':
		                        readEquipOption();
		                        break;
		                    case 'U':
		                    	readUnequipOption(s);
		                    	break;
		                    case 'e':
		                    	readRepairOption(s);
		                    	break;
		                    default:
		                        System.out.println("Invalid Input! Please try again!");
		                }
		            }
				} catch (InputMismatchException e) {
	                // display error message
	                System.out.println("Please enter a valid option!");
	            }
			} while (choice != 'R');
		}
	}

	public void displayEquipMenu(){
		System.out.println();
		System.out.println("== BattleStations :: Storage ==");

		System.out.println("Figureheads:");
		ArrayList<Part> figureheadList = equipCtrl.getAvailablePartType("figurehead");
		if (figureheadList.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<figureheadList.size(); i++){
				Part p = figureheadList.get(i);
				System.out.println("[F" + (i+1) +"] L"+ p.getLvlReq() +" - "+ p.getName() );
			}
		}
		System.out.println();

		System.out.println("Sails:");
		ArrayList<Part> sailList = equipCtrl.getAvailablePartType("sail");
		if (sailList.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<sailList.size(); i++){
				Part p = sailList.get(i);
				System.out.println("[S" + (i+1) +"] L"+ p.getLvlReq() +" - "+ p.getName() );
			}
		}
		System.out.println();

		System.out.println("Stabilizers:");
		ArrayList<Part> stabilizerList = equipCtrl.getAvailablePartType("stabilizer");
		if (stabilizerList.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<stabilizerList.size(); i++){
				Part p = stabilizerList.get(i);
				System.out.println("[t" + (i+1) +"] L"+ p.getLvlReq() +" - "+ p.getName() );
			}
		}
		System.out.println();

		System.out.println("Hulls:");
		ArrayList<Part> hullList = equipCtrl.getAvailablePartType("hull");
		if (hullList.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<hullList.size(); i++){
				Part p = hullList.get(i);
				System.out.println("[H" + (i+1) +"] L"+ p.getLvlReq() +" - "+ p.getName() );
			}
		}
		System.out.println();

		System.out.println("Engines:");
		ArrayList<Part> engineList = equipCtrl.getAvailablePartType("engine");
		if (engineList.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<engineList.size(); i++){
				Part p = engineList.get(i);
				System.out.println("[E" + (i+1) +"] L"+ p.getLvlReq() +" - "+ p.getName() );
			}
		}
		System.out.println();

		System.out.println("Weapons:");
		ArrayList<Weapon> availableWeapons = equipCtrl.getAvailableWeapon();
		if (availableWeapons.size() == 0){
			System.out.println("NIL");
		}
		else {
			for(int i=0; i<availableWeapons.size(); i++){
				Weapon w = availableWeapons.get(i);
				System.out.println("[W" + (i+1) +"] L"+ w.getLvlReq() +" - "+ w.getName() );
			}
		}
		System.out.println();
		System.out.print("[R]eturn to main | Enter weapon/part >");
	}

	public void readEquipOption() throws DataException{
		String input = null;
		do {
			displayEquipMenu();
			input = sc.nextLine();
			if (!input.equals("R")){
				char type = input.charAt(0);
				if (type != 'F' && type !='S' && type != 't' && type != 'H' && type != 'E' && type != 'W'){
					System.out.println("Invalid input!");
				}
				else {
					String positionStr = input.substring(1);
					try {
						int position = Integer.parseInt(positionStr);
						if (type =='W'){
							processEquipWeapon(position);
						}
						else {
							processEquipPart(type,position);
						}
					} catch (InputMismatchException e){
						System.out.println("Invalid input! Please try again!");
					}
				}
			}
		} while (!input.equals("R"));
	}

	public void processEquipWeapon(int position) throws DataException{
		boolean check = equipCtrl.equipWeapon(position);
		if (!check){
			System.out.println("You have input a wrong weapon. Please try again!");
		}
		else {
			System.out.println("Equipped Successfully!");
		}
	}

	public void processEquipPart(char type, int position)throws DataException{
		String typeStr= null;
		switch (type){
			case 'F':
				typeStr = "figurehead";
				break;
			case 'S':
				typeStr = "sail";
				break;
			case 't':
				typeStr = "stabilizer";
				break;
			case 'H':
				typeStr = "hull";
				break;
			case 'E':
				typeStr = "engine";
				break;
		}
		boolean check = equipCtrl.equipPart(typeStr,position);
		if (!check){
			System.out.println("You have input a wrong part. Please try again!");
		}
		else {
			System.out.println("Equipped Successfully!");
		}
	}


	public void displayUnequipMenu(Ship s){
		System.out.println();
		System.out.print("[R]eturn to main | Enter weapon/part to be unequipped > ");
	}

	public void readUnequipOption(Ship s) throws DataException{
		String input = null;
		do {
			displayUnequipMenu(s);
			input = sc.nextLine();
			if (!input.equals("R")){
				char type = input.charAt(0);
				if (type != 'F' && type !='S' && type != 't' && type != 'H' && type != 'E' && type != 'W'){
					System.out.println("Invalid input!");
				}
				else if (type == 'W') {
					String positionStr = input.substring(1);
					try {
						int position = Integer.parseInt(positionStr);
						processUnequipWeapon(position);
					} catch (InputMismatchException e){
						System.out.println("Invalid input! Please try again!");
					}
				}
				else {
					processUnequipPart(s,type);
				}
			}
			break;
		} while (!input.equals("R"));
	}

	public void processUnequipWeapon(int position) throws DataException{
		boolean check = equipCtrl.unequipWeapon(position);
		if (!check){
			System.out.println("You have input a wrong position. Please try again!");
		}
		else {
			System.out.println("Unequipped Successfully!");
		}
	}

	public void processUnequipPart(Ship s,char type) throws DataException{
		Part p = null;
		switch (type){
			case 'F':
				p=s.getFigurehead();
				break;
			case 'S':
				p=s.getSail();
				break;
			case 't':
				p=s.getStabilizer();
				break;
			case 'H':
				p=s.getHull();
				break;
			case 'E':
				p=s.getEngine();
				break;
		}
		boolean check = equipCtrl.unequipPart(p);
		if (!check){
			System.out.println("You have input a wrong part. Please try again!");
		}
		else {
			System.out.println("Unequipped Successfully!");
		}
	}

	public void displayRepairMenu(Ship s) {
		System.out.println();
		System.out.println("== BattleStations :: Storage ==");
		System.out.println("Gold available: " + characterLoggedIn.getGold());
		System.out.println("AP: " + characterLoggedIn.getAP());
		System.out.println();
		System.out.println("Status: " + s.getRealHP() + "/" + s.getHP());
		System.out.println("Repair (Max)");
		System.out.println("[A]P: " + repairCtrl.getNeededAP(s));
		System.out.println("[G]old: " + repairCtrl.getNeededGold(s));
		System.out.println();
		System.out.print("[R]eturn to main | Enter number of AP/Gold > ");
	}

	public void readRepairOption(Ship s) throws DataException {
		String input = null;
		do {
			displayRepairMenu(s);
			input = sc.nextLine();
			if (!input.equals("R")){
				char type = input.charAt(input.length() -1);
				if (type != 'A' && type !='G'){
					System.out.println("Invalid input!");
				} else {
					String amtStr = input.substring(0,input.length()-1);
					try {
						int amt = Integer.parseInt(amtStr);
						processRepair(amt,type);
					} catch (InputMismatchException e){
						System.out.println("Invalid input! Please try again!");
					}
				}
			}
		} while (!input.equals("R"));
	}

	public void processRepair(int amt, char type) throws DataException{
		boolean check = repairCtrl.repair(characterLoggedIn,amt,type);
		if (!check){
			System.out.println("Your ship was not repaired!");
		}
		else {
			System.out.println("Repaired Successfully!");
		}
	}
}