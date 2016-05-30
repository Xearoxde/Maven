package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;
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
	
	private ArrayList<Inventory> lastInventory = new ArrayList<Inventory>();
	
	private String inventoryName = "xDaily Admin - ";
	
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
		
		player = (Player) event.getWhoClicked();
		
		System.out.println("###################################");
		System.out.println(event.getWhoClicked().getUniqueId().toString());
		System.out.println("###################################");
		
		
		
		if(!XDaily.lastInventoryMap.containsKey(player.getUniqueId())){
			XDaily.lastInventoryMap.put(player.getUniqueId(), lastInventory);
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.AQUA+"Created new Key for "+player.getUniqueId().toString());
		} else {
			lastInventory = XDaily.lastInventoryMap.get(player.getUniqueId());
			System.out.println("get key from "+player.getUniqueId().toString());
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW+"Loaded key for "+player.getUniqueId().toString());
		}
		
		if(event.getCurrentItem().getType() == Material.AIR && event.getInventory().getName() != ChatColor.stripColor(inventoryName+"New Calendar")){
			return;
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Close Inventory")){
			player.closeInventory();
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new...")){
			lastInventory.add(event.getInventory());
			System.out.println(player.getUniqueId().toString());
			System.out.println(lastInventory.size());
			XDaily.lastInventoryMap.replace(player.getUniqueId(), lastInventory);
			player.openInventory(createNew());
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go one page back")){
			try{
				player.openInventory(lastInventory.get(lastInventory.size()-1));
				lastInventory.remove((Integer) lastInventory.size()-1);
				XDaily.lastInventoryMap.replace(player.getUniqueId(), lastInventory);
				
			} catch (IndexOutOfBoundsException e){
				e.printStackTrace();
			}
			
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go to index page")){
			lastInventory.add(event.getInventory());
			System.out.println(player.getUniqueId().toString());
			System.out.println(lastInventory.size());
			XDaily.lastInventoryMap.replace(player.getUniqueId(), lastInventory);
			player.openInventory(createIndex());
		}
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new reward calendar")){
			lastInventory.add(event.getInventory());
			System.out.println(player.getUniqueId().toString());
			System.out.println(lastInventory.size());
			XDaily.lastInventoryMap.replace(player.getUniqueId(), lastInventory);
			player.openInventory(createNewRewardCalendar());
		}
		
		if(event.getCurrentItem().getType() == Material.AIR && event.getInventory().getName() == ChatColor.stripColor(inventoryName+"New Calendar")){
			return;
		}
		plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED+"UUID = 804ca5ca-1828-30a7-bd62-831f2ba49731 "+XDaily.lastInventoryMap.get(UUID.fromString("804ca5ca-1828-30a7-bd62-831f2ba49731")).toString());
		plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"UUID = c62a6949-b7e2-3efb-8067-a7e846c40236 "+XDaily.lastInventoryMap.get(UUID.fromString("c62a6949-b7e2-3efb-8067-a7e846c40236")).toString());
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