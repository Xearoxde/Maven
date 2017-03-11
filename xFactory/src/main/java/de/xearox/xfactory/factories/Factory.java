package de.xearox.xfactory.factories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import de.xearox.xfactory.XFactory;
import de.xearox.xfactory.utility.FactoryType;
import net.md_5.bungee.api.ChatColor;

public class Factory implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public XFactory plugin;
	
	public String factoryName;
	
	public OfflinePlayer owner;
	public ArrayList<OfflinePlayer> coOwnerList;
	public BukkitTask buildingTask;
	
	public int factoryLevel = 1;
	public int workers = 0;
	public long buildSpeed = 5L;
	public int blocksBuilt = 0;
	public int blockCount = 0;
	
	public Location location;
	
	public float hourlyCost = 0;
	public float hourlyIncome = 0;
	
	public FactoryType factoryType;
	
	public HashMap<Material, Integer> factoryCost;
	
	public BuildingState buildingState;
	
	
	public double getConstructionProgress(){
		double progress = blocksBuilt*100.0/blockCount;
		progress = (double)Math.round(progress * 100)/100;
		return progress;
	}
	
	private void setBlockCount(){
		if(factoryCost == null){
			return;
		}
		int blockCount = 0;
		for(int i : factoryCost.values()){
			blockCount += i;
		}
		
		this.blockCount = blockCount;
	}
	
	public BukkitTask init(XFactory plugin, FactoryType factoryType, String factoryName, OfflinePlayer owner){
		if(plugin == null){System.out.println("[XFACTORY] Init Factory: plugin = null!"); return null;} else this.plugin = plugin;
		if(factoryType == null){System.out.println("[XFACTORY] Init Factory: factoryType = null!"); return null;} else this.factoryType = factoryType;
		if(factoryName == null){System.out.println("[XFACTORY] Init Factory: factoryName = null!"); return null;} else this.factoryName = factoryName;
		if(owner == null){System.out.println("[XFACTORY] Init Factory: owner = null!"); return null;} else this.owner = owner;
		factoryCost = new HashMap<Material, Integer>();
		factoryCost.put(Material.COBBLESTONE, 250);
		factoryCost.put(Material.LOG, 100);
		
		owner.getPlayer().sendMessage(ChatColor.AQUA+"Construction started!");
		
		setBlockCount();
		
		buildingState = BuildingState.CONSTRUCTION;
		buildingTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				if(buildingState == BuildingState.COMPLETE){
					owner.getPlayer().sendMessage(ChatColor.AQUA+"[XFACTORY] "+ChatColor.DARK_GREEN+"Your factory "+ChatColor.YELLOW+Factory.this.factoryName
							+ChatColor.DARK_GREEN+" is now ready!");
					buildingTask.cancel();
				}
				if(getConstructionProgress() >= 100.0){
					buildingState = BuildingState.COMPLETE;
				} else {
					owner.getPlayer().sendMessage("BuildingProgress = "+getConstructionProgress()+"%");
					blocksBuilt += 9;
				}
			}
		}, 10L*20L, buildSpeed*20L);
		return buildingTask;
	}
	
	enum BuildingState{
		CONSTRUCTION,
		COMPLETE;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
