import java.util.*;

public class Inventory{
	private Character characterLoggedIn;
	private Part part;
	private Weapon weapon;

	public Inventory(Character characterLoggedIn,Part part){
		this.characterLoggedIn = characterLoggedIn;
		this.part = part;
		this.weapon = null;
	}

	public Inventory(Character characterLoggedIn,Weapon weapon){
		this.characterLoggedIn = characterLoggedIn;
		this.weapon = weapon;
		this.part = null;
	}

	public Character getCharacter(){
		return characterLoggedIn;
	}

	public Part getPart(){
		return part;
	}

	public Weapon getWeapon(){
		return weapon;
	}
}