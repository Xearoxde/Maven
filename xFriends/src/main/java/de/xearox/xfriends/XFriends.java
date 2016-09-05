package de.xearox.xfriends;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TimerTask;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import de.xearox.myclasses.ServerObject;
import de.xearox.xfriends.client.ChatClient;
import de.xearox.xfriends.client.DatabaseClient;
import de.xearox.xfriends.listeners.MyExecutor;
import de.xearox.xfriends.listeners.PlayerJoinListener;
import de.xearox.xfriends.listeners.PlayerQuitListener;
import de.xearox.xfriends.utility.CreateConfig;
import de.xearox.xfriends.utility.MyLogger;
import de.xearox.xfriends.utility.Utility;

public class XFriends extends JavaPlugin{
	
	private MyLogger myLogger;
	private Utility utility;
	private CreateConfig createConfig;
	private MyExecutor myExecutor;
	private DatabaseClient myClient;
	private ChatClient chatClient;
	private static Socket socket;
	public final static boolean DEBUG = false;
	public final static String serverAddress = "94.114.6.167";//94.114.6.167
	public final static int serverDBPort = 3141;
	public final static int serverChatPort = 3142;
	private static boolean chatServerOnline = false;
	private static boolean databaseServerOnline = false;
	public static boolean chatClientRunning = false;
	public static boolean databaseClientRunning = false;
	private static BukkitTask startChatClientsTask;
	private static int databaseServerCheckTries = 0;
	private static int masterChatServerCheckTries = 0;
	
	public MyLogger getMyLogger(){
		return myLogger;
	}
	
	public Utility getUtility(){
		return utility;
	}
	
	public DatabaseClient getDatabaseClient(){
		return myClient;
	}
	
	public ChatClient getChatClient(){
		return chatClient;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public void registerServer() throws UnknownHostException{
		ServerObject serverObject = new ServerObject();
		serverObject.serverName = getServer().getServerName();
		serverObject.IP = utility.getExternalIP();
		serverObject.MOTD = getServer().getMotd();
		myClient.sendToServer("addserver", "nix", utility.getBytesFromObject(serverObject));
	}
	
	public static boolean hostAvailabilityCheck(String SERVER_ADDRESS, int TCP_SERVER_PORT) {
		if(DEBUG){
			if(TCP_SERVER_PORT == 3141){
				System.out.println("Hostcheck for Database Server");
			}
			if(TCP_SERVER_PORT == 3142){
				System.out.println("Hostcheck for Master Chat Server");
			}
		}
	    try (Socket s = new Socket(SERVER_ADDRESS, TCP_SERVER_PORT)) {
	        return true;
	    } catch (IOException ex) {
	        /* ignore */
	    }
	    return false;
	}
	
	public void registerListener(){
		PluginManager pluginManager = this.getServer().getPluginManager();
		//listens for the PlayerJoinListener
		pluginManager.registerEvents(new PlayerJoinListener(this), this);
		pluginManager.registerEvents(new PlayerQuitListener(this), this);
	}
	
	public void createCommands(){
		myExecutor = new MyExecutor(this);
		getCommand("friends").setExecutor(myExecutor);
		//getCommand("test").setExecutor(myExecutor);
		//getCommand("daily createRewards").setExecutor(myExecutor);
	}
	
	public void startChatClient(){
		this.getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
			
			@Override
			public void run() {
				try {
					chatClient.run();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("The Master Chat Server is not Running. Try again in 60 seconds again! Tries = "+masterChatServerCheckTries);
				} catch (NullPointerException e){
					System.out.println("The Master Chat Server is not Running. Try again in 60 seconds again! Tries = "+masterChatServerCheckTries);
				}
			}
		});
	}
	
	@Override
	public void onLoad(){
		this.myLogger = new MyLogger(this);
	}
	
	@Override
	public void onEnable(){
		if(!DEBUG){
			if(!this.getServer().getOnlineMode()){
				this.getPluginLoader().disablePlugin(this);
				return;
			}
		}
		
		this.utility = new Utility(this);
		this.createConfig = new CreateConfig(this);
		
		//this.createConfig.createConfig();
		this.myClient = new DatabaseClient(this);
		this.chatClient = new ChatClient(this);
		
		this.registerListener();
		
		this.createCommands();
		
		startTasks();
		
	}
	
	@Override
	public void onDisable(){
		if(XFriends.socket != null){
			try {
				XFriends.socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(startChatClientsTask != null){
			this.getServer().getScheduler().cancelTask(startChatClientsTask.getTaskId());
		}
	}
	
	public void startTasks(){
		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new HostCheckDatabaseServerTask(), 20, 1200);
		this.getServer().getScheduler().runTaskTimerAsynchronously(this, new HostCheckMasterChatServerTask(), 20, 1200);
		startChatClientsTask = this.getServer().getScheduler().runTaskTimerAsynchronously(this, new StartClients(), 60, 600);
	}
	
	private class StartClients implements Runnable{
		@Override
		public void run() {
			/*if(chatServerOnline && !chatClientRunning){
				
			}
			
			if(databaseServerOnline && !databaseClientRunning){
				try {
					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}*/
			if(databaseClientRunning && chatClientRunning){
				try {
					XFriends.socket = new Socket(serverAddress, serverChatPort);
					startChatClient();
					registerServer();
					System.out.println("All clients started");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getServer().getScheduler().cancelTask(startChatClientsTask.getTaskId());
			}
		}
    }
	
	private class HostCheckDatabaseServerTask implements Runnable{

		@Override
		public void run() {
			if(hostAvailabilityCheck(serverAddress, serverDBPort) && !databaseServerOnline){
				databaseServerOnline = true;
				if(databaseServerCheckTries > 0){
					databaseClientRunning = true;
					System.out.println("The Database Server now Online and connected to it!");
				} else if (databaseServerCheckTries == 0){
					System.out.println("Connected to Database Server!");
					databaseClientRunning = true;
				}
			} else if(!databaseServerOnline){
				databaseServerCheckTries++;
				System.out.println("The Database Server not Online. Try Again in 60 Seconds again! Tries = "+databaseServerCheckTries);
				databaseServerOnline = false;
			}
		}
		
	}
	
	private class HostCheckMasterChatServerTask implements Runnable{

		@Override
		public void run() {
			if(hostAvailabilityCheck(serverAddress, serverChatPort) && !chatServerOnline){
				chatServerOnline = true;
				if(masterChatServerCheckTries > 0){
					System.out.println("The Master Chat Server now Online and connected to it!");
					chatClientRunning = true;
				} else if (masterChatServerCheckTries == 0){
					System.out.println("Connected to The Master Chat Server!");
					chatClientRunning = true;
				}
			} else if(!chatServerOnline){
				masterChatServerCheckTries++;
				System.out.println("The Master Chat Server is not Running. Try again in 60 seconds again! Tries = "+masterChatServerCheckTries);
				chatClientRunning = false;
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
