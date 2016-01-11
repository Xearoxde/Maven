package de.xearox.httpserver.handler;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import de.xearox.httpserver.LoginData;

public class CookieHandler {
	
	public Map<String, String> getCookies(Map<String, String> cookies, ArrayList<String> request){
		for(int i = 0; i < request.size(); i += 1){
			if(request.get(i).indexOf("Cookie:") != -1){
				String loggedin;
				String cookie = request.get(i);
				if((cookie.substring(cookie.indexOf("loggedin=")).replace("loggedin=", "").equals("0"))){
					return cookies;
				}
				String username = (cookie.substring(cookie.indexOf("username="),cookie.indexOf("; IP=")));
				String ip = (cookie.substring(cookie.indexOf("IP=/"), cookie.indexOf("; key=")));
				String sessionID = (cookie.substring(cookie.indexOf("key="), cookie.indexOf("; loggedin=")));
				loggedin = (cookie.substring(cookie.indexOf("loggedin=")));
				
				cookies.put("username", username.replace("username=", ""));
				cookies.put("ip", ip.replace("IP=/", ""));
				cookies.put("key", sessionID.replace("key=", ""));
				cookies.put("loggedin", loggedin.replace("loggedin=", ""));
			} else {
				System.out.println("index = -1");
			}
		}
		return cookies;
	}
	
	public Map<String, String> setCookies(Map<String, String> cookies, LoginData loginData, Socket socket){
				
		cookies.put("username", loginData.username);
		cookies.put("ip", socket.getInetAddress().toString().replace("/", ""));
		cookies.put("key", createRandomString(16));
		cookies.put("loggedin", "1");
		return cookies;
	}
	
	public Map<String, String> setLogoutCookies(Map<String, String> cookies, LoginData loginData, Socket socket){
		
		cookies.put("username", "");
		cookies.put("ip", "");
		cookies.put("key", "");
		cookies.put("loggedin", "0");
		return cookies;
	}
	
	public String createRandomString(int length){
    	StringBuilder sb = new StringBuilder();
    	String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
    	for(int i = 0; i<length;i++){
    		sb.append(chars.charAt(new Random().nextInt(chars.length())));
    	}
    	return sb.toString();
    }
}
