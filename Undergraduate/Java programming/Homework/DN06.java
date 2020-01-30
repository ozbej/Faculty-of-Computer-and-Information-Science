import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileOutputStream;

public class DN06{
	public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            StringBuffer line = new StringBuffer("");
            String[] crke = new String[10000000];
            while (scanner.hasNext()) {
                line.append(scanner.nextLine());
                //System.out.println(line);
            }
            int st = 0;
            int st2 = 0;
            for (int i = 0; i < line.length(); i+=8){
            	StringBuffer crka = new StringBuffer("");
            	for (int j = 0; j < 8; j++){
            		crka.append(line.charAt(st));
            		st++;
            	}
            	crke[st2] = crka.toString();
            	st2++;
            }
            for (int i = 0; i < line.length()/8; i++){
            	int parseInt = Integer.parseInt(crke[i], 2);
				char c = (char)parseInt;
            	System.out.print(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
}
	