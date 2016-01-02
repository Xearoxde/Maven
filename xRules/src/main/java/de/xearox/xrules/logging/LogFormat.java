package de.xearox.xrules.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormat extends Formatter{
	public String format(LogRecord record){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("[dd.MM.yyyy - HH:mm:ss] ");
		String time = sdf.format(cal.getTime());
		StringBuilder builder = new StringBuilder();
		Level level = record.getLevel();
		builder.append(time);
		if(level == Level.FINEST){
			builder.append("[FINEST]");
		} else if(level == Level.FINER){
			builder.append("[FINER]");
		} else if(level == Level.FINE){
			builder.append("[FINE]");
		} else if(level == Level.INFO){
			builder.append("[INFO]");
		} else if(level == Level.WARNING){
			builder.append("[WARNING]");
		} else if(level == Level.SEVERE){
			builder.append("[SEVERE]");
		}
		builder.append(record.getMessage());
		builder.append("\r\n");
		
		Throwable throwable = record.getThrown();
		if(throwable != null){
			StringWriter writer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(writer));
			builder.append(writer);
		}
		return builder.toString();
	}
}
