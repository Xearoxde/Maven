package de.xearox.xcredit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.handler.CreditCardHandler;
import de.xearox.xcredit.objects.CreditCard;
import de.xearox.xcredit.utilz.ConfigFile;
import de.xearox.xcredit.utilz.Messages;
import de.xearox.xcredit.utilz.Permissions;

public class ArgumentRestore extends ArgumentedCommand {
	
	private ConfigFile cf;
	private XCredit plugin;
	
	protected ArgumentRestore(String name) {
		super(name);
		this.plugin = XCredit.getInstance();
		this.cf = plugin.getConfigFile();
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player)sender;
		if(args.length <= 1 || args.length > 3) return false;
		if(args.length == 2 && player.hasPermission(Permissions.creditRestoreAdmin())){
			
		} else {
			return false;
		}
		
		for(CreditCard creditCard : CreditCardHandler.getAllCreditCards()){
			if(creditCard.getUsername().equalsIgnoreCase(args[1]) && creditCard.getResetcode().equalsIgnoreCase(args[2])){
				
			} else if(creditCard.getUsername().equalsIgnoreCase(args[1]) && !creditCard.getResetcode().equalsIgnoreCase(args[2])){
				
			}
		}
		player.sendMessage(Messages.sendUsernameNotFound());
		return false;
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/credit restore §6<username> <resetcode>";
	}

	@Override
	public boolean playerOnly() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return Permissions.creditRestore();
	}
	
	@FormatMessage
	public void anyMethod(User user){
		user.sendMessage(Messages.sendUsernameNotFound());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
