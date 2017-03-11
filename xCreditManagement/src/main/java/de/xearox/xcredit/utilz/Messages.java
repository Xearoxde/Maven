package de.xearox.xcredit.utilz;

import java.util.Calendar;

import org.bukkit.ChatColor;

public class Messages {
	
	public static String paymentReminder(){
		return "You need to pay your payment rate soon.";
	}
	
	public static String paymentDeadline(){
		return "You reached the payment deadline. Your account will be locked soon";
	}
	
	public static String commandRegister(){
		return "Register a new credit card.";
	}

	public static String commandPay() {
		// TODO Auto-generated method stub
		return "Pay your payment rate.";
	}

	public static String commandRestore() {
		// TODO Auto-generated method stub
		return "Restore your creditcard account.";
	}

	public static String commandLoan() {
		// TODO Auto-generated method stub
		return "Get the money from your creditcard.";
	}

	public static String commandShow() {
		// TODO Auto-generated method stub
		return "Show you money on the creditcard.";
	}

	public static String notPermission() {
		// TODO Auto-generated method stub
		return "Sorry but you haven't the permission to do this!";
	}

	public static String sendResetCode() {
		// TODO Auto-generated method stub
		return ChatColor.translateAlternateColorCodes('&', "Your reset code is &3%resetcode%&f! Please save the code for later restore if you forgot the pincode!");
	}

	public static String sendSuccess() {
		// TODO Auto-generated method stub
		return "Your credit card was successful created.";
	}

	public static String sendPincodeToShort() {
		// TODO Auto-generated method stub
		return "Sorry, but your pincode is to short!";
	}

	public static String sendUsernameToShort() {
		// TODO Auto-generated method stub
		return "Sorry, but your username is to short!";
	}

	public static String sendDuplicateUser() {
		// TODO Auto-generated method stub
		return "Sorry, but the username is already taken!";
	}

	public static String sendAccountLimitReached() {
		// TODO Auto-generated method stub
		return "Sorry, but you have reached you account limit!";
	}

	public static String sendError() {
		// TODO Auto-generated method stub
		return "Sorry, but something goes wrong. Please contact a moderator or admin!";
	}

	public static String sendFailed() {
		// TODO Auto-generated method stub
		return "Sorry, but something goes wrong. Please contact a moderator or admin!";
	}

	public static String sendMoneyMsg(double money, long day, String currencySymbol) {
		long now = System.currentTimeMillis();
		long end = day - now;
		int days = (int) (end / (1000*60*60*24));
		if(days <= 1){
			return "You owe "+money+" "+currencySymbol+". You have 1 day to pay it back. After that your account will be locked!";
		} else {
			return "You owe "+money+" "+currencySymbol+". You have "+days+" days to pay it back.";
		}
		
		
	}

	public static String sendPincodeWrong(int failures, int maxFaliures) {
		return "Sorry, but you pincode is wrong. You tried it "+failures+"/"+maxFaliures+" times already. If you reached the maximum you will be locked!";
	}

	public static String sendUsernameNotFound() {
		return "Your username wasn't found. Please try again.";
	}

	public static String sendAdminShow(String username, double money, String currencySymbol, long day) {
		long now = System.currentTimeMillis();
		long end = day - now;
		System.out.println(now);
		System.out.println(day);
		System.out.println(end);
		int days = (int) (end / (1000*60*60*24));
		if(days <= 1){
			return "The user "+username+" owe "+money+" "+currencySymbol+" right now. The user has 1 day to pay it back.";
		} else {
			return "The user "+username+" owe "+money+" "+currencySymbol+" right now. The user has "+days+" days to pay it back.";
		}
	}
	
	public static String sendResetcodeWrong(){
		return "Sorry, but your resetcode was wrong. Please try again.";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
