package experiment;

import java.util.*;
import java.io.*;

public class IntBoard {
	Set<BoardCell> targets = new HashSet<BoardCell>();
	Set<BoardCell> visited = new HashSet<BoardCell>();
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

	public void printBoard() {
		for (BoardCell[] rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				System.out.print(cell.getRow() + ":" + cell.getColumn() + ", ");
			}
			System.out.println();
		}
	}
	
	public void printAdj() {
		for (BoardCell cell: adjacencyMap.get(this.getCell(0, 0))) {
			System.out.println(cell.getRow() + ":" + cell.getColumn());
		}
	}
	
	public void printTarg() {
		this.calcTargets(this.getCell(1, 1), 3);
		for (BoardCell cell: this.targets) {
			System.out.println(cell.getRow() + ":" + cell.getColumn());
		}
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
            	boardCells[row][col] = new BoardCell();
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
				thisAdj = new HashSet<BoardCell>();
				if (cell.getRow() < boardWidth -1) thisAdj.add(this.getCell(cell.getRow()+1, cell.getColumn()));
				if (cell.getColumn() < boardHeight -1) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()+1));
				if (cell.getColumn() > 0) thisAdj.add(this.getCell(cell.getRow(), cell.getColumn()-1));
				if (cell.getRow() > 0) thisAdj.add(this.getCell(cell.getRow()-1, cell.getColumn()));

				adjacents.put(cell, thisAdj);
			}
		}
		return adjacents;
	}
	
	public Set<BoardCell> getAdjList(BoardCell key) {
		return adjacencyMap.get(key);
	}
	
	public void calcTargets(BoardCell start, int pathLength) {
		if (pathLength != 0) {
			visited.add(start);
			for (BoardCell adj : adjacencyMap.get(start)){
				/*if (!visited.contains(adj))*/ calcTargets(adj, pathLength - 1);
			}
		}
		targets.add(start);
		return;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	
}
