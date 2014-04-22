import javax.annotation.processing.FilerException;
import java.util.*;
import java.io.*;
import java.text.*;

public class CharacterManager{
	private ArrayList<Character> characterList;
    private WeaponManager wpnMgr;
    private PartManager partMgr;
    private ShipManager shipMgr;

	private final String FILE_NAME = "data/characters.csv";
    private final String CLASS_NAME = "CharacterManager";

	public CharacterManager(WeaponManager wpnMgr,PartManager partMgr,ShipManager shipMgr) throws DataException{
		characterList = new ArrayList<Character>();
        this.wpnMgr = wpnMgr;
        this.partMgr = partMgr;
        this.shipMgr = shipMgr;
		PrintStream fileOut = null;
        try {
            File f = new File(FILE_NAME);
            if (!f.exists()) {
                //creates the File object with FILE_NAME and creates file Header
                fileOut = new PrintStream(new FileOutputStream(FILE_NAME, false));
                fileOut.print("Username,Password,Type,Gunnery,Craft,Navigation,Stat Point,Gold,Wood,Ore,Plasma Rock,AP,Level,EXP,");
                fileOut.print("Ship Name,Ship HP,");
                fileOut.println("Win,Loss,Joined Date,Last LoggedIn Date");
            }

        } catch (IOException e) {
            //display error
            String message = CLASS_NAME + " class : " + FILE_NAME + " not found";
            throw new DataException(message);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
		load();
	}

	private void load() throws DataException{
		Scanner fileIn = null;
        try {
            fileIn = new Scanner(new File(FILE_NAME));
            fileIn.useDelimiter(",|\r\n");

            //boolean firstRow = true;
            fileIn.nextLine();
            while (fileIn.hasNext()) {
                /*if (firstRow){
                    firstRow = false;

                }*/
                //Extract data from the file and populate the ArrayList
                String username = fileIn.next();
                String password = fileIn.next();
                char type = fileIn.next().charAt(0);
                String gunneryStr = fileIn.next();
                String craftStr = fileIn.next();
                String navigationStr = fileIn.next();
                String statPointStr = fileIn.next();
                String goldStr = fileIn.next();
                String woodStr = fileIn.next();
                String oreStr = fileIn.next();
                String plasmaRockStr = fileIn.next();
                String apStr = fileIn.next();
                String lvlStr = fileIn.next();
                String expStr = fileIn.next();
                String shipName = fileIn.next();
                String shipHPStr = fileIn.next();
                /*String engineName = fileIn.next();
                String figureheadName = fileIn.next();
                String hullName = fileIn.next();
                String sailName = fileIn.next();
                String stabilizerName = fileIn.next();
                String weapon1Name = fileIn.next();
                String weapon2Name = fileIn.next();
                String weapon3Name = fileIn.next();
                String weapon4Name = fileIn.next();
                String weapon5Name = fileIn.next();
                String weapon6Name = fileIn.next();
                String weapon7Name = fileIn.next();*/
                String winStr = fileIn.next();
                String lossStr = fileIn.next();
                String joinedDateStr = fileIn.next();
                String lastLoggedInDateStr = fileIn.next();

                double gunnery =0;
				double craft=0;
				double navigation =0;
				double freeStatsPoint=0;
				int gold=0;
				int wood=0;
				int ore=0;
				int plasmaRock=0;
				int ap=0;
				int lvl=0;
				int exp=0;
                int shipHP=0;
                int win=0;
                int loss=0;
                // extract primitive data types
                try {
                    gunnery = Double.parseDouble(gunneryStr);
                    craft = Double.parseDouble(craftStr);
                    navigation = Double.parseDouble(navigationStr);
                    freeStatsPoint = Double.parseDouble(statPointStr);
                    gold = Integer.parseInt(goldStr);
                    wood = Integer.parseInt(woodStr);
                    ore = Integer.parseInt(oreStr);
                    plasmaRock = Integer.parseInt(plasmaRockStr);
                    ap = Integer.parseInt(apStr);
                    lvl = Integer.parseInt(lvlStr);
                    exp = Integer.parseInt(expStr);
                    shipHP = Integer.parseInt(shipHPStr);
                    win = Integer.parseInt(winStr);
                    loss = Integer.parseInt(lossStr);
                    SISDate joinedDate = new SISDate(joinedDateStr);

                    SISDate lastLoggedInDate;
                    if (!lastLoggedInDateStr.equals("")){
                        lastLoggedInDate = new SISDate(lastLoggedInDateStr);
                    } else{
                        lastLoggedInDate = null;
                    }

                    Ship ship =null;

                    if (!shipName.equals("")){
                        ship = shipMgr.retrieveShip(shipName);

                        /*if (!engineName.equals("")){
                            Part engine = partMgr.retrievePart("engine",engineName);
                            ship.addPart(engine);
                        }
                        if (!figureheadName.equals("")){
                            Part figurehead = partMgr.retrievePart("figurehead",figureheadName);
                            ship.addPart(figurehead);
                        }
                        if (!hullName.equals("")){
                            Part hull = partMgr.retrievePart("hull",hullName);
                            ship.addPart(hull);
                        }
                        if (!sailName.equals("")){
                            Part sail = partMgr.retrievePart("sail",sailName);
                            ship.addPart(sail);
                        }
                        if (!stabilizerName.equals("")){
                            Part stabilizer = partMgr.retrievePart("stabilizer",stabilizerName);
                            ship.addPart(stabilizer);
                        }

                        if (!weapon1Name.equals("")){
                            Weapon w1 = wpnMgr.retrieveWeapon(weapon1Name).clone();
                            w1.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w1);
                        }
                        if (!weapon2Name.equals("")){
                            Weapon w2 = wpnMgr.retrieveWeapon(weapon2Name).clone();
                            w2.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w2);
                        }
                        if (!weapon3Name.equals("")){
                            Weapon w3 = wpnMgr.retrieveWeapon(weapon3Name).clone();
                            w3.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w3);
                        }
                        if (!weapon4Name.equals("")){
                            Weapon w4 = wpnMgr.retrieveWeapon(weapon4Name).clone();
                            w4.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w4);
                        }
                        if (!weapon5Name.equals("")){
                            Weapon w5 = wpnMgr.retrieveWeapon(weapon5Name).clone();
                            w5.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w5);
                        }
                        if (!weapon6Name.equals("")){
                            Weapon w6 = wpnMgr.retrieveWeapon(weapon6Name).clone();
                            w6.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w6);
                        }
                        if (!weapon7Name.equals("")){
                            Weapon w7 = wpnMgr.retrieveWeapon(weapon7Name).clone();
                            w7.increaseDamage(gunnery,craft);
                            ship.equipWeapon(w7);
                        }*/
                        ship.increaseStats(craft,navigation);
                        ship.setRealHP(shipHP);
                        if (shipHP == 0){
                            ship.setIsSunkStatus(true);
                        }
                    }
                    Character c = new Character(username,password,type,gunnery,craft,navigation,freeStatsPoint,
                        gold,wood,ore,plasmaRock,ap,lvl,exp,ship,win,loss,joinedDate,lastLoggedInDate);
                    characterList.add(c);
                } catch (NumberFormatException e) {
                    //propagate error
                    String message = "Reading error in File \"" + FILE_NAME +
                        "\". Invalid integer format.";
                    throw new DataException(message);
                } catch (ParseException e){
                    String message = "Reading error in File \"" + FILE_NAME +
                        "\". Invalid SISDate format.";
                    throw new DataException(message);
                }
            }
        } catch (FileNotFoundException e) {
            //propagate error
            String message = CLASS_NAME + " class: File " + FILE_NAME + " not found.";
            throw new DataException(message);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
	}

	private void save() throws DataException {
        PrintStream fileOut = null;
        try {
            // preparation to write to a file
            fileOut = new PrintStream(new FileOutputStream(FILE_NAME, false));
            fileOut.print("Username,Password,Type,Gunnery,Craft,Navigation,Stat Point,Gold,Wood,Ore,Plasma Rock,AP,Level,EXP,");
            fileOut.print("Ship Name,Ship HP,");
            fileOut.println("Win,Loss,Joined Date,Last LoggedIN Date");
            // iterate through patronList and for each patron, write all its attributes to the file
            for (int i = 0; i < characterList.size(); i++) {
                Character c = characterList.get(i);
                fileOut.print(c.getUsername());
                fileOut.print(",");
                fileOut.print(c.getPassword());
                fileOut.print(",");
                fileOut.print(c.getType());
                fileOut.print(",");
                fileOut.print(c.getGunneryPoint());
                fileOut.print(",");
                fileOut.print(c.getCraftPoint());
                fileOut.print(",");
                fileOut.print(c.getNavigationPoint());
                fileOut.print(",");
                fileOut.print(c.getFreeStatsPoint());
                fileOut.print(",");
                fileOut.print(c.getGold());
                fileOut.print(",");
                fileOut.print(c.getWood());
                fileOut.print(",");
                fileOut.print(c.getOre());
                fileOut.print(",");
                fileOut.print(c.getPlasmaRock());
                fileOut.print(",");
                fileOut.print(c.getAP());
                fileOut.print(",");
                fileOut.print(c.getLvl());
                fileOut.print(",");
                fileOut.print(c.getEXP());
                fileOut.print(",");

                Ship ship = c.getShip();
                if (ship != null){
                    fileOut.print(ship.getName());
                }
                    fileOut.print(",");

                if (ship!= null){
                    fileOut.print(ship.getRealHP());
                }else {
                    fileOut.print(0);
                }
                    fileOut.print(",");
                /*if (ship!=null) {
                    Part engine = ship.getEngine();
                    if (engine!=null)
                        fileOut.print(engine.getName());
                }
                fileOut.print(",");

                if (ship!=null) {
                    Part figurehead = ship.getFigurehead();
                    if (figurehead != null)
                        fileOut.print(figurehead.getName());
                }
                fileOut.print(",");

                if (ship!=null) {
                    Part hull = ship.getHull();
                    if (hull != null)
                        fileOut.print(hull.getName());
                }
                fileOut.print(",");

                if (ship!=null) {
                    Part sail = ship.getSail();
                    if (sail!= null)
                        fileOut.print(sail.getName());
                }
                fileOut.print(",");

                if (ship!=null) {
                    Part stabilizer = ship.getStabilizer();
                    if (stabilizer != null)
                        fileOut.print(stabilizer.getName());
                }
                fileOut.print(",");

                ArrayList<Weapon> temp = new ArrayList<Weapon>();
                if (ship!=null) {
                    temp = ship.getWeaponList();
                }

                if (temp.size()>=1){
                    fileOut.print(temp.get(1).getName());
                }
                fileOut.print(",");
                if (temp.size()>=2){
                    fileOut.print(temp.get(2).getName());
                }
                fileOut.print(",");
                if (temp.size()>=3){
                    fileOut.print(temp.get(3).getName());
                }
                fileOut.print(",");
                if (temp.size()>=4){
                    fileOut.print(temp.get(4).getName());
                }
                fileOut.print(",");
                if (temp.size()>=5){
                    fileOut.print(temp.get(5).getName());
                }
                fileOut.print(",");
                if (temp.size()>=6){
                    fileOut.print(temp.get(6).getName());
                }
                fileOut.print(",");
                if (temp.size()>=7){
                    fileOut.print(temp.get(7).getName());
                }
                fileOut.print(",");*/

                fileOut.print(c.getNumberOfWin());
                fileOut.print(",");
                fileOut.print(c.getNumberOfLoss());
                fileOut.print(",");

                SISDate joinedDate = c.getJoinedDate();
                fileOut.print(joinedDate.toString());

                fileOut.print(",");
                SISDate lastLoggedInDate = c.getLastLoggedInDate();
                if (lastLoggedInDate != null){
                    fileOut.print(lastLoggedInDate.toString());
                }else{
                    fileOut.print("");
                }

                fileOut.println();
            }
        } catch (FileNotFoundException e) {
            //propagate error
            String message = CLASS_NAME + " class : File " + FILE_NAME + " not found";
            throw new DataException(message);
        } finally {
            // remember to release the resource
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    /*public boolean register(String username, String password, char type){
        if (retrieveCharacter(username) == null){
            Character c = new Character(username,password,type);
            return true;
        }
        return false;
    }*/

    public void addCharacter(String username, String password, char type) throws DataException{
        Character c = new Character(username,password,type);
        characterList.add(c);
        save();
    }

    public Character retrieveCharacter(String username){
        Character characterToReturn =null;
        for (int i=0; i<characterList.size(); i++){
            Character c = characterList.get(i);
            if (c.getUsername().equals(username)){
                characterToReturn = c;
                break;
            }
        }
        return characterToReturn;
    }

    public ArrayList<Character> retrieveAll(){
        return characterList;
    }

    public void update(Character character) throws DataException {
        Character c = retrieveCharacter(character.getUsername());
        c.setGunnery(character.getGunneryPoint());
        c.setCraft(character.getCraftPoint());
        c.setNavigation(character.getNavigationPoint());
        c.setStatPoint(character.getFreeStatsPoint());
        c.setGold(character.getGold());
        c.setWood(character.getWood());
        c.setOre(character.getOre());
        c.setPlasmaRock(character.getPlasmaRock());
        c.setAP(character.getAP());
        c.setLvl(character.getLvl());
        c.setEXP(character.getEXP());
        Ship s = character.getShip();
        if (s != null){
            c.assignShip(character.getShip());
        }
        c.setNumberOfWin(character.getNumberOfWin());
        c.setNumberOfLoss(character.getNumberOfLoss());
        c.setJoinedDate(character.getJoinedDate());
        c.setLastLoggedInDate(character.getLastLoggedInDate());
        // save character list to file
        save();
    }
}