import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.lang.Object;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.FileNotFoundException;

public class DN09{
	public static void main(String[] args) throws Exception{
		if(args[0].equals("poisci")){
			Entiteta entiteta = new Entiteta(args[1], args[2]);
			File f = new File(entiteta.pot + entiteta.ime);
			if (f.isDirectory()){
				Datoteka dat1 = new Datoteka(entiteta.ime, entiteta.pot);
				dat1.poisci(args[3]);
			}
			else{
				Mapa mapa1 = new Mapa(entiteta.ime, entiteta.pot);
				mapa1.poisci(args[3]);
			}
		}
		else if(args[0].equals("drevo")){
			Mapa mapa1 = new Mapa(args[1], args[2]);
			mapa1.drevo(0);
		}
	}
}

class Entiteta{
	String ime;
	String pot;
	//private Entiteta prednik;

	Entiteta(){
		this.ime = "ni imena entitete";
		this.pot = "/";
	}

	Entiteta(String ime, String pot) throws Exception{
		this.ime = ime;
		this.pot = pot;
	}

	public void poisci(String niz) throws Exception{
		//prazna metoda
	}

	public void drevo(int nivo) throws Exception{
		//prazna metoda
	}
}

class Datoteka extends Entiteta{
	Datoteka(){
		ime = "ni imena datoteke";
		pot = "emptyFile";
	}

	Datoteka(String ime, String pot) throws Exception{
		super(ime, pot);
	}

	@Override
	public void poisci(String niz) throws Exception{
		int st = 1;
		Scanner sc = new Scanner(new File((pot+ime)));
		String next = "";
		boolean pot_printed = false;

        while(sc.hasNextLine()){
            next = sc.nextLine().trim();

            if (next.toLowerCase().contains(niz.toLowerCase())){
            	if (!pot_printed){
            		pot_printed = true;
            		System.out.println(pot+ime);
            	}

            	System.out.printf("%4s", st);
            	System.out.println(": " + next);
            	for (int i = 0; i < next.indexOf(niz)+6; i++)
        			System.out.print(" ");
		        for (int i = 0; i < niz.length(); i++)
		        	System.out.print("^");
		        System.out.println("");
            }
            st++;
        }
	}
}

class Mapa extends Entiteta{
	Entiteta[] nasledniki;

	Mapa(){
		ime = "ni imena mape";
		pot = "/";
	}

	Mapa(String ime, String pot) throws Exception{
		super(ime, pot);
		this.nasledniki = nasledniki();
	}

	public Entiteta[] nasledniki() throws Exception{
		File f = new File(pot + ime); 
		if (f.isDirectory()){
			String[] paths;	
			paths = f.list();
			Entiteta[] nasledniki = new Entiteta[paths.length];
		    int i = 0;
		    for(String path:paths) {
		    	nasledniki[i++] = new Entiteta(path, pot+ime);
		    }
	        return nasledniki;
		}
		else
			return new Entiteta[0];
	}

	@Override
	public void poisci(String niz) throws Exception{
		Path path = Paths.get(pot + "/" + ime);
		int nivo = -1;
		findFiles(path, nivo, niz);
	}

	public static void findFiles(Path path, int depth, String niz) throws Exception{
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		if (attr.isDirectory()){
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);

			for(Path tempPath: paths){
				findFiles(tempPath, depth + 1, niz);
			}
		}
		else{
			Datoteka dat = new Datoteka(path.getFileName().toString(), path.toString().replace(path.getFileName().toString(), ""));
			dat.poisci(niz);
		}
	}

	@Override
	public void drevo(int nivo) throws Exception{
		nivo--;
		Path path = Paths.get(pot + "/" + ime);
		listDir(path, nivo);
	}

	public static void listDir(Path path, int nivo) throws Exception{
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		if (attr.isDirectory()){
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			if (nivo == -1){
				System.out.println(path.getFileName());
			}
			else{
				System.out.println(spacesZaNivo(nivo) + " +--" + path.getFileName());
			}

			for(Path tempPath: paths){
				listDir(tempPath, nivo + 1);
			}
		}
		else{
			System.out.println(spacesZaNivo(nivo) + " +--" + path.getFileName());
		}
	}

	public static String spacesZaNivo(int nivo) throws Exception{
		StringBuilder builder = new StringBuilder();
			for(int i = 0; i < nivo; i++){
			builder.append(" |  ");		
		}
			return builder.toString();
	}
}