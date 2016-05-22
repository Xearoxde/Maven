package de.xearox.xdaily;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xdaily.admgui.CreateRewards;
import de.xearox.xdaily.listeners.InventoryClickEventListener;
import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;
import de.xearox.xdaily.utilz.CreateConfig;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.Utilz;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class XDaily extends JavaPlugin{
	
	private Utilz utilz;
	private MyExecutor myExecutor;
	private CreateRewards createRewards;
	private PlayerJoinListener onPlayerJoinListener;
	private CreateConfig createConfig;
	private CreateFiles createFiles;
	private VaultIntegration vaultIntegration;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Economy econ = null;
	public static Permission perm = null;


	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public CreateConfig getCreateConfig(){
		return createConfig;
	}
	
	public CreateFiles getCreateFiles(){
		return createFiles;
	}
	
	public PlayerJoinListener getPlayerJoinListener(){
		return onPlayerJoinListener;
	}
	
	public CreateRewards getCreateRewards(){
		return createRewards;
	}
	
	public void createCommands(){
		myExecutor = new MyExecutor(this);
		getCommand("daily").setExecutor(myExecutor);
		getCommand("test").setExecutor(myExecutor);
		//getCommand("daily createRewards").setExecutor(myExecutor);
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
			this.vaultIntegration = new VaultIntegration(this);
			this.utilz = new Utilz(this);
			this.createRewards = new CreateRewards(this);
			this.onPlayerJoinListener = new PlayerJoinListener(this);
			this.createConfig = new CreateConfig(this);
			this.createFiles = new CreateFiles(this);
			this.createConfig.createConfig();
			registerListener();
			createCommands();
			this.createFiles.createVIPFile();
			
			//Vault Stuff
			if(!vaultIntegration.setupEconomy()){
				log.warning("xDaily - WARNING - Economy plugin not available");
			} else {
				log.info("xDaily - INFO - Economy plugin found!");
			}
			
			if(!vaultIntegration.setupPermission()){
				log.warning("xDaily - WARNING - Permission plugin not available");
			} else {
				log.info("xDaily - INFO - Permission plugin found!");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onDisable(){}
	
	@Override
	public void onLoad(){}
	
	

}
