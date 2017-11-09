package game.chess;

import java.io.IOException;

import game.chess.model.ChessState;
import game.chess.model.ChessStateFileSerializer;
import game.chess.model.ChessStateTransitioner;

public class MainTest
{

	public static void main(String[] args) throws IOException
	{
		
		int [] pawnValuetable = new int[] {
				0,  0,  0,  0,  0,  0,  0,  0,
			     0,  0,  0,  0,  0,  0,  0,  0,
			    0,  0,  0,  0,  0,  0,  0,  0,
			    0,  0,  0,  0,  0,  0,  0,  0,
			     0,  0,  0,  0,  0,  0,  0,  0,
			     0,  0,  0,  0,  0,  0,  0,  0,
			     0,  0,  0,  0,  0,  0,  0,  0,
			     0,  0,  0,  0,  0,  0,  0,  0,
			};
		
		
		for (int i = -127; i < 128; i++)
		{
			System.out.println("byteTrueBitsMap.put(Byte.valueOf((byte)" + (byte)i + "), Integer.valueOf(" + String.format("%8s", Integer.toBinaryString(i & 0xFF)
					).replace("0", "").replaceAll(" ", "").length() + "));");
		}
		
//		  byte[] whitePawns = {0, -1, 0, 0, 0, 0, 0, 0}; byte[] blackPawns = {0, -1, 0, 0, 0, 0, 0, 0}; 
//		  ChessState state = new ChessState(whitePawns, blackPawns, false);
//		  
////		  int row = 0; int column = 0; boolean b = isFreeAt(row, column,state.getWhitePawns(), state.getBlackPawns()); //
////		  System.out.println(b); // System.out.println(isPawnAt(0, blackPawns[1])); //
////		  System.out.println(state);
//		  
		  //System.out.println((byte)Math.pow(2, 7));
		  ChessStateTransitioner stateTransitioner = new ChessStateTransitioner();
//		  
//		  
		  String filePath;
		  ChessStateFileSerializer serializer = new ChessStateFileSerializer(); 
//		  
		  filePath = "resources//chess//enpassat_attack.txt";
		  ChessState state = serializer.readState(filePath);
		  System.out.println(state);
		  
//		  serializer.writeState(state, filePath);
		  
//		  
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
		  
		  
		  for (ChessState s :
		  stateTransitioner.generateAllNextLegalStates(serializer.readState(filePath)))
		  { System.out.println(s); }
		
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
