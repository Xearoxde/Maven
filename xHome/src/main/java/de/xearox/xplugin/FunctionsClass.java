package de.xearox.xplugin;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FunctionsClass {
	
	UtilClass utclass = new UtilClass();
	
	public YamlConfiguration yamlCon(File file){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public void listHome(Plugin plugin, Player player){
		
		String filePath = "/data/";
		String fileName = "homelist";
		String fileType = ".yml";
		File homefile = new File(plugin.getDataFolder()+File.separator+filePath+fileName+fileType);
		if(!utclass.fileExist(homefile)){
			utclass.createFile(homefile);
		}
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(homefile);
		player.sendMessage(yamlFile.getConfigurationSection("Player."+ player.getUniqueId().toString()).getKeys(false).toString());
		
	}
	
	
	
}
