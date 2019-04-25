package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

public class gameActionTests {
	static Board gameBoard = Board.getInstance();
	
	@BeforeClass
	public static void setup() {
		gameBoard.setConfigFiles("ClueMap.csv", "RoomKey.txt", "players.txt", "weapons.txt");
		gameBoard.initialize();
		gameBoard.startNextPlayer();
	}
	
//	(10pts) Select a target. Tests include:
	@Test
	public void testTargetSelection() {
		//	if no rooms in list, select randomly
		
		
		//	if room in list that was not just visited, must select it
		
		//	if room just visited is in list, each target (including room) selected randomly
		
	}
	
//	(15pts) Make an accusation. Tests include:
	@Test
	public void testCheckAccusation() {
		Solution ans = gameBoard.getAnswer();
		Solution anscopy = new Solution(ans.person, ans.weapon, ans.room);
		
		//	solution with wrong person
		for (String person:gameBoard.getCharacters()) {
			if (anscopy.person != person) {
				anscopy.person = person;
				break;
			}
		}
		assert(!gameBoard.checkAccusation(anscopy));
		
		//	solution with wrong weapon
		anscopy.person = ans.person;
		for (String weapon:gameBoard.getWeapons()) {
			if (anscopy.weapon != weapon) {
				anscopy.weapon = weapon;
				break;
			}
		}
		assert(!gameBoard.checkAccusation(anscopy));
		
		//	solution with wrong room
		anscopy.weapon = ans.weapon;
		for (String room:gameBoard.getRooms()) {
			if (anscopy.room != room) {
				anscopy.room = room;
				break;
			}
		}
		assert(!gameBoard.checkAccusation(anscopy));
		
	}
	
	//	(15pts) Create suggestion. - Computer Player Tests include:
	@Test
	public void testCreateSuggestion() {
		// get all computer players in game
		Set<ComputerPlayer> compPlayers = gameBoard.getCompPlayerInstances();
		boolean willAssert;
		Solution testSuggestion;
		for (ComputerPlayer pla: compPlayers) {
			testSuggestion = pla.createSuggestion();
			
			//	Room matches current location
			assert(gameBoard.getLegend().get(gameBoard.getCellAt(pla.getRow(), pla.getCol()).getInitial()) == testSuggestion.room);
			
			//	If only one weapon not seen, it's selected
			if (pla.getUnseenWeapons().size() == 1) {
				willAssert = false;
				for (Card card:pla.getUnseenWeapons()) {
					if (card.getCardName() == testSuggestion.weapon) {
						willAssert = true;
					}	
				}
				if (willAssert) {
					assert(true);
				} else {
					assert(false);
				}
			}
			
			//	If only one person not seen, it's selected (can be same test as weapon)
			if (pla.getUnseenPeople().size() == 1) {
				willAssert = false;
				for (Card card:pla.getUnseenPeople()) {
					if (card.getCardName() == testSuggestion.person) {
						willAssert = true;
					}	
				}
				if (willAssert) {
					assert(true);
				} else {
					assert(false);
				}
			}
			//	If multiple weapons not seen, one of them is randomly selected
			
			willAssert = false;
			for (Card card:pla.getUnseenWeapons()) {
				if (card.getCardName() == testSuggestion.weapon) {
					willAssert = true;
				}	
			}
			if (willAssert) {
				assert(true);
			} else {
				assert(false);
			}
			
			//	If multiple persons not seen, one of them is randomly selected
		
			willAssert = false;
			for (Card card:pla.getUnseenPeople()) {
				if (card.getCardName() == testSuggestion.person) {
					willAssert = true;
				}	
			}
			if (willAssert) {
				assert(true);
			} else {
				assert(false);
			}
			
		}
	}
	
	
	//	(15pts) Disprove suggestion - ComputerPlayer. Tests include:
	@Test
	public void disproveSuggestionComp() {
		// get all computer players in game
		Set<ComputerPlayer> compPlayers = gameBoard.getCompPlayerInstances();
		
		Solution suggestionToDisprove = new Solution(gameBoard.getAnswer().person, gameBoard.getAnswer().weapon, gameBoard.getAnswer().room);
		int it = 0;
		for (ComputerPlayer pla: compPlayers) {
			if (it == 2) {
				for (Card card:pla.getHand()) {
					if (card.getType() == CardType.PERSON) {
						suggestionToDisprove.person = card.getCardName();
						break;
					}
				}
				for (Card card:pla.getHand()) {
					if (card.getType() == CardType.WEAPON) {
						suggestionToDisprove.weapon = card.getCardName();
						break;
					}
				}
			} else if (it == 4) {
				for (Card card:pla.getHand()) {
					if (card.getType() == CardType.ROOM) {
						suggestionToDisprove.room = card.getCardName();
						break;
					}
				}
			}
			it++;
		}
		
		it = 0;
		for (ComputerPlayer pla: compPlayers) {
			
			//	If player has only one matching card it should be returned
			if (it==4) {
				assert(pla.disproveSuggestion(suggestionToDisprove).getCardName() == suggestionToDisprove.room);
			}
			//	If players has >1 matching card, returned card should be chosen randomly
			else if (it==2) {
				assert(pla.disproveSuggestion(suggestionToDisprove).getCardName() == suggestionToDisprove.person || pla.disproveSuggestion(suggestionToDisprove).getCardName() == suggestionToDisprove.weapon); 
			}
			//	If player has no matching cards, null is returned
			else {
				assert(pla.disproveSuggestion(suggestionToDisprove) == null);
			}
			it++;
		}
		

	}
	
	
	//	(15pts) Handle suggestion - Board. Tests include:
	@Test
	public void handleSuggestionBoard() {
		Solution suggestionToHandle = new Solution(gameBoard.getAnswer().person, gameBoard.getAnswer().weapon, gameBoard.getAnswer().room);	
		
		// Suggestion no one can disprove returns null
		gameBoard.handleSuggestion(suggestionToHandle, gameBoard.getHuman());
		assert(gameBoard.getResponse().equals("no new clue"));
		
		for (Player pla:gameBoard.getPlayerInstances()) {
			// Suggestion only accusing player can disprove returns null
			if (pla == gameBoard.getHuman()) {
				for (Card card:pla.getHand()) {
					if (card.getType() == CardType.ROOM) {
						suggestionToHandle.room = card.getCardName();
						break;
					}
				}
				gameBoard.handleSuggestion(suggestionToHandle, pla);
				assert(gameBoard.getResponse().equals("no new clue"));
				break;
			} 
		}
		// Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		gameBoard.handleSuggestion(suggestionToHandle, gameBoard.getHuman());
		assert(gameBoard.getResponse().equals(suggestionToHandle.room) || gameBoard.getResponse().equals(suggestionToHandle.weapon ) || gameBoard.getResponse().equals(suggestionToHandle.person));
			
		for (Player pla:gameBoard.getCompPlayerInstances()) {
			for (Card card:pla.getHand()) {
				if (card.getType() == CardType.WEAPON) {
					suggestionToHandle.weapon = card.getCardName();
					break;
				}
			}
			break;
		}
		//Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
			//assert(suggestionToHandle.room == gameBoard.handleSuggestion(suggestionToHandle, pla).getCardName());
		// Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
			//assert(suggestionToHandle.room == gameBoard.handleSuggestion(suggestionToHandle, null).getCardName());
			
	}
	
}