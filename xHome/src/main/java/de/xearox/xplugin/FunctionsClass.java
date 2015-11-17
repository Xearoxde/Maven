package de.xearox.xplugin;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FunctionsClass {
	
	UtilClass utclass = new UtilClass();
	SetLanguageClass langClass = new SetLanguageClass();
	
	public void listHome(Plugin plugin, Player player){
		
		String filePath = "/data/";
		String fileName = "homelist";
		String fileType = ".yml";
		File homefile = new File(plugin.getDataFolder()+File.separator+filePath+fileName+fileType);
		if(!utclass.fileExist(homefile)){
			utclass.createFile(homefile);
		}
		YamlConfiguration yamlFile = utclass.yamlCon(homefile);
		player.sendMessage(yamlFile.getConfigurationSection("Player."+ player.getUniqueId().toString()).getKeys(false).toString());		
	}
	
	
	
}
