package de.xearox.xfactory;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xfactory.events.ChunkUnloadEvent;
import de.xearox.xfactory.events.PlayerClickEvent;
import de.xearox.xfactory.events.PlayerPlacedBlockEvent;
import de.xearox.xfactory.listeners.MyExecutor;
import de.xearox.xfactory.myclasses.BlockData;
import de.xearox.xfactory.utility.Utility;

public class XFactory extends JavaPlugin{
	
	private MyExecutor myExecutor;
	private Utility utility;
	
	public static HashMap<Player, Location> pos1Map;
	public static HashMap<Player, Location> pos2Map;
	public static HashMap<Player, HashMap<BlockData, Integer>> materialMap;
	public static boolean blockAnalyzingStarted = false;
	
	
	@Override
	public void onEnable(){
		pos1Map = new HashMap<Player, Location>();
		pos2Map = new HashMap<Player, Location>();
		materialMap = new HashMap<Player, HashMap<BlockData, Integer>>();
		this.utility = new Utility(this);
		
		createCommands();
		registerListener();
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@Override
	public void onLoad(){
		
	}
	
	public Utility getUtility(){
		return this.utility;
	}
	
	public void createCommands(){
		myExecutor = new MyExecutor(this);
		getCommand("factory").setExecutor(myExecutor);
		getCommand("xfactory").setExecutor(myExecutor);
		//getCommand("test").setExecutor(myExecutor);
		//getCommand("daily createRewards").setExecutor(myExecutor);
	}
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerClickEvent(this), this);
		pluginManager.registerEvents(new PlayerPlacedBlockEvent(this), this);
		pluginManager.registerEvents(new ChunkUnloadEvent(this), this);
	}
}
