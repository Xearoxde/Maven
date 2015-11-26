package de.xearox.xplugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin{
	
	private UtilClass utClass;
	private FunctionsClass functionClass;
	private SetLanguageClass langClass;
	private CreateConfigClass configClass;
	
	//Getter
	public UtilClass getUtilClass(){
		return utClass;
	}
	
	public FunctionsClass getFunctionClass(){
		return functionClass;
	}
	
	public SetLanguageClass getLangClass(){
		return langClass;
	}
	
	public CreateConfigClass configClass(){
		return configClass;
	}
	
	@Override
	public void onEnable(){
				
		try{
			this.utClass = new UtilClass(this);
		    this.functionClass = new FunctionsClass(this);
		    this.langClass = new SetLanguageClass(this);
		    this.configClass = new CreateConfigClass(this);
		    System.out.println("configClass= "+configClass);
			System.out.println("langClass= "+langClass);
			System.out.println("functionClass= "+functionClass);
			System.out.println("utClass= "+utClass);
			utClass.createLanguageFiles();
			configClass.createConfig();	
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable(){
		//
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){		
		Player player = null;
		Location pLoc = null;
		
		if(label.equalsIgnoreCase("home")){
			String filePath = "/data/";
			String fileName = "homelist";
			String fileType = "yml";
			File homeFile;
			homeFile = utClass.getFile(filePath, fileName, fileType);
			YamlConfiguration yamlFile = utClass.yamlCon(homeFile);
			if(!(sender instanceof Player)){
				if(args[0].equalsIgnoreCase("rl")){
					try {
						yamlFile.load(homeFile);
						System.out.println("HomeFile was reloaded");
						return true;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					} catch (InvalidConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return true;
					}
				}
			}else{
				player = (Player) sender; 
				pLoc = player.getLocation();
				langClass.setMessageLanguage(player);
			}
			if(args.length==0){
				functionClass.tpHome(pLoc, player);
				return true;
			}
			//##########################################################################################
			if(args.length==1){			
				if(args[0].equalsIgnoreCase("set")){
					if(args.length==1){
						functionClass.setMainHome(pLoc, player);
						return true;
					}
				}if(args[0].equalsIgnoreCase("list")){
					functionClass.listHome(player);
					return true;
				}else{
					try{
						functionClass.tpDiffHome(pLoc, player, args[0]);
						return true;
					}catch (Exception e){
						player.sendMessage("Your home was not found. Make sure you entered the correct home");
						return true;
					}
				}
			}
			//##########################################################################################
			if(args.length==2){
				if(args[0].equalsIgnoreCase("del")){				
					try{
						functionClass.delHome(player, args[1]);
						player.sendMessage("Your home "+args[1]+" was deleted");
						return true;
					}catch (Exception e){
						player.sendMessage("Make sure that you have entered the correct home");
						return true;
					}
				}if(args[0].equalsIgnoreCase("set")){
					functionClass.setDiffHome(pLoc, player, args[0], args[1]);
					return true;
				}
			}
			//##########################################################################################
		}
		return false;
	}
}
