import java.util.*;
import java.io.*;

public class ShopMenu{
	private Scanner sc;
	private Character characterLoggedIn;
	private GameControl gameCtrl;

	public ShopMenu(GameControl gameCtrl,Character characterLoggedIn){
		sc = new Scanner(System.in);
		this.characterLoggedIn = characterLoggedIn;
		this.gameCtrl = gameCtrl;
	}

	public void displayMenu(){
		System.out.println();
        System.out.println("== BattleStations :: Le Shippe Shoppe ==");
        System.out.println("1. Le Shipyard");
        System.out.println("2. Le Armory");
        System.out.println("3. Le Part");
        System.out.println();
        System.out.print("[R]eturn to main | Enter shop number > ");
	}

	public void readOption() throws DataException{
		String input = null;
		do {
			displayMenu();
			try {
				input = sc.next();
				sc.nextLine();
				if (input.length()!=1){
					System.out.println("Invalid input!");
				} else if (input.equals("R")){
				} else if (input.equals("1")){
					processShipShopping();
				} else if (input.equals("2")){
					processWeaponShopping();
				} else if (input.equals("3")){
					processPartShopping();
				} else {
					System.out.println("Please enter a valid option!");
				}
			} catch (NoSuchElementException e){
				System.out.println("No input! Please try again!");
			}
		} while (!input.equals("R"));
	}

	public void processShipShopping() throws DataException {
		ShipMenu shipMenu = new ShipMenu(gameCtrl,characterLoggedIn);
		shipMenu.readShipOption();
	}

	public void processPartShopping() throws DataException{
		PartMenu partMenu = new PartMenu(gameCtrl,characterLoggedIn);
		partMenu.readPartTypeOption();
	}

	public void processWeaponShopping()throws DataException{
		WeaponMenu wpnMenu = new WeaponMenu(gameCtrl,characterLoggedIn);
		wpnMenu.readWeaponTypeOption();
	}
}