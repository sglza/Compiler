/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

public class Lexema 
{
	// Elements of a Lexema
	private String tokenId;
	// a lexema can have a reserved word
	private String reservedWord;
	// or can have a dfa
	private DFA dfa;
	/////////////////
	
	// constructor
	// if the lexema has a dfa
	public Lexema(String id, DFA aDFA)
	{
		tokenId = id;
		dfa = aDFA;
		reservedWord = null;
	}//end constructor
	
	
	// constructor
	// if the lexema has a reserved word
	public Lexema(String id, String aReservedWord)
	{
		tokenId = id;
		reservedWord = aReservedWord;
		dfa = null;
	}//end constructor

	
	// A method that determines
	// if a given string is this Lexema
	public boolean isThisLexema(String string)
	{
		boolean stringIsValid;
		///////////////
		stringIsValid = false;
		
		// If the lexema does not imply a reserved word
		if(reservedWord == null)
		{
			// we ask the DFA if the string is valid
			stringIsValid = dfa.isValid(string);
		}//end if
		else
		{
			// we check if the string
			// is the reserved word
			if(reservedWord.equals(string))
			{
				stringIsValid = true;
			}// end if
		}//end else
		return stringIsValid;
	}//end isThisLexema
	
	
	// Get the token of the lexema
	public String getToken()
	{
		return tokenId;
	}//end getToken


}//end class
