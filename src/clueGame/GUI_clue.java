package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_clue extends JPanel{

	public GUI_clue(int rows, int cols, Board game) {
		setLayout(new GridLayout(2,0));
		JPanel panel = controlPanel();
		add(panel);
		panel = displayPanel();
		add(panel);

	}

	private JPanel controlPanel() {
		JTextField whoseTurn = new JTextField(20);
		whoseTurn.setEditable(false);
		whoseTurn.setBackground(null);
		JLabel label = new JLabel("Whose Turn?");
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		JPanel col1 = new JPanel();
		JPanel col2 = new JPanel();
		JPanel col3 = new JPanel();
		
		

		col1.add(label);
		col1.add(whoseTurn);
		col2.add(nextPlayer);
		col3.add(makeAccusation);
		panel.add(col1);
		panel.add(col2);
		panel.add(col3);
		return panel;
	}
	
	private JPanel displayPanel() {
		JPanel panel = new JPanel();
		/*
		panel = createNamePanel("Die", 0, 0);
		textField = new JTextField(6);
		textField.setEditable(false);
		textField.setBackground(null);
		label = new JLabel("Roll");
		panel.add(label);
		panel.add(textField);
		controls.add(panel);
		panel = createNamePanel("Guess", 0, 0);
		textField = new JTextField(35);
		textField.setEditable(false);
		textField.setBackground(null);
		label = new JLabel("Guess");
		panel.add(label);
		panel.add(textField);
		controls.add(panel);
		panel = createNamePanel("Guess Result", 0, 0);
		textField = new JTextField(16);
		textField.setEditable(false);
		textField.setBackground(null);
		label = new JLabel("Response");
		panel.add(label);
		panel.add(textField);
		controls.add(panel);
		*/
		return panel;
	}
	
	 private JPanel createNamePanel(String panelTitle, int grid_rows, int grid_cols) {
	 	JPanel panel = new JPanel();
	 	if (grid_rows != 0 || grid_cols != 0) {
	 		panel.setLayout(new GridLayout(grid_rows, grid_cols));
	 	}
		panel.setBorder(new TitledBorder (new EtchedBorder(), panelTitle));
		return panel;
	}
	 
	 private JPanel createNoNamePanel(int grid_rows, int grid_cols) {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
		 	if (grid_rows != 0 || grid_cols != 0) {
		 		panel.setLayout(new GridLayout(grid_rows, grid_cols));
		 	}
			panel.setBorder(new EtchedBorder());
			return panel;
		}
		
	 
	public static void main(String[] args) {
		// Set up board & game
		Board gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueMap.csv", "RoomKey.txt", "players.txt", "weapons.txt");
		gameBoard.initialize();
		int rows = gameBoard.getNumRows();
		int cols = gameBoard.getNumColumns();
		
		// begin gui functions
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(1000, 300);
		//frame.setSize(1000, 800); // size with board
		
		GUI_clue gui = new GUI_clue(rows, cols, gameBoard);
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	}
}
