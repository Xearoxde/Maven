package de.xearox.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import de.xearox.httpserver.util.Logger;
import de.xearox.httpserver.util.ServerHelper;
import de.xearox.xconomy.XConomy;

public class HTTPServer {

	private XConomy plugin;
	private ServerSocket socket;
	
	private ServerSocket setSocket(ServerSocket socket){
		return socket;
	}
	
	public ServerSocket getSocket(){
		return this.socket;
	}
	
	/**
     * Konstruktor; erstellt (wenn nötig) den Ordner für die Daten und startet schließlich den ConnectionListener
     */
    public HTTPServer(int port, final File webRoot, final boolean allowDirectoryListing, File logfile, 
    		final XConomy plugin) {
        Logger.setLogfile(logfile);

        // Gib die IP-Adresse sowie den Port des Servers aus
        // Passe die Ausgabe an die Länge der IP-Adresse + Port an
        String lineOne = "";
        String lineUrl = "### http://" + ServerHelper.getServerIp() + ":" + port + " ###";
        String lineTitle = "### HTTP-Server";
        for (int i = 0; i < lineUrl.length(); i++) lineOne += "#";
        for (int i = 0; i < lineUrl.length() - 18; i++) lineTitle += " ";
        lineTitle += "###";
        
        this.plugin = plugin;
        
        // Ausgabe der Informationen
        System.out.println(lineOne);
        System.out.println(lineTitle);
        System.out.println(lineUrl);
        System.out.println(lineOne);

        // Erstellt einen Ordner für die Daten (falls nötig)
        if (!webRoot.exists() && !webRoot.mkdir()) {
            // Ordner existiert nicht & konnte nicht angelegt werden: Abbruch
            Logger.exception("Konnte Daten-Verzeichnis nicht erstellen.");
            return;
        }

        // Erstelle einen ServerSocket mit dem angegebenen Port
        this.setSocket(socket = null);
        try {
            //socket = new ServerSocket(port);
        	this.setSocket(socket = new ServerSocket(port));
        } catch (IOException | IllegalArgumentException e) {
            // Port bereits belegt, darf nicht genutzt werden, ...: Abbruch
            Logger.exception(e.getMessage());
            return;
        }

        // Neuer Thread: wartet auf eingehende Verbindungen und "vermittelt" diese an einen neuen HTTPThread, der die Anfrage dann verarbeitet
        final ServerSocket finalSocket = socket;
        Thread connectionListener = new Thread(){
            public void run(){
                while (true) {
                    try {
                        HTTPThread thread = new HTTPThread(finalSocket.accept(), webRoot, allowDirectoryListing, plugin);
                        thread.start();
                    } catch (IOException e) {
                        Logger.exception(e.getMessage());
                        return;
                    }
                }
            }
        };
        connectionListener.start();
    }
}