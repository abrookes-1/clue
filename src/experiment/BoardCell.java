package experiment;

/*
 *  @author Giorgio Cassata, Aidan Brookes
 *  Part 2 code: passing tests
 */

public class BoardCell {
	private int row;
	private int column;
	private String symbol; 
	
	// constructor
	public BoardCell(int row, int column, String symbol) {
		super();
		this.row = row;
		this.column = column;
		this.symbol = symbol;
	}
	
	// Getters
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public String getSymbol() {
		return symbol;
	}
	
	// Setters
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
}
