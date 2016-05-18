package de.xearox.xdaily;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xdaily.listeners.InventoryClickEventListener;
import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;
import de.xearox.xdaily.utilz.CreateConfig;

public class XDaily extends JavaPlugin{
	
	private PlayerJoinListener onPlayerJoinListener;
	private MyExecutor myExecutor;
	private CreateConfig createConfig;
	
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public CreateConfig getCreateConfig(){
		return createConfig;
	}
	
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
		pluginManager.registerEvents(new InventoryClickEventListener(this), this);
	}
	
	@Override
	public void onEnable(){
		try{
			this.onPlayerJoinListener = new PlayerJoinListener(this);
			this.createConfig = new CreateConfig(this);
			this.createConfig.createConfig();
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
