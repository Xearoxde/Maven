package de.xearox.xplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	
	UtilClass utClass = new UtilClass();
	FunctionsClass functionClass = new FunctionsClass();
	SetLanguageClass langClass = new SetLanguageClass();
	
	@Override
	public void onEnable(){
		utClass.createLanguageFiles(this);
	}
	
	@Override
	public void onDisable(){
		//
	}
	

}
