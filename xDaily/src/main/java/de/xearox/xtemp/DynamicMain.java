package de.xearox.xtemp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class DynamicMain {

	public static void main(String[] args) {
		
		if(args.length == 0){
			System.exit(0);
		}
		
		double[] doubleArray = new double[args.length];
		int x = 0;
		for(String input: args){
			doubleArray[x] = Double.parseDouble(input)/100;
			x++;
		}
		
		Arrays.sort(doubleArray);
		
		HashSet<Double> set = new HashSet<Double>();
		
		ArrayList<Double> doubleList = new ArrayList<Double>();
		ArrayList<Integer> intList = new ArrayList<Integer>();
		
		for(double dA : doubleArray){
			doubleList.add(dA);
		}
		
		for(int i = 0; i < doubleArray.length;i++){
			//System.out.println(dA/10);
			if(set.add(doubleArray[i]) == false){
				intList.add(i);
			}
		}
		for(int i = 0; i < 10; i++){
			System.out.println(distributeActions(doubleArray));
		}
	}
	
	public static int distributeActions(double[] distribution) {
		/* 
		 * liefert eine zufällige Ereignisnummer
		 * gemäß der in doubleArray gegebenen 
		 * Wahrscheinlichkeitsverteilung
		 * Die Werte in doubleArray müssen zusammen
		 * kleiner als 1 sein
		 * Anm: 
		 * doubleArray sollte besser distribution heißen
		 */ 
		double rnd = Math.random();
		double rndCnt = 0.0;
		int elseWert = distribution.length;
		for (int i=0; i < distribution.length; i++) {
			rndCnt += distribution[i];
			if (rnd < rndCnt) {
				//System.out.println(i); // ggf weglassen
				return i;
			}
		}
		return elseWert;
	}
	
	

}
