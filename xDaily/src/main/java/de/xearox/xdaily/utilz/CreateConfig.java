package de.xearox.xdaily.utilz;

import java.io.File;
import java.io.IOException;

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
		yamlFile.addDefault("Config.DailyBonus.Rewards.1.Name", "Day One");
		yamlFile.addDefault("Config.DailyBonus.Rewards.1.Reward", "Diamond");
		
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
}
