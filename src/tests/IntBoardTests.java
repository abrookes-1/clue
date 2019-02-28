package tests;

import java.util.*;
import java.io.*;

import experiment.*;
import org.junit.*;


public class IntBoardTests {
	IntBoard board;
	private static final int MAX = 3;
	
	@Before
	public void setup(){
		try {
		board = new IntBoard("asdf.csv");  // constructor should call calcAdjacencies() so you can test them
		} catch (FileNotFoundException e) {
			assert(false); // file not found exception thrown by IntBoard Constructor
		} catch (IOException e) {
			assert(false); // io exception thrown by IntBoard Constructor
		}
	}
	
	// tests for four corners of the board
	@Test
	public void testAdjacencyTL() {
		// TL = top left
		Set<BoardCell> testList = board.getAdjList(board.getCell(0, 0));
		
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void testAdjacencyTR() {
		// TL = top right
		Set<BoardCell> testList = board.getAdjList(board.getCell(MAX, 0));
		
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void testAdjacencyBL() {
		// TL = bottom left
		Set<BoardCell> testList = board.getAdjList(board.getCell(0, MAX));
		
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void testAdjacencyBR() {
		// BR = bottom right
		Set<BoardCell> testList = board.getAdjList(board.getCell(MAX, MAX));
		
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
	}
	
	@Test
	public void testAdjacency11() {
		Set<BoardCell> testList = board.getAdjList(board.getCell(1, 1));
		
		assert(testList.contains(board.getCell(1, 0)));
		assert(testList.contains(board.getCell(0, 1)));
		assert(testList.contains(board.getCell(1, 2)));
		assert(testList.contains(board.getCell(2, 1)));

		
	}
	
	@Test
	public void testAdjacency22() {
		Set<BoardCell> testList = board.getAdjList(board.getCell(2, 2));
		
		assert(testList.contains(board.getCell(1, 2)));
		assert(testList.contains(board.getCell(2, 1)));
		assert(testList.contains(board.getCell(2, 3)));
		assert(testList.contains(board.getCell(3, 2)));

		
	}
	
	@Test
	public void testTargets() {
		board.calcTargets(board.getCell(1, 1), 3);
		Set<BoardCell> targetList = board.getTargets(board.getCell(1, 1), 3);
		
		assert(targetList.contains(board.getCell(0, 0)));
		assert(targetList.contains(board.getCell(0, 1)));
		assert(targetList.contains(board.getCell(0, 2)));
		assert(targetList.contains(board.getCell(0, 3)));
		assert(targetList.contains(board.getCell(1, 0)));
		assert(targetList.contains(board.getCell(1, 1)));
		assert(targetList.contains(board.getCell(1, 2)));
		assert(targetList.contains(board.getCell(1, 3)));
		assert(targetList.contains(board.getCell(2, 0)));
		assert(targetList.contains(board.getCell(2, 1)));
		assert(targetList.contains(board.getCell(2, 2)));
		assert(targetList.contains(board.getCell(2, 3)));
		assert(targetList.contains(board.getCell(3, 0)));
		assert(targetList.contains(board.getCell(3, 1)));
		assert(targetList.contains(board.getCell(3, 2)));
	}
}
