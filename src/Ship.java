import java.util.*;

public class Ship{
	private String name;
	private int speed;
	private int hp;
	private int baseHP;
	private int realHP;
	private int slot;
	private int realSlot;
	private int capacity;
	private int baseCapacity;
	private int realCapacity;
	private int lvlReq;
	private int goldReq;
	private int woodReq;
	private int oreReq;
	private int plasmaRockReq;
	private String port;
	private Part engine;
	private Part figurehead;
	private Part hull;
	private Part sail;
	private Part stabilizer;
	private ArrayList<Weapon> weaponList;
	private boolean isSunk;

	public Ship(String name, int speed, int baseHP, int slot, int baseCapacity,
		int lvlReq, int goldReq, int woodReq, int oreReq, int plasmaRockReq, String port){
		this.name = name;
		this.speed = speed;
		this.baseHP = baseHP;
		hp = baseHP;
		realHP = hp;
		this.slot = slot;
		realSlot = this.slot;
		this.baseCapacity = baseCapacity;
		capacity = baseCapacity;
		realCapacity = capacity;
		this.lvlReq = lvlReq;
		this.goldReq = goldReq;
		this.woodReq = woodReq;
		this.oreReq = oreReq;
		this.plasmaRockReq = plasmaRockReq;
		this.port = port;
		engine = null;
		figurehead = null;
		hull = null;
		sail = null;
		stabilizer = null;
		weaponList = new ArrayList<Weapon>();
		isSunk = false;
	}


	public String getName(){
		return name;
	}
	public int getSpeed(){
		return speed;
	}
	public int getHP(){
		return hp;
	}
	public int getBaseHP(){
		return baseHP;
	}
	public int getRealHP(){
		return realHP;
	}
	public int getSlot(){
		return slot;
	}
	public int getCapacity(){
		return capacity;
	}
	public int getBaseCapacity(){
		return baseCapacity;
	}
	public int getRealCapacity(){
		return realCapacity;
	}
	public int getLvlReq(){
		return lvlReq;
	}
	public int getGoldReq(){
		return goldReq;
	}
	public int getWoodReq(){
		return woodReq;
	}
	public int getOreReq(){
		return oreReq;
	}
	public int getPlasmaRockReq(){
		return plasmaRockReq;
	}
	public String getPort(){
		return port;
	}

	public Part getEngine(){
		return engine;
	}
	public Part getFigurehead(){
		return figurehead;
	}
	public Part getHull(){
		return hull;
	}
	public Part getSail(){
		return sail;
	}
	public Part getStabilizer(){
		return stabilizer;
	}
	public ArrayList<Weapon> getWeaponList(){
		return weaponList;
	}
	public Weapon retrieveWeapon(int n){
		if (n >= weaponList.size()){
			return null;
		}
		else {
			Weapon w = weaponList.get(n-1);
			return w;
		}
	}
	public boolean isSunk(){
		return isSunk;
	}

	public void increaseSpeed(int amt){
		speed += amt;
	}

	public void setRealHP(int realHP){
		this.realHP = realHP;
	}
	public void deductHP(int amt){
		hp -= amt;
	}
	public void increaseHP(int amt){
		hp += amt;
	}
	public void getDamaged(int amt){
		realHP -= amt;
		if (realHP <=0){
			realHP = 0;
			isSunk = true;
		}
	}
	public void restoreHP(int amt){
		realHP += amt;
		if (realHP > hp){
			realHP = hp;
		}
	}
	public void salvage(){
		realHP = hp;
		setIsSunkStatus(false);
	}
	public void setIsSunkStatus(boolean a){
		isSunk = a;
	}

	public Ship clone(){
		Ship p = new Ship(name, speed, baseHP, slot, baseCapacity,
		lvlReq, goldReq, woodReq, oreReq, plasmaRockReq, port);
		return p;
	}

	public boolean equipWeapon(Weapon w){
		if (realSlot == 0){
			return false;
		}
		else{
			w.setIsEquipped(true);
			weaponList.add(w);
			realCapacity -= w.getWeight();
			realSlot -=1;
			return true;
		}
	}

	public boolean removeWeapon(int n){
		if (n > weaponList.size() || n==0){
			return false;
		}
		else {
			realCapacity += weaponList.get(n-1).getWeight();
			Weapon w = weaponList.get(n-1);
			w.setIsEquipped(false);
			weaponList.remove(n-1);
			realSlot += 1;
			return true;
		}
	}

	public boolean addPart(Part part){
		if (part.getWeight() < this.realCapacity){
			Part p= part;
			String type = p.getType();
			if (type.equals("engine"))
				engine = p;
			if (type.equals("figurehead"))
				figurehead = p;
			if (type.equals("hull"))
				hull = p;
			if (type.equals("sail"))
				sail = p;
			if (type.equals("stabilizer"))
				stabilizer = p;
			speed += p.getSpeed();
			if (speed <0){
				return false;
			}
			//realHP += p.getHP();
			hp += p.getHP();
			capacity += p.getCapacity();
			realCapacity = realCapacity + p.getCapacity() - p.getWeight();
			p.setIsEquipped(true);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean removePart(Part p){
		if ((realCapacity - p.getCapacity()) <0){
			return false;
		}
		else {
			realCapacity = realCapacity - p.getCapacity() + p.getWeight();
		}
		capacity -= p.getCapacity();
		speed -= p.getSpeed();
		hp -= p.getHP();
		realHP -= p.getHP();
		p.setIsEquipped(false);
		String type = p.getType();
		if (type.equals("engine"))
			engine = null;
		if (type.equals("figurehead"))
			figurehead = null;
		if (type.equals("hull"))
			hull = null;
		if (type.equals("sail"))
			sail = null;
		if (type.equals("stabilizer")){
			stabilizer = null;
		}
		return true;
	}



	public void increaseStats(double craft, double navigation){
		increaseHP((int)Math.round(craft*0.01*baseHP));
		increaseSpeed((int)(navigation));
	}

	public int getMaxRange(){
		int maxRange = 0;
		for (int i =0; i<weaponList.size(); i++){
			Weapon w = weaponList.get(i);
			int range = w.getRange();
			if (range > maxRange){
				maxRange = range;
			}
		}
		return maxRange;
	}
}