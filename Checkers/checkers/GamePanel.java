package checkers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private ClientGUI parent;
	private BoardCell[][] cells;
	private Color tan = new Color(200,170,130);
	private Color green = new Color(1,50,32);
	private Color highLightColor = new Color(255,234,0);
	private GameSidePanel sidePanel;
	private GameBoard board;
	
	public GameSidePanel getSidePanel() {
		return sidePanel;
	}
	
	public GameBoard getBoard() {
		return board;
	}
	
	public Color getTan() {
		return tan;
	}
	
	public Color getGreen() {
		return green;
	}
	
	public Color getHighLightColor() {
		return highLightColor;
	}
	
	public ClientGUI getParent() {
		return parent;
	}
	
	public void buildBoard() {
		board.buildBoard();
	}
	
	public void addBoard() {
		board.addBoard();
	}

	
	public GamePanel(ClientGUI parent, GameSidePanel sidePanel) {
		this.parent = parent;
		this.setSize(600,600);
		this.sidePanel = sidePanel;
		setLayout(new GridLayout(8,8));
		this.board = new GameBoard(true, this);
		}
		//cells[4][4].setBackground(Color.BLACK);
		//cells[3][3].setBackground(Color.WHITE);//top left
		//cells[3][5].setBackground(Color.WHITE);//top right
		//cells[2][2].setBackground(Color.BLACK);//2x top left}
		
	}
