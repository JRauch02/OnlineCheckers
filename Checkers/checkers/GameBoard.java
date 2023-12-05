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
	
	public void setPlayerNumber (boolean num) {
		this.isPlayerOne = num;
		
		if (isPlayerOne) {
			playerPieceColor = 0;
		}
		else {
			playerPieceColor = 1;
		}
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
		//fillTranslationDictionary();
	}
}
