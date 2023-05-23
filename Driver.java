/*
 * Jaison Eccleston	15-May-2023
 *Driver class. 
 *Contains methods to get file names, and console formatting. 
 *Takes user inputs to determine number of files and dilution factor, then sends
 * to other classes for processing.
 *Contains try/catch to ensure user does not input invalid entries.
 *Do/while loop so the user can the program multiple times
 */

package hemolysis_V4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;

import javax.swing.SwingUtilities;

public class Driver {
	static GUI gui = new GUI();

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Header header = new Header(gui);
				// variables and declarations
				// int input;// = gui.getIntegerInput();
				// String inputString = null;// = gui.getStringInput();
				boolean continueInput = true;
				int DF = 1;
				int runs = 0;
				int sample = 0;
				String[] specTitle = null;
				String sentinel = null;
				String[] select = new String[5];

				// program begins
				gui.displayPrompt("----------------------------------------\n");

				// do-while loop so the user can re do this
				do {
					do {
						// ensures user inputs an integer
						try {
							gui.displayPrompt("\nEnter the number of files to parse: ");
							runs = gui.getIntegerInput();
							continueInput = false;
						} catch (InputMismatchException ex) {
							gui.displayPrompt("\nTry again. Input must be an whole number.");
							// gui.getStringInput();
						}
					} while (continueInput);

					// checks if runs>2 and ensures an integer is entered
					if (runs < 2 || runs > 5) {
						do {
							try {
								gui.displayPrompt(
										"\nMust enter at least 2 files, or no more than 5 files.\n\nEnter the number of files to parse: ");
								runs = 0;
								runs = gui.getIntegerInput();
							} catch (InputMismatchException ex) {
								gui.displayPrompt("\nTry again. Input must be an whole number.");
								// gui.getStringInput();
							}
						} while (runs < 2 || runs > 5);
					}

					// ensures an integer is entered
					do {
						gui.displayPrompt("\nDilution Factor should be 1, 2, 4, or 8.");
						gui.displayPrompt("\nEnter the Dilution Factor: ");
						try {
							DF = gui.getIntegerInput();
							if (DF <= 0 || DF == 3 || DF > 4 && DF < 8 || DF >= 9) {
								do {
									gui.displayPrompt("\nTry again. Dilution Factor needs to be 1, 2, 4, or 8");
									DF = gui.getIntegerInput();
								} while (DF == 3 || DF > 4 && DF < 8 || DF > 9);
							}
						} catch (InputMismatchException ex) {
							gui.displayPrompt("\nTry again. Input must be an whole number.");
							continueInput = false;
							// input;
						}
					} while (continueInput);
					consoleSpacer(1);

					// file name entry. user will enter two file names each will be saved to a
					// different String for later use
					gui.displayPrompt("\n\n\t File name selection\n\t---------------------\n");
					File[] library = Files();

					// loop to display files in directory and assign them to an array
					do {
						continueInput = false;
						gui.displayPrompt("\n\nEnter the number of the first file to be parsed: ");
						while (!continueInput) {
							try {
								for (int i = 0; i < runs; i++) {
									sample = (gui.getIntegerInput()) - 1;
									gui.displayPrompt("\nrun " + (i + 1) + " = " + library[sample].getName() + "\n");
									select[i] = library[sample].getName();
									if (i != (runs - 1))
										gui.displayPrompt("\nEnter the number of the next file to be parsed: ");
								}
								continueInput = true;
							} catch (Exception ex) {
								gui.displayPrompt(
										"\nInvalid Entry. Re-enter the number of the first file to be parsed: ");
								// gui.getStringInput();
								// sample= (input)-1;
							}
						}

						consoleSpacer(1);

						for (int i = 0; i < select.length; i++) {
							if (select[i] != null)
								gui.displayPrompt((i + 1) + ") " + select[i] + "\n");
						}

						gui.displayPrompt("\nIs this correct? Y/N: ");
						do {
							// gui.getStringInput();
							sentinel = gui.getStringInput();
							if (!verify(sentinel))
								gui.displayPrompt("\nInvalid input. Enter Y or N");
						} while (!verify(sentinel));

					} while (sentinel.equalsIgnoreCase("N"));

					// runs class to generate header information
					specTitle = Header.header(DF, runs, gui);
					consoleSpacer(1);

					gui.displayPrompt("\nParsing...");

					// catch filenotfoundexception due the the PDF being open
					try {
						Collector.switcher(runs, select, specTitle, DF, gui);
					} catch (Throwable e) {
						do {
							gui.displayPrompt(
									"\n\nFile not Found Exception: The file is being used by another process. Close all files generated by this program and type \"OK\" to proceed.");
							// gui.getStringInput();
							sentinel = gui.getStringInput();
							try {
								Collector.switcher(runs, select, specTitle, DF, gui);
							} catch (UnsupportedEncodingException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} while (sentinel.equalsIgnoreCase("ok"));
					}
					gui.displayPrompt(
							"\n\nAre there more files to parse? Press Y to run the program again with new files. Previous files will not be overwritten.");
					// gui.getStringInput();
					sentinel = gui.getStringInput();

				} while (sentinel.equalsIgnoreCase("Y"));

				// input.close();
				System.exit(0);
			}
		});
	}

	/*
	 * method to display which files are in the folder and ready to be parsed
	 * filters out non .csv files, lists them in order of last modified and lists
	 * files in 3 columns
	 */
	public static File[] Files() {
		File WLData = new File("C:\\Users\\jaison.eccleston\\eclipse-workspace\\Hemolysis Calculator2");
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File WLData, String type) {
				return type.endsWith(".csv");
			}
		};

		File[] listOfFiles = WLData.listFiles(filter);
		Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				if ((i + 1) < 10)
					gui.displayPrompt((i + 1) + ")  " + listOfFiles[i].getName() + "\t");
				else
					gui.displayPrompt((i + 1) + ") " + listOfFiles[i].getName() + "\t");
				if ((i + 1) % 3 == 0)
					gui.displayPrompt("\n");
			}
		}

		return listOfFiles;
	}

	// method to add multiple new lines in the console
	public static void consoleSpacer(int x) {
		for (int i = 0; i < x; i++) {
			gui.displayPrompt("\n");
		}
	}

	// method to add multiple tabs in strings
	public static String tabber(int x) {
		String tabs = "";
		for (int i = 0; i < x; i++)
			tabs = tabs + "\t";
		return tabs;
	}

	// method to add multiple new lines in strings
	public static String spacerString(int x) {
		String spaces = "";
		for (int i = 0; i < x; i++)
			spaces = spaces + "\n";
		return spaces;
	}

	public static boolean verify(String sentinel) {
		if (sentinel.equalsIgnoreCase("y"))
			return true;
		else if (sentinel.equalsIgnoreCase("n"))
			return true;
		return false;
	}
}
