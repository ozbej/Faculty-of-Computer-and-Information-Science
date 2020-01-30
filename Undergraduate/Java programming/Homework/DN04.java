import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class DN04 {

  static String[] prva   = {"Miha", "Micka", "Tone", "Lojze", "Mojca", "Pepca", "Franci", "Francka"};
  static String[] druga  = {"Vozi", "Seka", "Potrebuje", "Gleda", "Barva", "Voha", "Lomi", "Popravlja"};
  static String[] tretja = {"Kolo", "Avto", "Likalnik", "Sonce", "Vrtnico", "Drevo", "Lopato", "Sekiro"};

  static String ustvariGeslo() {
    Random rnd = new Random();
    int rnd1 = rnd.nextInt(prva.length);
    int rnd2 = rnd.nextInt(druga.length);
    int rnd3 = rnd.nextInt(tretja.length);
    
    return prva[rnd1] + druga[rnd2] + tretja[rnd3];
  }

  public static void main(String[] args) {
    StringBuilder builder = new StringBuilder();
    for(String s : args) {
        builder.append(s);
    }
    String geslo = builder.toString();
    String[] test = geslo.split("(?=[A-Z])");
    if (test.length >= 3 && Arrays.asList(prva).contains(test[0]) && Arrays.asList(druga).contains(test[1]) && Arrays.asList(tretja).contains(test[2])){
          System.out.print("true");
    }
    else{
      System.out.print("false");
    }
  }
}