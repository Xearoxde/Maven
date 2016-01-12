package de.xearox.httpserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import de.xearox.httpserver.handler.CookieHandler;
import de.xearox.httpserver.handler.HTTPHandler;
import de.xearox.httpserver.handler.IncludeHandler;
import de.xearox.httpserver.util.FileManager;
import de.xearox.httpserver.util.Logger;
import de.xearox.httpserver.util.PageSites;
import de.xearox.httpserver.util.PageVariables;
import de.xearox.httpserver.util.ServerHelper;
import de.xearox.xconomy.XConomy;
import de.xearox.xconomy.utility.Database;

public class HTTPThread extends Thread {
    private Socket socket;
    private File webRoot;
    private boolean allowDirectoryListing;
    private HTTPHandler httpHandler;
    private XConomy plugin;
    private LoginData loginData;
    private boolean loginSuccess;
    private CookieHandler cookieHandler;
    private IncludeHandler includeHandler;
    private Map<String, String> cookies;

    /**
     * Konstruktor; speichert die übergebenen Daten
     *
     * @param socket                verwendeter Socket
     * @param webRoot               Pfad zum Hauptverzeichnis
     * @param allowDirectoryListing Sollen Verzeichnisinhalte aufgelistet werden, falls keine Index-Datei vorliegt?
     */
    public HTTPThread(Socket socket, File webRoot, boolean allowDirectoryListing, XConomy plugin) {
        this.socket = socket;
        this.plugin = plugin;
        this.webRoot = webRoot;
        this.allowDirectoryListing = allowDirectoryListing;
        this.loginData = new LoginData();
        this.httpHandler = new HTTPHandler(plugin, this.loginData);
        this.cookies = new HashMap<String, String>();
        this.cookieHandler = new CookieHandler();
        this.includeHandler = new IncludeHandler();
    }

    /**
     * "Herz" des Servers: Verarbeitet den Request des Clients, und sendet schließlich die Response
     */
    public void run() {
        // Vorbereitung und Einrichtung des BufferedReader und BufferedOutputStream
        // zum Lesen des Requests und zur Ausgabe der Response
    	final BufferedReader in;
        final BufferedOutputStream out;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
            out = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            Logger.exception(e.getMessage());
            return;
        }
        // Timeout für die Verbindung von 30 Sekunden
        // Verhindert zu viele offengehaltene Verbindungen, aber auch Übertragung von großen Dateien
        try {
            socket.setSoTimeout(30000);
        } catch (SocketException e) {
            Logger.exception(e.getMessage());
        }

