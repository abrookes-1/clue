package clueGame;
import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private String colorString;
	private Set<Card> cards;
	private Set<Card> unseenWeapons;
	private Set<Card> unseenPeople;
	private Set<Card> unseenRooms;
	
	public Player(String character, Color color) {
		this.playerName = character;
		this.color = color;
		this.cards = new HashSet<Card>();
		this.unseenWeapons = new HashSet<Card>();
		this.unseenPeople = new HashSet<Card>();
	}
	
	public void dealCard(Card card) {
		cards.add(card);
		if (card.getType() == CardType.PERSON) {
			unseenPeople.remove(card);
		}
		if (card.getType() == CardType.WEAPON) {
			unseenWeapons.remove(card);
		}
		if (card.getType() == CardType.ROOM) {
			unseenRooms.remove(card);
		}
	}
	
	public int getHandSize() {
		return cards.size();
	}
	
	public Set<Card> getHand(){
		return cards;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	public String getRoom() {
		return Board.getInstance().getLegend().get(Board.getInstance().getCellAt(row, column).getInitial());
	}
	
	public void setUnseenPeople(Set<Card> unseen) {
		this.unseenPeople = unseen;
	}
	
	public void setUnseenWeapons(Set<Card> unseen) {
		this.unseenWeapons = unseen;
	}
	
	public void setUnseenRooms(Set<Card> unseen) {
		this.unseenRooms = unseen;
	}
	
	
	public Set<Card> getUnseenPeople() {
		return unseenPeople;
	}
	
	public Set<Card> getUnseenWeapons() {
		return unseenWeapons;
	}
	
	public Set<Card> getUnseenRooms() {
		return unseenRooms;
	}
	
	//returns all cards which disprove
	public Set<Card> findCardsDisprove(Solution suggestion){
		Set<Card> cardsDisp = new HashSet<Card>();
		for (Card aCard: cards) {
			if (aCard.getCardName() == suggestion.person) {
				cardsDisp.add(aCard);
			} else if (aCard.getCardName() == suggestion.room) {
				cardsDisp.add(aCard);
			} else if (aCard.getCardName() == suggestion.weapon) {
				cardsDisp.add(aCard);
			}
		}
		return cardsDisp;
	}
	
	// untested
	public Card disproveSuggestion(Solution suggestion) {
		Set<Card> cardsDisp = findCardsDisprove(suggestion);
		
		// get random card from set
		int size = cardsDisp.size();
		//return null if empty
		if (size == 0) return null;
		int item = new Random().nextInt(size);
		int i = 0;
		for(Card aCard : cardsDisp)
		{
		    if (i == item)
		        return aCard;
		    i++;
		}
		return null;
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
