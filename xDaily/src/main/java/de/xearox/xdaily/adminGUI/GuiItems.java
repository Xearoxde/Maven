package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class GuiItems{

	public static final ItemStack closeInventory(){
		ItemStack itemStack = new ItemStack(Material.BARRIER);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Close Inventory");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack createNew(){
		ItemStack itemStack = new ItemStack(Material.NETHER_STAR);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Create new...");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack createNewCalendar(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("Creates a new reward calendar");
		itemMeta.setLore(lore);
		itemMeta.setDisplayName("Create new reward calendar");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack pageGoBack(){
		ItemStack itemStack = new ItemStack(Material.WOOD_DOOR);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Go one page back");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack pageGoIndex(){
		ItemStack itemStack = new ItemStack(Material.IRON_DOOR);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Go to index page");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack listRewardsCalendar(){
		ItemStack itemStack = new ItemStack(Material.SIGN);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("List all reward Calendars");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack saveCalendar(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Save Calendar");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack resetNewCalendar(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 15);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Reset Calendar");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack nextPage(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 2);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Next Page");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack previuosPage(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Previuos Page");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
