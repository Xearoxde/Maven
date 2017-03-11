package de.xearox.xcredit.utilz;

public enum CheckRespond {
	
	SUCCESS(true),
	FAILED(false),
	DUPLICATEUSER(false),
	ACCOUNTLIMITREACHED(false),
	PINCODETOSHORT(false),
	USERNAMETOSHORT(false),
	ERROR(false);
	
	
	private boolean isPositive;
	
	private CheckRespond(boolean isPositive) {
		this.isPositive = isPositive;
	}
	
	public boolean getRespond(){
		return isPositive;
	}
}
