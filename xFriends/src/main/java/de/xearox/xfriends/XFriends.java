package de.xearox.xfriends;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import de.xearox.xfriends.handler.MyDatabase;
import de.xearox.xfriends.utility.CreateConfig;
import de.xearox.xfriends.utility.MyLogger;
import de.xearox.xfriends.utility.Utility;

public class XFriends extends JavaPlugin{
	
	private MyLogger myLogger;
	private Utility utility;
	private MyDatabase database;
	private CreateConfig createConfig;
	
	public MyLogger getMyLogger(){
		return myLogger;
	}
	
	public Utility getUtility(){
		return utility;
	}
	
	public MyDatabase getMyDatabase(){
		return database;
	}
	
	@Override
	public void onLoad(){
		this.myLogger = new MyLogger(this);
	}
	
	@Override
	public void onEnable(){
		this.utility = new Utility(this);
		this.database = new MyDatabase(this);
		this.createConfig = new CreateConfig(this);
		
		this.createConfig.createConfig();
		
		this.database.openConnection();
		this.database.closeConnection();
	}
	
	@Override
	public void onDisable(){
		
	}
	
	
}
