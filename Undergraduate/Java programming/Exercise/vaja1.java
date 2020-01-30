public class vaja1 
{
	public static void main(String[] args) {
		trikotnik(0, 8);
		pravokotnik(0, 10, 15);
		dvaPravokotnika(2, 5, 7, 3);
	}
	static void pravokotnik(int odmik, int visina, int sirina) {
	for (int i = 0; i < visina; i++){
		for (int j = 0; j < odmik; j++){
			System.out.print(" ");
		}
		for (int j = 0; j < sirina; j++){
			System.out.print("*");
		}
		System.out.println("");
	}
	}

	static void trikotnik(int odmik, int visina){
		int real_odmik = odmik + visina - 1;
		int st = 1;
		for (int i = 0; i < visina; i++){
			for (int j = 0; j < real_odmik; j++){
				System.out.print(" ");
			}
			for (int j = 0; j < st; j++){
				System.out.print("*");
			}
			real_odmik = real_odmik - 1;
			st = st + 2;
			System.out.println("");
		}
	}

	static void okvir(int odmik, int visina, int sirina){
		for (int i = 0; i < visina; i++){
			if (i == 0 || i == (visina - 1)){
				for (int j = 0; j < odmik; j++){
					System.out.print(" ");
				}
				for (int j = 0; j < sirina; j++){
					System.out.print("*");
				}
				System.out.println("");
			}
			else {
				for (int j = 0; j < odmik; j++){
					System.out.print(" ");
				}
				System.out.print("*");
				for (int j = 0; j < sirina-2; j++){
					System.out.print(" ");
				}
				System.out.print("*");
				System.out.println("");
			}
		}
	}

	static void dvaPravokotnika(int odmik, int razmik, int visina, int sirina){
		for (int i = 0; i < visina; i++){
			for (int j = 0; j < odmik; j++){
				System.out.print(" ");
			}
			for (int j = 0; j < sirina; j++){
				System.out.print("*");
			}
			for (int j = 0; j < razmik; j++){
				System.out.print(" ");
			}
			for (int j = 0; j < sirina; j++){
				System.out.print("*");
			}
			System.out.println("");
		}
	}
}
