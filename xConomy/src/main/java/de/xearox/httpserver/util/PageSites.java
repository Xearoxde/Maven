package de.xearox.httpserver.util;

public enum PageSites{
	
	HEADER("{header}","/tpl/header.tpl"),
	NAVIGATION("{navigation}","/tpl/navigation.tpl"),
	FOOTER("{footer}","/tpl/footer.tpl"),
	LOGIN("{login}","/tpl/login.tpl"),
	LOGOUT("{logout}","/tpl/logout.tpl"),
	PLAYERINFO("{playerinfo}","/tpl/playerinfo.tpl");
	//BODY("{body}","/tpl/body.tpl");
	
	
	String placeholder;
	String path;
	
	PageSites(String placeholder, String path){
		this.placeholder = placeholder;
		this.path = path;
	}
	
	public String getPlaceholder(){
		switch(this){
		case HEADER:
			return placeholder;
		case NAVIGATION:
			return placeholder;
		case FOOTER:
			return placeholder;
		case LOGIN:
			return placeholder;
		case LOGOUT:
			return placeholder;
		case PLAYERINFO:
			return placeholder;
		}		
		return "";
	}
	
	public String getPath(){
		switch(this){
		case HEADER:
			return path;
		case NAVIGATION:
			return path;
		case FOOTER:
			return path;
		case LOGIN:
			return path;
		case LOGOUT:
			return path;
		case PLAYERINFO:
			return path;
		}		
		return "";
	}











}