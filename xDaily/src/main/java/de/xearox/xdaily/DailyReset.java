package de.xearox.xdaily;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.SetLanguageClass;
import de.xearox.xdaily.utilz.Utilz;

public class DailyReset {

private XDaily plugin;
private Utilz utilz;
private CreateFiles createFiles;
private SetLanguageClass langClass;
	
	public DailyReset(XDaily plugin){
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.createFiles = plugin.getCreateFiles();
		this.langClass = plugin.getLanguageClass();
	}
	
	public void checkIfPlayerJoinedEveryDay(Player player){
		File file;
		
		if(XDaily.pluginVersion.contains("0.6")){
			file = new File(plugin.getDataFolder()+File.separator+"/data/playerData/" + player.getUniqueId().toString() + ".yml");
		} else {
			file = new File(plugin.getDataFolder()+File.separator+"/data/" + player.getUniqueId().toString() + ".yml");
		}
		
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		langClass.setLanguage(player, false);
		
		Set<String> list = yamlFile.getConfigurationSection("Rewards").getKeys(false);
		String[] dateArray = list.toArray(new String[list.size()]);
		
		String dateATM = utilz.getDate(SetLanguageClass.TxtDateFormat, Locale.forLanguageTag(utilz.getPlayerLanguage(player)));
		
		Date dateInArray;
		Date dateATM2;
		
		DateFormat format1 = new SimpleDateFormat(SetLanguageClass.TxtDateFormat, Locale.ENGLISH);
		DateFormat format2 = new SimpleDateFormat(SetLanguageClass.TxtDateFormat, Locale.forLanguageTag(utilz.getPlayerLanguage(player)));
		
		try {
			dateATM2 = format1.parse(dateATM);
			dateInArray = format2.parse(dateArray[dateArray.length-1]);
			
			if(dateATM2.after(dateInArray)){
				yamlFile.set("Rewards", null);
				yamlFile.save(file);
				createFiles.CreatePlayerFile(player, true);
				player.sendMessage(ChatColor.RED+"Hey! Your last login was "+dateArray.length+" days ago. Your rewards has been resetted!");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 1; i < dateArray.length; i++){
			String date = dateArray[i];
			String preDate = dateArray[i-1];
			if(!yamlFile.getBoolean("Rewards."+preDate+".Get_Reward?") && date.equalsIgnoreCase(dateATM)){
				yamlFile.set("Rewards", null);
				try {
					yamlFile.save(file);
					createFiles.CreatePlayerFile(player, true);
					player.sendMessage(ChatColor.RED+"Hey! You've forgot to login yesterday. Your rewards were reset");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}


















}
