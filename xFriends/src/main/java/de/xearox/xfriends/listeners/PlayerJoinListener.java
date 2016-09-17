package de.xearox.xfriends.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import de.xearox.myclasses.MyPlayerObject;
import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.client.ChatClient;
import de.xearox.xfriends.client.DatabaseClient;
import de.xearox.xfriends.client.PlayerChatClient;
import de.xearox.xfriends.utility.MyLogger;
import de.xearox.xfriends.utility.Utility;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoinListener implements Listener{
	
	private XFriends plugin;
	private DatabaseClient myClient;
	private Utility utility;
	private MyLogger myLogger;
	private TCompos tCompos;
	private BukkitTask bukkitTask;
	
	public PlayerJoinListener(XFriends plugin) {
		this.plugin = plugin;
		this.myClient = plugin.getDatabaseClient();
		this.utility = plugin.getUtility();
		this.myLogger = plugin.getMyLogger();
	}
	
	@EventHandler
	public void OnPlayerJoinEvent(PlayerJoinEvent event){
		Player player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		if(!XFriends.onlineMode){
			addPlayerToUUIDList(player);
		}
		MyPlayerObject myPlayerObject = new MyPlayerObject();
		
		myPlayerObject.playerName = player.getName();
		if(XFriends.onlineMode){
			//
		} else {
			uuid = utility.getYamlUUIDList().getString(uuid+".onlineUUID");
		}
		final String checkUUIDString = uuid;
		File configFile = new File(plugin.getDataFolder()+File.separator+"/config/config.yml");
		YamlConfiguration yamlConfigFile = YamlConfiguration.loadConfiguration(configFile);
		
		plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
				String serverResponse = myClient.checkIfUserExistsInDB(checkUUIDString);
				if(serverResponse.equalsIgnoreCase("USERNOTEXISTS")){
					tCompos = new TCompos();
					tCompos.xFriendsMessage.setBold(yamlConfigFile.getBoolean("Message.notRegisteredMessage.Bold"));
					tCompos.xFriendsMessage.setItalic(yamlConfigFile.getBoolean("Message.notRegisteredMessage.Italic"));
					tCompos.xFriendsMessage.setUnderlined(yamlConfigFile.getBoolean("Message.notRegisteredMessage.Underline"));
					tCompos.xFriendsMessage.setColor(ChatColor.valueOf(yamlConfigFile.getString("Message.notRegisteredMessage.Color")));
					tCompos.xFriendsMessage.setText(yamlConfigFile.getString("Message.notRegisteredMessage.Msg"));
					tCompos.xFriendsMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
							new ComponentBuilder(yamlConfigFile.getString("Message.notRegisteredMessage.HoverText")).create()));
					
					tCompos.xFriendsMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, yamlConfigFile.getString("Message.notRegisteredMessage.URL")));
					
					player.sendMessage(ChatColor.DARK_RED+"Hello "+ChatColor.YELLOW+player.getName()+ChatColor.DARK_RED+" it seems to be that you are not registered in the xFriends plugin");
					player.sendMessage(ChatColor.DARK_RED+"To use this plugin, you need to run the command"+ChatColor.YELLOW+" /friends register YourPassword YourEmailAddress");
					player.sendMessage(ChatColor.DARK_RED+"For more information about this plugin, please visit our website.");
					player.spigot().sendMessage(tCompos.xFriendsMessage);
				}
			}
		}, 10L*20L, 10L*60L*20L);
		bukkitTask = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
					String serverResponse = myClient.checkIfUserExistsInDB(checkUUIDString);
					if(serverResponse.equalsIgnoreCase("USEREXISTS")){
						myPlayerObject.UUID = checkUUIDString;
						myPlayerObject.IP = player.getAddress().getHostName();
						myPlayerObject.ServerName = plugin.getServer().getServerName();
						myClient.sendToServer("updateuser", "LoggedIn", utility.getBytesFromObject(myPlayerObject));
						XFriends.chatClientMap.put(player, new PlayerChatClient(plugin, player));
						player.sendMessage(ChatColor.AQUA+"Your are now online in the xFriends Network!");
						player.sendMessage(ChatColor.AQUA+"Use "+ChatColor.YELLOW+"/friends send UserName Message "+ChatColor.AQUA+"To send messages to someone!");
						bukkitTask.cancel();
					}
				}
		}, 20L, 5L*20L);
	}
	
	public void addPlayerToUUIDList(Player player){
		YamlConfiguration yamlFile = utility.getYamlUUIDList();
		String offlineUUID = player.getUniqueId().toString();
		String onlineUUID = "";
		String playerName = player.getName();
		
		if(!yamlFile.contains(offlineUUID)){
			try {
				onlineUUID = utility.getUUIDFromMojang(playerName);
				yamlFile.addDefault(offlineUUID+".playername", playerName);
				yamlFile.addDefault(offlineUUID+".onlineUUID", onlineUUID);
				yamlFile.options().copyDefaults(true);
				utility.saveYamlUUIDList(yamlFile);
				myLogger.setInfoMessage("The user "+playerName+" with the offline UUID "+offlineUUID+" was added to the UUID list. The online UUID is : "+onlineUUID);
			} catch (IOException e) {
				e.printStackTrace();
				myLogger.setErrMessage("IOException in addPlayerToUUIDList");
			} catch (IllegalArgumentException e){
				e.printStackTrace();
				myLogger.setErrMessage("IllegalArgumentException in addPlayerToUUIDList");
			}
		}
	}
	
	class TCompos{
		TextComponent xFriendsMessage = new TextComponent();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
