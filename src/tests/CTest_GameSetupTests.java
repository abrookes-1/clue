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
	public void setup_game() {
		gameBoard.setConfigFiles("ClueMap.csv", "RoomKey.txt", "players.txt", "weapons.txt");
		gameBoard.initialize();
	}
	
	@Test
	public void testPlayerCount() {
		Set<Player> players = gameBoard.getPlayerInstances();
		assert(players.size() == 6);
	}
	
	@Test
	public void testComputerPlayersExist(){
		Set<Player> players = gameBoard.getPlayerInstances();
		int computerPlayerCount = 0;
		Boolean containsHumanPlayer = false;
		Boolean containsMultipleHumanPlayer = false;
		Boolean nonHumanPlayersAreComputers = true;
		
		for (Player pla: players) {
			if (HumanPlayer.class.isInstance(pla)) {
				if (containsHumanPlayer) containsMultipleHumanPlayer = true;
				else containsHumanPlayer = true;
			} else if(!ComputerPlayer.class.isInstance(pla)){
				nonHumanPlayersAreComputers = false;
			} else if (ComputerPlayer.class.isInstance(pla)) {
				computerPlayerCount++;
			}
		}
		
		assert(computerPlayerCount == 5);
		assert(nonHumanPlayersAreComputers);
	}
	
	@Test
	public void testHumanPlayerExists() {
		Set<Player> players = gameBoard.getPlayerInstances();
		Boolean containsHumanPlayer = false;
		Boolean containsMultipleHumanPlayer = false;
		Boolean nonHumanPlayersAreComputers = true;
		assert(players.size() == 6);
		
		for (Player pla: players) {
			if (HumanPlayer.class.isInstance(pla)) {
				if (containsHumanPlayer) containsMultipleHumanPlayer = true;
				else containsHumanPlayer = true;
			} else if(!ComputerPlayer.class.isInstance(pla)){
				nonHumanPlayersAreComputers = false;
			}
		}
		
		assert(containsHumanPlayer);
	}
	
	@Test
	public void testOnlyOneHumanPlayer() {
		Set<Player> players = gameBoard.getPlayerInstances();
		Boolean containsHumanPlayer = false;
		Boolean containsMultipleHumanPlayer = false;
		Boolean nonHumanPlayersAreComputers = true;
		assert(players.size() == 6);
		
		for (Player pla: players) {
			if (HumanPlayer.class.isInstance(pla)) {
				if (containsHumanPlayer) containsMultipleHumanPlayer = true;
				else containsHumanPlayer = true;
			} else if(!ComputerPlayer.class.isInstance(pla)){
				nonHumanPlayersAreComputers = false;
			}
		}
		
		assert(!containsMultipleHumanPlayer);
	}
	
}
