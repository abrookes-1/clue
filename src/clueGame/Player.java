package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private String character;
	private int row;
	private int column;
	private Color color; 
	private String colorString;
	private final int SIZE = 20; // must be == that of BoardCell SIZE
	private Set<Card> cards;
	private Set<Card> unseenWeapons;
	private Set<Card> unseenPeople;
	private Set<Card> unseenRooms;
	public boolean isHuman = false;
	
	public Player(String character, String color) {
		this.character = character;
		this.color = getColor(color);
		this.cards = new HashSet<Card>();
		this.unseenWeapons = new HashSet<Card>();
		this.unseenPeople = new HashSet<Card>();
	}
	
	public Solution createSuggestion() {
		return null;
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
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.column = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	public String getCharacter() {
		return character;
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
	
	public void removeUnseenPerson(Card c) {
		unseenPeople.remove(c);
	}
	
	//returns all cards which disprove
	public Set<Card> findCardsDisprove(Solution suggestion){
		Set<Card> cardsDisp = new HashSet<Card>();
		for (Card aCard: cards) {
			if (aCard.getCardName().equals(suggestion.person)) {
				cardsDisp.add(aCard);
			} else if (aCard.getCardName().equals(suggestion.weapon)) {
				cardsDisp.add(aCard);
			} else if (aCard.getCardName().equals(suggestion.room)) {
				cardsDisp.add(aCard);
			}
		}
		return cardsDisp;
	}
	

	public Card disproveSuggestion(Solution suggestion) {
		Set<Card> cardsDisp = findCardsDisprove(suggestion);
		
		// get random card from set
		int size = cardsDisp.size();
		System.out.println(size);
		//return null if empty
		if (size == 0) return null;
		int item = new Random().nextInt(size);
		int i = 0;
		for(Card aCard : cardsDisp) {
		    if (i == item) {
		    	System.out.println("returned a card");
		        return aCard;
		    }
		    i++;
		}
		return null;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(column*SIZE, row*SIZE, SIZE, SIZE);
		g.setColor(Color.BLACK);
		g.drawOval(column*SIZE, row*SIZE, SIZE, SIZE);
	}
	
	
	private Color getColor(String color) {
		
		//return color;
		 //this block used when this.color was type string
		switch (color) {
		case "WHITE":
			return Color.white;
		case "MAGENTA":
			return Color.pink;
		case "YELLOW":
			return Color.getHSBColor(46, 97, 30); // more orange-ish as to distinguish from walkways
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
