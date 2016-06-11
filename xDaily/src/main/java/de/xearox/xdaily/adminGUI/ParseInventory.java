package de.xearox.xdaily.adminGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
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
		
		System.out.println(newItemList.size());
		
		File file = new File(plugin.getDataFolder()+File.separator+"/data/rewards/" + inventoryName + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		if(file.exists()){
			player.sendMessage("The reward calender "+inventoryName+" already exists!");
			return false;
		}
		yamlFile.addDefault("Calendar.Name", inventoryName);
		
		for(int i = 0; i < inv.getContents().length; i++){
			if(i > 44) break;
			System.out.println(i);
			ItemStack itemStack[] = inv.getContents();
			if(itemStack[i] == null) continue;
			String itemName = itemStack[i].getItemMeta().getDisplayName();
			String itemType = itemStack[i].getType().toString();
			int itemValue = itemStack[i].getAmount();
			
			if(itemType.equalsIgnoreCase("DOUBLE_PLANT")){
				itemType = "money";
				itemValue = newItem[i].value;
			} else if(newItem[i].itemType.equalsIgnoreCase("Type Decoration")){
				itemType = "decoration";
			}
			
			yamlFile.addDefault("Rewards.Day."+(i+1)+".Name", itemName);
			yamlFile.addDefault("Rewards.Day."+(i+1)+".Type", itemType);
			yamlFile.addDefault("Rewards.Day."+(i+1)+".Value", itemValue);
			
		}
		
		yamlFile.options().copyDefaults(true);
		
		try{
			System.out.println(file.getAbsolutePath());
			yamlFile.save(file);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			player.sendMessage("Can't save the file!");
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
