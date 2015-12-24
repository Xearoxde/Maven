package de.xearox.xconomy;

import java.io.File;
import java.util.Locale;
import java.util.Timer;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xconomy.licenseManager.LicenseManager;
import de.xearox.xconomy.listener.PlayerJoinListener;
import de.xearox.xconomy.listener.PlayerQuitListener;

public class XConomy extends JavaPlugin{
	public PluginDescriptionFile info;
	public PluginManager manger;
	
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
			//call RegisterListener
			registerListener();
			//Localize locale to prevent issues
			Locale.setDefault(Locale.US);
			
			//Get general plugin information
			info = getDescription();
			
			//Plugin directory setup
			directory = getDataFolder();
			if(!directory.exists()) directory.mkdir();
			//Extract Files
			//Common.extract("Config.yml", "Template.yml");
			
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
	
	




















}