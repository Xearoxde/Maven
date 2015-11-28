/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xplugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @author Xearox
 *
 */
public class UtilClass {
	
	private MainClass plugin;
	
	private SetLanguageClass langClass;
	
	public UtilClass(MainClass plugin){
		this.plugin = plugin;
		this.langClass = plugin.getLangClass();
	}
	
	/**
	 * 
	 * @param fileName String
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
	 * @param filename File
	 * @return if the File exist or not
	 */
	public boolean fileExist(File filename){
		if(filename.exists()){
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
	
	
	/**
	 *  
	 * @param plugin
	 * @param filePath The path of the File. It can be empty
	 * @param fileName The name of the File
	 * @param fileType The type of the File. e.g. .yml
	 */
	public void createFile(Plugin plugin,String filePath, String fileName, String fileType){
		File newFile = new File(plugin.getDataFolder()+File.separator+fileName+fileType);
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(newFile);
		if(!fileExist(plugin.getDataFolder()+File.separator+filePath+fileName+fileType)){
			try {
				yamlFile.save(newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("The file "+fileName+" does already exist. No operation needed!");
		}
		
	}
	
	public void createFile(File file){
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
		if(!fileExist(file)){
			try {
				yamlFile.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("The file "+file.getName().toString()+" does already exist. No operation needed!");
		}
	}
	
	/**
	 * 
	 * @param p Player
	 * @return the Language of the players client
	 */
	public String getPlayerLanguage(Player p){		
		try {
			Object obj = getMethod("getHandle", p.getClass()).invoke(p, (Object[]) null);
			Field f = obj.getClass().getDeclaredField("locale");
			f.setAccessible(true);
			String language = (String) f.get(obj);
			return language;
		} catch (Exception e) {
			return "en";
		}
	}
	/**
	 * 
	 * @param n
	 * @param c
	 * @return
	 */
	private Method getMethod(String n, Class<?> c) {
		for (Method m : c.getDeclaredMethods()) {
			if (m.getName().equals(n))
				return m;
		}
		return null;
	}
	
	public YamlConfiguration yamlCon(File file){
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public File getFile(String filePath, String fileName, String fileType ){
		File file;
		file = new File(plugin.getDataFolder()+File.separator+filePath+fileName+"."+fileType);
		return file;
	}
	
	public void createLanguageFiles(){
		String fileName;
		String filePath = "/locate/";
		String fileType = "yml";
		File file;
		String tempString;
		
		Languages[] languages = Languages.values();
		
		for(int i=0; i<languages.length; i++){
			fileName = languages[i].toString();
			tempString = langClass.getLanguageFileName(fileName);
			file = getFile(filePath, tempString, fileType);
			createFile(file);
			YamlConfiguration yamlFile = yamlCon(file);
			if(languages[i].toString().equalsIgnoreCase("nl")){
				yamlFile.options().header("That is the Language File. Here you can change everthing. You can use color codes. e.g. $3");
				yamlFile.addDefault("Message.HomeSet.MainHome", "Je huis is ingesteld");
				yamlFile.addDefault("Message.HomeSet.DifferentHome", "Je huis %home% is ingesteld");
				yamlFile.addDefault("Message.Teleport.ToMainHome", "Je bent geteleporteerd naar je huis");
				yamlFile.addDefault("Message.Teleport.ToDifferentHome", "Je bent geteleporteerd naar %home%");
				yamlFile.addDefault("Message.Plugin.Enabled", "De plugin is geactiveerd!");
				yamlFile.addDefault("Message.Plugin.Disabled", "De plugin is gedeactiveerd!");
				yamlFile.addDefault("Message.Plugin.Reloaded", "$3De plugin is herladen!");
				yamlFile.addDefault("Message.TeleportCosts.ToMainHome", "De teleportatie naar je huis kostte %home%");
				yamlFile.addDefault("Message.TeleportCosts.ToDifferentHome", "De teleportatie naar een ander huis kost %home%");
				yamlFile.addDefault("Message.Delete.Sucessfully", "$2Je huis %home% was succesvol verwijderd!");
				yamlFile.addDefault("Message.Error.TPDiffHomeNotFound", "Je huis %home% is kon niet worden gevonden!");
				yamlFile.addDefault("Message.Error.DeleteDiffHome", "Je huis %home% kon niet worden verwijderd, omdat het niet gevonden kon worden!");
				yamlFile.addDefault("Message.Error.ListHomeNotFound", "$4Je huis kon niet worden gevonden. Wees er zeker van dat je het correct hebt gespeld!");
				yamlFile.addDefault("Message.Error.DelHomeNotFound", "$4Je huis kon niet worden gevonden. Wees er zeker van dat je het correct hebt gespeld!");
				yamlFile.addDefault("Message.Permission.Error", "$4Sorry, maar je hebt geen permissie om dat te doen!");
				yamlFile.options().copyDefaults(true);
			}else{
				yamlFile.options().header("That is the Language File. Here you can change everthing. You can use color codes. e.g. $3");
				yamlFile.addDefault("Message.HomeSet.MainHome", "Your home was set");
				yamlFile.addDefault("Message.HomeSet.DifferentHome", "Your home %home% was set");
				yamlFile.addDefault("Message.Teleport.ToMainHome", "You was teleported to home");
				yamlFile.addDefault("Message.Teleport.ToDifferentHome", "You was teleported to %home%");
				yamlFile.addDefault("Message.Plugin.Enabled", "The plugin was enabled!");
				yamlFile.addDefault("Message.Plugin.Disabled", "The plugin was disabled!");
				yamlFile.addDefault("Message.Plugin.Reloaded", "$3The plugin was reloaded!");
				yamlFile.addDefault("Message.TeleportCosts.ToMainHome", "The teleport to your home cost %home%");
				yamlFile.addDefault("Message.TeleportCosts.ToDifferentHome", "The teleport to a different home cost %home%");
				yamlFile.addDefault("Message.Delete.Sucessfully", "$2Your home %home% was successfully deleted!");
				yamlFile.addDefault("Message.Error.TPDiffHomeNotFound", "Your home %home% was not found!");
				yamlFile.addDefault("Message.Error.DeleteDiffHome", "Your home %home% can not be deleted, because it could not found!");
				yamlFile.addDefault("Message.Error.ListHomeNotFound", "$4Your home was not found. Make sure you wrote it correct!");
				yamlFile.addDefault("Message.Error.DelHomeNotFound", "$4Your home was not found. Make sure you wrote it correct!");
				yamlFile.addDefault("Message.Permission.Error", "$4Sorry but you don't have the permission to do that!");
				yamlFile.options().copyDefaults(true);
			}
			try {
				yamlFile.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String Format(String format){
		return ChatColor.translateAlternateColorCodes('$', format);
	}
	
	
	
	
	
}
