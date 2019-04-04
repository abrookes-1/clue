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
	 private JPanel createNamePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,1));
//		 	JLabel nameLabel = new JLabel("Name");
//			name = new JTextField(20);
//			panel.add(nameLabel);
//			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder(), "Who are you?"));
			return panel;
	}
	
	public GUI_clue(int rows, int cols) {
		setLayout(new GridLayout(rows, cols));
		JPanel panel = createNamePanel();
		add(panel);
//		panel = createButtonPanel();
//		add(panel);
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
		frame.setSize(250, 150);
		
		GUI_clue gui_clue = new GUI_clue(rows, cols);
		frame.add(gui_clue, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
