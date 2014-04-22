public class Part{
	private String type;
	private String name;
	private int speed;
	private int hp;
	private int capacity;
	private int weight;
	private int lvlReq;
	private int goldReq;
	private int woodReq;
	private int oreReq;
	private int plasmaRockReq;
	private String port;
	private boolean isEquipped;

	public Part(String type,String name, int speed, int hp, int capacity, int weight,
		int lvlReq, int goldReq, int woodReq, int oreReq, int plasmaRockReq, String port){
		this.type = type;
		this.name = name;
		this.speed = speed;
		this.hp = hp;
		this.capacity = capacity;
		this.weight = weight;
		this.lvlReq = lvlReq;
		this.goldReq = goldReq;
		this.woodReq = woodReq;
		this.oreReq = oreReq;
		this.plasmaRockReq = plasmaRockReq;
		this.port = port;
		this.isEquipped =false;
	}

	public String getType(){
		return type;
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
	public int getCapacity(){
		return capacity;
	}
	public int getWeight(){
		return weight;
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
	public boolean getIsEquipped(){
		return isEquipped;
	}

	public void setIsEquipped(boolean a){
		this.isEquipped = a;
	}

	public Part clone(){
		Part p = new Part(type,name, speed, hp, capacity, weight,
			lvlReq, goldReq, woodReq, oreReq, plasmaRockReq, port);
		return p;
	}

	/*
	public void setType(String type){
		this.type = type;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setHP(int hp){
		this.hp = hp;
	}
	public void setCapacity(int capacity){
		this.capacity = capacity;
	}
	public void setWeight(int weight){
		this.weight = weight;
	}
	public void setLvlReq(int lvlReq){
		this.lvlReq = lvlReq;
	}
	public void setGoldReq(int goldReq){
		this.goldReq = goldReq;
	}
	public void setWoodReq(int woodReq){
		this.woodReq = woodReq;
	}
	public void setOreReq(int oreReq){
		this.oreReq = oreReq;
	}
	public void setPlasmaRockReq(int plasmaRockReq){
		this.plasmaRockReq = plasmaRockReq;
	}
	public void setPort(String port){
		this.port = port;
	}
	*/
}