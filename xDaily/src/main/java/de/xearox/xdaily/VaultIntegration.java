package de.xearox.xdaily;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultIntegration {
	
	private XDaily plugin;
	
	public VaultIntegration(XDaily plugin){
		this.plugin = plugin;
	}
	
	public boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		XDaily.econ = rsp.getProvider();
		return XDaily.econ != null;
    }
	
	public boolean setupPermission(){
		if(plugin.getServer().getPluginManager().getPlugin("Vault") == null){
			return false;
		}
		RegisteredServiceProvider<Permission> rspPerm = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		if(rspPerm == null) {
			return false;
		}
		XDaily.perm = rspPerm.getProvider();
		return rspPerm != null;
	}

}
