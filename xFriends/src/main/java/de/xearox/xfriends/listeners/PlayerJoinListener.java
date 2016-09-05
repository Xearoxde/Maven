package de.xearox.xfriends.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.myclasses.MyPlayerObject;
import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.client.DatabaseClient;
import de.xearox.xfriends.utility.Utility;

public class PlayerJoinListener implements Listener{
	
	private XFriends plugin;
	private DatabaseClient myClient;
	private Utility utility;
	
	public PlayerJoinListener(XFriends plugin) {
		this.plugin = plugin;
		this.myClient = plugin.getDatabaseClient();
		this.utility = plugin.getUtility();
	}
	
	@EventHandler
	public void OnPlayerJoinEvent(PlayerJoinEvent event){
		MyPlayerObject myPlayerObject = new MyPlayerObject();
		
		myPlayerObject.playerName = event.getPlayer().getDisplayName();
		myPlayerObject.UUID = event.getPlayer().getUniqueId().toString();
		myPlayerObject.IP = event.getPlayer().getAddress().getAddress().toString();
		myPlayerObject.ServerName = plugin.getServer().getServerName();
		
		myClient.sendToServer("updateuser", "LoggedIn", utility.getBytesFromObject(myPlayerObject));
	}
	
	
}
