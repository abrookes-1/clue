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
	public void testMakeAccusation() {
	for (Player pla: gameBoard.getPlayerInstances()) {
		
	}
//	solution with wrong person
//	solution with wrong weapon
//	solution with wrong room
	}
	
//	(15pts) Create suggestion. Tests include:
//	Room matches current location
//	If only one weapon not seen, it's selected
//	If only one person not seen, it's selected (can be same test as weapon)
//	If multiple weapons not seen, one of them is randomly selected
//	If multiple persons not seen, one of them is randomly selected
	
//	(15pts) Disprove suggestion - ComputerPlayer. Tests include:
	public static void disproveSuggestionComp() {
		for (Player pla: gameBoard.getPlayerInstances()) {
			//	If player has only one matching card it should be returned
			assert(pla.disproveSuggestion(Solution with one card in players hand) == that one card);
			//	If players has >1 matching card, returned card should be chosen randomly
			assert(pla.disproveSuggestion(Solution with >1 card in players hand) == one of those cards); // no clue how to check the randomness without repeating several times??
			//	If player has no matching cards, null is returned
			assert(pla.disproveSuggestion(Solution with no cards in common with players hand) == null);
		}
	}
		
//	(15pts) Handle suggestion - Board. Tests include:
	public static void handleSuggestionBoard() {
		//	Suggestion no one can disprove returns null
		assert(null == gameBoard.handleSuggestion(gameBoard.getAnswer(), null));
		//	Suggestion only accusing player can disprove returns null
		assert(null == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in accusers hand~  , accuser));
		//	Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		assert(  ~card must be the person or weapon card from suggestion~  == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in accusers hand~  , some computer player);
		//	Suggestion only human can disprove, but human is accuser, returns null
		assert(null == gameBoard.handleSuggestion(  ~suggestion must be a person + weapon in human players hand~  , gameBoard.getHuman()));
		//	Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
			// does this imply an array for playerInstances instead of a set??
		//	Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
			// no clue how to test
	}
}