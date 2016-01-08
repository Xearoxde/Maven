package de.xearox.xconomy.utility;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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
	
	public void createDatabaseTable(){
		Statement statement = null;
		try{
			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Accounts"
					+ "(UUID TEXT NOT NULL,"
					+ "PlayerLastName TEXT NOT NULL,"
					+ "Username TEXT NOT NULL,"
					+ "Password TEXT NOT NULL)";
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
		Statement statement = null;
		String sql;
		String uuid;
		if(offlinePlayer == null){
			System.out.println("offlinePlayer null!!");
			return false;
		}
		if(hasAccount(offlinePlayer)){
			System.out.println("Account vorhanden!");
			return false;
		}
		
		String playerName = offlinePlayer.getPlayer().getDisplayName();
		
		try{
			connection = getSQLConnection();
			
			password = MD5Hashing(password);
			
			uuid = offlinePlayer.getUniqueId().toString();
			
			statement = connection.createStatement();
			
			sql = "INSERT INTO Accounts (UUID, PlayerLastName, Username, Password)"
					+ "VALUES ('"+uuid+"','"+playerName+"','"+username+"','"+password+"');";
			
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
	
	public boolean hasAccount(OfflinePlayer offlinePlayer){
		Statement statement;
		ResultSet resultSet;
		Player player = Bukkit.getPlayer(offlinePlayer.getUniqueId());
		String uuid = offlinePlayer.getUniqueId().toString();
		try{
			connection = getSQLConnection();
			
			System.out.println(uuid);
			System.out.println(player.getDisplayName());
			
			System.out.print(connection);
			System.out.println("Vor pre Statement");
			try{
				String sql = "SELECT UUID FROM Accounts WHERE UUID = '"+uuid+"';";
				
				statement = connection.createStatement();
				
				resultSet = statement.executeQuery(sql);
				
				if(resultSet == null){
					return false;
				}
				if(resultSet.getString("UUID").equalsIgnoreCase(uuid.toLowerCase())){
					System.out.println("UUID gefunden!");
					return true;
				} else {
					System.out.println("UUID nicht gefunden!");
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
	
	public Connection getSQLConnection(){
		try{
			if(connection != null && !connection.isClosed()){
				return connection;
			}
			dbName = "/testDB.db";
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"+dbPath+"/data/"+dbName);
			plugin.logger.info("SQL Connection etablished");
			System.out.println("SQL Connection etablished");
			return connection;
		} catch ( Exception e){
			plugin.logger.severe(e.getMessage());
			return null;
			
		}
	}
	
	public void closeSQLConnection(){
		try{
			if(connection!=null){//&&!connection.isClosed()
				System.out.println("Database connection closed");
				connection.close();
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
