package experiment;

import java.util.*;
import java.io.*;

public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	BoardCell[][] boardCells;

	
	// Constructor
	public IntBoard(String fileName) throws FileNotFoundException {
		super();
		this.readBoard(fileName);
		this.adjacencyMap = calcAdjacencies();
	}

	private void readBoard(String fileName) throws FileNotFoundException {
		FileReader reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        // get dimensions
        String line = in.nextLine();
        String[] dim = line.split(delimeter);
        // use dimensions to size boardCells 
        boardCells = new BoardCell[Integer.valueOf(dim[0])][Integer.valueOf(dim[1])];
        // occupy boardCells array from file data
		for ( int row = 0; row < Integer.valueOf(dim[1]); ++row ) {
			line = in.nextLine();
            // use comma as separator
            String[] input = line.split(delimeter);
            for ( int col = 0; col < Integer.valueOf(dim[0]); ++col ) {
            	boardCells[row][col].setRow(Integer.valueOf(input[row]));
            	boardCells[row][col].setColumn(Integer.valueOf(input[col]));
            }
        }
		
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
	
	public Set<BoardCell> getTargets(BoardCell cell, int range) {
		return new HashSet<BoardCell>();
	}
	
	
}
