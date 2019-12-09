
import java.util.LinkedList;
import java.util.Stack;

public class Parser {
	static Stack<Integer> statesStack;
	static Stack<Token> symbolsStack;
	static LinkedList<Token> tokens;
	static LinkedList<ErrorMessage> errorMessages = new LinkedList<ErrorMessage>();
	static LinkedList<Rule> grammar;
	static int pointer;
	public static boolean weHaveAnError = false;

	public static void setGrammar(LinkedList<Rule> aGrammar) {
		grammar = aGrammar;
	}// end setGrammar

	public static void setErrorMessage(String errorId, String aMessage) {
		ErrorMessage errorMessage;
		//
		errorMessage = new ErrorMessage(errorId, aMessage);
		errorMessages.add(errorMessage);
	}// end setErrorMessage

	public static void parsing(LinkedList theTokens) {
		/// initialization
		tokens = theTokens;
		statesStack = new Stack();
		symbolsStack = new Stack<Token>();
		statesStack.push(new Integer(0));
		weHaveAnError = false;
		////////////////////////////
		syntaxAnalisys();
	}// end parsing

	private static void syntaxAnalisys() {
		int currentState;
		String currentSymbol;
		String action;
		String actionType;
		String actionID;
		Integer actionIdInteger;
		int actionNumber;
		boolean finished;
		//////////////////

		pointer = 0;
		currentState = statesStack.peek().intValue();
		currentSymbol = tokens.get(pointer).getTokenId();
		action = ParserTable.getNextAccion(currentState, currentSymbol);
		finished = false;
		while ((!weHaveAnError) && (!finished)) {
			// If we have the OK, we reduce with the rule number CERO
			if (action.equals("OK")) {
				System.out.print("DONE");
				reduce(0);
				finished = true;
			} // end if
			else {
				actionType = action.substring(0, 1);
				actionID = action.substring(1, action.length());
				actionIdInteger = new Integer(actionID);
				actionNumber = actionIdInteger.intValue();

				if (actionType.equals("S")) {
					System.out.println(action);
					shift(actionNumber);
				} // end if
				else if (actionType.equals("R")) {
					System.out.println(action);
					reduce(actionNumber);
				} // end if
				else if (actionType.equals("E")) {
					error(actionNumber);
					weHaveAnError = true;
				} // end if
					// end else
					// end else
				currentState = statesStack.peek().intValue();

				currentSymbol = tokens.get(pointer).getTokenId();
				action = ParserTable.getNextAccion(currentState, currentSymbol);
				if (action == null) {
					weHaveAnError = true;
					break;
				} // end if
			} // end else
		} // end while

		if (!weHaveAnError)
			System.out.println("PARSING COMPLETE!              (-v-)/");
		// end if
	}// end syntaxAnalisys

	private static void shift(int state) {
		Token symbol;
		///////////////
		statesStack.push(new Integer(state));
		symbol = tokens.get(pointer);
		symbolsStack.push(symbol);
		pointer = pointer + 1;
	}// end shift

	private static void reduce(int ruleNumber) {
		int i;
		int numberOfSymbols;
		Rule rule;
		Token symbol;
		String symbolId;
		int stateNumber;
		Integer state;
		Integer nextState;
		String action;
		///////////////
		if (ruleNumber == 0) {
			CodeGenerator.translation(0, symbolsStack);
		} // end if
		else {
			action = "";
			rule = grammar.get(ruleNumber);
			numberOfSymbols = rule.getNumberOfSymbols();
			CodeGenerator.translation(ruleNumber, symbolsStack);
			i = 0;
			while (i < numberOfSymbols) {
				statesStack.pop();
				symbolsStack.pop();
				i = i + 1;
			} // end while
			symbolId = rule.getNonTerminalSymbol();
			symbol = new Token(symbolId, symbolId);
			symbolsStack.push(symbol);
			state = (Integer) statesStack.peek();
			stateNumber = state.intValue();
			action = ParserTable.getNextAccion(stateNumber, symbolId);
			nextState = new Integer(action);
			statesStack.push(nextState);
		} // end else
	}// end reduce

	private static void error(int errorNumber) {
		System.out.println(errorMessages.get(errorNumber - 1).message);
	}// end error

}// end class
