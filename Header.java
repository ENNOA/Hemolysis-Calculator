/*
 * Jaison Eccleston 11-May-2023
 * Class to build the header for the print out of the hemolysis paperwork
 * Takes user input to generate the the title and blood information at the top of the pdf
 * Contains method to ensure user enters an integer
 */
package hemolysis_V4;

import java.util.InputMismatchException;

public class Header {
	public static String[] header(int df, int runs1) {
		boolean continueInput;
		int choice=0;
		int runs = runs1;
		String sample = null;
		String K = null;
		String lot = null;
		String unit = null;
		String time = null;
		String init = "Initial Hemolysis ";
		String hemo = "Hemolysis";
		String header1 = "";
		String header2 = "";
		String header3 = "";
		String header4 = "";
		String header5 = "";
		String Sentinel = null;
		String[] GDP = new String[6];
		
		//user inputs
		do {
			menu();
			
			//loop with error handling to catch invalid inputs and allow user to try again
			do {
				try{
					choice = Main.input.nextInt();
					continueInput=true;
				}catch(InputMismatchException e) {
					System.out.println("Entry must be a number.\n");
					menu();
					Main.input.nextLine();
					continueInput=false;
				}
			}while (!continueInput);
			
			//Ensures user only enters integers between 1 and 6
			if (choice>0 && choice>6){
				System.out.println("Invalid entry. Try again\n");
				}
		}while(choice>0 && choice>6);
			Main.consoleSpacer(1);
		
		//do-while loop to allow user check if information is correct
		do {
			//switch to determine which inputs are needed for the header
			switch(choice) {
			case 1: System.out.println("Blood Receiving - Whole Blood\n-----------------------------");
					System.out.print("\nEnter the lot number: ");
					lot=Main.input.next();
					System.out.print("Enter the unit number: ");
					unit=Main.input.next();
					System.out.print("Enter the time at which the sample was taken: ");
					time=Main.input.next();
					header1 = lot+" Unit #"+unit+" "+time+" "+hemo+" Run 1";
					header2 = lot+" Unit #"+unit+" "+time+" "+hemo+" Run 2";
					break;
				
			case 2:	System.out.println("Blood Receiving - Plasma\n------------------------");
					System.out.print("K+ testing? Enter Y/N: ");
					K = Main.input.next();
					if (K.equalsIgnoreCase("y")) {
						System.out.print("\nEnter the lot number: ");
						lot=Main.input.next();
						System.out.print("Enter the unit number: ");
						unit=Main.input.next();
						System.out.print("Enter the time at which the sample was taken: ");
						time=Main.input.next();
						header1 = lot+" Unit #"+unit+" "+time+" "+hemo+" Run 1";
						header2 = lot+" Unit #"+unit+" "+time+" "+hemo+" Run 2";	
						}
					
					else {
						System.out.print("\nEnter the lot number: ");
						lot=Main.input.next();
						System.out.print("Enter the unit number: ");
						unit=Main.input.next();
						header1 = lot+" Unit #"+unit+" "+hemo+" Run 1";
						header2 = lot+" Unit #"+unit+" "+hemo+" Run 2";					
						}
						break;	
					
			case 3:	System.out.println("Initial Hemolysis\n-----------------");
					System.out.print("\nEnter the time at which the sample was taken: ");
					time=Main.input.next();
					header1 = init+time+" Run 1";
					header2 = init+time+" Run 2";
					break;
					
			case 4: System.out.println("Sample\n------");
					System.out.print("\nEnter the sample number: ");
					do {
						sample=Main.input.next();
						if (!isInt(sample)) {
							System.out.println("Invalid Entry. \nEnter the sample number: ");
						}
					}while (!isInt(sample));
					System.out.print("Enter the time at which the sample was taken: ");
					time=Main.input.next();
				
					//if statements to create headers in case of dilutions
					if (runs==2) {
						header1 = "Sample #"+sample+" "+time+" "+hemo+" Run 1";
						header2 = "Sample #"+sample+" "+time+" "+hemo+" Run 2";
					}
					else if (runs==3) {
						header1 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header3 = "Sample #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					
					else if (runs==4) {
						header1 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header4 = "Sample #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					
					else if (runs==5) {
						header1 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = 4";
						header4 = "Sample #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header5 = "Sample #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					break;
				
				//if statements to create headers in case of dilutions
			case 5: System.out.println("In-Vivo\n-------");
					System.out.print("\nEnter the In-Vivo number: ");
					do {
						sample=Main.input.next();
						if (!isInt(sample)) {
							System.out.println("Invalid Entry. \nEnter the In-Vivo number: ");
						}
					}while (!isInt(sample));
					System.out.print("Enter the time at which the sample was taken: ");
					time=Main.input.next();
					if (runs==2) {
						header1 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1";
						header2 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 2";
					}
					
					else if (runs==3) {
						header1 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header3 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					
					else if (runs==4) {
						header1 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header4 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					
					else if (runs==5) {
						header1 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = 4";
						header4 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header5 = "In-Vivo #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
						}
					break;
				
				//if statements to create headers in case of dilutions
			case 6: System.out.println("Setpoint\n--------");
					System.out.print("\nEnter the Setpoint number: ");
					do {
						sample=Main.input.next();
						if (!isInt(sample)) {
							System.out.println("Invalid Entry. \nEnter the Setpoint number: ");
						}
					}while (!isInt(sample));
					System.out.print("Enter the time at which the sample was taken: ");
					time=Main.input.next();
					if (runs==2) {
						header1 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1";
						header2 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 2";
						}
					
					else if (runs==3) {
						header1 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header3 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
						}
					
					else if (runs==4) {
						header1 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header4 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
						}
					
					else if (runs==5) {
						header1 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 1";
						header2 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 2";
						header3 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = 4";
						header4 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 1 DF = "+df;
						header5 = "Setpoint #"+sample+" "+time+" "+hemo+" Run 2 DF = "+df;
					}
					break;		
			}
			
			//if statements to check if the extra headers are correct and to assign to headerSend
			Main.consoleSpacer(1);
			if (runs==2) {
				System.out.println("Header for run 1: "+header1);
				System.out.println("Header for run 2: "+header2);
				GDP[0] = header1;
				GDP[1] = header2;
			}
			
			else if (runs==3) {
				System.out.println("Header for run with DF = 1: "+header1);
				System.out.println("Header for run 1 with DF = 2: "+header2);
				System.out.println("Header for run 2 with DF = 2: "+header3);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
			}
			
			else if (runs==4) {
				System.out.println("Header for run with DF = 1: "+header1);
				System.out.println("Header for run with DF = 2: "+header2);
				System.out.println("Header for run 1 with DF = 4: "+header3);
				System.out.println("Header for run 2 with DF = 4: "+header4);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
				GDP[3] = header4;
			}
			
			else {
				System.out.println("Header for run with DF = 1: "+header1);
				System.out.println("Header for run with DF = 2: "+header2);
				System.out.println("Header for run with DF = 4: "+header3);
				System.out.println("Header for run 1 with DF = 8: "+header4);
				System.out.println("Header for run 2 with DF = 8: "+header5);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
				GDP[3] = header4;
				GDP[4] = header5;
			}

			System.out.print("\nIs the header information correct? Y/N  ");
			do {
				try {
					Sentinel=Main.input.next();
					continueInput=true;
				}catch(InputMismatchException ex){
					System.out.println("Try again. Input must be an whole number.");
					Main.input.nextLine();
					continueInput=false;
				}
			}while(!continueInput);
		}while(!Sentinel.equalsIgnoreCase("y"));
		
		return GDP;
	}
	
	//Check if String is an integer. Returns boolean
	public static boolean isInt(String x) {
		try {
			Integer.parseInt(x);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static void menu() {
		System.out.println("\n");
		System.out.println("                       Header Generation                              ");
		System.out.println("----------------------------------------------------------------------");
		System.out.println("1 = Blood Preparation (whole blood)\t 2 = Blood Preparation (plasma)\n"
				+ "3 = Initial Hemolysis\t\t\t"+ " 4 = Sample\n"
				+ "5 = In-Vivo\t\t\t\t 6 = Setpoint\n");
		System.out.print("Are these results for blood preparation, sample, setpoint, or In-Vivo? ");
	}
	
}
