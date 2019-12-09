/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

public class Transition {
	// Elements of a transition
	Integer originState;
	Integer goalState;
	char[] symbols;
	////////////////////

	public Transition(int originStateId, char[] symbolSet, int goalStateId) {
		originState = new Integer(originStateId);
		symbols = symbolSet;
		goalState = new Integer(goalStateId);
	}// end constructor

	public Integer getGoalState() {
		return goalState;
	}// end getGoalState

	public Integer getOriginState() {
		return originState;
	}// end getOriginState

	// Indicates if a given symbol
	// is the symbol of this transition
	public boolean itIsTheSymbol(char aSymbol) {
		int i;
		boolean symbolFound;
		//////////////////////////
		symbolFound = false;
		i = 0;
		while ((i < symbols.length) && !symbolFound) {
			if (symbols[i] == aSymbol) {
				symbolFound = true;
			} // end if
			i = i + 1;
		} // end while
		return symbolFound;
	}// end itIsTheSymbol

}// end class Transition
