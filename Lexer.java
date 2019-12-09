/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

import java.util.LinkedList;
import java.util.StringTokenizer;

public abstract class Lexer {
	// Elements of the LEXER:
	// The vocabulary (set of lexemas)
	static LinkedList<Lexema> vocabulary;
	// the valid separators
	static String separationSymbols;
	// a list of lexical errors to report
	static LinkedList<String> errors;
	///////////////////////

	// set the vocabulary
	public static void setVocabulary(LinkedList<Lexema> theVocabulary) {
		vocabulary = theVocabulary;
	}// end setVocabulary

	// set the separators
	public static void setSeparationSymbols(String theSeparationSymbols) {
		separationSymbols = theSeparationSymbols;
	}// end setSeparationSymbols

	// gets the lexema of a given string
	private static Lexema getLexema(String string) {
		Lexema lexema;
		int i;
		boolean theStringCorresponds;
		////////////////
		lexema = null;
		theStringCorresponds = false;
		i = 0;
		while ((i < vocabulary.size()) && (!theStringCorresponds)) {
			if (vocabulary.get(i).isThisLexema(string)) {
				theStringCorresponds = true;
				lexema = vocabulary.get(i);
			} // end if
			i = i + 1;
		} // end while
		return lexema;
	}// end getLexema

	// Show the lexical errors
	private static void showLexicalErrors() {
		int i;
		///////
		if (errors != null) {
			System.out.println("--- ERRORES LEXICOS ---");
			i = 0;
			while (i < errors.size()) {
				System.out.print("(-_-) Error # " + (i + 1) + " : ");
				System.out.println(errors.get(i));
				i = i + 1;
			} // end while
		} // end if
	}// end showLexicalErrors

	// Makes the lexical analysis
	// get the sequence of tokens from the source code
	public static LinkedList<Token> lexicalAnalysis(String sourceCode) {
		LinkedList<Token> tokens;
		boolean weHaveALexicalError;
		StringTokenizer tokenizer;
		String string;
		Lexema lexema;
		Token token;
		/////////////////////////

		weHaveALexicalError = false;
		tokens = new LinkedList<Token>();
		// Define the StringTokenizer with the separators
		tokenizer = new StringTokenizer(sourceCode, separationSymbols, true);
		while (tokenizer.hasMoreTokens()) {
			lexema = null;
			// we get one by one the words from the source code
			string = tokenizer.nextToken();
			// if the word is not a blank string
			// we analyze it
			if (!string.equals(" ") && !string.equals("\n")) {
				// we get the lexema of the word
				lexema = getLexema(string);
				// If there is a lexema
				if (lexema != null) {
					// we include the token of the lexema in our tokens
					token = new Token(lexema.getToken(), string);
					tokens.add(token);
				} // end if
					// otherwise, there is a lexical error
				else {
					weHaveALexicalError = true;
					// If we do not have an error list
					// we construct it
					if (errors == null)
						errors = new LinkedList<String>();
					// end if
					// if the word has more than 1 character
					if (string.length() != 1) {
						errors.add("'" + string + "'" + " no es una palabra válida en el lenguaje.");
					} // end if
					else {
						// the word is one character or symbol
						errors.add("'" + string + "'" + " no es un símbolo válido en el lenguaje.");
					} // end else
				} // end else
			} // end if
		} // end while

		token = new Token("$", "$");
		tokens.add(token);
		// If there are no lexical errors,
		// we provide a token list
		if (!weHaveALexicalError)
			return tokens;
		// end if
		else {
			// otherwise, we show the lexical errors
			// and return null
			showLexicalErrors();
			return null;
		} // end else
	}// end analisisLexico

}// end LEXER
