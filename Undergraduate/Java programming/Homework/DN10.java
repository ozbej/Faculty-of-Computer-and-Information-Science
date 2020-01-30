import java.io.File;
import java.util.Scanner;

interface Lik {   
   public double povrsina();
}

class Kvadrat implements Lik{
	int a;

	Kvadrat(int a){
		this.a = a;
	}

	@Override
	public double povrsina(){
		return a*a;
   }
}

class Pravokotnik implements Lik{
	int a;
	int b;

	Pravokotnik(int a, int b){
		this.a = a;
		this.b = b;
	}

	@Override
	public double povrsina(){
		return a*b;
   }
}

class Krog implements Lik{
	int r;

	Krog(int r){
		this.r = r;
	}

	@Override
	public double povrsina(){
		return Math.PI * r * r;
   }
}

public class DN10{

	static Lik[] Liki;
	public static String fileName;

	public static void main(String[] args) throws Exception{
		fileName = args[0];
		Liki = new Lik[100];
		preberi();
		izracunaj();
	}

	static void preberi() throws Exception{
        Scanner sc = new Scanner(new File(fileName));
        int i = 0;
        while(sc.hasNextLine()){
            String[] parametri = sc.nextLine().split(":");
            switch(parametri[0]){
            	case "kvadrat":
            		Liki[i++] = new Kvadrat(Integer.parseInt(parametri[1]));
            		break;
            	case "pravokotnik":
            		Liki[i++] = new Pravokotnik(Integer.parseInt(parametri[1]), Integer.parseInt(parametri[2]));
            		break;
            	case "krog":
            		Liki[i++] = new Krog(Integer.parseInt(parametri[1]));
            		break;
            }
        }
    }

    static void izracunaj(){
    	double vsota = 0;
    	for (int i = 0; i < Liki.length; i++){
    		if (Liki[i] != null){
				vsota += Liki[i].povrsina();
    		}
    	}
		System.out.printf("%.2f", vsota);
    }
}