package de.xearox.xdaily.adminGUI;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.utilz.Utilz;

public class ParseInventory {

	
	private XDaily plugin;
	private Utilz utilz;
	
	private ArrayList<NewItem> newItemList = new ArrayList<>();
	
	
	public ParseInventory (XDaily plugin){
		this.plugin = plugin;
	}
	
	public boolean createNewRewardFile(Inventory inv, Player player, ArrayList<NewItem> newItemList){
		//Setting Up New Yaml File
		String inventoryName = ChatColor.stripColor(inv.getTitle()).substring(17);
		NewItem[] newItem = newItemList.toArray(new NewItem[newItemList.size()]);
		
		File file = new File(plugin.getDataFolder()+File.separator+"/data/rewards/" + inventoryName + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		if(file.exists()){
			player.sendMessage(ChatColor.RED+"The reward calender "+inventoryName+" already exists!");
			return false;
		}
		yamlFile.addDefault("Calendar.Name", inventoryName);
		int day = 1;
		int decoIndex = 1;
		
		for(NewItem item : newItemList){
			ItemStack itemStack = item.itemStack;
			Material material = itemStack.getType();
			String itemName = item.displayName;
			String itemType = item.itemType;
			int itemSlot = item.position;
			int itemValue = item.value;
			
			if(itemName == null){
				itemName = material.name();
			}
			
			if(itemType.equalsIgnoreCase("Type Decoration")){
				itemType = "decoration";
			}
			
			if(itemType.equalsIgnoreCase("decoration")){
				yamlFile.addDefault("Decoration.Slot."+decoIndex+".Name", material.name());
				yamlFile.addDefault("Decoration.Slot."+decoIndex+".Type", itemType);
				yamlFile.addDefault("Decoration.Slot."+decoIndex+".Value", itemValue);
				yamlFile.addDefault("Decoration.Slot."+decoIndex+".Slot", itemSlot);
				decoIndex++;
			} else {
				yamlFile.addDefault("Rewards.Day."+day+".Name", itemName);
				yamlFile.addDefault("Rewards.Day."+day+".Type", material.name());
				yamlFile.addDefault("Rewards.Day."+day+".Value", itemValue);
				yamlFile.addDefault("Rewards.Day."+day+".Slot", itemSlot);
				day++;
			}
			
			
		}
		
		yamlFile.options().copyDefaults(true);
		
		try{
			yamlFile.save(file);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			player.sendMessage("Can't save the file!");
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
