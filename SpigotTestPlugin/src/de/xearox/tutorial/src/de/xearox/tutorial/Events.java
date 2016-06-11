package de.xearox.tutorial;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Events implements Listener {	
	Database database = new Database(null);
	FunctionLib functionlib = new FunctionLib();
	LocalizeDE loalizeDE = new LocalizeDE();
	public static Plugin plugin;
	
	public Events(Plugin plugin){
		Events.plugin = plugin;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		
		
		
		Player player = (Player)event.getPlayer();
		event.setJoinMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.GRAY + "] " + ChatColor.GOLD + player.getDisplayName());
		System.out.println("Joined Player Name is: "+player.getDisplayName());
		Float Value = database.GetValue("player_list", player.getDisplayName(), "Redling");
		player.sendMessage("Hallo "+player.getDisplayName()+" Willkommen zur�ck auf Unserem Server");
		player.sendMessage("Um auf Unseren TeamSpeak Server zu kommen, verwende "+ChatColor.DARK_RED+"ts.r3dbl4ck.de");
		player.sendMessage("Wir w�nschen dir noch viel Spa� auf Unserem Server");
		player.sendMessage("----------------------------------------------");
		player.sendMessage(ChatColor.GREEN+"Dein Kontostand betr�gt aktuell: "+Value.toString()+" Redlings");
		try {			
			database.AddNewPlayer(player.getDisplayName());	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		Player player = (Player)event.getPlayer();
		event.setQuitMessage(ChatColor.GRAY + "[" + ChatColor.RED + "-" + ChatColor.GRAY + "] " + ChatColor.GOLD + player.getDisplayName());
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = (Player)event.getPlayer();
		String Msg = event.getMessage();
		
		event.setFormat(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " >> " + ChatColor.DARK_GREEN + Msg);		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) throws InstantiationException, IllegalAccessException{
		String playerstr = (String)event.getEntity().getDisplayName();
		@SuppressWarnings("unused")
		Player player = (Player)event.getEntity();
		event.setDeathMessage(ChatColor.DARK_RED + "Der Spieler " + 
				ChatColor.DARK_GREEN + playerstr + ChatColor.DARK_RED +  " ist leider verstorben! Sein Level war: " + 
				ChatColor.GOLD + event.getEntity().getLevel());	
		try {
			
			database.UpdatePlayerDeath(playerstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onPlayerBreakBlock(BlockBreakEvent event){
		final Block block = event.getBlock();
		final Player player = event.getPlayer();
		database.UpdatePlayerBlockBreak(player.getDisplayName(), block.getType().toString());
		
		if((event.getBlock().getType() == Material.getMaterial("SIGN_POST")) | (event.getBlock().getType() == Material.getMaterial("WALL_SIGN"))){
			try{
				String UUID				= Bukkit.getPlayer(player.getDisplayName()).getUniqueId().toString();
				String BlockPosWorld 	= event.getBlock().getWorld().toString();
				String BlockXPos 		= Integer.toString(event.getBlock().getX());
				String BlockYPos 		= Integer.toString(event.getBlock().getY());
				String BlockZPos 		= Integer.toString(event.getBlock().getZ());
				String TempString		= BlockPosWorld+BlockXPos+BlockYPos+BlockZPos;
				String MD5Hash			= null;
				
				MD5Hash = functionlib.MD5Hashing(TempString);
			
				database.RemoveFromTable(plugin.getConfig().getString("Database.TableName.PlayerSignTrade.Name"), "UniqueID", MD5Hash);
				
				event.getPlayer().sendMessage("The sign was successfully deleted");
			
			
			
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}
	
	@EventHandler
	public void onPlayerPickUp(PlayerPickupItemEvent event){
		
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event){
		try {			
			String MD5Hash			= null;
			String BlockPosWorld 	= null;
			String BlockXPos 		= null;
			String BlockYPos 		= null;
			String BlockZPos 		= null;
			String ItemToSell 		= null;
			String ItemValue 		= null;
			String PlayerName 		= null;
			String SignTradeType	= null;
			String TempString		= null;
			String UUID				= null;
		
			if(event.getLine(0).contains("[SELL]")){
				if(!event.getLine(1).isEmpty()){
					if(!event.getLine(2).isEmpty()){				
						BlockPosWorld 	= event.getBlock().getWorld().toString();
						BlockXPos 		= Integer.toString(event.getBlock().getX());
						BlockYPos 		= Integer.toString(event.getBlock().getY());
						BlockZPos 		= Integer.toString(event.getBlock().getZ());
						SignTradeType	= event.getLine(0).toString();
						ItemToSell 		= event.getLine(1).toString();
						ItemValue 		= event.getLine(2).toString();
						PlayerName 		= event.getPlayer().getDisplayName();
						
						UUID			= Bukkit.getPlayer(PlayerName).getUniqueId().toString();
						
						TempString		= BlockPosWorld+BlockXPos+BlockYPos+BlockZPos;
						
						MD5Hash = functionlib.MD5Hashing(TempString);
						
						database.InsertSignsToTable(PlayerName,BlockPosWorld,BlockXPos,BlockYPos,BlockZPos,SignTradeType,
													ItemToSell,ItemValue,MD5Hash);
						
						event.setLine(3, ChatColor.GREEN+"ACTIVE");
						event.getPlayer().sendMessage("The sign is now enabled");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event){
	  Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		
		Player player			= event.getPlayer();
				//Wieviel Geld der K�ufer der Items hat, also der Schild Inhaber
		try{			
			//event.getPlayer().sendMessage(MD5Hash);
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
				String UUID				= player.getUniqueId().toString();
				String BlockPosWorld 	= event.getClickedBlock().getWorld().toString();
				String BlockXPos 		= Integer.toString(event.getClickedBlock().getX());
				String BlockYPos 		= Integer.toString(event.getClickedBlock().getY());
				String BlockZPos 		= Integer.toString(event.getClickedBlock().getZ());
				String TempString		= BlockPosWorld+BlockXPos+BlockYPos+BlockZPos;
				String MD5Hash			= null;
				int AmountToSell		= 0;
				int TotalSum			= 0;
				Float Price				= null;
				Float TotalPrice		= null;
				String SignOwner			= null;
				Float SellerBalance		= null;		//Wieviel Geld der Verk�ufer der Items hat, also der an das Schild verkauft
				Float SignOwnerBalance	= null;
				int TotalAmount			= 0;
				String Buyer			= event.getPlayer().getDisplayName();
				String ItemName			= null;
				MD5Hash = functionlib.MD5Hashing(TempString);
				SignOwner				= database.GetSign(MD5Hash, "PlayerName");
				if((event.getClickedBlock().getType() == Material.getMaterial("SIGN_POST")) | (event.getClickedBlock().
				getType() == Material.getMaterial("WALL_SIGN"))){
					if(database.GetSign(MD5Hash, "UniqueId").equalsIgnoreCase(MD5Hash)){
						if(database.GetSign(MD5Hash, "SignTradeType").equalsIgnoreCase("[SELL]")){
							if(!SignOwner.equals(event.getPlayer().getDisplayName())){
							//Abfrage ob es ein Verkaufs Schild ist
							String TempString2 = database.GetSign(MD5Hash, "ItemToSell");
							if(functionlib.GetItemInPlayerHand(player).equalsIgnoreCase(TempString2)){
								ItemName = TempString2;
							}else if(functionlib.GetItemInPlayerHand(player).equalsIgnoreCase(loalizeDE.TranslateToGerman(TempString2.toLowerCase()))){
								ItemName = loalizeDE.TranslateToGerman(TempString2.toLowerCase());
							}
							if(functionlib.GetItemInPlayerHand(player).equalsIgnoreCase(ItemName)){
							
								AmountToSell 		= player.getItemInHand().getAmount();
								Price 				= Float.parseFloat(database.GetSign(MD5Hash, "ItemValue"));
								TotalPrice			= Price * AmountToSell;
								SignOwnerBalance	= database.GetValue("player_list", SignOwner, "Redling");
								SellerBalance		= database.GetValue("player_list", player.getDisplayName(), "Redling");								
								TotalAmount     	= 0;
								
								TotalAmount = (int)(database.GetValue(plugin.getConfig().getString("Database.TableName.PlayerTrading.Name"),
											SignOwner, ItemName));
								
								if(SignOwnerBalance >= TotalPrice){									
									
									
									SignOwnerBalance = SignOwnerBalance - TotalPrice;
									SellerBalance = SellerBalance + TotalPrice;
									TotalSum = TotalAmount + AmountToSell;
									if(!SignOwner.equals(event.getPlayer().getDisplayName())){
										System.out.println("Geld des Verk�ufers "+SellerBalance);
									
										database.UpdatePlayerCurrency(player.getDisplayName(), "Redling", SellerBalance );
										System.out.println("Geld des Sign Owners "+SignOwnerBalance);
										database.UpdatePlayerCurrency(SignOwner, "Redling", SignOwnerBalance );
										player.sendMessage(ChatColor.DARK_PURPLE+"Du hast grade "+ChatColor.GREEN+TotalPrice.toString() 
										+ChatColor.DARK_PURPLE+ " Redlings, durch den Verkauf verdient!");
									
										database.UpdateTradingTable(SignOwner, TotalSum, functionlib.GetItemInPlayerHand(player).toLowerCase());
										player.getInventory().removeItem(player.getInventory().getItemInHand());
									}else{
										player.getInventory().removeItem(player.getInventory().getItemInHand());
									}
								}
								
							}
						}else event.getPlayer().sendMessage("Du kannst hier nicht Verkaufen!");	
						}else if(database.GetSign(MD5Hash, "SignTradeType").equalsIgnoreCase("[BUY]")){
						//Abfrage ob es ein Kauf Schild ist
							
							
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
		});	
		
	}
	@EventHandler
	public void onPlayerKillEnemy(final EntityDeathEvent event){
	Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		
	
		Entity killer;
		killer = event.getEntity().getKiller();
		Float randomint = null;
		Float currency = null;
		
		if(killer!=null && killer instanceof Player){
			Random rand = new Random();
			randomint = rand.nextFloat()*(10.00f - 1.00f) + 1.00f;
			randomint = randomint *100;
			randomint = (float) Math.round(randomint);
			randomint = randomint / 100;
			killer.sendMessage(ChatColor.DARK_AQUA + "Du hast " + Float.toString(randomint)+ " Redlings erhalten!");
			currency = database.GetValue("player_list", killer.getName().toString(), "Redling");
			currency = currency + randomint;
			database.UpdatePlayerCurrency(killer.getName().toString(), "Redling", currency);
		}
		}
	});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
