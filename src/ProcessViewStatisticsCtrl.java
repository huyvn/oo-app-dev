import java.util.*;

public class ProcessViewStatisticsCtrl{
	private Character characterLoggedIn;
	private CharacterManager charMgr;
	private InventoryManager inventoryMgr;

	public ProcessViewStatisticsCtrl(Character characterLoggedIn,CharacterManager charMgr,InventoryManager inventoryMgr){
		this.characterLoggedIn = characterLoggedIn;
		this.charMgr = charMgr;
		this.inventoryMgr = inventoryMgr;
	}

	public boolean allocateStats(double gunnery, double craft, double navigation) throws DataException{
		try {
			int checkDouble = Double.compare(gunnery+craft+navigation,characterLoggedIn.getFreeStatsPoint());
			if ( checkDouble >0 ){
				return false;
			}
			else {
				characterLoggedIn.addGunnery(gunnery);
				characterLoggedIn.addCraft(craft);
				characterLoggedIn.addNavigation(navigation);
				characterLoggedIn.deductFreeStatsPoint(gunnery+craft+navigation);

				Ship ship = characterLoggedIn.getShip();
				if (ship!= null){
					ship.increaseStats(characterLoggedIn.getCraftPoint(),characterLoggedIn.getNavigationPoint());
					ArrayList<Weapon> wpnList = inventoryMgr.retrieveWeaponInventory(characterLoggedIn.getUsername());
					for (int i=0; i<wpnList.size(); i++){
						Weapon w = wpnList.get(i);
						w.increaseDamage(characterLoggedIn.getGunneryPoint(),characterLoggedIn.getNavigationPoint());
					}
				}
				charMgr.update(characterLoggedIn);
				return true;
			}
		} catch (DataException e){
			throw e;
		}
	}
}