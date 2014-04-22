import java.util.*;
import java.text.*;

public class Character{
	private String username;
	private String password;
	private char type;
	private double gunneryPoint;
	private double craftPoint;
	private double navigationPoint;
	private double freeStatsPoint;
	private int gold;
	private int wood;
	private int ore;
	private int plasmaRock;
	private int ap;
	private int lvl;
	private int exp;
	private Ship ship;

	private int win;
	private int loss;
	private SISDate joinedDate;
	private SISDate lastLoggedInDate;

	public Character(String username, String password, char type){
		this.username = username;
		this.password = password;
		this.type = type;
		if (type == 'P'){
			gunneryPoint = 1.5;
			craftPoint = 0;
			navigationPoint = 0;
		}
		else if (type == 'E'){
			gunneryPoint = 0;
			craftPoint = 0;
			navigationPoint = 1.5;
		}
		freeStatsPoint = 3;
		gold = 10000;
		wood =500;
		ore = 50;
		plasmaRock =5;
		ap = 120;
		lvl =1;
		exp =0;
		ship = null;
		win = 0;
		loss = 0;
		joinedDate = new SISDate();
		lastLoggedInDate = null;
	}
	public Character(String username, String password, char type, double gunneryPoint, double craftPoint, double navigationPoint, double freeStatsPoint,
		int gold, int wood, int ore, int plasmaRock, int ap, int lvl, int exp, Ship ship, int win, int loss, SISDate joinedDate, SISDate lastLoggedInDate){
		this.username = username;
		this.password = password;
		this.type = type;
		this.gunneryPoint = gunneryPoint;
		this.craftPoint = craftPoint;
		this.navigationPoint = navigationPoint;
		this.freeStatsPoint = freeStatsPoint;
		this.gold = gold;
		this.wood = wood;
		this.ore = ore;
		this.plasmaRock = plasmaRock;
		this.ap = ap;
		this.lvl = lvl;
		this.exp = exp;
		this.ship = ship;
		this.win = win;
		this.loss = loss;
		this.joinedDate = joinedDate;
		this.lastLoggedInDate = lastLoggedInDate;
	}


	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public char getType(){
		return type;
	}
	public double getGunneryPoint(){
		return gunneryPoint;
	}
	public double getCraftPoint(){
		return craftPoint;
	}
	public double getNavigationPoint(){
		return navigationPoint;
	}
	public double getFreeStatsPoint(){
		return freeStatsPoint;
	}
	public int getGold(){
		return gold;
	}
	public int getWood(){
		return wood;
	}
	public int getOre(){
		return ore;
	}
	public int getPlasmaRock(){
		return plasmaRock;
	}
	public int getAP(){
		return ap;
	}
	public int getLvl(){
		return lvl;
	}
	public int getEXP(){
		return exp;
	}
	public Ship getShip(){
		return ship;
	}
	public int getNumberOfWin(){
		return win;
	}
	public int getNumberOfLoss(){
		return loss;
	}
	public SISDate getJoinedDate(){
		return joinedDate;
	}
	public SISDate getLastLoggedInDate(){
		return lastLoggedInDate;
	}


	public void setGunnery(double amount){
		this.gunneryPoint = amount;
	}
	public void setCraft(double amount){
		this.craftPoint = amount;
	}
	public void setNavigation(double amount){
		this.navigationPoint = amount;
	}
	public void setStatPoint(double amount){
		this.freeStatsPoint = amount;
	}
	public void setGold(int amount){
		this.gold = amount;
	}
	public void setWood(int amount){
		this.wood = amount;
	}
	public void setOre(int amount){
		this.ore = amount;
	}
	public void setPlasmaRock(int amount){
		this.plasmaRock = amount;
	}
	public void setAP(int amount){
		this.ap = amount;
		if (this.ap >500){
			ap = 500;
		}
	}
	public void setLvl(int amount){
		this.lvl = amount;
	}
	public void setEXP(int amount){
		this.exp = amount;
	}
	public void assignShip(Ship ship){
		this.ship = ship;
		ArrayList<Weapon> wList = ship.getWeaponList();
		for (int i=0; i<wList.size(); i++){
			Weapon w = wList.get(i);
			w.increaseDamage(gunneryPoint,navigationPoint);
		}
	}
	public void setNumberOfWin(int win){
		this.win = win;
	}
	public void setNumberOfLoss(int loss){
		this.loss = loss;
	}
	public void setJoinedDate(SISDate joinedDate){
		this.joinedDate = joinedDate;
	}
	public void setLastLoggedInDate(SISDate lastLoggedInDate){
		this.lastLoggedInDate = lastLoggedInDate;
	}


	public void addGunnery (double amount){
		gunneryPoint += amount;
	}
	public void addCraft(double amount){
		craftPoint += amount;
	}
	public void addNavigation(double amount){
		navigationPoint += amount;
	}
	public void gainGold(int amount){
		this.gold += amount;
	}
	public void gainWood(int amount){
		this.wood += amount;
	}
	public void gainOre(int amount){
		this.ore += amount;
	}
	public void gainPlasmaRock(int amount){
		this.plasmaRock += amount;
	}
	public void gainAP(int amount){
		this.ap += amount;
		if (this.ap >500){
			this.ap = 500;
		}
	}
	public void gainEXP(int exp){
		this.exp += exp;
	}
	public void wins(){
		this.win +=1;
	}
	public void loses(){
		this.loss +=1;
	}

	public boolean levelUp(){
		int lvlUp = 0;
		for (int i=1; i<=100; i++){
			if (this.exp>= (int)Math.round(50/3.0*Math.pow(i,3) - 50*Math.pow(i,2) + 400/3.0*(i)-100)){
				lvlUp=i;
			}
			else {
				break;
			}
		}
		int previousLvl = lvl;
		lvl = lvlUp;
		if (type == 'P'){
			gunneryPoint +=1.5*(lvl - previousLvl);
		}
		else if (type == 'E'){
			navigationPoint +=1.5*(lvl - previousLvl);
		}
		freeStatsPoint +=3*(lvl - previousLvl);
		if ((lvl - previousLvl) == 0){
			return false;
		}
		return true;

	}

	public void deductFreeStatsPoint(double amount){
		this.freeStatsPoint -= amount;
	}
	public void deductGold(int amount){
		this.gold -= amount;
	}
	public void deductWood(int amount){
		this.wood -= amount;
	}
	public void deductOre(int amount){
		this.ore -= amount;
	}
	public void deductPlasmaRock(int amount){
		this.plasmaRock -= amount;
	}
	public void deductAP(int amount){
		this.ap -= amount;
	}
}