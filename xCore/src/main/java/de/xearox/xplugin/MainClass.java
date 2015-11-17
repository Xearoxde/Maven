package de.xearox.xplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {

	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public void sendMessageToConsole(){
		System.out.println("Aufruf aus einer anderen Jar heraus");
	}
}
