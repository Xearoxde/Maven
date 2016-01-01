package de.xearox.xconomy.listener;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.xearox.xconomy.XConomy;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

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
		
		TextComponent joinMessage = new TextComponent();
		TextComponent clickMessage = new TextComponent();
		
		joinMessage.setBold(true);
		joinMessage.setColor(ChatColor.GREEN);
		joinMessage.setText("Welcome to the Server! =) ");
		
		clickMessage.setText("Click here for help");
		clickMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to go to the server website").create()));
		clickMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://minecraft.xearox.de"));
		
		joinMessage.addExtra(clickMessage);
		
		event.getPlayer().spigot().sendMessage(joinMessage);
	}
	
	
}
