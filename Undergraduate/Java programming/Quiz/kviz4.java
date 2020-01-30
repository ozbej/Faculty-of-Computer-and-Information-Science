public class kviz4 {
    public static void main(String[] args){
    	statistikaStavkov("besedilo1.txt");
    }

    static void statistikaStavkov(String imeDatoteke){
    try{
	    	java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke));
			while (sc.hasNext()) {
			    System.out.println(sc.next());
			}
	}

	catch (java.io.FileNotFoundException fnfe) {
        System.out.println("Napaka pri branju datoteke.");
    }
	}
}
