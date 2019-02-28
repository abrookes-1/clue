package experiment;

import java.util.*;
import java.io.*;

public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	BoardCell[][] boardCells;

	
	// Constructor
	public IntBoard(String fileName) throws FileNotFoundException{
		super();
		this.readBoard(fileName);
		this.adjacencyMap = calcAdjacencies();
	}

	private void readBoard(String fileName) throws FileNotFoundException{
		FileReader reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        // get dimensions
        String line = in.nextLine();
        String[] dim = line.split(delimeter);
        boardCells = new BoardCell[Integer.valueOf(dim[0])][Integer.valueOf(dim[1])];
		while (in.hasNextLine()) {
			line = in.nextLine();
            // use comma as separator
            String[] row = line.split(delimeter);
            for (
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
