package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{
	public static final boolean ISHUMAN = false;
	
	public ComputerPlayer(String character, Color color) {
		super(character, color);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public Solution createSuggestion() { // args tbd
		String person = "";
		String weapon = "";
		String room = "";
		Solution sol = new Solution(person, weapon, room);
		return sol;
	}
}
