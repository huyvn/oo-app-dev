import javax.annotation.processing.FilerException;
import java.util.*;
import java.io.*;

public class ShipManager{
	private ArrayList<Ship> shipList;

	private final String FILE_NAME = "data/ships.csv";
    private final String CLASS_NAME = "ShipManager";

    public ShipManager() throws DataException{
    	shipList = new ArrayList<Ship>();

    	load();
    }

    private void load() throws DataException{
    	Scanner fileIn = null;
        try {
        	fileIn = new Scanner(new File(FILE_NAME));
            fileIn.useDelimiter(",|\r\n");

            boolean firstRow = true;
            while (fileIn.hasNext()) {
                if (firstRow){
                    firstRow = false;
                    fileIn.nextLine();
                }
                //Extract data from the file and populate the ArrayList
                String name = fileIn.next();
                String speedStr = fileIn.next();
                String hpStr = fileIn.next();
                String slotStr = fileIn.next();
                String capacityStr = fileIn.next();
                String lvlReqStr = fileIn.next();
                String goldReqStr = fileIn.next();
                String woodReqStr = fileIn.next();
                String oreReqStr = fileIn.next();
                String plasmaRockReqStr = fileIn.next();
                String port = fileIn.next();


                // extract primitive data types
                try {
                	int speed = Integer.parseInt(speedStr);
                	int hp = Integer.parseInt(hpStr);
                	int slot = Integer.parseInt(slotStr);
                	int capacity = Integer.parseInt(capacityStr);
                	int lvlReq = Integer.parseInt(lvlReqStr);
                	int goldReq = Integer.parseInt(goldReqStr);
                	int woodReq = Integer.parseInt(woodReqStr);
                	int oreReq = Integer.parseInt(oreReqStr);
                	int plasmaRockReq = Integer.parseInt(plasmaRockReqStr);
                	Ship s = new Ship(name,speed,hp,slot,capacity,lvlReq,goldReq,woodReq,oreReq,plasmaRockReq,port);

                	shipList.add(s);
                } catch (NumberFormatException e) {
                    //propagate error
                    String message = "Reading error in File \"" + FILE_NAME +
                        "\". Invalid integer format.";
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

    public ArrayList<Ship> retrieveAll(){
        return shipList;
    }

    public Ship retrieveShip(String name){
        Ship shipToReturn = null;
        for (int i=0; i<shipList.size(); i++){
            Ship s = shipList.get(i);
            if (s.getName().equals(name)){
                shipToReturn = s.clone();
                break;
            }
        }
        return shipToReturn;
    }

    public void salvageShip(Ship s){
        s.setIsSunkStatus(false);
        s.restoreHP(s.getHP());
    }

}