package de.xearox.serversaver.core;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public static Main instance;
	public static String pluginPath;
	public static YamlConfiguration config;
	public static File configFile;
	
	
	
	@Override
	public void onLoad(){
		Main.instance = this;
		Main.pluginPath = this.getDataFolder().getAbsolutePath();
		this.getDataFolder().mkdirs();
		Main.configFile = new File(pluginPath+File.separator+"/config.yml");
		config = YamlConfiguration.loadConfiguration(configFile);
		try {
			createConfig(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onEnable(){
		
	    Calendar cal = Calendar.getInstance();
	    
	    int hour = config.getInt("config.server.restart.hour");
	    int minutes = config.getInt("config.server.restart.minutes");
	    int seconds = config.getInt("config.server.restart.seconds");
	    
	    long now = cal.getTimeInMillis();
	    if(cal.get(Calendar.HOUR_OF_DAY) >= hour)
	        cal.add(Calendar.DATE, 1);
	 
	    cal.set(Calendar.HOUR_OF_DAY, hour);
	    cal.set(Calendar.MINUTE, minutes);
	    cal.set(Calendar.SECOND, seconds);
	    cal.set(Calendar.MILLISECOND, 0);
	    
	    long offset = cal.getTimeInMillis() - now;
	    long ticks = offset / 50L;
	    
		Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				try {
					Bukkit.getServer().broadcastMessage(config.getString("config.server.restart.warning.message"));
					Thread.sleep(config.getLong("config.server.restart.warning.delay"));
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "save-all");
					Thread.sleep(1000);
					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "stop");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},ticks);
	}
	
	@Override
	public void onDisable(){
		
	}
	
	public void createConfig(YamlConfiguration config) throws IOException{
		config.addDefault("config.server.restart.hour", 13);
		config.addDefault("config.server.restart.minutes", 30);
		config.addDefault("config.server.restart.seconds", 0);
		config.addDefault("config.server.restart.warning.delay", 30000);
		config.addDefault("config.server.restart.warning.message", "Der Server wird gespeichert und neugestartet!");
		config.options().copyDefaults(true);
		config.save(configFile);
	}
}
