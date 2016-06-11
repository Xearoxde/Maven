package de.xearox.tutorial;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
	final FileConfiguration config = this.getConfig();
	
	@Override
	public void onEnable(){
		InitConfig();
		LoadConfig();
		InitPlugin();
		
	}
	
	private void InitPlugin(){
		Database database = new Database(null);
		if(this.getConfig().getString("Plugin.Configured").equalsIgnoreCase("false")){
			System.out.println("Das Plugin muss Konfiguriert werden!");
			System.out.println("Anschlie�end bei Database.Enable ein true und bei");
			System.out.println("Plugin.Configured ebenfalls ein true setzen");
			System.exit(0);
		}else{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Plugin wurde geladen!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Author" + ChatColor.GREEN+" Xearox");
			Bukkit.getPluginManager().registerEvents(new Events(this), this);
		}
		
		try {
			//Database Stuff
			if(this.getConfig().getString("Database.Enable").equalsIgnoreCase("true")){
				database.getSQLConnection();
				database.CreatePlayerListTable();
				database.CreatePlayerMiningTable();
				database.CreatePlayerTradingTable();
				database.CreatePlayerSignTrade();
			}else{
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Datenbankverbindung abgeschlatet, siehe Config!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void LoadConfig(){
		
		Database.DBPath = config.getString("Database.DatabasePath");
		Database.DBName = config.getString("Database.DatabaseName");
		Database.PlayerListTable = config.getString("Database.TableName.PlayerList.Name");
		Database.PlayerMiningTable = config.getString("Database.TableName.PlayerMining.Name");
		Database.PlayerTradingTable = config.getString("Database.TableName.PlayerTrading.Name");
		Database.PlayerSignTradeTable = config.getString("Database.TableName.PlayerSignTrade.Name");
	}	
	
	private void InitConfig(){
		
		//reloadConfig();
		
		config.options().header("!!!Do not change this Configuration File!!!");
		config.addDefault("Plugin.Configured", "false");
		config.addDefault("Database.DatabasePath", "PathOfDatabase");
		config.addDefault("Database.DatabaseName" ,"DatabaseName");
		config.addDefault("Database.TableName.PlayerList" ,"");
		config.addDefault("Database.TableName.PlayerList.Name", "player_list");
		config.addDefault("Database.TableName.PlayerList.Created", "false");
		config.addDefault("Database.TableName.PlayerMining", "");
		config.addDefault("Database.TableName.PlayerMining.Name", "player_mining");
		config.addDefault("Database.TableName.PlayerMining.Created", "false");
		config.addDefault("Database.TableName.PlayerTrading", "");
		config.addDefault("Database.TableName.PlayerTrading.Name", "player_trading");
		config.addDefault("Database.TableName.PlayerTrading.Created", "false");
		config.addDefault("Database.TableName.PlayerSignTrade", "");
		config.addDefault("Database.TableName.PlayerSignTrade.Name", "player_sign_trade");
		config.addDefault("Database.TableName.PlayerSignTrade.Created", "false");
		config.addDefault("Database.Enable", "false");
		config.options().copyDefaults(true);
		
		saveConfig();		
	}
		
	@Override
	public void onDisable(){
		Database database = new Database(null);
		System.out.println("Das Plugin wurde deaktivert");
		database.CloseSQLConnection();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,String[] args){
		Database database = new Database(null);
		sender.sendMessage("Online Players = "+Bukkit.getServer().getOnlinePlayers().size());
		if(label.equalsIgnoreCase("test")){
			Bukkit.getServer().getOnlinePlayers().size();
			return true;
		}
		
		if(label.equalsIgnoreCase("playerdeath")){
			if (args.length == 1){
				String PlayerName = args[0];
				if(Bukkit.getPlayer(PlayerName) != null){
					int PlayerDeathCount = database.GetPlayerDeath(PlayerName);
					sender.sendMessage(ChatColor.DARK_PURPLE + "Der Spieler "+PlayerName+" ist breits "+Integer.toString(PlayerDeathCount)+" mal gestorben!");
					return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Der Spieler existiert nicht! PlayerDeath 1");
					return true;
				}
			}else{
				sender.sendMessage(ChatColor.RED + "Zuviele oder zuwenige Argumente! PlayerDeat 2");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("findplayer")){
			if (args.length == 1){
				String PlayerName = args[0];
				boolean PlayerDeathCount = database.FindPlayer(PlayerName,1);
				sender.sendMessage(ChatColor.DARK_PURPLE + "Der Spieler "+PlayerName+" wurde gefunden? "+Boolean.toString(PlayerDeathCount));
				return true;
			}else{
				sender.sendMessage(ChatColor.RED + "Zuviele oder zuwenige Argumente! FindPlayer");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("blockstat")){
			if (args.length == 1){
				String PlayerName = sender.getName();
				String BlockName = args[0];
				Integer BlockCount = database.GetPlayerBlockBreak(PlayerName, BlockName);
				sender.sendMessage(ChatColor.DARK_PURPLE + "Du hast "+BlockCount+" "+BlockName+" abgebaut");
				return true;
				}else{
					sender.sendMessage(ChatColor.RED + "Zuviele oder zuwenige Argumente!");
			}
		}
		if(label.equalsIgnoreCase("addcolumn")){
			if(sender instanceof Player){
				sender.sendMessage("This command is not available for Players");
				return true;
			}else{			
				if(args.length == 3){
					String TableName = args[0];
					String ColumnName = args[1];
					String ColumnType2 = args[2];
					database.AddCloumn(TableName, ColumnName, ColumnType2);
					System.out.println("Column: "+ColumnName+" add in the Table "+TableName+" as Type "+ColumnType2+" Sucessfully");
					return true;
				}else{
					System.out.println("Not enought Args");
					return true;
				}
			}
		}
		if(label.equalsIgnoreCase("renamedbtable")){
			if(sender instanceof Player){
				sender.sendMessage("This command is not available for Players");
				return true;
			}else{			
				if(args.length == 2){
					String OldTableName = args[0];
					String NewTableName = args[1];
					database.RenameTable(OldTableName,NewTableName);
					System.out.println("The Database table "+OldTableName+" was successfully renamed in "+NewTableName);
					return true;
				}else{
					System.out.println("Not enought Args");
					return true;
				}
			}
		}
		
		
		
		if(label.equalsIgnoreCase("teleport")){			
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(args.length == 1){
					String Name = args[0];
					if(Bukkit.getPlayer(Name) != null){
						Player target = (Player)Bukkit.getPlayer(Name);
						player.teleport(target);
						player.sendMessage(ChatColor.GREEN + "Erfolgreich zu " + target.getDisplayName() + " teleportiert!");
						return true;
					}else{
						player.sendMessage(ChatColor.RED + "Der Spieler existiert nicht!");
						return true;
					}
							
					
				}else{
					player.sendMessage(ChatColor.RED + "Zuviele oder zuwenige Argumente! Teleport");
					return true;
					
				}
			}
		}
		
		if(label.equalsIgnoreCase("redling")){
			DecimalFormat df = new DecimalFormat("##.##");
			Float Value = null;
			Float PlayerTo = null;
			Float PlayerFrom = null;
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage(ChatColor.YELLOW+"Um anzeigen zu lassen, wieviel Geld du noch hast Verwende /redling balance");
						sender.sendMessage(ChatColor.YELLOW+"Um Geld zu einem anderen Spieler zu senden, Verwende /redling send ZielSpieler Betrag");
						sender.sendMessage(ChatColor.YELLOW+"Um dir die Hilfe anzeigen zu lassen, verwende /redling help");
						return true;
					}else if(args[0].equalsIgnoreCase("balance")){
						Value = database.GetValue("player_list", player.getDisplayName(), "Redling");
						sender.sendMessage(ChatColor.GREEN+"Dein Kontostand betr�gt aktuell: "+df.format(Value).toString()+" Redlings");
						return true;
					}
				}else if(args.length == 3){
					if(args[0].equalsIgnoreCase("send")&&(!args[1].isEmpty()||(!args[2].isEmpty()))){
						PlayerFrom = database.GetValue("player_list", player.getDisplayName(), "Redling");
						PlayerTo = database.GetValue("player_list", args[1], "Redling");
						Value = Float.parseFloat(args[2]);
						if( PlayerFrom >= Value) {
							
							PlayerTo = PlayerTo + Value; //Der Spieler zu dem das Geld gesendet wird
							PlayerFrom = PlayerFrom - Value; //Der Spieler von dem das Geld kommt
							
							database.UpdatePlayerCurrency(player.getDisplayName(), "Redling", PlayerFrom);
							database.UpdatePlayerCurrency(args[1], "Redling", PlayerTo);
							
							player.sendMessage(ChatColor.GREEN+"Du hast "+args[1]+" "+df.format(Float.parseFloat(args[2]))+" Redlings gesendet");
							player.sendMessage(ChatColor.GREEN+"Dein neuer Kontostand betr�gt nun "+df.format(PlayerFrom)+" Redlings");
							Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN+"Du hast von "+player.getDisplayName()+
							" "+df.format(Float.parseFloat(args[2]))+ " Redlings erhalten");
							Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN+"Dein Neuer Kontostand betr�gt nun "+df.format(PlayerTo)+" Redlings");
							return true;
						}else if(PlayerFrom < Value){
							player.sendMessage(ChatColor.DARK_RED+"Leider reicht dein Guthaben nicht aus, um die Transaktion durchzuf�hren :(");
							return true;
						}
					}
				}else if(args.length == 0){
					player.sendMessage(ChatColor.DARK_PURPLE + "Verwende "+ChatColor.YELLOW+"/redling help"+ChatColor.DARK_PURPLE+" um Hilfe zu erhalten");
					return true;
					
				}
			}else{
				System.out.println("Kann nicht von der Console aus aufgerufen werden");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("xearling")){
			DecimalFormat df = new DecimalFormat("##");
			df.setRoundingMode(RoundingMode.DOWN);
			Float Value = null;
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						sender.sendMessage(ChatColor.YELLOW+"Um dir die Hilfe anzeigen zu lassen, verwende /xearling help");
						sender.sendMessage(ChatColor.YELLOW+"Um anzeigen zu lassen, wieviel Geld du noch hast Verwende /xearling balance");
						return true;
					}else if(args[0].equalsIgnoreCase("balance")){
						Value = database.GetValue("player_list", player.getDisplayName(), "Xearling");
						sender.sendMessage(ChatColor.GREEN+"Dein Kontostand betr�gt aktuell: "+df.format(Value)+" Xearling");
						return true;
					}
				}else if(args.length == 0){
					player.sendMessage(ChatColor.DARK_PURPLE + "Verwende "+ChatColor.YELLOW+"/xearling help"+ChatColor.DARK_PURPLE+" um Hilfe zu erhalten");
					return true;					
				}
			}else{
				System.out.println("Kann nicht von der Console aus aufgerufen werden");
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("buy")){
			if(sender instanceof Player){
				Player player = (Player)sender;
				if(args.length == 1){
					if(args[0].equalsIgnoreCase("help")){
						player.sendMessage("Mit dem Command /buy kannst du dir Items und Kommandos kaufen");
						player.sendMessage("Bezahlen tust du mit Xearlings, die Premium W�hrung im Spiel");
						player.sendMessage("Um dir ein Item oder Kommando zu kaufen, kannst du /buy list aufrufen");
						player.sendMessage("Dann bekommst du eine Liste der Aktuellen Items aufgelistet");
						player.sendMessage("Mit /buy ItemName Menge kannst du dir entsprechend die Sachen kaufen");
						player.sendMessage("Xearlings kannst du dir im �brigen erarbeiten oder kaufen");
						player.sendMessage("F�r weitere Infos komm besuch unsere Webseite www.nichtvorhanden.de");
					}else if(args[0].equalsIgnoreCase("list")){
						player.sendMessage("Die Aktuellen Items und Kommandos");
						player.sendMessage("Menge --- Name --- Preis");
						player.sendMessage("1 x Stack Eichenholz :    1 Xearling");
						player.sendMessage("1 x Stack Stein         :    2 Xearlings");
						player.sendMessage("1 x Stack Redstone   :    4 Xearlings");
						player.sendMessage("1 x Stack Diamanten   :   10 Xearlings");
						player.sendMessage("1 x Stack Smaragde   :   20 Xearlings");
						player.sendMessage("1 x Teleport to xyz   :   50 Xearlings");
						player.sendMessage("1 x Voll Heilen          :   10 Xearlings");
						player.sendMessage("1 x Kill Kommando       : 1000 Xearlings");						
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("buy")){
						
					}
				}
			}
		}
		
		
		return false;		
	}
}
