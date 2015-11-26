package de.xearox.xplugin;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;




public class SetLanguageClass {
	
	private MainClass plugin;
	
	private UtilClass utClass;
	
	
	public SetLanguageClass(MainClass plugin){
		this.utClass = plugin.getUtilClass();
		this.plugin = plugin;
	}
	
	static String MsgHomeSetMainHome;
	static String MsgHomeSetDiffHome;
	static String MsgHomeTeleportToMainHome;
	static String MsgHomeTeleportToDiffHome;
	static String MsgHomePluginEnabled;
	static String MsgHomePluginDisabled;
	static String MsgHomeTeleportCostsToMainHome;
	static String MsgHomeTeleportCostsToDiffHome;
	static String MsgHomePluginReloaded;
	static String MsgHomeErrorTPDiffHomeNotFound;
	static String MsgHomeErrorDeleteDiffHome;	
	
	public String getLanguageFileName(String language){
		
		switch (language){			
			case "de": 		return "deutsch";
			
			case "en":		return "english";
			
			case "pl":		return "polski";
			
			case "it":		return "italiano";
			
			case "es":		return "espanol";
			
			case "fr":		return "francais";
			
			default:		return "english";		
		}
	}
	
	public void setMessageLanguage(Player player){
		
		String msgLanguage;
		String playerLanguage = utClass.getPlayerLanguage(player);
		playerLanguage = playerLanguage.substring(0, playerLanguage.indexOf("_"));
		msgLanguage = getLanguageFileName(playerLanguage).toLowerCase();
		YamlConfiguration yamlLangFile = utClass.yamlCon(utClass.getFile("locate", msgLanguage, "yml"));
		
		MsgHomeSetMainHome = yamlLangFile.getString("Message.HomeSet.MainHome");
		MsgHomeSetDiffHome = yamlLangFile.getString("Message.HomeSet.DifferentHome");
		MsgHomeTeleportToMainHome = yamlLangFile.getString("Message.Teleport.ToMainHome");
		MsgHomeTeleportToDiffHome = yamlLangFile.getString("Message.Teleport.ToDifferentHome");
		MsgHomePluginEnabled = yamlLangFile.getString("Message.Plugin.Enabled");
		MsgHomePluginDisabled = yamlLangFile.getString("Message.Plugin.Disabled");
		MsgHomePluginReloaded = yamlLangFile.getString("Message.Plugin.Reloaded");
		MsgHomeTeleportCostsToMainHome = yamlLangFile.getString("Message.TeleportCosts.ToMainHome");
		MsgHomeTeleportCostsToDiffHome = yamlLangFile.getString("Message.TeleportCosts.ToDifferentHome");
		MsgHomeErrorTPDiffHomeNotFound = yamlLangFile.getString("Message.Error.TPDiffHomeNotFound");
		MsgHomeErrorDeleteDiffHome = yamlLangFile.getString("Message.Error.DeleteDiffHome");
	}
}
