package de.xearox.xfactory.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.xearox.xfactory.XFactory;

public class Utility {
	
	private XFactory plugin;
	
	public Utility(XFactory plugin) {
		this.plugin = plugin;
	}
	
	public String createBuildingConfig(String buildingName, Player player){
		String pluginPath = plugin.getDataFolder().getPath();
		File dir = new File(pluginPath+File.separator+"/buildings/"+buildingName+"/");
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir.getPath()+File.separator+"/"+buildingName+".yml");
		if(file.exists()){
			return "BuildingExists";
		}
		YamlConfiguration yamlConf = YamlConfiguration.loadConfiguration(file);
		try{
			yamlConf.addDefault("building.name", buildingName);
			yamlConf.addDefault("building.type", "Choose a building type");
			yamlConf.addDefault("building.owner.name", player.getName());
			yamlConf.addDefault("building.owner.uuid", player.getUniqueId().toString());
			yamlConf.addDefault("building.otherOwners", "set;here;more;owners;");
			yamlConf.addDefault("building.startlevel", 1);
			yamlConf.addDefault("building.maxlevel", 10);
			yamlConf.addDefault("building.workers.baseCost", 0);
			yamlConf.addDefault("building.buildSpeed", 5L);
			yamlConf.addDefault("building.hourlyCost.baseCost", 0);
			yamlConf.addDefault("building.hourlyCost.costMultiplier", 0);
			yamlConf.addDefault("building.hourlyIncome.baseIncome", 0);
			yamlConf.addDefault("building.hourlyIncome.incomeMultiplier", 0);
			yamlConf.addDefault("building.constructing.useXMLFileForUpgrades?", false);
			yamlConf.addDefault("building.constructing.baseCost", 1000);
			yamlConf.addDefault("building.constructing.upgrade.useFormula?", false);
			yamlConf.addDefault("building.constructing.upgrade.upgradeFormula", "(%basecost% * ((%level% + 1) * 1.5 ^ %level%))");
			yamlConf.addDefault("building.constructing.upgrade.costMultiplier", 1.1);
			yamlConf.options().copyDefaults(true);
			yamlConf.save(file);
			return "Created";
		} catch(Exception e){
			e.printStackTrace();
			return "Error";
		}
	}
	
	public byte[] getBytesFromObject(Object object){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		byte[] yourBytes = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(object);
		  yourBytes = bos.toByteArray();
		  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}
		if(yourBytes != null){
			return yourBytes;
		} else {
			return null;
		}
	}
}
