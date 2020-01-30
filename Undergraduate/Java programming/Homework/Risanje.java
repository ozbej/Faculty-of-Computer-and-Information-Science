import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Risanje {

    public static void main(String[] args) {
        int koordinateMax = 100;
        int koordinateMin = -100;
        int MinY = -50;
        
        int barvaMin = 0;
        int barvaMax = 256;
        
        int velikostMin = 10;
        int velikostMax = 80;
        
        for(int i = 0; i < 100; i++){
            //random x in y koordinati
            double randomX = koordinateMin + Math.random() * (koordinateMax - koordinateMin);
            double randomY = MinY + Math.random() * (koordinateMax - MinY);
            
            //random rgb barva za cvetove
            double r = barvaMin + Math.random() * (barvaMax - barvaMin);
            double g = barvaMin + Math.random() * (barvaMax - barvaMin);
            double b = barvaMin + Math.random() * (barvaMax - barvaMin);
            int[] colors = {(int)r, (int)g, (int)b};
            
            //random velikost roze
            double randomVelikost = velikostMin + Math.random() * (velikostMax - velikostMin);
            
            //klice funkcijo
            narisi(randomX, randomY, colors, randomVelikost);
        }
    }
    
  static void narisi(double x, double y, int[] barve, double v) {
    // nastavitev koordinatnega sistema
    StdDraw.setScale(-100, 100);
    
    //narise steblo
    StdDraw.setPenColor(Color.green);
    StdDraw.setPenRadius(v/4*0.0005);
    StdDraw.line(x, y-2*v/4, x, y-4*v/4);
    
    //narise 4 cvetove
    StdDraw.setPenColor(barve[0], barve[1], barve[2]);
    StdDraw.filledCircle(x, y+v/4, v/4);
    StdDraw.filledCircle(x, y-v/4, v/4);
    StdDraw.filledCircle(x+v/4, y, v/4);
    StdDraw.filledCircle(x-v/4, y, v/4);
    
    //narise center roze
    StdDraw.setPenColor(Color.green);
    StdDraw.filledCircle(x, y, v/4*0.6);
  }
}
