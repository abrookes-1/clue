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

public class TestPlayers {
	static Board gameBoard = Board.getInstance();
	@BeforeClass
	public static void setup_game() {
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
	
	@Test
	public void aboutEvenCards() {
		Set<Player> players = gameBoard.getPlayerInstances();
		int min = 999;
		int max = -999;
		
		for (Player pla: players) {
			if (pla.getHandSize() < min) {
				min = pla.getHandSize();
			} else if (pla.getHandSize() > max) {
				max = pla.getHandSize();
			}
		}
		
		assert(max-min < 2);
	}
	
	@Test
	public void noCardDuplicates() {
		Set<Player> players = gameBoard.getPlayerInstances();
		// check hand of each player
		for (Player pla: players) {
			for (Card car: pla.getHand()) {
				for (Player pla2: players) {
					if (pla2 != pla) {
						assert(!pla2.getHand().contains(car));
					}
				}
			}
		}
	}
}
