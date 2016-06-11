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
	private SetLanguageClass langClass;
	
	public CreateFiles (XDaily plugin){
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.langClass = plugin.getLanguageClass();
		
	}
	
	public void CreatePlayerFile(Player player, boolean rewriteFile){
		//Setting Up Variables
		
		String uuid = utilz.getPlayerUUID(player);
		String playerName = utilz.getPlayerName(player);
		String myDate;
		boolean randomItems;
		int days;
		langClass.setLanguage(player, false);
		
		SimpleDateFormat sdf = new SimpleDateFormat(SetLanguageClass.TxtDateFormat);
		String date = sdf.format(Calendar.getInstance().getTime());
		myDate = sdf.format(Calendar.getInstance().getTime());
		Calendar calendar = Calendar.getInstance();
		
		//Setting Up New Yaml File
		File file = new File(plugin.getDataFolder()+File.separator+"/data/" + uuid + ".yml");
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		if(utilz.fileExist(file) && !rewriteFile) return;
		
		//Loading config File
		File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		String calendarName = yamlConfigFile.getString("Config.DailyBonus.UseSpecificCalendar");
		
		File defaultFile = new File(plugin.getDataFolder()+File.separator+"/data/rewards/"+calendarName+".yml");
		YamlConfiguration yamlDefaultFile;
		yamlDefaultFile = YamlConfiguration.loadConfiguration(defaultFile);
		
		if(yamlConfigFile.getBoolean("Config.DailyBonus.UseSpecific?")){
			int i = 1;
			days = 0;
			while(yamlConfigFile.getString("Config.DailyBonus.Rewards.Day."+i) != null){
				i++;
				days++;
			}
		} else {
			days = yamlConfigFile.getInt("Config.DailyBonus.Days");
		}
		
		
		if(yamlConfigFile.getBoolean("Config.DailyBonus.RandomItems")){
			randomItems = true;
		} else {
			randomItems = false;
		}
		
		//Yaml Default File
		yamlFile.addDefault("Player_Name", playerName );
		yamlFile.addDefault("Player_First_Login", myDate );
		yamlFile.addDefault("Is_Player_VIP?", false);
		for(int i = 0; i < days; i++){
			if(i == 0){
				myDate = sdf.format(calendar.getTime());
			} else {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				myDate = sdf.format(calendar.getTime());
			}
			if(randomItems){
				//
			} 
			
			if(yamlConfigFile.getBoolean("Config.DailyBonus.UseSpecific?")){
				try{
					yamlFile.addDefault("Rewards."+myDate+".Get_Reward?", false);
					yamlFile.addDefault("Rewards."+myDate+".Reward_Name", yamlDefaultFile.get("Rewards.Day."+(i+1)+".Name"));
					yamlFile.addDefault("Rewards."+myDate+".Reward_Type", yamlDefaultFile.get("Rewards.Day."+(i+1)+".Type"));
					yamlFile.addDefault("Rewards."+myDate+".Reward_Value", yamlDefaultFile.get("Rewards.Day."+(i+1)+".Value"));
					yamlFile.addDefault("Rewards."+myDate+".Reward_Slot", yamlDefaultFile.get("Rewards.Day."+(i+1)+".Slot"));
					System.out.println("UseSpecific= "+i);
				} catch(Exception e){
					e.printStackTrace();
				}
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
		if(yamlConfigFile.getBoolean("Config.DailyBonus.UseSpecific?")){
			int decoIndex =1;
			while(yamlDefaultFile.get("Decoration.Slot."+decoIndex+".") != null){
				yamlFile.addDefault("Decoration."+decoIndex+".Name", yamlDefaultFile.get("Decoration.Slot."+decoIndex+".Name"));
				yamlFile.addDefault("Decoration."+decoIndex+".Type", yamlDefaultFile.get("Decoration.Slot."+decoIndex+".Type"));
				yamlFile.addDefault("Decoration."+decoIndex+".Value", yamlDefaultFile.get("Decoration.Slot."+decoIndex+".Value"));
				yamlFile.addDefault("Decoration."+decoIndex+".Slot", yamlDefaultFile.get("Decoration.Slot."+decoIndex+".Slot"));
				System.out.println("Decoration= "+decoIndex);
				decoIndex++;
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
		String pluginDir = plugin.getDataFolder()+File.separator;
		
		File dir = new File(pluginDir+"/data/");
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(pluginDir + "/data/vip-player.txt");
		utilz.createFile(file);
	}
	
	public void createDirs(){
		String pluginDir = plugin.getDataFolder()+File.separator;
		File configDir = new File(pluginDir+"/config/");
		File dataDir = new File(pluginDir+"/data/");
		File locateDir = new File(pluginDir+"/locate/");
		File rewardsDir = new File(pluginDir+"/rewards/");
		
		if(!configDir.exists()) configDir.mkdirs();
		
		if(!dataDir.exists()) dataDir.mkdirs();
		
		if(!locateDir.exists()) locateDir.mkdirs();
		
		if(!rewardsDir.exists()) rewardsDir.mkdirs();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
