import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class vaja6{
	public static void main(String[] args) throws Exception{
		odkodiraj("pesem.txt", "koda.txt");
	}

	static void odkodiraj(String kodirnaKnjiga, String koda) throws Exception{
		//prebere prvi argument
		Scanner sc = new Scanner(new File(kodirnaKnjiga));
		StringBuffer line = new StringBuffer("");
		while (sc.hasNext()){
			line.append(sc.nextLine());
			line.append(" ");
		}
		sc.close();
		//String[] besede = line.toString().split("\\W+");
		String[] besede = line.toString().split(" ");
		//prebere drugi argument
		Scanner sc2 = new Scanner(new File(koda));
		List<Integer> code = new ArrayList<Integer>();
		int naslednji;
		while (sc2.hasNextInt()){
			naslednji = sc2.nextInt();
			if (naslednji != 0)
				code.add(naslednji);
		}
		sc2.close();
		int[] array = new int[code.size()];
		for(int i = 0; i < code.size(); i++) array[i] = code.get(i);

		//kodira
		String kodirano = "";
		String beseda;
		char crka;
		for (int i = 0; i < array.length; i+=2){
			int index = array[i];
			//System.out.print(array[i+1]);
			for (int j = 0; j < besede.length; j++){
				if (j == index){
					beseda = besede[j-1];
					//System.out.print(beseda);
					//System.out.println(array[i+1]-1);
					System.out.print(beseda.charAt(array[i+1]-1));
				}
			}
		}
}
}