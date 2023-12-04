package checkers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GameBoard {
	private boolean isPlayerOne;
	private Integer playerPieceColor;
	private BoardCell[][] cells;
	private List<Integer> oddStartCells = Arrays.asList(new Integer[] {1,3,5,7});
	private List<Integer> evenStartCells = Arrays.asList(new Integer[] {0,2,4,6});
	private MouseAdapter hoverEvents;
	private ActionListener cellAction;
	private ImageIcon greenPiece = new ImageIcon(GamePanel.class.getResource("greenPiece.png"));
	private ImageIcon tanPiece = new ImageIcon(GamePanel.class.getResource("tanPiece.png"));
	private ImageIcon greenPieceKing = new ImageIcon(GamePanel.class.getResource("greenPieceKing.png"));
	private ImageIcon tanPieceKing = new ImageIcon(GamePanel.class.getResource("tanPieceKing.png"));
	private Color tan = new Color(200,170,130);
	private Color green = new Color(1,50,32);
	private int rows = 8;
	private int columns = 8;
	private Color highLightColor = new Color(255,234,0);
	private BoardCell from;
	private BoardCell to;
	private GamePanel parent;
	private Map<Integer, Integer> dictionary = new HashMap<>();
	
	public void move(int fromRow, int fromColumn, int toRow, int toColumn) {
		BoardCell from = cells[fromRow][fromColumn];
		BoardCell to = cells[toRow][toColumn];
		
		
		
		to.setIcon(from.getIcon());
		to.setPieceColor(from.getPieceColor());
		to.setPiece(true);
		to.setKing(from.isKing());
		
		from.setIcon(null);
		from.setPiece(false);
		from.setKing(false);
		
		unHighlightPotentialMoves();
	}
	
	public void capture(int row, int column) {
		BoardCell toCapture = cells[row][column];
		if (playerPieceColor == toCapture.getPieceColor()) {
			removePiece(toCapture);
		}
		else if (playerPieceColor != toCapture.getPieceColor()) {
			removePiece(toCapture);
			parent.getSidePanel().pieceTaken();
		}
	}
	
	public void king(int row, int column) {
		BoardCell toKing = cells[row][column];
		
		toKing.setKing(true);
		
		if (toKing.getPieceColor() == 0) {
			toKing.setIcon(tanPieceKing);
		}
		else if (toKing.getPieceColor() == 1) {
			toKing.setIcon(greenPieceKing);
		}
	}
	
	public void fillTranslationDictionary() {
		dictionary.put(7,0);
		dictionary.put(6,1);
		dictionary.put(5, 2);
		dictionary.put(4, 3);
		dictionary.put(3, 4);
		dictionary.put(2, 5);
		dictionary.put(1, 6);
		dictionary.put(0, 7);
	}
	
	public void removePiece(BoardCell pieceRemoved) {
		pieceRemoved.setIcon(null);
		pieceRemoved.setPiece(false);
		pieceRemoved.setKing(false);
	}
	
	public void moveEnemyPiece(BoardCell from, BoardCell to) {
		//translate row of from to opposite side
		int iFrom = dictionary.get(from.getRow());
		//translate column of from to opposite side
		int jFrom = dictionary.get(from.getColumn());
		//translate row of to to opposite side
		int iTo = dictionary.get(to.getRow());
		//translate column of to to opposite side
		int jTo = dictionary.get(to.getColumn());
		// get equivalent cell on opposite side of board
		from = cells[iFrom][jFrom];
		// get equivalent cell on opposite side of board
		to = cells[iTo][jTo];
		// remove piece picture
		from.setIcon(null);
		// set cell to not have a piece
		from.setPiece(false);
		// set picture on new cell
		to.setIcon(greenPiece);
		// set that the cell does have a piece
		from.setPiece(true);
		// set that the piece is green
		from.setPieceColor(1);
	}
	
	public void movePiece(BoardCell to) {
		if (determineValidMoves().contains(to) && from.getPieceColor() == 0) {
			from.setIcon(null);
			from.setPiece(false);
			if (from.getPieceColor() == 1) {
				to.setIcon(greenPiece);
				to.setPieceColor(1);
				to.setPiece(true);
				
				//THIS LOGIC IS MESSED UP.
				if (cells[to.getRow()+1][to.getColumn()-1].hasPiece()) {
					removePiece(cells[to.getRow()+1][to.getColumn()-1]);
				}
				if (cells[to.getRow()+1][to.getColumn()+1].hasPiece()) {
					removePiece(cells[to.getRow()+1][to.getColumn()+1]);
				}
			}
			else if (from.getPieceColor() == 0) {
				to.setIcon(tanPiece);
				to.setPieceColor(0);
				to.setPiece(true);
				//the following line is for testing translation.
				// this will not be here in the final, this is just to demonstrate that we can interpret enemy moves to the opposite side for this stuff...
				//moveEnemyPiece(from, to); //HERE------------+++++++++++++++++++++----------------------
				//need a way to identify if this was a right move or a left move....
				//how would this be done?
				
				//need try catch in case there would be an error checking the first condition (if it was a jump move)
				try {
					//crowning a piece could also be done with similar logic - check if piece is on the opposite row
					if (to.equals(cells[from.getRow()-2][from.getColumn()-2])) {
						if (cells[to.getRow()+1][to.getColumn()+1].hasPiece()) {
							removePiece(cells[to.getRow()+1][to.getColumn()+1]);
							parent.getSidePanel().pieceTaken();
							System.out.println("Ate piece on left move!");
							//probably want some logic to check if there is a rejump available before sending to server
						}
					}
				} catch (Exception e) {
					System.out.println("Can't check for left move");
				}
				
				try {
					//crowning a piece could also be done with similar logic - check if piece is on the opposite row
					if (to.equals(cells[from.getRow()-2][from.getColumn()+2])) {
						if (cells[to.getRow()+1][to.getColumn()-1].hasPiece()) {
							removePiece(cells[to.getRow()+1][to.getColumn()-1]);
							parent.getSidePanel().pieceTaken();
							System.out.println("Ate piece on right move!");
							//probably want some logic to check if there is a rejump available before sending to server
						}
					}
				} catch (Exception e) {
					System.out.println("Can't check for right move");
				}
				
			}
			from = null;
			to = null;
		}
		else if (from.getPieceColor()==1) {
			from.setIcon(null);
			from.setPiece(false);
			to.setIcon(greenPiece);
			to.setPieceColor(1);
			to.setPiece(true);
			from = null;
			to = null;
		}
		
		unHighlightPotentialMoves();
		
	}
	
	public BoardCell[][] getCells() {
		return cells;
	}
	
	public BoardCell getCell(int row, int column) {
		return cells[row][column];
	}
	
	public void setPlayerNumber (boolean num) {
		this.isPlayerOne = num;
		
		if (isPlayerOne) {
			playerPieceColor = 0;
		}
		else {
			playerPieceColor = 1;
		}
	}
	
	public boolean getPlayerNumber () {
		return isPlayerOne;
	}
	
	public void setFrom(BoardCell from) {
		this.from = from;
	}
	
	public BoardCell getFrom() {
		return from;
	}
	
	public void setTo(BoardCell to) {
		this.to = to;
	}
	
	public void highlightPotentialMoves(String cell) {
		String[] coordinates = cell.split(",",2);
		coordinates[0] = coordinates[0].replaceAll("[^0-9]", "");
		coordinates[1] = coordinates[1].replaceAll("[^0-9]", "");
		
		cells[Integer.parseInt(coordinates[0])][Integer.parseInt(coordinates[1])].setBackground(highLightColor);
	}
	
	public void highLightPotentialMoves(List<Integer> coords) {
		for (int i = 0; i < coords.size(); i++) {
			cells[coords.get(i)][coords.get(i + 1)].setBackground(highLightColor);
			i = i +1;
		}
	}
	
	public void unHighlightPotentialMoves() {
		//int i = from.getRow();
		//int j = from.getColumn();
		for (BoardCell[] boardCells : cells) {
			for (BoardCell boardCell: boardCells) {
				//System.out.println("INDEX: " + boardCell.getRow() + "," + boardCell.getColumn());
				if (boardCell.isGreenCell()) {
					boardCell.setBackground(green);
				}
				else {
					boardCell.setBackground(tan);
				}
			}
		}
	}
	
	
	public List<BoardCell> determineValidMoves() {
		int i = from.getRow();
		int j = from.getColumn();
		boolean leftIsPossible = false;
		boolean rightIsPossible = false;
		//boolean backLeftIsPossible = false;
		//boolean backRightIsPossible = false;
		List<BoardCell> potentialMoves = new ArrayList<BoardCell>();
		BoardCell rightMove = null;
		BoardCell leftMove = null;
		try {
			leftMove = cells[i-1][j-1];
		} catch(Exception e) {		
			System.out.println("couldn't get left move");
		}
		try {
			rightMove = cells[i-1][j+1];
		} catch (Exception e) {
			System.out.println("couldn't get right move");
			
		}
		
		//if tan (player) piece
		if (from.getPieceColor() == 0) {
			//if the piece is a king
			if (from.isKing()) {
				
			}
			//if the piece is a normal piece
			else if (!from.isKing()) {
				if (leftMove != null && leftMove.hasPiece()) {
					if (leftMove.getPieceColor() == 0) {
						leftIsPossible = false;
					}
					else {

						leftMove = cells[leftMove.getRow()-1][leftMove.getColumn()-1];
						if (!leftMove.hasPiece()) {
							leftIsPossible = true;
						}
					}
				}
				else if (leftMove!=null){
					leftIsPossible = true;
				}
				if (rightMove != null && rightMove.hasPiece()) {
					if (rightMove.getPieceColor() == 0) {
						rightIsPossible = false;
					}
					else {

						rightMove = cells[rightMove.getRow()-1][rightMove.getColumn()+1];
						if (!rightMove.hasPiece()) {
							rightIsPossible = true;
						}
					}
				}
				else if (rightMove !=null) {
					rightIsPossible = true;
				}
			}
		}
		if (rightIsPossible) {
			potentialMoves.add(rightMove);
		}
		if (leftIsPossible) {
			potentialMoves.add(leftMove);
		}
		
		for (BoardCell boardCell : potentialMoves) {
			//boardCell.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			boardCell.setBackground(highLightColor);
		}
		
		return potentialMoves;
	}
	
	//called from addBoard
	public void setEvents() {
		hoverEvents = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				JButton parentButton = (JButton)e.getSource();
				parentButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
			}
			public void mouseExited(MouseEvent e) {
				JButton parentButton = (JButton)e.getSource();
				parentButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
		};
		
		cellAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BoardCell clickedCell = (BoardCell)e.getSource();
				GamePanel parent = (GamePanel)clickedCell.getParent();
				
				parent.getParent().getChatClient().sendSelectedCell(clickedCell);
				//if ()
				/*
				if (clickedCell.hasPiece()) {
					//parent.getBoard().setFrom(clickedCell);
					//parent.getBoard().unHighlightPotentialMoves();
					//parent.getBoard().determineValidMoves();
					parent.getParent().getChatClient().sendSelectedCell(clickedCell);
					
				}
				else if (!clickedCell.hasPiece() && parent.getBoard().getFrom() != null) {
					//parent.getBoard().movePiece(clickedCell);
					parent.getParent().getChatClient().sendSelectedCell(clickedCell);				}
					*/
			}
		};
	}
	
	
	//called before addBoard in gamePanel
	public void buildBoard() {
		cells = new BoardCell[rows][columns];
		boolean red = false;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j] = new BoardCell(i,j); 
				cells[i][j].setBackground(this.tan); //tan cells
				cells[i][j].setGreenCell(false);
				if (red) {
					cells[i][j].setBackground(this.green); //green cells
					cells[i][j].setGreenCell(true);
				}
				cells[i][j].setBorder(BorderFactory.createLineBorder(Color.WHITE));
				red = !red;
				//if we are on last coumn swap the color so that it stays the same on the first column of the next row...
				if (j == columns-1) {
					red = !red;
				}
				cells[i][j].addMouseListener(hoverEvents);
				cells[i][j].addActionListener(cellAction);
				
				if (isPlayerOne == true) {
					if (i == 0 || i == 2) {
						if (oddStartCells.contains(j)) {
							cells[i][j].setIcon(greenPiece);
							cells[i][j].setPieceColor(1);
							cells[i][j].setPiece(true);
						}
					}
					if (i==1) {
						if (evenStartCells.contains(j)) {
							cells[i][j].setIcon(greenPiece);
							cells[i][j].setPieceColor(1);
							cells[i][j].setPiece(true);
						}
					}
					if (i == 5 || i == 7) {
						if (evenStartCells.contains(j)) {
							cells[i][j].setIcon(tanPiece);
							cells[i][j].setPieceColor(0);
							cells[i][j].setPiece(true);
						}
					}
					if (i == 6) {
						if (oddStartCells.contains(j)) {
							cells[i][j].setIcon(tanPiece);
							cells[i][j].setPieceColor(0);
							cells[i][j].setPiece(true);
						}
					}
				}
				else {
					if (i == 0 || i == 2) {
						if (oddStartCells.contains(j)) {
							cells[i][j].setIcon(tanPiece);
							cells[i][j].setPieceColor(0);
							cells[i][j].setPiece(true);
						}
					}
					if (i==1) {
						if (evenStartCells.contains(j)) {
							cells[i][j].setIcon(tanPiece);
							cells[i][j].setPieceColor(0);
							cells[i][j].setPiece(true);
						}
					}
					if (i == 5 || i == 7) {
						if (evenStartCells.contains(j)) {
							cells[i][j].setIcon(greenPiece);
							cells[i][j].setPieceColor(1);
							cells[i][j].setPiece(true);
						}
					}
					if (i == 6) {
						if (oddStartCells.contains(j)) {
							cells[i][j].setIcon(greenPiece);
							cells[i][j].setPieceColor(1);
							cells[i][j].setPiece(true);
						}
					}
				}
				
			}
		}
	}
	
	//called after build board
	public void addBoard() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				parent.add(cells[i][j]);
				
			}
		}
		setEvents();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j].addMouseListener(hoverEvents);
				cells[i][j].addActionListener(cellAction);
				
			}
		}
		
		
	}

	public GameBoard(boolean playerOne, GamePanel parent) {
		isPlayerOne = playerOne;
		this.parent = parent;
		fillTranslationDictionary();
	}
}
