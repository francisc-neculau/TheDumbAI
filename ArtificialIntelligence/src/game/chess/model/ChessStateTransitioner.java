package game.chess.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.state.StateTransitioner;

public class ChessStateTransitioner implements StateTransitioner<ChessState>
{
	private static Map<Integer, Byte> positionCheckMap;
	public static final Map<Integer, Byte> byteMap;
	
	static
	{
		positionCheckMap = new HashMap<>();
		positionCheckMap.put(0, Byte.valueOf((byte)0b10000000));
		positionCheckMap.put(1, Byte.valueOf((byte)0b01000000));
		positionCheckMap.put(2, Byte.valueOf((byte)0b00100000));
		positionCheckMap.put(3, Byte.valueOf((byte)0b00010000));
		positionCheckMap.put(4, Byte.valueOf((byte)0b00001000));
		positionCheckMap.put(5, Byte.valueOf((byte)0b00000100));
		positionCheckMap.put(6, Byte.valueOf((byte)0b00000010));
		positionCheckMap.put(7, Byte.valueOf((byte)0b00000001));
		
		byteMap = new HashMap<>();
		byteMap.put(7, Byte.valueOf((byte)0b10000000));
		byteMap.put(6, Byte.valueOf((byte)0b01000000));
		byteMap.put(5, Byte.valueOf((byte)0b00100000));
		byteMap.put(4, Byte.valueOf((byte)0b00010000));
		byteMap.put(3, Byte.valueOf((byte)0b00001000));
		byteMap.put(2, Byte.valueOf((byte)0b00000100));
		byteMap.put(1, Byte.valueOf((byte)0b00000010));
		byteMap.put(0, Byte.valueOf((byte)0b00000001));
	}
	
	@Override
	public List<ChessState> generateAllNextLegalStates(ChessState currentState)
	{
		List<ChessState> states = new ArrayList<>();
		
		byte[] toBeMovedPawns  = currentState.isWhiteToMove() ? currentState.getWhitePawns() : currentState.getBlackPawns();
		byte[] stationaryPawns = currentState.isWhiteToMove() ? currentState.getBlackPawns() : currentState.getWhitePawns();
		
		states.addAll(generateForwardingMoves(toBeMovedPawns,stationaryPawns,currentState.isWhiteToMove()));
		states.addAll(generateAtackingMoves(toBeMovedPawns,stationaryPawns,currentState.isWhiteToMove()));
		
		return states;
	}

