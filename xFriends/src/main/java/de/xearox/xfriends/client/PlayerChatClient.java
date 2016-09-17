package de.xearox.xfriends.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import de.xearox.xfriends.XFriends;
import net.md_5.bungee.api.ChatColor;

public class PlayerChatClient {
	
	private XFriends plugin;
	
	private String playerName;
	private Player player;
	
	private BufferedReader chatIn;
	private PrintWriter chatOut;
	
	public Socket socket;
	
	public PlayerChatClient(XFriends plugin, Player player) {
		this.player = player;
		this.plugin = plugin;
		this.playerName = this.player.getName();
		if(XFriends.databaseClientRunning && XFriends.chatClientRunning){
			try {
				this.initSocket();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				System.out.println("[XFRIENDS] Server or Port wrong!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("[XFRIENDS] IO Exception. Line 39 in PlayerChatClient!");
				e.printStackTrace();
			}
		}
		XFriends.bukkitTaskMap.put(this.player, plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new StartChatClient()));
	}
	
	public void sendMessageToMasterChatServer(String message){
		if(chatIn == null || chatOut == null){
			return;
		}
		chatOut.println(message);
	}
	
	private void initSocket() throws UnknownHostException, IOException{
		this.socket = new Socket(XFriends.serverAddress, XFriends.serverChatPort);
	}
	
	public void runChatClient() throws IOException, NullPointerException {
		chatIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        chatOut = new PrintWriter(this.socket.getOutputStream(), true);
        
        while (true) {
            String line = chatIn.readLine();
            if (line.startsWith("SUBMITNAME")) {
                chatOut.println("MCClient"+this.playerName);
            } else if (line.startsWith("NAMEACCEPTED")) {
                System.out.println("[XFRIENDS] The Player "+this.playerName+" opened successful a connection to the Master Chat Server");
            } else if (line.startsWith("MESSAGE")) {
                //messageArea.append(line.substring(8) + "\n");
                String message = line.substring(8);
                
            } else if (line.startsWith("KEEPALIVE")){
            	if(XFriends.DEBUG){
            		System.out.println("Get keepalive packet");
            	}
            	sendMessageToMasterChatServer("KEEPALIVE MCClient"+this.playerName);
            } else if (line.startsWith("RECEIVER")){
            	try{
            		String to = line.substring(line.indexOf("TO:")+3, line.indexOf("SENDER:")-1);
            		String sender = line.substring(line.indexOf("SENDER:")+7, line.indexOf("MESSAGE:")-1).replace("PCClient", "").replace("MCClient", "");
                	sender = sender.replace(" ", "");
                	String message = line.substring(line.indexOf("MESSAGE:")+8, line.length());
                	
                	Player player = this.player;
                	if(player == null){
                		System.out.println("player == null");
                	}
                	ArrayList<String> lastMessages = new ArrayList<String>();
                	String playerMessage = ChatColor.DARK_PURPLE+"["+ChatColor.YELLOW+sender+ChatColor.DARK_PURPLE+">"+ChatColor.YELLOW+to+ChatColor.DARK_PURPLE+"]"
                        	+ChatColor.DARK_AQUA+message;
                	if(XFriends.lastMessages.containsKey(player)){
                		System.out.println("[XFRIENDS] last Message = "+lastMessages.get(lastMessages.size()-1));
            			System.out.println("[XFRIENDS] new message = "+playerMessage);
                		lastMessages = XFriends.lastMessages.get(playerMessage);
                		if(!lastMessages.get(lastMessages.size()-1).equalsIgnoreCase(playerMessage)){
                			player.sendMessage(playerMessage);
                			lastMessages.add(playerMessage);
                			XFriends.lastMessages.replace(player, lastMessages);
                		}
                	} else {
                		System.out.println("#####");
                		System.out.println("[XFRIENDS]"+XFriends.lastMessages.containsKey(player));
                		System.out.println("#####");
                		player.sendMessage(playerMessage);
            			lastMessages.add(playerMessage);
            			XFriends.lastMessages.put(player, lastMessages);
                	}
                	
            	} catch (Exception e){
            		e.printStackTrace();
            	}
            }
        }
        
        
	}
	
	private class StartChatClient implements Runnable{

		@Override
		public void run() {
			if(socket != null && socket.isConnected()){
				try {
					runChatClient();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					System.out.println("[XFRIENDS] NullPointerException");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("[XFRIENDS] Socket Closed for player "+player.getName());
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
