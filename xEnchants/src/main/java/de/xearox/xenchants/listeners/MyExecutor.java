package de.xearox.xenchants.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.xearox.xenchants.XEnchants;
import de.xearox.xenchants.utilz.Utilz;

public class MyExecutor implements CommandExecutor{
	
	private XEnchants plugin;
	private Utilz utilz;
	
	public MyExecutor(XEnchants plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

}