        // Lesen des Request
        String line;
        ArrayList<String> request = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null && line.length() != 0 && !line.equals("")) {
                request.add(line);
            }
        } catch (IOException e) {
            // Request konnte nicht (korrekt) gelesen werden
            sendError(out, 400, "Bad Request");
            Logger.exception(e.getMessage());
            return;
        }
        // Request war leer; sollte nicht auftreten
        if (request.isEmpty()) {
        	return;
        }

        // Nur Requests mit dem HTTP 1.0 / 1.1 Protokoll erlaubt
        if (!request.get(0).endsWith(" HTTP/1.0") && !request.get(0).endsWith(" HTTP/1.1")) {
            sendError(out, 400, "Bad Request");
            Logger.error(400, "Bad Request: " + request.get(0), socket.getInetAddress().toString());
            return;
        }

        // Es muss ein GET- oder POST-Request sein
        boolean isPostRequest = false;
        if (!request.get(0).startsWith("GET ")) {
            if (request.get(0).startsWith("POST ")) {
                // POST-Requests werden gesondert behandelt
                isPostRequest = true;
            } else {
                // Methode nicht implementiert oder unbekannt
                sendError(out, 501, "Not Implemented");
                Logger.error(501, "Not Implemented: " + request.get(0), socket.getInetAddress().toString());
                return;
            }
        }
        // Auf welche Datei / welchen Pfad wird zugegriffen?
        String wantedFile;
        String path;
        File file;
        
        wantedFile = request.get(0).substring(4, request.get(0).length() - 9);
        
        //If the wanted file : index.php, index.html, index.ecweb or just / then the server will return the replaced index.ecweb file
        //Also it will only return the index.ecweb if the browser has not set a cookie right now
        if(wantedFile.equals("/")||wantedFile.equals("/index.php")||wantedFile.equals("/index.html")||wantedFile.equals("/index.ecweb")){
        	file = new File(webRoot + "/index.ecweb");
        	if(file.isFile() && file.exists()){
        		for(int i = 0; i < request.size(); i += 1){
        			if(request.get(i).indexOf("Cookie:") == -1 && i == request.size()){ //If request return -1 then no cookie was set
        				try {
        					System.out.println(i);
        					Scanner scanner = new Scanner(file);
							String content = scanner.useDelimiter("\\Z").next();
							content = includeHandler.pageInclude(webRoot.toString(), content, loginData);
							content = content.replace("{hostname}", socket.getLocalAddress().toString());
							content = content.replace("{port}", String.valueOf(socket.getLocalPort()));
							sendHeader(out, 200, "OK", "text/html", content.length(), System.currentTimeMillis());
							
							out.write(content.getBytes());
							out.flush();
							out.close();
							
							scanner.close();
							
							// Schließt den Socket; "keep-alive" wird also ignoriert
							socket.close();
							return;
							
							
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			} else if(request.get(i).indexOf("Cookie:") > -1){
        				cookies = cookieHandler.getCookies(cookies, request);
        			}
        		}
        	}
        }
        
        

        if (isPostRequest) wantedFile = request.get(0).substring(5, request.get(0).length() - 9);

        if(wantedFile.contains("php") && isPostRequest){
        	loginData = this.httpHandler.getPostRequest(request, this, out, in, socket, cookies);
        	if(loginData == null){
        		//sendError(out, 403, "Forbidden");
        	}
        	System.out.println(loginData.wantedFile);
        	if(loginData.wantedFile.equals("/tpl/acpcommand.tpl")){
        		String content;
        		file = new File(webRoot + loginData.wantedFile);
				try {
					content = new Scanner(file).useDelimiter("\\Z").next();
					content = includeHandler.pageInclude(webRoot.getPath(), content, loginData);
					content = content.replace("{hostname}", socket.getLocalAddress().toString());
					content = content.replace("{port}", String.valueOf(socket.getLocalPort()));
					sendHeader(out, 200, "OK", "text/html", content.length(), System.currentTimeMillis());
					
					out.write(content.getBytes());
					out.flush();
					out.close();
					// Schließt den Socket; "keep-alive" wird also ignoriert
					socket.close();
					return;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					Logger.exception(e.getMessage());
					sendError(out, 404, "File not Found");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Logger.exception(e.getMessage());
					sendError(out, 403, "Forbidden");
					return;
				}
        	}
        	
        	if(loginData.loginSuccess || loginData.isLogout){
        		loginSuccess = loginData.loginSuccess;
        		wantedFile = loginData.wantedFile;
        		
        		if(cookies.isEmpty() && !loginData.isLogout){
        			try {
						cookies = plugin.database().getCookies(cookies, loginData);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						cookies = cookieHandler.setCookies(cookies, loginData, socket);
						plugin.database().createNewCookie(cookies);
						loginData.isCookieSet = true;
					}
        			
        		}
        		
        	} else {
        		if(loginData.isLogout){
        			wantedFile = "/index.ecweb";
        		} else {
        			sendError(out, 403, "Forbidden");
        			return;
        		}
        	}
        }
        // Angeforderte Datei abfangen und variabeln ersetzen
        if(wantedFile.contains("welcome.ecweb") && loginData.loginSuccess){
        	String content = WebResources.getWelcomeRedirect(socket);
        	try {
        		sendHeader(out, 301, "Moved Permanently", "text/html", content.length(), System.currentTimeMillis());
        		// Sendet Daten der Response
				out.write(content.getBytes());
				out.flush();
				out.close();
				
				// Schließt den Socket; "keep-alive" wird also ignoriert
				socket.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
        }
        
        if(wantedFile.contains("logout.php")){
        	String content = WebResources.getIndexRedirect(socket);
        	try {
        		sendHeader(out, 301, "Moved Permanently", "text/html", content.length(), System.currentTimeMillis());
        		// Sendet Daten der Response
				out.write(content.getBytes());
				out.flush();
				out.close();
				
				// Schließt den Socket; "keep-alive" wird also ignoriert
				socket.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        
        }
        if(wantedFile.contains(".ecweb")){
        	File getWantedFile = new File(webRoot + wantedFile);
        	if(wantedFile.equals("/acp/adminpanel.ecweb")&&!loginData.isAdmin){
        		cookies = cookieHandler.getCookies(cookies, request);
            	loginData.isAdmin = plugin.database().getAdmin(cookies.get("username"));
            	if(!loginData.isAdmin){
	        		System.out.println(loginData.isAdmin);
	        		System.out.println(loginData.username);
	        		System.out.println(wantedFile);
	        		sendError(out, 403, "Forbidden");
					return;
            	} else {
            		cookies.clear();
            	}
        	}
        	if(getWantedFile.exists()&&getWantedFile.isFile()){
        		try {
					@SuppressWarnings("resource")
					String content = new Scanner(getWantedFile).useDelimiter("\\Z").next();
					if(cookies.isEmpty()){
						try{
							cookies = cookieHandler.getCookies(cookies, request);
							loginData.username = cookies.get("username");
							if(loginData.username.equals("")){
								sendError(out, 403, "Forbidden");
								return;
							}
							loginData.isAdmin = plugin.database().getAdmin(loginData.username);
							
						} catch (Exception e){
							getWantedFile = new File(webRoot + "/index.ecweb");
							content = new Scanner(getWantedFile).useDelimiter("\\Z").next();
							content = includeHandler.pageInclude(webRoot.getPath(), content, loginData);
							content = content.replace("{hostname}", socket.getLocalAddress().toString());
							content = content.replace("{port}", String.valueOf(socket.getLocalPort()));
							sendHeader(out, 200, "OK", "text/html", content.length(), System.currentTimeMillis());
							try {
								// Sendet Daten der Response
								out.write(content.getBytes());
								out.flush();
								out.close();
								// Schließt den Socket; "keep-alive" wird also ignoriert
								socket.close();
								return;
							} catch (IOException ex) {
								Logger.exception(ex.getMessage());
								sendError(out, 403, "Forbidden");
								return;
							}
						}
					}
					try{
						if(!cookieHandler.getCookies(cookies, request).equals(plugin.database().getCookies(cookies,loginData))){
							sendError(out, 403, "Forbidden");
							return;
						}
					} catch ( SQLException e){
						wantedFile = "/";
					}
					content = includeHandler.pageInclude(webRoot.getPath(), content, loginData);
					content = content.replace("{hostname}", socket.getLocalAddress().toString());
					content = content.replace("{port}", String.valueOf(socket.getLocalPort()));
					content = content.replace("{username}", loginData.username);
					content = content.replace(PageVariables.PLAYER_NAME.getPlaceholder(), loginData.username);
					content = content.replace(PageVariables.PLAYER_MONEY.getPlaceholder(), 
							httpHandler.getPlayerMoney(UUID.fromString(plugin.database().getPlayerUUID(loginData.username))));
					sendHeader(out, 200, "OK", "text/html", content.length(), System.currentTimeMillis());
					
					try {
						// Sendet Daten der Response
						out.write(content.getBytes());
						out.flush();
						out.close();
						// Schließt den Socket; "keep-alive" wird also ignoriert
						socket.close();
					} catch (IOException e) {
						Logger.exception(e.getMessage());
					}
					
					return;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        // GET-Request mit Argumenten: Entferne diese für die Pfad-Angabe
        if (!isPostRequest && request.get(0).contains("?")) {
            path = wantedFile.substring(0, wantedFile.indexOf("?"));
        } else {
            path = wantedFile;
        }

        // Bestimme nun die exakte Datei, bzw. das Verzeichnis, welche(s) angefordert wurde
        try {
            file = new File(webRoot, URLDecoder.decode(path, "UTF-8")).getCanonicalFile();
        } catch (IOException e) {
            Logger.exception(e.getMessage());
            return;
        }

        // Falls ein Verzeichnis angezeigt werden soll, und eine Index-Datei vorhanden ist
        // soll letztere angezeigt werden
        if (file.isDirectory()) {
            File indexFile = new File(file, "index.ecweb");
            if (indexFile.exists() && !indexFile.isDirectory()) {
                file = indexFile;
                // "/index.html" an Verzeichnispfad anhängen
                if (wantedFile.contains("?")) {
                    wantedFile = wantedFile.substring(0, wantedFile.indexOf("?")) + "/index.ecweb" + wantedFile.substring(wantedFile.indexOf("?"));
                }
            }
        }

        if (!file.toString().startsWith(ServerHelper.getCanonicalWebRoot(webRoot))) {
            // Datei liegt nicht innerhalb des Web-Roots: Zugriff verhindern und
            // Fehlerseite senden
            sendError(out, 403, "Forbidden");
            Logger.error(403, wantedFile, socket.getInetAddress().toString());
            return;
        } else if (!file.exists()) {
            // Datei existiert nicht: Fehlerseite senden
            sendError(out, 404, "Not Found");
            Logger.error(404, wantedFile, socket.getInetAddress().toString());
            return;
        } else if (file.isDirectory()) {
            // Innerhalb eines Verzeichnis: Auflistung aller Dateien

            // Verzeichnisauflistung verboten?
            if (!allowDirectoryListing) {
                // Fehlermeldung senden
                sendError(out, 403, "Forbidden");
                Logger.error(403, wantedFile, socket.getInetAddress().toString());
                return;
            }

            // Ersetze alle "%20"-Leerzeichen mit einem "echten" Leerzeichen
            path = path.replace("%20", " ");

            File[] files = file.listFiles();

            // Das Verzeichnis ist leer? Sende eine entsprechende Fehlermeldung
            if (files != null) {
                if (files.length == 0) {
                    sendError(out, 404, "Not Found");
                    Logger.error(404, wantedFile, socket.getInetAddress().toString());
                    return;
                }
            } else {
                // Kann unter Umständen auf Windows-Systemen vorkommen
                // Beispiel: Aufruf von "Documents and Settings" anstelle von "Users"
                sendError(out, 403, "Forbidden");
                Logger.error(403, wantedFile, socket.getInetAddress().toString());
                return;
            }

            // Alle Einträge alphabetisch sortieren: Zuerst Ordner, danach Dateien
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    if (f1.isDirectory() && !f2.isDirectory()) {
                        return -1;
                    } else if (!f1.isDirectory() && f2.isDirectory()) {
                        return 1;
                    } else {
                        return f1.toString().compareToIgnoreCase(f2.toString());
                    }
                }
            });

            // Ausgabe in einer Tabelle vorbereiten
            String content = "<table><tr><th></th><th>Name</th><th>Last modified</th><th>Size</th></tr>";

            // Einen "Ebene höher"-Eintrag anlegen, falls nicht im Web-Root gearbeitet wird
            if (!path.equals("/")) {
                String parentDirectory = path.substring(0, path.length() - 1);
                int lastSlash = parentDirectory.lastIndexOf("/");
                if (lastSlash > 1) {
                    parentDirectory = parentDirectory.substring(0, lastSlash);
                } else {
                    parentDirectory = "/";
                }

                content += "<tr><td class=\"center\"><div class=\"back\">&nbsp;</div></td>" +
                        "<td><a href=\"" + parentDirectory.replace(" ", "%20") + "\">Parent Directory</a></td>" +
                        "<td></td>" +
                        "<td></td></tr>";
            }

            if (path.equals("/")) path = ""; // Anpassung für Dateiauflistung

            // Jede Datei zur Ausgabe hinzufügen
            for (File myFile : files) {
                // Meta-Daten der Datei abrufen
                String filename = myFile.getName();
                String img;
                String fileSize = FileManager.getReadableFileSize(myFile.length());
                if (myFile.isDirectory()) {
                    img = "<div class=\"folder\">&nbsp;</div>";
                    fileSize = "";
                } else {
                    img = "<div class=\"file\">&nbsp;</div>";
                }

                // Datei in die Tabelle einfügen
                content += "<tr><td class=\"center\">" + img + "</td>" +
                        "<td><a href=\"" + path.replace(" ", "%20") + "/" + filename.replace(" ", "%20") + "\">" + filename + "</a></td>" +
                        "<td>" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myFile.lastModified()) + "</td>" +
                        "<td class=\"center\">" + fileSize + "</td></tr>";
            }

            if (path.equals("")) path = "/"; // Rück-Anpassung für spätere Verwendung

            // Tabelle schließen und mit Template zusammenfügen
            content += "</table>";
            String output = WebResources.getDirectoryTemplate("Index of " + path, content);

            // Abschließenden Slash an Verzeichnis anhängen
            if (!wantedFile.endsWith("/")) wantedFile += "/";

            // Header und Inhalt senden
            sendHeader(out, 200, "OK", "text/html", -1, System.currentTimeMillis());
            Logger.access(wantedFile, socket.getInetAddress().toString());
            try {
                out.write(output.getBytes());
            } catch (IOException e) {
                Logger.exception(e.getMessage());
            }
        } else {
            // Eine einzelne Datei wurde angefordert: Ausgabe via InputStream

            // InputStream vorbereiten
            InputStream reader = null;
            try {
                reader = new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                Logger.exception(e.getMessage());
            }

            // Datei existiert (erstaunlicherweise) nicht (mehr)
            if (reader == null) {
                sendError(out, 404, "Not Found");
                Logger.error(404, wantedFile, socket.getInetAddress().toString());
                return;
            }

            // Falls es keinen festgelegten ContentType zur Dateiendung gibt, wird der Download gestartet
            String contentType = FileManager.getContentType(file);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            // Header senden, Zugriff loggen und Datei senden
            if(cookies.isEmpty()){
            	cookies = cookieHandler.getCookies(cookies, request);
            	if(cookies.isEmpty()){
            		cookies.put("loggedin", "0");
            	}
            }
            if((wantedFile.contains("index.ecweb")||(wantedFile.equals("/")))&&cookies.get("loggedin").equals("1")){
            	String content = WebResources.getWelcomeRedirect(socket);
            	try {
            		sendHeader(out, 301, "Moved Permanently", "text/html", content.length(), System.currentTimeMillis());
            		// Sendet Daten der Response
    				out.write(content.getBytes());
    				out.flush();
    				out.close();
    				
    				// Schließt den Socket; "keep-alive" wird also ignoriert
    				socket.close();
    				return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}        
            } 
            else if(wantedFile.contains("index.ecweb")||(wantedFile.equals("/"))){
            	String content;
				try {
					content = includeHandler.pageInclude(webRoot.getPath(), file, loginData);
					content = content.replace("{hostname}", socket.getLocalAddress().toString());
					content = content.replace("{port}", String.valueOf(socket.getLocalPort()));
					
					sendHeader(out, 200, "OK", "text/html", content.length(), System.currentTimeMillis());
	        		// Sendet Daten der Response
					out.write(content.getBytes());
					out.flush();
					out.close();
					return;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            } else {
            	sendHeader(out, 200, "OK", contentType, file.length(), file.lastModified());
            	Logger.access(wantedFile, socket.getInetAddress().toString());
            }
            try {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                reader.close();
            } catch (NullPointerException | IOException e) {
                // Wirft eine "Broken Pipe" oder "Socket Write Error" Exception,
                // wenn der Download / Stream abgebrochen wird
            	Logger.exception(e.getMessage());
            }
        }

        // OutputStream leeren und schließen
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            Logger.exception(e.getMessage());
        }
    }

    /**
     * Sende den HTTP 1.1 Header zum Client
     *
     * @param out           Genutzter OutputStream
     * @param code          Status-Code, der gesendet werden soll
     * @param codeMessage   Zum Status-Code gehörende Nachricht
     * @param contentType   ContentType des Inhalts
     * @param contentLength Größe des Inhalts
     * @param lastModified  Wann die Datei zuletzt verändert wurde (zum Caching des Browsers)
     */
    private void sendHeader(BufferedOutputStream out, int code, String codeMessage, String contentType, long contentLength, long lastModified) {
        try {
        	if(loginSuccess){
        		out.write(("HTTP/1.1 " + code + " " + codeMessage + "\r\n" +
    					"Set-Cookie: "+"name=ecweb" +"\r\n"+
    					"Set-Cookie: "+"username=" + loginData.username +"\r\n"+
        				"Set-Cookie: "+"IP=" +socket.getInetAddress() +"\r\n"+
    					"Set-Cookie: "+"key="+cookies.get("key") +"\r\n"+
    					"Set-Cookie: "+"loggedin="+ "1" +"\r\n"+
    					"Cache-Control: "+"no-cache"+"\r\n"+
    					"Pragma: "+"no-cache"+"\r\n"+
    					"Date: " + new Date().toString() + "\r\n" +
    					"Server: Xearox HTTP-Server\r\n" +
    					"Content-Type: " + contentType + "; charset=utf-8\r\n" +
    					((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "") +
    					"Last-modified: " + new Date(lastModified).toString() + "\r\n" +
    					"\r\n").getBytes());
        	} else if(loginData.isLogout){
        		out.write(("HTTP/1.1 " + code + " " + codeMessage + "\r\n" +
    					"Set-Cookie: "+"name=ecweb" +"\r\n"+
    					"Set-Cookie: "+"username=" + "" +"\r\n"+
        				"Set-Cookie: "+"IP=" +"" +"\r\n"+
    					"Set-Cookie: "+"key="+"" +"\r\n"+
    					"Set-Cookie: "+"loggedin="+ "0" +"\r\n"+
    					"Cache-Control: "+"no-cache"+"\r\n"+
    					"Pragma: "+"no-cache"+"\r\n"+
    					"Date: " + new Date().toString() + "\r\n" +
    					"Server: Xearox HTTP-Server\r\n" +
    					"Content-Type: " + contentType + "; charset=utf-8\r\n" +
    					((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "") +
    					"Last-modified: " + new Date(lastModified).toString() + "\r\n" +
    					"\r\n").getBytes());
        	} else {
        		out.write(("HTTP/1.1 " + code + " " + codeMessage + "\r\n" +
    					"Date: " + new Date().toString() + "\r\n" +
    					"Server: Xearox HTTP-Server\r\n" +
    					"Cache-Control: "+"no-cache"+"\r\n"+
    					"Pragma: "+"no-cache"+"\r\n"+
    					"Content-Type: " + contentType + "; charset=utf-8\r\n" +
    					((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "") +
    					"Last-modified: " + new Date(lastModified).toString() + "\r\n" +
    					"\r\n").getBytes());
        	}
			
			    } catch (IOException e) {
			        Logger.exception(e.getMessage());
			    }
			}

    /**
     * Sendet eine Fehlerseite zum Browser
     *
     * @param out     Genutzter OutputStream
     * @param code    Fehler-Code, der gesendet werden soll (403, 404, ...)
     * @param message Zusätzlicher Text ("Not Found", ...)
     */
    public void sendError(BufferedOutputStream out, int code, String message) {
        // Bereitet Daten der Response vor
        String output = WebResources.getErrorTemplate("Error " + code + ": " + message);

        // Sendet Header der Response
        sendHeader(out, code, message, "text/html", output.length(), System.currentTimeMillis());

        try {
            // Sendet Daten der Response
            out.write(output.getBytes());
            out.flush();
            out.close();

            // Schließt den Socket; "keep-alive" wird also ignoriert
            socket.close();
        } catch (IOException e) {
            Logger.exception(e.getMessage());
        }
    }










}