package de.xearox.httpserver.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.xearox.httpserver.util.PageSites;
import de.xearox.httpserver.util.PageVariables;

public class IncludeHandler {
	
	public String pageInclude(String webRoot, String content) throws FileNotFoundException{
		String reader = null;
		for(PageSites pages : PageSites.values()){
			File includeFile = new File(webRoot+pages.getPath());
			Scanner scanner = new Scanner(includeFile);
			if(includeFile.exists()){
				reader = scanner.useDelimiter("\\Z").next();
				content = content.replace(pages.getPlaceholder(), reader);
			}
			scanner.close();
		}
		return content;
	}
	
	public String pageInclude(String webRoot, File file) throws FileNotFoundException{
		String reader = null;
		File sourceFile = file;
		String content;
		File includeFile = null;
		Scanner scanner = new Scanner(sourceFile);
		content = scanner.useDelimiter("\\Z").next();
		scanner.close();
		for(PageSites pages : PageSites.values()){
			includeFile = new File(webRoot+pages.getPath());
			if(includeFile.exists()){
				scanner = new Scanner(includeFile);
				reader = scanner.useDelimiter("\\Z").next();
				content = content.replace(pages.getPlaceholder(), reader);
			}
			scanner.close();
		}
		return content;
	}




















}
