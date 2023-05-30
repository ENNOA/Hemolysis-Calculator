/*Jaison Eccleston 24-May-2023
 * Class to build a gui for the user
 * uses dialog boxes and input fields to take user information
 * contains methods
 * 
 */

package hemolysis_V4;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class GUI {
	private static JFrame frame;
	private JPanel topPanel;
	private JPanel rightPanel;
	private JTextArea textArea;
	private static ImageIcon TypeO;

	public GUI() {
		frame = new JFrame("Hemolysis Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		// Create the top panel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		// Create the right panel
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		

		// Create the text area and add it to the top panel
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		topPanel.add(scrollPane, BorderLayout.CENTER);

		// Add the panels to the frame
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(topPanel, BorderLayout.CENTER);
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);

		// Set the frame size, make it visible, set icon image, and window listener
		TypeO = new ImageIcon("TypeO.jpg");
		frame.setIconImage(TypeO.getImage());
		frame.setSize(900, 650);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}



	public void displayPrompt(String prompt) {
		textArea.append(prompt);
	}

	public int getIntegerInput() {
		String input = getUserInput();
			if (input.equals("")) {
				DontCallMeShirley();
			}

			else if (input.equals(null)) {
				DontCallMeShirley();
			}
			
//			else if (!Header.isInt(input)) {
//				textArea.append(" " + input + "\n");
//			}

		textArea.append(" " + input + "\n");
		return Integer.parseInt(input);
	}

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

	public String getStringInput() {
		String input = getUserInput();
		if (input.equals("")) {
			DontCallMeShirley();
		} else if (input.equals(null)) {
			DontCallMeShirley();
		}
		textArea.append(" " + input + "\n");
		return input;
	}

	// Getter for UserInput
	public String getUserInput() {
		JOptionPane optionPane = new JOptionPane("Enter an integer", JOptionPane.PLAIN_MESSAGE,
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
			public void run() {
				new GUI();
			}
		});
	}

	// Getter for the JTextArea
	public JTextArea getTextArea() {
		return textArea;
	}
	
}