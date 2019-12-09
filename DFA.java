/*
 * by Gerardo Ayala
 * UDLAP
 * September 2015
 */

import java.util.LinkedList;

// Deterministic Finite Automata
public class DFA 
{
	// Elements of a DFA.
	// one initial state
	Integer initialState;
	// a set of final states
	LinkedList<Integer> finalStates;
	// a set of transitions between states
	LinkedList<Transition> transitions;
	///////////////////////
	
	
	public DFA()
	{
		initialState = new Integer(0);
		finalStates = new LinkedList<Integer>();
		transitions = new LinkedList<Transition>();
	}//end constructor
	
	
	// add a transition
	public void addTransition(Transition aTransition)
	{
		transitions.add(aTransition);
	}//end addTransition
	
	
	
	// add a final state
	public void addFinalState(int unEstadoFinal)
	{
		finalStates.add(new Integer(unEstadoFinal));
	}//end addFinalState
	
	
	// provides the transition
	// from one state
	// using a symbol
	private Transition getTransition(Integer state, char symbol)
	{
		Transition transition;
		int i;
		boolean transitionFound;
		////////////////////////
		transition = null;
		transitionFound = false;
		i = 0;
		while( (i < transitions.size()) &&
				(!transitionFound) )
		{
			transition = transitions.get(i);
			if( (transition.getOriginState().equals(state) &&
			    (transition.itIsTheSymbol(symbol)))
			  )
			{
				transitionFound = true;
			}//end if
			i = i + 1;
		}//end while
		if(transitionFound)
			return transition;
		else
			return null;
		//end if
	}//end getTransition
	
	
	// Determines if a given string
	// is recognized by the DFA
	public boolean isValid(String aString)
	{
		int i;
		char symbol;
		Integer currentState;
		Transition transition;
		boolean isInvalid;
		/////////////////

		isInvalid = false;
		currentState = initialState;
		i=0;
		while( (i < aString.length()) && (!isInvalid) )
		{
			symbol = aString.charAt(i);
			transition = getTransition(currentState,symbol);
			if(transition != null)
			{
				currentState = transition.getGoalState();
			}//end if
			else
			{
				isInvalid = true;
			}//end else
			i = i + 1;
		}//end while
		if((finalStates.contains(currentState)) &&
		   (!isInvalid))
			return true;
		else
			return false;
		//end if
	}//end isValid
}//end class
