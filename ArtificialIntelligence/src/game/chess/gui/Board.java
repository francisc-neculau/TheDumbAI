package game.chess.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel
{
	private static final long serialVersionUID = -5937577678357934754L;
	
	private static final int CELL_SIZE   = 30;
	private static final int TOP_OFFSET  = 11;
	private static final int LEFT_OFFSET = 10;
	
	private static final Color brownCell  = new Color(255, 140, 0);
	private static final Color yellowCell = new Color(255, 250, 205);
	
	public Board(MouseListener mouseListener)
	{
		this.setBackground(new Color(139, 69, 19)); // brown color
		this.setBounds(30, 30, 319, 320);
		this.setLayout(null);
		JPanel gameSquare;
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
		JLabel label = null;
		Font font;
		for (int i = 0; i < 8; i++)
		{
			this.add(generateBlankCell(i + 1, 0, Integer.toString(8 - i)));
			for (int j = 1; j < 9; j++)
			{
				gameSquare = new JPanel();
				gameSquare.setBounds(10 + j * CELL_SIZE, 11 + (i + 1) * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				if( ( i + j ) % 2 != 0)
					gameSquare.setBackground(brownCell);
				else
					gameSquare.setBackground(yellowCell);
				if(i == 1)
					label = new JLabel("W");
				else if(i == 6)
					label = new JLabel("B");
				else
					label = new JLabel("");
				
				font = label.getFont();
				label.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));
				gameSquare.add(label);
				String name = "B" + Integer.toString(7-i) + Integer.toString(j -1) + "W" + Integer.toString(i) + Integer.toString(8 - j);
				gameSquare.setName(name);
				gameSquare.addMouseListener(mouseListener);
				this.add(gameSquare);
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
	}
	
	private JPanel generateBlankCell(int rowIndex, int columnIndex, String text)
	{
		JPanel blankSquare = new JPanel();
		blankSquare.setBounds(LEFT_OFFSET + columnIndex * CELL_SIZE, TOP_OFFSET + rowIndex * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		blankSquare.setBackground(Color.WHITE);
		blankSquare.add(new JLabel(text));
		return blankSquare;
	}
	
}
