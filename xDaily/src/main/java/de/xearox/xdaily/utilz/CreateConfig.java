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
		yamlFile.addDefault("Config.AutoUpdate.Enable", false);
		yamlFile.addDefault("Config.AutoUpdate.Version", "Stable");
		yamlFile.addDefault("Config.DailyBonus.Days", 7);
		yamlFile.addDefault("Config.DailyBonus.RandomItems", true);
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
		yamlFile.addDefault("Config.DailyBonus.Rewards.1.Name", "Day One");
		yamlFile.addDefault("Config.DailyBonus.Rewards.1.Reward", "100 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.2.Name", "Day Two");
		yamlFile.addDefault("Config.DailyBonus.Rewards.2.Reward", "150 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.3.Name", "Day Three");
		yamlFile.addDefault("Config.DailyBonus.Rewards.3.Reward", "200 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.4.Name", "Day Four");
		yamlFile.addDefault("Config.DailyBonus.Rewards.4.Reward", "300 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.5.Name", "Day Five");
		yamlFile.addDefault("Config.DailyBonus.Rewards.5.Reward", "400 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.6.Name", "Day Six");
		yamlFile.addDefault("Config.DailyBonus.Rewards.6.Reward", "800 Dollar");
		yamlFile.addDefault("Config.DailyBonus.Rewards.7.Name", "Day Seven");
		yamlFile.addDefault("Config.DailyBonus.Rewards.7.Reward", "1600 Dollar");
		
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
}
