import java.util.*;
import java.io.*;

public class GameControl{
	private CharacterManager charMgr;
	private PartManager partMgr;
	private WeaponManager wpnMgr;
	private ShipManager shipMgr;
	private InventoryManager inventoryMgr;

	private ProcessLoginCtrl loginCtrl;
	private ProcessViewStatisticsCtrl viewStatsCtrl;
	private ProcessShipCtrl shipCtrl;
	private ProcessPartCtrl partCtrl;
	private ProcessWeaponCtrl wpnCtrl;
	private ProcessEquipCtrl equipCtrl;
	private ProcessRepairCtrl repairCtrl;
	private ProcessPVPCtrl pvpCtrl;

	public GameControl() throws DataException{
		try {
			partMgr = new PartManager();
			wpnMgr = new WeaponManager();
			shipMgr = new ShipManager();
			charMgr = new CharacterManager(wpnMgr,partMgr,shipMgr);
			inventoryMgr = new InventoryManager(charMgr,partMgr,wpnMgr);
		}
		catch (DataException e) {
			throw e;
		}
	}

	public ProcessLoginCtrl createProcessLoginCtrl(){
		loginCtrl = new ProcessLoginCtrl(charMgr);
		return loginCtrl;
	}
	public ProcessViewStatisticsCtrl createProcessViewStatisticsCtrl(Character character){
		viewStatsCtrl = new ProcessViewStatisticsCtrl(character,this.charMgr,this.inventoryMgr);
		return viewStatsCtrl;
	}
	public ProcessShipCtrl createProcessShipCtrl(Character character){
		shipCtrl = new ProcessShipCtrl(character,this.charMgr,this.shipMgr,this.inventoryMgr);
		return shipCtrl;
	}
	public ProcessPartCtrl createProcessPartCtrl(Character character){
		partCtrl = new ProcessPartCtrl(character,this.charMgr,this.partMgr,this.inventoryMgr);
		return partCtrl;
	}
	public ProcessWeaponCtrl createProcessWeaponCtrl(Character character){
		wpnCtrl = new ProcessWeaponCtrl(character,this.charMgr,this.wpnMgr,this.inventoryMgr);
		return wpnCtrl;
	}

	public ProcessEquipCtrl createProcessEquipCtrl(Character character){
		equipCtrl = new ProcessEquipCtrl(character,this.charMgr,this.inventoryMgr);
		return equipCtrl;
	}
	public ProcessRepairCtrl createProcessRepairCtrl(Character character){
		repairCtrl = new ProcessRepairCtrl(character,this.charMgr);
		return repairCtrl;
	}
	public ProcessPVPCtrl createProcessPVPCtrl(Character character){
		pvpCtrl = new ProcessPVPCtrl(character,this.charMgr);
		return pvpCtrl;
	}
}