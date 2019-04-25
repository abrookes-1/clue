/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Board extends JPanel{
	private static Board boardInstance = new Board();
	private int boardWidth;
	private int boardHeight;

	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;

	private Map< BoardCell, Set<BoardCell> > adjacencyMap;
	private Set<BoardCell> targets;
	private ArrayList<ArrayList<BoardCell>> boardCells;
	private ArrayList<Integer> startingRows = new ArrayList<Integer>(Arrays.asList(0,6,6,13,15,22));
	private ArrayList<Integer> startingColumns = new ArrayList<Integer>(Arrays.asList(14,0,24,0,24,16));

	private Set<BoardCell> roomLabelCells;
	private Map<Character, String> legend;
	private Map<String, String> characters;
	private Set<String> weapons;
	private Set<String> rooms;

	private ArrayList<Card> deck;
	private Solution answer;

	private Set<Player> playerInstances;
	private Set<ComputerPlayer> compPlayerInstances;
	private HumanPlayer onlyHuman;

	private boolean humanFinished = true;
	private int die;
	private Player currentPlayer;
	private Iterator<Player> iter;
	private Card reason;
	private boolean humanPlayerInRoom = false;
	private Solution lastGuess = new Solution("","","");

	// Constructor
	private Board() {
		super();
		this.targets = new HashSet<BoardCell>();

		this.roomLabelCells = new HashSet<BoardCell>();
		this.legend = new HashMap<Character, String>();
		this.characters = new HashMap<String, String>();
		this.weapons = new HashSet<String>();
		this.rooms = new HashSet<String>();
		this.deck = new ArrayList<Card>();
		this.playerInstances = new HashSet<Player>();
		this.compPlayerInstances = new HashSet<ComputerPlayer>();
		iter = playerInstances.iterator();
	}

	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~		Turn Handling	 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	// Performs a random die roll (6 sided die)
	private void roll() {
		Random rand = new Random();
		die = rand.nextInt(5) + 1;
	}

	// Check if human player is in room
	public boolean humanPlayerIsInRoom() {
		if (getCellAt(onlyHuman.getRow(), onlyHuman.getCol()).getInitial() != 'W'){
			return true;
		}
		return false;
	}

	// Iterates playerInstances and sets next player as current
	private void nextPlayer() {
		if (!iter.hasNext()) {
			iter = playerInstances.iterator();
		}
		currentPlayer = iter.next();
	}

	// Set human player's turn as completed
	public void humanFinished() {
		humanFinished = true;
	}
 
	// Check if human player's turn has completed
	public boolean isFinished() {
		return humanFinished;
	}

	// After something has been disproved, 'shows' card to other players
	public void updateSeen() {
		for (Player pla: playerInstances) {
			if (reason == null) continue;
			if (reason.getType() == CardType.PERSON) {
				pla.removeUnseenPerson(reason);
			}
		}
	}

	// Performs logic of iterating turn
	public boolean startNextPlayer () {
		nextPlayer();
		roll();
		calcTargets(currentPlayer.getRow(), currentPlayer.getCol(), getDie());
		if (currentPlayer.isHuman) {
			humanFinished = false;
			if (getCellAt(currentPlayer.getRow(), currentPlayer.getCol()).getInitial() != 'W') {
				humanPlayerInRoom = true;
			} else {
				humanPlayerInRoom = false;
			}
			repaint(); // ensures targets are drawn
		} else {
			currentPlayer.setRow(targets.iterator().next().getRow());
			currentPlayer.setCol(targets.iterator().next().getColumn());
			if (getCellAt(currentPlayer.getRow(), currentPlayer.getCol()).getInitial() != 'W') {
				// make suggestion
				Solution suggestion = currentPlayer.createSuggestion();
				handleSuggestion(suggestion, currentPlayer);
			}
			targets.clear();
			repaint();
		}


		return true;
	}

	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~ Target & Movement Handling ~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
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
				} else { // if current cell is neither walkway nor door  do nothing for now

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
					// TODO: I don't completely understand what "not on even roll" means
					// should it only add doors if origin.getDoorDirection() == DoorDirection.NONE???
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

	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Deck & Card Handling 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	// for the number of cards in the deck, will swap two random cards in the deck
	private void shuffleDeck() {
		Random rand = new Random();
		int m,n;
		Card tempCard;
		for (int i = 0; i < deck.size(); ++i) {
			m = rand.nextInt(deck.size());
			n = rand.nextInt(deck.size());
			tempCard = deck.get(m);
			deck.set(m,deck.get(n));
			deck.set(n, tempCard);
		}
	}

	// Distributes cards in deck evenly to players
	private void dealDeck() {
		int index = 0;
		while (index < deck.size()) {
			for (Player pla:playerInstances) {
				if (index < deck.size()) {
					pla.dealCard(deck.get(index));
					index++;
				} else {
					break;
				}
			}
		}
	}

	// To use before selecting answer, will create sets of cards of each type and set tham as unseen for each player
	private void setUnseen() {
		for (Player pla:playerInstances) {
			
			Set<Card> toReturn1 = new HashSet<Card>();
			Set<Card> toReturn2 = new HashSet<Card>();
			Set<Card> toReturn3 = new HashSet<Card>();
	
			for (Card card:deck) {
				if (card.getType() == CardType.PERSON) {
					toReturn1.add(card);
				}
				if (card.getType() == CardType.WEAPON) {
					toReturn2.add(card);
				}
				if (card.getType() == CardType.ROOM) {
					toReturn3.add(card);
				}
			}
		
			pla.setUnseenPeople(toReturn1);
			pla.setUnseenWeapons(toReturn2);
			pla.setUnseenRooms(toReturn3);
		}
	}

	// select 3 random cards, one of each type and set as answer
	public void selectAnswer() {
		Random rand = new Random();
		int m;

		String person = null;
		String weapon = null;
		String room = null;

		while (person == null) {
			m = rand.nextInt(deck.size());
			if (deck.get(m).getType() == CardType.PERSON) {
				person = deck.get(m).getCardName();
				deck.remove(m);
			}
		}
		while (weapon == null) {
			m = rand.nextInt(deck.size());
			if (deck.get(m).getType() == CardType.WEAPON) {
				weapon = deck.get(m).getCardName();
				deck.remove(m);
			}
		}
		while (room == null) {
			m = rand.nextInt(deck.size());
			if (deck.get(m).getType() == CardType.ROOM) {
				room = deck.get(m).getCardName();
				deck.remove(m);
			}
		}
		answer = new Solution(person, weapon, room);
	}

	// checks if accusation matches answer and moves accused player to room
	public boolean checkAccusation(Solution accusation) {
		for (Player pla: playerInstances) {
			if (pla.getCharacter().contentEquals(accusation.person)) {
				pla.setRow(currentPlayer.getRow());
				pla.setCol(currentPlayer.getCol());
				repaint();
			}
		}
		if (accusation.person.equals(answer.person)) {
			if (accusation.weapon.equals(answer.weapon)) {
				if (accusation.room.equals(answer.room)) {
					return true;
				}
			}
		}
		return false;
	}

	// Checks if any player other than the accuser is able to disprove a suggestion, returns disproving card
	public void handleSuggestion(Solution sugg, Player suggSource) {
		lastGuess = sugg;
		reason = null;
		Card result;
		for (Player pla: playerInstances) {
			
			if (pla.getCharacter().contentEquals(sugg.person)) {
				pla.setRow(currentPlayer.getRow());
				pla.setCol(currentPlayer.getCol());
				repaint();
			}
			
			if (pla != suggSource) {
				result = pla.disproveSuggestion(sugg);
				if (result != null) {
				reason = result;
				}
			}
		}
	}

	
	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Config File Handling 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	// Uses boardConfigFile and populates an array with boardCells
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
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
            			if (input[col].charAt(1) == 'Z') {
            				roomLabelCells.add(cell);
            			}
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
	
	// Uses roomConfigFile and populates an Map with a legend with room names corresponding to characters
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        String line;

        while (in.hasNextLine()) {
			line = in.nextLine();
            String[] input = line.split(delimeter);
            legend.put(input[0].charAt(0), input[1]);
            if (!input[2].equals("Card") && !input[2].equals("Other")) {
            	throw new BadConfigFormatException(input[2]);
            }
            if (input[2].equals("Card")) {
            	Card newCard = new Card(input[1], CardType.ROOM);
            	deck.add(newCard);
            	rooms.add(input[1]);
            }
        }
	}

	// Uses playerConfigFile and populates an Map with a legend with colors corresponding to player characters
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);
        String delimeter = ",";
        String line;
        ComputerPlayer cpla;

        while (in.hasNextLine()) {
			line = in.nextLine();
            String[] input = line.split(delimeter);
            characters.put(input[1], input[0]); // PDF has proper way to do string to color w/o errors if color not valid
            Card newCard = new Card(input[0], CardType.PERSON);
        	deck.add(newCard);
        	if (playerInstances.size() == 0) {
        		onlyHuman = new HumanPlayer(input[0],input[1]);
        		onlyHuman.isHuman = true;
        		playerInstances.add(onlyHuman);
        	} else {
        		cpla = new ComputerPlayer(input[0],input[1]);
        		playerInstances.add(cpla);
        		compPlayerInstances.add(cpla);
        	}
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
            Card newCard = new Card(line, CardType.WEAPON);
        	deck.add(newCard);
        }
	}

	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Game Init & Drawing 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public void assignStartingPositions() {
		int i = 0;
		for (Player pla:playerInstances) {
			pla.setRow(startingRows.get(i));
			pla.setCol(startingColumns.get(i));
			i++;
		}
	}

	public void paintComponent(Graphics g) {
		for (ArrayList<BoardCell> row:boardCells) {
			for (BoardCell cell:row) {
				cell.draw(g);
			}
		}
		for (Player pla:playerInstances) {
			pla.draw(g);
		}
		for (BoardCell cell:roomLabelCells) {
			cell.drawLabel(g);
		}
	}

	
	// Loads information from external files, creates and manages a deck of cards, and
	// places players on the board
	public void initialize() {
		try {
			this.loadRoomConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e);  // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2: " + e.getMessage());  // io exception thrown by IntBoard Constructor
		}
		
		try {
			this.loadBoardConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2: " + e.getMessage());  // io exception thrown by IntBoard Constructor
		}
		this.adjacencyMap = calcAdjacencies();


		try {
			this.loadPlayerConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2: " + e.getMessage());
		}
		
		try {
			this.loadWeaponConfig();
		} catch (FileNotFoundException e) {
			System.out.println(e); // file not found exception thrown by IntBoard Constructor
		} catch (BadConfigFormatException e) {
			System.out.println("Bad Config2: " + e.getMessage());
		}

		//Create deck and deal cards
		shuffleDeck();
		setUnseen();
		selectAnswer();
		dealDeck();

		assignStartingPositions();
		// TODO: makeHumanFirst();
	}


	/*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 	~~~~	Getters and Setters 	~~~~
	 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public static Board getInstance() {
		return boardInstance;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCellAt(int row, int col) {
		return boardCells.get(row).get(col);
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjacencyMap.get(this.getCellAt(row,col));
	}

	public int getNumRows() {
		return boardHeight;
	}

	public int getNumColumns() {
		return boardWidth;
	}

	public Set<Player> getPlayerInstances(){
		return this.playerInstances;
	}

	public Set<ComputerPlayer> getCompPlayerInstances(){
		return this.compPlayerInstances;
	}

	public HumanPlayer getHuman() {
		return onlyHuman;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public Set<String> getCharacters() {
		Set<String> toReturn = new HashSet<String>();
		for (String item:characters.values()) {
			toReturn.add(item);
		}
		return toReturn;
	}

	public Set<String> getWeapons() {
		return weapons;
	}

	public Set<String> getRooms() {
		return rooms;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Solution getAnswer() {
		return answer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getDie() {
		return die;
	}

	public String getResponse() {
		if (reason == null) return "no new clue";
		return reason.getCardName();
	}
	
	public Solution getLastGuess() {
		return lastGuess;
	}
	
	public void setConfigFiles(String board, String rooms, String characters, String weapons) {
		this.roomConfigFile = rooms;
		this.boardConfigFile = board;
		this.playerConfigFile = characters;
		this.weaponConfigFile = weapons;
	}

	public void setLegend(Map<Character, String> legend) {
		this.legend = legend;
	}


}
