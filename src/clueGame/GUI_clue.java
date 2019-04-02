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
	
	public GUI_clue() {
//		setLayout(new GridLayout(2,0));
//		JPanel panel = createNamePanel();
//		add(panel);
//		panel = createButtonPanel();
//		add(panel);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue");
		frame.setSize(250, 150);
		
		GUI_clue gui_clue = new GUI_clue();
		frame.add(gui_clue, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
}
