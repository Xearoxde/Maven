package de.xearox.tutorial;

import java.security.MessageDigest;

import org.bukkit.entity.Player;

public class FunctionLib {

	public String MD5Hashing(String Value)throws Exception
    {
    	String password = Value;
    	
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
	

	public String GetItemInPlayerHand(Player player){
		String TempString;
		TempString = player.getItemInHand().getType().toString();
		TempString = TempString.toLowerCase();
		//System.out.println("In player Hand "+TempString);
		
		
			 
		
		
		return TempString;
	}
	
	
	
}