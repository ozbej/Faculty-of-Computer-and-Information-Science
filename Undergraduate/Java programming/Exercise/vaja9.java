import java.io.File;
import java.util.Scanner;

public class vaja9{
	public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new File("komponente.txt"));
        int i = 0;
        while(sc.hasNextLine()){
            String[] parametri = sc.nextLine().split(";");
            switch(parametri[0]){
            	case "Pomnilnik":
            		System.out.println(new Pomnilnik(parametri[1], Double.parseDouble(parametri[2]), Integer.parseInt(parametri[3]),Integer.parseInt(parametri[4]),Integer.parseInt(parametri[5])).toString());
            		System.out.println("");
            		break;
            	case "Procesor":
            		System.out.println(new Procesor(parametri[1], Double.parseDouble(parametri[2]), parametri[3], Integer.parseInt(parametri[4]), Integer.parseInt(parametri[5])).toString());
            		System.out.println("");
            		break;
            	case "Maticna":
            		System.out.println(new MaticnaPlosca(parametri[1], Double.parseDouble(parametri[2]), parametri[3], parametri[4], Integer.parseInt(parametri[5])).toString());
            		System.out.println("");
            		break;
            }
        }
    }
}

class Komponenta{
	String ime;
	double cena;
	int popust;

	Komponenta(String ime, double cena, int popust){
		this.ime = ime;
		this.cena = cena;
		this.popust = popust;
	}

	Komponenta(String ime, double cena){
		this.ime = ime;
		this.cena = cena;
	}

	public String toString(){
		if (this instanceof Pomnilnik)
			return ime + ", cena s popustom: " + String.format("%.2f", cena*0.9) + " EUR";
		else if (this instanceof Procesor)
			return ime + ", cena s popustom: " + String.format("%.2f", cena*0.95) + " EUR";
		else if (this instanceof MaticnaPlosca)
			return ime + ", cena s popustom: " + String.format("%.2f", cena*0.85) + " EUR";
		else
			return ime + ", cena s popustom: " + String.format("%.2f", cenaSPopustom()) + " EUR";
	}

	public double cenaSPopustom(){
		if (popust == 0)
			return cena;
		else
		{
			double discount = 1-popust/100.0;
			return cena * discount;
		}
	}
}

class MaticnaPlosca extends Komponenta{
	String format;
	String podnozje;
	int steviloPomnilniskihRez;

	MaticnaPlosca(String ime, double cena, String format, String podnozje, int steviloPomnilniskihRez){
		super(ime, cena);
		this.format = format;
		this.podnozje = podnozje;
		this.steviloPomnilniskihRez = steviloPomnilniskihRez;
	}

	public String toString(){
		return "Maticna plosca: " + super.toString() + "\nFormat: " + format + " Podnozje: " + podnozje + " stevilo pomn. rez: " + steviloPomnilniskihRez;
	}

	public boolean jeKompatibilna(Procesor p){
		if (podnozje.equals(p.podnozje))
			return true;
		else
			return false;
	}
}

class Procesor extends Komponenta{
	String podnozje;
	int steviloJeder;
	int hitrostJedra;

	Procesor(String ime, double cena, String podnozje, int steviloJeder, int hitrostJedra){
		super(ime, cena);
		this.podnozje = podnozje;
		this.steviloJeder = steviloJeder;
		this.hitrostJedra = hitrostJedra;
	}

	public String toString(){
		return "Procesor: " + super.toString() + "\nPodnozje: " + podnozje + " St. jeder: " + steviloJeder + " Hitrost jedra: " + hitrostJedra + "MHz";
	}
}

class Pomnilnik extends Komponenta{
	int tip;
	int velikost;
	int hitrost;

	Pomnilnik(String ime, double cena, int tip, int velikost, int hitrost){
		super(ime, cena);
		this.tip = tip;
		this.velikost = velikost;
		this.hitrost = hitrost;
	}

	public String toString(){
		return "Pomnilnik: " + super.toString() + "\nTip: " + tip() + " Velikost: " + velikost + " GB Hitrost: " + hitrost + "MHz";
	}

	public String tip(){
		switch(tip){
			case 0:
				return "SDRAM";
			case 1:
				return "DDR";
			case 2:
				return "DDR2";
			case 3:
				return "DDR3";
		}
		return "";
	}
}