package de.xearox.xdaily.adminGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.utilz.Utilz;

public class ParseInventory {

	
	private XDaily plugin;
	private Utilz utilz;
	
	
	
	public ParseInventory (XDaily plugin){
		this.plugin = plugin;
	}
	
	public boolean createNewRewardFile(Inventory inv, Player player){
		//Setting Up New Yaml File
		String inventoryName = inv.getTitle();
		
		
		File file = new File(plugin.getDataFolder()+File.separator+"/data/rewards/" + inventoryName + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		if(file.exists()){
			player.sendMessage("The reward calender "+inventoryName+" already exists!");
			return false;
		}
		yamlFile.addDefault("Calendar.Name", inventoryName);
		
		for(int i = 0; i < inv.getContents().length; i++){
			ItemStack itemStack[] = inv.getContents();
			String itemName = itemStack[i].getItemMeta().getDisplayName();
			String itemType = itemStack[i].getType().toString();
			int itemValue = itemStack[i].getAmount();
			
			yamlFile.addDefault("Rewards.Day."+i+1+".Name", itemName);
			yamlFile.addDefault("Rewards.Day."+i+1+".Type", itemType);
			yamlFile.addDefault("Rewards.Day."+i+1+".Value", itemValue);
			
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
