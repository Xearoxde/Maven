package de.xearox.xdaily.admgui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xdaily.XDaily;
import net.md_5.bungee.api.ChatColor;

public class CreateRewards {

	private XDaily plugin;
	
	public CreateRewards(XDaily plugin){
		this.plugin = plugin;
	}
	
	public void createAdminGUI(Player player){
		Inventory inv;
		
		ItemStack green_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemStack red_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemStack blue_glass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);
		
		ItemMeta green_glassMeta = green_glass.getItemMeta();
		ItemMeta red_glassMeta = red_glass.getItemMeta();
		ItemMeta blue_glassMeta = blue_glass.getItemMeta();
		
		ArrayList<String> lore1 = new ArrayList<String>();
		ArrayList<String> lore2 = new ArrayList<String>();
		ArrayList<String> lore3 = new ArrayList<String>();
		
		green_glassMeta.setDisplayName("Player List");
		red_glassMeta.setDisplayName("Reward List");
		blue_glassMeta.setDisplayName("Reload Server");
		
		inv = Bukkit.createInventory(null, 54, ChatColor.BLUE+"xDaily Admin GUI - Index");
		
		lore1.add(ChatColor.YELLOW + "Create new rewards");
		lore2.add(ChatColor.YELLOW + "Shows the reward list");
		lore3.add(ChatColor.YELLOW + "Reload the Server");
		
		green_glassMeta.setLore(lore1);
		red_glassMeta.setLore(lore2);
		blue_glassMeta.setLore(lore3);
		
		green_glass.setItemMeta(green_glassMeta);
		red_glass.setItemMeta(red_glassMeta);
		blue_glass.setItemMeta(blue_glassMeta);
		
		inv.setItem(0, green_glass);
		inv.setItem(9, red_glass);
		inv.setItem(18, blue_glass);
		
		
		player.openInventory(inv);
	}
}
