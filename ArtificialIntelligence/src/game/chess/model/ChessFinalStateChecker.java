package game.chess.model;

import model.state.FinalStateChecker;

public class ChessFinalStateChecker implements FinalStateChecker<ChessState>
{

	@Override
	public boolean isFinal(ChessState state)
	{
		ChessStateTransitionDetails stateDetails = state.getTransitionDetails();
		// 1. Check if the last moved pawn is on the last row
		if(stateDetails.getRowIndex() == 7)
			return true;
		
		// 2. Check if any of the players has no pawns left on the board
		if(state.isWhiteToMove() ? existPawnsIn(state.getWhitePawns()) : existPawnsIn(state.getBlackPawns()))
			return true;
		
		// 3. Check if the last moved pawn has clear line till the end of the board
		int lastMoveRowIndex = stateDetails.getRowIndex();
		int lastMoveColumnIndex = stateDetails.getColumnIndex();
		
		for(int index = lastMoveRowIndex; index <= 7; index++)
		{
			if(!ChessStateTransitioner.isFreeUnatackableAt(lastMoveRowIndex, lastMoveColumnIndex, state.isWhiteToMove() ? state.getWhitePawns() : state.getBlackPawns()))
				return false;
		}
		
		return true;
	}

	private boolean existPawnsIn(byte[] pawns) 
	{
		for (byte boardLine : pawns) 
		{
			if(boardLine != 0)
				return false;
		}
		
		return true;
	}

}
