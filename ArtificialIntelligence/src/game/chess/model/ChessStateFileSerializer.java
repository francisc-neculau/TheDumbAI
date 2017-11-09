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
	public static final String resourcesDirectoryPath = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "chess";
	private static final String standardStartStateFileName = "standard_start.txt";
	
	@Override
	public void writeState(ChessState state, String filePath) throws IOException
	{
		File f = new File(filePath);

		BufferedWriter bw = new BufferedWriter(new FileWriter(f));

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
		boolean enPassantVulnerable = Boolean.parseBoolean(br.readLine());// first line is not of interest
		byte enPassantColumn = Byte.parseByte(br.readLine());
		
		br.readLine();
		
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
		
		br.close();
		return new ChessState(whitePawns, blackPawns, isWhiteMove, enPassantVulnerable, enPassantColumn);
	}

	public static ChessState loadDefaultState()
	{
		// FIXME : This stuff is very messy. Maybe a Helper class after all ?
		ChessStateFileSerializer csfs = new ChessStateFileSerializer();
		try
		{
			return csfs.readState(csfs.resourcesDirectoryPath + File.separator + csfs.standardStartStateFileName);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
