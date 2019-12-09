/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class UdlapSequentialFile 
{
	// representaci�n abstracta del archivo
	private File file;
	// lector de archivo
	private FileReader fileReader;
	// Buffer de lectura
	private BufferedReader bufferedReader;
	// flujo de entrada
	private FileInputStream inputStream;
	// flujo de salida
	private FileOutputStream outputStream;
	
	// partes del nombre del archivo
	private String absoluteName;

	/** End of File. */
	public boolean eof;
	/** Number of Lines. */
	private  int numberOfLines;
	
	private int currentLine;
	private char newLine;
	private char returnCharacter;
	private boolean firstLineHasBeenWritten;

	/** Creates the File object from a given physical file location. 
	 * The file must be a simple text file MS-DOS codified.
	 * @param aPath of the sequential text file location.
	 * @param aFileName of the sequential text file.
	 * @param aSuffix of the sequential text file.
	 */
	public UdlapSequentialFile(String aPath,String aFileName, String aSuffix)
	{
		absoluteName = aPath + "/" + aFileName + "." + aSuffix;
		file = new File(absoluteName);
		newLine  = '\n';
		returnCharacter = '\r';
		eof=false;
		firstLineHasBeenWritten = false;
		currentLine = 0;
	}//end Constructor ArchivoSecuencial

	/** Opens an existing sequential text file. */
	public void open()
	{
		String line;
		//-----------
		try
		{
			inputStream = new FileInputStream(file);
			fileReader = new FileReader(absoluteName);
			bufferedReader = new BufferedReader(fileReader);
			numberOfLines = 0;
	        while(!eof)
	        { 
	        		line = readALine();
	        		numberOfLines = numberOfLines + 1;
	         }//end while
	        try
	        {
	            fileReader = new FileReader(absoluteName);
	            bufferedReader = new BufferedReader(fileReader);

	            eof=false;
	        }//end try
	        catch(FileNotFoundException excepcion)
	        {
	            System.out.println(excepcion);
	        }//end catch
		}//end try
		catch(FileNotFoundException excepcion)
		{
			System.out.println("No se encontr� el archivo!!!");
		}//end catch
		eof=false;
	}//end open



	/** Constructs a new sequential text file and opens it. */
	public void create()
	{
		numberOfLines = 0;
		try
		{
			file.createNewFile();
			outputStream = new FileOutputStream(file);
		}//end try
		catch(IOException excepcion)
		{
			System.out.println(excepcion);
		}//end catch 
	}//end create

	
	/** Indicates if End Of File has been reached.
	 * 
	 * @return true if End Of File has been reached, false otherwise.
	 */
	public boolean eof()
	{
		return eof;
	}//end eof

	
	/** Provides the number of lines of the sequential text file.
	 * 
	 * @return an int which value is the number of lines of the sequential text file.
	 */
	public int getNumberOfLines()
	{
		return numberOfLines;
	}//end getNumberOfLines

	
	
	private String readALine()
	{                 
		String line;
		char character;
		int integer;
		//--------------
 
		// inicia valores de la l�nea a regresar
		line = "ni";
		character = 'l';
		// linea + caracter == "nil" como valor inicial
		do
		{
			try
			{
				if (character != returnCharacter)
					line = line + character;
				//end if
				character = (char) bufferedReader.read();
				integer = (int) character;       
				if (integer == 65535)
					eof = true;
				//end if
			}//end try
			catch(Exception excepcion)
			{
				eof = true;
			}//end catch
		}//end do
		while ((character!=newLine) && (eof == false));

		currentLine = currentLine + 1;
		if ((line.length() >= 4))
			// no considera la cadena "nil" (posicion de 0 a 2)
			line = line.substring(3);
		//end if
		else
		{
			line = null;
		}//end else
		return line;
	}//end readALine


	
	/** Reads the next line of the sequential file and interprets it as a String value.
	 * @return a String object read from the sequential text file. 
	 * In case of reading garbagge it returns null.
	 * */
	public String readString()
	{                 
		return readALine();
	}//end readString

	
	/** Reads the next line of the sequential file and interprets it as an int value.
	 * @return an int value read from the sequential text file. 
	 * */
	public int readInt()
	{                 
		int integer;
		String line;
		int lineLength;
		char lastCharacter;
		Integer integerObject;
		//-----------
		integer = 0;
		line = readALine();
		if (line != null)
		{
			lineLength = line.length();
			lastCharacter = line.charAt(lineLength-1);
			if (lastCharacter == '\r')      
				line = line.substring(0,lineLength-1);
			//endif
			
			System.out.println("zzzzzzzzzzzzzzzzzz: " + line);
			
			integerObject = new Integer(line);
			integer = integerObject.intValue();
		}//end if
		return integer;
	}//end readInt


	/** Reads the next line of the sequential file and interprets it as a double value.
	 * @return a double value read from the sequential text file. 
	 * */
	public double readDouble()
	{                 
		double doubleValue;
		String line;
		//------------
		doubleValue = 0.0;
		line = readALine();
		if (line != null)
			doubleValue = Double.parseDouble(line);
		//end if
		return doubleValue;
	}//end readDouble


	/** Reads the next line of the sequential file and interprets it as a char value.
	 * @return a char value read from the sequential text file. 
	 * */
	public char readChar()
	{                 
		char character;
		String line;
		//-------------
		character = ' ';
		line = readALine();
		if (line != null)
			character= line.charAt(0);
		//end if
		return character;
	}//end readChar


	/** Reads the next line of the sequential file and interprets it as a boolean value. 
	 * @return a boolean value read from the sequential text file. 
	 * */
	public boolean readBoolean()
	{                 
		boolean booleanValue;
		String line;
		int lineLength;
		char lastCharacter;
		Boolean booleanObject;
		//-------------
		line = readALine();
		if (!line.equals("true") && !line.equals("false"))
		{
			booleanValue=false;
			try
			{
				throw new IOException("----> Can not read a boolean at line " + 
			                          currentLine + " of file " +
			                          absoluteName);
			}//end try
			catch(Exception e)
			{
				System.out.println(e);
			}//end catch
		}//end if
		else
		{
			lineLength = line.length();
			lastCharacter = line.charAt(lineLength-1);
			if (lastCharacter == '\r') 
			{
				line = line.substring(0,lineLength-1);
			}//end if
			booleanObject = Boolean.valueOf(line);
			booleanValue = booleanObject.booleanValue();
		}//end else
		return booleanValue;
}//end readBoolean


////////////METODOS DE ESCRITURA //////////////////////

	private void writeLine(String cadena)
	{
		// se escribir� un arreglo de BYTES
		byte outputArray[];
		int i;
		//--------------------
		// se obtienen los bytes de la cadena (String)
		outputArray = cadena.getBytes();           
		// se escribe el arreglo, byte por byte 
		i = 0;
		while (i < outputArray.length)
		{
			try
			{
				// escribimos el i�simo byte
				outputStream.write(outputArray[i]);
				numberOfLines = numberOfLines + 1;
			}//end try
			catch(Exception excepcion)
			{
				System.out.println(excepcion);
			}//end catch
			i = i + 1;
		}//end while
	}//end writeLine


	/** Writes a String as a new line into the sequential text file.
	 * 
	 * @param aString which contents will be written.
	 */
	public void writeString(String aString)
	{
		if (firstLineHasBeenWritten)
			aString = newLine+aString;
		//end if
		else
			firstLineHasBeenWritten = true;
		//end else
		writeLine(aString);
		eof = false;
	}//end writeString


	/** Writes a int value as a new line into the sequential file.
	 * 
	 * @param anInt which value will be written.
	 */
	public void writeInt(int anInt)
	{
		String line;
		//------------
		// se obtiene el String del int
		line = Integer.toString(anInt);
		writeLine(line);
		eof = false;
	}//end writeInt

	
	/** Writes a double value as a new line into the sequential file.
	 * 
	 * @param aDouble which value will be written.
	 */
	public void writeDouble(double aDouble)
	{
 		String line;
 		//-------------
 		// se obtiene el double del String
 		line = Double.toString(aDouble);
 		line = line + newLine;
 		writeLine(line);
 		eof = false;
	}//end writeDouble

	/** Writes a char value as a new line into the sequential file.
	 * 
	 * @param aChar which value will be written.
	 */
	public void writeChar(char aChar)
	{                 
		String line;
		//-----------
		// se inicia la cadena vac�a
		line = "";
		// se hace la cadena con el caracter
		line = line + aChar + newLine;
		writeLine(line);
		eof = false;
	}//end writeChar

	/** Writes a boolean value as a new line into the sequential file.
	 * 
	 * @param aBoolean which value will be written.
	 */
	public void writeBoolean(boolean aBoolean)
	{                 
		String line;
		//------------

		// se obtiene el boolean del String
		line = Boolean.toString(aBoolean);
		line = line + newLine;
		writeLine(line);
		eof = false;
	}//end writeBoolean


}//end class UdlapSequentialFile