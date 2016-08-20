package de.xearox.xfriends.server;

import de.xearox.xfriends.utility.LogLevel;
import de.xearox.xfriends.utility.MyLogger;

public class XFriendsServer {
	
	private MyLogger myLogger;
	
	public MyLogger getMyLogger(){
		return myLogger;
	}
	
	public void init(){
		myLogger = new MyLogger(this);
	}
	
	public static void main(String[] args) {
		XFriendsServer main = new XFriendsServer();
		main.init();
//		main.myLogger.createLogFile(LogLevel.INFO, "Start");
		
		
		
	}

}