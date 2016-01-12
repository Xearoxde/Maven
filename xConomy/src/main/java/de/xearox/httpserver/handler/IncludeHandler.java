package de.xearox.httpserver.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import de.xearox.httpserver.LoginData;
import de.xearox.httpserver.util.PageSites;
import de.xearox.httpserver.util.PageVariables;

public class IncludeHandler {
	
	@SuppressWarnings("resource")
	public String pageInclude(String webRoot, String content, LoginData loginData) throws FileNotFoundException{
		String reader = null;
		for(PageSites pages : PageSites.values()){
			File includeFile = new File(webRoot+pages.getPath());
			Scanner scanner = new Scanner(includeFile);
			if(includeFile.exists()){
				if(pages.equals(PageSites.ADMINPANEL)||pages.equals(PageSites.COMMANDFRAME)){
					if(loginData.isAdmin){
						scanner = new Scanner(includeFile);
						reader = scanner.useDelimiter("\\Z").next();
						content = content.replace(pages.getPlaceholder(), reader);
					} else {
						scanner = new Scanner(includeFile);
						reader = scanner.useDelimiter("\\Z").next();
						content = content.replace(pages.getPlaceholder(), "");
					}
				} else {
					reader = scanner.useDelimiter("\\Z").next();
					content = content.replace(pages.getPlaceholder(), reader);
				}
			}
			scanner.close();
		}
		return content;
	}
	
	public String pageInclude(String webRoot, File file, LoginData loginData) throws FileNotFoundException{
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
				if(pages.equals(PageSites.ADMINPANEL)||pages.equals(PageSites.COMMANDFRAME)){
					if(loginData.isAdmin){
						scanner = new Scanner(includeFile);
						reader = scanner.useDelimiter("\\Z").next();
						content = content.replace(pages.getPlaceholder(), reader);
					} else {
						scanner = new Scanner(includeFile);
						reader = scanner.useDelimiter("\\Z").next();
						content = content.replace(pages.getPlaceholder(), "");
					}
				} else {
					scanner = new Scanner(includeFile);
					reader = scanner.useDelimiter("\\Z").next();
					content = content.replace(pages.getPlaceholder(), reader);
				}
				
			}
			scanner.close();
		}
		return content;
	}




















}
