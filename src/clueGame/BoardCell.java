/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

public class BoardCell {
	private int row;
	private int column;
	private char initial; 
	private final int SIZE = 20; 
	private final int THICKNESS = 3;
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
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(column*SIZE, row*SIZE, SIZE, SIZE);
		} else if (initial == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
		} else {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
		}
		if ( Board.getInstance().getTargets().contains(this) ) {
			g.setColor(Color.CYAN);
			g.fillRect(column*SIZE, row*SIZE, SIZE, SIZE);
			g.setColor(Color.BLACK);
			g.drawRect(column*SIZE, row*SIZE, SIZE, SIZE);
		}
		
		// draw doors
		if (direction != DoorDirection.NONE) {
			g.setColor(Color.BLUE);
			switch (direction) {
			case DOWN:
				g.fillRect(column*SIZE, row*SIZE + SIZE - THICKNESS, SIZE, THICKNESS);
				break;
			case RIGHT:
				g.fillRect(column*SIZE + SIZE - THICKNESS, row*SIZE, THICKNESS, SIZE);
				break;
			case UP:
				g.fillRect(column*SIZE, row*SIZE, SIZE, THICKNESS);
				break;
			case LEFT:
				g.fillRect(column*SIZE, row*SIZE, THICKNESS, SIZE);
				break;
			default: 
				// shouldnt happen, maybe error here or something lmao
				break;
			}
			
		}
		
		
	}
	
	public void drawLabel(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawString(Board.getInstance().getLegend().get(initial), column*SIZE, row*SIZE);
		
	}
	
	public boolean containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(column*SIZE, row*SIZE + 2*SIZE + 6, SIZE, SIZE);
		if (rect.contains(new Point(mouseX, mouseY))) {
				return true;
			}
		return false;
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
