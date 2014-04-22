import java.util.*;

public class ProcessWeaponCtrl{
	private Character characterLoggedIn;
	private CharacterManager charMgr;
	private WeaponManager weaponMgr;
	private InventoryManager inventoryMgr;

	public ProcessWeaponCtrl(Character characterLoggedIn,CharacterManager charMgr,WeaponManager weaponMgr,InventoryManager inventoryMgr){
		this.characterLoggedIn = characterLoggedIn;
		this.weaponMgr = weaponMgr;
		this.charMgr = charMgr;
		this.inventoryMgr = inventoryMgr;
	}

	public ArrayList<Weapon> retrieveAvailableWeapons(){
		ArrayList<Weapon> listToReturn = new ArrayList<Weapon>();
		ArrayList<Weapon> weaponList = weaponMgr.retrieveAll();
		for (int i=0; i<weaponList.size(); i++){
			Weapon w = weaponList.get(i);
			if (w.getPort().equals("Imperial City Port")){
				listToReturn.add(w);
			}
		}
		return listToReturn;
	}

	public ArrayList<Weapon> retrieveAvailableWeaponsByType(String type){
		ArrayList<Weapon> listToReturn = new ArrayList<Weapon>();
		ArrayList<Weapon> typeList = weaponMgr.retrieveList(type);
		for (int i=0; i<typeList.size(); i++){
			Weapon w = typeList.get(i);
			if (w.getPort().equals("Imperial City Port")){
				listToReturn.add(w);
			}
		}
		return listToReturn;
	}

	public Weapon retrieveWeapon(String type, int position){
		ArrayList<Weapon> weaponList = retrieveAvailableWeaponsByType(type);
		Weapon weaponToReturn = null;
		if (position >0 && position <= weaponList.size()){
			weaponToReturn = weaponList.get(position-1);
		}
		return weaponToReturn.clone();
	}

	public boolean buyWeapon(Weapon w) throws DataException{
		if (w.getLvlReq() > characterLoggedIn.getLvl() || w.getGoldReq() > characterLoggedIn.getGold() ||
			w.getWoodReq() > characterLoggedIn.getWood() || w.getOreReq() > characterLoggedIn.getOre() ||
			w.getPlasmaRockReq() > characterLoggedIn.getPlasmaRock()) {
			return false;
		}
		else {
			characterLoggedIn.deductGold(w.getGoldReq());
			characterLoggedIn.deductWood(w.getWoodReq());
			characterLoggedIn.deductOre(w.getOreReq());
			characterLoggedIn.deductPlasmaRock(w.getPlasmaRockReq());
			charMgr.update(characterLoggedIn);
			w.increaseDamage(characterLoggedIn.getGunneryPoint(),characterLoggedIn.getNavigationPoint());
			inventoryMgr.add(characterLoggedIn,w);
			inventoryMgr.update();
			return true;
		}
	}
}