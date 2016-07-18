package de.xearox.xdaily.utilz;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xdaily.XDaily;

public class CreateDefaultCalendar {

	private XDaily plugin;
	
	public CreateDefaultCalendar(XDaily plugin) {
		this.plugin = plugin;
	}
	
	public void createDefault(){
		File ConfigFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(ConfigFile);
		
		File defaultFile = new File(plugin.getDataFolder()+File.separator+"/data/rewards/default.yml");
		YamlConfiguration yamlDefaultFile;
		yamlDefaultFile = YamlConfiguration.loadConfiguration(defaultFile);
		
		int i = 1;
		
		while(yamlConfigFile.getString("Config.DailyBonus.Rewards.Day."+i) != null){
			yamlDefaultFile.addDefault("Rewards.Day."+i+".Name", yamlConfigFile.get("Config.DailyBonus.Rewards.Day."+i+".Name"));
			yamlDefaultFile.addDefault("Rewards.Day."+i+".Type", yamlConfigFile.get("Config.DailyBonus.Rewards.Day."+i+".Type"));
			yamlDefaultFile.addDefault("Rewards.Day."+i+".Value", yamlConfigFile.get("Config.DailyBonus.Rewards.Day."+i+".Value"));
			yamlDefaultFile.addDefault("Rewards.Day."+i+".Slot", yamlConfigFile.get("Config.DailyBonus.Rewards.Day."+i+".Slot"));
			i++;
		}
		yamlDefaultFile.options().copyDefaults(true);
		try{
			yamlDefaultFile.save(defaultFile);
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
