import java.util.*;

public class ProcessEquipCtrl{
	private Character character;
	private CharacterManager charMgr;
	private InventoryManager inventoryMgr;

	public ProcessEquipCtrl(Character character, CharacterManager charMgr, InventoryManager inventoryMgr){
		this.character = character;
		this.charMgr = charMgr;
		this.inventoryMgr = inventoryMgr;
	}

	public boolean unequipPart(Part p) throws DataException{
		if (p == null){
			return false;
		} else{
			Ship s = character.getShip();
			boolean check = s.removePart(p);
			inventoryMgr.update();
			return check;
		}
	}

	public boolean unequipWeapon(int position) throws DataException{
		Ship s = character.getShip();
		boolean check = s.removeWeapon(position);
		inventoryMgr.update();
		return check;
	}

	public ArrayList<Weapon> getAvailableWeapon(){
		return inventoryMgr.retrieveAvailabeWeapon(character.getUsername());
	}

	public ArrayList<Part> getAvailablePartType(String type){
		return inventoryMgr.retrieveAvailablePartType(character.getUsername(),type);
	}

	public boolean equipPart(String type, int position)throws DataException{
		ArrayList<Part> availableParts = getAvailablePartType(type);
		if (position > availableParts.size() || position == 0){
			return false;
		}
		else{
			Part p = availableParts.get(position-1);
			Ship s = character.getShip();
			boolean check = s.addPart(p);
			inventoryMgr.update();
			return check;
		}
	}

	public boolean equipWeapon(int position)throws DataException{
		ArrayList<Weapon> availableWeapons = getAvailableWeapon();
		if (position > availableWeapons.size() || position == 0){
			return false;
		}
		else {
			Weapon w = availableWeapons.get(position -1);
			Ship s = character.getShip();
			boolean check = s.equipWeapon(w);
			inventoryMgr.update();
			return check;
		}
	}
}