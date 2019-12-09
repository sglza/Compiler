import java.util.LinkedList;

public class ProjectCompiler {

	/////////// Language Symbol Sets /////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	static char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	static char negative[] = { '-' };
	static char dot[] = { '.' };
	//////////////////////////////////////////////////////////////////////

	// Presents the source code
	private static void showSourceCode(String sourceCode) {
		if (sourceCode != null) {
			System.out.println("=====================================================");
			System.out.println("==================== SOURCE CODE ====================");
			System.out.println(sourceCode);
			System.out.println("=====================================================");
		} // end if
	}// end showSourceCode

	// Presents the sequence of tokens analyzed
	private static void showSequenceOfTokens(LinkedList<Token> tokens) {
		int i;
		///////

		if (tokens != null) {
			System.out.println("");
			System.out.println("");
			System.out.println("======================================");
			System.out.println("=============== TOKENS ===============");
			i = 0;
			while (i < tokens.size()) {
				System.out.println(i + ") " + tokens.get(i));
				i = i + 1;
			} // end while
			System.out.println("======================================");
		} // end if
	}// end showSequenceOfTokens

	// Reads the source code
	private static String loadSourceCode(String path, String fileName, String extension) {
		UdlapSequentialFile file;
		String sourceCode;
		String line;
		///////////////////
		sourceCode = "";
		file = new UdlapSequentialFile(path, fileName, extension);
		file.open();
		while (!file.eof) {
			line = file.readString();
			if (line != null)
				sourceCode = sourceCode + " \n" + line;
			// end if
		} // end while
			// then we remove the first 2 characters ( " \n" )
		sourceCode = sourceCode.substring(2, sourceCode.length());
		return sourceCode;
	}// end loadSourceCode

	///////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		// Our vocabulary is a set of Lexemas
		LinkedList<Lexema> vocabulary;
		DFA dfa;
		Lexema lexema;
		Transition transition;
		String sourceCode;
		LinkedList<Token> tokens;
		Rule rule;
		LinkedList<Rule> grammar;
		String objectiveCode;

		/////////////////////////////////

		// We obtain the source code C:\Users\Santiago\Documents
		// sourceCode = loadSourceCode("C:/Users/gerardoayala/Desktop/datos",
		// "miPrograma", "txt");
		sourceCode = loadSourceCode("C:\\Users\\Santiago\\Documents", "miPrograma", "txt");
		// We show the source code
		showSourceCode(sourceCode);
		// We construct the Vector of Lexemas,
		// which will be our vocabulary
		vocabulary = new LinkedList<Lexema>();

		/////////////////////////
		// GRAMMAR
		/////////////////////////
		// 0 F' -> F
		// 1 F -> math ( num , num )
		// 2 F -> math ( F , num )
		// 3 F -> math ( F , LF )
		// 4 F -> LF
		// 5 LF -> listMath ( L )
		// 6 L -> [ E ]
		// 7 E -> E , num
		// 8 E -> num
		//
		// math = { add | sub | mult | div }
		// listMath = { sum | max | min | avg }
		/////////////////////////

		// Include in the vocabulary
		// the operators and special symbols
		lexema = new Lexema("parentesisApertura", "(");
		vocabulary.add(lexema);
		lexema = new Lexema("parentesisCerradura", ")");
		vocabulary.add(lexema);
		lexema = new Lexema("corcheteApertura", "[");
		vocabulary.add(lexema);
		lexema = new Lexema("corcheteCerradura", "]");
		vocabulary.add(lexema);
		lexema = new Lexema("coma", ",");
		vocabulary.add(lexema);

		lexema = new Lexema("math", "add");
		vocabulary.add(lexema);
		lexema = new Lexema("math", "sub");
		vocabulary.add(lexema);
		lexema = new Lexema("math", "mult");
		vocabulary.add(lexema);
		lexema = new Lexema("math", "div");

		vocabulary.add(lexema);
		lexema = new Lexema("listMath", "sum");
		vocabulary.add(lexema);
		lexema = new Lexema("listMath", "max");
		vocabulary.add(lexema);
		lexema = new Lexema("listMath", "min");
		vocabulary.add(lexema);
		lexema = new Lexema("listMath", "avg");
		vocabulary.add(lexema);

