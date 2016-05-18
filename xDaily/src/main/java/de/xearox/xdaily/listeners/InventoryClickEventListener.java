package de.xearox.xdaily.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.xearox.xdaily.XDaily;
import net.md_5.bungee.api.ChatColor;

public class InventoryClickEventListener implements Listener{
	
	private XDaily plugin;
	
	public InventoryClickEventListener(XDaily plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event){
		if(!ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("Daily Login Bonus")) return;
		
		File file = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		int dailyDays = yamlFile.getInt("Config.DailyBonus.Days");
		
		if(dailyDays > 54){
			dailyDays = 54;
		}
		
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		
		if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
				player.closeInventory();
				return;
			}
		
		final ArrayList<Material> matList = new ArrayList<Material>();
		
		matList.add(Material.DIAMOND);
		matList.add(Material.COAL);
		matList.add(Material.IRON_BLOCK);
		matList.add(Material.COAL);
		matList.add(Material.IRON_INGOT);
		matList.add(Material.COAL);
		matList.add(Material.DIAMOND_BLOCK);
		matList.add(Material.STONE);
		matList.add(Material.GOLD_INGOT);
		matList.add(Material.COAL);
		matList.add(Material.GOLD_BLOCK);
		matList.add(Material.COAL);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		String string = sdf.format(Calendar.getInstance().getTime());
		
		System.out.println(string);
		
		for(int i = 0; i < dailyDays+1; i++){
			if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Day "+i)){
				/*plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
					
					@Override
					public void run() {
						
						Random random = new Random();
						
						event.getCurrentItem().setType(matList.get(random.nextInt(matList.size())));
					}
				}, 0, 2);*/
				Random random = new Random();
				
				event.getCurrentItem().setType(matList.get(random.nextInt(matList.size())));
			} else {
				event.setCancelled(true);
			}
		}
	}
}
