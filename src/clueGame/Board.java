/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

package clueGame;
import java.awt.Color;
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import clueGame.*;
//import clueGame.Solution;
//import clueGame.Card;
//import clueGame.BoardCell;
//import clueGame.BadConfigFormatException;

public class Board {
	private static Board boardInstance = new Board();
	private int boardWidth;
	private int boardHeight;
	private Map<Character, String> legend;
	private Map<Color, String> players;
	private Set<String> weapons;
	private Map< BoardCell, Set<BoardCell> > adjacencyMap;
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private ArrayList<ArrayList<BoardCell>> boardCells;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private ArrayList<Card> deck; 
	private Solution answer;
	private Set<Player> playerInstances;
	
	// Constructor
	private Board() {
		super();
		this.legend = new HashMap<Character, String>();
		this.players = new HashMap<Color, String>();
		this.weapons = new HashSet<String>();
		this.playerInstances = new HashSet<Player>();
	}

	// Uses boardConfigFile and populates an array with boardCells
	private void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        String line;
        boardCells = new ArrayList();
        
        // Build array from file data
        int row = -1;
        while (in.hasNextLine()) {
        	row++;
			line = in.nextLine();
			ArrayList<BoardCell> thisRow = new ArrayList<BoardCell>();
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
        
        // check that each row has the same number of columns
        for (int i = 1; i < boardHeight; ++i) {
        	if (boardCells.get(i).size() != boardWidth) {
        		throw new BadConfigFormatException();
        	}
        }
	}

	// Populates a map of cells with a Set of their respective adjacent cells
	private Map< BoardCell, Set<BoardCell> > calcAdjacencies() {
		Map< BoardCell, Set<BoardCell> > adjacents = new HashMap< BoardCell, Set<BoardCell> >();
		Set<BoardCell> thisAdj = new HashSet<BoardCell>();

		for (ArrayList<BoardCell> rowArr: boardCells) {
			for(BoardCell cell: rowArr) {
				thisAdj = new HashSet<BoardCell>();
				
				// If cell isn't walkway or door, doesn't have any adjacencies 
				if (cell.getInitial() != 'W' && cell.getDoorDirection() == DoorDirection.NONE) {
					adjacents.put(cell, thisAdj);
					continue;
				}
				BoardCell cellBelow = null;
				BoardCell cellRight = null;
				BoardCell cellAbove = null;
				BoardCell cellLeft = null;
			
				// Set adjacent cells if they exist within the board
				if (cell.getRow() < boardHeight -1) {
					cellBelow = this.getCellAt(cell.getRow()+1, cell.getColumn());
				}
				if (cell.getColumn() < boardWidth -1) {
					cellRight = this.getCellAt(cell.getRow(), cell.getColumn()+1);
				}
				if (cell.getColumn() > 0) {
					cellLeft = this.getCellAt(cell.getRow(), cell.getColumn()-1);
				}
				if (cell.getRow() > 0) {
					cellAbove = this.getCellAt(cell.getRow()-1, cell.getColumn());
				}
				
				// Add adjacent cells to adjacency Set if they are a walkway or door
				if (cell.getInitial() == 'W') { // if current cell is a walkway
					if (cellBelow != null && (cellBelow.getInitial() == 'W' || cellBelow.getDoorDirection() == DoorDirection.UP)) {
						thisAdj.add(cellBelow);
					}
					if (cellAbove != null && (cellAbove.getInitial() == 'W' || cellAbove.getDoorDirection() == DoorDirection.DOWN)) {
						thisAdj.add(cellAbove);
					}
					if (cellRight != null && (cellRight.getInitial() == 'W' || cellRight.getDoorDirection() == DoorDirection.LEFT)) {
						thisAdj.add(cellRight);
					}
					if (cellLeft != null && (cellLeft.getInitial() == 'W' || cellLeft.getDoorDirection() == DoorDirection.RIGHT)) {
						thisAdj.add(cellLeft);
					}
				} else if (cell.getDoorDirection() != DoorDirection.NONE) { // if current cell is door slot
					if (cell.getDoorDirection() == DoorDirection.RIGHT &&
							(cellRight.getInitial() == 'W' || cellRight.getDoorDirection() != DoorDirection.NONE)) {
						thisAdj.add(cellRight);
					}
					if (cell.getDoorDirection() == DoorDirection.LEFT &&
							(cellLeft.getInitial() == 'W' || cellLeft.getDoorDirection() != DoorDirection.NONE)) {
						thisAdj.add(cellLeft);
					}
					if (cell.getDoorDirection() == DoorDirection.UP &&
							(cellAbove.getInitial() == 'W' || cellAbove.getDoorDirection() != DoorDirection.NONE)) {
						thisAdj.add(cellAbove);
					}
					if (cell.getDoorDirection() == DoorDirection.DOWN &&
							(cellBelow.getInitial() == 'W' || cellBelow.getDoorDirection() != DoorDirection.NONE)) {
						thisAdj.add(cellBelow);
					}
				} else { // if current cell is neither walkway nor door (as of now this should never happen)
					
				}
				
				
				// Put Set of adjacent cells into map for current cell
				adjacents.put(cell, thisAdj);
			}
		}
		return adjacents;
	}

