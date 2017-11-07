package game.chess.model;

import java.util.BitSet;

import model.state.State;

public class ChessState implements State<ChessStateTransitionDetails>
{
	private static final long serialVersionUID = -3205944318017712694L;
	
	/**
	 * Each array contains 8 bytes
	 * representing the positions of
	 * the pawns on the table.
	 * The two vectors are in mirror
	 * whit respect to each other
	 * 
	 * For example : 
	 * 					 
	 *  ~ 7 6 5 4 3 2 1 0 W
	 *	7 - - - - - - - - 0
	 *	6 - - - - - - - - 1
	 *	5 - - - - - - - w 2
	 *	4 - - w - - b - - 3
	 *	3 - - b - w - - - 4
	 *	2 - - - - - - - - 5
	 *	1 - - - - - - - - 6
	 *	0 - - - - - - - - 7
	 *	B 0 1 2 3 4 5 6 7 ~
	 * 
	 */
	private byte[] blackPawns;
	private byte[] whitePawns;
	
	private ChessStateTransitionDetails transitionDetails;
	
	boolean whiteToMove = false;
	boolean enPassantVulnerable = false;
	byte enPassantColumn;
	
	public ChessState(byte[] whitePawns, byte[] blackPawns, boolean isWhiteMove, boolean enPassantVulnerable)
	{
		this(whitePawns,blackPawns,isWhiteMove);
		this.enPassantVulnerable = enPassantVulnerable;
	}
	
	public ChessState(byte[] whitePawns, byte[] blackPawns, boolean isWhiteMove)
	{
		this.setWhitePawns(whitePawns);
		this.setBlackPawns(blackPawns);
		this.setWhiteToMove(isWhiteMove);
	}
	
	public byte[] getBlackPawns()
	{
		return blackPawns;
	}
	public void setBlackPawns(byte[] blackPawns)
	{
		if(blackPawns == null || blackPawns.length != 8)
			throw new IllegalArgumentException();
		this.blackPawns = blackPawns;
	}
	
	public byte[] getWhitePawns()
	{
		return whitePawns;
	}
	public void setWhitePawns(byte[] whitePawns)
	{
		if(whitePawns == null || whitePawns.length != 8)
			throw new IllegalArgumentException();
		this.whitePawns = whitePawns;
	}

	public ChessStateTransitionDetails getTransitionDetails()
	{
		return transitionDetails;
	}

	public void setTransitionDetails(ChessStateTransitionDetails transitionDetails)
	{
		this.transitionDetails = transitionDetails;
	}

	public boolean isWhiteToMove()
	{
		return whiteToMove;
	}

	public void setWhiteToMove(boolean whiteToMove)
	{
		this.whiteToMove = whiteToMove;
	}

	public boolean isEnPassantVulnerable()
	{
		return enPassantVulnerable;
	}

	public void setEnPassantVulnerable(boolean enPassantVulnerable)
	{
		this.enPassantVulnerable = enPassantVulnerable;
	}

	public byte getEnPassantColumn()
	{
		return enPassantColumn;
	}

	public void setEnPassantColumn(byte enPassantColumn)
	{
		this.enPassantColumn = enPassantColumn;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		BitSet bbits, wbits;
		byte bp, wb;
		
		sb.append("  a b c d e f g h  | " + ( whiteToMove ? "white" : "black") +" move");
		sb.append(System.lineSeparator());
		
		for (int i = 0; i < 8; i++)
		{
			sb.append((8 - i)).append(" ");
			
			wb = whitePawns[i];
			bp = blackPawns[7 - i];
			
			wbits = BitSet.valueOf(new byte[]{wb});
			bbits = BitSet.valueOf(new byte[]{bp});

			for (int j = 0; j < 8; j++)
			{
				if(!bbits.get(j) && !wbits.get(7 - j))
					sb.append("-");
				else if(bbits.get(j))
					sb.append("b");
				else
					sb.append("w");
				sb.append(" ");
			}
			
			sb.append((i + 1));
			sb.append(System.lineSeparator());
		}
		
		sb.append("  a b c d e f g h ");
		
		return sb.toString();
	}
	
}
