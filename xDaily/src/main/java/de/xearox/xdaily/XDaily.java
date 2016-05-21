package de.xearox.xdaily;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xdaily.listeners.InventoryClickEventListener;
import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;
import de.xearox.xdaily.utilz.CreateConfig;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.Utilz;

public class XDaily extends JavaPlugin{
	
	private PlayerJoinListener onPlayerJoinListener;
	private MyExecutor myExecutor;
	private CreateConfig createConfig;
	private CreateFiles createFiles;
	private Utilz utilz;
	
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public CreateConfig getCreateConfig(){
		return createConfig;
	}
	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public CreateFiles getCreateFiles(){
		return createFiles;
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
			this.createFiles = new CreateFiles(this);
			this.utilz = new Utilz(this);
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
