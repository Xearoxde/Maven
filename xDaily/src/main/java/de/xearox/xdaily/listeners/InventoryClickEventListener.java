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
import de.xearox.xdaily.adminGUI.GuiActions;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;
import net.md_5.bungee.api.ChatColor;

public class InventoryClickEventListener implements Listener{
	
	private XDaily plugin;
	private Utilz utilz;
	private SetLanguageClass langClass;
	private GuiActions guiActions;
	
	public InventoryClickEventListener(XDaily plugin) {
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.langClass = plugin.getLanguageClass();
		this.guiActions = plugin.getGuiActions();
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
			
			
			File file;
			
			if(XDaily.pluginVersion.contains("0.6")){
				file = new File(plugin.getDataFolder()+File.separator+"/data/playerData/" + player.getUniqueId().toString() + ".yml");
			} else {
				file = new File(plugin.getDataFolder()+File.separator+"/data/" + player.getUniqueId().toString() + ".yml");
			}
			
			YamlConfiguration yamlFile;
			yamlFile = YamlConfiguration.loadConfiguration(file);
			
			File permGroupsFile = new File(plugin.getDataFolder()+File.separator+"/data/permGroups/groups.yml");		
			YamlConfiguration yamlpermGroupsFile;
			yamlpermGroupsFile = YamlConfiguration.loadConfiguration(permGroupsFile);
			
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
			boolean isVIP = false;
			String rewardType = "";
			int vipMulti = 1;
			String rewardCommand = "";
			
			for(String date : list){
				if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(date)
						&& !yamlFile.getBoolean("Rewards."+date+".Get_Reward?") && date.equalsIgnoreCase(dateATM)){
					
					if(yamlConfigFile.getBoolean("Config.Daily.UsePermGroupsInsteadVIP?")){
						if(yamlpermGroupsFile.getBoolean(XDaily.perm.getPrimaryGroup(player)+".CanUseMulti?")){
							isVIP = true;
							vipMulti = yamlpermGroupsFile.getInt(XDaily.perm.getPrimaryGroup(player)+".Multiplier");
						}
					} else {
						isVIP = yamlFile.getBoolean("Is_Player_VIP?");
						if(isVIP){
							vipMulti = yamlConfigFile.getInt("Config.DailyBonus.VIP.Multiplier");
						}
					}
					
					
					rewardType = yamlFile.getString("Rewards."+date+".Reward_Type").toUpperCase();
					rewardValue = yamlFile.getInt("Rewards."+date+".Reward_Value");
					
					if(isVIP){
						rewardValue *= vipMulti;
					}
					
					if(rewardType.equalsIgnoreCase("money")){
						XDaily.econ.depositPlayer(player, rewardValue);
						yamlFile.set("Rewards."+date+".Get_Reward?", true);
						if(!yamlConfigFile.getBoolean("Config.DailyBonus.Rewards.HideBonus?")){
							event.getCurrentItem().setType(Material.BARRIER);
						} else {
							event.getCurrentItem().setType(Material.DOUBLE_PLANT);
						}
						String msg = SetLanguageClass.PlayerGetThisReward.replace("%value%", Integer.toString(rewardValue));
						msg = msg.replace("%reward%", rewardType.toLowerCase());
						player.sendMessage(utilz.Format(msg));
						try {
							yamlFile.save(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if (rewardType.equalsIgnoreCase("command")){
						rewardCommand = yamlFile.getString("Rewards."+date+".Reward_Value").replace("%player%", player.getDisplayName());
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), rewardCommand);
						yamlFile.set("Rewards."+date+".Get_Reward?", true);
						
						if(!yamlConfigFile.getBoolean("Config.DailyBonus.Rewards.HideBonus?")){
							event.getCurrentItem().setType(Material.BARRIER);
						} else {
							event.getCurrentItem().setType(Material.COMMAND);
						}
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
						while(rewardValue > 64){
							itemStack.setAmount(rewardValue);
							player.getInventory().addItem(itemStack);
							rewardValue -= 64;
						}
						itemStack.setAmount(rewardValue);
						player.getInventory().addItem(itemStack);
						
						if(!yamlConfigFile.getBoolean("Config.DailyBonus.Rewards.HideBonus?")){
							event.getCurrentItem().setType(Material.BARRIER);
						} else {
							event.getCurrentItem().setType(Material.getMaterial(rewardType.toUpperCase()));
						}
						yamlFile.set("Rewards."+date+".Get_Reward?", true);
						String msg = SetLanguageClass.PlayerGetThisReward.replace("%value%", Integer.toString(rewardValue*vipMulti));
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
			if(ChatColor.stripColor(event.getInventory().getName()).contains("xDaily Admin")|| ChatColor.stripColor(event.getInventory().getName()).contains("Keyboard: ") 
					|| ChatColor.stripColor(event.getInventory().getName()).contains("Change Default Calendar")){
				Player player = (Player) event.getWhoClicked();
				event.setCancelled(true);
				
				if(event.getCurrentItem() == null){
						//player.closeInventory();
						return;
					}
				
				guiActions.runActions(player,event);
				/*
				try{
					if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Reload Server")){
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rl");
					}
					if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close Inventory")){
						player.closeInventory();
					}
				} catch (Exception e){
					e.printStackTrace();
				}*/
			}
		}
		return;
	}
}
