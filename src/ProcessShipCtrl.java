import java.util.*;

public class ProcessShipCtrl{
	private Character characterLoggedIn;
	private CharacterManager charMgr;
	private ShipManager shipMgr;
	private InventoryManager inventoryMgr;

	public ProcessShipCtrl(Character characterLoggedIn, CharacterManager charMgr, ShipManager shipMgr, InventoryManager inventoryMgr){
		this.characterLoggedIn = characterLoggedIn;
		this.charMgr = charMgr;
		this.shipMgr = shipMgr;
		this.inventoryMgr = inventoryMgr;
	}

	public ArrayList<Ship> retrieveAvailableShips(){
		return shipMgr.retrieveAll();
	}

	public Ship retrieveShip(int position){
		ArrayList<Ship> shipList = retrieveAvailableShips();
		Ship shipToReturn = null;
		if (position >0 && position <= shipList.size()){
			shipToReturn = shipList.get(position-1);
		}
		return shipToReturn.clone();
	}

	public boolean buyShip(Ship s) throws DataException{
		if (s.getLvlReq() > characterLoggedIn.getLvl() || s.getGoldReq() > characterLoggedIn.getGold() ||
			s.getWoodReq() > characterLoggedIn.getWood() || s.getOreReq() > characterLoggedIn.getOre() ||
			s.getPlasmaRockReq() > characterLoggedIn.getPlasmaRock()) {
			return false;
		}
		else {
			Ship ship = characterLoggedIn.getShip();
			if (ship != null){
				Part engine = ship.getEngine();
				if (engine != null) {
					engine.setIsEquipped(false);
				}
				Part figurehead = ship.getFigurehead();
				if (figurehead != null) {
					figurehead.setIsEquipped(false);
				}
				Part hull = ship.getHull();
				if (hull != null) {
					hull.setIsEquipped(false);
				}
				Part sail = ship.getSail();
				if (sail != null) {
					sail.setIsEquipped(false);
				}
				Part stabilizer = ship.getStabilizer();
				if (stabilizer != null) {
					stabilizer.setIsEquipped(false);
				}
				ArrayList<Weapon> wpnList = ship.getWeaponList();
				for (int i=0; i<wpnList.size(); i++){
					Weapon w = wpnList.get(i);
					w.setIsEquipped(false);
				}
				inventoryMgr.update();
			}
			characterLoggedIn.assignShip(s);
			characterLoggedIn.deductGold(s.getGoldReq());
			characterLoggedIn.deductWood(s.getWoodReq());
			characterLoggedIn.deductOre(s.getOreReq());
			characterLoggedIn.deductPlasmaRock(s.getPlasmaRockReq());
			charMgr.update(characterLoggedIn);
			return true;
		}
	}
}