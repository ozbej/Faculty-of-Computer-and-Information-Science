import java.lang.Math;

public class vaja2 
{
	public static void main(String[] args) {
		System.out.println("  n            n!               Stirling(n)         napaka (%)");
		System.out.println("--------------------------------------------------------------");
		int n = 1;
		for (int i = 0; i < 100; i++)
		{
			System.out.printf("%3d", n);
			for (int j = 0; j < 21 - String.valueOf(fakultetaL(n)).length(); j++){
				System.out.print(" ");
			}
			System.out.print(fakultetaL(n));
			for (int j = 0; j < 21 - String.valueOf(fakultetaL(n)).length(); j++){
				System.out.print(" ");
			}
			System.out.print(stirlingL(n));
			System.out.print("      ");
			double nap = napaka(fakultetaL(n), stirlingL(n));
			System.out.print(String.format("%10.7f\n", nap));
			n++;
		}
	}


	public static long fakultetaL(int n){
		long vsota = 1;
		for (int i = 1; i <= n; i++){
			vsota = vsota * i;
		}
		return vsota;
	}

	public static long stirlingL(int n){
		long fakulteta = Math.round(20.90, 2);
		return fakulteta;
	}

	public static double napaka(double x, double y){
		double rez;
		rez = ((x - y) / x) * 100;
		return rez;
	}


}