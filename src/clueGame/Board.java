package clueGame;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import clueGame.BoardCell;
import clueGame.BadConfigFormatException;

public class Board {
	private int boardWidth;
	private int boardHeight;
	private Map<Character, String> legend;
	//private Set<BoardCell> namedTargets;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Set<BoardCell> visited = new HashSet<BoardCell>();
	private Map< BoardCell, Set<BoardCell> > adjacencyMap;
	private ArrayList<ArrayList<BoardCell>> boardCells;
	private String boardConfigFile;
	private String roomConfigFile;
	

	// Constructor
	public Board()  {
		super();
		this.legend = new HashMap<Character, String>();
	}
	
	// takes in file for board and populates an array with boardCells
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardConfigFile);
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
            	if (!input[temp].isEmpty()) {
            		col++;
            		if (!legend.containsKey(input[col].charAt(0))) {
            			throw new BadConfigFormatException();
            		}
            		BoardCell cell = new BoardCell(row, col, input[col].charAt(0));
            		if (input[col].length() == 2) {
            			cell.setDirection(input[col].charAt(1));
            		} 
                	thisRow.add(cell);
            	}
            }
            boardCells.add(thisRow);
        }
		// record dimensions
		boardHeight = boardCells.size();
        boardWidth = boardCells.get(0).size();	
        for (int i = 1; i < boardHeight; ++i) {
        	if (boardCells.get(i).size() != boardWidth) {
        		throw new BadConfigFormatException();
        	}
        }
	}

	// populates a map of cells with their respective adjacent cells
	public Map< BoardCell, Set<BoardCell> > calcAdjacencies() {
		Map< BoardCell, Set<BoardCell> > adjacents = new HashMap< BoardCell, Set<BoardCell> >();
		Set<BoardCell> thisAdj = new HashSet<BoardCell>();

		for (ArrayList<BoardCell> rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				thisAdj = new HashSet<BoardCell>();
				if (cell.getRow() < boardHeight -1) thisAdj.add(this.getCellAt(cell.getRow()+1, cell.getColumn()));
				if (cell.getColumn() < boardWidth -1) thisAdj.add(this.getCellAt(cell.getRow(), cell.getColumn()+1));
				if (cell.getColumn() > 0) thisAdj.add(this.getCellAt(cell.getRow(), cell.getColumn()-1));
				if (cell.getRow() > 0) thisAdj.add(this.getCellAt(cell.getRow()-1, cell.getColumn()));

				adjacents.put(cell, thisAdj);
			}
		}
		return adjacents;
	}
	
	// calculates possible cells to move to given a path length and an empty Set of BoardCells
	// TODO: make it consider the tiles that can be visited "W" etc
	public Set<BoardCell> helperTargets(BoardCell start, int pathLength, Set<BoardCell> visited) {
		if (pathLength != 0) {
			for (BoardCell adj : adjacencyMap.get(start)){
				visited.add(start); // add current cell to a Set of visited cells
				helperTargets(adj, pathLength - 1, visited); // recursively calls itself, reducing the pathLength and passing along a Set of previous BoardCells 
			}
		}
		targets.add(start);
		return visited;
	}
	
	public Set<BoardCell> calcTargets(int x, int y, int pathlength) {
		Set<BoardCell> visited = new HashSet<BoardCell>();
		visited = helperTargets(this.getCellAt(x,	y), pathlength, visited);
		return visited;
	}
	
	
		
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ", ";
        String line;
        // occupy boardCells array from file data
        while (in.hasNextLine()) {
			line = in.nextLine();
            // use comma as separator
            String[] input = line.split(delimeter);
            legend.put(input[0].charAt(0), input[1]);
            if (!input[2].equals("Card") && !input[2].equals("Other")) {
            	throw new BadConfigFormatException(input[2]);
            }
        }
	}
	
	// setters and getters
	public void setConfigFiles(String b, String l) {
		this.roomConfigFile = l;
		this.boardConfigFile = b;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return boardCells.get(row).get(col);
	}


	public int getNumRows() {
		return boardHeight;
	}

	public int getNumColumns() {
		return boardWidth;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}

	// getter for adjacency list given a specific cell
	public Set<BoardCell> getAdjList(int x, int y) {
		return adjacencyMap.get(this.getCellAt(x,y));
	}
		
	// getter for Targets Set
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public static Board getInstance() {
		return new Board();
	}
	
	public void initialize() {
		try {
			this.loadRoomConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e);  // file not found exception thrown by IntBoard Constructor
		} catch (IOException e) {
			System.out.println(e);  // io exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2:" + e.getMessage());  // io exception thrown by IntBoard Constructor
		}
		try {
			this.loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (IOException e) {
			System.out.println(e);  // io exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config1");  // io exception thrown by IntBoard Constructor
		}
		this.adjacencyMap = calcAdjacencies();
	}

	

}
