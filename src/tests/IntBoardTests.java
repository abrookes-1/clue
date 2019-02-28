package tests;
import java.util.*;
import experiment.*;
import org.junit.*;


public class IntBoardTests {
	IntBoard board;
	
	@Before
	public void setup() {
		board = new IntBoard();  // constructor should call calcAdjacencies() so you can test them
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
		
	}
	
	@Test
	public void testAdjacencyBL() {
		// TL = bottom left
		
	}
	
	@Test
	public void testAdjacencyBR() {
		// BR = bottom right
		
	}
	
	@Test
	public void testAdjacency11() {
		
		
	}
	
	@Test
	public void testAdjacency22() {
		
		
	}
	
	@Test
	public void testTargets() {
		
		
	}
}
