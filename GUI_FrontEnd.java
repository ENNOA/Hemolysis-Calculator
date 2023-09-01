/*Jaison Eccleston 30-May-2023
 * Class to build a gui for the user
 * uses dialog boxes and input fields to take user information
 * contains methods to display text to GUI, get integers and strings from user input,
 * reset image size and dialog boxes to exit loop and terminate program
 *
 */

package hemolysis_V5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GUI_FrontEnd {
	private static JFrame frame;
	private JPanel topPanel;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	private JTextArea textArea;
	private JLabel egg;
	private static ImageIcon TypeO = new ImageIcon(GUI_FrontEnd.class.getResource("/images/TypeO.jpg"));
	private static ImageIcon RBC = new ImageIcon(GUI_FrontEnd.class.getResource("/images/donate.gif"));
	private static ImageIcon small;

	public GUI_FrontEnd() {
		// set the Frame and close ops
		frame = new JFrame("Hemolysis Calculator");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Create the top panel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());

		// Create the right panel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

		// Create the text area and add it to the top panel
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setForeground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		JScrollPane scrollPane = new JScrollPane(textArea);
		topPanel.add(scrollPane, BorderLayout.CENTER);

		// Add the panels to the frame
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(topPanel, BorderLayout.CENTER);
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		Easter();

		// Set the frame size, make it visible, set icon image, and window listener
		frame.setIconImage(TypeO.getImage());
		frame.setSize(900, 650);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// writes text to gui text area
	public void displayPrompt(String prompt) {
		textArea.append(prompt);
	}

	// returns Integer from user input
	public int getIntegerInput(int type) {
		String input = getUserInput(type);
		if (input.equals("")) {
			DontCallMeShirley();
		}

		else if (input.equals(null)) {
			DontCallMeShirley();
		}

		textArea.append(" " + input + "\n");
		return Integer.parseInt(input);
	}

	// Method to exit loops from the getUserInput
	public void DontCallMeShirley() {
		JDialog.setDefaultLookAndFeelDecorated(true);
		int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else if (response == JOptionPane.YES_OPTION) {
			textArea.append("\n  --Resuming program--   \n");
		} else if (response == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
	}

	public void Easter() {
		int rando = (int) (Math.random() * 20) + 1;
		if (rando == 20) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(new BorderLayout());
			egg = new JLabel();
			egg.setIcon(scaler(RBC, 150, 150));
			egg.setHorizontalAlignment(JLabel.CENTER);
			egg.setHorizontalTextPosition(JLabel.CENTER);
			egg.setVerticalTextPosition(JLabel.BOTTOM);
			bottomPanel.add(egg, BorderLayout.CENTER);
			frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		}
	}

	// sets the size of images to x and y
	public ImageIcon scaler(ImageIcon img, int x, int y) {
		small = img;
		Image newImg = small.getImage();
		newImg = newImg.getScaledInstance(x, y, java.awt.Image.SCALE_DEFAULT);
		small = new ImageIcon(newImg);
		return small;

	}

	// returns String from user input
	/*
	 * Modify method to use generics so it accepts both integers and strings
	 * 
	 */
	public String getStringInput(int type) {
		String input = getUserInput(type);
		if (input.equals("")) {
			DontCallMeShirley();
		} else if (input.equals(null)) {
			DontCallMeShirley();
		}
		textArea.append(" " + input + "\n");
		return input;
	}

	// Getter for User Input
	/*
	 * Change method to take a value if value==0 "Enter an integer" if value==1"Enter a String"
	 */
	public String getUserInput(int type) {
		String prompt;
		if(type==0)
			prompt="Enter an Integer";
		else
			prompt="Enter a character";
				
		JOptionPane optionPane = new JOptionPane(prompt, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, null);
		optionPane.setWantsInput(true);
		JDialog dialog = optionPane.createDialog(null, "Input");
		dialog.setLocationRelativeTo(rightPanel);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DontCallMeShirley();
			}
		});
		dialog.setVisible(true);
		String input = (String) optionPane.getInputValue();
		return input;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUI_FrontEnd();
			}
		});
	}

	// Getter for the JTextArea
	public JTextArea getTextArea() {
		return textArea;
	}

}