/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
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
	
	public void listHome(Player player){
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		if(!utClass.fileExist(homeFile)){
			utClass.createFile(homeFile);
		}
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		player.sendMessage(yamlFile.getConfigurationSection("Player."+ player.getUniqueId().toString()).getKeys(false).toString());		
	}
	
	public void setMainHome(Location pLoc, Player p){
		
		//SetLanguageClass langClass = plugin.getLangClass();
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.PlayerName", p.getDisplayName());
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.World", pLoc.getWorld().getName());
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.PosX", pLoc.getBlockX());
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.PosY", pLoc.getBlockY());
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.PosZ", pLoc.getBlockZ());
		yamlFile.set("Player."+ p.getUniqueId().toString()+".MainHome.Direction", pLoc.getDirection());
		p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeSetMainHome));
		try {
			yamlFile.save(homeFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDiffHome(Location pLoc, Player p, String args1, String args2){
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		if(args1.equalsIgnoreCase("set")){
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".PlayerName", p.getDisplayName());
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".World", pLoc.getWorld().getName());
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".PosX", pLoc.getBlockX());
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".PosY", pLoc.getBlockY());
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".PosZ", pLoc.getBlockZ());
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args2+".Direction", pLoc.getDirection());
			p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeSetDiffHome.replace("%home%", args2)));
			try {
				yamlFile.save(homeFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void delHome(Player p, String args1){
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		if(yamlFile.contains("Player."+ p.getUniqueId().toString()+"."+args1)){
			yamlFile.set("Player."+ p.getUniqueId().toString()+"."+args1, null);
			try {
				yamlFile.save(homeFile);
				p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDeleteHome.replace("%home%", args1)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void tpHome(Location pLoc, Player p){
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		int posX; int posY; int posZ; String world; Vector direction;
		if(yamlFile.contains("Player."+ p.getUniqueId().toString()+".MainHome")){
			posX = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".MainHome.PosX");
			posY = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".MainHome.PosY");
			posZ = yamlFile.getInt("Player."+ p.getUniqueId().toString()+".MainHome.PosZ");
			world = yamlFile.getString("Player."+ p.getUniqueId().toString()+".MainHome.World");
			direction = yamlFile.getVector("Player."+ p.getUniqueId().toString()+".MainHome.Direction");
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
			posX = yamlFile.getInt("Player."+ p.getUniqueId().toString()+"."+args1+".PosX");
			posY = yamlFile.getInt("Player."+ p.getUniqueId().toString()+"."+args1+".PosY");
			posZ = yamlFile.getInt("Player."+ p.getUniqueId().toString()+"."+args1+".PosZ");
			world = yamlFile.getString("Player."+ p.getUniqueId().toString()+"."+args1+".World");
			direction = yamlFile.getVector("Player."+ p.getUniqueId().toString()+"."+args1+".Direction");
			pLoc.setX(posX);pLoc.setY(posY);pLoc.setZ(posZ);pLoc.setWorld(plugin.getServer().getWorld(world));
			pLoc.setDirection(direction);
			p.teleport(pLoc);			
			p.sendMessage(utClass.Format(SetLanguageClass.MsgHomeTeleportToDiffHome.replace("%home%", args1)));
		}
		
		
	}
	
	
	
	
	
}
