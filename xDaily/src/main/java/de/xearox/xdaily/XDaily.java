package de.xearox.xdaily;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import de.xearox.xdaily.admgui.CreateRewards;
import de.xearox.xdaily.adminGUI.GuiActions;
import de.xearox.xdaily.adminGUI.ParseInventory;
import de.xearox.xdaily.listeners.InventoryClickEventListener;
import de.xearox.xdaily.listeners.MyExecutor;
import de.xearox.xdaily.listeners.PlayerJoinListener;
import de.xearox.xdaily.utilz.CreateConfig;
import de.xearox.xdaily.utilz.CreateDefaultCalendar;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;
import de.xearox.xletter.XLetter;
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
	private GuiActions guiActions;
	private XLetter xLetter;
	private ParseInventory parseInventory;
	private CreateDefaultCalendar createDefaultCalendar;
	private de.xearox.xdaily.adminGUI.NewItem newItem;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Economy econ = null;
	public static Permission perm = null;
	
	private HashMap<UUID, ArrayList<Inventory>> lastInventoryMap;
	public HashMap<UUID, ArrayList<Inventory>> getLastInventoryMap(){
		return lastInventoryMap;
	}
	
	private HashMap<UUID, ItemStack[]> inventoryContent;
	public HashMap<UUID, ItemStack[]> getInventoryContent(){
		return inventoryContent;
	}
	
	private HashMap<String, ItemStack> newItemReward;
	public HashMap<String, ItemStack> getNewItemReward(){
		return newItemReward;
	}
	
	private HashMap<UUID, ArrayList<de.xearox.xdaily.adminGUI.NewItem>> newItemMap;
	public HashMap<UUID, ArrayList<de.xearox.xdaily.adminGUI.NewItem>> getNewItemMap(){
		return newItemMap;
	}
	
	private HashMap<UUID, de.xearox.xdaily.adminGUI.NewItem> newItem2;
	public HashMap<UUID, de.xearox.xdaily.adminGUI.NewItem> getNewItem2(){
		return newItem2;
	}
	
	/*public class NewItem{
		  public String displayName;
		  public String itemType;
		  public ItemStack itemStack;
		  public int position;
		  public int value = 1;
	}
	public NewItem getNewItem(){
		return newItem;
	}*/
	
	/*private HashMap<UUID, Inventory> lastInventoryMap;
	public HashMap<UUID, Inventory> getLastInventoryMap(){
		return lastInventoryMap;
	}*/
	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public XLetter getXLetter(){
		return xLetter;
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
	
	public GuiActions getGuiActions(){
		return guiActions;
	}
	
	public ParseInventory getParseInventory(){
		return parseInventory;
	}
	
	public CreateDefaultCalendar getCreateDefaultCalendar(){
		return createDefaultCalendar;
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
			this.newItem = new de.xearox.xdaily.adminGUI.NewItem();
			this.newItem2 = new HashMap<>();
			this.newItemMap = new HashMap<>();
			this.lastInventoryMap = new HashMap<>();
			this.inventoryContent = new HashMap<>();
			this.newItemReward = new HashMap<>();
			this.vaultIntegration = new VaultIntegration(this);
			this.xLetter = new XLetter();
			this.utilz = new Utilz(this);
			this.parseInventory = new ParseInventory(this);
			this.langClass = new SetLanguageClass(this);
			this.guiActions = new GuiActions(this);
			this.createRewards = new CreateRewards(this);
			this.createFiles = new CreateFiles(this);
			this.dailyReset = new DailyReset(this);
			this.onPlayerJoinListener = new PlayerJoinListener(this);
			this.createConfig = new CreateConfig(this);
			this.createDefaultCalendar = new CreateDefaultCalendar(this);
			this.createFiles.createDirs();
			this.createConfig.createConfig();
			this.createFiles.createVIPFile();
			this.utilz.createLanguageFiles();
			this.createDefaultCalendar.createDefault();
			registerListener();
			createCommands();
			checkVIPFile();
			
			//Vault Stuff
			if(!vaultIntegration.setupEconomy()){
				log.info("xDaily - INFO - Economy plugin not available");
			} else {
				log.info("xDaily - INFO - Economy plugin found!");
			}
			
			if(!vaultIntegration.setupPermission()){
				log.info("xDaily - INFO - Permission plugin not available");
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
			e.printStackTrace();
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
				File dir = new File(getDataFolder()+File.separator+"/data/");
				dir.mkdirs();

				File file = new File(dir+"/vip-player.txt");
				if(!file.exists()){
					createFiles.createVIPFile();
				}

				ArrayList<String> list = utilz.readFileByLine(file);
				for(String uuid : list){
					try {
						File playerFile = new File(getDataFolder()+File.separator+"/data/"+uuid+".yml");
						if(!playerFile.exists()){
							return;
						}
						YamlConfiguration yamlPlayerFile;
						yamlPlayerFile = YamlConfiguration.loadConfiguration(playerFile);
						if(yamlPlayerFile.getBoolean("Is_Player_VIP?")){
							continue;
						}
						
						yamlPlayerFile.set("Is_Player_VIP?", true);
						
						yamlPlayerFile.save(playerFile);
						langClass.setLanguage(null, true); 
						getServer().getConsoleSender().sendMessage(utilz.Format(SetLanguageClass.ConsoleVIPPlayersUpdated));
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
	
	public void createVIPFileRunTaskLater(UUID uuid){
		class TestSchedulerTast implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File configFile = new File(getDataFolder()+File.separator+"/config/config.yml");
				YamlConfiguration yamlConfigFile;
				yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
				
				OfflinePlayer offPlayer = getServer().getOfflinePlayer(uuid);
				Player player = offPlayer.getPlayer();

				createFiles.CreatePlayerFile(player, false);
				
				if(yamlConfigFile.getBoolean("Config.DailyBonus.ResetIfPlayerDontLoginEveryDay?")) dailyReset.checkIfPlayerJoinedEveryDay(player);
			}
			
		}
		this.getServer().getScheduler().runTaskLaterAsynchronously(this, new TestSchedulerTast(), 20*5);
	}
	
	@Override
	public void onDisable(){}
	
	@Override
	public void onLoad(){}
	
	

}
