package de.xearox.xdaily.adminGUI;

import java.util.ArrayList;

import org.apache.commons.lang.WordUtils;
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
	
	public static final ItemStack capsLockOn(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Capslock ON");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack capsLockOnly(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 4);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Capslock Only");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack capsLockOff(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 14);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Capslock OFF");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack saveButton(String caption){
		ItemStack itemStack = new ItemStack(Material.REDSTONE_TORCH_ON);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName(caption);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack loadButton(String caption){
		ItemStack itemStack = new ItemStack(Material.REDSTONE_TORCH_OFF);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName(caption);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack reset(String caption){
		ItemStack itemStack = new ItemStack(Material.EMPTY_MAP);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName(caption);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack getNewItem(String materialName){
		ItemStack itemStack = new ItemStack(Material.getMaterial(materialName));
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		
		String displayName = Material.getMaterial(materialName).name().replaceAll("_", " ");
		displayName = WordUtils.capitalizeFully(displayName);
		
		itemMeta.setDisplayName(displayName);
		itemStack.setItemMeta(itemMeta);
		
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack incraseValue1(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Incrase Value +1");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack incraseValue10(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Incrase Value +10");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack incraseValue100(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Incrase Value +100");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack decraceValue1(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Decrase Value -1");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack decraceValue10(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Decrase Value -10");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack decraceValue100(){
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Decrase Value -100");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack rewardTypeDecoration(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 4);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Type Decoration");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack rewardTypeNormal(){
		ItemStack itemStack = new ItemStack(Material.WOOL, 1, (short) 5);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Type Normal");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack rewardTypeMoney(String caption){
		ItemStack itemStack = new ItemStack(Material.DOUBLE_PLANT);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName(caption);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack rewardCalendarName(String caption){
		ItemStack itemStack = new ItemStack(Material.MAP);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName(caption);
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack changeConfig(){
		ItemStack itemStack = new ItemStack(Material.MAP);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Change Config");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	public static final ItemStack changeDefaultCalendar(){
		ItemStack itemStack = new ItemStack(Material.MAP);
		ItemMeta itemMeta = itemStack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		itemMeta.setDisplayName("Change Default Calendar");
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
	
	
	
	
}
