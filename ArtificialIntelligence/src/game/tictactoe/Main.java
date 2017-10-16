package tictactoe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import tictactoe.model.*;

public class Main {
	
	public static void main(String[] args) {
		
		
		Board board;
		
		board = new Board(new int[][] {{-1, 1, 0},
									   {-1, 1, 1},
									   { 0,-1, 0}});
								
		board = new Board(new int[][] {{ 0, 0, 0},
									   { 0, 0, 0},
									   { 0, 0, 0}});

		State state = new State(Player.MAX, board);
		Game game   = new Game(state);
		Move move = null;
		int row, column;
		System.out.println(game.getState());
		
		while(!game.isOver()) {
			row    = Integer.valueOf(readConsole("row"));
			column = Integer.valueOf(readConsole("column"));
			game.setState(game.getState().result(new Move(row, column)));
			System.out.println(game.getState());
			
			if(game.isOver())
				break;
			
			move  = game.getBestMove();
			state = game.getState();
			state = state.result(move);
			game.setState(state);
			System.out.println(game.getState());
		}
		
		System.exit(0);
	}
	
	public static String readConsole(String outputMessage) {
		System.out.println(outputMessage);
		    
	   String result = null;
     
	   try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    result = bufferRead.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
       
       return result;
	}
	
}
