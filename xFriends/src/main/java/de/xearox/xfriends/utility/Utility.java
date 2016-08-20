package de.xearox.xfriends.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.xearox.xfriends.XFriends;

public class Utility {
	
	private XFriends plugin;
	
	public Utility(XFriends plugin) {
		this.plugin = plugin;
	}
	
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
	public void createYAMLFile(Plugin plugin,String filePath, String fileName, String fileType){
		File newFile = new File(plugin.getDataFolder()+File.separator+fileName+fileType);
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(newFile);
		if(!fileExist(plugin.getDataFolder()+File.separator+filePath+fileName+fileType)){
			try {
				yamlFile.save(newFile);
				System.out.println("xFriends - INFO - The file "+newFile.getName().toString()+" created!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println("xFriends - INFO - The file "+fileName+" does already exist. No operation needed!");
		}
		
	}
	
	public void createYAMLFile(File file){
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(file);
		if(!fileExist(file)){
			try {
				yamlFile.save(file);
				System.out.println("xFriends - INFO - The file "+file.getName().toString()+" created!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("xFriends - INFO - The file "+file.getName().toString()+" does already exist. No operation needed!");
		}
	}
	
	public void createFile(String fileName){
		if(!fileExist(plugin.getDataFolder()+File.separator+fileName)){
			try {
				File file = new File(fileName);
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void createFile(File file){
		if(!fileExist(file)){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createDir(File file){
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 
	 * @param p Player
	 * @return the Language of the players client
	 */
	public String getPlayerLanguage(Player player){		
		try {
			Object obj = getMethod("getHandle", player.getClass()).invoke(player, (Object[]) null);
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
		File dir = new File(plugin.getDataFolder()+File.separator+filePath+"/");
		createDir(dir);
		file = new File(plugin.getDataFolder()+File.separator+filePath+"/"+fileName+"."+fileType);
		createFile(file);
		return file;
	}
	
	public String Format(String format){
		return ChatColor.translateAlternateColorCodes('$', format);
	}
	
	public String getDate(String dateFormat,Locale...locales){
		SimpleDateFormat sdf;
		if(locales.length == 0){
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat(dateFormat,locales[0]);
		}
		String date = sdf.format(Calendar.getInstance().getTime());
		return date;
	}
	
	public ArrayList<String> readFileByLine(File file){
		try {
			Scanner scanner = new Scanner(file);
			ArrayList<String> list = new ArrayList<String>();
			while(scanner.hasNextLine()){
				list.add(scanner.nextLine());
			}
			scanner.close();
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
    public String getUUIDFromMojang(String playerName) throws IOException {
    	URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+playerName);
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	    StringBuilder sb = new StringBuilder();
	
	    String inputLine;
	    while ((inputLine = in.readLine()) != null) sb.append(inputLine);
	    
	    in.close();
	    
	    if(sb.toString().equalsIgnoreCase("")){
	    	return "";
	    }
	    
	    String content = sb.toString();
	    
	    content = content.substring(content.indexOf("\"id\":\"")+6, content.indexOf("\",\"name\""));
	    
	    content = new StringBuilder(content).insert(content.length()-24, "-").toString();
	    content = new StringBuilder(content).insert(content.length()-20, "-").toString();
	    content = new StringBuilder(content).insert(content.length()-16, "-").toString();
	    content = new StringBuilder(content).insert(content.length()-12, "-").toString();
	    
	    return content;
    }
	
	public void copyFileFromJarToOutside(String inputPath, String destPath){
		URL inputUrl = getClass().getResource(inputPath);
		File dest = new File(destPath);
		try {
			FileUtils.copyURLToFile(inputUrl, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void createLanguageFiles(){
		File file = new File(plugin.getDataFolder()+File.separator+"/locate/");
		if(!file.exists()){
			file.mkdir();
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/deutsch.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/deutsch.yml", plugin.getDataFolder()+File.separator+"/locate/deutsch.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/english.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/english.yml", plugin.getDataFolder()+File.separator+"/locate/english.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/chinese-simplified.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/chinese-simplified.yml", plugin.getDataFolder()+File.separator+"/locate/chinese-simplified.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/chinese-traditional.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/chinese-traditional.yml", plugin.getDataFolder()+File.separator+"/locate/chinese-traditional.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/french.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/french.yml", plugin.getDataFolder()+File.separator+"/locate/french.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/polish.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/polish.yml", plugin.getDataFolder()+File.separator+"/locate/polish.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/swedish.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/swedish.yml", plugin.getDataFolder()+File.separator+"/locate/swedish.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/russian.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/russian.yml", plugin.getDataFolder()+File.separator+"/locate/russian.yml");
		}
		file = new File(plugin.getDataFolder()+File.separator+"/locate/espanol.yml");
		if(!file.exists()){
			copyFileFromJarToOutside("/locate/espanol.yml", plugin.getDataFolder()+File.separator+"/locate/espanol.yml");
		}
	}*/
}
