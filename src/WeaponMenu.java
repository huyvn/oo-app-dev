import java.util.*;

public class WeaponMenu{
	private Scanner sc;
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private ProcessWeaponCtrl wpnCtrl;

	public WeaponMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl = gameCtrl;
		this.characterLoggedIn = characterLoggedIn;
		this.wpnCtrl = gameCtrl.createProcessWeaponCtrl(this.characterLoggedIn);
		sc = new Scanner(System.in);
	}

	public void displayTypeMenu(){
		System.out.println();
		System.out.println("== BattleStations :: Le Armory ==");
		System.out.println("1. Cannons");
		System.out.println("2. Subcannons");
		System.out.println("3. Missiles");
		System.out.println("4. Melee");
		System.out.print("[R]eturn to main | Enter number >");
	}

	public void readWeaponTypeOption() throws DataException{
		String input = null;
		do {
			displayTypeMenu();
			input = sc.next();
			sc.nextLine();
			if (input.length() != 1){
				System.out.println("Invalid input!");
			} else if (input.equals("R")){
			} else {
				try {
					int choice = Integer.parseInt(input);
					String type = null;
					switch (choice) {
						case 1:
							type = "cannon";
							break;
						case 2:
							type = "subcannon";
							break;
						case 3:
							type = "missile";
							break;
						case 4:
							type = "melee";
							break;
						default:
							System.out.println("Invalid input! Please try again!");
					}
					if (type != null){
						readWeaponOption(type);
					}
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void displayWeaponMenu(String type)throws DataException{
		ArrayList<Weapon> list = wpnCtrl.retrieveAvailableWeaponsByType(type);
		System.out.println();
		System.out.println("== BattleStations :: Le Armory :: "+ type+" ==");
		for (int i=0;i<list.size();i++){
			Weapon w = list.get(i);
			System.out.println((i+1) +". " + w.getName() + " (min: L" + w.getLvlReq() + ")");
		}
		System.out.print("[R]eturn to main | Enter number > ");
	}

	public void readWeaponOption(String type) throws DataException{
		String input = null;
		do {
			displayWeaponMenu(type);
			input = sc.next();
			sc.nextLine();
			if (input.length() != 1 && input.length() !=2){
				System.out.println("Invalid input!");
			} else if (input.equals("R")){
			} else {
				try {
					int choice = Integer.parseInt(input);
					Weapon w = wpnCtrl.retrieveWeapon(type,choice); //clone part here
					readBuyOption(w);
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void displayBuyMenu(Weapon w)throws DataException{
		System.out.println();
		System.out.println("== BattleStations :: Le Armory :: Details ==");
		System.out.println("Weapon: "+w.getName());
		System.out.println();
		System.out.println("Range: "+w.getRange());
		System.out.println("Min Damage: "+w.getBaseMinDamage());
		System.out.println("Max Damage: "+w.getBaseMaxDamage());
		System.out.println("Weight: " + w.getWeight());
		System.out.println("Level Required: " + w.getLvlReq());
		System.out.println();
		System.out.println("Gold: " + w.getGoldReq() + " | Wood: " + w.getWoodReq() + " | Ore: " + w.getOreReq() +
			" | Prock: " + w.getPlasmaRockReq());
		System.out.println();
		System.out.print("[R]eturn to main | [B]uy it > ");
	}

	public void readBuyOption(Weapon w)throws DataException{
		char choice = 0;
		do {
			displayBuyMenu(w);
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
	                    case 'B':
	                        processBuyWeapon(w);
	                        break;
	                    default:
	                        System.out.println("Invalid Input! Please try again!");
	                }
	                break;
	            }
			} catch (InputMismatchException e) {
                // display error message
                System.out.println("Please enter a valid option!");
            }
		} while (choice != 'R');
	}

	public void processBuyWeapon(Weapon w)throws DataException{
		boolean check = wpnCtrl.buyWeapon(w);
		if (!check){
			System.out.println("You don't have the required level/resources to buy this part! Please come back later.");
		}
		else {
			System.out.println("Purchase successfully!");
		}
	}
}