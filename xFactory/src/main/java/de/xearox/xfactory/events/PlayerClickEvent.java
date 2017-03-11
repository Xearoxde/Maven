package de.xearox.xfactory.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.xearox.xfactory.XFactory;
import de.xearox.xitemsapi.XItemAPI;

public class PlayerClickEvent implements Listener{
	private XFactory plugin;
	
	public PlayerClickEvent(XFactory plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings({ "deprecation" })
	@EventHandler
	public void onPlayerClickEvent(PlayerInteractEvent event){
		XItemAPI api = (XItemAPI)plugin.getServer().getPluginManager().getPlugin("xItemAPI");
		if(api == null){
			System.out.println("[XFACTORY] API = NULL");
			return;
		}
		
		Player player = event.getPlayer();
		String minecraftName = "minecraft:"+event.getClickedBlock().getType().toString().toLowerCase();
		byte itemData = event.getClickedBlock().getData();
		String itemID = Integer.toString(event.getClickedBlock().getTypeId());
		
		String message = "";
		
		if(api.getItemWithMCName(minecraftName, itemData) != null){
			message = api.valueOf(api.getItemWithMCName(minecraftName, itemData)).getItemName();
		}
		
		if(api.getItemWithItemID(itemID, itemData) != null){
			message = api.valueOf(api.getItemWithItemID(itemID, itemData)).getItemName();
		}
		
		if(message.equalsIgnoreCase("")){
			message = minecraftName;
		}
		
		player.sendMessage(message);
		
		event.setCancelled(true);
		
//		player.sendMessage(api.valueOf().getItemName());
		
//		if(!event.getPlayer().getInventory().getItemInMainHand().hasItemMeta()){
//			return;
//		}
//		if(event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("Factory Wand")){
//			EquipmentSlot e = event.getHand();
//			if (e.equals(EquipmentSlot.HAND)) {
//				if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
//					event.getPlayer().sendMessage(event.getClickedBlock().getData()+"");
//					if(XFactory.pos1Map.containsKey(event.getPlayer())){
//						XFactory.pos1Map.replace(event.getPlayer(), event.getClickedBlock().getLocation());
//					} else {
//						XFactory.pos1Map.put(event.getPlayer(), event.getClickedBlock().getLocation());
//					}
//					event.getPlayer().sendMessage("Pos 1: X = "+event.getClickedBlock().getX()+" Y = "+event.getClickedBlock().getY()+" Z = "+event.getClickedBlock().getZ());
//					event.setCancelled(true);
//				}
//				if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
//					if(XFactory.pos2Map.containsKey(event.getPlayer())){
//						XFactory.pos2Map.replace(event.getPlayer(), event.getClickedBlock().getLocation());
//					} else {
//						XFactory.pos2Map.put(event.getPlayer(), event.getClickedBlock().getLocation());
//					}
//					event.getPlayer().sendMessage("Pos 2: X = "+event.getClickedBlock().getX()+" Y = "+event.getClickedBlock().getY()+" Z = "+event.getClickedBlock().getZ());
//					event.setCancelled(true);
//				}
//			}
//		}
	}
	
	
	
}