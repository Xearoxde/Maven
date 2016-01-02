package de.bangl.wgpdf.listener;
     
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import de.bangl.wgpdf.Utils;
import de.bangl.wgpdf.WGPlayerDamageFlagsPlugin;
     
public class PlayerDamageListener implements Listener{

	WGPlayerDamageFlagsPlugin plugin;

	public PlayerDamageListener(WGPlayerDamageFlagsPlugin plugin){
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event){
		if ((event.getEntity() != null) && (event.getCause() != null) && ((event.getEntity() instanceof Player))){
			if (!Utils.dmgAllowedAtLocation(this.plugin, Utils.castCause(event.getCause()), event.getEntity().getLocation())) {
				event.setCancelled(true);
			}
		}
	}
}