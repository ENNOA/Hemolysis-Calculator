/*
 * Jaison Eccleston	06-JUL-2023
 * Contains static variables for PDF tables, page size, document, and font size/style
 * Contains method to generate new file names to prevent overwriting
 * Contains getter methods to get data from 3D lists and place then in appropriate table cells
 * Contains methods to set absolute positions for the tables on the pdf page
 * Class to take the strings and lists returned from COLlector and build a PDF file to be printed out and logged.
 * Ensures that the files are not overwritten
 * Fixed Null Pointer Exception by ensuring  that if a folder is not found, it is created.
 */

package hemolysis_V5;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.UnitValue;

public class PdfGenerator {

	// declare PDF, File, and String objects
	private static PdfWriter PdfWriter;
	private static PdfDocument pdfDocument;
	private static Document document;
	private static File file;
	private static String fileName;

	// constants
	private static final String DIRECTORY = System.getProperty("user.dir");
	private static final String LOCATION = DIRECTORY + File.separator + "Hemolysis Results";
	private static final float FONTSIZESMALL = 8f;
	private static final float FONTSIZEMID = 10f;
	private static final float FONTSIZELARGE = 12f;
	private static Style small;
	private static Style mid;
	private static Style large;
	private static Style lambda;

	// constants for the size of the table columns
	private static final UnitValue[] COL4 = UnitValue.createPercentArray(new float[] { 25, 25, 25, 25 });
	private static final UnitValue[] COL2 = UnitValue.createPercentArray(new float[] { 50, 50 });
	private static final UnitValue[] COL = UnitValue.createPercentArray(new float[] { 100 });

	// sets the tables with the specified COLumn widths
	private static Table header1 = new Table(COL);
	private static Table header2 = new Table(COL);
	private static Table specInfo1 = new Table(COL2);
	private static Table specInfo2 = new Table(COL2);
	private static Table data1 = new Table(COL4);
	private static Table data2 = new Table(COL4);
	private static Table run1Calc = new Table(COL);
	private static Table run2Calc = new Table(COL);
	private static Table avg = new Table(COL);

	// This must be here for the program to work
	@SuppressWarnings("unused")
	private GUI_FrontEnd gUI_FrontEnd;

	//Constructor  to initialize pdfGenerator  with reference to gui
	public PdfGenerator(GUI_FrontEnd gUI_FrontEnd) {
		this.gUI_FrontEnd = gUI_FrontEnd;
	}

	// creates new font objects for each loop of the program
	public static void setPdfGenerator() throws IOException {
		PdfFont symbol = PdfFontFactory.createFont(StandardFonts.SYMBOL);
		PdfFont courier = PdfFontFactory.createFont(StandardFonts.COURIER);
		PdfGenerator.small = new Style().setFont(courier).setFontSize(FONTSIZESMALL);
		PdfGenerator.mid = new Style().setFont(courier).setFontSize(FONTSIZEMID);
		PdfGenerator.large = new Style().setFont(courier).setFontSize(FONTSIZELARGE);
		PdfGenerator.lambda = new Style().setFont(symbol);
		PdfGenerator.fileName = FileNamer();
	}

