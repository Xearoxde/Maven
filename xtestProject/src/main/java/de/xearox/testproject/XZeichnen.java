package de.xearox.testproject;

public class XZeichnen {

	public static void main(String[] args) {

	    int höhe = 5;
	    int höhe2 = höhe / 2;
	    int shift = höhe % 2; // 0 der 1, je nach gerade/ungerade

	    for (int i = 1; i <= höhe; i++) {
	        int k = i > höhe2 ? höhe - i + 1 : i;
	        // k zur Untersuchung am besten mit System.out.println am Ende der Zeile loggen, 
	        // zählt anfangs hoch wie i, ab der Mitte wieder bis auf 1 runter 
	        int abstand = höhe2 - k;
	        for (int j = 1; j <= k-1; j++) {
		        System.out.print(' ');
		    }
	        System.out.print("*");
	        if (abstand >= 0) {
	            for (int j = 1; j <= abstand * 2 + shift; j++) {
			        System.out.print(' ');
			    }
	            System.out.print("* ");
	        } // wenn nicht, dann ist gerade die Mitte dran
	        System.out.println();
	    }
	}

}
