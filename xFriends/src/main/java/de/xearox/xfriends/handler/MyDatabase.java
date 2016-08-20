package de.xearox.xfriends.handler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.utility.LogLevel;
import de.xearox.xfriends.utility.MyLogger;
import de.xearox.xfriends.utility.Utility;

public class MyDatabase {
	
	private static Connection con = null;
	private static String dbHost;
	private static String dbPort;
	private static String dbName;
	private static String dbUser;
	private static String dbPass;
	
	private Utility utility;
	private MyLogger myLogger;
	
	public MyDatabase(XFriends plugin) {
		this.utility = plugin.getUtility();
		this.myLogger = plugin.getMyLogger();
		initDBVars();
	}
	
	public void initDBVars(){
		File configFile = utility.getFile("config", "config", "yml");
		YamlConfiguration yamlConfigFile = utility.yamlCon(configFile);
		
		dbHost = yamlConfigFile.getString("Database.dbHost");
		dbPort = yamlConfigFile.getString("Database.dbPort");
		dbName = yamlConfigFile.getString("Database.dbName");
		dbUser = yamlConfigFile.getString("Database.dbUser");
		dbPass = yamlConfigFile.getString("Database.dbPassword");
		
	}
	
	public Connection getConnection(){
		if(con == null){
			return openConnection();
		}
		return con;
	}
	
	public Connection openConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://"+dbHost+":"+ dbPort+"/"+dbName+"?"+"user="+dbUser+"&"+"password="+dbPass);
			myLogger.createLogFile(LogLevel.INFO, "Established SQL Connection Successfully!");
			return con;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not establish SQL Connection!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
		} catch (ClassNotFoundException e) {
			myLogger.createLogFile(LogLevel.ERR, "SQL Driver Not Found!");
		}
		return null;
	}
	
	public void closeConnection(){
		try{
			con.close();
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not close SQL Connection!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
