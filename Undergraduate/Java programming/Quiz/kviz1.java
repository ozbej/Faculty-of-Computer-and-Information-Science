public class kviz1 {
    public static void main(String[] args){
    	//java();
    	//kalkulator(42, 0);
    	//krog(7.5);
    	//obsegKroga(7.5, 3);
    	//System.out.println(pretvoriSekunde(49330));
    	//javaJavaJava(4);
    	//System.out.println(modulo(13,1));
    	//System.out.println(jePrastevilo(42));
    	//izrisiZastavo(2);
    	//vDesetisko(509);
    	//System.out.println(pretvoriVDesetisko("G", 16));
    	//vsotaPrvih(100);
    	//pitagoroviTrojcki(25);
    	//narisiDrevo(7);
    	System.out.print(izracunajRazliko("01:00:00", "02:00:00"));
    }

    public static void java(){
    	System.out.println("   J    a   v     v  a");
    	System.out.println("   J   a a   v   v  a a");
    	System.out.println("J  J  aaaaa   V V  aaaaa");
    	System.out.println(" JJ  a     a   V  a     a");
    }

    public static void kalkulator(int a, int b){
    	if(b==0)
    		System.out.println("Napaka: deljenje z 0");
    	else{
	    	System.out.println(a + " + " + b + " = " + (a+b));
	    	System.out.println(a + " - " + b + " = " + (a-b));
	    	System.out.println(a + " x " + b + " = " + a*b);
	    	System.out.println(a + " / " + b + " = " + a/b);
	    	System.out.println(a + " % " + b + " = " + a%b);
    	}
    }

    public static void krog(double r){
    	if(r < 0)
			System.out.println("Napaka: negativen polmer");
		else{
			System.out.println("Obseg = " + Math.PI * r * 2);
			System.out.println("Ploscina = " + Math.PI * r * r);
		}  		
    }

    public static void obsegKroga(double r, int d){
    	if(r < 0)
			System.out.println("Napaka: negativen polmer");
		else if(d < 0)
			System.out.println("Napaka: negativen d");
		else{
			double obseg = Math.PI * r * 2;
			double ploscina = Math.PI * r * r;
			System.out.printf("Obseg kroga s polmerom r=");
			System.out.printf("%.2f", r);
			System.out.printf(" je " + "%."+d+"f\n", obseg);
			System.out.printf("Ploscina kroga s polmerom r=");
			System.out.printf("%.2f", r);
			System.out.printf(" je " + "%."+d+"f\n", ploscina);
		}  		
    }

    public static String pretvoriSekunde(int sekunde){
    	if (sekunde < 0)
    		return "Število sekund ne more biti negativno";
    	else{
    		if (sekunde < 60)
    			return "00:00:"+String.format("%02d", sekunde);
    		else if (sekunde < 3600){
    			int mm = sekunde/60;
    			int ss = sekunde%60;
    			return "00:"+String.format("%02d", mm)+":"+ String.format("%02d", ss);
    		}
    		else{
    			int hh = sekunde/3600;
    			int mm = (sekunde-hh*3600)/60;
    			int ss = (sekunde-hh*3600)%60;
    			return String.format("%02d", hh)+":"+String.format("%02d", mm)+":"+ String.format("%02d", ss);
    		}
    	}
    }

    public static void javaJavaJava(int n){
    	if (n < 0)
    		System.out.print("Napaka: negativen n");
    	else{
	    	System.out.print("  ");
	    	for (int i = 0; i < n; i++){
	    		System.out.print("   J    a   v     v  a     ");
	    	}
	    	System.out.println("");
	    	System.out.print("  ");
	    	for (int i = 0; i < n; i++){
	 			System.out.print("   J   a a   v   v  a a    ");   		
	    	}
	    	System.out.println("");
	    	System.out.print("  ");
	    	for (int i = 0; i < n; i++){
	    		System.out.print("J  J  aaaaa   V V  aaaaa   ");
	    	}
	    	System.out.println("");
	    	System.out.print("  ");
	    	for (int i = 0; i < n; i++){
	    		System.out.print(" JJ  a     a   V  a     a  ");
	    	}
    }
    }

    public static int modulo(int deljenec, int deljitelj){
    	if (deljitelj == 0)
    		return -1;
    	while (deljenec >= deljitelj){
    		deljenec -= deljitelj;
    	}
    	return deljenec;
    }

    public static boolean jePrastevilo(int i){
    	if (i < 0)
    		return false;
    	else{
    		boolean flag = false;
	        for(int st = 2; st <= i/2; ++st)
	        {
	            if(i % st == 0)
	            {
	                flag = true;
	                break;
	            }
	        }

	        if (!flag)
	            return true;
	        else
	            return false;
    	}
    }

    public static void izrisiZastavo(int n){
    	for (int i = 0; i < n*5; i++){
    		if (i < n*3){
    			if(i%2==0){
	    			for (int j = 0; j < n*2; j++){
	    				System.out.print("* ");
	    			}
	    			for (int j = 0; j < 16*n-(n-1)-n*2*2; j++){
	    				System.out.print("=");
	    			}
	    			System.out.println("");
    			}
    			if(i%2==1){
	    			for (int j = 0; j < n*2-1; j++){
	    				System.out.print(" *");
	    			}
	    			System.out.print("  ");
	    			for (int j = 0; j < 16*n-(n-1)-n*2*2; j++){
	    				System.out.print("=");
	    			}
	    			System.out.println("");
    			}
    		}
    		else{
    			for (int j = 0; j < 16*n-(n-1); j++){
	    				System.out.print("=");
	    			}
	    			System.out.println("");
    		}
    	}
    }

    public static void vDesetisko(int n){
    	String str = String.valueOf(n);
    	if (str.contains("9"))
    		System.out.println("Število "+n+" ni število v osmiškem sistemu (števka 9)");
    	else if (str.contains("8"))
    		System.out.println("Število "+n+" ni število v osmiškem sistemu (števka 8)");
    	else
    		System.out.print(n+"(8) = "+Integer.parseInt(str,8)+"(10)");
    }

    public static String pretvoriVDesetisko(String n, int b){
    	if (n.contains(String.valueOf(b)))
    		return "Napaka pri pretvorbi sistema - števka "+b;
    	else if (n.contains("G"))
    		return "Napaka pri pretvorbi sistema - števka G";
    	else
    		return n+"("+b+")="+Integer.parseInt(n,b)+"(10)";
    }

    public static int vsotaPrvih(int n){
    	int vsota = 0;
    	int i = 0;
    	int st = 0;
    	while (st <= n){
    		i++;
    		if (jePrastevilo(i)){
    			vsota += i;
    			st++;
    		}
    	}
    	return vsota-1;
    }

    public static void pitagoroviTrojcki(int x){
    	for (int a = 1; a <= x; a++){
    		for (int b = 1; b <= x; b++){
    			for (int c = 1; c <= x; c++){
    				if (a*a + b*b == c*c && a <= b && b <= c && c <= x)
    					System.out.println(a + " " + b + " " + c);
    			}
    		}
    	}
    }

    public static void narisiDrevo(int n){
    	if (n==0)
    		System.out.print(" .");
    	else if (n==1)
    		System.out.print("|");
    	else if (n==2){
	    		System.out.println(" |");
	    		System.out.println(" |");
	    	}
    	else{
    		if (n%2==1)
    		System.out.println(" * ");
    		for (int i = 0; i < n/2-1; i++){
    			System.out.println("* *");
    		}
    		System.out.println(" |");
	    	System.out.println(" |");
    	}
    }

    public static String izracunajRazliko(String prviCas, String drugiCas){
    	int cas1Sec = 0;
    	int cas2Sec = 0;
    	String[] cas1 = prviCas.split(":");
    	String[] cas2 = drugiCas.split(":");
    	cas1Sec = Integer.parseInt(cas1[0])*3600;
    	cas2Sec = Integer.parseInt(cas2[0])*3600;
    	cas1Sec += Integer.parseInt(cas1[1])*60;
    	cas2Sec += Integer.parseInt(cas2[1])*60;
    	cas1Sec += Integer.parseInt(cas1[2]);
    	cas2Sec += Integer.parseInt(cas2[2]);
    	int sekunde;
    	if (cas2Sec > cas1Sec)
    		sekunde = cas2Sec - cas1Sec;
    	else 
    		sekunde = cas1Sec - cas2Sec;
    	return pretvoriSekunde(sekunde);
    }
}