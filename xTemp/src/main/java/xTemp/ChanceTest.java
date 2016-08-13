package xTemp;

import java.util.Random;

public class ChanceTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i<10; i++){
			chance(90.0D);
		}
	}

	public static void chance(double c) {
		double d = new Random().nextDouble() * 100.0D;
		if (d <= c) {
			System.out.println("true");
			System.out.println(d);
		} else {
			System.out.println("false");
			System.out.println(d);
		}
		return;
	}

}
