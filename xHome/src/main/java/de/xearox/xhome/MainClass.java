/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xhome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

public class MainClass extends JavaPlugin{
	
	private UtilClass utClass;
	private FunctionsClass functionClass;
	private SetLanguageClass langClass;
	private CreateConfigClass configClass;
	private checkUpdates checkUpdates;
	private static boolean econAvailable;
	@SuppressWarnings("unused")
	private static boolean permAvailable;
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
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Economy econ = null;
	public static Permission perm = null;
	
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
			registerListener();
			
			if(getConfigFile().getBoolean("Config.DEVMessage.Enable?")){
				sendMessageToOP(this);
			}
			if(getConfigFile().getBoolean("Config.Update.automatically")){
				updateChecker(this);
			}
			
			if (!setupEconomy() ) {
				econAvailable = false;
				log.warning("econAvailable = false");
			}else{
				econAvailable = true;
				log.info("econAvailable = true");
			}

			if(!setupPermission() ){
				permAvailable = false;
				log.warning("permAvailable = false");
			}else{
				permAvailable = true;
				log.info("permAvailable = true");
			}
			
			try{
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e){
				System.out.println("Failed to submit the stats");
				e.printStackTrace();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		//
	}
	
	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
    }
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		
	}
	
	private boolean setupPermission(){
		if(getServer().getPluginManager().getPlugin("Vault") == null){
			return false;
		}
		RegisteredServiceProvider<Permission> rspPerm = getServer().getServicesManager().getRegistration(Permission.class);
		if(rspPerm == null) {
			return false;
		}
		perm = rspPerm.getProvider();
		return rspPerm != null;
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
		Player playerSender = null;
		Player player = null;
		Location pLoc = null;
		OfflinePlayer offPlayer = null;
		
		if(label.equalsIgnoreCase("home")){
//##########Check if the sender is the console. If true, then return a message or something like this #######################################
			if(!(sender instanceof Player)){
				//sender.sendMessage(utClass.Format("$cThe console cant do this!"));
				log.info(utClass.Format("$cThe console can't do this!"));
				return true;
			}
			String yamlFilePath = "/data/";
			String yamlFileName = "homelist";
			String yamlFileType = "yml";
			String configFilePath = "/config/";
			String configFileName = "config";
			String configFileType = "yml";
			EconomyResponse responseTPHome = null;
			EconomyResponse responseTPDiffHome = null;
			File homeFile;
			File configFile;
			homeFile = utClass.getFile(yamlFilePath, yamlFileName, yamlFileType);
			configFile = utClass.getFile(configFilePath, configFileName, configFileType);
			YamlConfiguration yamlConfigFile = utClass.yamlCon(configFile);
			YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
			playerSender = (Player) sender;
			offPlayer = this.getServer().getOfflinePlayer(playerSender.getUniqueId());
			player = offPlayer.getPlayer();
			
			pLoc = player.getLocation();
//##########Set the Variables for Player, Players Location and the Language of the player####################################################
			player = (Player) sender;
			pLoc = player.getLocation();
			langClass.setMessageLanguage(player);
//##########If no args added to the command the player will be teleported to his homebase####################################################
			if(args.length == 0){
				if(player.hasPermission("home.teleport.mainhome")){
					if((econAvailable)&&(yamlConfigFile.getBoolean("Config.CostsForTeleport"))){
						responseTPHome = econ.withdrawPlayer(offPlayer, yamlConfigFile.getInt("Config.TeleportCostsToHome"));
						if(responseTPHome.transactionSuccess()){
							functionClass.tpHome(pLoc, player);
							return true;
						}else{
							player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeCostsTPHome));
							return true;
						}
					}else{
						functionClass.tpHome(pLoc, player);
						return true;
					}
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
						if((econAvailable)&&(yamlConfigFile.getBoolean("Config.TeleportCostsToDiffHome"))){
							responseTPDiffHome = econ.withdrawPlayer(offPlayer, yamlConfigFile.getInt("Config.TeleportCostsToDiffHome"));
							if(responseTPDiffHome.transactionSuccess()){
								functionClass.tpDiffHome(pLoc, player, args[0]);
								return true;
							}else{
								player.sendMessage(utClass.Format(SetLanguageClass.MsgHomeCostsTPDiffHome));
								return true;
							}
						}else{
							functionClass.tpDiffHome(pLoc, player, args[0]);
							return true;
						}
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
							functionClass.delHome(player, args[1]);
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
								if(yamlConfigFile.getBoolean("Config.Update.reloadAfterApply")){
									this.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rl");
									return true;
								}
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
		if(cmd.getName().equalsIgnoreCase("spawn")) {
	         //if(player.hasPermission("vitality.spawn")) {
			player = (Player) sender;     
			player.teleport(player.getWorld().getSpawnLocation());    
			player.sendMessage("You have been teleported to spawn");
	            }
	           //}
		
		if(cmd.getName().equalsIgnoreCase("getPermGroup")){
			if(!(sender instanceof Player)){
				//sender.sendMessage(utClass.Format("$cThe console cant do this!"));
				log.info(utClass.Format("$cThe console can't do this!"));
				return true;
			}
			String filePath = "/config/";
			String fileName = "config";
			String fileType = "yml";
			YamlConfiguration yamlFile;
			File file = utClass.getFile(filePath, fileName, fileType);
			yamlFile = utClass.yamlCon(file);
			
			if(!yamlFile.getBoolean("Config.DebugCommands")){
				return true;
			}
			
			player = (Player) sender;
			UUID playerUUID = player.getUniqueId();
			
			offPlayer = this.getServer().getOfflinePlayer(playerUUID);
			
			if(!player.hasPermission("home.debugCommands")){
				return true;
			}
			
			if(perm == null){
				player.sendMessage("Permissions not available");
				return true;
			}
			
			
			String playerPermGroup = perm.getPrimaryGroup(offPlayer.getPlayer().getWorld().getName(), offPlayer);
			
			player.sendMessage("Du bist in der Gruppe = " + playerPermGroup);
			
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("getYamlList")){
			if(!(sender instanceof Player)){
				//sender.sendMessage(utClass.Format("$cThe console cant do this!"));
				log.info(utClass.Format("$cThe console can't do this!"));
				return true;
			}
			player = (Player) sender;
			UUID playerUUID = player.getUniqueId();
			
			offPlayer = this.getServer().getOfflinePlayer(playerUUID);
			
			String filePath = "/config/";
			String fileName = "config";
			String fileType = "yml";
			YamlConfiguration yamlFile;
			File file = utClass.getFile(filePath, fileName, fileType);
			yamlFile = utClass.yamlCon(file);
			
			if(!yamlFile.getBoolean("Config.DebugCommands")){
				return true;
			}
			
			if(!player.hasPermission("home.debugCommands")){
				return true;
			}
			
			if(args.length == 0){
				player.sendMessage("Gebe eine Wert ein");
				return true;
			}
			
			player.sendMessage("Config: "+yamlFile.getConfigurationSection(args[0]).getKeys(Boolean.getBoolean(args[1])));
			
			return true;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("writePermGroups")){
			if(!(sender instanceof Player)){
				//sender.sendMessage(utClass.Format("$cThe console cant do this!"));
				log.info(utClass.Format("$cThe console can't do this!"));
				return true;
			}
			String filePath = "/config/";
			String fileName = "config";
			String fileType = "yml";
			YamlConfiguration yamlFile;
			player = (Player) sender;
			File file = utClass.getFile(filePath, fileName, fileType);
			yamlFile = utClass.yamlCon(file);
			
			if(!player.hasPermission("home.getPermissionGroups")){
				return true;
			}
			
			for(String group : perm.getGroups()){
				yamlFile.set("Config.Maximumhome.Groups."+group, 0);
				player.sendMessage(ChatColor.GOLD + group + ChatColor.GREEN +" added");
			}
			try {
				yamlFile.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
