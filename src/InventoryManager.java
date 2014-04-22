import javax.annotation.processing.FilerException;
import java.util.*;
import java.io.*;

public class InventoryManager{
	private ArrayList<Inventory> inventoryList;
	private CharacterManager charMgr;
	private PartManager partMgr;
	private WeaponManager wpnMgr;

	private final String FILE_NAME = "data/inventories.csv";
    private final String CLASS_NAME = "InventoryManager";

    public InventoryManager(CharacterManager charMgr, PartManager partMgr, WeaponManager wpnMgr) throws DataException{
    	inventoryList = new ArrayList<Inventory>();
    	this.charMgr = charMgr;
    	this.partMgr = partMgr;
    	this.wpnMgr = wpnMgr;

    	PrintStream fileOut = null;
        try {
            File f = new File(FILE_NAME);
            if (!f.exists()) {
                //creates the File object with FILE_NAME and creates file Header
                fileOut = new PrintStream(new FileOutputStream(FILE_NAME, false));
                fileOut.println("UserName,Part/Weapon Name,Equipped?");
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

            fileIn.nextLine();
            while (fileIn.hasNext()) {

                //Extract data from the file and populate the ArrayList
                String username = fileIn.next();
                String partOrWeaponName = fileIn.next();
                String equipStatusStr = fileIn.next();

                // extract primitive data types
                /*try {*/
            	boolean equipStatus = Boolean.parseBoolean(equipStatusStr);
            	Character c = charMgr.retrieveCharacter(username);
            	Part p = partMgr.retrievePart(partOrWeaponName);
            	Weapon w = wpnMgr.retrieveWeapon(partOrWeaponName);
            	Inventory inventory = null;
            	if (p!= null){
            		p.setIsEquipped(equipStatus);
            		if (equipStatus){
            			Ship s = c.getShip();
            			s.addPart(p);
            		}
            		inventory = new Inventory(c,p);
            	}
            	else if (w!= null){
            		w.setIsEquipped(equipStatus);
            		w.increaseDamage(c.getGunneryPoint(),c.getNavigationPoint());
            		if (equipStatus){
            			Ship s= c.getShip();
            			s.equipWeapon(w);
            		}
            		inventory = new Inventory(c,w);
            	}

            	inventoryList.add(inventory);

                /*} catch (ParseException e) {
                    //propagate error
                    String message = "Reading error in File \"" + FILE_NAME +
                        "\". Invalid boolean format.";
                    throw new DataException(message);
                }*/
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
            fileOut.println("UserName,Part/Weapon Name,Equipped?");
            for (int i=0; i<inventoryList.size();i++){
            	Inventory inventory = inventoryList.get(i);
            	Character character = inventory.getCharacter();
            	Part p = inventory.getPart();
            	Weapon w = inventory.getWeapon();
            	fileOut.print(character.getUsername());
            	fileOut.print(",");
            	if(p != null){
            		fileOut.print(p.getName());
            		fileOut.print(",");
            		fileOut.println(p.getIsEquipped());
            	} else if(w != null){
            		fileOut.print(w.getName());
            		fileOut.print(",");
            		fileOut.println(w.getIsEquipped());
            	}
            }
        }
        catch (FileNotFoundException e) {
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


    public ArrayList<Inventory> retrieveInventory(String username){
    	ArrayList<Inventory> listToReturn = new ArrayList<Inventory>();
    	for (int i=0; i<inventoryList.size(); i++){
    		Inventory inventory = inventoryList.get(i);
    		Character c =inventory.getCharacter();
    		if (c.getUsername().equals(username)){
    			listToReturn.add(inventory);
    		}
    	}
    	return listToReturn;
    }

    public ArrayList<Part> retrievePartInventory(String username){
        ArrayList<Part> listToReturn = new ArrayList<Part>();
        ArrayList<Inventory> inventoryList = retrieveInventory(username);
        for (int i=0; i<inventoryList.size(); i++){
            Inventory inventory = inventoryList.get(i);
            Part p = inventory.getPart();
            if (p!=null){
                listToReturn.add(p);
            }
        }
        return listToReturn;
    }

    public ArrayList<Part> retrievePartTypeInventory(String username,String type){
        ArrayList<Part> partList = retrievePartInventory(username);
        ArrayList<Part> listToReturn = new ArrayList<Part>();
        for (int i=0; i<partList.size();i++){
            Part p = partList.get(i);
            if (p.getType().equals(type)){
                listToReturn.add(p);
            }
        }
        return listToReturn;
    }

    public ArrayList<Part> retrieveAvailablePartType(String username, String type){
        ArrayList<Part> listToReturn = new ArrayList<Part>();
        ArrayList<Part> partList = retrievePartTypeInventory(username,type);
        for (int i=0; i<partList.size();i++){
            Part p = partList.get(i);
            boolean checkIsEquipped = p.getIsEquipped();
            if (!checkIsEquipped){
                listToReturn.add(p);
            }
        }
        return listToReturn;
    }

    public ArrayList<Weapon> retrieveWeaponInventory(String username){
        ArrayList<Weapon> listToReturn = new ArrayList<Weapon>();
        ArrayList<Inventory> inventoryList = retrieveInventory(username);
        for (int i=0; i<inventoryList.size(); i++){
            Inventory inventory = inventoryList.get(i);
            Weapon w = inventory.getWeapon();
            if (w!=null){
                listToReturn.add(w);
            }
        }
        return listToReturn;
    }

    public ArrayList<Weapon> retrieveAvailabeWeapon(String username){
        ArrayList<Weapon> listToReturn = new ArrayList<Weapon>();
        ArrayList<Weapon> wpnList = retrieveWeaponInventory(username);
        for (int i=0; i<wpnList.size(); i++){
            Weapon w = wpnList.get(i);
            boolean checkIsEquipped = w.getIsEquipped();
            if (!checkIsEquipped){
                listToReturn.add(w);
            }
        }
        return listToReturn;
    }

    public void add(Character character, Part part)throws DataException{
    	Inventory inventory = new Inventory(character,part);
    	inventoryList.add(inventory);
        save();
    }

    public void add(Character character, Weapon wpn)throws DataException{
    	Inventory inventory = new Inventory(character,wpn);
    	inventoryList.add(inventory);
        save();
    }

    public void update() throws DataException{
        save();
    }

}