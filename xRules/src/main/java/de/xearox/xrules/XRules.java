package de.xearox.xrules;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xrules.listener.PlayerJoinListener;
import de.xearox.xrules.logging.DynamicStreamHandler;
import de.xearox.xrules.logging.LogFormat;
import de.xearox.xrules.logging.XRulesPrefixHandler;

public class XRules extends JavaPlugin{
	
	public File configFile;
	public File playerFile;
	//public File logFile;
	
	public File configFileDir;
	public File playerFileDir;
	public File logFileDir;
	
	public File logFile;
	
	private static String version;
	
	private final static Logger logger = Logger.getLogger(XRules.class.getCanonicalName());
	private final DynamicStreamHandler dynamicHandler = new DynamicStreamHandler();
	
	public YamlConfiguration yamlConfigFile;
	public YamlConfiguration yamlPlayerFile;
	
	public Handler handler;
	
	public SimpleFormatter formatter;
	
	public String url;
	
	public File pluginDirectory;
	
	static{
		XRulesPrefixHandler.register("de.xearox.xrules");
		getVersion();
	}
	
	//Getter
	public YamlConfiguration getYamlPlayerFile(){
		return yamlPlayerFile;
	}
	
	public YamlConfiguration getYamlConfigFile(){
		return yamlConfigFile;
	}
	
	public File getPluginDirectory(){
		return pluginDirectory;
	}
	
	public File getConfigFile(){
		return configFile;
	}
	
	public File getPlayerFile(){
		return playerFile;
	}
	
	//Setter
	private void setYamlPlayerFile(File file){
		yamlPlayerFile = YamlConfiguration.loadConfiguration(file);
	}
	
	private void setYamlConfigFile(File file){
		yamlConfigFile = YamlConfiguration.loadConfiguration(file);
	}
	
	private void setPluginDirectory(Plugin plugin){
		pluginDirectory = plugin.getDataFolder();
	}
	
	private void setConfigFile(String path){
		configFile = new File(path);
	}
	
	private void setPlayerFile(String path){
		playerFile = new File(path);
	}
	
	@Override
	public void onEnable(){
		try{
			PluginManager pluginManager = this.getServer().getPluginManager();
			
			setPluginDirectory(this);
			
			configFileDir = new File(this.pluginDirectory+"/config/");
			playerFileDir = new File(this.pluginDirectory+"/data/");
			logFileDir = new File(this.pluginDirectory+"/logs/");
			
			setConfigFile(configFileDir+"/config.yml");
			setPlayerFile(playerFileDir+"/players.yml");
			
			setYamlConfigFile(this.getConfigFile());
			setYamlPlayerFile(this.getPlayerFile());
			
			if(!this.pluginDirectory.exists()) this.pluginDirectory.mkdir();
			if(!configFileDir.exists()) configFileDir.mkdir();
			if(!playerFileDir.exists()) playerFileDir.mkdir();
			if(!logFileDir.exists()) logFileDir.mkdir();
			
			logger.addHandler(dynamicHandler);
			this.dynamicHandler.setFormatter(new LogFormat());
			
			this.logFile = new File(logFileDir+"/xrules.log");
			logger.setLevel(Level.ALL);
			
			logger.log(Level.INFO, "Logging xRules to "+ this.logFile.getAbsolutePath());
			try{
				this.dynamicHandler.setHandler(new FileHandler(logFile.getAbsolutePath(), true));
			}catch(IOException e){
				logger.log(Level.WARNING, "Could not use log file xrules.log :"+e.getMessage());
			}
			
			createConfigFile(this.getYamlConfigFile());
			
			pluginManager.registerEvents(new PlayerJoinListener(this), this);
			
			logger.info("Plugin started");
			
			
		} catch(SecurityException e){
			e.printStackTrace();
		//} catch(IOException e){
		//	e.printStackTrace();
		}
	}
	
