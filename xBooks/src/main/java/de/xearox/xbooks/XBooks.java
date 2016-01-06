package de.xearox.xbooks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class XBooks extends JavaPlugin {
	
	@Override
	public void onLoad(){
		
	}
	
	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public void createBook(){
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta)book.getItemMeta();
		bm.setTitle("Commands");
		bm.setAuthor("Xearox");
		
		
	}

}
