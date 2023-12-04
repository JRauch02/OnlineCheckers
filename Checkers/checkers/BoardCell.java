package checkers;

import javax.swing.JButton;

public class BoardCell extends JButton{
	int row;
	int column;
	boolean piece = false;
	boolean pieceIsKing = false;
	int pieceColor = 0;
	boolean cellIsGreen = false;
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	public void setPieceColor(int pieceColor) {
		this.pieceColor = pieceColor;
	}
	
	public int getPieceColor() {
		return this.pieceColor;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public boolean hasPiece() {
		if (piece) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setGreenCell(boolean bool) {
		this.cellIsGreen = bool;
	}
	
	public boolean isGreenCell() {
		return cellIsGreen;
	}
	
	public void setPiece(boolean piece) {
		this.piece = piece;
	}
	
	public boolean isKing()  {
		return pieceIsKing;
	}
	
	public void setKing(boolean kingStatus) {
		pieceIsKing = kingStatus;
	}
	
	public String toString() {
		return "(" + row + "," + column+")";
	}
}
