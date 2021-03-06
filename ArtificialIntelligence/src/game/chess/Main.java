package game.chess;

import java.io.IOException;
import java.security.SecureRandom;

import game.chess.model.ChessFinalStateChecker;
import game.chess.model.ChessState;
import game.chess.model.ChessStateFileSerializer;
import game.chess.model.ChessStateTransitioner;
import game.chess.strategy.ChessDefensiveHeuristics;
import game.chess.strategy.ChessOffensiveHeuristics;
import model.minmax.MinMaxTree;
import model.state.FinalStateChecker;

public class Main
{

	public static void main(String[] args) throws Exception
	{
//		  byte[] whitePawns = {0, -1, 0, 0, 0, 0, 0, 0}; byte[] blackPawns = {0, -1, 0, 0, 0, 0, 0, 0}; 
//		  ChessState state = new ChessState(whitePawns, blackPawns, false);
//		  
////		  int row = 0; int column = 0; boolean b = isFreeAt(row, column,state.getWhitePawns(), state.getBlackPawns()); //
////		  System.out.println(b); // System.out.println(isPawnAt(0, blackPawns[1])); //
////		  System.out.println(state);
//		  
//		  
//		  ChessStateTransitioner stateTransitioner = new ChessStateTransitioner();
//		  
//		  

		FinalStateChecker<ChessState> finalStateChecker = new ChessFinalStateChecker();
		SecureRandom random = new SecureRandom();
		String filePath;
		ChessStateFileSerializer serializer = new ChessStateFileSerializer(); 
//		  
		  filePath = "resources//chess//w_move_case_attack_pawn.txt";
//		  filePath = "resources//chess//standard_start.txt";
		  ChessState state = serializer.readState(filePath);
		  System.out.println(state);
		  
		  MinMaxTree<ChessState> tree = new MinMaxTree<>(4);
		  tree.setTransitioner(new ChessStateTransitioner());
		  if(random.nextDouble() > 0.5)
			  tree.setEvaluationFunction(new ChessOffensiveHeuristics());
		  else
			  tree.setEvaluationFunction(new ChessDefensiveHeuristics());
		  
//		  tree.setEvaluationFunction(new ChessOffensiveHeuristics());
		  
//		  if(finalStateChecker.isFinal(state))
//			  System.out.println("YES!!!");
		  
		  ChessState nextState = tree.nextState(state, MinMaxTree.MAX_PLAYER);
		  while(nextState != null)
		  {
			  System.out.println(nextState);
			  if(finalStateChecker.isFinal(nextState))
			  {
				  if(nextState.isWhiteToMove())
					  System.out.println("Winner is BLACK!");
				  else
					  System.out.println("Winner is WHITE!");
				  break;
			  }
			  
			  // Set randomly the strategy of the next move
			  if(random.nextDouble() > 0.5)
				  tree.setEvaluationFunction(new ChessOffensiveHeuristics());
			  else
				  tree.setEvaluationFunction(new ChessDefensiveHeuristics());
			  
//			  tree.setEvaluationFunction(new ChessOffensiveHeuristics());
			  nextState = tree.nextState(nextState, MinMaxTree.MAX_PLAYER);
			  if(nextState == null)
				  System.out.println("Remiza!");
		  }
		  
		  
		  
//		  filePath = "resources//chess//b_advantage_case_en_passant.txt";
////		  System.out.println(serializer.readState(filePath));
//		  
//		  filePath = "resources//chess//w_move_case_attack_pawn.txt";
//		  System.out.println(serializer.readState(filePath));
//		  
////		  System.out.println();System.out.println();System.out.println();
//		  
//		  System.out.println(ChessStateTransitioner.isPositionAtackedAt(3, 3, serializer.readState(filePath).getWhitePawns()));
//		  System.out.println(ChessStateTransitioner.isPositionBlockedAt(3, 3, serializer.readState(filePath).getWhitePawns()));
		  
		  
//		  for (ChessState s :
//		  stateTransitioner.generateAllNextLegalStates(serializer.readState(filePath)))
//		  { System.out.println(s); }
		
		/*
		 * byte[] whitePawns = {0, -1, 0, 0, 0, 0, 0, 0}; byte[] blackPawns = {0, -1, 0,
		 * 0, 0, 0, 0, 0}; ChessState state = new ChessState(whitePawns, blackPawns,
		 * false);
		 * 
		 * int row = 0; int column = 0; boolean b = isFreeAt(row,
		 * column,state.getWhitePawns(), state.getBlackPawns()); //
		 * System.out.println(b); // System.out.println(isPawnAt(0, blackPawns[1])); //
		 * System.out.println(state);
		 * 
		 * 
		 * ChessStateTransitioner stateTransitioner = new ChessStateTransitioner();
		 * 
		 * 
		 * String filePath; ChessStateFileSerializer serializer = new
		 * ChessStateFileSerializer(); // // filePath =
		 * "resources//chess//standard_start.txt"; //
		 * System.out.println(serializer.readState(filePath)); // // filePath =
		 * "resources//chess//b_advantage_case_en_passant.txt"; //
		 * System.out.println(serializer.readState(filePath)); // filePath =
		 * "resources//chess//w_move_case_attack_pawn.txt"; //
		 * System.out.println(serializer.readState(filePath));
		 * System.out.println(serializer.readState(filePath));
		 * System.out.println();System.out.println();System.out.println();
		 * 
		 * for (ChessState s :
		 * stateTransitioner.generateAllNextLegalStates(serializer.readState(filePath)))
		 * { System.out.println(s); }
		 */
	}

}
