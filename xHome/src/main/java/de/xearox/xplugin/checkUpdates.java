package de.xearox.xplugin;

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

import com.google.common.io.Files;

public class checkUpdates {

	private MainClass plugin;

	public checkUpdates(MainClass plugin){
		this.plugin = plugin;
	}
	
	public boolean checkForUpdates(){
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
			
			System.out.println(localFileMD5);
			System.out.println(newFileMD5);
			
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
	
	private void downloadPlugin(){
		try(
				ReadableByteChannel in=Channels.newChannel(
						new URL("http://minecraft.xearox.de/data/documents/xHome.jar").openStream());
				FileChannel out=new FileOutputStream(
						plugin.getDataFolder()+"/download/xHome.jar").getChannel() ) {
	
			  out.transferFrom(in, 0, Long.MAX_VALUE);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
