package de.xearox.xenchants.utilz;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xenchants.XEnchants;

public class CreateItem {
	
	private XEnchants plugin;
	
	public CreateItem(XEnchants plugin) {
		this.plugin = plugin;
	}
	
	public ItemStack createEnchantedItem(Material material, String itemName, ArrayList<String> lore,ArrayList<EnchantedItem> enchantedItem){
		ItemStack itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		
		itemMeta.setDisplayName(itemName);
		
		for(int i = 0; i < enchantedItem.size(); i++){
			try{
				itemMeta.addEnchant(enchantedItem.get(i).enchantment, enchantedItem.get(i).level, true);
			} catch ( NullPointerException e){
				e.printStackTrace();
			}
		}
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		itemMeta.setLore(lore);
		
		itemStack.setItemMeta(itemMeta);
		
		return itemStack;
	}
}
