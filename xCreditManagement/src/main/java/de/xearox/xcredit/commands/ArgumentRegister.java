package de.xearox.xcredit.commands;

import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.utilz.CheckRespond;
import de.xearox.xcredit.utilz.Messages;
import de.xearox.xcredit.utilz.Permissions;

public class ArgumentRegister extends ArgumentedCommand{
	
	private XCredit plugin;
	
	protected ArgumentRegister(String name) {
		super(name);
		this.plugin = XCredit.getInstance();
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if(args.length != 3){
			return false;
		}
		Player player = (Player)sender;
		UUID playerUUID = player.getUniqueId();
		String username = args[1];
		String pincode = args[2];
		
		CheckRespond checkRespond = plugin.getCreditCardHandler().createCreditCard(playerUUID, username, pincode);
		if(checkRespond == CheckRespond.SUCCESS){
			String resetCode = plugin.getCreditCardHandler().getCreditCardWithPlayerUUID(playerUUID).getTempResetcode();
			player.sendMessage(Messages.sendSuccess());
			player.sendMessage(Messages.sendResetCode().replaceAll("%resetcode%",resetCode));
			return true;
		} else if(checkRespond == CheckRespond.PINCODETOSHORT){
			player.sendMessage(Messages.sendPincodeToShort());
			return true;
		} else if(checkRespond == CheckRespond.USERNAMETOSHORT){
			player.sendMessage(Messages.sendUsernameToShort());
			return true;
		} else if(checkRespond == CheckRespond.DUPLICATEUSER){
			player.sendMessage(Messages.sendDuplicateUser());
			return true;
		} else if(checkRespond == CheckRespond.ACCOUNTLIMITREACHED){
			player.sendMessage(Messages.sendAccountLimitReached());
			return true;
		} else if(checkRespond == CheckRespond.ERROR){
			player.sendMessage(Messages.sendError());
			return true;
		} else if(checkRespond == CheckRespond.FAILED){
			player.sendMessage(Messages.sendFailed());
			return true;
		}
		
		return false;
	}
	
	@Override
	public String getSyntax(){
		return "§7/credit register §6<username> <pincode>";
	}

	@Override
	public boolean playerOnly() {
		return true;
	}

	@Override
	public String getPermission() {
		return Permissions.creditRegister();
	}
}
