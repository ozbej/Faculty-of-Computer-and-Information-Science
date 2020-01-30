public class kviz2 {
    public static void main(String[] args){
    	int[] a = {3,6,9,1,3,5};
    	int[] b = {3,4,6,1,3,8};
    	//range(5, 10, 5);
    	presek(a, b);

    	int[] c = {1, 2, 3, 4, 5, 6};
    	//rotiraj(c, 1);

    	//int[] d = {1,4,3,2,56,4,3,2,6,3,1};
        //duplikati(d);

        //koren(9, 0);

        //System.out.println(vsotaSkupnihCifer(1234512343, 134616130));
        //odpleti("KDaokbor os,i  hkvaajl ad!a n e s ? ");

        //vMorse("sos");

        //fibo(3);
        izpisKoledarja(1999, 9);
    }

    public static int[] unija(int[] tabela1, int[] tabela2){
    	int[] unija = new int[tabela1.length + tabela2.length];
    	for (int i = 0; i < tabela1.length; i++)
    		unija[i] = tabela1[i];
    	int st = 0;
    	for (int i = tabela1.length; i < tabela1.length + tabela2.length; i++){
    		unija[i] = tabela2[st];
    		st++;
    	}
    	return unija;
    }

    public static int[] presek(int[] tabela1, int[] tabela2){
        StringBuffer pres = new StringBuffer();
        int st = 0;
    	for(int i = 0; i<tabela1.length; i++ ) {
            for(int j = 0; j<tabela2.length; j++) {
                if(tabela1[i]==tabela2[j]) {
                    if (pres.indexOf(String.valueOf(tabela2[j])) == -1)
                        pres.append(String.valueOf(tabela2[j]) + ", ");
                }
            }
        }
        String[] splitArray = pres.toString().split(", ");
        int[] presek = new int[splitArray.length];
        for (int i = 0; i < splitArray.length; i++){
            presek[i] = Integer.parseInt(splitArray[i]);
        }
        return presek;
    }

    public static int[] range(int a, int b, int c){
    	int[] range = new int[(b-a)/c];
	    int x = 0;
	    while(x < range.length){
	    	range[x] = a + x*c;
	    	System.out.print(range[x] + " ");
	    	x++;
	    }
	    return range;
    }

    public static void rotiraj(int [] tabela, int k){
    	int[] tabela2 = new int[tabela.length];
    	for(int i = 0; i < tabela.length; i++){
    		if (i+k >= tabela.length)
    			tabela2[i] = tabela[-1+k];
    		else
    			tabela2[i] = tabela[i+k];
    	}
    	tabela = tabela2;
    	for (int i = 0; i < tabela.length; i++)
    		System.out.print(tabela[i] + " ");
    }

    public static void duplikati(int[] tabela){
        int st = 0;
        boolean found;
        for (int i = 1; i < tabela.length; i++){
            found = false;
            for (int j = i-1; j >= 0; j--){
                if (tabela[i] == tabela[j]){
                    found = true;
                    break;
                }         
            }
            if (!found){
                System.out.print(tabela[i] + ", ");
                st++;
            }
        }
        System.out.print(st);       
    }

    public static void koren(int x, int d){
        int c = 1;
        int c1 = 0;
        while (c <= x/c)
            c++;
        c--;
        System.out.print(c + ",");
        for(int i = 0; i < 10; i++){
            c1 = c+i/10;
            if (c1 <= x/c1){
                System.out.print(c1);
                break; 
            }         
        }
    }

    public static int vsotaSkupnihCifer(int a, int b){
        String krajsa;
        String daljsa;
        int vsota = 0;
        StringBuffer porabljene = new StringBuffer();
        if (a < b)
        {
            krajsa = String.valueOf(a);
            daljsa = String.valueOf(b);
        }
        else{
            krajsa = String.valueOf(b);
            daljsa = String.valueOf(a);
        }
        for (int i = 0; i < krajsa.length(); i++){
            if (daljsa.contains(String.valueOf(krajsa.charAt(i))) && porabljene.indexOf(String.valueOf(krajsa.charAt(i))) == -1){
                vsota += Integer.parseInt(String.valueOf(krajsa.charAt(i)));
                porabljene.append(krajsa.charAt(i));
            }
        }
        return vsota;
    }

    public static String prepleti(String niz1, String niz2){
        String daljsa;
        String krajsa;
        String a;
        String b;
        StringBuffer prepleteno = new StringBuffer();
        if (niz1.length() < niz2.length())
        {
            krajsa = String.valueOf(niz1);
            daljsa = String.valueOf(niz2);
        }
        else{
            krajsa = String.valueOf(niz2);
            daljsa = String.valueOf(niz1);
        }
        for (int i = 0; i < daljsa.length(); i++){
            if (i >= krajsa.length()){
                if (krajsa.equals(niz1)){
                    a = " ";
                    b = String.valueOf(niz2.charAt(i));
                }
                else{
                    a = String.valueOf(niz1.charAt(i));
                    b = " ";
                }
            }
            else{
                a = String.valueOf(niz1.charAt(i));
                b = String.valueOf(niz2.charAt(i));
            }
            prepleteno.append(a + b);
        }
        return prepleteno.toString();
    }

    public static void odpleti(String niz){
        StringBuffer niz1 = new StringBuffer();
        StringBuffer niz2 = new StringBuffer();
        for (int i = 0; i < niz.length(); i++){
            if (i%2 == 0)
                niz1.append(niz.charAt(i));
            else if (i%2 == 1)
                niz2.append(niz.charAt(i));
        }
        System.out.println(niz1);
        System.out.println(niz2);
    }

    public static String vMorse(String niz){
        StringBuffer morse = new StringBuffer();
        String crka;
        for (int i = 0; i < niz.length(); i++){
            crka = String.valueOf(niz.charAt(i)).toUpperCase();
            switch(crka) {
                case "A":
                    morse.append(".- ");
                    break;
                case "B":
                    morse.append("-... ");
                    break;
                case "C":
                    morse.append("-.-. ");
                    break;
                case "D":
                    morse.append("-.. ");
                    break;
                case "E":
                    morse.append(". ");
                    break;
                case "F":
                    morse.append("..-. ");
                    break;
                case "G":
                    morse.append("--. ");
                    break;
                case "H":
                    morse.append(".... ");
                    break;
                case "I":
                    morse.append(".. ");
                    break;
                case "J":
                    morse.append(".--- ");
                    break;
                case "K":
                    morse.append("-.- ");
                    break;
                case "L":
                    morse.append(".-.. ");
                    break;
                case "M":
                    morse.append("-- ");
                    break;
                case "N":
                    morse.append("-. ");
                    break;
                case "O":
                    morse.append("--- ");
                    break;
                case "P":
                    morse.append(".--. ");
                    break;
                case "R":
                    morse.append(".-. ");
                    break;
                case "Q":
                    morse.append("--.- ");
                    break;
                case "S":
                    morse.append("... ");
                    break;
                case "T":
                    morse.append("- ");
                    break;
                case "U":
                    morse.append("..- ");
                    break;
                case "V":
                    morse.append("...- ");
                    break;
                case "W":
                    morse.append(".--");
                    break;
                case "X":
                    morse.append("-..- ");
                    break;
                case "Y":
                    morse.append("-.-- ");
                    break;
                case "Z":
                    morse.append("--.. ");
                    break;
                case "0":
                    morse.append("----- ");
                    break;
                case "1":
                    morse.append(".---- ");
                    break;
                case "2":
                    morse.append("..--- ");
                    break;
                case "3":
                    morse.append("...-- ");
                    break;
                case "4":
                    morse.append("....- ");
                    break;
                case "5":
                    morse.append("..... ");
                    break;
                case "6":
                    morse.append("-.... ");
                    break;
                case "7":
                    morse.append("--... ");
                    break;
                case "8":
                    morse.append("---.. ");
                    break;
                case "9":
                    morse.append("----. ");
                    break;
                case " ":
                    morse.append(" ");
                    break;
                case "?":
                    morse.append("..--..");
                    break;
                case ".":
                    morse.append(".-.-.- ");
                    break;
            }
        }
        return morse.toString();
    }
    
    public static int fibo(int n){
        int[][] tabela = new int[n][n];
        int fib = 1;
        int predz = 1;
        int predpredz = 1;
        int temp = 0;
        int st = 1;
        int vsota = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (st == 1 || st == 2){
                    tabela[i][j] = 1;
                }
                else{
                    tabela[i][j] = predz + predpredz;
                    temp = predz;
                    predz = tabela[i][j];
                    predpredz = temp;
                }
                st++;
            }
        }
        for (int i = 0; i < n; i++){
            vsota += tabela[i][i];
        }
        for (int i = 0; i < n; i++){
            vsota += tabela[n-i-1][i];
        }
        return vsota;
    }

    //public static void pascal(int n){}

    public static void izpisKoledarja(int leto, int mesec){
        int[][] koledar = new int[5][7];
        java.time.YearMonth yearMonth = java.time.YearMonth.of(leto, mesec);
        int steviloDni = yearMonth.lengthOfMonth();   
        int prviDan    = java.time.LocalDate.of(leto, mesec, 01).getDayOfWeek().getValue();        
        System.out.println("PO  TO  SR  ČE  PE  SO  NE");
        int stDni = 1;
        for(int i = 0; i < prviDan-1; i++)
            System.out.print("    ");
        for(int i = prviDan-1; i < 7; i++){
            System.out.printf("%2s  ", stDni);
            stDni++;
        }
        System.out.println("");
        for (int i = 0; i < 5 && stDni <= steviloDni; i++){
            for (int j = 0; j < 7 && stDni <= steviloDni; j++){
                System.out.printf("%2s  ", stDni);
                stDni++;
            }
            System.out.println("");
        }
    }
}