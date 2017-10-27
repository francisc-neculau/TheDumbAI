package model.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import model.state.FinalStateChecker;
import model.state.State;
import model.state.StateTransitioner;

public abstract class Backtracking<S extends State> 
{
	protected List<S> visitedStates;
	protected Deque<S> iterationDeque;
	
	protected FinalStateChecker<S> finalStateChecker;
	protected StateTransitioner<S> stateTransitioner;

	public Backtracking()
	{
		visitedStates = new ArrayList<>();
		iterationDeque = new ArrayDeque<>();
	}
	
	public void solveHt(S initialState)
	{
		iterationDeque.push(initialState);
		while(!iterationDeque.isEmpty())
		{
			S currentState = iterationDeque.pop();
			visitedStates.add(currentState);
			
			if(!finalStateChecker.isFinal(currentState))
			{
				List<S> childStates = stateTransitioner.generateAllNextLegalStates(currentState);
				childStates			= filterVisitedStates(childStates);
				
				for (S childState : childStates) 
				{
					iterationDeque.push(childState);
				}
			}
			else
			{
				//System.out.println("############### Found a final state ###############");
				printFinalState(currentState);
				break; // FIXME : Warning, it should find all states !
				//System.out.println("###################################################");
			}
		}
	}
	
	protected abstract List<S> filterVisitedStates(List<S> stateList);
	protected abstract void printFinalState(S finalState);

	/*
	 *  GETTERS AND SETTERS
	 */
	public void setFinalStateChecker(FinalStateChecker<S> finalStateChecker) {
		this.finalStateChecker = finalStateChecker;
	}


	public void setStateTransitioner(StateTransitioner<S> stateTransitioner) {
		this.stateTransitioner = stateTransitioner;
	}
}
