package de.xearox.xfriendsserver.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import de.xearox.utility.LogLevel;
import de.xearox.utility.MyLogger;
import de.xearox.utility.Utility;

public class MyDatabase {
	
	private static Connection con = null;
	private static String dbHost;
	private static String dbPort;
	private static String dbName;
	private static String dbUser;
	private static String dbPass;
	private MyLogger myLogger;
	private Utility utility;
	
	public void initDBVars(){
		
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
			if(con == null){
				return;
			}
			con.close();
			con = null;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not close SQL Connection!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
		}
	}
	
	public boolean createAccountTable(){
		con = getConnection();
		try{
			String sql = "CREATE TABLE IF NOT EXISTS Accounts"+
					"(ID 						INT 						NOT NULL 		AUTO_INCREMENT,"+
					" UUID						TEXT						NOT NULL,"+
					" PlayerName 				TEXT 						NOT NULL,"+
					" Email						TEXT						NOT NULL,"+
					" Password					TEXT						NOT NULL,"+
					" IP						TEXT						NOT NULL,"+
					" RegistrationDate			TEXT						NOT NULL,"+
					" OnlineStatus				INT							NOT NULL,"+
					" Invisible					INT							NOT NULL,"+
					" LastOnlineOn				TEXT						NOT NULL,"+
					" VacationStatus			INT							NOT NULL,"+
					" InGame					INT							NOT NULL,"+
					" PlayingOn					TEXT								,"+
					" AutoResponseMessage		TEXT								,PRIMARY KEY (`ID`))";
			PreparedStatement createTable = con.prepareStatement(sql);
			createTable.executeUpdate();
			myLogger.createLogFile(LogLevel.INFO, "Account table successfully created!");
			return true;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not create account table!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
			return false;
		}finally{
			closeConnection();
		}
	}
	
	public boolean createFriendlistTable(String UUID){
		con = getConnection();
		try{
			String sql = "CREATE TABLE IF NOT EXISTS "+UUID+
					"(ID 						INT 						NOT NULL 		AUTO_INCREMENT,"+
					" UUID						TEXT						NOT NULL,"+
					" PlayerName 				TEXT 						NOT NULL,"+
					" Nickname	 				TEXT 						NOT NULL,"+
					" Group						TEXT						NOT NULL,"+
					" Blocked					INT								,PRIMARY KEY (`ID`))";
			PreparedStatement createTable = con.prepareStatement(sql);
			createTable.executeUpdate();
			myLogger.createLogFile(LogLevel.INFO, "Account table successfully created!");
			return true;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not create FriendList table for "+UUID+"!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
			return false;
		}finally{
			closeConnection();
		}
	}
	
	public boolean updatePlayerOnlineStatus(String UUID, int OnlineStatus){
		if(!checkIfAccountExists(UUID)){
			return true;
		}
		con = getConnection();
		try{
			String sql = "UPDATE Accounts SET OnlineStatus = ? WHERE UUID = ?";
			PreparedStatement updateTable = con.prepareStatement(sql);
			updateTable.setInt(1, OnlineStatus);
			updateTable.setString(2, UUID);
			updateTable.executeUpdate();
			myLogger.createLogFile(LogLevel.INFO, "Online status successfully updated for "+UUID+" in account table!");
			return true;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not update online status in account table for "+UUID+"!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
			return false;
		}finally{
			closeConnection();
		}
	}
	
	public boolean insertNewAccount(String UUID, String PlayerName, String Email, String Password, String IP){
		con = getConnection();
		try{
			if(checkIfAccountExists(UUID)){
				return true;
			}
			String sql = "INSERT INTO Accounts "
					+ "(UUID, PlayerName, Email, Password, IP, RegistrationDate, OnlineStatus, Invisible, LastOnlineOn, VacationStatus, InGame) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insertAccount = con.prepareStatement(sql);
			insertAccount.setString(1, UUID);
			insertAccount.setString(2, PlayerName);
			insertAccount.setString(3, Email);
			insertAccount.setString(4, utility.getMD5(Password));
			insertAccount.setString(5, IP);
			insertAccount.setString(6, utility.getDate("yyyy MM dd", Locale.ENGLISH));
			insertAccount.setInt(7, 1); // The Online Status: 0 = Offline, 1 = Online, Default = 1
			insertAccount.setInt(8, 0); // The Invisible Status: 0 = Visible, 1 = Invisible, Default = 0
			insertAccount.setString(9, utility.getDate("yyyy MM dd", Locale.ENGLISH));
			insertAccount.setInt(10, 0); // The Vacation Status: 0 = Not in Vacation, 1 = In Vacation, Default = 0
			insertAccount.setInt(11, 1); // The InGame Status: 0 = Not InGame, 1 = Is InGame, Default = 1
			insertAccount.executeLargeUpdate();
			myLogger.createLogFile(LogLevel.INFO, "The user "+PlayerName+" with the UUID "+UUID+" was created!");
			return true;
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not create new account!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
			return false;
		}finally{
			closeConnection();
		}
	}
	
	public boolean checkIfAccountExists(String UUID){
		con = getConnection();
		try{
			String sql = "SELECT UUID FROM Accounts WHERE UUID = ?";
			boolean request = false;
			ResultSet resultSet = null;
			PreparedStatement selectAccount = con.prepareStatement(sql);
			selectAccount.setString(1, UUID);
			resultSet = selectAccount.executeQuery();
			request = resultSet.next();
			
			if(request){
				return true;
			} else {
				return false;
			}
		} catch (SQLException e){
			myLogger.createLogFile(LogLevel.ERR, "Could not select an account!");
			myLogger.createLogFile(LogLevel.ERR, e.getMessage());
			myLogger.createLogFile(LogLevel.ERR, e.getSQLState());
			myLogger.createLogFile(LogLevel.ERR, Integer.toString(e.getErrorCode()));
			e.printStackTrace();
			return false;
		}finally{
			closeConnection();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
