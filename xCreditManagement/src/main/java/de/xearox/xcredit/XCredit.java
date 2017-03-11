package de.xearox.xcredit;

import java.io.File;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xcredit.commands.CommandXCredit;
import de.xearox.xcredit.handler.CreditCardHandler;
import de.xearox.xcredit.objects.CreditCard;
import de.xearox.xcredit.utilz.ConfigFile;
import de.xearox.xcredit.utilz.Utilz;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class XCredit extends JavaPlugin{
	
	private static XCredit instance;
	public static Economy econ;
	public static Permission perm;
	
	private Utilz utilz;
	private ConfigFile configFile;
	private CommandXCredit commandXCredit;
	private CreditCardHandler creditCardHandler;
	
	public static XCredit getInstance(){
		return instance;
	}
	
	public Utilz getUtilz(){
		return utilz;
	}
	
	public ConfigFile getConfigFile(){
		return configFile;
	}
	
	public CreditCardHandler getCreditCardHandler(){
		return creditCardHandler;
	}
	
	@Override
	public void onLoad(){
		XCredit.instance = this;
		this.utilz = new Utilz();
		this.configFile = new ConfigFile();
		this.creditCardHandler = new CreditCardHandler();
		File dataDir = new File(this.getDataFolder().getAbsolutePath()+"/creditcardsave/cards/");
		if(!dataDir.exists()) dataDir.mkdirs();
		this.creditCardHandler.loadCreditCards();
	}
	
	
	@Override
	public void onEnable(){
		this.registerCommandExecutor();
		this.getServer().getScheduler().runTaskTimer(this, creditCardHandler.getCreditCardRunner() , 20L, 60*20L);
	}
	
	@Override
	public void onDisable(){
		for(CreditCard creditCard : CreditCardHandler.getAllCreditCards()){
			creditCard.saveCreditCard();
		}
	}
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
	}
	
	public void registerCommandExecutor(){
		this.commandXCredit = new CommandXCredit();
		getCommand("credit").setExecutor(commandXCredit);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
