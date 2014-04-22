public class BattleStations{

	public static void main (String [] args){
		try {
			GameControl gameCtrl = new GameControl();
			WelcomeMenu wlcMenu = new WelcomeMenu(gameCtrl);
			wlcMenu.readOption();
		} catch (DataException e) {
			//System.out.println(e.getMessage());
            System.out.println("We apologize, the application must terminate due to a system error.");
            System.exit(1);
        }
	}
}