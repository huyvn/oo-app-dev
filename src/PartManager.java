import javax.annotation.processing.FilerException;
import java.util.*;
import java.io.*;

public class PartManager{
	private ArrayList<Part> partList;

	private final String FILE_NAME_ENGINE = "data/engines.csv";
	private final String FILE_NAME_FIGUREHEAD = "data/figureheads.csv";
	private final String FILE_NAME_HULL = "data/hulls.csv";
	private final String FILE_NAME_SAIL = "data/sails.csv";
	private final String FILE_NAME_STABILIZER = "data/stabilizers.csv";
    private final String CLASS_NAME = "PartManager";

    public PartManager() throws DataException{
    	partList = new ArrayList<Part>();

    	load(FILE_NAME_ENGINE,"engine");
    	load(FILE_NAME_FIGUREHEAD,"figurehead");
    	load(FILE_NAME_HULL,"hull");
    	load(FILE_NAME_SAIL,"sail");
    	load(FILE_NAME_STABILIZER,"stabilizer");
    }

    private void load(String filename, String type) throws DataException{
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
                String speedStr = fileIn.next();
                String hpStr = fileIn.next();
                String capacityStr = fileIn.next();
                String weightStr = fileIn.next();
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
                	int capacity = Integer.parseInt(capacityStr);
                	int weight = Integer.parseInt(weightStr);
                	int lvlReq = Integer.parseInt(lvlReqStr);
                	int goldReq = Integer.parseInt(goldReqStr);
                	int woodReq = Integer.parseInt(woodReqStr);
                	int oreReq = Integer.parseInt(oreReqStr);
                	int plasmaRockReq = Integer.parseInt(plasmaRockReqStr);
                	Part p = new Part(type,name,speed,hp,capacity,weight,lvlReq,goldReq,woodReq,oreReq,plasmaRockReq,port);

                	partList.add(p);
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

    public ArrayList<Part> retrieveList(String type){
    	ArrayList<Part> listToReturn = new ArrayList<Part>();
    	for (int i=0; i<partList.size();i++){
    		Part p = partList.get(i);
    		if (p.getType().equalsIgnoreCase(type)){
    			listToReturn.add(p);
    		}
    	}
    	return listToReturn;
    }

    public ArrayList<Part> retrieveAll(){
    	return partList;
    }

    public Part retrievePart(String name){
        Part partToReturn = null;
        for (int i=0;i<partList.size();i++){
            Part p = partList.get(i);
            if (p.getName().equals(name)){
                partToReturn = p.clone();
                break;
            }
        }
        return partToReturn;
    }
}