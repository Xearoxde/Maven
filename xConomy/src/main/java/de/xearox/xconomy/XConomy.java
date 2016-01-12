package de.xearox.xconomy;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.httpserver.HTTPServer;
import de.xearox.xconomy.accounts.AccountActions;
import de.xearox.xconomy.accounts.CreateAccount;
import de.xearox.xconomy.listener.PlayerJoinListener;
import de.xearox.xconomy.listener.PlayerQuitListener;
import de.xearox.xconomy.utility.Common;
import de.xearox.xconomy.utility.CreateFiles;
import de.xearox.xconomy.utility.Database;

public class XConomy extends JavaPlugin{
	public PluginDescriptionFile info;
	public PluginManager manger;
	private CreateAccount createAccount;
	private Common common;
	private CreateFiles createFiles;
	private AccountActions accountActions;
	private Database database;
	private HTTPServer httpServer;
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
	public AccountActions getAccountActions(){
		return accountActions;
	}
	public Database database(){
		return database;
	}
	public HTTPServer httpServer(){
		return httpServer;
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
	public void onLoad(){}
	
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
			
			//Create instance
			this.createInstances();
			
			createFiles.createConfigFile();
			createFiles.createReadmeFile();
			createFiles.createPlayerTable();
			this.createWebserver();
			
			database.createAccountsDBTable();
			database.createCookiesDBTable();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void onDisable(){
		try {
			this.httpServer.getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("#### Can't close socket ####");
		}
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
	
	public void createWebserver(){
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		int port;
		File webRoot;
		String webRootString;
		boolean allowDirectoryListing;
		File logfile;
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		
		if(!yamlFile.getBoolean("Webserver.Enable")){
			logger.info("Running xConomy without webserver");
		} else {
			webRootString = directory + yamlFile.getString("Webserver.Webroot");
			port = yamlFile.getInt("Webserver.Port");
			webRoot = new File(webRootString);
			allowDirectoryListing = yamlFile.getBoolean("Webserver.AllowDirectoryListing");
			logfile = new File(yamlFile.getString("Webserver.Logfile"));
			this.httpServer = new HTTPServer(port, webRoot, allowDirectoryListing, logfile, this);
		}
		
	}
	
	public void createInstances(){
		this.createAccount = new CreateAccount(this);
		this.common = new Common(this);
		this.logger = Logger.getLogger("Minecraft");
		this.createFiles = new CreateFiles(this);
		this.accountActions = new AccountActions(this);
		this.database = new Database(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		
		if(label.equalsIgnoreCase("money")){
			if(!(sender instanceof Player)){
				logger.info("xConomy - INFO - The console can't do this!");
				if(args[0].equalsIgnoreCase("add")){
					accountActions.depositMoney(Bukkit.getOfflinePlayer(args[1]), Double.parseDouble(args[2]));
					System.out.println("blaa");
				}
				return true;
			}
			
			Player playerSender = null;
			Player player = null;
			Location pLoc = null;
			playerSender = (Player) sender;
			OfflinePlayer offPlayer = this.getServer().getOfflinePlayer(playerSender.getUniqueId());
			
			String CurrencySingular = yamlFile.getString("Currency.Name.Singular");
			String CurrencyPlural = yamlFile.getString("Currency.Name.Plural");
			
			player = offPlayer.getPlayer();
			
			if(args.length == 0){
				Double balance = accountActions.getPlayerBalance(player);
				if(balance <=1 ){
					player.sendMessage("[BALANCE] "+ balance + " "+ CurrencySingular);
					return true;
				} else {
					player.sendMessage("[BALANCE] "+ balance + " "+ CurrencyPlural);
					return true;
				}
				
			}
			if(args.length == 3){
				if(args[0].equalsIgnoreCase("add")){
					System.out.println(this.getServer().getOfflinePlayers());
					OfflinePlayer targetPlayer = null;
					for(int i = 0; i<Bukkit.getOfflinePlayers().length;i++){
						System.out.println("OfflinePlayer = " + Bukkit.getOfflinePlayers()[i]);
						if(Bukkit.getOfflinePlayers()[i].getPlayer().getName().equalsIgnoreCase(args[1])){
							targetPlayer = Bukkit.getOfflinePlayer(Bukkit.getOfflinePlayers()[i].getPlayer().getUniqueId());
							accountActions.depositMoney(targetPlayer, Double.parseDouble(args[2]));
							return true;
						}
					}
					if(targetPlayer == null){
						player.sendMessage("The player "+args[1]+" was not found!");
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("send")){
					OfflinePlayer targetPlayer = null;
					for(int i = 0; i<Bukkit.getOfflinePlayers().length;i++){
						if(!Bukkit.getOfflinePlayers()[i].isOnline()){
							player.sendMessage("The Player is Offline, sorry!");
							return true;
						}
						if(Bukkit.getOfflinePlayers()[i].getPlayer().getName().equalsIgnoreCase(args[1])){
							if(accountActions.getPlayerBalance(player) < Double.parseDouble(args[2])){
								player.sendMessage("Sorry, but you haven't enough money to do this");
								return true;
							} else {
								targetPlayer = Bukkit.getOfflinePlayer(Bukkit.getOfflinePlayers()[i].getPlayer().getUniqueId());
								accountActions.depositMoney(targetPlayer, Double.parseDouble(args[2]));
								accountActions.withdrawMoney(player, Double.parseDouble(args[2]));
								if(targetPlayer.isOnline()){
									if(Double.parseDouble(args[2])< 1){
										targetPlayer.getPlayer().sendMessage("You received "+args[2]
										+ " "+ CurrencySingular+" from "+player.getDisplayName());
									} else {
										targetPlayer.getPlayer().sendMessage("You received "+args[2]
										+ " "+ CurrencyPlural+" from "+player.getDisplayName());
									}
								}
								if(Double.parseDouble(args[2]) < 1){
									player.sendMessage("You have send "+ args[2] + " " + CurrencySingular + " to "+targetPlayer.getPlayer().getDisplayName());
								} else {
									player.sendMessage("You have send "+ args[2] + " " + CurrencyPlural + " to "+targetPlayer.getPlayer().getDisplayName());
								}
								return true;
							}
						}
					}
					if(targetPlayer == null){
						player.sendMessage("The player "+args[1]+" was not found!");
						return true;
					}
				}
			}
		}
		
		if(label.equalsIgnoreCase("ecweb")){
			if(!(sender instanceof Player)){
				logger.info("xConomy - INFO - The console can't do this!");
			}
			
			Player playerSender = null;
			Player player = null;
			Location pLoc = null;
			playerSender = (Player) sender;
			OfflinePlayer offPlayer = this.getServer().getOfflinePlayer(playerSender.getUniqueId());
			
			player = offPlayer.getPlayer();
			
			if(args.length == 0){
				player.sendMessage("Nicht genug Parameter, benutze /ecweb help um weitere hilfe zu erhalten!");
				return true;
			}
			if(args[0].equalsIgnoreCase("register")){
				if(args.length == 3){
					if(database.createNewPlayer(offPlayer, args[1], args[2])){
						player.sendMessage("Dein Account wurde im Webinterface angelegt");
					} else {
						player.sendMessage("Du hast bereits ein Account!");
					}
					return true;
				} else {
					player.sendMessage("Um einen Account im Webinterface anlegen zu können, musst du den folgenden Befehlt nutzen");
					player.sendMessage("/ecweb register Username Password");
					return true;
				}
			}
			
		}
		return false;
	}
	


















}
