package de.xearox.xconomy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.xearox.xconomy.XConomy;

public class PlayerQuitListener implements Listener{
	
	private XConomy plugin;
	
	public PlayerQuitListener(XConomy plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		event.setQuitMessage(event.getPlayer().getDisplayName() + " left the game. :-(");
	}

}
