package de.xearox.xdaily.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;
import net.md_5.bungee.api.ChatColor;

public class InventoryClickEventListener implements Listener{
	
	private XDaily plugin;
	private Utilz utilz;
	private SetLanguageClass langClass;
	
	public InventoryClickEventListener(XDaily plugin) {
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.langClass = plugin.getLanguageClass();
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event){
		langClass.setLanguage((Player) event.getWhoClicked(), false);
		if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase(utilz.Format(SetLanguageClass.TxtDailyLoginInventar))){

			Player player = (Player) event.getWhoClicked();
			event.setCancelled(true);
			
			File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
			YamlConfiguration yamlConfigFile;
			yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
			
			File file = new File(plugin.getDataFolder()+File.separator+"/data/" + player.getUniqueId().toString() + ".yml");
			YamlConfiguration yamlFile;
			yamlFile = YamlConfiguration.loadConfiguration(file);
			
			int dailyDays = yamlConfigFile.getInt("Config.DailyBonus.Days");
			
			if(dailyDays > 54){
				dailyDays = 54;
			}
			
			Set<String> list = yamlFile.getConfigurationSection("Rewards").getKeys(false);
			Material rewardMatType = null;
			
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
				player.closeInventory();
				return;
			}
			String dateATM = utilz.getDate(SetLanguageClass.TxtDateFormat, Locale.forLanguageTag(utilz.getPlayerLanguage(player)));
			int rewardValue = 0;
			String rewardType = "";
			
			for(String date : list){
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(date)
						&& !yamlFile.getBoolean("Rewards."+date+".Get_Reward?") && date.equalsIgnoreCase(dateATM)){
					
					boolean isVIP = yamlFile.getBoolean("Is_Player_VIP?");
					rewardType = yamlFile.getString("Rewards."+date+".Reward_Type").toUpperCase();
					rewardValue = yamlFile.getInt("Rewards."+date+".Reward_Value");
					int vipMulti = yamlConfigFile.getInt("Config.DailyBonus.VIP.Multiplier");
					
					if(isVIP){
						rewardValue *= vipMulti;
					}
					
					if(rewardType.equalsIgnoreCase("money")){
						XDaily.econ.depositPlayer(player, rewardValue);
						yamlFile.set("Rewards."+date+".Get_Reward?", true);
						event.getCurrentItem().setType(Material.BARRIER);
						try {
							yamlFile.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try{
							rewardMatType = Material.getMaterial(rewardType);
						} catch (Exception e){
							e.printStackTrace();
						}
						ItemStack itemStack = new ItemStack(rewardMatType);
						itemStack.setAmount(rewardValue);
						player.getInventory().addItem(itemStack);
						yamlFile.set("Rewards."+date+".Get_Reward?", true);
						event.getCurrentItem().setType(Material.BARRIER);
						String msg = SetLanguageClass.PlayerGetThisReward.replace("%value%", Integer.toString(rewardValue));
						msg = msg.replace("%reward%", rewardType.toLowerCase());
						player.sendMessage(utilz.Format(msg));
						try {
							yamlFile.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					
				} else if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(date)
						&& yamlFile.getBoolean("Rewards."+date+".Get_Reward?")){
					
					event.getWhoClicked().sendMessage(utilz.Format(SetLanguageClass.PlayerGetAlreadyReward));
					event.setCancelled(true);
					break;
				} else{
					event.setCancelled(true);
				}
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
			
			
			SimpleDateFormat sdf = new SimpleDateFormat(SetLanguageClass.TxtDateFormat);
			String string = sdf.format(Calendar.getInstance().getTime());
			
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
		} else {
			if(ChatColor.stripColor(event.getInventory().getName()).equalsIgnoreCase("xDaily Admin GUI - Index")){
				Player player = (Player) event.getWhoClicked();
				event.setCancelled(true);
				
				if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()){
						player.closeInventory();
						return;
					}
				try{
					if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Reload Server")){
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rl");
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return;
	}
}
