package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;
import clueGame.HumanPlayer;
import clueGame.ComputerPlayer;

public class CTest_GameSetupTests {
	static Board gameBoard = Board.getInstance();
	@Before
	public static void setup_game() {
		gameBoard.initialize();
	}
	
	@Test
	public void testPlayersExist() {
		Set<Player> players = gameBoard.getPlayerInstances();
		Boolean containsHumanPlayer = false;
		Boolean containsMultipleHumanPlayer = false;
		assert(players.size() == 6);
		
		for (Player pla: players) {
			if (HumanPlayer.class.isInstance(pla)) {
				if (containsHumanPlayer) containsMultipleHumanPlayer = true;
				else containsHumanPlayer = true;
			}
		}
		
		assert(containsHumanPlayer);
		assert(!containsMultipleHumanPlayer);
	}
	
}
