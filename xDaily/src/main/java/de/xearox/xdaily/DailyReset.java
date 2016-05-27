package de.xearox.xdaily;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.Utilz;

public class DailyReset {

private XDaily plugin;
private Utilz utilz;
private CreateFiles createFiles;
	
	public DailyReset(XDaily plugin){
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.createFiles = plugin.getCreateFiles();
	}
	
	public void checkIfPlayerJoinedEveryDay(Player player){
		File file = new File(plugin.getDataFolder()+File.separator+"/data/" + player.getUniqueId().toString() + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		Set<String> list = yamlFile.getConfigurationSection("Rewards").getKeys(false);
		String[] dateArray = list.toArray(new String[list.size()]);
		
		String dateATM = utilz.getDate();
		
		for(int i = 1; i < dateArray.length; i++){
			String date = dateArray[i];
			String preDate = dateArray[i-1];
			if(!yamlFile.getBoolean("Rewards."+preDate+".Get_Reward?") && date.equalsIgnoreCase(dateATM)){
				yamlFile.set("Rewards", null);
				try {
					yamlFile.save(file);
					createFiles.CreatePlayerFile(player, true);
					player.sendMessage(ChatColor.RED+"Hey! You've forgot to login yesterday. Your rewards were reset");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
