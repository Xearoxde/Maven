package de.xearox.xfriends.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import de.xearox.xfriends.XFriends;

public class DatabaseClient {
	
	private XFriends plugin;
	
	private static String serverAddress = XFriends.serverAddress;
	private static int serverPort = XFriends.serverDBPort;
	
	public DatabaseClient(XFriends plugin) {
		this.plugin = plugin;
	}
	
	public void sendToServer(String command, String commandLine, byte[] bytes){
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Socket server = null;
				try {
					server = new Socket(serverAddress, serverPort);
					Scanner in = new Scanner(server.getInputStream());
					PrintWriter out = new PrintWriter(server.getOutputStream(), true);
					String inputString = "";
					
					out.println(command);
					out.println(commandLine);
					
					byte[] myByteArray = bytes;
					
					out.println(myByteArray.length);
					
					for(byte myByte : myByteArray){
						out.println(myByte);
					}
					
					if(in.hasNextLine()){
						inputString = in.nextLine();
					}
					
					if(inputString.contains("User updated")){	}
					
					
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (server != null)
						try {
							server.close();
						} catch (IOException e) {
						}
				}
			}
		});
		
	}
	public String getPlayerUUID(String playerName){
		Socket server = null;
		String UUID = "";
		try {
			server = new Socket(serverAddress, serverPort);
			Scanner in = new Scanner(server.getInputStream());
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			
			out.println("GETPLAYERUUID");
			out.println("nix");
			out.println(playerName);
			
			UUID = in.nextLine();
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
				}
		}
		return UUID;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
