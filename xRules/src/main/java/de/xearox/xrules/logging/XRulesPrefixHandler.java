package de.xearox.xrules.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class XRulesPrefixHandler extends Handler{

	@Override
	public void publish(LogRecord record) {
		// TODO Auto-generated method stub
		String message = record.getMessage();
		if((!message.startsWith("xRules: ")) && (!message.startsWith("[xRules] "))){
			record.setMessage("[xRules] "+message);
		}
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}
	
	public static void register(String name){
		Logger.getLogger(name).addHandler(new XRulesPrefixHandler());
	}
}
