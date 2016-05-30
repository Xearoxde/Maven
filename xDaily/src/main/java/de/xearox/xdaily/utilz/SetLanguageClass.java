package de.xearox.xdaily.utilz;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xdaily.XDaily;

public class SetLanguageClass {
	
	private XDaily plugin;
	private Utilz utilz;
	
	public SetLanguageClass(XDaily plugin) {
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
	}
	
	//GUI Text
	public static String TxtDailyLoginInventar;
	public static String TxtDateFormat;
	
	
	//Player Messages
	public static String PlayerDontHavePermission;
	public static String PlayerGetAlreadyReward;
	public static String PlayerDontLoginYesterday;
	public static String PlayerGetThisReward;
	
	
	//Console Messages
	
	public static String ConsoleVIPPlayersUpdated;
	public static String ConsoleCantDoThat;
	
	//Admin Messages
	public static String AdmPlayerAlreadyInVIPFile;
	public static String AdmPlayerAddedToVIPFile;
	
	
	//Info Messages
	public static String InfoVaultNotAvailable;
	
	
	//Warning Messages
	
	
	
	//Error Messages
	public static String ErrMojangAPINotAvailable;
	public static String ErrCantCreateFile;
	public static String ErrCantFindFile;
	
	public String getLanguageFileName(String language){
		switch (language){			
			case "de": 		return "deutsch";
			
			case "en":		return "english";
			
			case "pl":		return "polski";
			
			case "it":		return "italiano";
			
			case "es":		return "espanol";
			
			case "fr":		return "francais";
			
			case "nl":		return "nederlands";
			
			case "cn":		return "chinese-simplified";
			
			case "tw":		return "chinese-traditional";
			
			default:		return "english";
		}
	}

	public void setLanguage(Player player, boolean consoleMsg){
		String msgLanguage = "";
		String playerLanguage = "";
		
		File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		try{
			if(!yamlConfigFile.getBoolean("Config.Daily.UseMultiLanguage")){
				msgLanguage = yamlConfigFile.getString("Config.Daily.ServerLanguage");
			} else if(consoleMsg && player == null) {
				msgLanguage = yamlConfigFile.getString("Config.Daily.ServerLanguage");
			} else if(yamlConfigFile.getBoolean("Config.Daily.UseMultiLanguage")){
				playerLanguage = utilz.getPlayerLanguage(player);
				
				if((playerLanguage.substring(0,  playerLanguage.indexOf("_"))).equalsIgnoreCase("zh")){
					playerLanguage = playerLanguage.substring(playerLanguage.indexOf("_")+1, playerLanguage.length()).toLowerCase();
				} else {
					playerLanguage = playerLanguage.substring(0,  playerLanguage.indexOf("_"));
				}
				msgLanguage = getLanguageFileName(playerLanguage).toLowerCase();
			}
			
			YamlConfiguration yamlFile = utilz.yamlCon(utilz.getFile("/locate/", msgLanguage, "yml"));
			
			//GUI Text
			TxtDailyLoginInventar = yamlFile.getString("Txt.DailyLoginInventar");
			TxtDateFormat = yamlFile.getString("Txt.DateFormat");
			
			
			//Player Messages
			PlayerDontHavePermission = yamlFile.getString("Player.DontHavePermission");
			PlayerGetAlreadyReward = yamlFile.getString("Player.GetAlreadyReward");
			PlayerDontLoginYesterday = yamlFile.getString("Player.DontLoginYesterday");
			PlayerGetThisReward = yamlFile.getString("Player.GetThisReward");
			
			//Console Messages
			ConsoleVIPPlayersUpdated = yamlFile.getString("Console.VIPPlayerUpdated");
			ConsoleCantDoThat = yamlFile.getString("Console.CantDoThat");
			
			//Admin Messages
			AdmPlayerAlreadyInVIPFile = yamlFile.getString("Admin.PlayerAlreadyInVIPFile");
			AdmPlayerAddedToVIPFile = yamlFile.getString("Admin.PlayerAddedToVIPFile");
			
			
			//Info Messages
			InfoVaultNotAvailable = yamlFile.getString("Info.VaultNotAvailable");
			
			
			//Warning Messages
			
			
			
			//Error Messages
			ErrMojangAPINotAvailable = yamlFile.getString("Error.MojangAPINotAvailable");
			ErrCantCreateFile = yamlFile.getString("Error.CantCreateFile");
			ErrCantFindFile = yamlFile.getString("Error.CantFindFile");
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
