/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private char initial; 
	private final int SIZE = 20;
	private DoorDirection direction;
	
	// Constructor
	public BoardCell(int row, int column, char symbol) {
		super();
		this.row = row;
		this.column = column;
		this.initial = symbol;
		this.direction = DoorDirection.NONE;
	}

	public void draw(Graphics g) {
		if (initial == 'W') {
			g.setColor(Color.YELLOW);
			g.fillRect(row*SIZE, column*SIZE, SIZE, SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(row*SIZE, column*SIZE, SIZE, SIZE);
		} else {
			g.setColor(Color.GRAY);
			g.fillRect(row*SIZE, column*SIZE, SIZE, SIZE);
		}
		
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
