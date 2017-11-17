package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileParser
{

	public static void read()
	{
		try
		{
			File f = new File("resources//cobweb//RO.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			while((line = br.readLine()) != null)
				System.out.println(line.replaceAll("\t", ";"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
