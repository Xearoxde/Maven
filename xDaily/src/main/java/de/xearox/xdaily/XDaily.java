package de.xearox.xdaily;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;

public class XDaily extends JavaPlugin{
	
	private PlayerJoinListener onPlayerJoinListener;
	private MyExecutor myExecutor;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	
	public PlayerJoinListener getPlayerJoinListener(){
		return onPlayerJoinListener;
	}
	
	public void createCommands(){
		myExecutor = new MyExecutor(this);
		getCommand("daily").setExecutor(myExecutor);
		getCommand("test").setExecutor(myExecutor);
	}
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
	}
	
	@Override
	public void onEnable(){
		try{
			this.onPlayerJoinListener = new PlayerJoinListener(this);
			registerListener();
			createCommands();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable(){}
	
	@Override
	public void onLoad(){}
	
	

}