	// method to create and populate pdf
	public static void GeneratePDF(String[] GDP, List<List<List<String>>> records, String[] results, int runs, GUI_FrontEnd gUI_FrontEnd)
			throws IOException {
		setPdfGenerator();
		file = new File(LOCATION, fileName);
		PdfWriter = new PdfWriter(file);
		pdfDocument = new PdfDocument(PdfWriter);
		document = new Document(pdfDocument);

		if (runs == 2) {
			// page 1
			header1 = getHeader(GDP, 0);
			header2 = getHeader(GDP, 1);
			specInfo1 = getSpecInfo(records, 0);
			specInfo2 = getSpecInfo(records, 1);
			data1 = getData(records, 0);
			data2 = getData(records, 1);
			run1Calc = getCalculations(results, 0, 1, 4, false);
			run2Calc = getCalculations(results, 2, 3, 5, true);
			avg = getAverage(results);
			TwoTables(document);
			getFooter(document);
		}

		else if (runs == 3) {
			// page 1
			header1 = getHeader(GDP, 0);
			specInfo1 = (getSpecInfo(records, 0));
			data1 = getData(records, 0);
			OneTable(document);

			// page 2
			document.add(new AreaBreak());
			header1 = getHeader(GDP, 1);
			header2 = getHeader(GDP, 2);
			specInfo1 = getSpecInfo(records, 1);
			specInfo2 = getSpecInfo(records, 2);
			data1 = getData(records, 1);
			data2 = getData(records, 2);
			run1Calc = getCalculations(results, 0, 1, 4, false);
			run2Calc = getCalculations(results, 2, 3, 5, true);
			avg = getAverage(results);
			TwoTables(document);
			getFooter(document);

		}

		else if (runs == 4) {
			// page 1
			header1 = getHeader(GDP, 0);
			header2 = getHeader(GDP, 1);
			specInfo1 = getSpecInfo(records, 0);
			specInfo2 = getSpecInfo(records, 1);
			data1 = getData(records, 0);
			data2 = getData(records, 1);
			TwoTables(document);

			// page 2
			document.add(new AreaBreak());
			header1 = getHeader(GDP, 2);
			header2 = getHeader(GDP, 3);
			specInfo1 = getSpecInfo(records, 2);
			specInfo2 = getSpecInfo(records, 3);
			data1 = getData(records, 2);
			data2 = getData(records, 3);
			run1Calc = getCalculations(results, 0, 1, 4, false);
			run2Calc = getCalculations(results, 2, 3, 5, true);
			avg = getAverage(results);
			TwoTables(document);
			getFooter(document);
		}

		else {
			// page 1
			header1 = getHeader(GDP, 0);
			specInfo1 = getSpecInfo(records, 0);
			data1 = getData(records, 0);
			OneTable(document);

			// page 2
			document.add(new AreaBreak());
			header1 = getHeader(GDP, 1);
			header2 = getHeader(GDP, 2);
			specInfo1 = getSpecInfo(records, 1);
			specInfo2 = getSpecInfo(records, 2);
			data1 = getData(records, 1);
			data2 = getData(records, 2);
			TwoTables(document);

			// page 3
			document.add(new AreaBreak());
			header1 = getHeader(GDP, 3);
			header2 = getHeader(GDP, 4);
			specInfo1 = getSpecInfo(records, 3);
			specInfo2 = getSpecInfo(records, 4);
			data1 = getData(records, 3);
			data2 = getData(records, 4);
			run1Calc = getCalculations(results, 0, 1, 4, false);
			run2Calc = getCalculations(results, 2, 3, 5, true);
			avg = getAverage(results);
			TwoTables(document);
			getFooter(document);

		}

		document.close();

		gUI_FrontEnd.displayPrompt("\nCreating PDF file named " + fileName + "...");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		gUI_FrontEnd.displayPrompt("\n\nDone.\n");
		gUI_FrontEnd.displayPrompt(fileName + " can be found in " + LOCATION + "\\hemolysis results.\n");
	}

