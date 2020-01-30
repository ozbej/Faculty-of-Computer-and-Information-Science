package dn12;

import java.io.File;
import java.util.Scanner;

public class DN12 {
    
    public static void main(String[] args) {
        Fotka slikca = new Fotka();
        slikca.preberiTextDat("viri/naloga12-01.p2t");
        new Izris(slikca).prikazi();
    }
    
}

class SlikovnaTocka{
    private int red;
    private int green;
    private int blue;
    int x;
    int y;
    
    
    SlikovnaTocka(int grey){
        this.red = grey;
        this.green = grey;
        this.blue = grey;
    }
    
    SlikovnaTocka(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    SlikovnaTocka(int red, int green, int blue, int x, int y){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.x = x;
        this.y = y;
    }

    int[] getBarve(){
    	int[] rgb = {this.red, this.green, this.blue};
    	return rgb;
    }
    
}

class Fotka{
    private int visina;
    private int sirina;
    private String datoteka;
    private String tip;
    private SlikovnaTocka[] pixels;
    
    Fotka(){
        
    }
    
    void preberiTextDat(String ImeDatoteke){
        try{
            File file = new File(ImeDatoteke);
            this.datoteka = file.getParentFile().getName();
            Scanner sc = new Scanner(file);
            String line;
            String[] splitLine;
            int st = 0;
            while(sc.hasNext()){
            	if (st == 0){
                    line = sc.nextLine();
                    splitLine = line.split(" ");
                    this.tip = splitLine[0];
                    String[] velikost = splitLine[1].split("x");
                    this.sirina = Integer.parseInt(velikost[0]);
                    this.visina = Integer.parseInt(velikost[1]);
            	}
                else{
                    String[] lineRed = sc.nextLine().split(" ");
                    String[] lineGreen = sc.nextLine().split(" ");
                    String[] lineBlue = sc.nextLine().split(" ");
                    for(int i = 0; i < lineRed.length; i++){
                        SlikovnaTocka pixel = new SlikovnaTocka(Integer.parseInt(lineRed[i]), Integer.parseInt(lineGreen[i]), Integer.parseInt(lineBlue[i]), i, st);
                    }
                }
            	st++;
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
                
    }

    String getIme(){
    	return this.datoteka;
    }

    String getSirinaVisina(){
    	return this.sirina + "x" + this.visina;
    }

    int getSirina(){
    	return this.sirina;
    }

    int getVisina(){
    	return this.visina;
    }

    int getVelikost(){
    	return this.sirina * this.visina;
    }

    SlikovnaTocka getTocka(int x, int y){
    	for(SlikovnaTocka pixel : pixels){
    		if (pixel.x == x && pixel.y == y)
    			return pixel;
    	}
    	return null;
    }
}