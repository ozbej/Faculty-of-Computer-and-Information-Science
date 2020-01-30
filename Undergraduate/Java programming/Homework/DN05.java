import java.io.File;
import java.util.Scanner;
import java.lang.Math;
import java.util.Locale;
import java.io.FileWriter;
import java.io.Writer;
public class DN05 {
    public static void main(String[] args) throws Exception {
    	Locale.setDefault(Locale.GERMANY);
      if (args.length == 1)
        analizirajTemperature(args[0]);
      else if (args.length == 4){
        String ime = args[0].split("\\.")[0];
        int[][] sudoku = preberiResitevSudoku(args[0]);
        int[] diagonale = vsotaPoDiagonali(sudoku);
        if (preveriSudoku(sudoku))
          System.out.println("Resitev je pravilna.");
        else
          System.out.println("Resitev NI pravilna.");
        System.out.println("Vsota na diagonalah je " + diagonale[0] + ", " + diagonale[1] + " in " + diagonale[2] + ".");
        String filename = ime + "-popravljeno.txt";
        zapisiResitev(filename, popraviResitev(preberiResitevSudoku(args[0])));
        zamenjajStolpca(sudoku, Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        zapisiResitev(args[1], sudoku);
      }
      else
        System.out.println("Vnesite pravilno stevilo argumentov (1 ali 4)!");
    }


    //1
    public static void analizirajTemperature(String datoteka) throws Exception {
    	Scanner sc = new Scanner(new File(datoteka));
      double[] top5 = new double[]{-Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE, -Double.MAX_VALUE};
      double vsota = 0;
      int st = 0;
    	while (sc.hasNext()) {
    		double x = sc.nextDouble();
        double[] minPar = minDouble(top5);
        double y = minPar[1];
        Integer index = (int) y; 
        vsota += x;
        st++;
        if (minPar[0] < x){
          top5[index] = x;
        }
    	}
      sc.close();
      bubbleSort(top5);
      System.out.printf("Povprecje zapisanih temperatur je %.2f.\n", vsota / st);
      System.out.println("Najvisjih 5 temperatur:");
      for (int i = 0; i < 5; i++){
        System.out.printf("%.2f\n", top5[i]);
      }
    }

    public static void bubbleSort(double[] array){
	    boolean swapped = true;
	    int j = 0;
	    double tmp;
	    while (swapped) {
	        swapped = false;
	        j++;
	        for (int i = 0; i < array.length - j; i++) {
	            if (array[i] < array[i + 1]) {
	                tmp = array[i];
	                array[i] = array[i + 1];
	                array[i + 1] = tmp;
	                swapped = true;
	            }
	        }
	    }
	}

  public static double[] minDouble(double[] seznam) throws Exception{
      double min = seznam[0];
      int minIndex = 0;
      for (int i = 0; i < seznam.length; i++){
        if (seznam[i] <= min){
          min = seznam[i];
          minIndex = i;
        }
      }
      double[] minPar = new double[]{min, minIndex};
      return minPar;
  }

  public static void izpis(int[][] sudoku){
    for (int i = 0; i < 25; i++){
        for (int j = 0; j < 25; j++){
          System.out.printf("%3d", sudoku[i][j]);
        }
        System.out.println("");
      }
  }
  //2.1
  public static int[][] preberiResitevSudoku(String datoteka) throws Exception{
    Scanner sc = new Scanner(new File(datoteka));
    int[][] sudoku = new int[25][25];
    while (sc.hasNext()) {
      for (int i = 0; i < 25; i++){
        for (int j = 0; j < 25; j++){
          sudoku[i][j] = sc.nextInt();
        }
      }
    }
    return sudoku;
  }

  public static boolean miniSudoku(int zacetek, int konec, int vrstica, int[][] sudoku) throws Exception{
    int[] minisudoku = new int[25];
    int vsota = 0;
    int st = 0;
    for (int i = vrstica; i < vrstica+5; i++){
      for (int j = zacetek; j < konec; j++){
        if (minisudoku[st] > 25)
          return false;
        minisudoku[st] = sudoku[i][j];
        vsota += minisudoku[st];
        st++;
      }
    }
    if (vsota == 325)
      return true;
    else
      return false;
  }

  public static boolean stolpciVrstice(int[][] sudoku) throws Exception{ 
    for (int i = 0; i < 25; i++){
      for (int j = 0; j < 25; j++){
        for (int st = j+1; st < 25; st++){
          if (sudoku[i][j] == sudoku[i][st] || sudoku[i][j] > 25){
            return false;
          }
        }
        for (int st = i+1; st < 25; st++){
          if (sudoku[i][j] == sudoku[st][j] || sudoku[i][j] > 25){
            return false;
          }      
        }
      }
    }
    return true;
  }

  //2.2
  public static boolean preveriSudoku(int[][] sudoku) throws Exception{
    for (int i = 0; i < 25; i += 5){
      for (int j = 0; j < 25; j += 5){
        if (miniSudoku(j, j+5, i, sudoku) == false)
          return false;
      }
    }
    if (stolpciVrstice(sudoku) == false)
      return false;
    else
      return true;
  }

  static final int pet = 5;

  public static int[][] vrsticeStolpciFix(int[][] sudoku) throws Exception{ 
    int trenutna;
    int[][] new_sudoku = sudoku;

    //Večje od 25
    for (int i = 0; i < 25; i++){
      for (int j = 0; j < 25; j++){
        if (sudoku[i][j] > 25)
          sudoku[i][j] = 0;
      }
    }

    //Rows
    for (int i = 0; i < 25; i++){
      for (int j = 0; j < 24; j++){
        trenutna = new_sudoku[i][j];
        if (new_sudoku[i][j] == 0)
          continue;
        for (int k = j+1; k < 25; k++){
          if (trenutna == new_sudoku[i][k]){
            new_sudoku[i][k] = 0;
            new_sudoku[i][j] = 0;
          }
        }
      }
    }

    //Columns
    for (int j = 0; j < 25; j++){
      for (int i = 0; i < 24; i++){
        trenutna = new_sudoku[i][j];
        if (trenutna == 0)
          continue;
        for (int k = i+1; k < 25; k++){
          if (trenutna == new_sudoku[k][j]){
            new_sudoku[k][j] = 0;
            new_sudoku[i][j] = 0;
          }
        }
      }
    }
    return new_sudoku;
  }  
  //2.3
  public static int[][] popraviResitev(int[][] sudoku) throws Exception{ 
    int[][] new_sudoku = vrsticeStolpciFix(sudoku);
    if (preveriSudoku(sudoku) == false){
    int trenutna;

    //Preverjanje kvadratkov
    for (int i = 0; i < 25; i+=pet){
      for (int j = 0; j < 25; j+=pet){
        for (int k = 0; k < 24; k++){
          trenutna = new_sudoku[i + k%pet][j + k/pet];
          if (trenutna == 0)
            continue;
          for (int l = k+1; l < 25; l++){
            if (trenutna == new_sudoku[i+l%pet][j+l/pet]){
              new_sudoku[i+k%pet][j+k/pet] = 0;
              new_sudoku[i+l%pet][j+l/pet] = 0;
            }
          }
        }
      }
    }
  }
    return new_sudoku;
  } 

  //2.4
  public static int[] vsotaPoDiagonali(int[][] sudoku) throws Exception{
    //vsota diagonale 1
    int vsota_d1 = 0;
    for (int i = 0; i < 25; i++)
      vsota_d1 += sudoku[i][i];

    //vsota diagonale 2
    int vsota_d2 = 0;
    int st = 24;
    for (int i = 0; i < 25; i++){
      vsota_d2 += sudoku[i][st];
      st--;
    }

    //vsota malih diagonal
    int vsota_dmale = 0;
    for (int i = 0; i < 25; i += 5) {
      for (int j = 0; j < 25; j += 5) {
        int i2 = i;
        int j2 = j;
        for (int k = 0; k < 5; k++) {
          vsota_dmale += sudoku[i2][j2];
          j2++;
          i2++; 
        }
        i2 = i + 4;
        j2 = j;
        for (int k = 0; k < 5; k++) {
          vsota_dmale += sudoku[i2][j2];
          j2++;
          i2--; 
        }
      }
    }
    int[] rezultat = new int[3];
    rezultat[0] = vsota_d1;
    rezultat[1] = vsota_d2;
    rezultat[2] = vsota_dmale;
    return rezultat;
  }

  //2.5
  public static void zamenjajStolpca(int[][] sudoku, int j, int k) throws Exception{
    int[] tmp = new int[25];
    j-=1;
    k-=1;
    for (int i = 0; i < 25; i++){
      tmp[i] = sudoku[i][j];
      sudoku[i][j] = sudoku[i][k];
      sudoku[i][k] = tmp[i];
    }
  }

  //2.6
  public static void zapisiResitev(String datoteka, int[][] sudoku) throws Exception{
    Writer wr = new FileWriter(datoteka);
    for (int i = 0; i < 25; i++){
      for (int j = 0; j < 25; j++){
        wr.write(String.valueOf(sudoku[i][j]) + " ");
      }
      wr.write("\n");
    }
    wr.flush();
    wr.close();
  }
}