	/*
	 * method to generate a name for the PDF by checking other files in the folder
	 * returns String "Hemolysis Results" with a number should this be changed to
	 * Hemolysis Results" and then pull the sample time from the csv?
	 */
	public static String FileNamer() {
		String filename = null;
		File WLData = new File(LOCATION);
		boolean resultsFolder = WLData.exists();
		if (!resultsFolder) {
			WLData.mkdir();
		}
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File WLData, String type) {
				return type.endsWith(".pdf");
			}
		};

		// create array and arrange oldest first
		File[] listOfFiles = WLData.listFiles(filter);
		listOfFiles = Arrays.stream(listOfFiles).filter(Objects::nonNull).toArray(File[]::new);
		Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified));
		filename = "Hemolysis Results" + (listOfFiles.length + 1) + ".pdf";

		// checks for name duplicates and increments to avoid overwriting
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().equals(filename))
				filename = "Hemolysis Results" + (i + 1) + ".pdf";
		}
		return filename;
	}

	// Method to create header table for the PDF
	public static Table getHeader(String[] header, int x) {
		Table GDP = new Table(COL);
		Text t0 = new Text(header[x]);
		GDP.addCell(new Cell().add(new Paragraph(t0))).addStyle(mid);
		return GDP;
	}

	// method to generate the average Hb Free table for the PDF
	public static Table getAverage(String[] calc) {
		Table HbFreeAvg = new Table(COL);
		Cell cell1 = new Cell().setBorder(Border.NO_BORDER);
		Paragraph HBFreeAvg = new Paragraph().addStyle(large);
		HBFreeAvg.addTabStops(new TabStop(75f, TabAlignment.LEFT));
		Text t0 = new Text("Average HbFree = (Run 1+ Run 2)/2)\n");
		Text t1 = new Text("= " + calc[6]);
		Text t2 = new Text(" mg").setTextRise(1).setFontSize(FONTSIZESMALL);
		Text t3 = new Text("/");
		Text t4 = new Text("dL").setTextRise(-1).setFontSize(FONTSIZESMALL);
		HBFreeAvg.add(t0);
		HBFreeAvg.add(new Tab());
		HBFreeAvg.add(t1).add(t2).add(t3).add(t4);
		HbFreeAvg.addCell(cell1.add(HBFreeAvg)).setBorder(Border.NO_BORDER);
		return HbFreeAvg;
	}

	// Method to generate the calculations table for PDF
	public static Table getCalculations(String[] calc, int x, int y, int z, boolean run2)
			throws IOException, PdfException {
		Table hbfree = new Table(COL);
		Cell cell1 = new Cell().setBorder(Border.NO_BORDER);

		Paragraph FreeHemo = new Paragraph().addStyle(large);
		FreeHemo.addTabStops(new TabStop(35f, TabAlignment.LEFT));
		Text t1 = new Text("Hb");
		Text t2 = new Text("Free").setTextRise(-1).addStyle(mid);
		Text t3 = new Text(" = [DFx(");
		Text t4 = new Text("l").addStyle(lambda);// prints the lambda character
		Text t5 = new Text("578").setTextRise(-1).addStyle(small);
		Text t6 = new Text("-l").addStyle(lambda);// prints the lambda character
		Text t7 = new Text("700").setTextRise(-1).addStyle(small);
		Text t8 = new Text(")x113]-0.4\n");
		Text t9 = new Text(("= [" + calc[7] + "x(" + calc[x] + "-" + calc[y] + ")x113]-0.4\n"));
		Text t10 = new Text("Run 1 =  " + calc[z]);
		Text t11 = new Text("mg").setTextRise(1).addStyle(small);
		Text t12 = new Text("/");
		Text t13 = new Text("dL").setTextRise(-1).addStyle(small);
		if (run2) {
			t10 = new Text("Run 2 =  " + calc[z]);
		}
		FreeHemo.add(t1).add(t2).add(t3).add(t4).add(t5).add(t6).add(t7).add(t8);
		FreeHemo.add(new Tab());
		FreeHemo.add(t9).add(t10).add(t11).add(t12).add(t13);
		hbfree.addCell(cell1.add(FreeHemo)).setBorder(Border.NO_BORDER);

		return hbfree;
	}

	// Method to generate table with Spec Information
	public static Table getSpecInfo(List<List<List<String>>> records, int x) {
		Table spec = new Table(COL2);
		for (int i = 0; i < 7; i++) {
			String holder;
			if (i == 4)
				i = 6;
			if (records.get(x).get(i).size() == 1)
				holder = records.get(x).get(i).get(0);
			else
				holder = records.get(x).get(i).get(0) + " " + records.get(x).get(i).get(1);
			spec.addCell(new Cell().add(new Paragraph(holder))).addStyle(small);
		}
		spec.addCell(new Cell().setBorder(Border.NO_BORDER));// adds empty cell without border preserve table dimensions
		return spec;
	}

	// Method to generate wavelength data table
	public static Table getData(List<List<List<String>>> records, int x) {
		Table data = new Table(COL4);
		for (int i = 8; i < records.get(x).size(); i++) {
			data.addCell(new Cell().add(new Paragraph(records.get(x).get(i).get(0)))).addStyle(small);
			data.addCell(new Cell().add(new Paragraph(records.get(x).get(i).get(1)))).addStyle(small);
			if (i == 8) {
				data.addCell(new Cell().add(new Paragraph(records.get(x).get(i).get(0)))).addStyle(small);
				data.addCell(new Cell().add(new Paragraph(records.get(x).get(i).get(1)))).addStyle(small);
			}
		}

		return data;
	}

	// Method to populate and position tables PDF for one sample
	public static void OneTable(Document doc) {
		// Set the absolute position of the lower left corner of each element. Page size
		// is 545f by 842f
		header1.setFixedPosition(136, 806, 298);
		specInfo1.setFixedPosition(136, 747, 298);
		data1.setFixedPosition(136, 277, 298);

		// add elements to document
		doc.add(header1);
		doc.add(specInfo1);
		doc.add(data1);
	}

	// Method to populate and position tables PDF for two sample
	public static void TwoTables(Document doc) {
		// Set the absolute position of the lower left corner of each element. Page size
		// is 545f by 842f
		header1.setFixedPosition(30, 806, 250);
		header2.setFixedPosition(320, 806, 250);
		specInfo1.setFixedPosition(30, 737, 250);
		specInfo2.setFixedPosition(320, 737, 250);
		data1.setFixedPosition(30, 267, 250);
		data2.setFixedPosition(320, 267, 250);
		run1Calc.setFixedPosition(30, 192, 250);
		run2Calc.setFixedPosition(320, 192, 250);
		avg.setFixedPosition(160, 122, 290);

		// add the documents to the page
		doc.add(header1);
		doc.add(header2);
		doc.add(specInfo1);
		doc.add(specInfo2);
		doc.add(data1);
		doc.add(data2);
		doc.add(run1Calc); // Borderless Cell so even if printed won't show
		doc.add(run2Calc);// Borderless Cell so even if printed won't show
		doc.add(avg); // Borderless Cell so even if printed won't show
	}

	// Method to add and position Table at bottom of page of signature
	public static void getFooter(Document doc) {
		Table footer = new Table(COL);
		Cell cell = new Cell().setBorder(Border.NO_BORDER);
		cell.add(new Paragraph("Reviewed and Checked by: __________________________").addStyle(mid));
		footer.addCell(cell);
		footer.setFixedPosition(150, 20, 320);
		doc.add(footer);
	}
}