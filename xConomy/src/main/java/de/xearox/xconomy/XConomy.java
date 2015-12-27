package de.xearox.xconomy;

import java.io.File;
import java.util.Locale;
import java.util.Timer;
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

import de.xearox.xconomy.accounts.AccountActions;
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
	private AccountActions accountActions;
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
			createFiles.createPlayerTable();
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
		this.accountActions = new AccountActions(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		
		if(label.equalsIgnoreCase("money")){
			if(!(sender instanceof Player)){
				logger.info("xConomy - INFO - The console can't do this!");
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
					OfflinePlayer targetPlayer = null;
					for(int i = 0; i<Bukkit.getOfflinePlayers().length;i++){
						if(Bukkit.getOfflinePlayers()[i].getPlayer().getName().equalsIgnoreCase(args[1])){
							System.out.println("Test");
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
		return false;
	}
	


















}
