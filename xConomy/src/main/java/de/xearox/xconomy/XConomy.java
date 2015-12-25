package de.xearox.xconomy;

import java.io.File;
import java.util.Locale;
import java.util.Timer;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xconomy.accounts.CreateAccount;
import de.xearox.xconomy.listener.PlayerJoinListener;
import de.xearox.xconomy.listener.PlayerQuitListener;
import de.xearox.xconomy.utility.Common;
import de.xearox.xconomy.utility.CreateFiles;

public class XConomy extends JavaPlugin{
	public PluginDescriptionFile info;
	public PluginManager manger;
	private CreateAccount createAccount;
	private Common common;
	private CreateFiles createFiles;
	public Logger logger;
	
	
	//Getter
	public CreateAccount getCreateAccount(){
		return createAccount;
	}
	public Common getCommon(){
		return common;
	}
	public CreateFiles getCreateFiles(){
		return createFiles;
	}
	//private static Accounts Accounts = new Accounts();
	//public Parser Commands = new Parser();
	//public Permissions Permissions;
	//private boolean testedPermissions = false;
	
	public static boolean TerminalSupport = false;
	public static File directory;
	//public static Database database;
	//public static Server Server;
	//public static Template Template;
	public static Timer Interest;
	
	@Override
	public void onLoad(){
		System.out.println("Test on Load");
	}
	
	@Override
	public void onEnable(){
		final long startTime = System.nanoTime();
		final long endTime;
		try{
			System.out.println("On Enable");
			//call RegisterListener
			registerListener();
			//Localize locale to prevent issues
			Locale.setDefault(Locale.US);
			
			//Get general plugin information
			info = getDescription();
			
			//Plugin directory setup
			directory = getDataFolder();
			if(!directory.exists()) directory.mkdir();
			
			//Create instance
			this.createInstances();
			
			createFiles.createConfigFile();
			createFiles.createReadmeFile();
			System.out.println("On Enable End");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable(){
		
	}
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		
		//listens for the PlayerQuitListener
		pluginManager.registerEvents(new PlayerQuitListener(this), this);
		
	}
	public void checkForUpdates(){}
	
	public void checkLicense(){}
	
	public void createInstances(){
		this.createAccount = new CreateAccount(this);
		this.common = new Common(this);
		this.logger = Logger.getLogger("Minecraft");
		this.createFiles = new CreateFiles(this);
	}




















}
