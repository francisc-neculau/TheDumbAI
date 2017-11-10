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
	public static final Map<Byte, Integer> reversedByteMap;
	public static final Map<Byte, Integer> byteTrueBitsMap;
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
		
		reversedByteMap = new HashMap<>();
		reversedByteMap.put(Byte.valueOf((byte)0b10000000), 7);
		reversedByteMap.put(Byte.valueOf((byte)0b01000000), 6);
		reversedByteMap.put(Byte.valueOf((byte)0b00100000), 5);
		reversedByteMap.put(Byte.valueOf((byte)0b00010000), 4);
		reversedByteMap.put(Byte.valueOf((byte)0b00001000), 3);
		reversedByteMap.put(Byte.valueOf((byte)0b00000100), 2);
		reversedByteMap.put(Byte.valueOf((byte)0b00000010), 1);
		reversedByteMap.put(Byte.valueOf((byte)0b00000001), 0);
		
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
		if(currentState.isEnPassantVulnerable())
			states.addAll(generateEnPassatAttackMove(toBeMovedPawns,stationaryPawns, currentState.getEnPassantColumn(), currentState.isWhiteToMove()));
		
		return states;
	}

	private List<ChessState> generateEnPassatAttackMove(byte[] toBeMovedPawns, byte[] stationaryPawns, byte enPassatColumn, boolean isWhiteMove)
	{
		List<ChessState> states = new ArrayList<>();
		ChessState state;
		int toColumnIndex = 7 - reversedByteMap.get(enPassatColumn);
		byte[] movedPawnsCopy      = Arrays.copyOf(toBeMovedPawns, 8);
		byte[] stationaryPawnsCopy = Arrays.copyOf(stationaryPawns, 8);
		
		// Attacks from left to the center
		if(toColumnIndex - 1 > 0 && isPawnAt(4, toColumnIndex - 1, toBeMovedPawns))
		{
			movedPawnsCopy[4] = (byte) (movedPawnsCopy[4] - byteMap.get(toColumnIndex - 1));
			movedPawnsCopy[5] = (byte) (movedPawnsCopy[5] + byteMap.get(toColumnIndex));
			
			stationaryPawnsCopy[3] = (byte) (stationaryPawnsCopy[3] - byteMap.get(7 - toColumnIndex));
			if(isWhiteMove)
			{
				state = new ChessState(movedPawnsCopy, stationaryPawnsCopy, false);
				state.setTransitionDetails(new ChessStateTransitionDetails(
						true, true, true, 1, 5, 7 - toColumnIndex));
			}
			else
			{
				state = new ChessState(stationaryPawnsCopy, movedPawnsCopy, true);
				state.setTransitionDetails(new ChessStateTransitionDetails(
						true, true, true, 1, 5, toColumnIndex));
			}
			
			states.add(state);
		}
		
		movedPawnsCopy      = Arrays.copyOf(toBeMovedPawns, 8);
		stationaryPawnsCopy = Arrays.copyOf(stationaryPawns, 8);
		// Attacks from right to the center
		if(toColumnIndex + 1 < 8 && isPawnAt(4, toColumnIndex + 1, toBeMovedPawns))
		{
			movedPawnsCopy[4] = (byte) (movedPawnsCopy[4] - byteMap.get(toColumnIndex + 1));
			movedPawnsCopy[5] = (byte) (movedPawnsCopy[5] + byteMap.get(toColumnIndex));
			
			stationaryPawnsCopy[3] = (byte) (stationaryPawnsCopy[3] - byteMap.get(7 - toColumnIndex));
			if(isWhiteMove)
			{
				state = new ChessState(movedPawnsCopy, stationaryPawnsCopy, false);
				state.setTransitionDetails(new ChessStateTransitionDetails(
						true, true, true, 1, 5, 7 - toColumnIndex));
			}
			else
			{
				state = new ChessState(stationaryPawnsCopy, movedPawnsCopy, true);
				state.setTransitionDetails(new ChessStateTransitionDetails(
						true, true, true, 1, 5, toColumnIndex));
			}
			
			states.add(state);
		}

		return states;
	}

	private List<ChessState> generateAtackingMoves(byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
	{
		List<ChessState> states = new ArrayList<>();
		ChessState generatedState;
		
//		// Check EnPassat Attacks
//		for (int i = 0; i < 8; i++)
//		{
//			if(!isPawnAt(1, i, toBeMovedPawns))
//				continue;
//			/* Can we move the pawn two step ?
//			 * check non-existence of enemy blocking pawn or own blocking pawn two cells ahead */
//			if(isPawnAt(5, 7 - i, stationaryPawns) || isPawnAt(2, i, toBeMovedPawns) ||
//				isPawnAt(4, 7 -i, stationaryPawns) || isPawnAt(3, i, toBeMovedPawns))
//				continue;
//			generatedState = move(1, 3, i, i, toBeMovedPawns, stationaryPawns, isWhiteMove);
//			generatedState.setEnPassantVulnerable(true);
//			generatedState.setEnPassantColumn(byteMap.get(i));
//			generatedState.setTransitionDetails(new ChessStateTransitionDetails(
//					true, false, true, 2, 3, i));
//			states.add(generatedState);
//		}

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
				{
					generatedState = move(i, i + 1, j, j - 1, toBeMovedPawns, stationaryPawns, isWhiteMove);
					generatedState.setTransitionDetails(new ChessStateTransitionDetails(
							true, true, false, 1, i + 1, j - 1));
					states.add(generatedState);
				}
				/* Can attack right ?
				 * check board limit and check existence of opponent pawn */
				if(j + 1 < 8 && isPawnAt(7 - j - 1, stationaryPawns[7 - i - 1]))
				{
					generatedState = move(i, i + 1, j, j + 1, toBeMovedPawns, stationaryPawns, isWhiteMove);
					generatedState.setTransitionDetails(new ChessStateTransitionDetails(
							true, true, false, 1, i + 1, j + 1));
					states.add(generatedState);
				}
			}
		}
		return states;
	}
	
	private List<ChessState> generateForwardingMoves(byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
	{
		List<ChessState> states = new ArrayList<>();
		ChessState generatedState;
		
		// check the double moves
		// go through each column
		for (int i = 0; i < 8; i++)
		{
			if(!isPawnAt(1, i, toBeMovedPawns))
				continue;
			/* Can we move the pawn two step ?
			 * check non-existence of enemy blocking pawn or own blocking pawn two cells ahead */
			if(isPawnAt(5, 7 - i, stationaryPawns) || isPawnAt(2, i, toBeMovedPawns) ||
				isPawnAt(4, 7 -i, stationaryPawns) || isPawnAt(3, i, toBeMovedPawns))
				continue;
			generatedState = move(1, 3, i, i, toBeMovedPawns, stationaryPawns, isWhiteMove);
			generatedState.setEnPassantVulnerable(true);
			generatedState.setEnPassantColumn(byteMap.get(i));
			generatedState.setTransitionDetails(new ChessStateTransitionDetails(
					true, false, true, 2, 3, i));
			states.add(generatedState);
		}
		
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
				/* Can we move the pawn one step ?
				 * check non-existence of enemy blocking pawn or own blocking pawn one cell ahead  */
				if(isPawnAt(7 - j, stationaryPawns[7 - i - 1]) || isPawnAt(j, toBeMovedPawns[i + 1]))
					continue;
				
				generatedState = move(i, i + 1, j, j, toBeMovedPawns, stationaryPawns, isWhiteMove);
				generatedState.setTransitionDetails(new ChessStateTransitionDetails(
						true, false, false, 1, i + 1, j));
				states.add(generatedState);
			}
		}
		return states;
	}
	
	public static ChessState move(int fromRowIndex, int toRowIndex, Integer fromColumnIndex, Integer toColumnIndex, ChessState state)
	{
		byte[] toBeMovedPawns  = state.getBlackPawns();
		byte[] stationaryPawns = state.getWhitePawns();
		boolean isWhiteMove    = state.isWhiteToMove();
		if(state.isWhiteToMove())
		{
			toBeMovedPawns  = state.getWhitePawns();
			stationaryPawns = state.getBlackPawns();
		}
		return move(fromRowIndex, toRowIndex, fromColumnIndex, toColumnIndex, toBeMovedPawns, stationaryPawns, isWhiteMove);
	}
	
	public static ChessState move(int fromRowIndex, int toRowIndex, Integer fromColumnIndex, Integer toColumnIndex, byte[] toBeMovedPawns, byte[] stationaryPawns, boolean isWhiteMove)
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
		columnIndex = 7 - columnIndex;
		rowIndex	= 7 - rowIndex;
		if(columnIndex - 1 > 0)
		{	
			byte leftB  = byteMap.get(columnIndex - 1);
			if((stationaryPawns[rowIndex -1] & leftB) == leftB)
				return true;
		}
		if(columnIndex + 1 < 8)
		{	
			byte rightB = byteMap.get((columnIndex) + 1);
			if((stationaryPawns[rowIndex - 1] & rightB) == rightB)
				return true;
		}	
		return false;
	}
	
	public static boolean isPositionBackingAt(int rowIndex, Integer columnIndex, byte[] personalPawns)
	{
		if(columnIndex - 1 > 0)
		{	
			byte leftB  = byteMap.get(columnIndex - 1);
			if((personalPawns[rowIndex -1] & leftB) != leftB)
				return false;
		}
		if(columnIndex + 1 < 8)
		{	
			byte rightB = byteMap.get(columnIndex + 1);
			if((personalPawns[rowIndex - 1] & rightB) != rightB)
				return false;
		}	
		return true;
	}
	
	public static boolean isPositionBlockingAt(int rowIndex, Integer columnIndex, byte[] personalPawns)
	{
		if(columnIndex > - 1 && rowIndex < 8)
		{	
			byte leftB  = byteMap.get(columnIndex);
			if((personalPawns[rowIndex - 1] & leftB) == leftB)
				return true;
		}
		return false;
	}
	
	public static boolean isFreeUnatackableAt(int rowIndex, Integer columnIndex, byte[] stationaryPawns)
	{
		columnIndex = 7 - columnIndex;
		rowIndex	= 7 - rowIndex;
		if(columnIndex - 1 > 0)
		{	
			byte leftB  = byteMap.get(columnIndex - 1);
			if((stationaryPawns[rowIndex] & leftB) != leftB)
				return false;
		}
		if(columnIndex + 1 < 8)
		{	
			byte rightB = byteMap.get(columnIndex + 1);
			if((stationaryPawns[rowIndex] & rightB) != rightB)
				return false;
		}	
		return true;
	}
	
	public static boolean isLegalMove(int fromRowIndex, int toRowIndex, Integer fromColumnIndex, Integer toColumnIndex, byte[] toBeMovedPawns, byte[] stationaryPawns, boolean enPassantVulnerable, byte enPassantColumn)
	{
		/* Forward Move */
		if(fromColumnIndex - toColumnIndex == 0)
		{	
			if(toRowIndex - fromRowIndex == 2) // do we have a double move ?
			{
				if(!isPawnAt(7 - toColumnIndex, 7 - toColumnIndex, stationaryPawns) && // is one of the opponent pawns blocking it ?
						!isPawnAt(toColumnIndex, toColumnIndex, toBeMovedPawns) && // is one of our pawns blocking it ?
						fromRowIndex == 1) // is the move legal ?
					return true;
				return false;
			}
			else if(toRowIndex - fromRowIndex == 1) // do we have a normal move ?
			{
				if(!isPawnAt(toRowIndex, toColumnIndex, toBeMovedPawns) &&
					!isPawnAt(7 - toRowIndex, 7 - toColumnIndex, stationaryPawns))
					return true;
				return false;
			}
			else
				return false; // we cannot move backwards
		}
		/* Attacking Move */
		else if(Math.abs(fromColumnIndex - toColumnIndex) == 1)// Attacking move
		{	
			if(Math.abs(fromRowIndex - toRowIndex) != 1) // cannot jump two rows
				return false;
			/* EnPassant Attack */
			if((byteMap.get(7 - toColumnIndex) & enPassantColumn) == enPassantColumn && // do we target the vulnerable column ?
					isPawnAt(7 - fromRowIndex, 7 - toColumnIndex, stationaryPawns) && // is his pawn in the vulnerable spot ?
					enPassantVulnerable) // was the move en passant ?
				return true;
			/* Left or Right Attack */
			if(isPawnAt(7 - toRowIndex, 7 - toColumnIndex, stationaryPawns))
				return true;
			return false;
		}
		else
			return false;// we cannot move on more than one column
	}
	
	public static boolean isPawnAt(Integer columnIndex, byte line)
	{
		byte b = byteMap.get(columnIndex);
		return ((line & b) == b) ? true : false;
	}
	
	public static boolean isPawnAt(int rowIndex, int columnIndex, byte[] toBeMovedPawns)
	{
		byte b = byteMap.get(columnIndex);
		return ((toBeMovedPawns[rowIndex] & b) == b) ? true : false;
	}
	
	public static boolean erasePawnAt(int rowIndex, int columnIndex, byte[] toBeMovedPawns)
	{
		byte b = byteMap.get(columnIndex);
		return ((toBeMovedPawns[rowIndex] & b) == b) ? true : false;
	}
	
	public static boolean isBackedLeft(int rowIndex, Integer columnIndex, byte[] toBeMovedPawns)
	{
		byte b = byteMap.get(columnIndex - 1);
		return ((toBeMovedPawns[rowIndex-1] & b) == b) ? true : false;
	}
	
	public static boolean isBackedRight(int rowIndex, Integer columnIndex, byte[] toBeMovedPawns)
	{
		byte b = byteMap.get(columnIndex + 1);
		return ((toBeMovedPawns[rowIndex-1] & b) == b) ? true : false;
	}

	public static int computeNumberOfPawns(byte[] pawns)
	{
		int pawnsNumber = 0;
		for (byte b : pawns)
		{
			pawnsNumber += byteTrueBitsMap.get(Byte.valueOf((byte) (b & 0xFF)));
		}
		return pawnsNumber;
	}
	
	static
	{
		byteTrueBitsMap = new HashMap<>();
		byteTrueBitsMap.put(Byte.valueOf((byte)-128), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)-127), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-126), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-125), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-124), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-123), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-122), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-121), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-120), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-119), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-118), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-117), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-116), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-115), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-114), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-113), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-112), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-111), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-110), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-109), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-108), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-107), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-106), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-105), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-104), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-103), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-102), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-101), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-100), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-99), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-98), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-97), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-96), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-95), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-94), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-93), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-92), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-91), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-90), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-89), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-88), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-87), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-86), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-85), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-84), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-83), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-82), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-81), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-80), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-79), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-78), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-77), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-76), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-75), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-74), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-73), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-72), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-71), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-70), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-69), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-68), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-67), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-66), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-65), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-64), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)-63), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-62), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-61), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-60), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-59), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-58), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-57), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-56), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-55), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-54), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-53), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-52), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-51), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-50), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-49), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-48), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-47), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-46), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-45), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-44), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-43), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-42), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-41), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-40), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-39), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-38), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-37), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-36), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-35), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-34), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-33), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-32), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)-31), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-30), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-29), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-28), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-27), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-26), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-25), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-24), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-23), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-22), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-21), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-20), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-19), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-18), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-17), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-16), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)-15), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-14), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-13), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-12), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-11), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-10), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-9), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-8), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)-7), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-6), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-5), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-4), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)-3), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-2), Integer.valueOf(7));
		byteTrueBitsMap.put(Byte.valueOf((byte)-1), Integer.valueOf(8));
		byteTrueBitsMap.put(Byte.valueOf((byte)0), Integer.valueOf(0));
		byteTrueBitsMap.put(Byte.valueOf((byte)1), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)2), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)3), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)4), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)5), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)6), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)7), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)8), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)9), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)10), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)11), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)12), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)13), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)14), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)15), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)16), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)17), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)18), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)19), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)20), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)21), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)22), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)23), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)24), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)25), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)26), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)27), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)28), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)29), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)30), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)31), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)32), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)33), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)34), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)35), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)36), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)37), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)38), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)39), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)40), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)41), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)42), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)43), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)44), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)45), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)46), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)47), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)48), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)49), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)50), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)51), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)52), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)53), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)54), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)55), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)56), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)57), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)58), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)59), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)60), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)61), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)62), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)63), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)64), Integer.valueOf(1));
		byteTrueBitsMap.put(Byte.valueOf((byte)65), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)66), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)67), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)68), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)69), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)70), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)71), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)72), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)73), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)74), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)75), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)76), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)77), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)78), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)79), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)80), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)81), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)82), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)83), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)84), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)85), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)86), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)87), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)88), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)89), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)90), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)91), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)92), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)93), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)94), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)95), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)96), Integer.valueOf(2));
		byteTrueBitsMap.put(Byte.valueOf((byte)97), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)98), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)99), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)100), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)101), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)102), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)103), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)104), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)105), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)106), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)107), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)108), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)109), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)110), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)111), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)112), Integer.valueOf(3));
		byteTrueBitsMap.put(Byte.valueOf((byte)113), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)114), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)115), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)116), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)117), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)118), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)119), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)120), Integer.valueOf(4));
		byteTrueBitsMap.put(Byte.valueOf((byte)121), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)122), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)123), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)124), Integer.valueOf(5));
		byteTrueBitsMap.put(Byte.valueOf((byte)125), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)126), Integer.valueOf(6));
		byteTrueBitsMap.put(Byte.valueOf((byte)127), Integer.valueOf(7));
	}
	
}
