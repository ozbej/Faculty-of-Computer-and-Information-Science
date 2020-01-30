//import java.util.Comparator;
//import java.util.Arrays;

public class kviz3 {
    public static void main(String[] args){
    	//System.out.print(jeAnagram("ABC", "bca", true));
    	//preveriInNarisi(new int[]{1,3,0,2});
    	preberiInIzpisi("i1");
    }

        public static void preberiInIzpisi(String oznaka){
    	try{
    	java.util.Scanner sc_count = new java.util.Scanner(new java.io.File(oznaka+"-prijave.txt"));
    	int count = 0;
	    while(sc_count.hasNext())
		{
			String line = sc_count.nextLine();
			if (line.isEmpty()){}
			else{
			count++;
			}
		}
		String[][] redovalnica = new String[count][4];
		java.util.Scanner sc1 = new java.util.Scanner(new java.io.File(oznaka+"-prijave.txt"));
		int st = 0;
		while(sc1.hasNextLine()){
			String line = sc1.nextLine();
			if (line.isEmpty()){}
				else{
	            String[] parametri = line.split(":");
	            redovalnica[st][0] = parametri[0];
	            redovalnica[st][1] = parametri[1];
	            redovalnica[st][2] = "0";
	            redovalnica[st][3] = "0";
	            st++;
	        }
        }

        java.util.Scanner sc2 = new java.util.Scanner(new java.io.File(oznaka+"-n1.txt"));
        while(sc2.hasNextLine()){
        	String line = sc2.nextLine();
			if (line.isEmpty()){}
			else{
        	String[] parametri = line.split(":");
        	for (int i = 0; i < redovalnica.length; i++){
        		if (redovalnica[i][0] != null && redovalnica[i][0].equals(parametri[0])){
        			redovalnica[i][3] = "1";
        			redovalnica[i][2] = String.valueOf((Integer.parseInt(redovalnica[i][2]) + Integer.parseInt(parametri[1])));
        		}
        	}
        	}
        }

        java.util.Scanner sc3 = new java.util.Scanner(new java.io.File(oznaka+"-n2.txt"));
        while(sc3.hasNextLine()){
        	String line = sc3.nextLine();
			if (line.isEmpty()){}
			else{
        	String[] parametri = line.split(":");
        	for (int i = 0; i < redovalnica.length; i++){
        		if (redovalnica[i][0] != null && redovalnica[i][0].equals(parametri[0])){
        			redovalnica[i][3] = "1";
        			redovalnica[i][2] = String.valueOf((Integer.parseInt(redovalnica[i][2]) + Integer.parseInt(parametri[1])));
        		}
        	}
        	}
        }

        java.util.Scanner sc4 = new java.util.Scanner(new java.io.File(oznaka+"-n3.txt"));
        while(sc4.hasNextLine()){
        	String line = sc4.nextLine();
			if (line.isEmpty()){}
			else{
        	String[] parametri = line.split(":");
        	for (int i = 0; i < redovalnica.length; i++){
        		if (redovalnica[i][0] != null && redovalnica[i][0].equals(parametri[0])){
        			redovalnica[i][3] = "1";
        			redovalnica[i][2] = String.valueOf((Integer.parseInt(redovalnica[i][2]) + Integer.parseInt(parametri[1])));
        		}
        	}
        	}
        }

        java.util.Scanner sc5 = new java.util.Scanner(new java.io.File(oznaka+"-n4.txt"));
        while(sc5.hasNextLine()){
        	String line = sc5.nextLine();
			if (line.isEmpty()){}
			else{
        	String[] parametri = line.split(":");
        	for (int i = 0; i < redovalnica.length; i++){
        		if (redovalnica[i][0] != null && redovalnica[i][0].equals(parametri[0])){
        			redovalnica[i][3] = "1";
        			redovalnica[i][2] = String.valueOf((Integer.parseInt(redovalnica[i][2]) + Integer.parseInt(parametri[1])));
        		}
        	}
        	}
        }

        java.util.Arrays.sort(redovalnica, new java.util.Comparator<String[]>(){

    @Override
    public int compare(final String[] first, final String[] second){
        return (first[1]).compareTo(
            (second[1])
        );
    }
});

        for (int i = 0; i < redovalnica.length; i++){
        	if (redovalnica[i][3].equals("1"))
        		System.out.println(redovalnica[i][0] + ":" + redovalnica[i][1] + ":" + redovalnica[i][2]);
        	else
        		System.out.println(redovalnica[i][0] + ":" + redovalnica[i][1] + ":VP");
        }
    }
    catch (java.io.FileNotFoundException fnfe) {
        System.out.println(fnfe);
    	}
    }

