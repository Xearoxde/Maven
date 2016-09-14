package de.xearox.xfriends.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.utility.Utility;
import net.md_5.bungee.api.ChatColor;

public class ChatClient {
	
	private XFriends plugin;
	private DatabaseClient databaseClient;
	private Utility utility;
	
    static BufferedReader chatIn;
    static PrintWriter chatOut;
    
    public ChatClient(XFriends plugin) {
    	this.plugin = plugin;
    	this.databaseClient = plugin.getDatabaseClient();
    	this.utility = plugin.getUtility();
	}
    	
    public static void sendMessageToMasterChatServer(String message){
		if(chatIn == null || chatOut == null){
			return;
		}
		chatOut.println(message);
	}
    
	public void run() throws IOException, NullPointerException {

        
        chatIn = new BufferedReader(new InputStreamReader(plugin.getSocket().getInputStream()));
        chatOut = new PrintWriter(plugin.getSocket().getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = chatIn.readLine();
            if (line.startsWith("SUBMITNAME")) {
                chatOut.println(plugin.getServer().getServerName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                System.out.println("This server was successfully registered on the Master Chat Server!");
            } else if (line.startsWith("MESSAGE")) {
                //messageArea.append(line.substring(8) + "\n");
                String message = line.substring(8);
                
            } else if (line.startsWith("KEEPALIVE")){
            	if(XFriends.DEBUG){
            		System.out.println("Get keepalive packet");
            	}
            	sendMessageToMasterChatServer("KEEPALIVE "+plugin.getServer().getServerName());
            } else if (line.startsWith("RECEIVER")){
            	try{
            		String to = line.substring(line.indexOf("TO:")+3, line.indexOf("SENDER:")-1);
            		String sender = line.substring(line.indexOf("SENDER:")+7, line.indexOf("MESSAGE:")-1).replace("PCClient", "").replace("MCClient", "");
                	sender = sender.replace(" ", "");
                	String message = line.substring(line.indexOf("MESSAGE:")+8, line.length());
                	YamlConfiguration yamlFile = utility.getYamlUUIDList();
                	String toUUID = databaseClient.getPlayerUUID(to);
                	UUID uuid = null;
                	
                	Set<String> setString = yamlFile.getKeys(true);
                	if(!XFriends.onlineMode){
                		for(String string : setString){
                    		if(yamlFile.getString(string).contains(toUUID)){
                    			uuid = UUID.fromString(string.substring(0 , string.indexOf(".")));
                    		}
                    	}
                	} else {
                		uuid = UUID.fromString(databaseClient.getPlayerUUID(to));
                	}
                	
                	
                	OfflinePlayer offPlayer = plugin.getServer().getOfflinePlayer(uuid);
                	Player player = offPlayer.getPlayer();
                	if(player == null){
                		System.out.println("player == null");
                	}
                	player.sendMessage(ChatColor.DARK_PURPLE+"["+ChatColor.YELLOW+sender+ChatColor.DARK_PURPLE+">"+ChatColor.YELLOW+to+ChatColor.DARK_PURPLE+"]"
                	+ChatColor.DARK_AQUA+message);
                	
            	} catch (Exception e){
            		e.printStackTrace();
            	}
            }
        }
    }
}
