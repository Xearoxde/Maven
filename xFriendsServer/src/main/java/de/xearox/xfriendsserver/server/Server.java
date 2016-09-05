package de.xearox.xfriendsserver.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import de.xearox.myclasses.MyPlayerObject;
import de.xearox.utility.LogLevel;
import de.xearox.utility.MyLogger;
import de.xearox.utility.Utility;

public class Server {
	
	public static MyLogger myLogger = new MyLogger();
	public static Utility utility = new Utility();
	public static String programPath = Server.class.getProtectionDomain().getCodeSource().getLocation().toString().replace(".jar", "");
	
	private static void firstServerStart(){
		System.out.println(programPath+File.separator);
		File dir = new File(programPath+File.separator+"/config/");
		if(!dir.exists()){
			dir.mkdirs();
			System.out.println(dir.getPath());
		}
		dir = new File(programPath+File.separator+"/logs/");
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		utility.copyFileFromJarToOutside("/config/config.conf", programPath+"/config/config.conf");
		System.exit(0);
	}
	
	@SuppressWarnings("resource")
	private static void handleConnection( Socket client ) throws IOException
	  {
	    Scanner     in  = new Scanner( client.getInputStream() );
	    PrintWriter out = new PrintWriter( client.getOutputStream(), true );
	    
	    MyPlayerObject myPlayerObject;
	    
	    String command = in.nextLine();
	    String commandLine = in.nextLine();
	    
	    ArrayList<Byte> myByteList = new ArrayList<Byte>();
	    
	    while(in.hasNextByte()){
	    	myByteList.add(in.nextByte());
	    }
	    
	    byte[] myByteArray = new byte[myByteList.size()];
	    
	    for(int i = 0; i < myByteList.size(); i++){
	    	myByteArray[i] = myByteList.get(i);
	    }
	    
	    myPlayerObject = GetMyPlayerObject(myByteArray);
	    
	    if(myPlayerObject != null){
	    	System.out.println(myPlayerObject.playerName);
	    	System.out.println(myPlayerObject.UUID);
	    	System.out.println(myPlayerObject.IP);
	    	System.out.println(myPlayerObject.ServerName);
	    }
	    
	    
	    if(command.equalsIgnoreCase("NEWUSER")){
	    	System.out.println(commandLine);
	    	out.println("User added");
	    } else if(command.equalsIgnoreCase("UPDATEUSER")){
	    	System.out.println(commandLine);
	    	out.println("User updated");
	    } else if(command.equalsIgnoreCase("REMOVEUSER")){
	    	System.out.println(commandLine);
	    	out.println("User removed");
	    } else {
	    	System.out.println("Nothing");
	    	out.println("Nothing done");
	    }
	    
	  }
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		firstServerStart();
		ServerSocket server = new ServerSocket(3141);
		System.out.println("Server started!");
		while (true) {
			Socket client = null;
			try {
				client = server.accept();
				handleConnection(client);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (client != null)
					try {
						client.close();
					} catch (IOException e) {
						myLogger.createLogFile(LogLevel.ERR, e.getMessage());
						myLogger.createLogFile(LogLevel.ERR, e.getMessage());
						myLogger.createLogFile(LogLevel.ERR, e.getMessage());
					}
			}
		}

	}
	
	public static MyPlayerObject GetMyPlayerObject(byte[] yourBytes){
		ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
		ObjectInput in = null;
		Object object = null;
		MyPlayerObject myPlayerObject;
		try {
		  in = new ObjectInputStream(bis);
		  object = in.readObject(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		  try {
		    bis.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		
		myPlayerObject = (MyPlayerObject) object;
		
		if(myPlayerObject != null){
			return myPlayerObject;
		} else {
			return null;
		}
	}

}
