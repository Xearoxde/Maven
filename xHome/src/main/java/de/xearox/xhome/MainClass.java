/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xhome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class MainClass extends JavaPlugin{
	
	private UtilClass utClass;
	private FunctionsClass functionClass;
	private SetLanguageClass langClass;
	private CreateConfigClass configClass;
	private checkUpdates checkUpdates;
	
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
	
	public checkUpdates getCheckUpdates(){
		return checkUpdates;
	}
	
	@Override
	public void onEnable(){
		try{
			this.checkUpdates = new checkUpdates(this);
		    this.functionClass = new FunctionsClass(this);
		    this.langClass = new SetLanguageClass(this);
		    this.utClass = new UtilClass(this);
		    this.configClass = new CreateConfigClass(this);
			utClass.createLanguageFiles();
			configClass.createConfig();
			functionClass.createHomeFile();
			checkUpdates.createDownloadFolder();
			checkUpdates.downloadPlugin();
			if(getConfigFile().getBoolean("Config.DEVMessage.Enable?")){
				sendMessageToOP(this);
			}
			if(getConfigFile().getBoolean("Config.Update.automatically")){
				updateChecker(this);
			}
			try{
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e){
				System.out.println("Failed to submit the stats");
				e.printStackTrace();
			}
			System.out.println("Runns");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		//
	}
	
	public YamlConfiguration getConfigFile(){
		String configFilePath = "/config/";
		String configFileName = "config";
		String configFileType = "yml";
		File configFile;
		configFile = utClass.getFile(configFilePath, configFileName, configFileType);
		YamlConfiguration yamlConfigFile = utClass.yamlCon(configFile);
		return yamlConfigFile;
		
	}
	
	public void updateChecker(MainClass plugin){
		getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(checkUpdates.checkForUpdates()){
					for(Player player : Bukkit.getServer().getOnlinePlayers()){
						if(player == null){
							continue;
						}
						langClass.setMessageLanguage(player);
						if(player.hasPermission("home.getOPMessage")){
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeUpdateNewUpadteAvailable));
						}
					}
				}
			}
		}, 0, getConfigFile().getLong("Config.Update.checkInterval")*20*60);
	}
	
	public void sendMessageToOP(MainClass plugin){
		getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i = 0; i<Bukkit.getServer().getOnlinePlayers().size();i++){
					Bukkit.getServer().broadcast(utClass.Format("$axHome - DEVNOTICE - In the next Update I will change the update checker!\n"
							+ "$aPlease download it from the SpiGot MC Page, if it available. Thank You\n"
							+ "$dThis message can you disable in the config file"), "home.getOPMessage");
				}
			}
		}, 0, 20*60*60);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		Location pLoc = null;
		
		if(label.equalsIgnoreCase("home")){
//##########Check if the sender is the console. If true, then return a message or something like this #######################################
			if(!(sender instanceof Player)){
				sender.sendMessage(utClass.Format("$cThe console cant do this!"));
				return true;
			}
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
//##########Set the Variables for Player, Players Location and the Language of the player####################################################
			player = (Player) sender;
			pLoc = player.getLocation();
			langClass.setMessageLanguage(player);
//##########Reload files Command#############################################################################################################
			if(args[0].equalsIgnoreCase("rl")){
				try{
					yamlFile.load(homeFile);
					yamlConfigFile.load(configFile);
					sender.sendMessage(utClass.Format(SetLanguageClass.MsgHomePluginReloaded));
					return true;
				} catch (FileNotFoundException e){
					e.printStackTrace();
					return true;
				} catch (IOException e){
					e.printStackTrace();
					return true;
				} catch (InvalidConfigurationException e){
					e.printStackTrace();
					return true;
				}
			}
//##########If no args added to the command the player will be teleported to his homebase####################################################
			if(args.length == 0){
				if(player.hasPermission("home.teleport.mainhome")){
					functionClass.tpHome(pLoc, player);
					return true;
				} else {
					player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
					return true;
				}
			}
//##########If one argument added to the command the following commands will run#############################################################
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("set")){
					if(player.hasPermission("home.set.mainhome")){
						functionClass.setMainHome(pLoc, player);
						return true;
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("list")){
					if(player.hasPermission("home.list")){
						functionClass.listHome(player);
						return true;
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("help")){
					if(player.hasPermission("home.help")){
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomePluginHelp));
						return true;
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
				try{
					if(player.hasPermission("home.teleport.diffhome")){
						functionClass.tpDiffHome(pLoc, player, args[0]);
						return true;
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				} catch (Exception e){
					player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeErrorHomeNotFound));
					return true;
				}
			}
//##########If two arguments added to the command the following will run#####################################################################
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("del")){
					if(player.hasPermission("home.del")){
						try{
							functionClass.delHome(player, args[0]);
							return true;
						} catch (Exception e){
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeErrorDeleteHome));
							return true;
						}
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("set")){
					if(player.hasPermission("home.set.diffhome")){
						functionClass.setDiffHome(pLoc, player, args[0], args[1]);
						return true;
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("update")){
					if(player.hasPermission("home.update")){
						if(args[1].equalsIgnoreCase("check")){
							if(checkUpdates.checkForUpdatesCMD()){
								player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeUpdateNewUpadteAvailable));
								return true;
							} else {
								player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeUpdatePluginUpToDate));
								return true;
							}
						}
						if(args[1].equalsIgnoreCase("apply")){
							if(checkUpdates.applyUpdate()){
								player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeUpdateInstallSuccessfully));
								return true;
							} else {
								player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeUpdateInstallFailed));
								return true;
							}
						}
					} else {
						player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeDontHavePermission));
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
