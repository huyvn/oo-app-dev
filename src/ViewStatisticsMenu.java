import java.util.*;
import java.io.*;

public class ViewStatisticsMenu{
	private GameControl gameCtrl;
	private ProcessViewStatisticsCtrl viewStatsCtrl;
	private Character character;
	private Scanner sc;

	public ViewStatisticsMenu(GameControl gameCtrl, Character character){
		this.gameCtrl = gameCtrl;
		viewStatsCtrl = gameCtrl.createProcessViewStatisticsCtrl(character);
		this.character = character;
		sc = new Scanner(System.in);
	}

	public void displayMenu(){
		System.out.println();
        System.out.println("== BattleStations :: Captain ==");
        System.out.println("Craft: " + character.getCraftPoint());
        if (character.getShip() != null) {
        	System.out.println("Gunnery: " + character.getGunneryPoint() + "\tSpeed: " + character.getShip().getSpeed() +
        		" (+" + (int)character.getNavigationPoint()+ ")");
        } else {
        	System.out.println("Gunnery: " + character.getGunneryPoint() + "\tSpeed: 0" +
        		" (+" + (int)character.getNavigationPoint()+ ")");
        }
        System.out.println("Navigation: " + character.getNavigationPoint() + "\tStats Pts: " + character.getFreeStatsPoint());
        System.out.println("Wins: " + character.getNumberOfWin() + "\t\tLosses: " + character.getNumberOfLoss());
        System.out.println("Total EXP: " + character.getEXP());
        System.out.println("Joined on: " + character.getJoinedDate().toString() );
        System.out.print("[R]eturn to main | [A]llocate Stat Pts > ");
	}

	public void readOption() throws DataException{
		char choice = 0;
		do {
			displayMenu();
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
	                    case 'A':
	                        processAllocateStatsPoints();
	                        break;
	                    default:
	                        System.out.println("Invalid Input! Please try again.");
	                }
	            }
            } catch (InputMismatchException e) {
                // display error message
                System.out.println("Please enter a valid option!");
            }

		} while (choice != 'R');
	}

	public void processAllocateStatsPoints() throws DataException{
		try {
			System.out.println();
			System.out.println("== BattleStations :: Captain ==");
			System.out.print("Gunnery (+) > ");
			double gunnery = sc.nextDouble();
			sc.nextLine();
			System.out.print("Craft (+) > ");
			double craft = sc.nextDouble();
			sc.nextLine();
			System.out.print("Navigation (+) > ");
			double navigation = sc.nextDouble();
			sc.nextLine();

			boolean check = viewStatsCtrl.allocateStats(gunnery,craft,navigation);
			if (!check){
				System.out.println("You have enter wrong value!");
			} else {
				System.out.println("Successful!");
			}
		} catch (InputMismatchException e){
			System.out.println("Please enter a valid option!");
		}
	}
}