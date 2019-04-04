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
		JPanel panel;
		JButton button;
		JLabel label;
		JTextField textField;
		
		setLayout(new GridLayout(2, 0));
		//JPanel panel = createNamePanel("Board", 0, 0);
		//panel.setLayout(new GridLayout(rows, cols));
		//fillBoard(game, panel, rows, cols);
		//add(panel);
		
		
		// begin row 1
		JPanel row1 = createNamePanel("Row1", 0, 3);
		panel = createNamePanel("", 0, 0);
		textField = new JTextField(20);
		textField.setEditable(false);
		textField.setBackground(null);
		label = new JLabel("Who's Turn?");
		panel.add(label);
		panel.add(textField);
		row1.add(panel);
		// buttons
		panel = createNoNamePanel(0, 2);
		button = new JButton("Next player");
		// button.add() // add listener
		row1.add(button);
		button = new JButton("Make an accusation");
		// button.add() // add listener
		row1.add(button);
		//row1.add(panel);
		add(row1);
		// end row1
		
		// begin row 2
		
		
		
		JPanel controls = createNamePanel("Controls", 0, 0);
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
		
		add(controls);
		

//		panel = createButtonPanel();
//		add(panel);
		// create
	}

	public void fillBoard(Board game, JPanel board, int rows, int cols) {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				JPanel cell = createBoardCell(game.getCellAt(i, j));
				board.add(cell);
			}
		}
	}
	
	 private JPanel createNamePanel(String panelTitle, int grid_rows, int grid_cols) {
	 	JPanel panel = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
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
	 
	 private JPanel createBoardCell(BoardCell boardCell) {
	 	JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,1));
		
		if (boardCell.getInitial() == 'W') {
			panel.setBorder(new EtchedBorder());
			panel.setBackground(Color.yellow);
		} else {
			panel.setBackground(Color.gray);
		}
		return panel;
	}
		 
	public static void main(String[] args) {
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
		//frame.setSize(1000, 800);
		
		GUI_clue gui_clue = new GUI_clue(rows, cols, gameBoard);
		frame.add(gui_clue, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
