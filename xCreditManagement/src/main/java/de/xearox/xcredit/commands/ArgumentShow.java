package de.xearox.xcredit.commands;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.handler.CreditCardHandler;
import de.xearox.xcredit.objects.CreditCard;
import de.xearox.xcredit.utilz.ConfigFile;
import de.xearox.xcredit.utilz.Messages;
import de.xearox.xcredit.utilz.Permissions;
import de.xearox.xcredit.utilz.Utilz;

public class ArgumentShow extends ArgumentedCommand {
	
	private XCredit plugin;
	private ConfigFile cf;

	protected ArgumentShow(String name) {
		super(name);
		this.plugin = XCredit.getInstance();
		this.cf = plugin.getConfigFile();
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Player player = (Player)sender;
		
		if(args.length < 2) return false;
		
		if((player.isOp() || player.hasPermission(Permissions.creditShowAdmin())) && args.length == 2){
			for(CreditCard creditCard : CreditCardHandler.getAllCreditCards()){
				if(creditCard.getUsername().equalsIgnoreCase(args[1])){
					player.sendMessage(Messages.sendAdminShow(creditCard.getUsername(), creditCard.getMoney(), 
							cf.getYamlFile().getString(ConfigFile.currencySymbol), creditCard.getPaymentDeadline()));
					return true;
				}
			}
			player.sendMessage(Messages.sendUsernameNotFound());
		}
		
		UUID playerUUID = player.getUniqueId();
		String username = args[1];
		String pincode = Utilz.makeHash(args[2]);
		for(CreditCard creditCard : CreditCardHandler.getAllCreditCards()){
			if(creditCard.getUsername().equalsIgnoreCase(username) && creditCard.getPincode().equals(pincode)){
				player.sendMessage(Messages.sendMoneyMsg(creditCard.getMoney(),creditCard.getPaymentDeadline(), cf.getYamlFile().getString(ConfigFile.currencySymbol)));
				return true;
			} else if(creditCard.getUsername().equalsIgnoreCase(username) && !creditCard.getPincode().equals(pincode)){
				creditCard.setLoginFailures(creditCard.getLoginFailures()+1);
				player.sendMessage(Messages.sendPincodeWrong(creditCard.getLoginFailures(), cf.getYamlFile().getInt(ConfigFile.accountMaxLoginFailures)));
				return true;
			}
		}
		player.sendMessage(Messages.sendUsernameNotFound());
		return true;
	}
	
	@Override
	public String getSyntax() {
		return "§7/credit show §6<username> <pincode>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public String getPermission() {
		return Permissions.creditShow();
	}

	

}
