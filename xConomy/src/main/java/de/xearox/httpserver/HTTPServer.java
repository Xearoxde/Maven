package de.xearox.httpserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import de.xearox.httpserver.util.Logger;
import de.xearox.httpserver.util.ServerHelper;

public class HTTPServer {

	/**
     * Einstiegspunkt der Anwendung; erstellt ein HTTPServer-Objekt
     * @param args Beim Aufruf übergebene Argumente; werden aber ignoriert
     */
    public static void main(String[] args) {
    	
    	if(args.length==0){
    		new HTTPServer(9090, new File("./www"), true, new File("log.txt"));
    	}else if(args.length==1){
    		new HTTPServer(Integer.parseInt(args[0]), new File("./www"), true, new File("log.txt"));
    	}
        
    }

    /**
     * Konstruktor; erstellt (wenn nötig) den Ordner für die Daten und startet schließlich den ConnectionListener
     */
    public HTTPServer(int port, final File webRoot, final boolean allowDirectoryListing, File logfile) {
        Logger.setLogfile(logfile);

        // Gib die IP-Adresse sowie den Port des Servers aus
        // Passe die Ausgabe an die Länge der IP-Adresse + Port an
        String lineOne = "";
        String lineUrl = "### http://" + ServerHelper.getServerIp() + ":" + port + " ###";
        String lineTitle = "### HTTP-Server";
        for (int i = 0; i < lineUrl.length(); i++) lineOne += "#";
        for (int i = 0; i < lineUrl.length() - 18; i++) lineTitle += " ";
        lineTitle += "###";

        // Ausgabe der Informationen
        System.out.println(lineOne);
        System.out.println(lineTitle);
        System.out.println(lineUrl);
        System.out.println(lineOne);

        // Erstellt einen Ordner für die Daten (falls nötig)
        if (!webRoot.exists() && !webRoot.mkdir()) {
            // Ordner existiert nicht & konnte nicht angelegt werden: Abbruch
            Logger.exception("Konnte Daten-Verzeichnis nicht erstellen.");
            Logger.exception("Beende...");
            System.exit(1);
        }

        // Erstelle einen ServerSocket mit dem angegebenen Port
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(port);
        } catch (IOException | IllegalArgumentException e) {
            // Port bereits belegt, darf nicht genutzt werden, ...: Abbruch
            Logger.exception(e.getMessage());
            Logger.exception("Beende...");
            System.exit(1);
        }

        // Neuer Thread: wartet auf eingehende Verbindungen und "vermittelt" diese an einen neuen HTTPThread, der die Anfrage dann verarbeitet
        final ServerSocket finalSocket = socket;
        Thread connectionListener = new Thread(){
            public void run(){
                while (true) {
                    try {
                        HTTPThread thread = new HTTPThread(finalSocket.accept(), webRoot, allowDirectoryListing);
                        thread.start();
                    } catch (IOException e) {
                        Logger.exception(e.getMessage());
                        Logger.exception("Beende...");
                        System.exit(1);
                    }
                }
            }
        };
        connectionListener.start();
    }
}