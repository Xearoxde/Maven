package de.xearox.xdaily.utilz;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.xearox.xdaily.XDaily;

public class PermissionGroups {
	
	private XDaily plugin;
	
	public PermissionGroups(XDaily plugin) {
		this.plugin = plugin;
	}
	
	
	public void writePermGroups(){
		if(XDaily.perm == null){
			return;
		}
		
		String permGroups[] = XDaily.perm.getGroups();
		String pluginDirPath = plugin.getDataFolder().getPath()+File.separator;
		
		
		File dir = new File(pluginDirPath+"/data/permGroups/");
		
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File file = new File(pluginDirPath+"/data/permGroups/groups.yml");		
		YamlConfiguration yamlFile;
		yamlFile = YamlConfiguration.loadConfiguration(file);
		
		for(int i = 0; i < permGroups.length; i++){
			yamlFile.addDefault(permGroups[i]+".CanUseMulti?", false);
			yamlFile.addDefault(permGroups[i]+".Multiplier", 1);
		}
		
		yamlFile.options().copyDefaults(true);
		
		try {
			yamlFile.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
