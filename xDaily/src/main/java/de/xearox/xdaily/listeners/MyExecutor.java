package de.xearox.xdaily.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
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
import de.xearox.xdaily.admgui.CreateRewards;
import net.md_5.bungee.api.ChatColor;

public class MyExecutor implements CommandExecutor {

	private XDaily plugin;
	private CreateRewards createRewards;
	private Calendar calendar = Calendar.getInstance();
	
	public MyExecutor(XDaily plugin){
		this.plugin = plugin;
		this.createRewards = plugin.getCreateRewards();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("daily")){
			if(args.length == 0){
				Player player = (Player) sender;
				Inventory inv;
				
				ItemStack slot1 = new ItemStack(Material.BEDROCK);
				ItemMeta slot1Meta = slot1.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				
				File file = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
				YamlConfiguration yamlFile;
				yamlFile = YamlConfiguration.loadConfiguration(file);
				
				boolean randomItems = yamlFile.getBoolean("Config.DailyBonus.RandomItems");
				
				int dailyDays = yamlFile.getInt("Config.DailyBonus.Days");
				int maxDays = 0;
				
				if(dailyDays <= 9){
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
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
				Calendar calendar = Calendar.getInstance();
				
				String myDate = sdf.format(Calendar.getInstance().getTime());
				
				lore.add(ChatColor.YELLOW + myDate);
				for(int i = 0; i < dailyDays; i++){
					
					slot1Meta.setDisplayName(ChatColor.RED+"Day "+(i+1));
					if(i == 0){
						myDate = sdf.format(calendar.getTime());
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, 1);
						myDate = sdf.format(calendar.getTime());
					}
					
					lore.set(0, ChatColor.YELLOW + myDate);
					
					slot1Meta.setLore(lore);
					
					slot1.setItemMeta(slot1Meta);
					
					inv.setItem(i, slot1);
				}
				
				player.openInventory(inv);
				return true;
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("admin")){
					Player player = (Player) sender;
					
					createRewards.createAdminGUI(player);
					return true;
				}
			}
		}
		
		if(label.equalsIgnoreCase("test")){
			Player player = (Player) sender;
			
			player.sendMessage("test33");
			return true;
		}
		
		return false;
	}

}
