package de.xearox.xfriends.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.xearox.myclasses.MyPlayerObject;
import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.client.DatabaseClient;
import de.xearox.xfriends.utility.Utility;

public class PlayerQuitListener implements Listener{
	
	private XFriends plugin;
	private DatabaseClient myClient;
	private Utility utility;
	
	public PlayerQuitListener(XFriends plugin) {
		this.plugin = plugin;
		this.myClient = plugin.getDatabaseClient();
		this.utility = plugin.getUtility();
	}
	
	@EventHandler
	public void OnPlayerQuitEvent(PlayerQuitEvent event){
		MyPlayerObject myPlayerObject = new MyPlayerObject();
		
		myPlayerObject.playerName = event.getPlayer().getDisplayName();
		myPlayerObject.UUID = event.getPlayer().getUniqueId().toString();
		myPlayerObject.IP = event.getPlayer().getAddress().getAddress().toString();
		myPlayerObject.ServerName = "";
		
		myClient.sendToServer("updateuser", "loggedOff", utility.getBytesFromObject(myPlayerObject));
	}
}
