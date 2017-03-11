package de.xearox.xcredit.utilz;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;

import de.xearox.xcredit.XCredit;
import net.md_5.bungee.api.ChatColor;

public class Utilz {
	
	private XCredit plugin;
	
	public Utilz() {
		this.plugin = XCredit.getInstance();
	}
	
	public void copyFileFromJarToOutside(String inputPath, String destPath){
		URL inputUrl = getClass().getResource(inputPath);
		File dest = new File(destPath);
		try {
			FileUtils.copyURLToFile(inputUrl, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}

	public static String makeHash(String pincode) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] pincodeMD5 = pincode.getBytes("UTF-8");
			return Hex.encodeHexString(md.digest(pincodeMD5));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String formatMessage(String message){
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}
