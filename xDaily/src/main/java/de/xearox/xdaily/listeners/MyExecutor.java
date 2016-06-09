package de.xearox.xdaily.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

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
import de.xearox.xdaily.adminGUI.GuiActions;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;
import net.md_5.bungee.api.ChatColor;

public class MyExecutor implements CommandExecutor {

	private XDaily plugin;
	private CreateRewards createRewards;
	private CreateFiles createFiles;
	private Utilz utilz;
	private SetLanguageClass langClass;
	private GuiActions guiActions;
	
	public MyExecutor(XDaily plugin){
		this.plugin = plugin;
		this.createRewards = plugin.getCreateRewards();
		this.createFiles = plugin.getCreateFiles();
		this.utilz = plugin.getUtilz();
		this.langClass = plugin.getLanguageClass();
		this.guiActions = plugin.getGuiActions();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("daily")){
			if(!sender.hasPermission("daily")){
				langClass.setLanguage((Player)sender, false);
				sender.sendMessage(utilz.Format(SetLanguageClass.PlayerDontHavePermission));
				return true;
			}
			if(args.length == 0){
				if(!(sender instanceof Player)){
					sender.sendMessage(utilz.Format(SetLanguageClass.ConsoleCantDoThat));
					return true;
				}
				Player player = (Player) sender;
				langClass.setLanguage(player, false);
				Inventory inv;
				
				ItemStack slot1 = new ItemStack(Material.BEDROCK);
				ItemMeta slot1Meta = slot1.getItemMeta();
				ArrayList<String> lore = new ArrayList<String>();
				
				File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
				YamlConfiguration yamlConfigFile;
				yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
				
				File file = new File(plugin.getDataFolder()+File.separator+"/data/" + player.getUniqueId().toString() + ".yml");
				YamlConfiguration yamlFile;
				yamlFile = YamlConfiguration.loadConfiguration(file);
				
				int dailyDays = yamlConfigFile.getInt("Config.DailyBonus.Days");
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
				
				inv = Bukkit.createInventory(null, maxDays, utilz.Format(SetLanguageClass.TxtDailyLoginInventar));
				
				Set<String> list = yamlFile.getConfigurationSection("Rewards").getKeys(false);
				
				lore.add(""); // 0 Date or Description
				lore.add(""); // 1 Reward Type
				
				int i = 0;
				try{
					for(String date : list){
						String rewardType = yamlFile.getString("Rewards."+date+".Reward_Type");
						String rewardValue = yamlFile.getString("Rewards."+date+".Reward_Value");
						String vipMulti = yamlConfigFile.getString("Config.DailyBonus.VIP.Multiplier");
						boolean getReward = yamlFile.getBoolean("Rewards."+date+".Get_Reward?");
						//slot1Meta.setDisplayName(ChatColor.RED+yamlFile.getString("Rewards."+date+".Reward_Name"));
						slot1Meta.setDisplayName(ChatColor.RED+date);
						if(yamlFile.getString("Rewards."+date+".Reward_Type").equalsIgnoreCase("money") && !getReward){
							slot1.setType(Material.DOUBLE_PLANT);
						} else if(getReward){
							slot1.setType(Material.BARRIER);
						} else {
							slot1.setType(Material.getMaterial(rewardType.toUpperCase()));
						}
						//lore.set(0, ChatColor.YELLOW+date);
						lore.set(1, ChatColor.DARK_PURPLE+rewardType+" x"+rewardValue);
						if(yamlFile.getBoolean("Is_Player_VIP?")){
							lore.add(ChatColor.GREEN+"VIP Bonus : x"+vipMulti);
							slot1Meta.setLore(lore);
							slot1.setItemMeta(slot1Meta);
							inv.setItem(i, slot1);
							lore.remove(2);
						} else {
							slot1Meta.setLore(lore);
							slot1.setItemMeta(slot1Meta);
							inv.setItem(i, slot1);
						}
						
						i++;
					}
				} catch(Exception e){
					e.printStackTrace();
				}
				
				
				
				
				/*for(int i = 0; i < dailyDays; i++){
					
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
				}*/
				
				player.openInventory(inv);
				return true;
			} else if(args.length == 1){
				if(args[0].equalsIgnoreCase("admin")){
					if(!(sender instanceof Player)){
						sender.sendMessage(utilz.Format(SetLanguageClass.ConsoleCantDoThat));
						return true;
					}
					if(!sender.hasPermission("daily.admin")){
						sender.sendMessage(utilz.Format(SetLanguageClass.PlayerDontHavePermission));
						return true;
					}
					Player player = (Player) sender;
					
					//createRewards.createAdminGUI(player);
					guiActions.runActions(player);
					return true;
				}
			}else if(args.length == 2){
				if(args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("rewriteplayerfiles")){
					if(!(sender instanceof Player)){
						sender.sendMessage(utilz.Format(SetLanguageClass.ConsoleCantDoThat));
						return true;
					}
					if(!sender.hasPermission("daily.admin.rewriteplayerfiles")){
						sender.sendMessage(utilz.Format(SetLanguageClass.PlayerDontHavePermission));
						return true;
					}
					Player player = (Player) sender;
					createFiles.CreatePlayerFile(player, true);
					return true;
					
				}
			}else if(args.length == 3){
				if(args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("addVIP")){
					/*if(!(sender instanceof Player)){
						sender.sendMessage(ChatColor.RED+"The console can't do this!");
						return true;
					}*/
					
					try {
						Writer writer = new BufferedWriter(new FileWriter(plugin.getDataFolder()+File.separator+"/data/vip-player.txt", true));
						
						String addingPlayer = utilz.getUUIDFromMojang(args[2]);
						
						if(addingPlayer.equalsIgnoreCase("")){
							sender.sendMessage(utilz.Format(SetLanguageClass.ErrMojangAPINotAvailable));
							writer.close();
							return true;
						}
						
						
						ArrayList<String> fileContent = utilz.readFileByLine(new File(plugin.getDataFolder()+File.separator+"/data/vip-player.txt"));
						
						if(fileContent.contains(addingPlayer)){
							String message = utilz.Format(SetLanguageClass.AdmPlayerAlreadyInVIPFile);
							message.replace("%player%", args[2]);

							sender.sendMessage(message);
							writer.close();
							return true;
						}
						
						
						writer.write(addingPlayer);
						writer.write(System.lineSeparator());
						writer.close();
						String message = utilz.Format(SetLanguageClass.AdmPlayerAddedToVIPFile);
						message.replace("%player%", args[2]);
						message.replace("%uuid%", addingPlayer);
						sender.sendMessage(message);
						return true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		
		if(label.equalsIgnoreCase("test")){
			StringBuilder sb = new StringBuilder();
			
			sb.append("haaaalo").append(" muhahahaha");
			
			System.out.println(sb.toString());
            
			/*if((sender instanceof Player)){
				Player player = (Player) sender;
				langClass.setLanguage(player, false);
			} else {
				langClass.setLanguage(null, true);
			}
			
			try {
				String addingPlayer = utilz.getUUIDFromMojang(args[0]);
				if(addingPlayer.equalsIgnoreCase("")){
					sender.sendMessage(utilz.Format(SetLanguageClass.ErrMojangAPINotAvailable));
					return true;
				}
				sender.sendMessage("Test : "+addingPlayer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			//System.out.println ((char)27 + "[31mThis is just a test" + (char)27 +"[0m");
			//System.out.println ((char)27 + "[31;1mThis is just a test" + (char)27 +"[0m");
			
			//XDaily.econ.depositPlayer(player, 100);
			//player.sendMessage("test33");
			return true;
		}
		
		return false;
	}

}
