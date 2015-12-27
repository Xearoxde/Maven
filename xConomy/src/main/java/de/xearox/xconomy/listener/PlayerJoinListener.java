package de.xearox.xconomy.listener;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xconomy.XConomy;

public class PlayerJoinListener implements Listener{

	private XConomy plugin;
	
	public PlayerJoinListener(XConomy plugin) {
		// TODO Auto-generated constructor stub
		this.plugin = plugin;
	}
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		UUID playerUUID = event.getPlayer().getUniqueId();
		OfflinePlayer player = plugin.getServer().getOfflinePlayer(playerUUID);
		
		plugin.getCreateAccount().createNewAccount(player);
		
	}
	
	
}
