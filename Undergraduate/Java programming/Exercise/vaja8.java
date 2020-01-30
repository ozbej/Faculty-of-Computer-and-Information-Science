import java.io.File;
import java.util.Scanner;

public class vaja8{

	//private static Oseba[] osebe;

	public static void main(String[] args){
		
	}

	public static Oseba[] preberiOsebe(String vir) throws Exception{
		Oseba[] osebe = new Oseba[100];
        Scanner sc = new Scanner(new File(vir));
        int i = 0;
        while(sc.hasNextLine()){
            String[] oseba = sc.nextLine().split(", ");
            osebe[i++] = new Oseba(oseba[0], oseba[1], oseba[2], oseba[4], oseba[3], oseba[5]);
        }
        return osebe;
    }

    public Oseba isciOseboPoImenu(String ime, Oseba[] tabelaOseb){
		for (int i = 0; i < tabelaOseb.length; i++){
			if (tabelaOseb[i] != null && ime.equals(tabelaOseb[i].ime))
				return tabelaOseb[i];
		}
		return null;
	}

    int isciOsebePoPosti(String posta, Oseba[] tabelaOseb){
    	int stOseb = 0;
    	for (int i = 0; i < tabelaOseb.length; i++){
			if (tabelaOseb[i] != null && posta.equals(tabelaOseb[i].posta))
				stOseb++;
		}
		return stOseb;
    }
}

class Oseba{
	public String ime;
	public String priimek;
	public String naslov;
	public String mesto;
	public String posta;
	public String telefon;

	Oseba(String ime, String priimek, String naslov, String mesto, String posta, String telefon){
		this.ime = ime;
		this.priimek = priimek;
		this.naslov = naslov;
		this.mesto = mesto;
		this.posta = posta;
		this.telefon = telefon;
	}

	public String toString(){
		return this.ime + ", " +  this.priimek + ", " +  this.naslov + ", " +  this.mesto + ", " +  this.posta + ", " +  this.telefon;
	}

	
}