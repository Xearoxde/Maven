package de.xearox.tutorial;


import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;


public class Database{
	
	public static Plugin plugin;
	
	public Database(Plugin plugin){
		Database.plugin = plugin;
	}

	Connection connection;
	
	static String DBPath;
	static String DBName;
	static String PlayerListTable;
	static String PlayerMiningTable;
	static String PlayerTradingTable;
	static String PlayerSignTradeTable;
	String PlayerUUID;
	
	public void CreatePlayerMiningTable(){
		
		Statement statement = null;
		try{
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "+PlayerMiningTable+
						"(UUID			TEXT					NOT NULL,"+
						" PlayerName 	TEXT 					NOT NULL,"+
						" Grass			INT						NOT NULL,"+
						" Stone			INT						NOT NULL,"+
						" Sand			INT						NOT NULL,"+
						" Log			INT						NOT NULL,"+
						" Other			INT						NOT NULL)";
			
			System.out.println("Datenbank wurde erstellt");
			statement.executeUpdate(sql);
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 3");
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}
			}
	}
	
	public void CreatePlayerListTable(){
		
		Statement statement = null;
		try{
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "+PlayerListTable+
						"(UUID			TEXT					NOT NULL,"+
						" PlayerName 	TEXT 					NOT NULL,"+
						" PlayerDeath 	INT 					NOT NULL,"+
						" Redling		REAL					NOT NULL,"+
						" Xearling		INT						NOT NULL)";
			
			System.out.println("Datenbank wurde erstellt");
			statement.executeUpdate(sql);
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 3");
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}
			}
	}
	
	public void CreatePlayerTradingTable(){
		Statement statement = null;
		String TempString = null;
		int TempInt = 0;
		int TempInt2 = 0;
		int TempInt3 = 0;
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		//if(!plugin.getConfig().getBoolean("Database.TableName.PlayerTrading.Created"))
		try{
			System.out.println(ChatColor.RED+"The table for "+PlayerTradingTable+" will now created!");
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "+PlayerTradingTable+
						"(UUID			TEXT					NOT NULL,"+
						" PlayerName 	TEXT 					NOT NULL)";
			
			System.out.println("Datenbank wurde erstellt");
			statement.executeUpdate(sql);
			
			TempInt2 = Material.values().length;
			
			for(Material c : Material.values()){
				TempString = c.toString().toLowerCase();
				AddCloumn(PlayerTradingTable, TempString, "TXT");
				TempInt++;
				TempInt3++;
				if(TempInt3 == 5){
					Bukkit.getConsoleSender().sendMessage(ChatColor.RED+df.format(((float) (TempInt * 100 / TempInt2))).toString()+" % created");
					TempInt3 = 0;
				}					
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Es wurden "+TempInt+" Spalten hinzugefügt");
			//plugin.getConfig().set("Database.TableName.PlayerTrading.Created", "true");
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 3");
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}
			}
	}
	
	public void CreatePlayerSignTrade(){
		
		Statement statement = null;
		try{
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "+PlayerSignTradeTable+
						"(UUID			TEXT						NOT NULL,"+
						" PlayerName 	TEXT	 					NOT NULL,"+
						" BlockPosWorld	TEXT	 					NOT NULL,"+
						" BlockXPos		TEXT						NOT NULL,"+
						" BlockYPos		TEXT						NOT NULL,"+
						" BlockZPos		TEXT						NOT NULL,"+
						" SignTradeType	TEXT						NOT NULL,"+
						" ItemToSell	TEXT						NOT NULL,"+
						" ItemValue		TEXT						NOT NULL,"+
						" UniqueID		TEXT						NOT NULL)";
						
			
			System.out.println("Datenbank wurde erstellt");
			statement.executeUpdate(sql);
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 3");
		}finally{
			try{					
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}
			}
	}
	
	public void UpdatePlayerDeath(String PlayerName){
		int Result;
		Result = 0;
		try{
			Result = GetPlayerDeath(PlayerName);
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		Result++;
		
		
		
		PreparedStatement prestatement = null;
		
		Statement statement = null;
		
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		try{			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "UPDATE "+PlayerListTable+" SET PlayerDeath ="+Result+" WHERE UUID ='"+PlayerUUID+"'";
			
			statement.executeUpdate(sql);
			
			System.out.println("Death successfully updated");
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}
	
	public void AddNewPlayer(String PlayerName){
		Statement statement = null;
		String sql;
		if((FindPlayer(PlayerName,1))&&(FindPlayer(PlayerName,2))&&(FindPlayer(PlayerName,3))){
			System.out.println("Player allready in the Database");
		}else{
			try{
				
				connection = getSQLConnection();
				
				PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
				
				statement = connection.createStatement();
				System.out.println("####################################################################");
				
				if(!FindPlayer(PlayerName,1)){
				sql = "INSERT INTO "+PlayerListTable+" (UUID,PlayerName,PlayerDeath,Redling,Xearling)"
						+ "VALUES ('"+PlayerUUID+"','"+PlayerName+"',0,500,2 );";
				System.out.println("Player added to table: "+PlayerListTable);
				System.out.println("####################################################################");
				statement.executeUpdate(sql);
				}else System.out.println("Player already in the Database "+PlayerListTable);
				
				if(!FindPlayer(PlayerName,2)){
				sql = "INSERT INTO "+PlayerMiningTable+" (UUID,PlayerName,Grass,Stone,Sand,Log,Other)"
						+ "VALUES ('"+PlayerUUID+"','"+PlayerName+"',0,0,0,0,0 );";
				System.out.println("Player added to table: "+PlayerMiningTable);	
				System.out.println("####################################################################");
				statement.executeUpdate(sql);
				}else System.out.println("Player already in the Database "+PlayerMiningTable);
				
				if(!FindPlayer(PlayerName,3)){
				sql = "INSERT INTO "+PlayerTradingTable+" (UUID, PlayerName) VALUES ('"+PlayerUUID+"','"+PlayerName+"');";
				System.out.println("Player added to table: "+PlayerTradingTable);
				System.out.println("####################################################################");
				statement.executeUpdate(sql);
				}else System.out.println("Player allready in the Database "+PlayerTradingTable);
				
				
				statement.close();
				
			}catch ( Exception e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}
	
	public boolean FindPlayer(String PlayerName,int TableNumber){
		//
		Statement statement = null;
		PreparedStatement prestatement = null;
		ResultSet resultset = null;
		Boolean abfrage = false;
		String TableName = null;
		switch (TableNumber){
			case 1: TableName = PlayerListTable;
								break;
			
			case 2: TableName = PlayerMiningTable;
								break;
								
			case 3: TableName = PlayerTradingTable;
								break;
		}
		try{
			connection = getSQLConnection();
			
			PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
			
			prestatement = connection.prepareStatement("SELECT UUID FROM "+TableName+" WHERE UUID = '"+PlayerUUID+"';");
			
			resultset = prestatement.executeQuery();			
			
			try{
				abfrage = resultset.getString("UUID").equalsIgnoreCase(PlayerUUID.toLowerCase());
			}catch ( SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
			//if(resultset.getString("PlayerName").equalsIgnoreCase(PlayerName.toLowerCase())){
			if(abfrage){
				System.out.println("Player found");
				return true;
			}else{
				System.out.println("Player not Found");
				return false;

			}
			
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Blaaa1");
			System.out.println("resultset = "+resultset);
		}finally{
			try{
				//if(connection != null){
					//connection.close();
					//System.out.println("Connection geschlossen");
				//}
					
				if(prestatement != null){
					prestatement.close();
					System.out.println("PreStatement geschlossen");
				}
				if(statement != null){
					statement.close();
					System.out.println("Statement geschlossen");
				}
				if(resultset != null){
					resultset.close();
					System.out.println("Resultset geschlossen");
				}
				}catch (SQLException e){
					System.err.println( e.getClass().getName() + ": " + e.getMessage() );
				}
			}		
		return false;
		
	}
	
	public Integer GetPlayerDeath(String PlayerName){
		
		
		PreparedStatement prestatement = null;		
		ResultSet resultset = null;
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		try{			
			connection = getSQLConnection();			
			prestatement = connection.prepareStatement("SELECT * FROM "+PlayerListTable+" WHERE UUID = '"+PlayerUUID+"';");
			
			resultset = prestatement.executeQuery();			
			while(resultset.next()){
				if(resultset.getString("UUID").equalsIgnoreCase(PlayerUUID.toLowerCase())){
					return resultset.getInt("PlayerDeath");
				}
			}
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			return null;
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
		return null;
		
	}
	
	public void UpdatePlayerBlockBreak(String PlayerName, String BlockType){
		int Result;
		Result = GetPlayerDeath(PlayerName);
		Result++;
		
		String BlockName;
		
		PreparedStatement prestatement = null;
		
		Result = 0;
		BlockName = "Other";
		
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		
		Statement statement = null;
		
		if((BlockType.equals("GRASS"))|(BlockType.equals("DIRT")))
			BlockName = "Grass";
		else
		if((BlockType.equals("STONE"))|(BlockType.equals("COBBLESTONE")))
			BlockName = "Stone";
		else
		if(BlockType.equals("SAND"))
			BlockName = "Sand";
		else	
		if(BlockType.equals("LOG_2"))
			BlockName = "Log";
		else
		if(!BlockType.equals(""))
			BlockName = "Other";		
		
		try{
			Result = GetPlayerBlockBreak(PlayerName, BlockName);
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		Result++;
		
		try{
			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "UPDATE "+PlayerMiningTable+" SET "+BlockName+" ="+Result+" WHERE UUID ='"+PlayerUUID+"'";
			
			statement.executeUpdate(sql);
			
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
				//if(connection != null)
					//connection.close();
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}

	public Integer GetPlayerBlockBreak(String PlayerName, String BlockName){
		
		//
		PreparedStatement prestatement = null;		
		ResultSet resultset = null;
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		
		try{			
			connection = getSQLConnection();			
			prestatement = connection.prepareStatement("SELECT * FROM "+PlayerMiningTable+" WHERE UUID = '"+PlayerUUID+"';");
			
			resultset = prestatement.executeQuery();			
			while(resultset.next()){
				if(resultset.getString("UUID").equalsIgnoreCase(PlayerUUID.toLowerCase())){
					return resultset.getInt(BlockName);
				}
			}
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 1");
			return null;
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
				//if(connection != null)
					//connection.close();
				if(resultset != null)
					resultset.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
		return null;
		
	}

	public Connection getSQLConnection(){
		try{
			if(connection!=null){
				return connection;
			}else{			
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:"+DBPath+DBName);
				System.out.println("Opened database successfully");
				return connection;
			}
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}
		return null;
		
	}

	public void CloseSQLConnection(){
		try{
			if(connection!=null&&!connection.isClosed()){
				System.out.println("Database connection closed");
				connection.close();
				return;
			}
		}catch ( Exception e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			
		}
	}

	public void AddCloumn(String TableName2, String ColumnName, String ColumnType){
		String sql;
		//
		Statement statement = null;				
		try{			
			connection = getSQLConnection();			
			statement = connection.createStatement();
			
			sql = "ALTER TABLE "+TableName2+" ADD COLUMN "+ColumnName+" "+ColumnType+";";
			statement.execute(sql);			
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 1");
		}finally{
			try{
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}		
	}
	
	public void RenameTable(String OldTableName, String NewTableName){
		String sql;
		//
		Statement statement = null;				
		try{			
			connection = getSQLConnection();			
			statement = connection.createStatement();
			
			sql = "ALTER TABLE "+OldTableName+" RENAME TO "+NewTableName;
			statement.execute(sql);			
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.out.println("Hier 1");
		}finally{
			try{
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}		
	}
	
	public void UpdatePlayerCurrency(String PlayerName, String Currency, float Value){
		
		
		
		PreparedStatement prestatement = null;
		
		Statement statement = null;
		
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		try{			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "UPDATE "+PlayerListTable+" SET "+Currency+" ="+Value+" WHERE UUID ='"+PlayerUUID+"'";
			
			statement.executeUpdate(sql);
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}

	public float GetValue(String TableName, String PlayerName, String ColumnName){		
		PreparedStatement prestatement = null;		
		ResultSet resultset = null;
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		
		//
		
		try{			
			connection = getSQLConnection();			
			prestatement = connection.prepareStatement("SELECT * FROM "+TableName+" WHERE UUID = '"+PlayerUUID+"';");
			
			resultset = prestatement.executeQuery();			
			while(resultset.next()){
				if(resultset.getString("UUID").equalsIgnoreCase(PlayerUUID.toLowerCase())){
					return resultset.getFloat(ColumnName);
				}
			}
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
		return 0;
	}

	public void InsertSignsToTable(String PlayerName,String...args ){
		Statement statement = null;
		String sql;
			try{
				connection = getSQLConnection();
				
				PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
				
				statement = connection.createStatement();
				
				sql = "INSERT INTO "+PlayerSignTradeTable+" (UUID,PlayerName,BlockPosWorld,BlockXPos,BlockYPos,BlockZPos,"
						+ "SignTradeType,ItemToSell,ItemValue,UniqueID)"
						+ "VALUES ('"+PlayerUUID+"','"+PlayerName+"','"+args[0]+"','"+args[1]+"','"+args[2]+"','"
						+args[3]+"','"+args[4]+"','"+args[5]+"','"+args[6]+"','"+args[7]+"');";
				System.out.println("Sign added to table: "+PlayerSignTradeTable);
						
				statement.executeUpdate(sql);
								
				statement.close();
				
			}catch ( SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	
	public void RemoveFromTable(String TableName, String ColumnName, String ColumnValue){
		
		
		Statement statement = null;		
		String sql = null;
		try{			
			connection = getSQLConnection();			
			statement = connection.createStatement();
			
			sql = "DELETE FROM "+TableName+" WHERE "+ColumnName+" = '"+ColumnValue+"';";
			
			statement.executeUpdate(sql);			
			
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}

	public String GetSign(String MD5Hash, String ColumnName){
		PreparedStatement prestatement = null;		
		ResultSet resultset = null;
		try{
			connection = getSQLConnection();			
			prestatement = connection.prepareStatement("SELECT * FROM "+PlayerSignTradeTable+" WHERE UniqueID = '"+MD5Hash+"';");
			
			resultset = prestatement.executeQuery();
			
			while(resultset.next()){
				if(resultset.getString("UniqueID").equalsIgnoreCase(MD5Hash.toLowerCase())){
					return resultset.getString(ColumnName);
				}
			}
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
		
		
		return "";
	}

	public void UpdateTradingTable(String PlayerName, int Value, String ColumnName){		
		PreparedStatement prestatement = null;
		
		Statement statement = null;
		
		PlayerUUID = Bukkit.getPlayer(PlayerName).getUniqueId().toString();
		try{			
			connection = getSQLConnection();
			
			statement = connection.createStatement();
			
			String sql = "UPDATE "+PlayerTradingTable+" SET "+ColumnName+" ="+Value+" WHERE UUID ='"+PlayerUUID+"'";
			
			statement.executeUpdate(sql);
			
		}catch ( SQLException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		}finally{
			try{
				if(prestatement != null)
					prestatement.close();
				if(statement != null)
					statement.close();
			}catch (SQLException e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			}
		}
	}
















}