/*
 * Jaison Eccleston	24-May-2023
 *Driver class. 
 *Contains methods to get file names, and console formatting. 
 *Takes user inputs to determine number of files and dilution factor, then sends
 * to other classes for processing.
 *Contains try/catch to ensure user does not input invalid entries.
 *Do/while loop so the user can the program multiple times
 */

package hemolysis_V4;

import java.awt.Desktop;
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
	static String DIRECTORY = System.getProperty("user.dir");

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				boolean continueInput = true;
				int DF = 1;
				int runs = 0;
				int sample = 0;
				String[] specTitle = null;
				String sentinel = null;
				String[] select = new String[5];

				// program begins
				gui.displayPrompt("---------------------------------------------------------------------\n");

				// do-while loop so the user can re do this
				do {
					do {
						// ensures user inputs an integer
						try {
							gui.displayPrompt("\nEnter the number of files to parse: ");
							runs = gui.getIntegerInput();
							continueInput = false;
						} catch (InputMismatchException ex) {
							gui.displayPrompt("\n Input must be an whole number.");
							gui.getTextArea().repaint();
						} catch (NumberFormatException e1) {
							gui.displayPrompt("\n Input must be an whole number.");
							gui.getTextArea().repaint();
						} 
					} while (continueInput);
					continueInput=true;//reset continueInput

					// checks if runs>2 and ensures an integer is entered
					if (runs < 2 || runs > 5) {
						do {
							try {
								gui.displayPrompt(
										"\nMust enter at least 2 files, or no more than 5 files.\n\nEnter the number of files to parse: ");
								runs = 0;
								runs = gui.getIntegerInput();
								continueInput = false;
							} catch (InputMismatchException ex) {
								gui.displayPrompt("\nTry again. Input must be an whole number.");
								gui.getTextArea().repaint();
							} catch (NumberFormatException e1) {
								gui.displayPrompt("\n Input must be an whole number.");
								gui.getTextArea().repaint();
							}
						} while (runs < 2 || runs > 5);
					}

					// ensures an integer is entered
					do {
						gui.displayPrompt("\nDilution Factor should be 1, 2, 4, or 8.");
						gui.displayPrompt("\nEnter the Dilution Factor: ");
						try {
							DF = gui.getIntegerInput();
							if (DF != 1 && DF != 2 && DF != 4 && DF != 8) {
								do {
									gui.displayPrompt("\nTry again. Dilution Factor needs to be 1, 2, 4, or 8\n");
									DF = gui.getIntegerInput();
								} while (DF != 1 && DF != 2 && DF != 4 && DF != 8);
							}
							continueInput = false;
						} catch (InputMismatchException ex) {
							gui.displayPrompt("\nInput Mistmatch. Input must be an whole number.");
							gui.getTextArea().repaint();
							
						} catch (NumberFormatException e1) {
							gui.displayPrompt("\n Number Format. Input must be an whole number.");
							gui.getTextArea().repaint();
						}
					} while (continueInput);
					consoleSpacer(1);

					// file name entry. user will enter two file names each will be saved to a
					// different String for later use

					// loop to display files in directory and assign them to an array
					do {
						gui.displayPrompt("\n\n\t File name selection\n\t--------------------------\n");
						File[] library = Files();
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
							}
						}

						consoleSpacer(1);

						for (int i = 0; i < select.length; i++) {
							if (select[i] != null)
								gui.displayPrompt((i + 1) + ") " + select[i] + "\n");
						}

						gui.displayPrompt("\nIs this correct? Y/N: ");
						do {
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
					do {
						try {
							Collector.switcher(runs, select, specTitle, DF, gui);
						} catch (NullPointerException e) {
							gui.displayPrompt("\n\n"+e+"\n\nNull Pointer Exception:Cannot read the array length because \"a\" is null. Type HALP to call for help!");
							sentinel = gui.getStringInput();
							if (sentinel.equals("HALP"))
								System.exit(0);
							} catch (FileNotFoundException e1) {
								gui.displayPrompt("\n\n"+e1+"\n\nFile Not Found Exception: File is in use by another program. Give up");
								sentinel = gui.getStringInput();
							} catch (IOException e1) {
								gui.displayPrompt("\n\n"+e1+"\n\nIOException: I Dunno, lol lmao");
								sentinel = gui.getStringInput();
							}
						
					} while (sentinel.equalsIgnoreCase("ok"));
					
					gui.displayPrompt(
							"\n\nAre there more files to parse? Press Y to run the program again with new files. Previous files will not be overwritten.");
					sentinel = gui.getStringInput();
					
				} while (sentinel.equalsIgnoreCase("Y"));

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
		File WLData = new File(DIRECTORY);
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
	
	//Open folder for the user
	public static void openFolder() {
		String folderPath = "DIRECTORY"+File.separator+"Hemolysis Results";
        File folder = new File(folderPath);
        if (folder.exists()) {
            try {
                Desktop.getDesktop().open(folder);
            } catch (IOException e) {
            	gui.displayPrompt("Failed to open folder: " + e.getMessage());
            }
        } else {
        	gui.displayPrompt("Folder does not exist.");
        }
	}

	// method to add multiple new lines in the console
	public static void consoleSpacer(int x) {
		for (int i = 0; i < x; i++) {
			gui.displayPrompt("\n");
		}
	}

	public static boolean verify(String sentinel) {
		if (sentinel.equalsIgnoreCase("y"))
			return true;
		else if (sentinel.equalsIgnoreCase("n"))
			return true;
		return false;
	}
	
}
