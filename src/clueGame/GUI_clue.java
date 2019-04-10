package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_clue extends JFrame{

	public GUI_clue(Board game) {
		setSize(660, 660);
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(game);
		JPanel panel = controlPanel();
		add(panel, BorderLayout.SOUTH);
		
		JMenuBar fileMenu = new JMenuBar();
		setJMenuBar(fileMenu);
		fileMenu.add(createFileMenu());
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createNoteItem());
		menu.add(createExitItem());
		return menu;
	}
	
	private JMenuItem createExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		
		return item;
	}
	
	private JMenuItem createNoteItem() {
		JMenuItem item = new JMenuItem("Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		
		return item;
	}

	private JPanel controlPanel() {
		JTextField whoseTurn = new JTextField(14);
		whoseTurn.setEditable(false);
		whoseTurn.setBackground(null);
		JTextField die = new JTextField(3);
		die.setEditable(false);
		die.setBackground(null);
		JTextField guess = new JTextField(20);
		guess.setEditable(false);
		guess.setBackground(null);
		JTextField guessResult = new JTextField(10);
		guessResult.setEditable(false);
		guessResult.setBackground(null);
		JLabel label = new JLabel("Whose Turn?");
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		JPanel row = new JPanel();
		row.setLayout(new GridLayout(0,3));
		JPanel col = new JPanel();

		// Row 1
		col.add(label);
		col.add(whoseTurn);
		row.add(col);
		
		col = new JPanel();
		col.setLayout(new GridLayout(0,1));
		col.add(nextPlayer);
		row.add(col);
		
		col = new JPanel();
		col.setLayout(new GridLayout(0,1));
		col.add(makeAccusation);
		row.add(col);
		
		panel.add(row);
		
		// Row 2
		row = new JPanel();
		col = new JPanel();
		col.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		label = new JLabel("Roll");
		col.add(label);
		col.add(die);
		row.add(col);
		
		col = new JPanel();
		col.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		label = new JLabel("Guess");
		col.add(label);
		col.add(guess);
		row.add(col);
		
		col = new JPanel();
		col.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		label = new JLabel("Response");
		col.add(label);
		col.add(guessResult);
		row.add(col);
		
		panel.add(row);
		
		return panel;
	}
	
	private JPanel displayBoard(Board game, Graphics g) {
		JPanel panel = new JPanel();
		
		
		
		return panel;
	}
	/*
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
		
	 */
	public static void main(String[] args) {
		// Set up board & game
		Board gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueMap.csv", "RoomKey.txt", "players.txt", "weapons.txt");
		gameBoard.initialize();
		
		GUI_clue gui = new GUI_clue(gameBoard);
		gui.setVisible(true);
	}
}
