package de.xearox.xdaily.utilz;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.xearox.xdaily.XDaily;

public class RandomItem {
	
	private XDaily plugin;
	private Map<ItemStack, Double> itemMap;
	private String pluginPath;
	private Utilz utilz;
	
	public RandomItem(XDaily plugin) {
		this.plugin = plugin;
		this.itemMap = new LinkedHashMap<ItemStack, Double>();
		this.pluginPath = plugin.getDataFolder().getAbsolutePath();
		this.utilz = plugin.getUtilz();
	}
	
	public ItemStack mainFunction(){
		if((itemMap = readFromFile()) == null){
			return null;
		}
		double[] doubleArray = new double[itemMap.size()];
		Object[] itemObject = itemMap.values().toArray();
		int choosenNumber = 0;
		
		doubleArray = convertToDouble(itemObject);
		Arrays.sort(doubleArray);
		
		choosenNumber = distributeActions(doubleArray);
		
		Map<Double, ArrayList<ItemStack>> groupedMap = sortMapInGroups(itemMap);
		ArrayList<ItemStack> itemStackList = groupedMap.get(doubleArray[choosenNumber]);
		
		if(itemStackList.size() > 1){
			Random generator = new Random();
			int i = generator.nextInt(itemStackList.size());
			return itemStackList.get(i);
		}
		
		return itemStackList.get(0);
	}
	
	public Map<ItemStack, Double> readFromFile(){
		File file = new File(this.pluginPath+File.separator+"/data/randomItemList.txt");
		ArrayList<String> fileContent = new ArrayList<String>();
		Map<ItemStack, Double> resultMap = new LinkedHashMap<ItemStack, Double>();
		
		if(!file.exists()){
			return null;
		}
		
		fileContent = utilz.readFileByLine(file);
		
		for(int i = 0; i < fileContent.size(); i++){
			String splitedList[];
			splitedList = fileContent.get(i).split(";");
			
			//splitedList[1] = Material Name
			ItemStack resultItemStack = new ItemStack(Material.getMaterial(splitedList[1]));
			
			//splitedList[2] = Amount of Items
			resultItemStack.setAmount(Integer.parseInt(splitedList[2]));
			
			//splitedList[0] = Double - e.g. 0.5 for 50% chance to drop this ItemStack
			resultMap.put(resultItemStack, Double.valueOf(splitedList[0]));
		}
		
		return resultMap;
	}
	
	public double[] convertToDouble(Object[] objectArray){
		double[] doubleArray = new double[objectArray.length];
		for (int i = 0; i < objectArray.length; i++)
		{
			doubleArray[i] = new Double((double) objectArray[i]);
		}
		return doubleArray;
	}
	
	public static int distributeActions(double[] distribution) {
		double rnd = Math.random();
		double rndCnt = 0.0;
		int elseWert = distribution.length; 
		for (int i=0; i < distribution .length; i++) {
			rndCnt += distribution[i];
			if (rnd < rndCnt) {
				return i;
			}
		}
		return elseWert;
	}
	
	public Map<Double, ArrayList<ItemStack>> sortMapInGroups(Map<ItemStack, Double> inputMap){
		Map<Double, ArrayList<ItemStack>> reverseMap = new HashMap<>();

		for (Map.Entry<ItemStack,Double> entry : inputMap.entrySet()) {
		    if (!reverseMap.containsKey(entry.getValue())) {
		        reverseMap.put(entry.getValue(), new ArrayList<>());
		    }
		    ArrayList<ItemStack> keys = reverseMap.get(entry.getValue());
		    keys.add(entry.getKey());
		    reverseMap.put(entry.getValue(), keys);
		}
		return reverseMap;
	}
	
	
}
