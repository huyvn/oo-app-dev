import javax.annotation.processing.FilerException;
import java.util.*;
import java.io.*;

public class WeaponManager{
	private ArrayList<Weapon> weaponList;

	private final String FILE_NAME_CANNON = "data/cannons.csv";
	private final String FILE_NAME_SUBCANNON = "data/subcannons.csv";
	private final String FILE_NAME_MISSILE = "data/missiles.csv";
	private final String FILE_NAME_MELEE = "data/melee.csv";
    private final String CLASS_NAME = "WeaponManager";

    public WeaponManager() throws DataException{
    	weaponList = new ArrayList<Weapon>();

    	load(FILE_NAME_CANNON,"cannon");
    	load(FILE_NAME_SUBCANNON,"subcannon");
    	load(FILE_NAME_MISSILE,"missile");
    	load(FILE_NAME_MELEE,"melee");
    }

    private void load(String filename, String type) throws DataException {
        Scanner fileIn = null;
        try {
        	fileIn = new Scanner(new File(filename));
            fileIn.useDelimiter(",|\r\n");

            boolean firstRow = true;
            while (fileIn.hasNext()) {
                if (firstRow){
                    firstRow = false;
                    fileIn.nextLine();
                }
                //Extract data from the file and populate the ArrayList
                String name = fileIn.next();
                String rangeStr = fileIn.next();
                String minDamageStr = fileIn.next();
                String maxDamageStr = fileIn.next();
                String weightStr = fileIn.next();
                String lvlReqStr = fileIn.next();
                String goldReqStr = fileIn.next();
                String woodReqStr = fileIn.next();
                String oreReqStr = fileIn.next();
                String plasmaRockReqStr = fileIn.next();
                String port = fileIn.next();


                // extract primitive data types
                try {
                	int range = Integer.parseInt(rangeStr);
                	int baseMinDamage = Integer.parseInt(minDamageStr);
                	int baseMaxDamage = Integer.parseInt(maxDamageStr);
                	int weight = Integer.parseInt(weightStr);
                	int lvlReq = Integer.parseInt(lvlReqStr);
                	int goldReq = Integer.parseInt(goldReqStr);
                	int woodReq = Integer.parseInt(woodReqStr);
                	int oreReq = Integer.parseInt(oreReqStr);
                	int plasmaRockReq = Integer.parseInt(plasmaRockReqStr);
                	Weapon w = new Weapon(type,name,range,baseMinDamage,baseMaxDamage,weight,lvlReq,goldReq,woodReq,oreReq,plasmaRockReq,port);

                	weaponList.add(w);
                } catch (NumberFormatException e) {
                    //propagate error
                    String message = "Reading error in File \"" + filename +
                        "\". Invalid integer format.";
                    throw new DataException(message);
                }
            }
        } catch (FileNotFoundException e) {
            //propagate error
            String message = CLASS_NAME + " class: File " + filename + " not found.";
            throw new DataException(message);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
    }

    // retrieve every weapon with <type>, including weapons not found in port
    public ArrayList<Weapon> retrieveList(String type){
    	ArrayList<Weapon> listToReturn = new ArrayList<Weapon>();
    	for (int i=0; i<weaponList.size();i++){
    		Weapon w = weaponList.get(i);
    		if (w.getType().equalsIgnoreCase(type)){
    			listToReturn.add(w);
    		}
    	}
    	return listToReturn;
    }

    public ArrayList<Weapon> retrieveAll(){
    	return weaponList;
    }

    public Weapon retrieveWeapon(String name){
        Weapon weaponToReturn = null;
        for (int i=0; i<weaponList.size();i++){
            Weapon w = weaponList.get(i);
            if (w.getName().equals(name)){
                weaponToReturn = w.clone();
                break;
            }
        }
        return weaponToReturn;
    }
}