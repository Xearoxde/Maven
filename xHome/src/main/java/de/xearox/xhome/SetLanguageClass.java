/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xhome;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;




public class SetLanguageClass {
	
	private MainClass plugin;
	
	
	public SetLanguageClass(MainClass plugin){
		this.plugin = plugin;
	}
	
	static String MsgHomeSetMainHome;
	static String MsgHomeSetDiffHome;
	static String MsgHomeTeleportToMainHome;
	static String MsgHomeTeleportToDiffHome;
	static String MsgHomePluginEnabled;
	static String MsgHomePluginDisabled;
	static String MsgHomePluginHelp;
	static String MsgHomeTeleportCostsToMainHome;
	static String MsgHomeTeleportCostsToDiffHome;
	static String MsgHomeDeleteHome;
	static String MsgHomePluginReloaded;
	static String MsgHomeErrorTPDiffHomeNotFound;
	static String MsgHomeErrorDeleteHome;
	static String MsgHomeErrorHomeNotFound;
	static String MsgHomeDontHavePermission;
	static String MsgHomeUpdateNewUpadteAvailable;
	static String MsgHomeUpdatePluginUpToDate;
	static String MsgHomeUpdateInstallFailed;
	static String MsgHomeUpdateInstallSuccessfully;
	static String MsgHomeCostsTPHome;
	static String MsgHomeCostsTPDiffHome;
	static String MsgHomeNotEnoughHomeSpace;
	
	
	public String getLanguageFileName(String language){
		
		switch (language){			
			case "de": 		return "deutsch";
			
			case "en":		return "english";
			
			case "pl":		return "polski";
			
			case "it":		return "italiano";
			
			case "es":		return "espanol";
			
			case "fr":		return "francais";
			
			case "nl":		return "nederlands";
			
			default:		return "english";
		}
	}
	
	public void setMessageLanguage(Player player){
		
		String msgLanguage;
		String playerLanguage;
		UtilClass utClass = plugin.getUtilClass();
		try{
			playerLanguage = utClass.getPlayerLanguage(player);			
			
			playerLanguage = playerLanguage.substring(0, playerLanguage.indexOf("_"));
			msgLanguage = getLanguageFileName(playerLanguage).toLowerCase();
			YamlConfiguration yamlLangFile = utClass.yamlCon(utClass.getFile("/locate/", msgLanguage, "yml"));
			
			MsgHomeSetMainHome = yamlLangFile.getString("Message.HomeSet.MainHome");
			MsgHomeSetDiffHome = yamlLangFile.getString("Message.HomeSet.DifferentHome");
			MsgHomeTeleportToMainHome = yamlLangFile.getString("Message.Teleport.ToMainHome");
			MsgHomeTeleportToDiffHome = yamlLangFile.getString("Message.Teleport.ToDifferentHome");
			MsgHomePluginEnabled = yamlLangFile.getString("Message.Plugin.Enabled");
			MsgHomePluginDisabled = yamlLangFile.getString("Message.Plugin.Disabled");
			MsgHomePluginReloaded = yamlLangFile.getString("Message.Plugin.Reloaded");
			MsgHomePluginHelp = yamlLangFile.getString("Message.Plugin.Help");
			MsgHomeTeleportCostsToMainHome = yamlLangFile.getString("Message.TeleportCosts.ToMainHome");
			MsgHomeTeleportCostsToDiffHome = yamlLangFile.getString("Message.TeleportCosts.ToDifferentHome");
			MsgHomeErrorTPDiffHomeNotFound = yamlLangFile.getString("Message.Error.TPDiffHomeNotFound");
			MsgHomeDeleteHome = yamlLangFile.getString("Message.Delete.Sucessfully");
			MsgHomeErrorDeleteHome = yamlLangFile.getString("Message.Error.DeleteDiffHome");
			MsgHomeErrorHomeNotFound = yamlLangFile.getString("Message.Error.ListHomeNotFound");
			MsgHomeDontHavePermission = yamlLangFile.getString("Message.Permission.Error");
			MsgHomeUpdateNewUpadteAvailable = yamlLangFile.getString("Message.Update.NewUpdateAvailable");
			MsgHomeUpdatePluginUpToDate = yamlLangFile.getString("Message.Update.UpToDate");
			MsgHomeUpdateInstallFailed = yamlLangFile.getString("Message.Update.InstallFailed");
			MsgHomeUpdateInstallSuccessfully = yamlLangFile.getString("Message.Update.InstallSuccessfully");
			MsgHomeCostsTPHome = yamlLangFile.getString("Message.Costs.TeleportToHome");
			MsgHomeCostsTPDiffHome = yamlLangFile.getString("Message.Costs.TeleportToDiffHome");
			MsgHomeNotEnoughHomeSpace = yamlLangFile.getString("Message.HomeSet.NotEnoughHomeSpace");
			
		
		}catch (Exception e){
			e.printStackTrace();
			
		}
	}	
	
}
