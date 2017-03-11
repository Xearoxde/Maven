package de.xearox.deathcounter.core;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public static Main instance;
	public static String pluginPath;
	public static YamlConfiguration config;
	public static YamlConfiguration playerData;
	public static File configFile;
	public static File playerDataFile;
	
	@Override
	public void onLoad(){
		Main.instance = this;
		Main.pluginPath = this.getDataFolder().getAbsolutePath();
		this.getDataFolder().mkdirs();
		Main.configFile = new File(pluginPath+File.separator+"/config.yml");
		Main.playerDataFile = new File(pluginPath+File.separator+"/playerData.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		playerData = YamlConfiguration.loadConfiguration(playerDataFile);
	}
	
	@Override
	public void onEnable(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(this, this);
		try {
			createConfig(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		
	}
	
	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event){
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if(playerData.getInt("player."+uuid.toString()+".deathCounter") == config.getInt("config.player.maxdeath")){
			event.disallow(PlayerLoginEvent.Result.KICK_OTHER, config.getString("config.message.kickmessage"));
			return;
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		if(playerData.getString("player."+uuid.toString()+".deathCounter") == null){
			playerData.addDefault("player."+uuid.toString()+".name", player.getName());
			playerData.addDefault("player."+uuid.toString()+".deathCounter", 0);
			try {
				playerData.options().copyDefaults(true);
				playerData.save(playerDataFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event){
		
	}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event){
		Player player = event.getEntity();
		UUID uuid = player.getUniqueId();
		int death = playerData.getInt("player."+uuid.toString()+".deathCounter");
		death++;
		if(death < config.getInt("config.player.maxdeath")){
			playerData.set("player."+uuid.toString()+".deathCounter", death);
			try {
				playerData.save(playerDataFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(death == config.getInt("config.player.maxdeath")){
			playerData.set("player."+uuid.toString()+".deathCounter", death);
			try {
				playerData.save(playerDataFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			player.kickPlayer(config.getString("config.message.kickmessage"));
		}
	}
	
	public void createConfig(YamlConfiguration config) throws IOException{
		config.addDefault("config.player.maxdeath", 3);
		config.addDefault("config.message.kickmessage", "Du bist zu oft gestorben!");
		config.options().copyDefaults(true);
		config.save(configFile);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
