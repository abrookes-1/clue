package clueGame;

/*
 *  @author Giorgio Cassata, Aidan Brookes
 *  Part 2 code: passing tests
 */

public class BoardCell {
	private int row;
	private int column;
	private char initial; 
	
	// constructor
	public BoardCell(int row, int column, char symbol) {
		super();
		this.row = row;
		this.column = column;
		this.initial = symbol;
	}
	
	// Getters
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public char getInitial() {
		return initial;
	}
	
	public DoorDirection getDoorDirection() {
		return DoorDirection.NONE;
	}
	
	// Setters
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setInitial(char symbol) {
		this.initial = symbol;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;

	}
	
	public boolean isDoorway() {
		return false;

	}
	
	
}
