package de.xearox.xcredit.objects;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xcredit.XCredit;
import de.xearox.xcredit.handler.CreditCardHandler;
import de.xearox.xcredit.utilz.ConfigFile;
import de.xearox.xcredit.utilz.Utilz;

public class CreditCard {
	private ConfigFile cf;
	private YamlConfiguration yc;
	
	private final long DAY = 86400000L; 
	
	private UUID uuid;
	private UUID playerUUID;
	private int loginFailures;
	private int accountLimit;
	private int specialRank;
	private double borrowLimit;
	private double money;
	private double interestRate;
	private String username;
	private String pincode;
	private String resetcode;
	private String tempResetCode;
	private long registerDate;
	private long paymentReminder;
	private long paymentDeadline;
	private long paymentLockDate;
	private long nextInterestDay;
	private boolean accountLocked;
	private List<String> lockedUUIDs;
	
	public CreditCard() {
		
	}
	
	public CreditCard(UUID playerUUID, String username, String pincode) {
		long now = System.currentTimeMillis();
		this.cf = XCredit.getInstance().getConfigFile();
		this.yc = cf.getYamlFile();
		this.uuid = UUID.randomUUID();
		this.playerUUID = playerUUID;
		this.username = username;
		this.pincode = Utilz.makeHash(pincode);
		this.interestRate = yc.getDouble(ConfigFile.interestRate);
		this.specialRank = cf.getPlayerPermission(playerUUID);
		this.borrowLimit = specialRank == -1 ? yc.getDouble(ConfigFile.borrowLimit) : yc.getDouble("credit.specialrank."+specialRank+".borrowlimit");
		this.paymentDeadline = now+getPaymentDeadlineLong(specialRank);
		this.paymentReminder = now+getPaymentReminderLong(specialRank);
		this.paymentLockDate = getPaymentLockDateLong(specialRank);
		this.tempResetCode = XCredit.getInstance().getCreditCardHandler().generateResetCode(yc.getInt(ConfigFile.resetCodeLenth));
		this.resetcode = Utilz.makeHash(resetcode);
		this.registerDate = System.currentTimeMillis();
		this.money = 0.0;
		this.accountLocked = false;
		this.lockedUUIDs = new ArrayList<String>();
	}
	
//--------------------METHODS---------------------
	
	public long getPaymentReminderLong(int specialRank){
		if(specialRank == -1){
			return (yc.getInt(ConfigFile.paymentReminderDays)*DAY)+registerDate;
		} else {
			return (yc.getInt("credit.specialrank."+specialRank+".paymentreminder")*DAY)+registerDate;
		}
	}
	
	public long getPaymentDeadlineLong(int specialRank){
		if(specialRank == -1){
			return (yc.getInt(ConfigFile.paymentDeadline)*DAY)+registerDate;
		} else {
			return (yc.getInt("credit.specialrank."+specialRank+".paymentdeadline")*DAY)+registerDate;
		}
	}
	
	public long getPaymentLockDateLong(int specialRank){
		if(specialRank == -1){
			return (yc.getInt(ConfigFile.paymentLockDate)*DAY)+paymentDeadline;
		} else {
			return (yc.getInt("credit.specialrank."+specialRank+".paymentLockDate")*DAY)+paymentDeadline;
		}
	}
	
	public long getPaymentDayLong(){
		return (yc.getInt(ConfigFile.interestDay)*DAY)+registerDate;
	}
	
