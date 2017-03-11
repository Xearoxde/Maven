package de.xearox.xcredit;

import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultIntegration {
	
	private XCredit plugin;
	
	public VaultIntegration(){
		this.plugin = XCredit.getInstance();
	}
	
	public boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		XCredit.econ = rsp.getProvider();
		return XCredit.econ != null;
    }
	
	public boolean setupPermission(){
		if(plugin.getServer().getPluginManager().getPlugin("Vault") == null){
			return false;
		}
		RegisteredServiceProvider<Permission> rspPerm = plugin.getServer().getServicesManager().getRegistration(Permission.class);
		if(rspPerm == null) {
			return false;
		}
		XCredit.perm = rspPerm.getProvider();
		return rspPerm != null;
	}

}
