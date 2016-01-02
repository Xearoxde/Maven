package de.bangl.wgpdf;
 
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.util.Util;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Utils{
	public static WGCustomFlagsPlugin getWGCustomFlags(WGPlayerDamageFlagsPlugin plugin){
		Plugin wgcf = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
		if ((wgcf == null) || (!(wgcf instanceof WGCustomFlagsPlugin))) {
			return null;
		}
		return (WGCustomFlagsPlugin)wgcf;
	}

	public static WorldGuardPlugin getWorldGuard(WGPlayerDamageFlagsPlugin plugin) {
		Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
		if ((wg == null) || (!(wg instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin)wg;
	}

	@SuppressWarnings("deprecation")
	public static boolean dmgAllowedAtLocation(WGPlayerDamageFlagsPlugin plugin, DmgCause cause, Location loc) {
		return Util.flagAllowedAtLocation(plugin.getWGP(), cause, loc, WGPlayerDamageFlagsPlugin.ALLOW_DAMAGE_FLAG, WGPlayerDamageFlagsPlugin.DENY_DAMAGE_FLAG, DmgCause.ANY);
	}

	public static DmgCause castCause(EntityDamageEvent.DamageCause cause) {
		try {
			return DmgCause.valueOf(cause.name());
		} catch (IllegalArgumentException ex) {}
		return null;
	}

















}