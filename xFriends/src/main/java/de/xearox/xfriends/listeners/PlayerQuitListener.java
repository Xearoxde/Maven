package de.xearox.xfriends.listeners;

import org.bukkit.entity.Player;
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
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		MyPlayerObject myPlayerObject = new MyPlayerObject();
		
		myPlayerObject.playerName = player.getName();
		if(XFriends.onlineMode){
			myPlayerObject.UUID = uuid;
		} else {
			myPlayerObject.UUID = utility.getYamlUUIDList().getString(uuid+".onlineUUID");
		}
		myPlayerObject.IP = player.getAddress().getAddress().toString();
		myPlayerObject.ServerName = "";
		
		myClient.sendToServer("updateuser", "loggedOff", utility.getBytesFromObject(myPlayerObject));
	}
}
