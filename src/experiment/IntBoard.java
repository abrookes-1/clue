package experiment;

import java.util.*;
import java.io.*;

public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	BoardCell[][] boardCells;

	
	// Constructor
	public IntBoard(String fileName) throws IOException {
		super();
		try {
			this.readBoard(fileName);
		} catch (Exception e) {
			//something
		}
		this.adjacencyMap = calcAdjacencies();
	}

	private void readBoard(String fileName) throws IOException {
		FileReader reader = new FileReader(fileName);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        // get dimensions
        String line = in.nextLine();
        String[] dim = line.split(line);//Integer.valueOf()
        boardCells = new BoardCell[10][10];
		while (in.hasNextLine()) {
			String line = in.nextLine();
            // use comma as separator
            String[] country = line.split(cvsSplitBy);

            System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

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
	
	public Set<BoardCell> getTargets() {
		return new HashSet<BoardCell>();
	}
	
	
}
