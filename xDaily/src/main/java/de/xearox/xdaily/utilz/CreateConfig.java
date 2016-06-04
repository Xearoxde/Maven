package de.xearox.xdaily.utilz;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xdaily.XDaily;

public class CreateConfig {
	
	private XDaily plugin;
	
	public CreateConfig(XDaily plugin){
		this.plugin = plugin;
	}
	
	public void createConfig(){
		
		File file = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		yamlFile.options().header("Config File");
		yamlFile.addDefault("Config.Daily.UseMultiLanguage", true);
		yamlFile.addDefault("Config.Daily.ServerLanguage", "english");
		yamlFile.addDefault("Config.AutoUpdate.Enable", false);
		yamlFile.addDefault("Config.AutoUpdate.Version", "Stable");
		yamlFile.addDefault("Config.DailyBonus.Days", 7);
		yamlFile.addDefault("Config.DailyBonus.ResetIfPlayerDontLoginEveryDay?", true);
		yamlFile.addDefault("Config.DailyBonus.ResetIfPlayerGotAllRewards?", true);
		yamlFile.addDefault("Config.DailyBonus.VIP.VIPFile.AutoUpdate?", false);
		yamlFile.addDefault("Config.DailyBonus.VIP.VIPFile.AutoUpdateInterval?", 60);
		yamlFile.addDefault("Config.DailyBonus.VIP.GetDoubleReward?", false);
		yamlFile.addDefault("Config.DailyBonus.VIP.Multiplier", 2);
		yamlFile.addDefault("Config.DailyBonus.RandomItems", false);
		yamlFile.addDefault("Config.DailyBonus.RandomItem.1", "DIAMOND");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.2", "COAL");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.3", "IRON_BLOCK");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.4", "COAL");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.5", "IRON_INGOT");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.6", "COAL");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.7", "DIAMOND_BLOCK");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.8", "STONE");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.9", "GOLD_INGOT");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.10", "COAL");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.11", "GOLD_BLOCK");
		yamlFile.addDefault("Config.DailyBonus.RandomItem.12", "COAL");
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.HideBonus?", false);
		yamlFile.addDefault("Config.DailyBonus.Rewards.ItemInstead", "BEDROCK");
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.1.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.1.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.1.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.2.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.2.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.2.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.3.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.3.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.3.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.4.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.4.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.4.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.5.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.5.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.5.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.6.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.6.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.6.Value", 100);
		
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.7.Name", "");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.7.Reward", "money");
		yamlFile.addDefault("Config.DailyBonus.Rewards.Day.7.Value", 100);
		
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
}
