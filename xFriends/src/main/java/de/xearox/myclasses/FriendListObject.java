package de.xearox.myclasses;

import java.io.Serializable;
import java.util.HashMap;

public class FriendListObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<Integer, FriendListEntries> friendListEntriesMap;
	
	
	public class FriendListEntries implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String UUID;
		public String playerName;
		public String nickname;
		public String friendGroup;
		public boolean blocked;
		public boolean OnlineStatus;
	}

}