	public void createConfigFile(YamlConfiguration yamlCon){
		if(yamlCon == null){
			return;
		}
		yamlCon.options().header("Configuration file");
		yamlCon.addDefault("config.Logging.Enable", true);
		yamlCon.addDefault("config.Logging.LogPlayerIP", true);
		yamlCon.addDefault("config.Logging.LogPlayerName", true);
		yamlCon.addDefault("config.Logging.LogPlayerUUID", true);
		
		yamlCon.addDefault("config.WelcomeMessage.Enable", true);
		yamlCon.addDefault("config.WelcomeMessage.Msg", "Welcome %player% to the server");
		yamlCon.addDefault("config.WelcomeMessage.Color", "RED");
		yamlCon.addDefault("config.WelcomeMessage.Bold", false);
		yamlCon.addDefault("config.WelcomeMessage.Italic", false);
		yamlCon.addDefault("config.WelcomeMessage.Underline", false);
		
		yamlCon.addDefault("config.Colors.Normal", "AQUA,BLACK,BLUE,GOLD,GRAY,GREEN,RED,WHITE,YELLOW");
		yamlCon.addDefault("config.Colors.Dark", "DARK_AQUA,DARK_BLUE,DARK_PURPLE,DARK_RED");
		yamlCon.addDefault("config.Colors.Light", "LIGHT_PURPLE");
		yamlCon.addDefault("config.Colors.Special", "MAGIC");
		
		yamlCon.addDefault("config.Rules.Message.Color", "YELLOW");
		yamlCon.addDefault("config.Rules.Message.Bold", false);
		yamlCon.addDefault("config.Rules.Message.Italic", false);
		yamlCon.addDefault("config.Rules.Message.Underline", false);
		yamlCon.addDefault("config.Rules.Message.Line1", "Please read our server rules.");
		yamlCon.addDefault("config.Rules.Message.Line2", "If your read the rules then click on accept or");
		yamlCon.addDefault("config.Rules.Message.Line3", "decline to leave the server!");
		yamlCon.addDefault("config.Rules.Message.Line4", "If you decline the rules you will be kicked from server");
		yamlCon.addDefault("config.Rules.Message.Line5", "");
		yamlCon.addDefault("config.Rules.Message.Line6", "");
		yamlCon.addDefault("config.Rules.Message.Line7", "");
		yamlCon.addDefault("config.Rules.Message.Line8", "");
		yamlCon.addDefault("config.Rules.Message.Line9", "");
		yamlCon.addDefault("config.Rules.Message.Line10", "");
		
		yamlCon.addDefault("config.ServerRules.URL", "http://www.google.de");
		yamlCon.addDefault("config.ServerRules.Msg", "Click here to read the server rules");
		yamlCon.addDefault("config.ServerRules.HoverText", "Server Rules");
		yamlCon.addDefault("config.ServerRules.Color", "DARK_GREEN");
		yamlCon.addDefault("config.ServerRules.Bold", false);
		yamlCon.addDefault("config.ServerRules.Italic", false);
		yamlCon.addDefault("config.ServerRules.Underline", true);
		
		yamlCon.addDefault("config.Accept.Message.Color", "GREEN");
		yamlCon.addDefault("config.Accept.Message.Bold", false);
		yamlCon.addDefault("config.Accept.Message.Italic", false);
		yamlCon.addDefault("config.Accept.Message.Underline", false);
		yamlCon.addDefault("config.Accept.Message.Msg", "[Accept]");
		yamlCon.addDefault("config.Accept.Command", "manuadd %player% %group%");
		yamlCon.addDefault("config.Accept.Group", "Default");
		
		yamlCon.addDefault("config.Decline.Message.Color", "RED");
		yamlCon.addDefault("config.Decline.Message.Bold", false);
		yamlCon.addDefault("config.Decline.Message.Italic", false);
		yamlCon.addDefault("config.Decline.Message.Underline", false);
		yamlCon.addDefault("config.Decline.Message.Msg", "[DECLINE]");
		yamlCon.addDefault("config.Decline.Command", "kick %player% You was kicked because you don't accept the Server Rules");
		
		yamlCon.options().copyDefaults(true);
		try {
			yamlCon.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		this.dynamicHandler.close();
	}
	
	public static String getVersion(){
		if(version != null){
			return version;
		}
		Package xPackage = XRules.class.getPackage();
		if(xPackage == null){
			xPackage = Package.getPackage("de.xearox.xrules");
		}
		if(xPackage == null){
			version = "unkown";
		} else {
			version = xPackage.getImplementationVersion();
			if(version == null){
				version = "unkown"; 
			}
		}
		return version;
	}
	
	public void createPlayerTable(){
		YamlConfiguration yamlCon = this.getYamlPlayerFile();
		yamlCon.options().header("A list of all players, who accepted or decline the server rules");
		yamlCon.options().copyDefaults(true);
		try {
			yamlCon.save(this.getPlayerFile());
			logger.log(Level.INFO, "Player table created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, "Cannot create player table", e);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		String command;
		
		if(label.equalsIgnoreCase("xrules")){
			if(!(sender instanceof Player)){
				//
			}
			Player playerSender = (Player) sender;
			OfflinePlayer offPlayer = this.getServer().getOfflinePlayer(playerSender.getUniqueId());
			Player player = offPlayer.getPlayer();
			if(args.length == 0){
				return true;
			}
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("accept")){
					if(!yamlPlayerFile.getBoolean("UUID."+player.getUniqueId().toString()+".AcceptTheRules")){
						command = yamlConfigFile.getString("config.Accept.Command");
						command = command.replace("%player%", player.getDisplayName());
						command = command.replace("%group%", yamlConfigFile.getString("config.Accept.Group"));
						this.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
						yamlPlayerFile.set("UUID."+player.getUniqueId().toString()+".AcceptTheRules", true);
						try {
							yamlPlayerFile.save(getPlayerFile());
							logger.log(Level.INFO, "Player "+player.getDisplayName()+" accepted the server rules!");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.log(Level.SEVERE, "Can't set the Accept Rules to true", e);
						}
					}
					return true;
				}
				if(args[0].equalsIgnoreCase("decline")){
					if(!yamlPlayerFile.getBoolean("UUID."+player.getUniqueId().toString()+".AcceptTheRules")){
						command = yamlConfigFile.getString("config.Decline.Command");
						command = command.replace("%player%", player.getDisplayName());
						this.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
						return true;
					} else {
						return true;
					}
				}
			}
		}
		
		return false;
	}













}
