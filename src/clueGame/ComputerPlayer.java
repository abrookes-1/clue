package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	
	// Constructor
	public ComputerPlayer(String character, String color) {
		super(character, color);
	}
	
	// Once enough cards have been seen, will make guesses as accusations
	@Override
	public Solution makeAccusation() {
		if (getUnseenPeople().size() <= 2 && getUnseenWeapons().size() <= 2 && getUnseenRooms().size() <= 2) {
			Solution finalGuess = createSuggestion();
			return finalGuess;
		} else if (getUnseenPeople().size() + getUnseenWeapons().size() + getUnseenRooms().size() <= 6) {
			Solution finalGuess = createSuggestion();
			return finalGuess;
		}
		return null;
	}
	
	// Generates an accusation randomly from unseen cards
	@Override
	public Solution createAccusation() { 
		String person = null;
		String weapon = null;
		String room = null;
		int index = new Random().nextInt(getUnseenPeople().size());
		int i = 0;
		for (Card unseen:getUnseenPeople()) {
			if (i == index) {
				person = unseen.getCardName();
			}
			i++;
		}
		index = new Random().nextInt(getUnseenWeapons().size());
		i = 0;
		for (Card unseen:getUnseenWeapons()) {
			if (i == index) {
				weapon = unseen.getCardName();
			}
			i++;
		}
		index = new Random().nextInt(getUnseenWeapons().size());
		i = 0;
		for (Card unseen:getUnseenRooms()) {
			if (i == index) {
				room = unseen.getCardName();
			}
			i++;
		}
		
		Solution sol = new Solution(person, weapon, room);
		return sol;
	}
	
	// Generates a suggestion randomly from unsees cards
	@Override
	public Solution createSuggestion() { 
		String person = null;
		String weapon = null;
		int index = new Random().nextInt(getUnseenPeople().size());
		int i = 0;
		for (Card unseen:getUnseenPeople()) {
			if (i == index) {
				person = unseen.getCardName();
			}
			i++;
		}
		index = new Random().nextInt(getUnseenWeapons().size());
		i = 0;
		for (Card unseen:getUnseenWeapons()) {
			if (i == index) {
				weapon = unseen.getCardName();
			}
			i++;
		}
		String room = getRoom();
		Solution sol = new Solution(person, weapon, room);
		return sol;
	}
}
