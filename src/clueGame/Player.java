package clueGame;
import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private String colorString;
	
	public Player(String character, String color) {
		this.playerName = character;
		this.colorString = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public Color getColor() {
		switch (colorString) {
		case "WHITE":
			return Color.white;
		case "MAGENTA":
			return Color.pink;
		case "YELLOW":
			return Color.yellow;
		case "GREEN":
			return Color.green;
		case "BLUE":
			return Color.blue;
		case "RED":
			return Color.red;
		}
		return Color.black;
	}
}