	private List<ChessState> generateAtackingMoves(byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
	{
		List<ChessState> states = new ArrayList<>();
		for (int i = 0; i < 7; i++)
		{
			if(toBeMovedPawns[i] == 0) // no pawn on this row
				continue;
			// go through each column of the toBeMovedPawns
			for (int j = 0; j < toBeMovedPawns.length; j++)
			{
				/* Do we have a pawn to be moved ?
				 * check existence of pawn */
				if(!isPawnAt(j, toBeMovedPawns[i])) // no pawn on that column
					continue;
				/* Can attack left ?
				 * check board limit and check existence of opponent pawn */
				if(j - 1 >= 0 && isPawnAt(7 - j + 1, stationaryPawns[7 - i - 1]))
					states.add(move(i, i + 1, j, j - 1, toBeMovedPawns, stationaryPawns, isWhiteMove));
				/* Can attack right ?
				 * check board limit and check existence of opponent pawn */
				if(j + 1 < 8 && isPawnAt(7 - j - 1, stationaryPawns[7 - i - 1])) // 
					states.add(move(i, i + 1, j, j + 1, toBeMovedPawns, stationaryPawns, isWhiteMove));
			}
		}
		return states;
	}
	
	private List<ChessState> generateForwardingMoves(byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
	{
		List<ChessState> states = new ArrayList<>();
		// go through each row
		for (int i = 0; i < 7; i++)
		{
			if(toBeMovedPawns[i] == 0) // no pawn on this row
				continue;
			// go through each column of the toBeMovedPawns
			for (int j = 0; j < toBeMovedPawns.length; j++)
			{
				/* Do we have a pawn to be moved ?
				 * check existence of pawn */
				if(!isPawnAt(j, toBeMovedPawns[i])) // no pawn to be moved on that column
					continue;
				/* Can we move the pawn ?
				 * check non-existence of enemy blocking pawn or own blocking pawn  */
				if(isPawnAt(7 - j, stationaryPawns[7 - i - 1]) || isPawnAt(j, toBeMovedPawns[i + 1]))
					continue;
				states.add(move(i, i + 1, j, j, toBeMovedPawns, stationaryPawns, isWhiteMove));
			}
		}
		return states;
	}
	
	private ChessState move(int fromRowIndex, int toRowIndex, Integer fromColumnIndex, Integer toColumnIndex, byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
	{
		byte[] movedPawnsCopy      = Arrays.copyOf(toBeMovedPawns, 8);
		byte[] stationaryPawnsCopy = Arrays.copyOf(stationaryPawns, 8);
		
		movedPawnsCopy[fromRowIndex] = (byte) (movedPawnsCopy[fromRowIndex] - byteMap.get(fromColumnIndex));
		movedPawnsCopy[toRowIndex]   = (byte) (movedPawnsCopy[toRowIndex]   + byteMap.get(toColumnIndex));
		
		if(fromColumnIndex != toColumnIndex) // means we have an attacking move, thus we should clean the attacked position
			stationaryPawnsCopy[7 - toRowIndex] = (byte) (stationaryPawnsCopy[7 - toRowIndex] - byteMap.get(7- toColumnIndex));

		if(isWhiteMove)
			return new ChessState(movedPawnsCopy, stationaryPawnsCopy, false);
		return new ChessState(stationaryPawnsCopy, movedPawnsCopy, true);
	}
	
	public static boolean isPawnAt(Integer columnIndex, byte line)
	{
		byte b = byteMap.get(columnIndex);
		return ((line & b) == b) ? true : false;
	}
	
	public static boolean isFreeAt(int rowIndex, Integer columnIndex, byte[] whitePawns, byte[] blackPawns)
	{
		byte b = positionCheckMap.get(columnIndex);
		return ((whitePawns[rowIndex] & b) != b) &&
			   ((blackPawns[7 - rowIndex] & b) != b) ? true : false;
	}
	
	public static boolean isPositionBlockedAt(int rowIndex, Integer columnIndex, byte[] stationaryPawns)
	{
		if(columnIndex > - 1 && rowIndex < 8)
		{	
			byte leftB  = byteMap.get(7 - columnIndex);
			if((stationaryPawns[7 - rowIndex - 1] & leftB) == leftB)
				return true;
		}
		return false;
	}
	
	/**
	 * This function calculates if the position is free
	 * and is not attacked by the opponent.
	 * rowIndex and columnIndex have values from the
	 * perspective of the player that makes the move
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @param stationaryPawns
	 * @return
	 */
	public static boolean isPositionAtackedAt(int rowIndex, Integer columnIndex, byte[] stationaryPawns)
	{
		if(columnIndex - 1 > 0)
		{	
			byte leftB  = byteMap.get((7 - columnIndex) - 1);
			if((stationaryPawns[7 - rowIndex -1] & leftB) != leftB)
				return false;
		}
		if(columnIndex + 1 < 8)
		{	
			byte rightB = byteMap.get((7 - columnIndex) + 1);
			if((stationaryPawns[7 - rowIndex - 1] & rightB) != rightB)
				return false;
		}	
		return true;
	}
	
	public static boolean isFreeUnatackableAt(int rowIndex, Integer columnIndex, byte[] stationaryPawns)
	{
		if(columnIndex - 1 > 0)
		{	
			byte leftB  = byteMap.get((7 - columnIndex) - 1);
			if((stationaryPawns[7 - rowIndex] & leftB) != leftB)
				return false;
		}
		if(columnIndex + 1 < 8)
		{	
			byte rightB = byteMap.get((7 - columnIndex) + 1);
			if((stationaryPawns[7 - rowIndex] & rightB) != rightB)
				return false;
		}	
		return true;
	}
	
}
