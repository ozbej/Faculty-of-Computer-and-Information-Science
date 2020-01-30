import java.util.concurrent.ThreadLocalRandom;

public class vaja3{
	public static void main(String[] args) {	
		String[] karte = ustvariKarte();
		premesajKarte(karte, 200);
		System.out.printf("Preprosto stetje: %d\n", preprostoStetje(karte));
	}

	static char pik  = '\u2660'; // ♠
	static char kriz = '\u2663'; // ♣
	static char srce = '\u2665'; // ♥
	static char karo = '\u2666'; // ♦

	static char barve[] = {srce, karo, kriz, pik};
	static String prazneRdece[] = {"1", "2", "3", "4"};
	static String prazneCrne[] = {"7", "8", "9", "10"};

	static String figure[] = {"Fant", "Kaval", "Dama", "Kralj"};

	static String taroki[] = {
	             "I", "II", "III", "IIII", "V", "VI", "VII",
	             "VIII", "IX", "X", "XI", "XII", "XIII", "XIV",
	             "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
	             "XXI", "SKIS"};
	static String[] ustvariKarte(){
		String[] vsi_taroki = new String[54];
		int st = 0;
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[0]) + prazneRdece[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[0]) + figure[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[1]) + prazneRdece[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[1]) + figure[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[2]) + prazneCrne[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[2]) + figure[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[3]) + prazneCrne[i];
			st ++;
		}
		for (int i = 0; i < 4; i++){
			vsi_taroki[st] = String.valueOf(barve[3]) + figure[i];
			st ++;
		}
		for (int i = 0; i < 22; i++){
			vsi_taroki[st] = taroki[i];
			st ++;
		}
		return vsi_taroki;
	}

	static void izpisi(String karte[]){
		for (int i = 0; i < 54; i++){
			System.out.printf("%-10s", karte[i]);
			if (i == 7 || i == 15 || i == 23 || i == 31 || i == 39 || i == 47){
				System.out.println("");
			}
		}
	}

	static void premesajKarte(String karte[], int koliko){
		for (int i = 0; i < koliko; i++){
		String tmp = "";
		int randomNum1 = ThreadLocalRandom.current().nextInt(0, 53 + 1);
		int randomNum2 = ThreadLocalRandom.current().nextInt(0, 53 + 1);
		tmp = karte[randomNum1];
		karte[randomNum1] = karte [randomNum2];
		karte[randomNum2] = tmp;
		}
	}

	static int preprostoStetje(String karte[]){
		int st = 0;
		for (int i = 0; i < 54; i++){
			if (karte[i].contains("Fant")){
				st = st + 2;
			}
			else if (karte[i].contains("Kaval")){
				st = st + 3;
			}
			else if (karte[i].contains("Dama")){
				st = st + 4;
			}
			else if (karte[i].contains("Kralj") || karte[i].equals("I") || karte[i].equals("XXI") || karte[i].equals("SKIS")){
				st = st + 5;
			}
			else{
				st++;
			}
		}
		return st;

	}
}