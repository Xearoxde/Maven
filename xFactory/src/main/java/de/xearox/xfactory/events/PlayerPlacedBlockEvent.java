package de.xearox.xfactory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xfactory.XFactory;

public class PlayerPlacedBlockEvent implements Listener{
	private XFactory plugin;
	
	public PlayerPlacedBlockEvent(XFactory plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPlacedBlockEvent(BlockPlaceEvent event){
		Player player = event.getPlayer();
		ItemMeta itemMeta = player.getItemInHand().getItemMeta();
		if(itemMeta.getDisplayName().equalsIgnoreCase("Woodcutter lvl1")){
			player.sendMessage("You placed woodcutter");
		}
	}
}
