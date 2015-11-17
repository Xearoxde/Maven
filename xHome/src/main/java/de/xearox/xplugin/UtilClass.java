/**
 * 
 */
package de.xearox.xplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author Xearox
 *
 */
public class UtilClass {
	
	/**
	 * 
	 * @param fileName
	 * @return if the File exist or not
	 */
	public boolean fileExist(String fileName){
		File file = new File(fileName);
		if(file.exists()){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param player
	 * @return the DisplayName of the Player
	 */
	public String getPlayerName(Player player){
		return player.getDisplayName();
	}
	
	/**
	 * 
	 * @param player
	 * @return the UniqueID of the Player
	 */
	public String getPlayerUUID(Player player){
		return player.getUniqueId().toString();
	}
	
	public void listHome(Player player){
	
		fileExist("");
	}
	/**
	 *  
	 * @param plugin
	 * @param fileName The name of the File
	 */
	public void createFile(Plugin plugin, String fileName){
		File newFile = new File(plugin.getDataFolder()+File.separator+fileName+".yml");
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(newFile);
		if(!fileExist(plugin.getDataFolder()+File.separator+fileName+".yml")){
			try {
				yamlFile.save(newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			System.out.println("The file "+fileName+" does already exist. No operation needed");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
