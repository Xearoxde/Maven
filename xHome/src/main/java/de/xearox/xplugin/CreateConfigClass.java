/**
 * Copyright 2015 Xearox - Christopher Hahnen
 */
package de.xearox.xplugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class CreateConfigClass {

	private UtilClass utClass;
	
	public CreateConfigClass(MainClass plugin){
		this.utClass = plugin.getUtilClass();
	}
	
	public void createConfig(){
		
		String filePath = "/config/";
		String fileName = "config";
		String fileType = "yml";
		YamlConfiguration yamlFile;
		File file = utClass.getFile(filePath, fileName, fileType);
		StringBuilder sb = new StringBuilder();
		utClass.createFile(file);
		yamlFile = utClass.yamlCon(file);
		
		sb.append("                            This is the config file.\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		sb.append("                            You need my xcore plugin too\n");
		sb.append("                        Here can you change different lines\n");
		sb.append("                     If you have any questions, PM me in forum\n");
		sb.append("                     Or send me an email to support@xearox.de\n");
		sb.append("                 If you send me an email, make sure you have a subject,\n");
		sb.append("               which I can understand and not something like (I need help)\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		sb.append("              If you use my Economy Plugin, you can enable the teleport costs\n");
		sb.append("                         The Economy Plugin is not ready yet\n");
		sb.append("                            I will upload it if it ready\n");
		//sb.append("                  The premium costs are only enabled in the premium plguin,\n");
		//sb.append("               which you can buy on my website http://www.xearox-games.de or\n");
		//sb.append("                                on the SpiGot forum\n");
		sb.append("                            Thank you for using my Plugin\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		sb.append("                            Copyrights Xearox 2014 - 2015\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		sb.append("         Do not reupload this plugin to other websites or you will get punished!\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		sb.append("        In the line 'CustomUserAnswerMessage' you can change it to true or false\n");
		sb.append("        If you have choosen false you need to add in the next line which language\n");
		sb.append("              the massage should have. You can choose 'en' or 'de' actually\n");
		sb.append("           if you want add your own language please send me a translated file\n");
		sb.append("                    and I will add the language to the next release\n");
		sb.append(" ---------------------------------------------------------------------------------\n");
		yamlFile.options().header(sb.toString());
		yamlFile.addDefault("Config.CostsForTeleport", false);
		yamlFile.addDefault("Config.TeleportCosts", "100");
		yamlFile.addDefault("Config.PremiumCosts", false);
		yamlFile.addDefault("Config.TeleportPremiumCosts", "10");
		yamlFile.addDefault("Config.TeleportPremiumFlatrate", false);
		yamlFile.addDefault("Config.CustomUserAnwserMessage", true);
		yamlFile.addDefault("Config.CustomUserLanguage", "null");
		yamlFile.addDefault("Config.CanOpReloadYamlFiles", false);
		yamlFile.addDefault("Config.Update.automatically", true);
		yamlFile.addDefault("Config.Update.download", true);
		yamlFile.addDefault("Config.Update.version", "stable");
		yamlFile.addDefault("Config.Update.checkInterval", 30);
		yamlFile.addDefault("Config.Update.reloadAfterApply", false);
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
}
