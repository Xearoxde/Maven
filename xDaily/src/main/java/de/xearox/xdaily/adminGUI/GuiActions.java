package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import de.xearox.xdaily.XDaily;

public class GuiActions {

	private XDaily plugin;
	
	private HashMap<UUID, Inventory> lastInventoryMap;
	
	private String inventoryName = "xDaily Admin - ";
	
	public GuiActions(XDaily plugin) {
		this.plugin = plugin;
		this.lastInventoryMap = plugin.getLastInventoryMap();
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
		
		player = (Player) event.getWhoClicked();
		
		//If the hashmap "XDaily.lastInventoryMap" hasn't the key from the players UUID it will create a new key from the UUID
		//If it has a key named the UUID from the player, it will load it in to the lastInventory in this class
		
		if(event.getCurrentItem().getType() == Material.AIR && event.getInventory().getName() != ChatColor.stripColor(inventoryName+"New Calendar")){
			return;
		}
		
		//Close the inventory
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close Inventory")){
			player.closeInventory();
		}
		
		//Creates the "Create new..." inventory
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new...")){
			lastInventoryMap.put(player.getUniqueId(), event.getInventory());
			plugin.getServer().getConsoleSender().sendMessage(event.getInventory().getTitle());
			plugin.getServer().getConsoleSender().sendMessage(player.getUniqueId().toString());
			player.openInventory(createNew());
		}
		
		//Go one step back
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go one page back")){
			plugin.getServer().getConsoleSender().sendMessage(event.getInventory().getTitle());
			plugin.getServer().getConsoleSender().sendMessage(player.getUniqueId().toString());
			player.openInventory(lastInventoryMap.get(player.getUniqueId()));
			lastInventoryMap.put(player.getUniqueId(), event.getInventory());
		}	
		//Go to the index page		
		
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go to index page")){
			lastInventoryMap.put(player.getUniqueId(),event.getInventory());
			plugin.getServer().getConsoleSender().sendMessage(event.getInventory().getTitle());
			plugin.getServer().getConsoleSender().sendMessage(player.getUniqueId().toString());
			player.openInventory(createIndex());
		}
		
		//Creates the new reward calendar inventory
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new reward calendar")){
			lastInventoryMap.put(player.getUniqueId(),event.getInventory());
			plugin.getServer().getConsoleSender().sendMessage(event.getInventory().getTitle());
			plugin.getServer().getConsoleSender().sendMessage(player.getUniqueId().toString());
			player.openInventory(createNewRewardCalendar());
		}
		
		
		if(event.getCurrentItem().getType() == Material.AIR && event.getInventory().getName() == ChatColor.stripColor(inventoryName+"New Calendar")){
			return;
		}
		
		//Some debug messages
		try{
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED+"UUID = 804ca5ca-1828-30a7-bd62-831f2ba49731 "
					+ChatColor.YELLOW+(this.lastInventoryMap.get(UUID.fromString("804ca5ca-1828-30a7-bd62-831f2ba49731")).getTitle()));
			
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"UUID = c62a6949-b7e2-3efb-8067-a7e846c40236 "
					+ChatColor.AQUA+this.lastInventoryMap.get(UUID.fromString("c62a6949-b7e2-3efb-8067-a7e846c40236")).getTitle());
			
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"UUID = c7ed7753-1d64-3c8c-9780-af3c664a0be9 "
					+ChatColor.LIGHT_PURPLE+this.lastInventoryMap.get(UUID.fromString("c7ed7753-1d64-3c8c-9780-af3c664a0be9")).getTitle());
			
			plugin.getServer().getConsoleSender().sendMessage("");
		} catch (Exception e){
			
		}
	}
	
	public Inventory createIndex(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Index");
		
		inventory.setItem(45, GuiItems.createNew());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
		
	}
	
	public Inventory createNew(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Create New...");
		
		inventory.setItem(2, GuiItems.createNewCalendar());
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory createNewRewardCalendar(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"New Calendar");
		inventory.setItem(49, GuiItems.saveCalendar());
		inventory.setItem(50, GuiItems.resetNewCalendar());
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	public Inventory CreateItemListPage1(){
		Inventory inventory;
		
		inventory = Bukkit.createInventory(null, 54, ChatColor.BLUE+inventoryName+"Available Item 1/x");
		
		inventory.setItem(48, GuiItems.nextPage());
		inventory.setItem(49, GuiItems.previuosPage());
		
		inventory.setItem(51, GuiItems.pageGoBack());
		inventory.setItem(52, GuiItems.pageGoIndex());
		inventory.setItem(53, GuiItems.closeInventory());
		
		return inventory;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}