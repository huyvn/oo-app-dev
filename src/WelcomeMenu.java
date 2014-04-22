import java.util.*;
import java.io.*;

public class WelcomeMenu{
	private GameControl gameCtrl;
	private ProcessLoginCtrl loginCtrl;
	private Scanner sc;

	public WelcomeMenu(GameControl gameCtrl){
		this.gameCtrl = gameCtrl;
		this.loginCtrl = gameCtrl.createProcessLoginCtrl();
		sc = new Scanner(System.in);
	}

	public void displayMenu(){
		System.out.println();
        System.out.println("== BattleStations :: Welcome ==");
        System.out.println();
        System.out.println("Hello, player!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter your choice > ");
	}

	public void readOption() throws DataException{
		int choice = 0;
		boolean loopAgain;
        do {
        	loopAgain = false;
            displayMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        processLogin();
                        break;
                    case 2:
                        processRegister();
                        loopAgain = true;
                        break;
                    default:
                        System.out.println("Invalid Input! Please try again.");
                        loopAgain = true;
                }
            } catch (InputMismatchException e) {
                // display error message
                System.out.println("Please enter a valid option!");
                sc.nextLine();
                loopAgain = true;
            }
        } while (loopAgain);
	}

	public void processLogin() throws DataException{
		System.out.println();
		System.out.println("== BattleStations :: Log In ==");

		System.out.print("Enter your username > ");
		String usernameEntered = sc.nextLine();

		System.out.print("Enter your password > ");
		String passwordEntered = sc.nextLine();

		Character character = loginCtrl.authenticateCharacter(usernameEntered,passwordEntered);

		if (character != null){
			MainMenu mainMenu = new MainMenu(gameCtrl,character);
			mainMenu.readOption();
		}
		else {
			System.out.println("Sorry, you entered a wrong user name and/or password!");
		}
	}

	public void processRegister() throws DataException{
		System.out.println();
		System.out.println("== BattleStations :: Registration ==");

		boolean checkError = false;
		boolean checkExist;

		System.out.print("Enter your username > ");
		String usernameEntered = sc.nextLine();

		String passwordEntered = null;
		do {
			System.out.print("Enter your password > ");
			passwordEntered = sc.nextLine();

			System.out.print("Confirm your password > ");
			String passwordConfirmEntered = sc.nextLine();

			if (!passwordConfirmEntered.equals(passwordEntered)){
				checkError = true;
				System.out.println("Password mismatched! Please try again!");
			}
		} while (checkError);

		char typeEntered = 0;
		do {
			System.out.print("(P)irate / (E)xplorer > ");
			typeEntered = sc.nextLine().charAt(0);

			if (typeEntered != 'P' && typeEntered != 'E'){
				checkError = true;
				System.out.println("Invalid input! Please try again!");
			}
		}while (checkError);

		checkExist = loginCtrl.registerCharacter(usernameEntered, passwordEntered, typeEntered);

		if (!checkExist){
			System.out.println("The username you chose is already in use! Please try again!");
		}

	}
}