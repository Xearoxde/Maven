package de.xearox.xfriends.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.myclasses.MyPlayerObject;
import de.xearox.myclasses.RegistrationFormObject;
import de.xearox.xfriends.XFriends;
import de.xearox.xfriends.client.ChatClient;
import de.xearox.xfriends.client.DatabaseClient;
import de.xearox.xfriends.utility.Utility;
import net.md_5.bungee.api.ChatColor;

public class MyExecutor implements CommandExecutor{
	
	private XFriends plugin;
	private Utility utility;
	private DatabaseClient myClient;
	private ChatClient myChatClient;
	
	public MyExecutor(XFriends plugin) {
		this.plugin = plugin;
		this.utility = plugin.getUtility();
		this.myClient = plugin.getDatabaseClient();
		this.myChatClient = plugin.getChatClient();
		
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("xfriends")|| label.equalsIgnoreCase("friends")){
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("register")){
					if(args.length == 3){
						if(!(sender instanceof Player)){
							return true;
						}
						Player player = (Player) sender;
						String UUID = player.getUniqueId().toString();
						String PlayerName = player.getDisplayName();
						String Password = args[1];
						String Email = args[2];
						String IP = player.getAddress().getAddress().toString();
						if(Password.contains("@")){
							player.sendMessage("Make sure that the password doesn't contains an @ !");
							return false;
						}
						if(!Email.contains("@")){
							player.sendMessage("Please enter an email adress!");
							return false;
						}
						RegistrationFormObject registrationFormObject = new RegistrationFormObject();
				    	registrationFormObject.UUID = UUID;
				    	registrationFormObject.PlayerName = PlayerName;
						registrationFormObject.Email = Email;
						registrationFormObject.Password = Password;
						registrationFormObject.IP = IP;
						
						myClient.sendToServer("adduser", "commandLine", utility.getBytesFromObject(registrationFormObject));
						
						return true;
					}
				}
				if(args[0].equalsIgnoreCase("test")){
					MyPlayerObject myPlayerObject = new MyPlayerObject();
					
					myPlayerObject.playerName = "Christopher asdasdasdasdasd";
					myPlayerObject.UUID = "Ich";
					myPlayerObject.IP = "Hallo";
					myPlayerObject.ServerName = plugin.getServer().getIp();
					
					myClient.sendToServer("updateuser", "commandline", utility.getBytesFromObject(myPlayerObject));
				}
				if(args[0].equalsIgnoreCase("send")){
					if(args.length > 2){
						StringBuilder sb = new StringBuilder();
						StringBuilder message = new StringBuilder();
						sb.append("RECIPIENT:"+args[1]+" ");
						sb.append("SENDER:"+sender.getName()+" ");
						sb.append("MESSAGE:");
						for(int i = 2; i < args.length; i++){
							message.append(args[i]);
							message.append(" ");
						}
						sb.append(message.toString());
						myChatClient.sendMessageToMasterChatServer(sb.toString());
						Player player = (Player) sender;
						player.sendMessage(ChatColor.DARK_PURPLE+"["+ChatColor.YELLOW+sender.getName()+ChatColor.DARK_PURPLE+">"+ChatColor.YELLOW+args[1]+ChatColor.DARK_PURPLE+"]"
			                	+ChatColor.DARK_AQUA+message.toString());
						return true;
					}
				}
			}
		}
		return false;
	}

}
