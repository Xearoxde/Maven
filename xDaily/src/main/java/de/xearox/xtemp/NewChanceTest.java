package de.xearox.xtemp;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewChanceTest {
	static Map<Double, String> items = new LinkedHashMap<Double, String>();
	
	
	
	public static void main(String[] args) {
		items.put(5D, "Iron");
		items.put(13D, "Wood");
		items.put(17D, "Gold");
		items.put(12D, "Diamond");
		items.put(11D, "Stone");
		items.put(7D, "Copper");
		items.put(4D, "Tin");
		items.put(1D, "Lead");
		items.put(100D, "Coal");
		
		double[] doubleArray = new double[items.size()];
		Object[] itemObject = items.keySet().toArray(); 
		
		doubleArray = convertToDouble(itemObject);
		
		Arrays.sort(doubleArray);
		
		for(int i = 0; i < 20; i++){
			System.out.println("Index = "+items.get(doubleArray[distributeActions(doubleArray)]));
		}
	}
	
	public static int distributeActions(double[] distribution) {
		  double rnd = Math.random();
		  double rndCnt = 0.0;
		  int elseWert = distribution.length;
		  
		  for (int i=0; i < distribution .length; i++) {
		    rndCnt += distribution[i]/100;
		    if (rnd < rndCnt) {
		      return i;
		    }
		  }
		  return elseWert;
		}
	
	public static double[] convertToDouble(Object[] objectArray){
		double[] doubleArray = new double[objectArray.length];
		for (int i = 0; i < objectArray.length; i++)
		{
			doubleArray[i] = new Double((double) objectArray[i]);
		}
		return doubleArray;
	}

}
