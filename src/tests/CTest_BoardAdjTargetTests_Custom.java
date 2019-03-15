package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CTest_BoardAdjTargetTests_Custom {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueMap.csv", "RoomKey.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(22, 24);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(7, 4);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(19, 19);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(10, 24);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(0, 4);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY UP 
		Set<BoardCell> testList = board.getAdjList(20, 0);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 0)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(11, 15);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 14)));
		// TEST DOORWAY DOWN
		testList = board.getAdjList(14, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 21)));
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(19, 0);
		assertTrue(testList.contains(board.getCellAt(19, 1)));
		assertTrue(testList.contains(board.getCellAt(20, 0)));
		assertEquals(2, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(11, 5);
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertEquals(3, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(6, 14);
		assertTrue(testList.contains(board.getCellAt(6, 15)));
		assertTrue(testList.contains(board.getCellAt(6, 13)));
		assertTrue(testList.contains(board.getCellAt(5, 14)));
		assertTrue(testList.contains(board.getCellAt(7, 14)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(15, 21);
		assertTrue(testList.contains(board.getCellAt(15, 20)));
		assertTrue(testList.contains(board.getCellAt(15, 22)));
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertEquals(3, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(5, 0);
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(5, 1)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertEquals(1, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(5, 12);
		assertTrue(testList.contains(board.getCellAt(5, 11)));
		assertTrue(testList.contains(board.getCellAt(6, 12)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(18,15);
		assertTrue(testList.contains(board.getCellAt(18, 16)));
		assertTrue(testList.contains(board.getCellAt(18, 14)));
		assertTrue(testList.contains(board.getCellAt(17, 15)));
		assertTrue(testList.contains(board.getCellAt(19, 15)));
		assertEquals(4, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(12, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));	
		
		board.calcTargets(21, 15, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 15)));
		assertTrue(targets.contains(board.getCellAt(22, 15)));	
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		assertTrue(targets.contains(board.getCellAt(21, 16)));	
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		// targets one cell away seem to be selected as well as those two cells away
		// targets = (row, column): (13, 14) (12, 14) (10, 15) (11, 14) (11, 15)
		board.calcTargets(12, 15, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 15)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(11, 14)));
		
		board.calcTargets(5, 7, 2);
		targets= board.getTargets();
		// targets = (row, column): ^(4,6) 1(5,8) ^(6,8) ^(5,9) ^(6,6) 1(4,7) ^(7,7) 1(5,6) ^(5,5) 1(6,7) ^(3,7)
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 9)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(3, 7)));
		assertTrue(targets.contains(board.getCellAt(7, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(4, 6)));

	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(5, 7, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 12)));
		assertTrue(targets.contains(board.getCellAt(1, 7)));
		assertTrue(targets.contains(board.getCellAt(2, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 6)));
		assertTrue(targets.contains(board.getCellAt(3, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 9)));
		assertTrue(targets.contains(board.getCellAt(6, 8)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		assertTrue(targets.contains(board.getCellAt(6, 4)));
		assertTrue(targets.contains(board.getCellAt(5, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 9)));

		
		// Includes a path that doesn't have enough length
		board.calcTargets(16, 5, 4);
		targets = board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 5)));
		assertTrue(targets.contains(board.getCellAt(13, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 6)));	
		assertTrue(targets.contains(board.getCellAt(14, 5)));
		assertTrue(targets.contains(board.getCellAt(16, 7)));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 1 away
		board.calcTargets(21, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 15)));
		assertTrue(targets.contains(board.getCellAt(22, 15)));	
		assertTrue(targets.contains(board.getCellAt(21, 14)));	
		assertTrue(targets.contains(board.getCellAt(21, 16)));	
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
			board.calcTargets(12, 15, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(10, 15)));
			assertTrue(targets.contains(board.getCellAt(11, 14)));
			assertTrue(targets.contains(board.getCellAt(13, 14)));
			assertTrue(targets.contains(board.getCellAt(11, 14)));
	}

}
