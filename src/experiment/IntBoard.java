package experiment;

import java.util.*;


public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	BoardCell[][] boardCells;
	//ArrayList<ArrayList<BoardCell>> boardCells;
	//int[][] intarray = new int[10][10];
	
	// Constructor
	public IntBoard() {
		super();
		this.readBoard("asdf.csv");
		this.adjacencyMap = calcAdjacencies();
	}

	private void readBoard(String fileName) {
		boardCells = new BoardCell[10][10];
	}
	
	public BoardCell getCell(int x, int y) {
		return boardCells[x][y];
	}
	
	
	public Map< BoardCell, Set<BoardCell> > calcAdjacencies() {
		return new HashMap< BoardCell, Set<BoardCell> >();
	}
	
	public Set<BoardCell> getAdjList(BoardCell key) {
		return adjacencyMap.get(key);
	}
	
	public void calcTargets(int startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
	
}
