/**
 * This class is a simple IO, text file reader and writer.
 * @author Mrs. T (altered & edited by Christopher Zhu)
 * @since June 3, 2014
 */

import java.io.*;
public class FileAccessor
{
	private static PrintWriter fileOut; // define a class variable that is
										// accessible from all methods

	public static void createOutputFile(String fileName, boolean addOnEnd) //file writer/creator
	{											// if false, rewrites over that text file
		try
		{	
			fileOut = new PrintWriter(new BufferedWriter(new FileWriter(fileName, addOnEnd)));
		}catch (IOException e)
		{
			System.out.println("Cannot create/open file: " + fileName + ".");
		}
	}

	public static void write(String text)
	{	// writes the text string into the text file
		fileOut.println(text);
	}

	public static void closeOutputFile() // must be called to save changes to file
	{
		fileOut.close();
	}

	private static BufferedReader fileIn;
	
	public static void openInputFile(String fileName) // file reader/accessor
	{
		try
		{
			fileIn = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e)
		{
			System.out.println("Cannot open " + fileName + ".");
		}
	}

	public static String readLine()
	{
		try
		{
			return fileIn.readLine();
		} catch (IOException e)
		{
			return null;
		}
	}

	public static void closeInputFile()
	{
		try
		{
			fileIn.close();
		}catch(IOException e)
		{
			System.out.println("Cannot close the file.");
		}
	}
} // end class