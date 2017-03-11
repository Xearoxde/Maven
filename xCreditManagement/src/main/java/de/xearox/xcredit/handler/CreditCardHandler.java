package de.xearox.xcredit.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.objects.CreditCard;
import de.xearox.xcredit.utilz.CheckRespond;
import de.xearox.xcredit.utilz.ConfigFile;
import de.xearox.xcredit.utilz.Messages;

public class CreditCardHandler {
	
	private CreditCardRunner creditCardRunner;
	private ConfigFile cf;
	private YamlConfiguration yc;
	
	private char[] resetChars;
	
	private static List<CreditCard> creditCardList = new ArrayList<CreditCard>();
	
	public static List<CreditCard> getAllCreditCards(){
		return creditCardList;
	}
	
	private XCredit plugin;
	
	public CreditCardHandler() {
		this.plugin = XCredit.getInstance();
		this.creditCardRunner = new CreditCardRunner();
		this.cf = XCredit.getInstance().getConfigFile();
		this.yc = cf.getYamlFile();
		this.resetChars = yc.getString(ConfigFile.resetCodeChars).toCharArray();
	}
	
	public CreditCardRunner getCreditCardRunner(){
		return creditCardRunner;
	}
	
	public String generateResetCode(int length){
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
		    char c = resetChars[random.nextInt(resetChars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	public CreditCard getCreditCard(UUID uuid){
		for(CreditCard creditCard : creditCardList){
			if(creditCard.getUUID().equals(uuid)){
				return creditCard;
			}
		}
		return null;
	}
	
	public CreditCard getCreditCard(String username){
		for(CreditCard creditCard : creditCardList){
			if(creditCard.getUsername().equalsIgnoreCase(username)){
				return creditCard;
			}
		}
		return null;
	}
	
	public CreditCard getCreditCardWithPlayerUUID(UUID playerUUID){
		for(CreditCard creditCard : creditCardList){
			if(creditCard.getPlayerUUID().equals(playerUUID)){
				return creditCard;
			}
		}
		return null;
	}
	
	public CheckRespond createCreditCard(UUID playerUUID, String username, String pincode){
		int accountAmount = 0;
		for(CreditCard creditCard : creditCardList){
			if(creditCard.getPlayerUUID().equals(playerUUID)){
				accountAmount++;
			}
		}
		int specialRank = cf.getPlayerPermission(playerUUID);
		int accountLimit = specialRank == -1 ? yc.getInt(ConfigFile.accountLimitPerUser) : yc.getInt("credit.specialrank."+specialRank+".accountlimit");
		if(playerUUID == null) return CheckRespond.ERROR;
		if(username.length() <= yc.getInt(ConfigFile.accountMinUsernameLength)) return CheckRespond.USERNAMETOSHORT;
		if(pincode.length() < yc.getInt(ConfigFile.accountMinPincodeLength)) return CheckRespond.PINCODETOSHORT;
		if(accountAmount >= accountLimit) return CheckRespond.ACCOUNTLIMITREACHED;
		if(getCreditCard(username) != null) return CheckRespond.DUPLICATEUSER;
		CreditCard creditCard = new CreditCard(playerUUID, username, pincode);
		
		CreditCardHandler.creditCardList.add(creditCard);
		return CheckRespond.SUCCESS;
	}
	
	public void loadCreditCards(){
		if(yc.getBoolean(ConfigFile.useFlatFile)){
			File loadFile = new File(XCredit.getInstance().getDataFolder().getAbsolutePath()+"/creditcardsave/creditcards.yml");
			YamlConfiguration yc = YamlConfiguration.loadConfiguration(loadFile);
			Set<String> keySet = yc.getValues(false).keySet();
			for(String key : keySet){
				CreditCard creditCard = new CreditCard();
				creditCard.setUUID(UUID.fromString(key));
				creditCard.setPlayerUUID(yc.getString(key+".playerUUID"));
				creditCard.setUsername(yc.getString(key+".username"));
				creditCard.setPincode(yc.getString(key+".pincode"));
				creditCard.setLoginFailures(yc.getInt(key+".locingFailures"));
				creditCard.setAccountLimit(yc.getInt(key+".accountLimit"));
				creditCard.setSpecialRank(cf.getPlayerPermission(yc.getString(key+".playerUUID")));
				creditCard.setBorrowLimit(yc.getDouble(key+".borrowLimit"));
				creditCard.setMoney(yc.getDouble(key+".money"));
				creditCard.setInterestRate(yc.getDouble(ConfigFile.interestRate));
				creditCard.setResetcode(yc.getString(key+".resetcode"));
				creditCard.setRegisterDate(yc.getLong(key+".registerDate"));
				creditCard.setPaymentReminder(yc.getLong(key+".paymentReminder"));
				creditCard.setPaymentDeadline(yc.getLong(key+".paymentDeadline"));
				creditCard.setPaymentLockDate(yc.getLong(key+".paymentLockDate"));
				creditCard.setNextInterestDay(yc.getLong(key+".nextInterestDay"));
				creditCard.setAccountLocked(yc.getBoolean(key+".accountLocked"));
				creditCardList.add(creditCard);
			}
			return;
		}
		if(yc.getBoolean(ConfigFile.useMultipleFlatFiles)){
			File saveDir = new File(XCredit.getInstance().getDataFolder().getAbsolutePath()+"/creditcardsave/cards/");
			for(File saveFile : saveDir.listFiles()){
				YamlConfiguration yc = YamlConfiguration.loadConfiguration(saveFile);
				String key = saveFile.getName().replace(".yml", "");
				CreditCard creditCard = new CreditCard();
				creditCard.setUUID(UUID.fromString(key));
				creditCard.setPlayerUUID(yc.getString("playerUUID"));
				creditCard.setUsername(yc.getString("username"));
				creditCard.setPincode(yc.getString("pincode"));
				creditCard.setLoginFailures(yc.getInt("locingFailures"));
				creditCard.setAccountLimit(yc.getInt("accountLimit"));
				creditCard.setSpecialRank(cf.getPlayerPermission(yc.getString("playerUUID")));
				creditCard.setBorrowLimit(yc.getDouble("borrowLimit"));
				creditCard.setMoney(yc.getDouble("money"));
				creditCard.setInterestRate(yc.getDouble(ConfigFile.interestRate));
				creditCard.setResetcode(yc.getString("resetcode"));
				creditCard.setRegisterDate(yc.getLong("registerDate"));
				creditCard.setPaymentReminder(yc.getLong("paymentReminder"));
				creditCard.setPaymentDeadline(yc.getLong("paymentDeadline"));
				creditCard.setPaymentLockDate(yc.getLong("paymentLockDate"));
				creditCard.setNextInterestDay(yc.getLong("nextInterestDay"));
				creditCard.setAccountLocked(yc.getBoolean("accountLocked"));
				creditCardList.add(creditCard);
			}
			return;
		}
	}
	
	
	
	public class CreditCardRunner implements Runnable{

		@Override
		public void run() {
			for(CreditCard creditCard : creditCardList){
				if(creditCard.getPaymentReminder() >= System.currentTimeMillis() && creditCard.getMoney() != 0.0){
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(creditCard.getPlayerUUID());
					if(offPlayer.isOnline()){
						offPlayer.getPlayer().sendMessage(Messages.paymentReminder());
					}
				}
				if(creditCard.getPaymentDeadline() >= System.currentTimeMillis() && creditCard.getMoney() != 0.0){
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(creditCard.getPlayerUUID());
					if(offPlayer.isOnline()){
						offPlayer.getPlayer().sendMessage(Messages.paymentDeadline());
					}
				}
				if(creditCard.getPaymentDeadline() >= System.currentTimeMillis() && creditCard.getMoney() != 0.0){
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(creditCard.getPlayerUUID());
					if(offPlayer.isOnline()){
						offPlayer.getPlayer().sendMessage(Messages.paymentDeadline());
					}
				}
				if(creditCard.getNextInterestDay() >= System.currentTimeMillis() && creditCard.getMoney() != 0.0){
					OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(creditCard.getPlayerUUID());
					if(offPlayer.isOnline()){
						offPlayer.getPlayer().sendMessage(Messages.paymentDeadline());
					}
					double money = creditCard.getMoney();
					money += money * creditCard.getInterestRate();
					creditCard.setMoney(money);
					creditCard.setNextInterstDay(System.currentTimeMillis()+creditCard.getNextInterestDay());
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
