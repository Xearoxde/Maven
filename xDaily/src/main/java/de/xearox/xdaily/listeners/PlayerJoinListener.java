package de.xearox.xdaily.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xdaily.XDaily;

public class PlayerJoinListener implements Listener{

	private XDaily plugin;
	
	public PlayerJoinListener(XDaily plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		
	}
}
