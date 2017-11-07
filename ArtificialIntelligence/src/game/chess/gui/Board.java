package game.chess.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import game.chess.model.ChessState;
import static game.chess.model.ChessStateTransitioner.isPawnAt;

public class Board extends JPanel
{
	private static final long serialVersionUID = -5937577678357934754L;
	
	private static final int CELL_SIZE   = 30;
	private static final int TOP_OFFSET  = 11;
	private static final int LEFT_OFFSET = 10;
	
	private static final Color brownCell  = new Color(255, 140, 0);
	private static final Color yellowCell = new Color(255, 250, 205);
	
	private Map<String, JTextField> cellsMap;
	
	public static final Font bigBoldFont    = new Font("Arial", Font.BOLD, 17);
	public static final Font normalBoldFont = new Font("Arial", Font.BOLD, 11);
	
	public Board(ChessState state, MouseListener mouseListener)
	{
		this.cellsMap = new HashMap<>();
		this.setBackground(new Color(139, 69, 19)); // brown color
		this.setBounds(30, 30, 319, 320);
		this.setLayout(null);
		
		JTextField gameCell;
		/*
		 * Create Header of the board
		 */
		this.add(generateBlankCell(0, 0, ""));
		for (int i = 1; i < 9; i++)
			this.add(generateBlankCell(0, i, Character.toString((char)(73 - i))));
		this.add(generateBlankCell(0, 9, ""));
		/*
		 * Create Body of the board
		 */
		for (int i = 0; i < 8; i++)
		{
			this.add(generateBlankCell(i + 1, 0, Integer.toString(8 - i)));
			for (int j = 1; j < 9; j++)
			{
				gameCell = new JTextField();
				gameCell.setEditable(false);
				gameCell.setColumns(10);
				gameCell.setBorder(null);
				gameCell.setHorizontalAlignment(SwingConstants.CENTER);
				gameCell.setBounds(10 + j * CELL_SIZE, 11 + (i + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				if( ( i + j ) % 2 != 0)
					gameCell.setBackground(brownCell);
				else
					gameCell.setBackground(yellowCell);
				String name = Integer.toString(7-i) + Integer.toString((j - 1));
				gameCell.setName(name);
				gameCell.addMouseListener(mouseListener);
				cellsMap.put(name, gameCell);
				this.add(gameCell);
			}
			this.add(generateBlankCell(i + 1, 9, Integer.toString(i + 1)));
		}
		/*
		 * Create Footer of the board
		 */
		this.add(generateBlankCell(9, 0, ""));
		for (int i = 1; i < 9; i++)
			this.add(generateBlankCell(9, i, Character.toString((char)(64 + i))));
		this.add(generateBlankCell(9, 9, ""));
		this.reDraw(state);
	}
	
	private JPanel generateBlankCell(int rowIndex, int columnIndex, String text)
	{
		JPanel blankSquare = new JPanel();
		blankSquare.setBounds(LEFT_OFFSET + columnIndex * CELL_SIZE, TOP_OFFSET + rowIndex * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		blankSquare.setBackground(Color.WHITE);
		blankSquare.add(new JLabel(text));
		return blankSquare;
	}

	public void reDraw(ChessState state)
	{
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				cellsMap.get(Integer.toString(i) + Integer.toString(j)).setText("");
				cellsMap.get(Integer.toString(i) + Integer.toString(j)).setFont(normalBoldFont);
			}
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
			{
				if(isPawnAt(i, j, state.getBlackPawns()))
					cellsMap.get(Integer.toString(i) + Integer.toString(j)).setText("B");
				if(isPawnAt(i, j, state.getWhitePawns()))
					cellsMap.get(Integer.toString(7 - i) + Integer.toString(7 - j)).setText("W");
			}
	}
}
