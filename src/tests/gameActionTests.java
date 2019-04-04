package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
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
		for (String person:gameBoard.getPlayers()) {
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
		
		Solution testSuggestion;
		for (ComputerPlayer pla: compPlayers) {
			testSuggestion = pla.createSuggestion();
			
			//	Room matches current location
			assert(gameBoard.getLegend().get(gameBoard.getCellAt(pla.getRow(), pla.getCol()).getInitial()) == testSuggestion.room);
			
			//	If only one weapon not seen, it's selected
			if (pla.getUnseenWeapons().size() == 1) {
				assert(pla.getUnseenWeapons().contains(testSuggestion.weapon));
			}
			
			//	If only one person not seen, it's selected (can be same test as weapon)
			if (pla.getUnseenPeople().size() == 1) {
				assert(pla.getUnseenPeople().contains(testSuggestion.person));
			}
			//	If multiple weapons not seen, one of them is randomly selected
			if (pla.getUnseenWeapons().size() > 1) {
				assert(pla.getUnseenWeapons().contains(testSuggestion.weapon));
			}
			//	If multiple persons not seen, one of them is randomly selected
			if (pla.getUnseenPeople().size() > 1) {
				assert(pla.getUnseenPeople().contains(testSuggestion.person));
			}
		}
	}
	
	/*
	//	(15pts) Disprove suggestion - ComputerPlayer. Tests include:
	@Test
	public static void disproveSuggestionComp() {
		// get all computer players in game
		Set<ComputerPlayer> compPlayers = gameBoard.getCompPlayerInstances();
		
		Solution testSuggestion;
		for (ComputerPlayer pla: compPlayers) {
			//Card roomFromHand = pla.getRoomFromHand();
			Solution suggestionToDisprove = new Solution(gameBoard.getAnswer().person, gameBoard.getAnswer().weapon, roomFromHand.getCardName());
		
			//	If player has only one matching card it should be returned
			assert(pla.disproveSuggestion(suggestionToDisprove) == roomFromHand);
			//	If players has >1 matching card, returned card should be chosen randomly
			assert(pla.disproveSuggestion(testSuggestion == one of those cards); 
			//	If player has no matching cards, null is returned
			assert(pla.disproveSuggestion(Solution with no cards in common with players hand) == null);
		
		
		}
		

	}
	*/
//		
//	//	(15pts) Handle suggestion - Board. Tests include:
//	@Test
//	public static void handleSuggestionBoard() {
//		//	Suggestion no one can disprove returns null
//		assert(null == gameBoard.handleSuggestion(gameBoard.getAnswer(), null));
//		//	Suggestion only accusing player can disprove returns null
//		assert(null == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in accusers hand~  , accuser));
//		//	Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
//		assert(  ~card must be the person or weapon card from suggestion~  == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in accusers hand~  , some computer player);
//		//	Suggestion only human can disprove, but human is accuser, returns null
//		assert(null == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in human players hand~  , gameBoard.getHuman()));
//		//	Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
//			// does this imply an array for playerInstances instead of a set??
//		//	Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
//			// no clue how to test
//	}
}