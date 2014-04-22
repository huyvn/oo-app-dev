import java.util.*;

public class PartMenu{
	private Scanner sc;
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private ProcessPartCtrl partCtrl;

	public PartMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl = gameCtrl;
		this.characterLoggedIn = characterLoggedIn;
		this.partCtrl = gameCtrl.createProcessPartCtrl(this.characterLoggedIn);
		sc = new Scanner(System.in);
	}

	public void displayTypeMenu(){
		System.out.println();
		System.out.println("== BattleStations :: Le Part ==");
		System.out.println("1. Engines");
		System.out.println("2. Figureheads");
		System.out.println("3. Hulls");
		System.out.println("4. Sails");
		System.out.println("5. Stabilizers");
		System.out.print("[R]eturn to main | Enter number >");
	}

	public void readPartTypeOption() throws DataException{
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
							type = "engine";
							break;
						case 2:
							type = "figurehead";
							break;
						case 3:
							type = "hull";
							break;
						case 4:
							type = "sail";
							break;
						case 5:
							type = "stabilizer";
							break;
						default:
							System.out.println("Invalid input! Please try again!");
					}
					if (type != null){
						readPartOption(type);
					}
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void displayPartMenu(String type)throws DataException{
		ArrayList<Part> list = partCtrl.retrieveAvailablePartsByType(type);
		System.out.println();
		System.out.println("== BattleStations :: Le Part :: "+ type+" ==");
		for (int i=0;i<list.size();i++){
			Part p = list.get(i);
			System.out.println((i+1) +". " + p.getName() + " (min: L" + p.getLvlReq() + ")");
		}
		System.out.print("[R]eturn to main | Enter number > ");
	}

	public void readPartOption(String type) throws DataException{
		String input = null;
		do {
			displayPartMenu(type);
			input = sc.next();
			sc.nextLine();
			if (input.length() != 1 && input.length() !=2){
				System.out.println("Invalid input!");
			} else if (input.equals("R")){
			} else {
				try {
					int choice = Integer.parseInt(input);
					Part p = partCtrl.retrievePart(type,choice); //clone part here
					readBuyOption(p);
				} catch (InputMismatchException e){
					System.out.println("Invalid input! Please try again!");
				}
			}
		} while (!input.equals("R"));
	}

	public void displayBuyMenu(Part p)throws DataException{
		System.out.println();
		System.out.println("== BattleStations :: Le Part :: Details ==");
		System.out.println("Part: "+p.getName());
		System.out.println();
		System.out.println("Speed: "+p.getSpeed());
		System.out.println("HP: "+p.getHP());
		System.out.println("Capacity: "+p.getCapacity());
		System.out.println("Weight: " + p.getWeight());
		System.out.println("Level Required: " + p.getLvlReq());
		System.out.println();
		System.out.println("Gold: " + p.getGoldReq() + " | Wood: " + p.getWoodReq() + " | Ore: " + p.getOreReq() +
			" | Prock: " + p.getPlasmaRockReq());
		System.out.println();
		System.out.print("[R]eturn to main | [B]uy it > ");
	}

	public void readBuyOption(Part p)throws DataException{
		char choice = 0;
		do {
			displayBuyMenu(p);
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
	                        processBuyPart(p);
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

	public void processBuyPart(Part p)throws DataException{
		boolean check = partCtrl.buyPart(p);
		if (!check){
			System.out.println("You don't have the required level/resources to buy this part! Please come back later.");
		}
		else {
			System.out.println("Purchase successfully!");
		}
	}
}