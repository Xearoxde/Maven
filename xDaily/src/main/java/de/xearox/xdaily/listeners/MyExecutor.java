package de.xearox.xdaily.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xdaily.XDaily;
import net.md_5.bungee.api.ChatColor;

public class MyExecutor implements CommandExecutor {

	private XDaily plugin;
	private Calendar calendar = Calendar.getInstance();
	
	public MyExecutor(XDaily plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("daily")){
			Player player = (Player) sender;
			Inventory inv;
			
			ItemStack slot1 = new ItemStack(Material.BEDROCK);
			ItemMeta slot1Meta = slot1.getItemMeta();
			ArrayList<String> lore = new ArrayList<String>();
			
			File file = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
			YamlConfiguration yamlFile;
			yamlFile = YamlConfiguration.loadConfiguration(file);
			
			int dailyDays = yamlFile.getInt("Config.DailyBonus.Days");
			int maxDays = 0;
			
			if(dailyDays < 9){
				maxDays = 9;
			} else
			
			if(dailyDays > 9 && dailyDays < 18){
				maxDays = 18;
			} else
			
			if(dailyDays > 18 && dailyDays < 27){
				maxDays = 27;
			} else
			
			if(dailyDays > 27 && dailyDays < 36){
				maxDays = 36;
			} else
			
			if(dailyDays > 36 && dailyDays < 45){
				maxDays = 45;
			} else
			
			if(dailyDays > 45 && dailyDays < 54){
				maxDays = 54;
			} else
			
			if(dailyDays >= 54){
				maxDays = 54;
				dailyDays = 54;
			}
			
			inv = Bukkit.createInventory(null, maxDays, ChatColor.BLUE+"Daily Login Bonus");
			
			lore.add(ChatColor.YELLOW + "Datum");
			
			for(int i = 0; i < dailyDays; i++){
				
				slot1Meta.setDisplayName(ChatColor.RED+"Day "+(i+1));
				
				slot1Meta.setLore(lore);
				
				slot1.setItemMeta(slot1Meta);
				
				inv.setItem(i, slot1);
			}
			
			player.openInventory(inv);
			
			player.sendMessage("Test");
			player.sendMessage(calendar.getTime().toString());
			return true;
		}
		
		if(label.equalsIgnoreCase("test")){
			Player player = (Player) sender;
			
			player.sendMessage("test33");
			return true;
		}
		
		return false;
	}

}
