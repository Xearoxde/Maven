package de.xearox.xplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	
	UtilClass utClass = new UtilClass();
	FunctionsClass functionClass = new FunctionsClass();
	SetLanguageClass langClass = new SetLanguageClass();
	CreateConfigClass configClass = new CreateConfigClass();
	
	@Override
	public void onEnable(){
		try{
			utClass.createLanguageFiles(this);
			configClass.createConfig(this);	
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		//
	}
	

}
