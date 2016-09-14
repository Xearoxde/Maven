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
		
		yamlConfigFile.addDefault("Message.notRegisteredMessage.URL", "http://www.google.de");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.Msg", "Click here to read the server rules");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.HoverText", "Server Rules");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.Color", "DARK_GREEN");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.Bold", false);
		yamlConfigFile.addDefault("Message.notRegisteredMessage.Italic", false);
		yamlConfigFile.addDefault("Message.notRegisteredMessage.Underline", false);
		
		yamlConfigFile.addDefault("Message.notRegisteredMessage.playermessage.firstLine", "First Line");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.playermessage.secondLine", "Second Line");
		yamlConfigFile.addDefault("Message.notRegisteredMessage.playermessage.thirdLine", "Third Line");
		
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
