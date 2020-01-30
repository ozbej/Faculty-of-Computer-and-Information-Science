public class vaja4 {
    public static void main(String[] args) {
    	long z = getCrko(new int[] {255, 2, 4, 8, 16, 32, 64, 255});
		long b = getCrko(new int[] {254, 129, 129, 254, 254, 129, 129, 254});
		long e = getCrko(new int[] {127, 64, 64, 120, 64, 64, 64, 127});
		izpisi(new long[] {
    	4342175383962075708l, z, b, e, 144680345680364600l});
	}

    public static void izpisi_crko(long crka)
    {
    	for (int i = 7; i >= 0; i--){
    		for (int j = 7; j >= 0; j--){
    			long maska = 1l << (i*8+j);
    			if ((crka & maska) == maska){
    				System.out.print("*");
    			}
    			else{
    				System.out.print(" ");
    			}
    		}
    		System.out.println("");
    	}
    }

    public static void izpisi(long crke[]){
    	for (int i = 7; i >= 0; i--){
    		for (int k = 0; k < crke.length; k++){
	    		for (int j = 7; j >= 0; j--){
	    				long maska = 1l << (i*8+j);
		    			if ((crke[k] & maska) == maska){
		    				System.out.print("*");
		    			}
		    			else{
		    				System.out.print(" ");
		    			}
	    			}
	    		}
    		System.out.println("");
    	}
    }

    public static long getCrko(int [] vrstice){
    	long crka = 0;
    	int st = 7;
    	for (int i = 0; i < vrstice.length; i++){
    		crka = crka + (Long.valueOf(vrstice[i]) << st*8);
    		st--;
    	}
    	return crka;
    }
}