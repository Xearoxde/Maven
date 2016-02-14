/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xhome;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FunctionsClass {
	
	private MainClass plugin; 
	
	public FunctionsClass(MainClass plugin){
		this.plugin = plugin;
		
	}	
	
	//SetLanguageClass langClass = new SetLanguageClass(plugin);
	
	private String filePath = "/data/";
	private String fileName = "homelist";
	private String fileType = "yml";
	private File homeFile;
	
	public void createHomeFile(){
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		if(!utClass.fileExist(homeFile)){
			utClass.createFile(homeFile);
		}
	}
	
	public void listHome(Player player){
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		if(!utClass.fileExist(homeFile)){
			utClass.createFile(homeFile);
		}
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		try{
			player.sendMessage(yamlFile.getConfigurationSection("Player."+ player.getUniqueId().toString()+".Homes").getKeys(false).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setMainHome(Location pLoc, Player player){
		
		//SetLanguageClass langClass = plugin.getLangClass();
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		
		UUID uuid = player.getUniqueId();
		
		yamlFile.set("Player."+ uuid.toString()+".Homes.MainHome.World", pLoc.getWorld().getName());
		yamlFile.set("Player."+ uuid.toString()+".Homes.MainHome.PosX", pLoc.getBlockX());
		yamlFile.set("Player."+ uuid.toString()+".Homes.MainHome.PosY", pLoc.getBlockY());
		yamlFile.set("Player."+ uuid.toString()+".Homes.MainHome.PosZ", pLoc.getBlockZ());
		yamlFile.set("Player."+ uuid.toString()+".Homes.MainHome.Direction", pLoc.getDirection());
		System.out.println(SetLanguageClass.MsgHomeSetMainHome);
		player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeSetMainHome));
		try {
			yamlFile.save(homeFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDiffHome(Location pLoc, Player player, String args1, String args2){
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		
		String filePath = "/config/";
		String fileName = "config";
		String fileType = "yml";
		
		File file = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlConfigFile = utClass.yamlCon(file);
		UUID uuid = player.getUniqueId();
		
		
		OfflinePlayer offPlayer = plugin.getServer().getOfflinePlayer(uuid);
		
		if(args1.equalsIgnoreCase("set")){
			if(yamlConfigFile.getBoolean("Config.MaxHomes?")){
				int maxHomeCount = utClass.getPlayerMaxHomeCount(offPlayer);
				int homeCount = utClass.getPlayerHomeCount(offPlayer);
				if(utClass.compareHomeCount(homeCount, maxHomeCount) || MainClass.perm.getPrimaryGroup(offPlayer.getPlayer().getWorld().getName(), offPlayer).equalsIgnoreCase("Owner")){
					yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".World", pLoc.getWorld().getName());
					yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosX", pLoc.getBlockX());
					yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosY", pLoc.getBlockY());
					yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosZ", pLoc.getBlockZ());
					yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".Direction", pLoc.getDirection());
					player.sendMessage("HomeCount = "+ homeCount);
					homeCount = homeCount +1;
					yamlFile.set("Player."+ uuid.toString()+".HomeCount", homeCount);
					player.sendMessage("HomeCount = "+ homeCount);
					player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeSetDiffHome.replace("%home%", args2)));
				} else {
					player.sendMessage("Sorry, but you have set all your homes");
				}
			} else {
				yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".World", pLoc.getWorld().getName());
				yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosX", pLoc.getBlockX());
				yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosY", pLoc.getBlockY());
				yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".PosZ", pLoc.getBlockZ());
				yamlFile.set("Player."+ uuid.toString()+".Homes."+args2+".Direction", pLoc.getDirection());
				player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeSetDiffHome.replace("%home%", args2)));
			}
			
			try {
				yamlFile.save(homeFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void delHome(Player p, String args1){
		OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(p.getUniqueId());
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		int homeCount = utClass.getPlayerHomeCount(offPlayer);
		if(yamlFile.contains("Player."+ p.getUniqueId().toString()+".Homes."+args1)){
			yamlFile.set("Player."+ p.getUniqueId().toString()+".Homes."+args1, null);
			try {
				yamlFile.save(homeFile);
				p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDeleteHome.replace("%home%", args1)));
				homeCount = homeCount - 1;
				utClass.setPlayerHomeCount(offPlayer, homeCount);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeErrorDeleteHome.replace("%home%", args1)));
		}
	}
	
	public void tpHome(Location pLoc, Player p){
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		int posX; int posY; int posZ; String world; Vector direction;
		if(yamlFile.contains("Player."+ p.getUniqueId().toString()+".MainHome")){
			posX = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes.MainHome.PosX");
			posY = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes.MainHome.PosY");
			posZ = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes.MainHome.PosZ");
			world = yamlFile.getString("Player."+ p.getUniqueId().toString()+".Homes.MainHome.World");
			direction = yamlFile.getVector("Player."+ p.getUniqueId().toString()+".Homes.MainHome.Direction");
			pLoc.setX(posX);pLoc.setY(posY);pLoc.setZ(posZ);pLoc.setWorld(plugin.getServer().getWorld(world));
			pLoc.setDirection(direction);
			p.teleport(pLoc);
			p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeTeleportToMainHome));
			
		}
	}
	
	public void tpDiffHome(Location pLoc, Player p, String args1){
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		int posX; int posY; int posZ; String world; Vector direction;
		if(yamlFile.contains("Player."+ p.getUniqueId().toString()+"."+args1)){
			posX = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes."+args1+".PosX");
			posY = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes."+args1+".PosY");
			posZ = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".Homes."+args1+".PosZ");
			world = yamlFile.getString("Player."+ p.getUniqueId().toString()+".Homes."+args1+".World");
			direction = yamlFile.getVector("Player."+ p.getUniqueId().toString()+".Homes."+args1+".Direction");
			pLoc.setX(posX);pLoc.setY(posY);pLoc.setZ(posZ);pLoc.setWorld(plugin.getServer().getWorld(world));
			pLoc.setDirection(direction);
			p.teleport(pLoc);
			p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeTeleportToDiffHome.replace("%home%", args1)));
		}
	}
	
	
}
