package de.xearox.xdaily.utilz;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xdaily.XDaily;

public class CreateFiles {

	private XDaily plugin;
	private Utilz utilz;
	
	public CreateFiles (XDaily plugin){
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
	}
	
	public void CreatePlayerFile(Player player){
		//Setting Up Variables
		String uuid = utilz.getPlayerUUID(player);
		String playerName = utilz.getPlayerName(player);
		String myDate;
		boolean randomItems;
		int days;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		String date = sdf.format(Calendar.getInstance().getTime());
		myDate = sdf.format(Calendar.getInstance().getTime());
		Calendar calendar = Calendar.getInstance();
		
		//Setting Up New Yaml File
		File file = new File(plugin.getDataFolder()+File.separator+"/data/" + uuid + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		//Loading config File
		File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		days = yamlConfigFile.getInt("Config.DailyBonus.Days");
		if(yamlConfigFile.getBoolean("Config.DailyBonus.RandomItems")){
			randomItems = true;
		} else {
			randomItems = false;
		}
		
		//Yaml Default File
		yamlFile.addDefault("Player_Name", playerName );
		yamlFile.addDefault("Player_First_Login", myDate );
		
		for(int i = 0; i < days; i++){
			
			if(i == 0){
				myDate = sdf.format(calendar.getTime());
			} else {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				myDate = sdf.format(calendar.getTime());
			}
			if(randomItems){
				//
			} else {
				try{
					yamlFile.addDefault("Rewards."+myDate+".Get_Reward?", false);
					yamlFile.addDefault("Rewards."+myDate+".Reward_Name", yamlConfigFile.getInt("Config.DailyBonus.Rewards.Day."+(i+1)+".Name"));
					yamlFile.addDefault("Rewards."+myDate+".Reward_Type", yamlConfigFile.getString("Config.DailyBonus.Rewards.Day."+(i+1)+".Reward"));
					yamlFile.addDefault("Rewards."+myDate+".Reward_Value", yamlConfigFile.getInt("Config.DailyBonus.Rewards.Day."+(i+1)+".Value"));
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void createVIPFile(){
		String pluginDir = plugin.getDataFolder().getAbsolutePath();
		
		File file = new File(pluginDir + "/data/vip-player.txt");
		utilz.createFile(file);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
