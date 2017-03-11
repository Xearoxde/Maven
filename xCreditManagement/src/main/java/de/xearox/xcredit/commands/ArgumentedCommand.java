package de.xearox.xcredit.commands;

import org.bukkit.command.Command;

public abstract class ArgumentedCommand extends Command{

	protected ArgumentedCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public abstract String getSyntax();
	
	public abstract boolean playerOnly();
	
	public abstract String getPermission();
}
