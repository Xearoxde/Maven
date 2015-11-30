/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	
	private UtilClass utClass;
	private FunctionsClass functionClass;
	private SetLanguageClass langClass;
	private CreateConfigClass configClass;
	
	//Getter
	public UtilClass getUtilClass(){
		return utClass;
	}
	
	public FunctionsClass getFunctionClass(){
		return functionClass;
	}
	
	public SetLanguageClass getLangClass(){
		return langClass;
	}
	
	public CreateConfigClass configClass(){
		return configClass;
	}
	
	@Override
	public void onEnable(){
				
		try{
		    this.functionClass = new FunctionsClass(this);
		    this.langClass = new SetLanguageClass(this);
		    this.utClass = new UtilClass(this);
		    this.configClass = new CreateConfigClass(this);
			utClass.createLanguageFiles();
			configClass.createConfig();
			functionClass.createHomeFile();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		//
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){		
		Player player = null;
		Location pLoc = null;
		
		if(label.equalsIgnoreCase("home")){
			String yamlFilePath = "/data/";
			String yamlFileName = "homelist";
			String yamlFileType = "yml";
			String configFilePath = "/config/";
			String configFileName = "config";
			String configFileType = "yml";
			File homeFile;
			File configFile;
			homeFile = utClass.getFile(yamlFilePath, yamlFileName, yamlFileType);
			configFile = utClass.getFile(configFilePath, configFileName, configFileType);
			YamlConfiguration yamlConfigFile = utClass.yamlCon(configFile);
			YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
			player = (Player) sender; 
			pLoc = player.getLocation();
			if((!(sender instanceof Player))&&(!yamlConfigFile.getBoolean("Config.CanOpReloadYamlFiles"))){
				if(args[0].equalsIgnoreCase("rl")){
					try {
						yamlFile.load(homeFile);
						yamlConfigFile.load(configFile);
						sender.sendMessage(utClass.Format(SetLanguageClass.MsgHomePluginReloaded));
						return true;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (InvalidConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					}
				}else{
					player = (Player) sender; 
					pLoc = player.getLocation();
					langClass.setMessageLanguage(player);
				}
			}else if(yamlConfigFile.getBoolean("Config.CanOpReloadYamlFiles")){
				if(args[0].equalsIgnoreCase("rl")){
					try {
						yamlFile.load(homeFile);
						yamlConfigFile.load(configFile);
						sender.sendMessage(utClass.Format(SetLanguageClass.MsgHomePluginReloaded));
						return true;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (InvalidConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					}
				}else{
					player = (Player) sender; 
					pLoc = player.getLocation();
					langClass.setMessageLanguage(player);
				}
			}else{
				player = (Player) sender; 
				pLoc = player.getLocation();
				langClass.setMessageLanguage(player);
			}
			if(args.length==0){
				if(player.hasPermission("home.teleport.mainhome")){
					functionClass.tpHome(pLoc, player);
					return true;
				}else{
					player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
					return true;
				}
			}
			//##########################################################################################
			if(args.length==1){			
				if(args[0].equalsIgnoreCase("set")){
					if(args.length==1){
						if(player.hasPermission("home.set.mainhome")){
							functionClass.setMainHome(pLoc, player);
							return true;
						}else{
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
							return true;
						}
							
					}
					
				}if(args[0].equalsIgnoreCase("list")){
					System.out.println(player);
					if(player.hasPermission("home.list")){
						functionClass.listHome(player);
						return true;
					}else{
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
					
				}if(args[0].equalsIgnoreCase("help")){
					if(player.hasPermission("home.help")){
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomePluginHelp));
						return true;
					}else{
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
									
				}else{
					try{
						if(player.hasPermission("home.teleport.diffhome")){
							functionClass.tpDiffHome(pLoc, player, args[0]);
							return true;
						}else{
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
							return true;
						}
					}catch (Exception e){
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeErrorHomeNotFound));
						return true;
					}
				}
			}
			//##########################################################################################
			if(args.length==2){
				if(args[0].equalsIgnoreCase("del")){				
					if(player.hasPermission("home.del")){
						try{
							functionClass.delHome(player, args[1]);
							return true;
						}catch (Exception e){
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeErrorDeleteHome));
							return true;
						}
					}else{
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
					}
				}if(args[0].equalsIgnoreCase("set")){
					if(player.hasPermission("home.set.diffhome")){
						functionClass.setDiffHome(pLoc, player, args[0], args[1]);
						return true;
					}else{
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
					}
				}
			}
			//##########################################################################################
		}
		return false;
	}
}
