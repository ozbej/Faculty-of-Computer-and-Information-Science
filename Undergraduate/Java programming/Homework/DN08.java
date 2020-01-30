import java.io.File;
import java.util.Scanner;

public class DN08 {
    private static Planet[] planeti;

    public static void main(String[] args) throws Exception {
        planeti = new Planet[9];
        readFile(args[0]);
        izpisPovrsin(args[1]);
    }


    public static void readFile(String datoteka) throws Exception{
        Scanner sc = new Scanner(new File(datoteka));
        int i = 0;
        while(sc.hasNextLine()){
            String[] planet = sc.nextLine().split(":");
            if (planet.length == 2){
                planeti[i] = new Planet(planet[0], Integer.parseInt(planet[1]));
                i++;
            }
        }
    }

    public static void izpisPovrsin(String argumenti){
        String[] splitted = argumenti.split("\\+");
        double vsotaPovrsin = 0;
        for (int i = 0; i < splitted.length; i++){
            for (int j = 0; j < 9; j++){
                String upperArg = splitted[i].toUpperCase();
                String upperPlanet = planeti[j].imeCurrentPlaneta().toUpperCase();
                if (upperArg.equals(upperPlanet))
                    vsotaPovrsin += planeti[j].povrsina();
            }
        }
        System.out.print("Povrsina planetov \"" + argumenti + "\" je ");
        System.out.printf("%.0f", vsotaPovrsin / 1000000);
        System.out.print(" milijonov km2");
    }

}

class Planet {

    public String imePlaneta;
    public int radijPlaneta;

    Planet(String imePlaneta, int radijPlaneta){
        this.imePlaneta = imePlaneta;
        this.radijPlaneta = radijPlaneta;
    }

    public double povrsina(){
        double pov = 4 * Math.PI * this.radijPlaneta * this.radijPlaneta;
        return pov;
    }

    public String imeCurrentPlaneta() {
        return this.imePlaneta;
    }
}