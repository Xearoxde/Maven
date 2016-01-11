package de.xearox.httpserver.handler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import de.xearox.httpserver.HTTPServer;
import de.xearox.httpserver.HTTPThread;
import de.xearox.httpserver.LoginData;
import de.xearox.httpserver.WebResources;
import de.xearox.httpserver.util.Logger;
import de.xearox.xconomy.XConomy;

public class HTTPHandler {

	private HTTPThread httpThread;
	private XConomy plugin;
	private LoginData loginData;
	private WebResources webRessources;
	private CookieHandler cookieHandler;
	
	//public HTTPHandler(HTTPServer httpServer){
	//	this.httpServer = httpServer;
	//}
	
	public HTTPHandler(XConomy plugin, LoginData loginData){
		this.plugin = plugin;
		this.loginData = loginData;
		this.webRessources = new WebResources();
		this.cookieHandler = new CookieHandler();
	}
	
	public LoginData getPostRequest(ArrayList<String> request, HTTPThread httpThread
			,BufferedOutputStream out, BufferedReader in, Socket socket,Map<String, String> cookies){
		String wantedFile;
		StringBuilder sb = new StringBuilder();
		
		this.httpThread = httpThread;
		if(request.isEmpty()){
			this.httpThread.sendError(out, 400, "BAD REQUEST");
			Logger.error(400, "Bad Request: " + request.get(0), socket.getInetAddress().toString());
			
		}
		wantedFile = request.get(0).substring(5, request.get(0).length() - 9);
		if(wantedFile.equalsIgnoreCase("/login.php")){
			int ch = 0;
			try{
				while(in.ready() && (ch = in.read()) != -1){
					sb.append(Character.toChars(ch));
				}
				loginData = loginHandler(sb.toString());
				
				if(!loginData.loginSuccess){
					return loginData;
				} else {
					return loginData;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		if(wantedFile.equalsIgnoreCase("/logout.php")){
			if(cookies == null){
				return loginData;
			}
			try{
				cookies = cookieHandler.getCookies(cookies, request);
				if(cookies == null || cookies.isEmpty()){
					return loginData;
				}
				if(plugin.database().removeCookie(cookies.get("key"))){
					loginData.isCookieSet = false;
					loginData.isLogout = true;
					cookies.put("loggedin", "0");
					loginData.wantedFile = "index.ecweb";
					return loginData;
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return loginData;
		
	}
	
	public LoginData loginHandler(String postData){
		
		String username;
		String password;
		
		username = StringUtils.substringBetween(postData, "Username=", "&");
		password = StringUtils.substringAfter(postData, "Password=");
		
		if(plugin.database().checkLoginData(username, password)){
			loginData.loginSuccess = true;
			loginData.username = username;
			loginData.wantedFile = "/welcome.ecweb";
			return loginData;
		} else {
			loginData.loginSuccess = false;
			loginData.username = username;
			loginData.wantedFile = "/loginfailed.ecweb";
			return loginData;
		}
	}
	
	public String getPlayerMoney(UUID uuid){
		double money = plugin.getAccountActions().getPlayerBalance(plugin.getServer().getOfflinePlayer(uuid));
		String playerMoney;
		
		playerMoney = String.valueOf(money);
		
		return playerMoney;
	}


















}
