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
			File configDir = new File(XConomy.directory+"/config");
			File configFile = new File(configDir, "config.yml");
			
			if(!configDir.exists()){
				configDir.mkdir();
			}
			
			InputStream is = XConomy.class.getResourceAsStream("/config/config.yml");
			
			if(is == null){
				plugin.logger.warning("xConomy - WARNING - Internal config file was not found");
				return false;
			}
			
			if(configFile.exists()){
				plugin.logger.info("xConomy - INFO - Config file already exist");
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
			File readmeDir = new File(XConomy.directory+"/config");
			File readmeFile = new File(readmeDir, "readme.txt");
			
			if(!readmeDir.exists()){
				readmeDir.mkdir();
			}
			
			InputStream is = XConomy.class.getResourceAsStream("/config/readme.txt");
			
			if(is == null){
				plugin.logger.warning("xConomy - WARNING - Internal README file was not found");
				return false;
			}
			
			if(readmeFile.exists()){
				plugin.logger.info("xConomy - INFO - README file already exist");
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
