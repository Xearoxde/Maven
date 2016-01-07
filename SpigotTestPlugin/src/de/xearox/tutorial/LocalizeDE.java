package de.xearox.tutorial;

public class LocalizeDE {
	
	public String TranslateToGerman(String LocalString){
		switch (LocalString){
			case "diamant":
				return "diamond";
			
			case "eichenholz"	:return "oak_wood";
			
			case "kohle"		:return "coal";
			
			case "eisenerz"		:return "iron_ore";
			
			case "eisenbarren"	:return "iron_ingot";
			
			case "stein"		:return "stone";
			
			case "bruchstein"	:return "cobblestone";
			
			case "sand"			:return "sand";
			
			case "apfel"		:return "apple";
			
			case "goldbarren"	:return "gold_ingot";
			
			case "golderz"		:return	"gold_ore";
			
			case "dreck"		:return "dirt";
			
			
		}
		
		
		return "cobblestone";
	}
	
	
}
