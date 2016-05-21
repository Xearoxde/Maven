package de.xearox.xdaily.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xdaily.XDaily;
import de.xearox.xdaily.utilz.CreateFiles;
import de.xearox.xdaily.utilz.Utilz;

public class PlayerJoinListener implements Listener{

	private XDaily plugin;
	private Utilz utilz;
	private CreateFiles createFiles;
	
	public PlayerJoinListener(XDaily plugin) {
		this.plugin = plugin;
		this.utilz = plugin.getUtilz();
		this.createFiles = plugin.getCreateFiles();
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		createFiles.CreatePlayerFile(event.getPlayer());
	}
}
