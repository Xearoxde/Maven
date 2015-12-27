package de.xearox.xconomy.accounts;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xconomy.XConomy;
import de.xearox.xconomy.utility.Common;

public class CreateAccount {

	private XConomy plugin;
	
	public CreateAccount(XConomy plugin){
		this.plugin = plugin;
	}
	
	public boolean createNewAccount(OfflinePlayer player){
		Common common = plugin.getCommon();
		
		UUID uuid = player.getUniqueId();
		
		String playerUUID = uuid.toString();
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		Double startBalance = yamlConfigFile.getDouble("NewPlayer.StartMoney.Value");
		
		if(!common.hasAccount(player)){
			yamlFile.set("UUID."+playerUUID+".PlayerName", player.getName());
			yamlFile.set("UUID."+playerUUID+".Money", startBalance);
			
			plugin.logger.info("xConomy - INFO - Account created for "+player.getName());
			
			try {
				yamlFile.save(playerTable);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
}
