import java.util.*;
import java.io.*;

public class MainMenu{
	private GameControl gameCtrl;
	private Character characterLoggedIn;
	private Scanner sc;

	public MainMenu(GameControl gameCtrl, Character characterLoggedIn){
		this.gameCtrl = gameCtrl;
		this.characterLoggedIn = characterLoggedIn;
		sc = new Scanner(System.in);
	}

	public void displayMenu(){
		System.out.println();
        System.out.println("== BattleStations :: Main Menu ==");
        System.out.println("Captain: " + characterLoggedIn.getUsername() + " [L"+ characterLoggedIn.getLvl()+ "]");
        if (characterLoggedIn.getShip() != null){
            System.out.println("AP: " + characterLoggedIn.getAP() + "\t\tHP: " +characterLoggedIn.getShip().getRealHP() +
        	   "/" + characterLoggedIn.getShip().getHP());
        } else {
            System.out.println("AP: " + characterLoggedIn.getAP() + "\t\tHP: 0/0");
        }
        System.out.println("Gold: " + characterLoggedIn.getGold() + "\tWood: " + characterLoggedIn.getWood());
        System.out.println("Ore: " + characterLoggedIn.getOre() + "\t\tProck: " + characterLoggedIn.getPlasmaRock());
        System.out.println("1. View my vital statistics");
        System.out.println("2. My Hangar");
        System.out.println("3. Le Shippe Shoppe");
        System.out.println("4. PVP");
        System.out.println("5. Exit");
        System.out.print("Enter your choice > ");
	}

	public void readOption() throws DataException{
		int choice = 0;
		do {
			displayMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        processViewStatistics();
                        break;
                    case 2:
                        processViewHangar();
                        break;
                    case 3:
                    	processViewShop();
                    	break;
                    case 4:
                    	processPVP();
                    	break;
                    case 5:
                        System.out.println();
                    	System.out.println("== Exit ==");
                        System.out.println("Bye bye!!!");
                    	break;
                    default:
                        System.out.println("Invalid Input! Please try again.");
                }
            } catch (InputMismatchException e) {
                // display error message
                System.out.println("Please enter a valid option!");
                sc.nextLine();
            }
		} while (choice != 5);
	}

	public void processViewStatistics() throws DataException{
        ViewStatisticsMenu viewStatsMenu = new ViewStatisticsMenu(gameCtrl,characterLoggedIn);
        viewStatsMenu.readOption();
	}

	public void processViewHangar() throws DataException{
        HangarMenu hangarMenu = new HangarMenu(gameCtrl,characterLoggedIn);
        hangarMenu.readOption();
	}

	public void processViewShop() throws DataException {
        ShopMenu shopMenu = new ShopMenu(gameCtrl,characterLoggedIn);
        shopMenu.readOption();
	}

	public void processPVP() throws DataException{
        PVPMenu pvpMenu = new PVPMenu(gameCtrl,characterLoggedIn);
        pvpMenu.readOption();
	}

}