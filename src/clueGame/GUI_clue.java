package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_clue extends JPanel{

	public GUI_clue(int rows, int cols) {
		setLayout(new GridLayout(2, 2));
		JPanel panel = createNamePanel("Board");
		panel.setLayout(new GridLayout(rows, cols));
		fillBoard(panel);
		add(panel);
		panel = createNamePanel("Controls");
		add(panel);

//		panel = createButtonPanel();
//		add(panel);
		// create
	}

	public void fillBoard(JPanel board) {
		
	}
	
	 private JPanel createNamePanel(String panelTitle) {
	 	JPanel panel = new JPanel();
	 	// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,1));
//	 	JLabel nameLabel = new JLabel("Name");
//		name = new JTextField(20);
//		panel.add(nameLabel);
//		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), panelTitle));
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
		frame.setSize(800, 500);
		
		GUI_clue gui_clue = new GUI_clue(rows, cols);
		frame.add(gui_clue, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
