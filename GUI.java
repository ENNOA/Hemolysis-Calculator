package hemolysis_V4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI {
	private JFrame frame;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JTextArea textArea;
	private JTextField inputField;
	private JButton submit;

	public GUI() {
		frame = new JFrame("Hemolysis Calculator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the top panel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());

		// Create the text area and add it to the top panel
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		topPanel.add(scrollPane, BorderLayout.CENTER);

		// Create the bottom panel
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());

		// Create the input field and add it to the bottom panel
		inputField = new JTextField();
		bottomPanel.add(inputField, BorderLayout.CENTER);

		// Create the button and add it to the bottom panel
		submit = new JButton("Submit");
		bottomPanel.add(submit, BorderLayout.EAST);

		// Add the panels to the frame
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(topPanel, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		// Add action listener to the button
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addInputToList();
			}
		});

		// Add key listener to the input field
		inputField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					addInputToList();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		// Set the frame size and make it visible
		ImageIcon blood = new ImageIcon(
				"C:\\Users\\jaison.eccleston\\eclipse-workspace\\Hemolysis Calculator2\\src\\hemolysis_V4\\TypeO.jpg");
		frame.setIconImage(blood.getImage());
		frame.setSize(900, 500);
		frame.setVisible(true);

	}

	private void addInputToList() {
		String input = inputField.getText();
		parseInput(input);
		textArea.append(input + "\n");
		inputField.setText("");
	}

	public void displayPrompt(String prompt) {
		textArea.append(prompt);
	}

//	public int getIntegerInput() {
//        while (inputField.getText().isEmpty()) {
//            try {
//                Thread.sleep(100); // Wait for 100 milliseconds
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        String input = inputField.getText();
//        try {
//            return Integer.parseInt(input);
//        } catch (NumberFormatException e) {
//            // Handle invalid input
//            return 0; // Return a default value or handle the error case as needed
//        }
//    }

	public int getIntegerInput() {
		String input = JOptionPane.showInputDialog(frame, "Enter an integer");
		textArea.append(" " + input + "\n");
		return Integer.parseInt(input);
	}

	public String getStringInput() {
		String input = JOptionPane.showInputDialog(frame, "Enter a string");
		textArea.append(" " + input + "\n");
		return input;
	}

	private Object parseInput(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return input;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}
}