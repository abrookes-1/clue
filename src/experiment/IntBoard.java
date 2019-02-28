package experiment;

import java.util.*;


public class IntBoard {
	Set<BoardCell> targets;
	Map< BoardCell, Set<BoardCell> > adjacencyMap;
	
	// Constructor
	public IntBoard() {
		super();
		this.adjacencyMap = calcAdjacencies();
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
