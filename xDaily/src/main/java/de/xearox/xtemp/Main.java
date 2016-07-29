package de.xearox.xtemp;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main {

	public static void main(String[] args) {
		int onePercent = 0;
		int twoPercent = 0;
		int threePercent = 0;
		int anyPercent = 0;
		int runs = 0;
		int times = 0;
		if(args.length == 0){
			times = 2000000000;
		} else {
			times = Integer.parseInt(args[0]);
		}
		long startTime = System.currentTimeMillis();
		for(int i = 0; i<times;i++){
			double d = Math.random();
			if (d < 0.30){
				onePercent++;
			} else if (d < 0.60){
				twoPercent++;
			}
			runs++;
			if((runs % (times/10)) == 0){
				System.out.println("runs :" + runs);
			}
		}
		long endTime   = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.println("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
		System.out.println("30% Chance dropped "+onePercent+" times! " +onePercent+"/"+runs);
		System.out.println("60% Chance dropped "+twoPercent+" times! " +twoPercent+"/"+runs);
		/*System.out.println("3% Chance dropped "+threePercent+" times! " +threePercent+"/"+runs);
		System.out.println("90% Chance dropped "+anyPercent+" times! " +anyPercent+"/"+runs);*/
	}
}
