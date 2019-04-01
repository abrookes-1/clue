package clueGame;

public class Card {
	private String cardName;
	private CardType type = null;;
	
	public Card (String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public Boolean equals() {
		return null;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public CardType getType() {
		return this.type;
	}
}
