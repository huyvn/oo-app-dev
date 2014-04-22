import java.util.*;

public class ProcessPartCtrl{
	private Character characterLoggedIn;
	private CharacterManager charMgr;
	private PartManager partMgr;
	private InventoryManager inventoryMgr;

	public ProcessPartCtrl(Character characterLoggedIn,CharacterManager charMgr,PartManager partMgr,InventoryManager inventoryMgr){
		this.characterLoggedIn = characterLoggedIn;
		this.partMgr = partMgr;
		this.charMgr = charMgr;
		this.inventoryMgr = inventoryMgr;
	}

	public ArrayList<Part> retrieveAvailableParts(){
		ArrayList<Part> listToReturn = new ArrayList<Part>();
		ArrayList<Part> partList = partMgr.retrieveAll();
		for (int i=0; i<partList.size(); i++){
			Part p = partList.get(i);
			if (p.getPort().equals("Imperial City Port")){
				listToReturn.add(p);
			}
		}
		return listToReturn;
	}

	public ArrayList<Part> retrieveAvailablePartsByType(String type){
		ArrayList<Part> listToReturn = new ArrayList<Part>();
		ArrayList<Part> typeList = partMgr.retrieveList(type);
		for (int i=0; i<typeList.size(); i++){
			Part p = typeList.get(i);
			if (p.getPort().equals("Imperial City Port")){
				listToReturn.add(p);
			}
		}
		return listToReturn;
	}

	public Part retrievePart(String type, int position){
		ArrayList<Part> partList = retrieveAvailablePartsByType(type);
		Part partToReturn = null;
		if (position >0 && position <= partList.size()){
			partToReturn = partList.get(position-1);
		}
		return partToReturn.clone();
	}

	public boolean buyPart(Part p) throws DataException{
		if (p.getLvlReq() > characterLoggedIn.getLvl() || p.getGoldReq() > characterLoggedIn.getGold() ||
			p.getWoodReq() > characterLoggedIn.getWood() || p.getOreReq() > characterLoggedIn.getOre() ||
			p.getPlasmaRockReq() > characterLoggedIn.getPlasmaRock()) {
			return false;
		}
		else {
			characterLoggedIn.deductGold(p.getGoldReq());
			characterLoggedIn.deductWood(p.getWoodReq());
			characterLoggedIn.deductOre(p.getOreReq());
			characterLoggedIn.deductPlasmaRock(p.getPlasmaRockReq());
			charMgr.update(characterLoggedIn);
			inventoryMgr.add(characterLoggedIn,p);
			inventoryMgr.update();
			return true;
		}
	}
}