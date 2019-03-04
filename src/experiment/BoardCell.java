package experiment;

/*
 *  @author Giorgio Cassata, Aidan Brookes
 */

public class BoardCell {
	private int row;
	private int column;
	private String symbol; 
	
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
