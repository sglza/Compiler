import java.util.LinkedList;

public class Rule {
	LinkedList<String> symbols;
	int ruleNumber;
	String nonTerminalSymbol;

	public Rule(int aNumber, String aNonTerminalSymbol) {
		ruleNumber = aNumber;
		symbols = new LinkedList<String>();
		nonTerminalSymbol = aNonTerminalSymbol;
	}// end constructor

	public void addSymbol(String aSymbol) {
		symbols.add(aSymbol);
	}// end addSymbol

	public String getNonTerminalSymbol() {
		return nonTerminalSymbol;
	}// end getNonTerminalSymbol

	public int getNumberOfSymbols() {
		return symbols.size();
	}// end getNumberOfSymbols

}// end class
