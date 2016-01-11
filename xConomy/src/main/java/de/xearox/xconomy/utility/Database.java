package de.xearox.xconomy.utility;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.httpserver.LoginData;
import de.xearox.xconomy.XConomy;

public class Database {

	private XConomy plugin;
	private Connection connection;
	private Player player;
	private OfflinePlayer offlinePlayer;
	private String dbPath;
	private String dbName;
	
	

	public Database(XConomy plugin){
		this.plugin = plugin;
		this.dbPath = plugin.getDataFolder().getAbsolutePath();
		File configDir = new File(XConomy.directory+"/config/");
		File configFile = new File(configDir+"/config.yml");
		
		YamlConfiguration yamlFile = YamlConfiguration.loadConfiguration(configFile);
		//this.dbName = yamlFile.getString("Config.DBName");
	}
	
	public void createAccountsDBTable(){
		Statement statement = null;
		try{
			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Accounts"
					+ "(UUID TEXT NOT NULL,"
					+ "playerlastname TEXT NOT NULL,"
					+ "username TEXT NOT NULL,"
					+ "password TEXT NOT NULL,"
					+ "admin INTEGER NOT NULL)";
			statement.executeQuery(sql);
					
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				} finally {
					closeSQLConnection();
				}
		}
	}
	
	public void createCookiesDBTable(){
		Statement statement = null;
		try{
			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Cookies"
					+ "(username TEXT NOT NULL,"
					+ "ip TEXT NOT NULL,"
					+ "key TEXT NOT NULL,"
					+ "loggedin INTEGER NOT NULL)";
			statement.executeQuery(sql);
					
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				} finally {
					closeSQLConnection();
				}
		}
	}
	
	public boolean createNewPlayer(OfflinePlayer offlinePlayer, String username, String password){
		int isAdmin = 0;
		
		if(plugin.getServer().getOperators().contains(offlinePlayer)){
			isAdmin = 1;
		} else {
			isAdmin = 0;
		}
		
		Statement statement = null;
		String sql;
		String uuid;
		if(offlinePlayer == null){
			return false;
		}
		if(hasAccount(offlinePlayer)){
			return false;
		}
		
		String playerName = offlinePlayer.getPlayer().getDisplayName();
		
		try{
			connection = getSQLConnection();
			
			password = MD5Hashing(password);
			
			uuid = offlinePlayer.getUniqueId().toString();
			
			statement = connection.createStatement();
			
			sql = "INSERT INTO Accounts (UUID, playerlastname, username, password, admin)"
					+ "VALUES ('"+uuid+"','"+playerName+"','"+username+"','"+password+"','"+isAdmin+"');";
			
			statement.executeUpdate(sql);
			
			return true;
		} catch ( SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQLConnection();
		}
		return false;
	}
	
	public void createNewCookie(Map<String, String> cookies){
		Statement statement = null;
		String sql;
		
		try{
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			sql = "INSERT INTO Cookies (username, ip, key, loggedin)"
					+ "VALUES ('"+cookies.get("username")+"','"+cookies.get("ip")
					+ "','"+cookies.get("key")+"','"+cookies.get("loggedin")+"');";
			
			statement.executeUpdate(sql);
			
		} catch ( SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeSQLConnection();
		}
	}
	
	public Map<String, String> getCookies(Map<String, String> cookies, LoginData loginData) throws SQLException{
		Statement statement;
		ResultSet resultSet;
		
		connection = getSQLConnection();
			
		String sql = "SELECT * FROM Cookies WHERE username = '"+loginData.username+"';";
				
		statement = connection.createStatement();
				
		resultSet = statement.executeQuery(sql);
		
		if(resultSet.getString("username").equalsIgnoreCase(loginData.username)){
			cookies.put("username", resultSet.getString("username"));
			cookies.put("ip", resultSet.getString("ip"));
			cookies.put("key", resultSet.getString("key"));
			cookies.put("loggedin", resultSet.getString("loggedin"));
			closeSQLConnection();
			return cookies;
		} else {
			closeSQLConnection();
			return cookies;
		}
	}
	
	public boolean hasAccount(OfflinePlayer offlinePlayer){
		Statement statement;
		ResultSet resultSet;
		Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
		String uuid = offlinePlayer.getUniqueId().toString();
		try{
			connection = getSQLConnection();
			
			try{
				String sql = "SELECT UUID FROM Accounts WHERE UUID = '"+uuid+"';";
				
				statement = connection.createStatement();
				
				resultSet = statement.executeQuery(sql);
				
				if(resultSet == null){
					return false;
				}
				if(resultSet.getString("UUID").equalsIgnoreCase(uuid.toLowerCase())){
					return true;
				} else {
					return false;
				}
				
			} catch (SQLException e){
				return false;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
			//System.out.println("Nach pre Statement");
		} catch (Exception e){
			e.printStackTrace();
			return false;
		} finally {
			closeSQLConnection();
		}
	}
	
	public void updateAccount(){
		
	}
	
	public int getInt(){
		return 0;
	}
	
	public String getString(){
		return "";
	}
	
	public boolean checkLoginData(String username, String password){
		Statement statement;
		ResultSet resultSet;
		
		try{
			connection = getSQLConnection();
			
			password = MD5Hashing(password);
			
			String sql = "SELECT * FROM Accounts WHERE Username = '"+username+"';";
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sql);
			
			if(resultSet == null){
				return false;
			}
			
			
			if(resultSet.getString("Password").equalsIgnoreCase(password)){
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e){
			//e.printStackTrace();
		} finally {
			closeSQLConnection();
		}
		
		return false;
	}
	
	public String getPlayerUUID(String username){
		Statement statement;
		ResultSet resultSet;
		
		try{
			connection = getSQLConnection();
			
			String sql = "SELECT * FROM Accounts WHERE Username = '"+username+"';";
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sql);
			
			if(resultSet == null){
				return "";
			}
			
			return resultSet.getString("UUID");
			
		} catch (Exception e){
			//e.printStackTrace();
		} finally {
			closeSQLConnection();
		}
		
		return "";
	}
	
	public boolean removeCookie(String cookieKey){
		Statement statement;
		
		try{
			connection = getSQLConnection();
			
			String sql = "DELETE FROM Cookies WHERE key = '"+cookieKey+"';";
			
			statement = connection.createStatement();
			
			statement.executeUpdate(sql);
			
			statement.close();
			
			return true;
			
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			closeSQLConnection();
		}
		
		return false;
	}
	
	public Connection getSQLConnection(){
		try{
			if(connection != null && !connection.isClosed()){
				return connection;
			}
			dbName = "/testDB.db";
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath+"/data/"+dbName);
			return connection;
		} catch ( Exception e){
			plugin.logger.severe(e.getMessage());
			return null;
			
		}
	}
	
	public void closeSQLConnection(){
		try{
			if(connection!=null){
				connection.close();
				connection = null;
				return;
			}
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			
		}
	}
	
	public String MD5Hashing(String Value)throws Exception
    {
    	String password = Value;
    	
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
	
}
