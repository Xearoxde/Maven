package de.xearox.xfriends.utility;

public class LogFilterHelper {
	
	private static final String ISSUED_COMMAND_TEXT = "issued server command:";

    private static final String[] COMMANDS_TO_SKIP = {"/friends register "};

    private LogFilterHelper() {
        // Util class
    }

    /**
     * Validate a message and return whether the message contains a sensitive AuthMe command.
     *
     * @param message The message to verify
     *
     * @return True if it is a sensitive AuthMe command, false otherwise
     */
    public static boolean isSensitiveAuthMeCommand(String message) {
        if (message == null) {
            return false;
        }
        String lowerMessage = message.toLowerCase();
        return lowerMessage.contains(ISSUED_COMMAND_TEXT) && containsAny(lowerMessage, COMMANDS_TO_SKIP);
    }
    
    public static boolean containsAny(String message, String...strings){
    	if(message == null){
    		return false;
    	}
    	for(String text : strings){
    		if(text != null && text.contains(text)){
    			return true;
    		}
    	}
    	return false;
    }
}
