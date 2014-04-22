import java.util.*;

public class ShipMenu{
	private Scanner sc;
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private ProcessShipCtrl shipCtrl;

	public ShipMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl = gameCtrl;
		this.shipCtrl = gameCtrl.createProcessShipCtrl(characterLoggedIn);
		this.characterLoggedIn = characterLoggedIn;
		sc = new Scanner(System.in);
	}

	public void displayAvailableShips(){
		System.out.println();
		System.out.println("== BattleStations :: Le Shipyard ==");
		ArrayList<Ship> shipList = shipCtrl.retrieveAvailableShips();
		for (int i=0; i<shipList.size();i++){
			Ship s = shipList.get(i);
			System.out.println((i+1) +". " + s.getName() + " (min: L" + s.getLvlReq() + ")");
		}
		System.out.print("[R]eturn to main | Enter number > ");
	}

	public void readShipOption() throws DataException {
		String input = null;
		do {
			displayAvailableShips();
			input = sc.next();
			sc.nextLine();
			if (input.length() != 1 && input.length() !=2){
				System.out.println("Invalid input!");
			} else if (input.equals("R")){
			} else {
				try {
					int choice = Integer.parseInt(input);
					Ship s =shipCtrl.retrieveShip(choice);
					readBuyOption(s);
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void displayBuyMenu(Ship s){
		System.out.println();
		System.out.println("== BattleStations :: Le Shipyard :: Details ==");
		System.out.println("Ship: "+s.getName());
		System.out.println();
		System.out.println("Speed: "+s.getSpeed());
		System.out.println("HP: "+s.getBaseHP());
		System.out.println("Slots: "+s.getSlot());
		System.out.println("Capacity: "+s.getCapacity());
		System.out.println("Level Required: " + s.getLvlReq());
		System.out.println();
		System.out.println("Gold: " + s.getGoldReq() + " | Wood: " + s.getWoodReq() + " | Ore: " + s.getOreReq() +
			" | Prock: " + s.getPlasmaRockReq());
		System.out.println();
		System.out.print("[R]eturn to main | [B]uy it > ");
	}

	public void readBuyOption(Ship s) throws DataException {
		char choice = 0;
		do {
			displayBuyMenu(s);
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
	                        processBuyShip(s);
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

	public void processBuyShip(Ship s) throws DataException{
		boolean check = shipCtrl.buyShip(s);
		if (!check){
			System.out.println("You don't have the required level/resources to buy this ship! Please come back later.");
		}
		else {
			System.out.println("Purchase successfully!");
		}
	}
}