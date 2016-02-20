package de.xearox.xdaily.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.xdaily.XDaily;

public class MyExecutor implements CommandExecutor {

	private XDaily plugin;
	
	public MyExecutor(XDaily plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		if(label.equalsIgnoreCase("daily")){
			Player player = (Player) sender;
			
			player.sendMessage("Test");
			return true;
		}
		
		if(label.equalsIgnoreCase("test")){
			Player player = (Player) sender;
			
			player.sendMessage("test33");
			return true;
		}
		
		return false;
	}

}
