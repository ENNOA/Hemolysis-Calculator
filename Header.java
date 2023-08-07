/*
 * Jaison Eccleston 23-May-2023
 * Class to build the header for the print out of the hemolysis paperwork
 * Takes user input to generate the the title and blood information at the top of the pdf
 * Contains method to ensure user enters an integer
 */
package hemolysis_V4;

import java.util.InputMismatchException;

public class Header {
	@SuppressWarnings("unused")
	private GUI gui;

	//Constructor  to initialize Header with reference to gui
	public Header(GUI gui) {
		this.gui = gui;
	}

	// generate the header information from user input
	public static String[] header(int df, int runs1, GUI gui) {
		// Header header = new Header(gui);

		boolean continueInput;
		int choice = 0;
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
		String sentinel = null;
		String[] GDP = new String[6];

		// user inputs
		do {
			menu(gui);

			// loop with error handling to catch invalid inputs and allow user to try again
			do {
				try {
					choice = gui.getIntegerInput();
					continueInput = true;
				} catch (InputMismatchException e) {
					gui.displayPrompt("\nEntry must be a number.\n");
					menu(gui);
					continueInput = false;
				} catch (NumberFormatException e1) {
					gui.displayPrompt("\nEntry must be a number.\n");
					menu(gui);
					continueInput = false;
				}

			} while (!continueInput);

			// Ensures user only enters integers between 1 and 6
			if (choice < 1 || choice > 6) {
				gui.displayPrompt("\nInvalid entry. Try again\n");
			}
		} while (choice < 1 || choice > 6);
		Driver.consoleSpacer(1);

		// do-while loop to allow user check if information is correct
		do {
			// switch to determine which inputs are needed for the header
			switch (choice) {
			case 1:
				gui.displayPrompt("\nBlood Receiving - Whole Blood\n-----------------------------");
				gui.displayPrompt("\nEnter the lot number: ");
				lot = gui.getStringInput();
				gui.displayPrompt("\nEnter the unit number: ");
				unit = gui.getStringInput();
				gui.displayPrompt("\nEnter the time at which the sample was taken: ");
				time = gui.getStringInput();
				header1 = lot + " Unit #" + unit + " " + time + " " + hemo + " Run 1";
				header2 = lot + " Unit #" + unit + " " + time + " " + hemo + " Run 2";
				break;

			case 2:
				gui.displayPrompt("\nBlood Receiving - Plasma\n------------------------");
				gui.displayPrompt("\nK+ testing? Enter Y/N: ");
				K = gui.getStringInput();
				if (K.equalsIgnoreCase("y")) {
					gui.displayPrompt("\nEnter the lot number: ");
					lot = gui.getStringInput();
					gui.displayPrompt("\nEnter the unit number: ");
					unit = gui.getStringInput();
					gui.displayPrompt("\nEnter the time at which the sample was taken: ");
					time = gui.getStringInput();
					header1 = lot + " Unit #" + unit + " " + time + " " + hemo + " Run 1";
					header2 = lot + " Unit #" + unit + " " + time + " " + hemo + " Run 2";
				}

				else {
					gui.displayPrompt("\nEnter the lot number: ");
					lot = gui.getStringInput();
					gui.displayPrompt("\nEnter the unit number: ");
					unit = gui.getStringInput();
					header1 = lot + " Unit #" + unit + " " + hemo + " Run 1";
					header2 = lot + " Unit #" + unit + " " + hemo + " Run 2";
				}
				break;

			case 3:
				gui.displayPrompt("\nInitial Hemolysis\n-----------------");
				gui.displayPrompt("\nEnter the time at which the sample was taken: ");
				time = gui.getStringInput();
				header1 = init + time + " Run 1";
				header2 = init + time + " Run 2";
				break;

			case 4:
				gui.displayPrompt("\nSample\n------");
				gui.displayPrompt("\nEnter the sample number: ");
				do {
					sample = gui.getStringInput();
					if (!isInt(sample)) {
						gui.displayPrompt("\nInvalid Entry. \nEnter the sample number: ");
					}
				} while (!isInt(sample));
				gui.displayPrompt("\nEnter the time at which the sample was taken: ");
				time = gui.getStringInput();

				// if statements to create headers in case of dilutions
				if (runs == 2) {
					header1 = "Sample #" + sample + " " + time + " " + hemo + " Run 1";
					header2 = "Sample #" + sample + " " + time + " " + hemo + " Run 2";
				} else if (runs == 3) {
					header1 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header3 = "Sample #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 4) {
					header1 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header4 = "Sample #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 5) {
					header1 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = 4";
					header4 = "Sample #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header5 = "Sample #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}
				break;

			// if statements to create headers in case of dilutions
			case 5:
				gui.displayPrompt("\nIn-Vivo\n-------");
				gui.displayPrompt("\nEnter the In-Vivo number: ");
				do {
					sample = gui.getStringInput();
					if (!isInt(sample)) {
						gui.displayPrompt("\nInvalid Entry. \nEnter the In-Vivo number: ");
					}
				} while (!isInt(sample));
				gui.displayPrompt("\nEnter the time at which the sample was taken: ");
				time = gui.getStringInput();
				if (runs == 2) {
					header1 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1";
					header2 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 2";
				}

				else if (runs == 3) {
					header1 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header3 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 4) {
					header1 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header4 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 5) {
					header1 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = 4";
					header4 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header5 = "In-Vivo #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}
				break;

			// if statements to create headers in case of dilutions
			case 6:
				gui.displayPrompt("\nSetpoint\n--------");
				gui.displayPrompt("\nEnter the Setpoint number: ");
				do {
					sample = gui.getStringInput();
					if (!isInt(sample)) {
						gui.displayPrompt("\nInvalid Entry. \nEnter the Setpoint number: ");
					}
				} while (!isInt(sample));
				gui.displayPrompt("\nEnter the time at which the sample was taken: ");
				time = gui.getStringInput();
				if (runs == 2) {
					header1 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1";
					header2 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 2";
				}

				else if (runs == 3) {
					header1 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header3 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 4) {
					header1 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header4 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}

				else if (runs == 5) {
					header1 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 1";
					header2 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 2";
					header3 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = 4";
					header4 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 1 DF = " + df;
					header5 = "Setpoint #" + sample + " " + time + " " + hemo + " Run 2 DF = " + df;
				}
				break;
			}

			// if statements to check if the extra headers are correct and to assign to
			// headerSend
//			Driver.consoleSpacer(1);
			if (runs == 2) {
				gui.displayPrompt("\nHeader for run 1: " + header1);
				gui.displayPrompt("\nHeader for run 2: " + header2);
				GDP[0] = header1;
				GDP[1] = header2;
			}

			else if (runs == 3) {
				gui.displayPrompt("\nHeader for run with DF = 1: " + header1);
				gui.displayPrompt("\nHeader for run 1 with DF = 2: " + header2);
				gui.displayPrompt("\nHeader for run 2 with DF = 2: " + header3);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
			}

			else if (runs == 4) {
				gui.displayPrompt("\nHeader for run with DF = 1: " + header1);
				gui.displayPrompt("\nHeader for run with DF = 2: " + header2);
				gui.displayPrompt("\nHeader for run 1 with DF = 4: " + header3);
				gui.displayPrompt("\nHeader for run 2 with DF = 4: " + header4);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
				GDP[3] = header4;
			}

			else {
				gui.displayPrompt("\nHeader for run with DF = 1: " + header1);
				gui.displayPrompt("\nHeader for run with DF = 2: " + header2);
				gui.displayPrompt("\nHeader for run with DF = 4: " + header3);
				gui.displayPrompt("\nHeader for run 1 with DF = 8: " + header4);
				gui.displayPrompt("\nHeader for run 2 with DF = 8: " + header5);
				GDP[0] = header1;
				GDP[1] = header2;
				GDP[2] = header3;
				GDP[3] = header4;
				GDP[4] = header5;
			}

			gui.displayPrompt("\n\nIs the header information correct? Y/N  ");
			do {
				sentinel = gui.getStringInput();
				if (!Driver.verify(sentinel))
					gui.displayPrompt("\nInvalid input. Enter Y or N");
			} while (!Driver.verify(sentinel));

		} while (sentinel.equalsIgnoreCase("N"));

		return GDP;
	}

	// Check if String is an integer. Returns boolean
	public static boolean isInt(String x) {
		try {
			Integer.parseInt(x);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// display menu to gui text area
	public static void menu(GUI gui) {
		gui.displayPrompt("\n");
		gui.displayPrompt("\n                       Header Generation                              ");
		gui.displayPrompt("\n----------------------------------------------------------------------");
		gui.displayPrompt("\n1 = Blood Preparation (whole blood)\t 2 = Blood Preparation (plasma)"
				+ "\n3 = Initial Hemolysis\t\t" + " 4 = Sample\n" + "5 = In-Vivo\t\t\t 6 = Setpoint");
		gui.displayPrompt("\n\nAre these results for blood preparation, sample, setpoint, or In-Vivo? ");
	}

}
