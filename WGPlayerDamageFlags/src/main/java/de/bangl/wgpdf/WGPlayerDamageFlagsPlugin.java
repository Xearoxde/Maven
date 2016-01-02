package de.bangl.wgpdf;

import org.bukkit.plugin.java.JavaPlugin;

import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.mewin.WGCustomFlags.flags.CustomSetFlag;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.EnumFlag;

import de.bangl.wgpdf.listener.PlayerDamageListener;
     
public class WGPlayerDamageFlagsPlugin extends JavaPlugin{

	public static final EnumFlag DAMAGE_CAUSE_FLAG = new EnumFlag("damage-cause", DmgCause.class);
	public static final CustomSetFlag ALLOW_DAMAGE_FLAG = new CustomSetFlag("allow-damage", DAMAGE_CAUSE_FLAG);
	public static final CustomSetFlag DENY_DAMAGE_FLAG = new CustomSetFlag("deny-damage", DAMAGE_CAUSE_FLAG);
	private WGCustomFlagsPlugin pluginWGCustomFlags;
	private WorldGuardPlugin pluginWorldGuard;
	private PlayerDamageListener listenerplayerDamage;
       
	public WorldGuardPlugin getWGP(){
		return this.pluginWorldGuard;
	}
       
	public WGCustomFlagsPlugin getWGCFP() {
		return this.pluginWGCustomFlags;
	}
       
	public void onEnable(){
		this.pluginWorldGuard = Utils.getWorldGuard(this);
		this.pluginWGCustomFlags = Utils.getWGCustomFlags(this);
		this.listenerplayerDamage = new PlayerDamageListener(this);
         
		this.pluginWGCustomFlags.addCustomFlag(ALLOW_DAMAGE_FLAG);
		this.pluginWGCustomFlags.addCustomFlag(DENY_DAMAGE_FLAG);
	}       

	public void onDisable(){
		this.pluginWGCustomFlags = null;
		this.pluginWorldGuard = null;
		this.listenerplayerDamage = null;
	}










}