import java.util.*;

public class ProcessRepairCtrl{
	private Character characterLoggedIn;
	private CharacterManager charMgr;

	public ProcessRepairCtrl(Character characterLoggedIn, CharacterManager charMgr){
		this.characterLoggedIn = characterLoggedIn;
		this.charMgr=charMgr;
	}

	private int getLostHP(Ship s){
		int lostHP = s.getHP() - s.getRealHP();
		return lostHP;
	}

	private int getHPperAP(Ship s){
		return s.getHP()/10;
	}

	public int getNeededAP(Ship s){
		if (s.getRealHP() == 0){
			return 10;
		}
		int lostHP = getLostHP(s);
		int unitAP = getHPperAP(s); //get how much HP can be cure by 1AP
		int curedHP =0;
		for (int i =0; i<=11; i++){
			curedHP = unitAP*i;
			if (curedHP >= lostHP){
				return i;
			}
		}
		return 0;
	}

	public int getNeededGold(Ship s){
		return getLostHP(s);
	}

	public boolean repair(Character characterLoggedIn, int amt, char type) throws DataException{
		Ship s = characterLoggedIn.getShip();
		int restoredHP = 0;
		if ( amt == 0 ){
			return false;
		}
		else if (type == 'A'){
			int unitAP = getHPperAP(s);
			if (amt ==10){
				s.salvage();
			} else{
				restoredHP = amt*unitAP;
			}
			characterLoggedIn.deductAP(amt);
		}
		else if (type == 'G'){
			restoredHP = amt;
			characterLoggedIn.deductGold(amt);
		}
		s.restoreHP(restoredHP);
		charMgr.update(characterLoggedIn);
		return true;
	}
}