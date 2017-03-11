package de.xearox.xfriends.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.utility.Utility;

public class PlayerCommandPreprocessListener implements Listener{
	
	private XFriends plugin;
	private Utility utility;
	
	
	public PlayerCommandPreprocessListener(XFriends plugin) {
		this.plugin = plugin;
		this.utility = plugin.getUtility();
	}
	
	@EventHandler
	public void onPlayerCommandPrecocess(PlayerCommandPreprocessEvent event){
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		String[] splittedMessage = message.split(" ");
		
		if(splittedMessage.length == 4){
			if(splittedMessage[0].equalsIgnoreCase("/friends") && splittedMessage[1].equalsIgnoreCase("register")){
				String[] sm = splittedMessage;
				event.setMessage(sm[0]+" "+sm[1]+" "+utility.getSHA(sm[2])+" "+sm[3]);
				System.out.println(player.getName()+" runs the command: "+sm[0]+" "+sm[1]+" "+utility.getSHA(sm[2])+" "+sm[3]);
			}
		}
	}
	
	
}
