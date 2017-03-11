package de.xearox.xcredit.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.utilz.Messages;
import de.xearox.xcredit.utilz.Utilz;

public class CommandXCredit implements CommandExecutor{
	
	private Utilz utilz;
	
	private Map<String, ArgumentedCommand> argumentedCommandMap = new HashMap<String, ArgumentedCommand>();
	
	public CommandXCredit() {
		this.utilz = XCredit.getInstance().getUtilz();
		argumentedCommandMap.put("register", new ArgumentRegister("register"));
		argumentedCommandMap.put("show", new ArgumentShow("show"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		for(String arg : args){
			if(!argumentedCommandMap.containsKey(arg)) continue;
			ArgumentedCommand argumentedCommand = argumentedCommandMap.get(arg);
			if(argumentedCommand.playerOnly()){
				if(!(sender instanceof Player)){
					sender.sendMessage("Sorry but Albert Einstein didn't allow you to call this command :P ");
					return true;
				}
				Player player = (Player)sender;
				if(!player.hasPermission(argumentedCommand.getPermission())){
					player.sendMessage(Messages.notPermission());
					return true;
				}
				if(argumentedCommand.execute(sender, label, args)){
					return true;
				} else {
					player.sendMessage(argumentedCommandMap.get(arg).getSyntax());
					return true;
				}
			} else {
				if(argumentedCommand.execute(sender, label, args)){
					return true;
				} else {
					sender.sendMessage("§8[§3XCredit§8]"+argumentedCommand.getSyntax());
					return true;
				}
			}
		}
		sender.sendMessage("§8[§3XCredit§8] §7/credit register §6<username> <pincode>§7 -- "+Messages.commandRegister());
		sender.sendMessage("§8[§3XCredit§8] §7/credit pay §6<username> <pincode> <amount>§7 -- "+Messages.commandPay());
		sender.sendMessage("§8[§3XCredit§8] §7/credit restore §6<username> <resetcode>§7 -- "+Messages.commandRestore());
		sender.sendMessage("§8[§3XCredit§8] §7/credit loan §6<username> <pincode> <amount>§7 -- "+Messages.commandLoan());
		sender.sendMessage("§8[§3XCredit§8] §7/credit show §6<username> <pincode>§7 -- "+Messages.commandShow());
		return true;
	}

}
