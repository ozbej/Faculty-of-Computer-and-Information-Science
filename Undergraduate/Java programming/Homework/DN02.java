public class DN02
{
	public static void main(String[] args) {
		String max = "";
		for (int i = 0; i < args.length; i++){
			if (args[i].length() > max.length()){
				max = args[i];
			}
		}
		for (int i = 0; i < max.length()+4; i++){
			System.out.print("*");
		}
		System.out.println("");
		for (int i = 0; i < args.length; i++){
			System.out.print("*");
			for (int j = 0; j < (max.length()+2-args[i].length())/2; j++){
				System.out.print(" ");
			}
			System.out.print(args[i]);
			for (int j = 0; j < (max.length()+2-args[i].length())-((max.length()+2-args[i].length())/2); j++){
				System.out.print(" ");
			}
			System.out.print("*");
			System.out.println("");
		}
		for (int i = 0; i < max.length()+4; i++){
			System.out.print("*");
		}
	}
}