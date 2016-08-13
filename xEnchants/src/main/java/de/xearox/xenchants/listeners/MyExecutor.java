package de.xearox.xenchants.listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffectTypeWrapper;

import de.xearox.xenchants.XEnchants;
import de.xearox.xenchants.utilz.CreateItem;
import de.xearox.xenchants.utilz.EnchantedItem;
import de.xearox.xenchants.utilz.Utilz;

public class MyExecutor implements CommandExecutor{
	
	private XEnchants plugin;
	private Utilz utilz;
	private CreateItem createItem;
	
	public MyExecutor(XEnchants plugin) {
		this.plugin = plugin;
		this.createItem = plugin.getCreateItem();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if(label.equalsIgnoreCase("enchantment")){
			if(!(sender instanceof Player)){
				System.out.println("oops...console can't do this");
			}
			Player player = (Player) sender;
			
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("This is the Ultimate");
			lore.add("Sword of Death");
			
			ArrayList<EnchantedItem> enchantedItemList = new ArrayList<EnchantedItem>();
			EnchantedItem enchantedItem = new EnchantedItem();
			
			enchantedItem.enchantment = Enchantment.DURABILITY;
			enchantedItem.level = 1000;
			
			
			enchantedItemList.add(enchantedItem);
			
			enchantedItem = new EnchantedItem();
			
			player.setFireTicks(200);
			
			enchantedItem.enchantment = Enchantment.DAMAGE_UNDEAD;
			enchantedItem.level = 1000;
			
			
			
			enchantedItemList.add(enchantedItem);
			
			player.getInventory().addItem(createItem.createEnchantedItem(Material.DIAMOND_SWORD, "The Ultimate Killer", lore, enchantedItemList));
			return true;
			
		}
		return false;
	}

}