		// Construct the DFA for recognizing a num
		dfa = new DFA();
		dfa.addFinalState(2);
		dfa.addFinalState(4);
		transition = new Transition(0, digit, 2);
		dfa.addTransition(transition);
		transition = new Transition(0, negative, 1);
		dfa.addTransition(transition);

		transition = new Transition(1, digit, 2);
		dfa.addTransition(transition);

		transition = new Transition(2, digit, 2);
		dfa.addTransition(transition);
		transition = new Transition(2, dot, 3);
		dfa.addTransition(transition);

		transition = new Transition(3, digit, 4);
		dfa.addTransition(transition);

		transition = new Transition(4, digit, 4);
		dfa.addTransition(transition);

		lexema = new Lexema("num", dfa);
		vocabulary.add(lexema);
		/////////////////////////////////////////////////////

		// We set the vocabulary
		Lexer.setVocabulary(vocabulary);
		// We set the separation symbols
		Lexer.setSeparationSymbols(" " + "\n");
		// We make the lexical analysis
		// and obtain the tokens
		tokens = Lexer.lexicalAnalysis(sourceCode);

		/////////////////////////
		// GRAMMAR
		/////////////////////////
		// 0 F' -> F
		// 1 F -> math ( num , num )
		// 2 F -> math ( F , num )
		// 3 F -> math ( F , LF )
		// 4 F -> LF
		// 5 LF -> listMath ( [ L ] )
		// 6 L -> [ E ]
		// 7 E -> E , num
		// 8 E -> num
		//
		// math = { add | sub | mult | div }
		// listMath = { sum | max | min | avg }
		/////////////////////////

