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
	
	UtilClass utclass = new UtilClass();
	FunctionsClass functionClass = new FunctionsClass();
	SetLanguageClass langClass = new SetLanguageClass();
	CreateConfigClass configClass = new CreateConfigClass();
	
	@Override
	public void onEnable(){
		try{
			utclass.createLanguageFiles(this);
			configClass.createConfig(this);	
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
		Player player = null;Location pLoc = null;
		if(label.equalsIgnoreCase("home")){
			String filePath = "/data/";
			String fileName = "homelist";
			String fileType = "yml";
			File homeFile;
			homeFile = utclass.getFile(this, filePath, fileName, fileType);
			YamlConfiguration yamlFile = utclass.yamlCon(homeFile);
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
				langClass.setMessageLanguage(player, this);
			}
			if(args.length==0){
				functionClass.tpHome(pLoc, player, this);
				return true;
			}
			//##########################################################################################
			if(args.length==1){			
				if(args[0].equalsIgnoreCase("set")){
					if(args.length==1){
						functionClass.setMainHome(pLoc, player, this);
						return true;
					}
				}if(args[0].equalsIgnoreCase("list")){
					functionClass.listHome(this, player);
					return true;
				}else{
					try{
						functionClass.tpDiffHome(pLoc, player, this, args[0]);
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
						functionClass.delHome(player, args[1], this);
						player.sendMessage("Your home "+args[1]+" was deleted");
						return true;
					}catch (Exception e){
						player.sendMessage("Make sure that you have entered the correct home");
						return true;
					}
				}if(args[0].equalsIgnoreCase("set")){
					functionClass.setDiffHome(pLoc, player, this, args[0], args[1]);
					return true;
				}
			}
			//##########################################################################################
		}
		return false;
	}
}
