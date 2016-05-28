package de.xearox.xdaily;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import de.xearox.xdaily.admgui.CreateRewards;
import de.xearox.xdaily.listeners.InventoryClickEventListener;
import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;
import de.xearox.xdaily.utilz.CreateConfig;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class XDaily extends JavaPlugin{
	
	private Utilz utilz;
	private MyExecutor myExecutor;
	private SetLanguageClass langClass;
	private CreateRewards createRewards;
	private PlayerJoinListener onPlayerJoinListener;
	private CreateConfig createConfig;
	private CreateFiles createFiles;
	private VaultIntegration vaultIntegration;
	private DailyReset dailyReset;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Economy econ = null;
	public static Permission perm = null;


	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public SetLanguageClass getLanguageClass(){
		return langClass;
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
	
	public DailyReset getDailyReset(){
		return dailyReset;
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
			this.langClass = new SetLanguageClass(this);
			this.createRewards = new CreateRewards(this);
			this.createFiles = new CreateFiles(this);
			this.dailyReset = new DailyReset(this);
			this.onPlayerJoinListener = new PlayerJoinListener(this);
			this.createConfig = new CreateConfig(this);
			this.createConfig.createConfig();
			utilz.createLanguageFiles();
			registerListener();
			createCommands();
			this.createFiles.createVIPFile();
			checkVIPFile();
			
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
			
			try{
				Metrics metrics = new Metrics(this);
				metrics.start();
			} catch (IOException e){
				System.out.println("Failed to submit the stats");
				//e.printStackTrace();
			} 
		} catch (NoClassDefFoundError e){
			//e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void checkVIPFile(){
		File configFile = new File(this.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		if(yamlConfigFile.getBoolean("Config.DailyBonus.VIP.VIPFile.AutoUpdate?"))
		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(getDataFolder()+File.separator+"/data/vip-player.txt");
				ArrayList<String> list = utilz.readFileByLine(file);
				for(String uuid : list){
					try {
						File playerFile = new File(getDataFolder()+File.separator+"/data/"+uuid+".yml");
						YamlConfiguration yamlPlayerFile;
						yamlPlayerFile = YamlConfiguration.loadConfiguration(playerFile);
						if(yamlPlayerFile.getBoolean("Is_Player_VIP?")){
							continue;
						}
						
						yamlPlayerFile.set("Is_Player_VIP?", true);
						
						yamlPlayerFile.save(playerFile);
						langClass.setLanguage(null, true);
						getServer().getConsoleSender().sendMessage(utilz.Format(SetLanguageClass.ErrMojangAPINotAvailable));
						getServer().getConsoleSender().sendMessage(utilz.Format(SetLanguageClass.getConsoleVIPPlayersUpdated()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}, 0, yamlConfigFile.getInt("Config.DailyBonus.VIP.VIPFile.AutoUpdateInterval?")*20*60);
	}
	
	@Override
	public void onDisable(){}
	
	@Override
	public void onLoad(){}
	
	

}
