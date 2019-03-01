package experiment;

import java.util.*;
import java.io.*;

public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	BoardCell[][] boardCells;
	private int boardWidth;
	private int boardHeight;

	
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
        boardHeight = Integer.valueOf(dim[1]);
        boardWidth = Integer.valueOf(dim[0]);
        boardCells = new BoardCell[boardHeight][boardWidth];
        // occupy boardCells array from file data
		for ( int row = 0; row < Integer.valueOf(dim[1]); ++row ) {
			line = in.nextLine();
            // use comma as separator
            String[] input = line.split(delimeter);
            for ( int col = 0; col < Integer.valueOf(dim[0]); ++col ) {
            	boardCells[row][col].setRow(row);
            	boardCells[row][col].setColumn(col);
            	boardCells[row][col].setSymbol(input[col]);
            }
        }
		
	}
	
	public BoardCell getCell(int x, int y) {
		return boardCells[x][y];
	}
	
	
	public Map< BoardCell, Set<BoardCell> > calcAdjacencies() {
		Map< BoardCell, Set<BoardCell> > adjacents = new HashMap< BoardCell, Set<BoardCell> >();
		Set<BoardCell> thisAdj = new HashSet<BoardCell>();
		
		for (BoardCell[] rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				if (cell.getColumn() < boardWidth -1) thisAdj.add(this.getCell(cell.getRow()+1, cell.getColumn()));
				if (cell.getRow() < boardHeight -1) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()+1));
				if (cell.getRow() > 0) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()-1));
				if (cell.getColumn() > 0) thisAdj.add(this.getCell(cell.getRow()-1, cell.getColumn()));

				adjacents.put(cell, thisAdj);
				thisAdj.clear();
			}
		}
		return adjacents;
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
