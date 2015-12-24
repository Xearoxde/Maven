package de.xearox.xhome;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.codec.digest.DigestUtils;
import org.bukkit.Bukkit;

import com.google.common.io.Files;

public class checkUpdates {

	private MainClass plugin;

	public checkUpdates(MainClass plugin){
		this.plugin = plugin;
	}
	
	public boolean checkForUpdates(){
		if(plugin.getConfigFile().getBoolean("Config.Update.download")){
			downloadPlugin();
		}else{
			return false;
		}
		String rootDir;
		String localFileMD5;
		String newFileMD5;
		String localJarName;
		rootDir = plugin.getServer().getWorldContainer().getAbsolutePath();
		rootDir = rootDir.substring(0, rootDir.length() -1);
		try{
			localJarName = Bukkit.getPluginManager().getPlugin("xHome").getClass().getProtectionDomain().getCodeSource().getLocation().toString();
			localJarName = localJarName.replace("file:/", "");
			FileInputStream localFile = new FileInputStream(new File(localJarName));
			FileInputStream newFile = new FileInputStream(new File(plugin.getDataFolder()+"/download/xHome.jar"));
			
			localFileMD5 = DigestUtils.md5Hex(localFile);
			newFileMD5 = DigestUtils.md5Hex(newFile);
			
			if(localFileMD5.equals(newFileMD5)){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean checkForUpdatesCMD(){
		downloadPlugin();
		String rootDir;
		String localFileMD5;
		String newFileMD5;
		rootDir = plugin.getServer().getWorldContainer().getAbsolutePath();
		rootDir = rootDir.substring(0, rootDir.length() -1);
		try{
			FileInputStream localFile = new FileInputStream(new File(rootDir+"plugins/xHome.jar"));
			FileInputStream newFile = new FileInputStream(new File(plugin.getDataFolder()+"/download/xHome.jar"));
			
			localFileMD5 = DigestUtils.md5Hex(localFile);
			newFileMD5 = DigestUtils.md5Hex(newFile);
			
			if(localFileMD5.equals(newFileMD5)){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("resource")
	public void downloadPlugin(){
		plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{	
					ReadableByteChannel in = null;
					if(plugin.getConfigFile().getString("Config.Update.version").equalsIgnoreCase(Versions.stable.toString())){
						in=Channels.newChannel(new URL("http://minecraft.xearox.de/data/documents/stable/xHome.jar").openStream());
					}else if(plugin.getConfigFile().getString("Config.Update.version").equalsIgnoreCase(Versions.devbuild.toString())){
						in=Channels.newChannel(new URL("http://minecraft.xearox.de/data/documents/devbuild/xHome.jar").openStream());
					}else if(plugin.getConfigFile().getString("Config.Update.version").equalsIgnoreCase(Versions.lastbuild.toString())){
						in=Channels.newChannel(new URL("http://minecraft.xearox.de/data/documents/lastbuild/xHome.jar").openStream());
					}else if(plugin.getConfigFile().getString("Config.Update.version").equalsIgnoreCase(Versions.snapshot.toString())){
						in=Channels.newChannel(new URL("http://minecraft.xearox.de/data/documents/snapshot/xHome.jar").openStream());
					}					
					FileChannel out=new FileOutputStream(plugin.getDataFolder()+"/download/xHome.jar").getChannel();	
						out.transferFrom(in, 0, Long.MAX_VALUE);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
		});
	}
	
	public void createDownloadFolder(){
		String path = plugin.getDataFolder().toString();
		String dirName = "/download";
		try{
			File dir = new File(path + dirName);
			if(!dir.exists()){
				dir.mkdir();
				System.out.println("xHome - INFO - "+dir+" created!");
			}else{
				System.out.println("xHome - INFO - Download Folder already exist");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean applyUpdate(){
		String newFile = plugin.getDataFolder()+"/download/xHome.jar";
		String oldFile = plugin.getServer().getWorldContainer().getAbsolutePath();
		oldFile = oldFile.substring(0, oldFile.length() -1);
		oldFile = oldFile + "/plugins/xHome.jar";
		File toFile = new File(oldFile);
		File fromFile = new File(newFile);
		
		try{
			Files.copy(fromFile, toFile);
			return true;
		}catch(Exception e){
			return false;
		}		
	}






}
