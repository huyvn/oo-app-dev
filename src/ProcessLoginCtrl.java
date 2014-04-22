import java.util.*;

public class ProcessLoginCtrl{
	private CharacterManager charMgr;

	private Character characterLoggedIn;

	public ProcessLoginCtrl(CharacterManager charMgr){
		this.charMgr = charMgr;
	}

	public Character authenticateCharacter(String username, String password) throws DataException{
		Character c = charMgr.retrieveCharacter(username);
		if (c != null && c.getPassword().equals(password)) {
			characterLoggedIn = c;
			SISDate lastLoggedInDate = characterLoggedIn.getLastLoggedInDate();
			SISDate today = new SISDate();
			if (lastLoggedInDate != null && today.after(lastLoggedInDate)){
				characterLoggedIn.gainAP(120);
			}
			characterLoggedIn.setLastLoggedInDate(today);
			charMgr.update(characterLoggedIn);
		}
		else {
			characterLoggedIn = null;
		}
		return characterLoggedIn;
	}

	public boolean registerCharacter(String username, String password, char type) throws DataException{
		Character c = charMgr.retrieveCharacter(username);
		if (c == null){
			charMgr.addCharacter(username,password,type);
			return true;
		}
		return false;
	}
}