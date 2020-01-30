import java.lang.Math;

public class DN03 
{
	public static void main(String[] args) {
			if (args[0].equals("min")){
				double min = Integer.MAX_VALUE;
				String output = "Minimum stevil";
				for (int i = 1; i < args.length; i++){
					if (Double.parseDouble(args[i]) < min){
						min = Double.parseDouble(args[i]);
					}
					if (args[i].contains(".")){
						output = output + " " + args[i];
					}
					else {
						int y = Integer.parseInt(args[i]);
						output = output + " " + args[i];
					}
				}
				System.out.print(output + " je ");
				System.out.printf("%.2f\n", min);
			}
			else{
				double rez;
				switch(args[1]){
				case "+":
					System.out.print(args[0] + " + " + args[2] + " = ");
					rez = Double.parseDouble(args[0]) + Double.parseDouble(args[2]);
					System.out.printf("%.2f\n", rez);
					break;
				case "-":
					System.out.print(args[0] + " - " + args[2] + " = ");
					rez = Double.parseDouble(args[0]) - Double.parseDouble(args[2]);
					System.out.printf("%.2f\n", rez);
					break;
				case "#":
					System.out.print(args[0] + " * " + args[2] + " = ");
					rez = Double.parseDouble(args[0]) * Double.parseDouble(args[2]);
					System.out.printf("%.2f\n", rez);
					break;
				case "/":
					System.out.print(args[0] + " / " + args[2] + " = ");
					rez = Double.parseDouble(args[0]) / Double.parseDouble(args[2]);
					System.out.printf("%.2f\n", rez);
					break;
				case "^":
					System.out.print(args[0] + " ^ " + args[2] + " = ");
					rez = Math.pow(Double.parseDouble(args[0]), Double.parseDouble(args[2]));
					System.out.printf("%.2f\n", rez);
					break;
				default:
					System.out.print("Niste vpisali veljavne funkcije!");
			}
			}
		}
	}