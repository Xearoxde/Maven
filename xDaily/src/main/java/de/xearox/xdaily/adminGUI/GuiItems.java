package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}