    public static void preveriInNarisi(int[] kraljice){
    	int[][] deska = new int[kraljice.length][kraljice.length];
    	int j;
    	boolean napadajo = false;
    	for (int i = 0; i < kraljice.length; i++){
    		j = kraljice[i];
    		deska[j][i] = 1;
    	}
    	for (int i = 0; i < kraljice.length; i++){
    		for (int k = 0; k < kraljice.length; k++){
    			if (deska[i][k] == 1 && i > 0 && k > 0 && i < kraljice.length-1 && k < kraljice.length-1){
    				if (deska[i-1][k] == 1 || deska[i+1][k] == 1 || deska[i][k-1] == 1 || deska[i][k+1] == 1 || deska[i-1][k-1] == 1 || deska[i-1][k+1] == 1 || deska[i+1][k+1] == 1 || deska[i+1][k-1] == 1)
    					napadajo = true;
    			}
    			else if(deska[i][k] == 1 && i == 0 && k > 0){
    				if ( deska[i+1][k-1] == 1 || deska[i+1][k+1] == 1)
    					napadajo = true;
    			}
    			if (deska[i][k] != 1)
    				System.out.print(". ");
    			else
    				System.out.print("K ");
    		}
    		System.out.println("");
    	}
    	if (napadajo)
    		System.out.println("Kraljice se napadajo");
    	else
    		System.out.println("Kraljice se ne napadajo");
    }

    public static int[] sestejPolinoma(int[] a, int[] b){
    	int[] daljsi;
    	int[] krajsi;
    	if (a.length > b.length){
    		daljsi = a;
    		krajsi = b;
    	}
    	else{
    		daljsi = b;
    		krajsi = a;
    	}
    	for (int i = 0; i < daljsi.length; i++){
    		if (i < krajsi.length)
    		daljsi[i] = a[i] + b[i];
    	}
    	return daljsi;
    }

    public static boolean jeAnagram(String prvaBeseda, String drugaBeseda, boolean zanemariVelikost){
    	if (zanemariVelikost){
    		prvaBeseda = prvaBeseda.toLowerCase();
    		drugaBeseda = drugaBeseda.toLowerCase();
    	}

    	if(prvaBeseda.length() != drugaBeseda.length())
    		return false;
    	Character[] array1 = new Character[prvaBeseda.length()];
	   	for (int i = 0; i < prvaBeseda.length() ; i++){
	    	array1[i] = new Character(prvaBeseda.charAt(i));
   		}

   		Character[] array2 = new Character[drugaBeseda.length()];
	   	for (int i = 0; i < drugaBeseda.length() ; i++){
	    	array2[i] = new Character(drugaBeseda.charAt(i));
   		}

   		java.util.Arrays.sort(array1); 
        java.util.Arrays.sort(array2); 

        for (int i = 0; i < prvaBeseda.length(); i++) 
            if (!array1[i].equals(array2[i])) 
                return false; 
  
        return true; 
    }
}

class Tocka{
	int x;
	int y;

	Tocka(int x, int y){
		this.x = x;
		this.y = y;
	}

	public static Tocka[] preberiTocke(String imeDatoteke){
		try{
			Tocka[] tocke = new Tocka[100];
	        java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke));
	        java.util.Scanner sc2 = new java.util.Scanner(new java.io.File(imeDatoteke));
	        int count = 0;
	        while(sc2.hasNext())
		    {
		      sc2.next();
		      count++;
		    }
    		if (count == 0)
    			return tocke;
	        int i = 0;
	        if (!sc.hasNextLine())
	        	return new Tocka[0];
	        while(sc.hasNextLine()){
	            tocke[i++] = new Tocka(sc.nextInt(), sc.nextInt());
	        }
	        return tocke;
    	}
    	catch (java.io.FileNotFoundException fnfe) {
        System.out.println(fnfe);
    	}
    	return null;
	}

	public static String tabelaToString(Tocka[] tocke){
		StringBuffer buff = new StringBuffer("[");
		for (int i = 0; i < tocke.length; i++){
			if (tocke[i] != null)
				buff.append("("+tocke[i].x+","+tocke[i].y+"), ");
		}
		if (buff.length() > 2){
			buff.deleteCharAt(buff.length()-1);
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		return buff.toString();
	}

	static void najblizji(Tocka[] t1, Tocka[] t2){
		double najblizji = 10000;
		int[] najT1 = new int[2];
		int[] najT2 = new int[2];
		double x = 0;
		double y = 0;
		if (t2[0] == null)
			System.out.println("Druga tabela ne vsebuje tock");
		else{
			for (int i = 0; i < t1.length; i++){
				for (int j = 0; j < t2.length; j++){
					if (t1[i] != null && t2[j] != null){
						x = Math.pow(t2[j].x-t1[i].x, 2);
						y = Math.pow(t2[j].y-t1[i].y, 2);
						if (Math.sqrt(x+y) <= najblizji){
							najT1[0] = t1[i].x;
							najT1[1] = t1[i].y;
							najT2[0] = t2[j].x;
							najT2[1] = t2[j].y;
							najblizji = Math.sqrt(x+y);
						}
					}
				}
			}
			System.out.printf("Najbližji tocki sta Tocka (" + najT1[0] + ", " + najT1[1] + ") in Tocka (" + najT2[0] + ", " + najT2[1] + "), razdalja med njima je " + "%.2f", najblizji);
		}
	}
}

class Kompleksno{
	private String ime;
	private double realni;
	private double imaginarni;

	Kompleksno(String ime, double realni, double imaginarni){
		this.ime = ime;
		this.realni = realni;
		this.imaginarni = imaginarni;
	}

}