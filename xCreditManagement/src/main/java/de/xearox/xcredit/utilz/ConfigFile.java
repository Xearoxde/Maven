package de.xearox.xcredit.utilz;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xcredit.XCredit;

public class ConfigFile {
	
	private String pluginPath;
	private String configPath;
	private File configFile;
	private File configDir;
	private YamlConfiguration yamlFile;
	
	public static String useFlatFile = "credit.saveoption.useflatfile";
	public static String useMultipleFlatFiles = "credit.saveoption.usemultipleflatfiles";
	public static String currencySymbol = "credit.currency.symbol";
	public static String interestRate = "credit.interest.rate";
	public static String interestDay = "credit.interest.day";
	public static String borrowLimit = "credit.main.borrowlimit";
	public static String paymentReminderDays = "credit.payment.reminder.days";
	public static String paymentDeadline = "credit.payment.deadline";
	public static String paymentLockDate = "credit.payment.lockdate";
	public static String resetCodeLenth = "credit.main.resetcode.length";
	public static String resetCodeChars = "credit.main.resetcode.chars";
	public static String accountLimitPerUser = "credit.main.account.limit";
	public static String accountMinUsernameLength = "credit.main.account.username.minlength";
	public static String accountMinPincodeLength = "credit.main.account.pincode.minlength";
	public static String accountMaxLoginFailures = "credit.main.account.login.maxfailures";
	public static String specialRankEnable = "credit.specialrank.enable";
	public static String specialRankAmount = "credit.specialrank.amount";
	
	
	public ConfigFile() {
		this.pluginPath = XCredit.getInstance().getDataFolder().getAbsolutePath();
		configDir = new File(pluginPath+"/config/");
		if(!configDir.exists()){
			configDir.mkdirs();
		}
		this.configPath = configDir.getAbsolutePath()+"/config.yml";
		this.configFile = new File(configPath);
		this.yamlFile = YamlConfiguration.loadConfiguration(configFile);
		if(!this.configFile.exists()){
			//XCredit.getInstance().getUtilz().copyFileFromJarToOutside("/config/config.yml", configPath+"/config.yml");
			createDefaultConfig();
		}
	}
	
	public void createDefaultConfig(){
		yamlFile.addDefault(useFlatFile, false);
		yamlFile.addDefault(useMultipleFlatFiles, true);
		yamlFile.addDefault(currencySymbol, "$");
		yamlFile.addDefault(interestRate, 0.255D);
		yamlFile.addDefault(interestDay, 2);
		yamlFile.addDefault(borrowLimit, 500);
		yamlFile.addDefault(paymentReminderDays, 10);
		yamlFile.addDefault(paymentDeadline, 30);
		yamlFile.addDefault(paymentLockDate, 1);
		yamlFile.addDefault(resetCodeChars, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		yamlFile.addDefault(resetCodeLenth, 8);
		yamlFile.addDefault(accountLimitPerUser, 1);
		yamlFile.addDefault(accountMinUsernameLength, 3);
		yamlFile.addDefault(accountMinPincodeLength, 4);
		yamlFile.addDefault(accountMaxLoginFailures, 10);
		yamlFile.addDefault(specialRankEnable, true);
		yamlFile.addDefault(specialRankAmount, 3);
		for(int i = 0; i < 3; i++){
			yamlFile.addDefault("credit.specialrank."+i+".accountlimit", 1);
			yamlFile.addDefault("credit.specialrank."+i+".paymentreminder", 10);
			yamlFile.addDefault("credit.specialrank."+i+".paymentdeadline", 30);
			yamlFile.addDefault("credit.specialrank."+i+".paymentlockdate", 1);
			yamlFile.addDefault("credit.specialrank."+i+".borrowlimit", 1000);
		}
		yamlFile.options().copyDefaults(true);
		try {
			yamlFile.save(configFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getConfigPath(){
		return configPath;
	}
	
	public File getConfigFile(){
		return configFile;
	}
	
	public YamlConfiguration getYamlFile(){
		return yamlFile;
	}

	public int getPlayerPermission(UUID playerUUID){
		Player player = XCredit.getInstance().getServer().getPlayer(playerUUID);
		if(!XCredit.getInstance().getConfigFile().getYamlFile().getBoolean(ConfigFile.specialRankEnable)){
			return -1;
		}
		int specialRankAmount = XCredit.getInstance().getConfigFile().getYamlFile().getInt(ConfigFile.specialRankAmount);
		for(int i = 0; i < specialRankAmount; i++){
			if(player.hasPermission("xcredit.specialrank."+i)){
				return i;
			}
		}
		if(XCredit.getInstance().getConfigFile().getYamlFile().getBoolean(ConfigFile.specialRankEnable)){
			return 0;
		} else {
			return -1;
		}
	}
	
	public int getPlayerPermission(String playerUUID){
		UUID uuid = UUID.fromString(playerUUID);
		Player player = XCredit.getInstance().getServer().getPlayer(uuid);
		if(!XCredit.getInstance().getConfigFile().getYamlFile().getBoolean(ConfigFile.specialRankEnable)){
			return -1;
		}
		int specialRankAmount = XCredit.getInstance().getConfigFile().getYamlFile().getInt(ConfigFile.specialRankAmount);
		for(int i = 0; i < specialRankAmount; i++){
			if(player.hasPermission("xcredit.specialrank."+i)){
				return i;
			}
		}
		if(XCredit.getInstance().getConfigFile().getYamlFile().getBoolean(ConfigFile.specialRankEnable)){
			return 0;
		} else {
			return -1;
		}
	}
}
