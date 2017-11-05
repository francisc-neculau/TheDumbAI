package game.chess.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import model.state.StateFileSerializer;

public class ChessStateFileSerializer implements StateFileSerializer<ChessState>
{

	@Override
	public void writeState(ChessState state, String filePath) throws IOException
	{
		File f = new File(filePath);

//		if(f.exists())
//			throw new IOException("File " + f.getName() + " allready exists"
//					+ " thus no writting will occur");
//		if(!f.createNewFile())
//			throw new IOException("File " + f.getName() + " could not be created");
					
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//		for (byte b : state.getWhitePawns())
//		{
//			bw.write(Byte.toString(b));
//			bw.newLine();
//		}
//		bw.newLine();
//		
//		for (byte b : state.getBlackPawns())
//		{
//			bw.write(Byte.toString(b));
//			bw.newLine();
//		}
		bw.write(Boolean.toString(state.isWhiteToMove()));
		bw.write(state.toString());
		bw.flush();
		bw.close();
	}

	@Override
	public ChessState readState(String filePath) throws IOException
	{
		File f = new File(filePath);
		if(!f.exists())
			throw new IOException("File " + f.getName() + " does not exist");
		
		BufferedReader br = new BufferedReader(new FileReader(f));
		boolean isWhiteMove = Boolean.parseBoolean(br.readLine());
		br.readLine(); // first line is not of interest
		
		byte[] whitePawns = new byte[8];
		byte[] blackPawns = new byte[8];
		int wp = 0;
		int bp = 0;
		
		String[] pieces;
		String line = "";
		for (int i = 0; i < 8; i++)
		{
			line = br.readLine();
			line = line.substring(2, 17);
			pieces = line.split(" ");

			wp = 0;
			bp = 0;
			for (int j = 0; j < 8; j++)
			{
				if(pieces[j].equals("w"))
					wp += Math.pow(2, (double)(7 - j));
				if(pieces[j].equals("b"))
					bp += Math.pow(2, j);
			}

			whitePawns[i] = (byte)wp;
			blackPawns[7 - i] = (byte)bp;
		}
		
//		for (int i = 0; i < 8; i++)
//			whitePawns[i] = Byte.parseByte(br.readLine());
//		br.readLine();
//		for (int i = 0; i < 8; i++)
//			blackPawns[i] = Byte.parseByte(br.readLine());
		
		br.close();
		return new ChessState(whitePawns, blackPawns, isWhiteMove);
	}

}
