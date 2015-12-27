package de.xearox.xconomy.utility;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xconomy.XConomy;

public class Common {

	private XConomy plugin;
	
	public Common(XConomy plugin){
		this.plugin = plugin;
	}
	
	public boolean hasAccount(OfflinePlayer player){
		
		UUID playerUUID = player.getUniqueId();
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		
		try{
			if(yamlFile.contains("UUID."+playerUUID.toString())){
				return true;
			} else {
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	public String getCurrencyNameSingular(){
		
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		return yamlFile.getString("Currency.Name.Singular");
	}
	
	public String getCurrencyNamePlural(){
		
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		return yamlFile.getString("Currency.Name.Plural");
	}






}