	// Builds targets Set with possible cells to move to given a starting point, path length, and an empty Set of BoardCells 
	public Set<BoardCell> helperTargets(BoardCell start, int pathLength, Set<BoardCell> visited, BoardCell origin) {
		//if (visited == null) visited = new HashSet<BoardCell>();
		if (pathLength != 0 && !visited.contains(start)) {
			visited.add(start); // add current cell to a Set of visited cells
			for (BoardCell adj : adjacencyMap.get(start)){
				if (adj.getDoorDirection() == DoorDirection.NONE) {
					if (adj.getRow() != origin.getRow() || adj.getColumn() != origin.getColumn()) { // make sure the path never goes through origin
						helperTargets(adj, pathLength - 1, visited, origin); // recursively calls itself, reducing the pathLength and passing along a Set of previous BoardCells
					}
				} else { // add doors when not on even roll
					targets.add(adj);
				}
			}
		} else if (pathLength == 0){
			targets.add(start);
			visited.clear();
		}
		return visited;
	}

	// Calls helperTargets correctly so it can build targets Set
	public void calcTargets(int x, int y, int pathlength) {
		Set<BoardCell> visited = new HashSet<BoardCell>();
		this.targets.clear();
		helperTargets(this.getCellAt(x, y), pathlength, visited, this.getCellAt(x, y));
		targets.remove(this.getCellAt(x, y)); // removes starting cell from targets if it gets included
	}
	
	// Uses roomConfigFile and populates an Map with a legend with room names corresponding to characters
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ", ";
        String line;
        
        while (in.hasNextLine()) {
			line = in.nextLine();
            String[] input = line.split(delimeter);
            legend.put(input[0].charAt(0), input[1]);
            if (!input[2].equals("Card") && !input[2].equals("Other")) {
            	throw new BadConfigFormatException(input[2]);
            }
            if (input[2].equals("Card")) {
            	// TODO: create card and add to deck
            }
        }
	}
	
	// Uses playerConfigFile and populates an Map with a legend with colors corresponding to player characters
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ", ";
        String line;
        
        while (in.hasNextLine()) {
			line = in.nextLine();
            String[] input = line.split(delimeter);
            players.put(Color.getColor(input[1]), input[0]); // PDF has proper way to do string to color w/o errors if color not valid
            // TODO: create card and add to deck
        }
	}
	
	// Uses weapon ConfigFile and populates an Set with weapons
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);
        String line;
        
        while (in.hasNextLine()) {
			line = in.nextLine();
            weapons.add(line);
            // TODO: create card and add to deck
        }
	}
	
	// for the number of cards in the deck, will swap two random cards in the deck 
	private void shuffleDeck() {
		Random rand = new Random();
		int m,n;
		Card tempCard;
		for (int i = 0; i < deck.size(); ++i) {
			m = rand.nextInt(deck.size());
			n = rand.nextInt(deck.size());
			tempCard = deck.get(m);
//			deck[m] = deck[n]; // must be changed to not use index access (since deck is arraylist)
//			deck[n] = tempCard;
		}
	}
	
	// select 3 random cards, one of each type and set as answer
	public void selectAnswer() {
		Random rand = new Random();
		int m;
		
		String person = null;
		String weapon = null;
		String room = null;
		
		// multiple undefined types etc cause compile errors
		
//		while (person == null) {
//			m = rand.nextInt(deck.size());
//			if (deck.get(m).getType() == CardType.PERSON) {
//				person = deck.get(m).getCardName();
//			}
//		}
//		while (weapon == null) {
//			m = rand.nextInt(deck.size());
//			if (deck.get(m).getType() == CardType.weapon) {
//				weapon = deck.get(m).getCardName();
//			}
//		}
//		while (room == null) {
//			m = rand.nextInt(deck.size());
//			if (deck.get(m).getType() == CardType.room) {
//				room = deck.get(m).getCardName();
//			}
//		}
//		
//		answer = new Solution(person, weapon, room); //this construtor is undefined
	}
	
	// checks if accusation matches answer
	public Boolean checkAccusation(Solution accusation) {
		if (accusation == answer) {
			return true;
		}
		return false;
	}
	
	// inputs are TBD
	public Card handleSuggestion() {
		return null;
	}
	
	// setters and getters
	public void setConfigFiles(String b, String l, String p, String w) {
		this.roomConfigFile = l;
		this.boardConfigFile = b;
		this.playerConfigFile = p;
		this.weaponConfigFile = w;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return boardCells.get(row).get(col);
	}

	public Set<Player> getPlayerInstances(){
		return this.playerInstances;
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
		return boardInstance;
	}

	// TODO: re-factor
	public void initialize() {
		try {
			this.loadRoomConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e);  // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2:" + e.getMessage());  // io exception thrown by IntBoard Constructor
		}
		try {
			this.loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config1");  // io exception thrown by IntBoard Constructor
		}
		this.adjacencyMap = calcAdjacencies();
		
		// add methods here to instantiate human and computer players
		// call methods to create deck and deal cards
		
		try {
			this.loadPlayerConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config3");  // io exception thrown by IntBoard Constructor
		}
		try {
			this.loadWeaponConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config4");  // io exception thrown by IntBoard Constructor
		}
	}

//	public void selectAnswer() {
//		
//	}
//	
//	public Card handleSuggestion() { //args tbd
//		return null;
//	}
//	
//	public Boolean checkAccusation(Solution accusation) {
//		return null;
//	}

}
