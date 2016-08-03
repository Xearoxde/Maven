package de.xearox.xenchants;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xenchants.listeners.InventoryClickEventListener;
import de.xearox.xenchants.listeners.PlayerJoinListener;
import de.xearox.xenchants.logger.LogLevel;
import de.xearox.xenchants.logger.MyLogger;
import de.xearox.xenchants.utilz.Utilz;

public class XEnchants extends JavaPlugin{
	
	private Utilz utilz;
	private MyLogger logger;
	
	
	public Utilz getUtilz(){
		return utilz;
	}
	
	@Override
	public void onLoad(){
		this.logger = new MyLogger(this);
	}
	
	@Override
	public void onEnable(){
		this.utilz = new Utilz(this);
		logger.createLogFile(LogLevel.INFO, "xEnchants successfully started!");
	}
	
	@Override
	public void onDisable(){
		logger.createLogFile(LogLevel.INFO, "xEnchants disabled");
	}
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new InventoryClickEventListener(this), this);
	}
	

}
