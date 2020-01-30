import java.util.Random;
import java.util.Scanner;
public class vaja5 {

    static final int MAX_NAPAK = 10;

  // Seznam fraz; dopolnite ga s poljubnimi frazami
    // (vec ko jih bo, bolj zabavna bo igra)
    static String[] fraze = {"SONCE SIJE", "TRAVA RASTE", "KOLESAR BRZI",
        "ZIDAR ZIDA", "METLA POMETA", "VELIKA NOGA", "RDECA VRTNICA",
        "SLEPA ULICA", "MODRINA NEBA", "SABLJA", "NETOPIR", "KOLOVRAT",
        "SMUCANJE", "BELE STRMINE", "OTROCI SE ZOGAJO", "BABICA LIKA",
        "PROGRAMER PROGRAMIRA", "SKAF IMA LUKNJO", "VISLICE SO ZABAVNE"};

    // fraza, ki jo uganjujemo
    static String fraza;

    // katere crke smo ze uganili
    static boolean uganil[];

    static int steviloNapak;

    static void novaIgra() {
        int index = new Random().nextInt(fraze.length);
        fraza = (fraze[index]);
        System.out.println(fraza);
        uganil = new boolean[fraza.length()];
        for (int i = 0; i < uganil.length; i++){
            if (fraza.charAt(i) == ' ')
                uganil[i] = true;
            else
                uganil[i] = false;
        }
        steviloNapak = 0;
    }

    static boolean jeUganil() {
        for (int i = 0; i < uganil.length; i++){
            if (uganil[i] == false)
                return false;
        }
        return true;
    }

    static void izpisiUganko() {
        for (int i = 0; i < uganil.length; i++){
            if (uganil[i] == true)
                System.out.print(fraza.charAt(i));
            else
                System.out.print(".");
        }
        System.out.println("");
    }

    static void postaviVprasanje() {
        System.out.print("Naredite lahko se "+  (MAX_NAPAK - steviloNapak) +" napak:");
        Scanner reader = new Scanner(System.in);
        String c = reader.nextLine();
        String line = "";
        if (c.length()!=1)
        {
            if (c.equals(fraza)){
                System.out.println(fraza);
                System.out.println("Bravo!");
                System.exit(0);
            }
            else
                steviloNapak++;
        }
        else
        {
            int ujemanja = 0;
            char crka = c.charAt(0);
            for (int i = 0; i < fraza.length(); i++){
                if (fraza.charAt(i) == crka){
                    uganil[i] = true;
                    ujemanja++;
                }
        }
        if (ujemanja == 0)
            steviloNapak++;
        }
        System.out.println("");
    }

    public static void main(String args[]) {
        novaIgra();
        while ((steviloNapak < MAX_NAPAK) && !jeUganil()) {
            izpisiUganko();
            postaviVprasanje();
        }
        if (jeUganil()) {
            System.out.println(fraza);
            System.out.println("Bravo!");
        } else {
            System.out.println("Konec igre! Pravilen odgovor je: " + fraza);
        }
    }
}