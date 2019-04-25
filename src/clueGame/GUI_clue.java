package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GUI_clue extends JFrame{
	NotesDialog notes;
	SugDialog sug;
	AccusationDialog acc;
	static Board gameBoard;

	// Constructor Method 
	public GUI_clue() {
		setSize(660, 660);
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(gameBoard);
		JPanel panel = controlPanel();
		add(panel, BorderLayout.SOUTH);
		
		JMenuBar fileMenu = new JMenuBar();
		setJMenuBar(fileMenu);
		fileMenu.add(createFileMenu());
		
		notes = new NotesDialog(gameBoard);
		notes.setLocationRelativeTo(this);
		notes.setSize(500, 600);
		
		sug = new SugDialog(gameBoard);
		sug.setLocationRelativeTo(this);
		sug.setSize(300, 200);
		
		acc = new AccusationDialog(gameBoard);
		acc.setLocationRelativeTo(this);
		acc.setSize(300, 200);
		
		
		String message = "You are " + gameBoard.getHuman().getCharacter() + ". Are you ready to play Clue?";
		JOptionPane.showMessageDialog(this, message, "Welcom to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		add(cardsPanel(gameBoard), BorderLayout.EAST);
		
		
		addMouseListener(new MouseSelectListener());
	}
	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Pop-up Dialogues 		~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	private void displayUnfinishedTurn() {
		JOptionPane.showMessageDialog(this, "Please finish your turn", "Turn Unfinished", JOptionPane.INFORMATION_MESSAGE);
	}
	private void displayPlayerWin(Solution accusation) {
		JOptionPane.showMessageDialog(this, accusation.toString() + "\n" + "Is Correct\n" + "\n" + "You Win", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
	}
	private void displayComputerWin(Solution accusation, String character) {
		JOptionPane.showMessageDialog(this, character + " Guessed\n" + accusation.toString() + "\n" + "Correctly" + "\n" + "You Lose", "A Player Has Won", JOptionPane.INFORMATION_MESSAGE);
	}
	private void displayWrongAcc(Solution accusation) {
		JOptionPane.showMessageDialog(this, accusation.toString() + "\n" + "Is Incorrect\n", "Incorrect Accusation", JOptionPane.INFORMATION_MESSAGE);
	}
	private void displayNotTurn() {
		JOptionPane.showMessageDialog(this, "Wait until your turn to make an accusation", "Illegal Action", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~		Listeners		 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	//
	private class NextTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gameBoard.updateSeen();
			Solution ans = gameBoard.getAnswer();
			if (gameBoard.isFinished()){
				gameBoard.startNextPlayer();
				Solution acc = gameBoard.getCurrentPlayer().makeAccusation();
				if (acc != null) {
					if (gameBoard.checkAccusation(acc)) {
						displayComputerWin(acc, gameBoard.getCurrentPlayer().getCharacter());
					}
				}
				die.setText(Integer.toString(gameBoard.getDie()));
				whoseTurn.setText(gameBoard.getCurrentPlayer().getCharacter());
				guessResult.setText(gameBoard.getResponse());
				guess.setText(gameBoard.getLastGuess().toString());
			} else {
				displayUnfinishedTurn();
			}
		}
	}
	
	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (gameBoard.getCurrentPlayer().isHuman && gameBoard.isFinished() != true) {
				acc.setVisible(true);
			} else {
				displayNotTurn();
			}
		}
	}
	
	private class MouseSelectListener implements MouseListener {
		public void mousePressed (MouseEvent event) {}
		public void mouseReleased (MouseEvent event) {}
		public void mouseEntered (MouseEvent event) {}
		public void mouseExited (MouseEvent event) {}
		public void mouseClicked (MouseEvent event) {
			for (BoardCell target:gameBoard.getTargets()) {
				if (target.containsClick(event.getX(), event.getY())) {
					gameBoard.getCurrentPlayer().setRow(target.getRow());
					gameBoard.getCurrentPlayer().setCol(target.getColumn());
					gameBoard.getTargets().clear();
					repaint();
					if (gameBoard.humanPlayerIsInRoom()) {
						// open dialog for human player to make suggestion
						sug.setVisible(true);
						roomAnswer.setText(gameBoard.getCurrentPlayer().getRoom());
					}
					gameBoard.humanFinished();
					break;
				}
			}
		}
	}
	
	private class SugSubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Solution sugg = new Solution(personAnswer.getSelectedItem().toString(), weaponAnswer.getSelectedItem().toString(), roomAnswer.getText());
			gameBoard.handleSuggestion(sugg, gameBoard.getCurrentPlayer());
			guessResult.setText(gameBoard.getResponse());
			guess.setText(gameBoard.getLastGuess().toString());
			gameBoard.repaint();
			
			sug.setVisible(false);
		}
	}
	
	private class SugCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sug.setVisible(false);
		}
	}
	
	private class AccSubmitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Solution sugg = new Solution(personAnswerChoose.getSelectedItem().toString(), weaponAnswerChoose.getSelectedItem().toString(), roomAnswerChoose.getSelectedItem().toString());
			boolean checkWin = gameBoard.checkAccusation(sugg);
			if (checkWin) {
				displayPlayerWin(sugg);
			} else {
				displayWrongAcc(sugg);
				// ensure human can't perform any more actions this turn 
				gameBoard.getTargets().clear();
				gameBoard.humanFinished(); 
				repaint();
			}
			
			acc.setVisible(false);
		}
	}
	
	private class AccCancelListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			acc.setVisible(false);
		}
	}

	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Specific Dialogues		~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	JComboBox roomAnswerChoose;
	JComboBox personAnswerChoose;
	JComboBox weaponAnswerChoose;
	JButton submitAcc;
	JButton cancelAcc;
	
	private class AccusationDialog extends JDialog {
		public AccusationDialog(Board game) {
			setLayout(new GridLayout(4,2));
			JTextField yourRoom = new JTextField("Your Room");
			JTextField person = new JTextField("Person");
			JTextField weapon = new JTextField("Weapon");
			yourRoom.setEditable(false);
			person.setEditable(false);
			weapon.setEditable(false);
			yourRoom.setBackground(null);
			person.setBackground(null);
			weapon.setBackground(null);
			roomAnswerChoose = new JComboBox(gameBoard.getRooms().toArray());
			personAnswerChoose = new JComboBox(gameBoard.getCharacters().toArray());
			weaponAnswerChoose = new JComboBox(gameBoard.getWeapons().toArray());
			submitAcc = new JButton("Submit");
			cancelAcc = new JButton("Cancel");
			submitAcc.addActionListener(new AccSubmitListener());
			cancelAcc.addActionListener(new AccCancelListener());
			add(yourRoom);
			add(roomAnswerChoose);
			add(person);
			add(personAnswerChoose);
			add(weapon);
			add(weaponAnswerChoose);
			add(submitAcc);
			add(cancelAcc);
		}
	}
	
	JTextField roomAnswer;
	JComboBox personAnswer;
	JComboBox weaponAnswer;
	JButton submit;
	JButton cancel;
	
	private class SugDialog extends JDialog {
		public SugDialog(Board game) {
			setLayout(new GridLayout(4,2));
			JTextField yourRoom = new JTextField("Your Room");
			JTextField person = new JTextField("Person");
			JTextField weapon = new JTextField("Weapon");
			yourRoom.setEditable(false);
			person.setEditable(false);
			weapon.setEditable(false);
			yourRoom.setBackground(null);
			person.setBackground(null);
			weapon.setBackground(null);
			roomAnswer = new JTextField("");
			personAnswer = new JComboBox(gameBoard.getCharacters().toArray());
			weaponAnswer = new JComboBox(gameBoard.getWeapons().toArray());
			submit = new JButton("Submit");
			cancel = new JButton("Cancel");
			roomAnswer.setEditable(false);
			submit.addActionListener(new SugSubmitListener());
			cancel.addActionListener(new SugCancelListener());
			add(yourRoom);
			add(roomAnswer);
			add(person);
			add(personAnswer);
			add(weapon);
			add(weaponAnswer);
			add(submit);
			add(cancel);
			
		}
	}
	
	private class NotesDialog extends JDialog {
		public NotesDialog(Board game) {
			setTitle("Detective Notes");
			setLayout(new GridLayout(3,2));
			JPanel panel;
			panel = createNamePanel("People", 0, 0);
			for (String person: game.getCharacters()) {
				panel.add(new JCheckBox(person, false));
			}
			add(panel);
			panel = createNamePanel("Person Guess", 0, 0);
			panel.add(new JComboBox(game.getCharacters().toArray()));
			add(panel);
			panel = createNamePanel("Rooms", 0, 0);
			for (String room: game.getRooms()) {
				panel.add(new JCheckBox(room, false));
			}
			add(panel);
			panel = createNamePanel("Room Guess", 0, 0);
			panel.add(new JComboBox(game.getRooms().toArray()));
			add(panel);
			panel = createNamePanel("Weapons", 0, 0);
			for (String weapon: game.getWeapons()) {
				panel.add(new JCheckBox(weapon, false));
			}
			add(panel);
			panel = createNamePanel("Weapon Guess", 0, 0);
			panel.add(new JComboBox(game.getWeapons().toArray()));
			add(panel);
		}
	}
	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Specific Panels		~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	JTextField whoseTurn = new JTextField(14);
	JTextField die = new JTextField(3);
	JTextField guess = new JTextField(20);
	JTextField guessResult = new JTextField(10);
	JLabel label = new JLabel("Whose Turn?");
	JButton nextPlayer = new JButton("Next Player");
	JButton makeAccusation = new JButton("Make Accusation");

	private JPanel controlPanel() {
		whoseTurn.setEditable(false);
		whoseTurn.setBackground(null);
		
		die.setEditable(false);
		die.setBackground(null);
		
		guess.setEditable(false);
		guess.setBackground(null);
		
		guessResult.setEditable(false);
		guessResult.setBackground(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		JPanel row = new JPanel();
		row.setLayout(new GridLayout(0,3));
		JPanel col = new JPanel();

		// add action listeners
		nextPlayer.addActionListener(new NextTurnListener());
		makeAccusation.addActionListener(new AccusationListener());
		
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
	 
	private JPanel cardsPanel(Board game) {
		JPanel cpanel = createNamePanel("Cards", 3, 0);
		JPanel panel;
		JTextField text;
		
		panel = createNamePanel("People", 0, 1);
		for (Card card: game.getHuman().getHand()) {
			if (card.getType() == CardType.PERSON) {
				text = new JTextField(card.getCardName(), 8);
				text.setEditable(false);
				text.setBackground(null);
				panel.add(text);
			}
		}
		cpanel.add(panel);
		
		
		panel = createNamePanel("Rooms", 0, 1);
		for (Card card: game.getHuman().getHand()) {
			if (card.getType() == CardType.ROOM) {
				text = new JTextField(card.getCardName(), 8);
				text.setEditable(false);
				text.setBackground(null);
				panel.add(text);
			}
		}
		cpanel.add(panel);
		
		panel = createNamePanel("Weapons", 0, 1);
		for (Card card: game.getHuman().getHand()) {
			if (card.getType() == CardType.WEAPON) {
				text = new JTextField(card.getCardName(), 8);
				text.setEditable(false);
				text.setBackground(null);
				panel.add(text);
			}
		}
		cpanel.add(panel);
		
		return cpanel;
	}
	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~		File Menu			~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

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
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		
		return item;
	}
	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Helper Functions		~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	// Helper Function to create named and bordered panels
	 private JPanel createNamePanel(String panelTitle, int grid_rows, int grid_cols) {
	 	JPanel panel = new JPanel();
	 	if (grid_rows != 0 || grid_cols != 0) {
	 		panel.setLayout(new GridLayout(grid_rows, grid_cols));
	 	}
		panel.setBorder(new TitledBorder (new EtchedBorder(), panelTitle));
		return panel;
	}

	 
	public static void main(String[] args) {
		// Set up board & game
		gameBoard = Board.getInstance();
		gameBoard.setConfigFiles("ClueMap.csv", "RoomKey.txt", "players.txt", "weapons.txt");
		gameBoard.initialize();
		
		GUI_clue gui = new GUI_clue();
		gui.setVisible(true);
	}
}
