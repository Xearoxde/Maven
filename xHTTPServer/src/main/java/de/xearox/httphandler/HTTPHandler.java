package de.xearox.httphandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.net.Socket;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import de.menzerath.httpserver.HTTPThread;
import de.menzerath.util.Logger;

public class HTTPHandler {

	private HTTPThread httpThread;
	
	//public HTTPHandler(HTTPServer httpServer){
	//	this.httpServer = httpServer;
	//}
	
	public void getPostRequest(ArrayList<String> request, HTTPThread httpThread
			,BufferedOutputStream out, BufferedReader in, Socket socket){
		String wantedFile;
		StringBuilder sb = new StringBuilder();
		this.httpThread = httpThread;
		if(request.isEmpty()){
			this.httpThread.sendError(out, 400, "BAD REQUEST");
			Logger.error(400, "Bad Request: " + request.get(0), socket.getInetAddress().toString());
			return;
		}
		System.out.println(request.toString());
		wantedFile = request.get(0).substring(5, request.get(0).length() - 9);
		System.out.println(wantedFile);
		if(wantedFile.equalsIgnoreCase("/test.php")){
			System.out.println("Test Bestanden");
			int ch = 0;
			try{
				while(in.ready() && (ch = in.read()) != -1){
					sb.append(Character.toChars(ch));
				}
				System.out.println(sb.toString());
				System.out.println("");
				loginHandler(sb.toString());
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	public boolean loginHandler(String postData){
		
		String username;
		String password;
		
		username = StringUtils.substringBetween(postData, "Username=", "&");
		password = StringUtils.substringAfter(postData, "Password=");
		
		System.out.println(username);
		System.out.println(password);
		
		return true;
	}


















}
