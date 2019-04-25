package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	public static final boolean ISHUMAN = false;
	
	public ComputerPlayer(String character, String color) {
		super(character, color);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		// if possible, make an accusation, else do nothing
	}
	
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
