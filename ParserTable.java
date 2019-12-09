
import java.util.LinkedList;

public abstract class ParserTable {

	static LinkedList<Action> actions = new LinkedList<Action>();
	//

	public static void addAction(int aState, String aSymbol, String anAction) {
		Action action;
		//
		action = new Action(aState, aSymbol, anAction);
		actions.add(action);
	}// end addAction

	public static String getNextAccion(int aState, String aSymbol) {
		String nextAction;
		int i;
		Action anAction;
		boolean found;
		/////////////
		found = false;
		nextAction = null;
		i = 0;
		while ((i < actions.size() && !found)) {
			anAction = actions.get(i);
			if ((aState == anAction.state) && (aSymbol.equals(anAction.symbol))) {
				nextAction = anAction.theAction;
				found = true;
			} // end if
			i = i + 1;
		} // end while

		return nextAction;
	}// end getNextAccion

}// end class
