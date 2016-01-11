package de.xearox.httpserver.util;

public enum PageVariables {
	
	PLAYER_UUID("{player_uuid}","/tpl/header.tpl"),
	PLAYER_NAME("{player_name}","/tpl/navigation.tpl"),
	PLAYER_MONEY("{player_money}","/tpl/footer.tpl"),
	PLAYER_LASTLOGIN("{player_lastlogin}","/tpl/login.tpl"),
	PLAYER_IP("{player_ip}","/tpl/logout.tpl"),
	PLAYER_("{player_}","/tpl/playerinfo.tpl");
	//BODY("{body}","/tpl/body.tpl");
	
	
	String placeholder;
	String path;
	
	PageVariables(String placeholder, String path){
		this.placeholder = placeholder;
		this.path = path;
	}
	
	public String getPlaceholder(){
		switch(this){
		case PLAYER_UUID:
			return placeholder;
		case PLAYER_NAME:
			return placeholder;
		case PLAYER_MONEY:
			return placeholder;
		case PLAYER_LASTLOGIN:
			return placeholder;
		case PLAYER_IP:
			return placeholder;
		case PLAYER_:
			return placeholder;
		}		
		return "";
	}
	
	public String getPath(){
		switch(this){
		case PLAYER_UUID:
			return path;
		case PLAYER_NAME:
			return path;
		case PLAYER_MONEY:
			return path;
		case PLAYER_LASTLOGIN:
			return path;
		case PLAYER_IP:
			return path;
		case PLAYER_:
			return path;
		}		
		return "";
	}











}