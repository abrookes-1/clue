package clueGame;
import java.util.*;

import clueGame.BoardCell;

public class Board {
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> namedTargets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	public Board() {
		
	}
	
	// setters and getters
	public void setConfigFiles(String b, String l) {
		this.roomConfigFile = l;
		this.boardConfigFile = b;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return null;
	}
	


	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}

	// getter for adjacency list given a specific cell
	public Set<BoardCell> getAdjList(BoardCell key) {
		//return adjacencyMap.get(key);
		return null;
	}
		
	// getter for Targets Set
	public Set<BoardCell> getTargets() {
		//return targets;
		return null;
	}
	
	public static Board getInstance() {
		
		return new Board();
	}
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() {}
	
	public void loadBoardConfig() {}
	
	public void calcAdjacencies() {}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
}
