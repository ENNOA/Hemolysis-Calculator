/*
 * Jaison Eccleston 23-May-2023
 * Contains getter methods: getLow, getHigh getData
 * Contains results, round methods for calculating values
 * Contains getData method to get data from files.
 * Class to parse and assign data from csv into 2D String lists. A 3D String List is  to be sent
 * to the PdfGenerator class. Uses Switch based on user input from Main to determine from which files
 * values should be taken. Then sends the values to Results class for calculations, and then sends the 3D
 * String list, the String array from Results, SpecTitle Array and number of runs to PdfGenerator for PDF
 * generation.
 */

package hemolysis_V5;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collector {

	public static void switcher(int runs, String select[], String specTitle[], int DF, GUI_FrontEnd gUI_FrontEnd)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		// Variables and declarations
		String[] results = null;

		// Holds the values from the Results method
		double hemoLow1 = 0;
		double hemoHigh1 = 0;
		double hemoLow2 = 0;
		double hemoHigh2 = 0;

		// 2D lists to hold the values taken from the spec
		List<List<String>> records1 = new ArrayList<>();
		List<List<String>> records2 = new ArrayList<>();
		List<List<String>> records3 = new ArrayList<>();
		List<List<String>> records4 = new ArrayList<>();
		List<List<String>> records5 = new ArrayList<>();
		List<List<List<String>>> ListOfRecords = new ArrayList<>();

		// user inputs the number of runs. The number of runs determines the number of
		// files to be parsed
		// and which dilution factor will be used
		switch (runs) {
		case 2:
			records1 = getData(select[0]);
			records2 = getData(select[1]);
			ListOfRecords.add(records1);
			ListOfRecords.add(records2);

			// get needed results from arrays
			hemoLow1 = getLow(0, ListOfRecords);
			hemoHigh1 = getHigh(0, ListOfRecords);
			hemoLow2 = getLow(1, ListOfRecords);
			hemoHigh2 = getHigh(1, ListOfRecords);

			// send results to class for calculations
			results = results(hemoLow1, hemoHigh1, hemoLow2, hemoHigh2, DF);

			// fills array elements with Stringbuilders for sending to PdfGenerator
			PdfGenerator.GeneratePDF(specTitle, ListOfRecords, results, runs, gUI_FrontEnd);
			break;

		case 3:
			records1 = getData(select[0]);
			records2 = getData(select[1]);
			records3 = getData(select[2]);
			ListOfRecords.add(records1);
			ListOfRecords.add(records2);
			ListOfRecords.add(records3);

			// get needed results from arrays
			hemoLow1 = getLow(1, ListOfRecords);
			hemoHigh1 = getHigh(1, ListOfRecords);
			hemoLow2 = getLow(2, ListOfRecords);
			hemoHigh2 = getHigh(2, ListOfRecords);

			// send results to class for calculations
			results = results(hemoLow1, hemoHigh1, hemoLow2, hemoHigh2, DF);

			// send to PdfGenerator for printing
			PdfGenerator.GeneratePDF(specTitle, ListOfRecords, results, runs, gUI_FrontEnd);
			break;

		case 4:
			records1 = getData(select[0]);
			records2 = getData(select[1]);
			records3 = getData(select[2]);
			records4 = getData(select[3]);
			ListOfRecords.add(records1);
			ListOfRecords.add(records2);
			ListOfRecords.add(records3);
			ListOfRecords.add(records4);

			// get needed results from arrays
			hemoLow1 = getLow(2, ListOfRecords);
			hemoHigh1 = getHigh(2, ListOfRecords);
			hemoLow2 = getLow(3, ListOfRecords);
			hemoHigh2 = getHigh(3, ListOfRecords);

			// send results to class for calculations
			results = results(hemoLow1, hemoHigh1, hemoLow2, hemoHigh2, DF);

			// send to PdfGenerator for printing
			PdfGenerator.GeneratePDF(specTitle, ListOfRecords, results, runs, gUI_FrontEnd);
			break;

		case 5:
			records1 = getData(select[0]);
			records2 = getData(select[1]);
			records3 = getData(select[2]);
			records4 = getData(select[3]);
			records5 = getData(select[4]);
			ListOfRecords.add(records1);
			ListOfRecords.add(records2);
			ListOfRecords.add(records3);
			ListOfRecords.add(records4);
			ListOfRecords.add(records5);

			// get needed results from array
			hemoLow1 = getLow(3, ListOfRecords);
			hemoHigh1 = getHigh(3, ListOfRecords);
			hemoLow2 = getLow(4, ListOfRecords);
			hemoHigh2 = getHigh(4, ListOfRecords);

			// send results to class for calculations
			results = results(hemoLow1, hemoHigh1, hemoLow2, hemoHigh2, DF);

			// send to PdfGenerator for printing
			PdfGenerator.GeneratePDF(specTitle, ListOfRecords, results, runs, gUI_FrontEnd);
			break;
		}
	}

	// gets data from row of csv and stores in array split by comma
	static List<List<String>> getData(String line)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		List<List<String>> values = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(line), "UTF-16"))) {
			while ((line = br.readLine()) != null) {
				String[] inputs = line.split(",");
				values.add(Arrays.asList(inputs));
			}
		}
		values.remove(0);
		return values;
	}

	// method to retrieve result from 578nm
	static double getLow(int i, List<List<List<String>>> records) {
		double value = 0.000;
		String lo = (records.get(i).get(9)).get(1);
		value = Double.parseDouble(lo);
		return value;
	}

	// method to retrieve result from 700nm
	static double getHigh(int i, List<List<List<String>>> records) {
		double value = 0.000;
		String high = records.get(i).get(70).get(1);
		value = Double.parseDouble(high);
		return value;
	}

	// calculates results passed from Collector class and returns String aray
	static String[] results(double x1, double y1, double x2, double y2, int df) {
		// declarations and variables
		String X1 = String.format("%.3f", x1);
		String Y1 = String.format("%.3f", y1);
		String X2 = String.format("%.3f", x2);
		String Y2 = String.format("%.3f", y2);

		// StringBuilder results = new StringBuilder();
		String[] results = new String[8];
		double result1 = 0;
		double result2 = 0;
		double avg = 0;

		// calculations
		result1 = ((df * (x1 - y1) * 113) - 0.4);
		result2 = ((df * (x2 - y2) * 113) - 0.4);
		result1 = round(result1, 1);
		result2 = round(result2, 1);
		avg = (result1 + result2) / 2;
		avg = round(avg, 1);

		// outputs to String Array
		results[0] = (X1);
		results[1] = (Y1);
		results[2] = (X2);
		results[3] = (Y2);
		results[4] = (result1 + "");
		results[5] = (result2 + "");
		results[6] = (avg + "");
		results[7] = df + "";

		return results;
	}
	
	// rounds appropriately
	private static double round(double value, int precision) {
		int scale = (int) Math.pow(10, precision);
		return (double) Math.round(value * scale) / scale;
	}

}
