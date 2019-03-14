/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial; 
	private DoorDirection direction;
	
	// Constructor
	public BoardCell(int row, int column, char symbol) {
		super();
		this.row = row;
		this.column = column;
		this.initial = symbol;
		this.direction = DoorDirection.NONE;
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
		return this.direction;
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
	public void setDirection(char direction) {
		switch(direction) {
		case 'D':
			this.direction = DoorDirection.DOWN;
			break;
		case 'R':
			this.direction = DoorDirection.RIGHT;
			break;
		case 'U':
			this.direction = DoorDirection.UP;
			break;
		case 'L':
			this.direction = DoorDirection.LEFT;
			break;
		default:
			this.direction = DoorDirection.NONE;
			break;
		}
	}
	
	// Checks for cell conditions
	public boolean isWalkway() {
		return false;
	}
	public boolean isRoom() {
		return false;

	}
	public boolean isDoorway() {
		if (this.direction == DoorDirection.NONE) return false;
		return true;
	}
	
	
}