		if (tokens != null) {
			// We show the tokens
			showSequenceOfTokens(tokens);

			//////////////////// PARSER /////////////////
			// Define the grammar
			grammar = new LinkedList<Rule>();

			// Rule 0
			rule = new Rule(0, "Fprima");
			rule.addSymbol("F");
			grammar.add(rule);

			// Rule 1
			rule = new Rule(1, "F");
			rule.addSymbol("math");
			rule.addSymbol("parentesisApertura");
			rule.addSymbol("num");
			rule.addSymbol("coma");
			rule.addSymbol("num");
			rule.addSymbol("parentesisCerradura");
			grammar.add(rule);

			// Rule 2
			rule = new Rule(2, "F");
			rule.addSymbol("math");
			rule.addSymbol("parentesisApertura");
			rule.addSymbol("F");
			rule.addSymbol("coma");
			rule.addSymbol("num");
			rule.addSymbol("parentesisCerradura");
			grammar.add(rule);

			// Rule 3
			rule = new Rule(3, "F");
			rule.addSymbol("math");
			rule.addSymbol("parentesisApertura");
			rule.addSymbol("F");
			rule.addSymbol("coma");
			rule.addSymbol("LF");
			rule.addSymbol("parentesisCerradura");
			grammar.add(rule);

			// Rule 4
			rule = new Rule(4, "F");
			rule.addSymbol("LF");
			grammar.add(rule);

			// Rule 5
			rule = new Rule(5, "LF");
			rule.addSymbol("listMath");
			rule.addSymbol("parentesisApertura");
			rule.addSymbol("corcheteApertura");
			rule.addSymbol("E");
			rule.addSymbol("corcheteCerradura");
			rule.addSymbol("parentesisCerradura");
			grammar.add(rule);

			// Rule 6
			rule = new Rule(6, "E");
			rule.addSymbol("E");
			rule.addSymbol("coma");
			rule.addSymbol("num");
			grammar.add(rule);

			// Rule 7
			rule = new Rule(7, "E");
			rule.addSymbol("num");
			grammar.add(rule);

			// Set the grammar
			Parser.setGrammar(grammar);

			// Define the Parser Table
			//////////////////////////

			// state 0
			ParserTable.addAction(0, "math", "S4");
			ParserTable.addAction(0, "listMath", "S5");
			ParserTable.addAction(0, "num", "S6");
			ParserTable.addAction(0, "F", "1");
			ParserTable.addAction(0, "LF", "2");
			ParserTable.addAction(0, "E", "3");

			// state 1
			ParserTable.addAction(1, "$", "OK");

			// state 2
			ParserTable.addAction(2, "coma", "R4");
			ParserTable.addAction(2, "$", "R4");

			// state 3
			ParserTable.addAction(3, "coma", "S7");

			// state 4
			ParserTable.addAction(4, "parentesisApertura", "S8");

			// state 5
			ParserTable.addAction(5, "parentesisApertura", "S9");

			// state 6
			ParserTable.addAction(6, "corcheteCerradura", "R7");
			ParserTable.addAction(6, "coma", "R7");

			// state 7
			ParserTable.addAction(7, "num", "S10");

			// state 8
			ParserTable.addAction(8, "math", "S4");
			ParserTable.addAction(8, "listMath", "S5");
			ParserTable.addAction(8, "num", "S11");
			ParserTable.addAction(8, "F", "12");
			ParserTable.addAction(8, "LF", "2");

			// state 9
			ParserTable.addAction(9, "corcheteApertura", "S13");

			// state 10
			ParserTable.addAction(10, "corcheteCerradura", "R6");
			ParserTable.addAction(10, "coma", "R6");

			// state 11
			ParserTable.addAction(11, "coma", "S14");

			// state 12
			ParserTable.addAction(12, "coma", "S15");

			// state 13
			ParserTable.addAction(13, "num", "S6");
			ParserTable.addAction(13, "E", "16");

			// state 14
			ParserTable.addAction(14, "num", "S17");

			// state 15
			ParserTable.addAction(15, "listMath", "S5");
			ParserTable.addAction(15, "num", "S18");
			ParserTable.addAction(15, "LF", "19");

			// state 16
			ParserTable.addAction(16, "corcheteCerradura", "S20");
			ParserTable.addAction(16, "coma", "S7");

			// state 17
			ParserTable.addAction(17, "parentesisCerradura", "S21");

			// state 18
			ParserTable.addAction(18, "parentesisCerradura", "S22");

			// state 19
			ParserTable.addAction(19, "parentesisCerradura", "S23");

			// state 20
			ParserTable.addAction(20, "parentesisCerradura", "S24");

			// state 21
			ParserTable.addAction(21, "coma", "R1");
			ParserTable.addAction(21, "$", "R1");

			// state 22
			ParserTable.addAction(22, "coma", "R2");
			ParserTable.addAction(22, "$", "R2");

			// state 23
			ParserTable.addAction(23, "coma", "R3");
			ParserTable.addAction(23, "$", "R3");

			// state 24
			ParserTable.addAction(24, "parentesisCerradura", "R5");
			ParserTable.addAction(24, "coma", "R5");
			ParserTable.addAction(24, "$", "R5");

			// Set error messages
			Parser.setErrorMessage("E1", "Se espera un operando.");
			Parser.setErrorMessage("E2", "No hay código fuente.");
			Parser.setErrorMessage("E3", "ERROR");
			Parser.setErrorMessage("E4", "ERROR");
			Parser.setErrorMessage("E5", "Sobra un operando. Favor de quitarlo.");
			Parser.setErrorMessage("E6", "Se espera un paréntesis de cerradura.");
			Parser.setErrorMessage("E7", "Se espera un paréntesis de apertura.");

			//////////////////////////////////////////////////////////////////////
			// PARSING
			System.out.println("");
			System.out.println("");
			System.out.println("//============================================");
			System.out.println("//============= COMPILING..... ===============");

			Parser.parsing(tokens);

			System.out.println("//============================================");

			//////////////////////////////////////////////////////////////////////
			// CODE GENERATION

			if (!Parser.weHaveAnError) {
				// Get objective code
				objectiveCode = CodeGenerator.getObjectiveCode();
				// show objective code
				System.out.println("");
				System.out.println("");
				System.out.println("//============================================");
				System.out.println("//============= OBJECTIVE CODE ===============");
				System.out.println(objectiveCode);
				System.out.println("//============================================");

			} // end if
			else
				System.out.println("WE HAVE SYNTAX ERRORS.");
		} // end else

	}// end main

}// end class