	public long getNextPaymentDayLong(){
		return yc.getInt(ConfigFile.interestDay)*DAY;
	}
	
	
	public void saveCreditCard(){
		if(yc.getBoolean(ConfigFile.useFlatFile)){
			File saveFile = new File(XCredit.getInstance().getDataFolder().getAbsolutePath()+"/creditcardsave/creditcards.yml");
			YamlConfiguration yc = YamlConfiguration.loadConfiguration(saveFile);
			String key = this.getUUID().toString();
			yc.addDefault(key+".playerUUID", getPlayerUUID().toString());
			yc.addDefault(key+".username", getUsername());
			yc.addDefault(key+".pincode", getPincode());
			yc.addDefault(key+".locingFailures", getLoginFailures());
			yc.addDefault(key+".accountLimit", getAccountLimit());
			yc.addDefault(key+".specialRank", getSpecialRank());
			yc.addDefault(key+".borrowLimit", getBorrowLimit());
			yc.addDefault(key+".money", getMoney());
			yc.addDefault(key+".interestRate", getInterestRate());
			yc.addDefault(key+".resetcode", getResetcode());
			yc.addDefault(key+".registerDate", getRegisterDate());
			yc.addDefault(key+".paymentReminder", getPaymentReminder());
			yc.addDefault(key+".paymentDeadline", getPaymentDeadline());
			yc.addDefault(key+".paymentLockDate", getPaymentLockDate());
			yc.addDefault(key+".nextInterestDay", getNextInterestDay());
			yc.addDefault(key+".accountLocked", getAccountLocked());
			yc.options().copyDefaults(true);
			try {
				yc.save(saveFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if(yc.getBoolean(ConfigFile.useMultipleFlatFiles)){
			File saveFile = new File(XCredit.getInstance().getDataFolder().getAbsolutePath()+"/creditcardsave/cards/"+this.getUUID().toString()+".yml");
			YamlConfiguration yc = YamlConfiguration.loadConfiguration(saveFile);
			yc.addDefault("playerUUID", getPlayerUUID().toString());
			yc.addDefault("username", getUsername());
			yc.addDefault("pincode", getPincode());
			yc.addDefault("locingFailures", getLoginFailures());
			yc.addDefault("accountLimit", getAccountLimit());
			yc.addDefault("specialRank", getSpecialRank());
			yc.addDefault("borrowLimit", getBorrowLimit());
			yc.addDefault("money", getMoney());
			yc.addDefault("interestRate", getInterestRate());
			yc.addDefault("resetcode", getResetcode());
			yc.addDefault("registerDate", getRegisterDate());
			yc.addDefault("paymentReminder", getPaymentReminder());
			yc.addDefault("paymentDeadline", getPaymentDeadline());
			yc.addDefault("paymentLockDate", getPaymentLockDate());
			yc.addDefault("nextInterestDay", getNextInterestDay());
			yc.addDefault("accountLocked", getAccountLocked());
			yc.options().copyDefaults(true);
			try {
				yc.save(saveFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return;
		}
	}
	
//--------------------STATICMETHODS---------------
	public static void loadCreditCard(){
		
	}
//--------------------GETTER----------------------
	
	public UUID getUUID(){
		return uuid;
	}
	
	public UUID getPlayerUUID(){
		return playerUUID;
	}
	
	public int getLoginFailures(){
		return loginFailures;
	}
	
	public int getSpecialRank(){
		return specialRank;
	}
	
	public int getAccountLimit(){
		return accountLimit;
	}
	
	public double getBorrowLimit(){
		return borrowLimit;
	}
	
	public double getMoney(){
		return money;
	}
	
	public double getInterestRate(){
		return interestRate;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPincode(){
		return pincode;
	}
	
	public String getResetcode(){
		return resetcode;
	}
	
	public String getTempResetcode(){
		return tempResetCode;
	}
	
	public long getRegisterDate(){
		return registerDate;
	}
	
	public long getPaymentReminder(){
		return paymentReminder;
	}
	
	public long getPaymentDeadline(){
		return paymentDeadline;
	}
	
	public long getNextInterestDay(){
		return nextInterestDay;
	}
	
	public long getPaymentLockDate(){
		return paymentLockDate;
	}
	
	public boolean getAccountLocked(){
		return accountLocked;
	}
	
	public List<String> getLockedUUIDs(){
		return lockedUUIDs;
	}
//--------------------SETTER----------------------
	
	public void setUUID(UUID uuid){
		this.uuid = uuid;
	}
	
	public void setPlayerUUID(UUID playerUUID){
		this.playerUUID = playerUUID;
	}
	
	public void setPlayerUUID(String playerUUID){
		this.playerUUID = UUID.fromString(playerUUID);
	}
	
	public void setLoginFailures(int loginFailures){
		this.loginFailures = loginFailures;
	}
	
	public void setAccountLimit(int accountLimit){
		this.accountLimit = accountLimit;
	}
	
	public void setBorrowLimit(double borrowLimit){
		this.borrowLimit = borrowLimit;
	}
	
	public void setMoney(double money){
		this.money = money;
	}
	
	public void setInterestReate(double interestRate){
		this.interestRate = interestRate;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setPincode(String pincode){
		this.pincode = pincode;
	}
	
	public void setResetcode(String resetcode){
		this.resetcode = resetcode;
	}
	
	public void setTempResetcode(String tempResetCode){
		this.tempResetCode = tempResetCode;
	}
	
	public void setRegisterDate(long registerDate){
		this.registerDate = registerDate;
	}
	
	public void setPaymentReminder(long paymentReminder){
		this.paymentReminder = paymentReminder;
	}
	
	public void setPaymentDeadline(long paymentDeadline){
		this.paymentDeadline = paymentDeadline;
	}
	
	public void setNextInterstDay(long nextInterestDay){
		this.nextInterestDay = nextInterestDay;
	}
	
	public void setPaymentLockDate(long paymentLockDate){
		this.paymentLockDate = paymentLockDate;
	}
	
	public void setAccountLocked(boolean accountLocked){
		this.accountLocked = accountLocked;
	}

	public void setSpecialRank(int specialRank) {
		this.specialRank = specialRank;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate; 
	}

	public void setNextInterestDay(long nextInterestDay) {
		this.nextInterestDay = nextInterestDay;
	}
	
	public void setLockedUUIDs(List<String> lockedUUIDs){
		this.lockedUUIDs = lockedUUIDs;
	}
	
	
	
	
	
	
	
	
	
}
