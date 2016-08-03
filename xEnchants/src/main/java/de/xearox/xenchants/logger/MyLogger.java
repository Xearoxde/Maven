package de.xearox.xenchants.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.common.io.Files;

import de.xearox.xenchants.XEnchants;

public class MyLogger {
	
	private XEnchants plugin;
	private File logFile;
	private File logDir;
	private String logFilePath;
	private String logFileName;
	
	
	public MyLogger(XEnchants plugin) {
		this.plugin = plugin;
		this.logFilePath = plugin.getDataFolder()+File.separator+"/log/";
	}
	
	public void createLogFile(LogLevel logLevel, String input){
		String date;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
		date = sdf.format(Calendar.getInstance().getTime());
		logFileName = logFilePath+"/log_"+date+".log";
		logFile = new File(logFileName);
		logDir = new File(logFilePath);
		if(!logDir.exists()){
			logDir.mkdirs();
		}
		if(logFile.length() >= 1024L){
			int i = 1;
			File oldFile = new File(logFilePath+"/"+logFile.getName()+i+".bak");
			while(oldFile.exists()){
				i++;
				oldFile = new File(logFilePath+"/"+logFile.getName()+i+".bak");
			}
			logFile.renameTo(oldFile);
		}
		logFile = new File(logFileName);
		try {
			Writer writer = new BufferedWriter(new FileWriter(logFile.getAbsolutePath(), true));
			if(logLevel == LogLevel.INFO){
				writer.write(setInfoMessage(input));
				writer.write(System.lineSeparator());
			}
			if(logLevel == LogLevel.WARN){
				writer.write(setWarnMessage(input));
				writer.write(System.lineSeparator());
			}
			if(logLevel == LogLevel.ERR){
				writer.write(setErrMessage(input));
				writer.write(System.lineSeparator());
			}
			if(logLevel == LogLevel.CRIT){
				writer.write(setCritMessage(input));
				writer.write(System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String setInfoMessage(String input){
		String editedText;
		editedText = "[INFO]["+getTime()+"]"+input;
		return editedText;
	}
	
	public String setWarnMessage(String input){
		String editedText;
		editedText = "[WARN]["+getTime()+"]"+input;
		return editedText;
	}
	
	public String setErrMessage(String input){
		String editedText;
		editedText = "[ERROR]["+getTime()+"]"+input;
		return editedText;
	}
	
	public String setCritMessage(String input){
		String editedText;
		editedText = "[CRITICAL]["+getTime()+"]"+input;
		return editedText;
	}
	
	
	public String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
