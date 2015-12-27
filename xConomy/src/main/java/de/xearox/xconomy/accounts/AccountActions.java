package de.xearox.xconomy.accounts;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xconomy.XConomy;

public class AccountActions {

	private XConomy plugin;
	
	public AccountActions(XConomy plugin){
		this.plugin = plugin;
	}
	
	public boolean depositMoney(OfflinePlayer player, Double amount){
		
		UUID uuid = player.getUniqueId();
		String playerUUID = uuid.toString();
		
		double balance;
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		
		try{
			balance = yamlFile.getDouble("UUID."+playerUUID+".Money");
			balance = balance + amount;
			System.out.println("Balance = "+balance);
			System.out.println("Amount = "+amount);
			yamlFile.set("UUID."+playerUUID+".Money", balance);
			yamlFile.save(playerTable);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	public boolean removeAllMoneyFromPlayer(OfflinePlayer player){
		
		UUID uuid = player.getUniqueId();
		String playerUUID = uuid.toString();
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		
		try{
			yamlFile.set("UUID."+playerUUID+".Money", 0);
			yamlFile.save(playerTable);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	public boolean withdrawMoney(OfflinePlayer player, Double amount){
		
		UUID uuid = player.getUniqueId();
		String playerUUID = uuid.toString();
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		
		Double balance;
		
		try{
			balance = yamlFile.getDouble("UUID."+playerUUID+".Money");
			
			if(amount > balance){
				return false;
			} else {
				balance = balance - amount;
				
				yamlFile.set("UUID."+playerUUID+".Money", balance);
				yamlFile.save(playerTable);
				return true;
			}
		} catch (Exception e){
			return false;
		}
	}
	
	public Double getPlayerBalance(OfflinePlayer player){
		
		UUID uuid = player.getUniqueId();
		String playerUUID = uuid.toString();
		
		File playerTableDir = new File(XConomy.directory+"/data/");
		File playerTable = new File (playerTableDir+"/playertable.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(playerTable);
		
		Double balance;
		
		try{
			balance = yamlFile.getDouble("UUID."+playerUUID+".Money");
			
			return balance;
		} catch (Exception e){
			return null;
		}
	}


























}
