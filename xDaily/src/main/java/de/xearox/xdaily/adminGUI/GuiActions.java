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
import de.xearox.xletter.TextureUrlList;
import de.xearox.xletter.XLetter;

public class GuiActions {

	private XDaily plugin;
	private XLetter xLetter;
	
	private HashMap<UUID, ArrayList<Inventory>> lastInventoryMap;
	
	
	private String inventoryName = "xDaily Admin - ";
	
	public GuiActions(XDaily plugin) {
		this.plugin = plugin;
		this.lastInventoryMap = plugin.getLastInventoryMap();
		this.xLetter = plugin.getXLetter();
	}
	
	public void runActions(Player player,InventoryClickEvent...events){
		InventoryClickEvent event;
		ArrayList<Inventory> inventory = new ArrayList<>();
		
		if(player.getOpenInventory().getType() == InventoryType.CRAFTING){
			player.openInventory(createIndex());
			inventory.add(createIndex());
			
			if(!this.lastInventoryMap.containsKey(player.getUniqueId())){
				this.lastInventoryMap.put(player.getUniqueId(), inventory);
			} else {
				this.lastInventoryMap.replace(player.getUniqueId(), inventory);
			}
			try{
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED+"UUID = 804ca5ca-1828-30a7-bd62-831f2ba49731 "
						+ChatColor.YELLOW+(this.lastInventoryMap.get(UUID.fromString("804ca5ca-1828-30a7-bd62-831f2ba49731")).size()));
				
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"UUID = c62a6949-b7e2-3efb-8067-a7e846c40236 "
						+ChatColor.AQUA+this.lastInventoryMap.get(UUID.fromString("c62a6949-b7e2-3efb-8067-a7e846c40236")).size());
				
				plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"UUID = c7ed7753-1d64-3c8c-9780-af3c664a0be9 "
						+ChatColor.LIGHT_PURPLE+this.lastInventoryMap.get(UUID.fromString("c7ed7753-1d64-3c8c-9780-af3c664a0be9")).size());
				
				plugin.getServer().getConsoleSender().sendMessage("");
			} catch (Exception e){
				
			}
			return;
		}
		if(events.length == 0){
			return;
		} else {
			event = events[0];
		}
		
		player = (Player) event.getWhoClicked();
		
		if(lastInventoryMap.containsKey(player.getUniqueId())){
			inventory = lastInventoryMap.get(player.getUniqueId());
		}
		
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
			inventory.add(event.getInventory());
			player.openInventory(createNew());
		}
		
		//Go to the index page		
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go to index page")){
			inventory.add(event.getInventory());
			player.openInventory(createIndex());
		}
		
		//Creates the new reward calendar inventory
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Create new reward calendar")){
			inventory.add(event.getInventory());
			player.openInventory(createNewRewardCalendar());
		}
		
		//Go one step back
		if(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Go one page back")){
			player.openInventory(inventory.get(inventory.size()-1));
			inventory.remove(inventory.size()-1);
		}
		
		if(event.getCurrentItem().getType() == Material.AIR && event.getInventory().getName() == ChatColor.stripColor(inventoryName+"New Calendar")){
			return;
		}
		
		//Some debug messages
		try{
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED+"UUID = 804ca5ca-1828-30a7-bd62-831f2ba49731 "
					+ChatColor.YELLOW+(this.lastInventoryMap.get(UUID.fromString("804ca5ca-1828-30a7-bd62-831f2ba49731")).size()));
			
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"UUID = c62a6949-b7e2-3efb-8067-a7e846c40236 "
					+ChatColor.AQUA+this.lastInventoryMap.get(UUID.fromString("c62a6949-b7e2-3efb-8067-a7e846c40236")).size());
			
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"UUID = c7ed7753-1d64-3c8c-9780-af3c664a0be9 "
					+ChatColor.LIGHT_PURPLE+this.lastInventoryMap.get(UUID.fromString("c7ed7753-1d64-3c8c-9780-af3c664a0be9")).size());
			
			plugin.getServer().getConsoleSender().sendMessage("");
		} catch (Exception e){
			
		}
		if(!this.lastInventoryMap.containsKey(player.getUniqueId())){
			this.lastInventoryMap.put(player.getUniqueId(), inventory);
		} else {
			this.lastInventoryMap.replace(player.getUniqueId(), inventory);
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
		
		//inventory.setItem(2, GuiItems.createNewCalendar());
		inventory.setItem(0, xLetter.getItemStack(TextureUrlList.A.getURL(), "A"));
		inventory.setItem(1, xLetter.getItemStack(TextureUrlList.B.getURL(), "B"));
		inventory.setItem(2, xLetter.getItemStack(TextureUrlList.C.getURL(), "C"));
		inventory.setItem(3, xLetter.getItemStack(TextureUrlList.D.getURL(), "D"));
		inventory.setItem(4, xLetter.getItemStack(TextureUrlList.E.getURL(), "E"));
		inventory.setItem(5, xLetter.getItemStack(TextureUrlList.F.getURL(), "F"));
		inventory.setItem(6, xLetter.getItemStack(TextureUrlList.G.getURL(), "G"));
		inventory.setItem(7, xLetter.getItemStack(TextureUrlList.H.getURL(), "H"));
		inventory.setItem(8, xLetter.getItemStack(TextureUrlList.I.getURL(), "I"));
		inventory.setItem(9, xLetter.getItemStack(TextureUrlList.J.getURL(), "J"));
		inventory.setItem(10, xLetter.getItemStack(TextureUrlList.K.getURL(), "K"));
		inventory.setItem(11, xLetter.getItemStack(TextureUrlList.L.getURL(), "L"));
		inventory.setItem(12, xLetter.getItemStack(TextureUrlList.M.getURL(), "M"));
		inventory.setItem(13, xLetter.getItemStack(TextureUrlList.N.getURL(), "N"));
		inventory.setItem(14, xLetter.getItemStack(TextureUrlList.O.getURL(), "O"));
		inventory.setItem(15, xLetter.getItemStack(TextureUrlList.P.getURL(), "P"));
		inventory.setItem(16, xLetter.getItemStack(TextureUrlList.Q.getURL(), "Q"));
		inventory.setItem(17, xLetter.getItemStack(TextureUrlList.R.getURL(), "R"));
		inventory.setItem(18, xLetter.getItemStack(TextureUrlList.S.getURL(), "S"));
		inventory.setItem(19, xLetter.getItemStack(TextureUrlList.T.getURL(), "T"));
		inventory.setItem(20, xLetter.getItemStack(TextureUrlList.U.getURL(), "U"));
		inventory.setItem(21, xLetter.getItemStack(TextureUrlList.V.getURL(), "V"));
		inventory.setItem(22, xLetter.getItemStack(TextureUrlList.W.getURL(), "W"));
		inventory.setItem(23, xLetter.getItemStack(TextureUrlList.X.getURL(), "X"));
		inventory.setItem(24, xLetter.getItemStack(TextureUrlList.Y.getURL(), "Y"));
		inventory.setItem(25, xLetter.getItemStack(TextureUrlList.Z.getURL(), "Z"));
		inventory.setItem(26, xLetter.getItemStack(TextureUrlList.ArrowLeft.getURL(), "BackSpace"));
		
		//inventory.setItem(51, GuiItems.pageGoBack());
		//inventory.setItem(52, GuiItems.pageGoIndex());
		//inventory.setItem(53, GuiItems.closeInventory());
		
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