package experiment;

import java.util.*;
import java.io.*;

/*
 *  @author Giorgio Cassata, Aidan Brookes
 *  Part 2 code: passing tests
 */

public class IntBoard {
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Map< BoardCell, Set<BoardCell> > adjacencyMap;
	private ArrayList<ArrayList<BoardCell>> boardCells;
	private int boardWidth;
	private int boardHeight;

	
	// Constructor
	public IntBoard(String fileName) throws FileNotFoundException {
		super();
		this.readBoard(fileName);
		this.adjacencyMap = calcAdjacencies();
	}
	
	// takes in file for board and populates an array with boardCells
	private void readBoard(String fileName) throws FileNotFoundException {
		FileReader reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        String line;
        boardCells = new ArrayList();
        // occupy boardCells array from file data
        int row = -1;
        while (in.hasNextLine()) {
        	row++;
			line = in.nextLine();
			ArrayList<BoardCell> thisRow = new ArrayList<BoardCell>();
			
            // use comma as separator
            String[] input = line.split(delimeter);
            int col = -1;
            for ( int temp = 0; temp < input.length; ++temp ) {
            	if (!input[temp].isBlank()) {
            		col++;
            		BoardCell cell = new BoardCell(row, col, input[col]);
                	thisRow.add(cell);
            	}
            }
            boardCells.add(thisRow);
        }
		// record dimensions
		boardHeight = boardCells.size();
        boardWidth = boardCells.get(0).size();	
	}
	

	// populates a map of cells with their respective adjacent cells
	public Map< BoardCell, Set<BoardCell> > calcAdjacencies() {
		Map< BoardCell, Set<BoardCell> > adjacents = new HashMap< BoardCell, Set<BoardCell> >();
		Set<BoardCell> thisAdj = new HashSet<BoardCell>();

		for (ArrayList<BoardCell> rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				thisAdj = new HashSet<BoardCell>();
				if (cell.getRow() < boardHeight -1) thisAdj.add(this.getCell(cell.getRow()+1, cell.getColumn()));
				if (cell.getColumn() < boardWidth -1) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()+1));
				if (cell.getColumn() > 0) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()-1));
				if (cell.getRow() > 0) thisAdj.add(this.getCell(cell.getRow()-1, cell.getColumn()));

				adjacents.put(cell, thisAdj);
			}
		}
		return adjacents;
	}
	
	
	// calculates possible cells to move to given a path length and an empty Set of BoardCells
	// TODO: make it consider the tiles that can be visited "W" etc
	public Set<BoardCell> calcTargets(BoardCell start, int pathLength, Set<BoardCell> visited) {
		if (pathLength != 0) {
			for (BoardCell adj : adjacencyMap.get(start)){
				visited.add(start); // add current cell to a Set of visited cells
				calcTargets(adj, pathLength - 1, visited); // recursively calls itself, reducing the pathLength and passing along a Set of previous BoardCells 
			}
		}
		targets.add(start);
		return visited;
	}
	
	// Getters
	
	// getter for a specific cell in the board
	public BoardCell getCell(int x, int y) {
		return boardCells.get(x).get(y);
	}
		
	// getter for adjacency list given a specific cell
	public Set<BoardCell> getAdjList(BoardCell key) {
		return adjacencyMap.get(key);
	}
		
	// getter for Targets Set
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	// Print methods (for testing)
	
	// prints items in board to console (for testing)
	public void printBoard() {
		for (ArrayList<BoardCell> rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				System.out.print(cell.getRow() + ":" + cell.getColumn() + ", ");
			}
			System.out.println();
		}
	}
	
	// prints adjacency lists to console (for testing)
	public void printAdj() {
		for (BoardCell cell: adjacencyMap.get(this.getCell(0, 0))) {
			System.out.println(cell.getRow() + ":" + cell.getColumn());
		}
	}
	
	// prints targets to console (for testing)
	public void printTarg() {
		this.calcTargets(this.getCell(1, 1), 3);
		for (BoardCell cell: this.targets) {
			System.out.println(cell.getRow() + ":" + cell.getColumn());
		}
	}
	
}
