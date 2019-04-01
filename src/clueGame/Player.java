package clueGame;
import java.awt.Color;

public class Player {
	String playerName;
	int row;
	int column;
	Color color;
	String stringColor;
	
	public Player(String character, String color) {
		this.playerName = character;
		this.stringColor = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
}
