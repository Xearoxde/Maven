package de.xearox.xdaily.listeners;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xdaily.DailyReset;
import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.Utilz;

public class PlayerJoinListener implements Listener{

	private XDaily plugin;
	private Utilz utilz;
	private CreateFiles createFiles;
	private DailyReset dailyReset;
	
	public PlayerJoinListener(XDaily plugin) {
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.createFiles = plugin.getCreateFiles();
		this.dailyReset = plugin.getDailyReset();
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile;
		yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		createFiles.CreatePlayerFile(event.getPlayer(), false);
		if(yamlConfigFile.getBoolean("Config.DailyBonus.ResetIfPlayerDontLoginEveryDay?")) dailyReset.checkIfPlayerJoinedEveryDay(event.getPlayer());
		
	}
}
