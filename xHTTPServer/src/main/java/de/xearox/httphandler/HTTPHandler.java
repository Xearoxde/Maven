package de.xearox.httphandler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

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
			
			return;
		}
		
		
	}
}
