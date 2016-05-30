package de.xearox.xdaily.adminGUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.xearox.xdaily.XDaily;

public class GuiActions {

	private XDaily plugin;
	
	public GuiActions(XDaily plugin) {
		this.plugin = plugin;
	}
	
	public void runActions(Player player,InventoryClickEvent...events){
		InventoryClickEvent event;
		if(player.getOpenInventory().getType() == InventoryType.CRAFTING){
			player.openInventory(createIndex());
			return;
		}
		if(events.length == 0){
			return;
		} else {
			event = events[0];
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close Inventory")){
			player.closeInventory();
		}
	}
	
	public Inventory createIndex(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+"xDaily Admin GUI - Index");
		
		inventory.setItem(45, GuiItems.createNew());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
		
	}
}