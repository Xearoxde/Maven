package de.xearox.xhome;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	
	private MainClass plugin;

	public PlayerJoinListener(MainClass plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		
		String filePath = "/data/";
		String fileName = "homelist";
		String fileType = "yml";
		File homeFile;
		
		Player player = plugin.getServer().getPlayer(event.getPlayer().getUniqueId());
		
		UtilClass utClass = plugin.getUtilClass();
		homeFile = utClass.getFile(filePath, fileName, fileType);
		YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
		
		yamlFile.set("Player."+ player.getUniqueId().toString()+".PlayerName", player.getDisplayName());
		
		if(yamlFile.contains("Player."+ player.getUniqueId().toString()+".HomeCount") == false){
			yamlFile.set("Player."+ player.getUniqueId().toString()+".HomeCount", 0);
		}
		
		try {
			yamlFile.save(homeFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
