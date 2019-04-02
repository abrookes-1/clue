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
	
//	(10pts) Select a target. Tests include:
	@Test
	public void testTargetSelection() {
//	if no rooms in list, select randomly
		
//	if room in list that was not just visited, must select it
//	if room just visited is in list, each target (including room) selected randomly
	}
	
//	(15pts) Make an accusation. Tests include:
//	solution that is correct
//	solution with wrong person
//	solution with wrong weapon
//	solution with wrong room
	
//	(15pts) Create suggestion. Tests include:
//	Room matches current location
//	If only one weapon not seen, it's selected
//	If only one person not seen, it's selected (can be same test as weapon)
//	If multiple weapons not seen, one of them is randomly selected
//	If multiple persons not seen, one of them is randomly selected
	
//	(15pts) Disprove suggestion - ComputerPlayer. Tests include:
//	If player has only one matching card it should be returned
//	If players has >1 matching card, returned card should be chosen randomly
//	If player has no matching cards, null is returned
	
//	(15pts) Handle suggestion - Board. Tests include:
	public static void handleSuggestionBoard() {
//	Suggestion no one can disprove returns null
//	Suggestion only accusing player can disprove returns null
//	Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
//	Suggestion only human can disprove, but human is accuser, returns null
//	Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
//	Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
	}
}