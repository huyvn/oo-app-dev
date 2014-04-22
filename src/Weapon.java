public class Weapon {
	private String type;
	private String name;
	private int range;
	private int minDamage;
	private int maxDamage;
	private int baseMinDamage;
	private int baseMaxDamage;
	private int weight;
	private int lvlReq;
	private int goldReq;
	private int woodReq;
	private int oreReq;
	private int plasmaRockReq;
	private String port;
	private boolean isEquipped;

	public Weapon(String type,String name, int range, int baseMinDamage, int baseMaxDamage, int weight,
		int lvlReq, int goldReq, int woodReq, int oreReq, int plasmaRockReq, String port){
		this.type = type;
		this.name = name;
		this.range = range;
		this.baseMinDamage = baseMinDamage;
		minDamage = this.baseMinDamage;
		this.baseMaxDamage = baseMaxDamage;
		maxDamage = this.baseMaxDamage;
		this.weight = weight;
		this.lvlReq = lvlReq;
		this.goldReq = goldReq;
		this.woodReq = woodReq;
		this.oreReq = oreReq;
		this.plasmaRockReq = plasmaRockReq;
		this.port = port;
		isEquipped = false;
	}


	public String getType(){
		return type;
	}
	public String getName(){
		return name;
	}
	public int getRange(){
		return range;
	}
	public int getMinDamage(){
		return minDamage;
	}
	public int getBaseMinDamage(){
		return baseMinDamage;
	}
	public int getMaxDamage(){
		return maxDamage;
	}
	public int getBaseMaxDamage(){
		return baseMaxDamage;
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


	public void setType(String type){
		this.type = type;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setRange(int range){
		this.range = range;
	}
	public void setMinDamage(int minDamage){
		this.minDamage = minDamage;
	}
	public void setMaxDamage(int maxDamage){
		this.maxDamage = maxDamage;
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
	public void setIsEquipped(boolean a){
		this.isEquipped = a;
	}


	// increase damage by gunnery points & navigation points
	public void increaseDamage(double gunneryPoint,double navigationPoint){
		double percentIncrease =0;
		if (type.equalsIgnoreCase("cannon") || type.equalsIgnoreCase("subcannon")){
			percentIncrease = 1+0.005*gunneryPoint;
		}
		else if (type.equalsIgnoreCase("melee")){
			percentIncrease = 1+0.005*navigationPoint;
		}
		else if (type.equalsIgnoreCase("missile")){
			percentIncrease = 1 + 0.005*gunneryPoint + 0.0025*navigationPoint;
		}
		this.minDamage = (int)Math.round(this.baseMinDamage*percentIncrease);
		this.maxDamage = (int)Math.round(this.baseMaxDamage*percentIncrease);
	}

	// pass value of a weapon to a another weapon (when player buy a new weapon from shop)
	public Weapon clone(){

		/* Weapon another
		another.setType(type);
		another.setName(name);
		another.setRange(range);
		another.setMinDamage(minDamage);
		another.setMaxDamage(maxDamage);
		another.setWeight(weight);
		another.setLvlReq(lvlReq);
		another.setGoldReq(goldReq);
		another.setWoodReq(woodReq);
		another.setOreReq(oreReq);
		another.setPlasmaRockReq(plasmaRockReq);
		another.setPort(port);
		*/
		Weapon w = new Weapon(type,name, range, baseMinDamage, baseMaxDamage, weight,
		lvlReq, goldReq, woodReq, oreReq, plasmaRockReq, port);
		return w;
	}
}