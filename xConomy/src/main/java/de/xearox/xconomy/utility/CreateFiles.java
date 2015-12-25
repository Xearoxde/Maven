package de.xearox.xconomy.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import de.xearox.xconomy.XConomy;

public class CreateFiles {

	private XConomy plugin;
	
	public CreateFiles(XConomy plugin){
		this.plugin = plugin;
	}
	
	public boolean createConfigFile(){
		try{
			File configFile = new File(XConomy.directory+"/config", "config.yml");
			
			InputStream is = XConomy.class.getResourceAsStream("/resources/config/config.yml");
			
			if(is == null) 
				return false;
			
			if(configFile.exists()){
				return false;
			}
			
			FileOutputStream fos = null;
			
			try{
				fos = new FileOutputStream(configFile);
				byte[] buf = new byte[8192];
				int length = 0;
				
				while((length = is.read(buf)) > 0){
					fos.write(buf, 0, length);
				}
				plugin.logger.info("xConomy - INFO - Default config file written");
			}catch(Exception e){
				e.printStackTrace();
				return false;
			} finally{
				try{
					if(is != null){
						is.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
				try{
					if(fos != null){
						fos.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createLocatesFiles(){
		return false;
	}
	
	public boolean createReadmeFile(){
		try{
			File readmeFile = new File(XConomy.directory+"/config", "readme.txt");
			
			InputStream is = XConomy.class.getResourceAsStream("/resources/config/readme.txt");
			
			if(is == null) 
				return false;
			
			if(readmeFile.exists()){
				return false;
			}
			
			FileOutputStream fos = null;
			
			try{
				fos = new FileOutputStream(readmeFile);
				byte[] buf = new byte[8192];
				int length = 0;
				
				while((length = is.read(buf)) > 0){
					fos.write(buf, 0, length);
				}
				plugin.logger.info("xConomy - INFO - README file written");
			}catch(Exception e){
				e.printStackTrace();
				return false;
			} finally{
				try{
					if(is != null){
						is.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
				try{
					if(fos != null){
						fos.close();
					}
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean createPlayerTable(){
		return false;
	}
}
