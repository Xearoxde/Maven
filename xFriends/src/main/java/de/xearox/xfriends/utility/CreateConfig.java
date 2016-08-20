package de.xearox.xfriends.utility;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xfriends.XFriends;

public class CreateConfig {
	
	private XFriends plugin;
	private Utility utility;
	private MyLogger myLogger;
	
	public CreateConfig(XFriends plugin){
		this.plugin = plugin;
		this.utility = plugin.getUtility();
		this.myLogger = plugin.getMyLogger();
	}
	
	public void createConfig(){
		File configFile = utility.getFile("config", "config", "yml");
		YamlConfiguration yamlConfigFile = utility.yamlCon(configFile);
		
		yamlConfigFile.addDefault("Database.dbHost", "127.0.0.1");
		yamlConfigFile.addDefault("Database.dbPort", "3306");
		yamlConfigFile.addDefault("Database.dbName", "DBName");
		yamlConfigFile.addDefault("Database.dbUser", "Username");
		yamlConfigFile.addDefault("Database.dbPassword", "Password");
		
		yamlConfigFile.options().copyDefaults(true);
		
		try{
			yamlConfigFile.save(configFile);
		} catch (IOException e) {
			for(StackTraceElement message : e.getStackTrace()){
				myLogger.createLogFile(LogLevel.ERR, message.toString());
			}
		}
		
	}
}
