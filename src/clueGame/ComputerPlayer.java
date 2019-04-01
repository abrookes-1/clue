package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	public ComputerPlayer(String character, Color color) {
		super(character, color);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() { // args tbd
		
	}
}
