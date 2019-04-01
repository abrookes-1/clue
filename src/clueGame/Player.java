package clueGame;
import java.awt.Color;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private String colorString;
	private Set<Card> cards;
	
	public Player(String character, Color color) {
		this.playerName = character;
		this.color = color;
	}
	
	public void dealCard(Card card) {
		cards.add(card);
	}
	
	public int getHandSize() {
		return cards.size();
	}
	
	public Set<Card> getHand(){
		return cards;
	}
	
	// untested
	public Card disproveSuggestion(Solution suggestion) throws Exception {
		for (Card aCard: cards) {
			if (aCard.getCardName() == suggestion.person) {
				return aCard;
			} else if (aCard.getCardName() == suggestion.room) {
				return aCard;
			} else if (aCard.getCardName() == suggestion.weapon) {
				return aCard;
			}
		}
		throw new gamePlayException("card may not be disproved");
	}
	
	public Color getColor() {
		
		return color;
		// this block used when this.color was type string
//		switch (colorString) {
//		case "WHITE":
//			return Color.white;
//		case "MAGENTA":
//			return Color.pink;
//		case "YELLOW":
//			return Color.yellow;
//		case "GREEN":
//			return Color.green;
//		case "BLUE":
//			return Color.blue;
//		case "RED":
//			return Color.red;
//		}
//		return Color.black;
	}
}
