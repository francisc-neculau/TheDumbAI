package model.backtracking;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import model.state.FinalStateChecker;
import model.state.State;
import model.state.StateTransitioner;

public abstract class Backtracking<T extends State> 
{
	protected List<T> visitedStates;
	protected Deque<T> iterationDeque;
	
	protected FinalStateChecker<T> finalStateChecker;
	protected StateTransitioner<T> stateTransitioner;

	public Backtracking()
	{
		visitedStates = new ArrayList<>();
		iterationDeque = new ArrayDeque<>();
	}
	
	public void solveHt(T initialState)
	{
		iterationDeque.push(initialState);
		while(!iterationDeque.isEmpty())
		{
			T currentState = iterationDeque.pop();
			visitedStates.add(currentState);
			
			if(!finalStateChecker.isFinal(currentState))
			{
				List<T> childStates = stateTransitioner.generateAllNextLegalStates(currentState);
				childStates			= filterVisitedStates(childStates);
				
				for (T childState : childStates) 
				{
					iterationDeque.push(childState);
				}
			}
			else
			{
				System.out.println("############### Found a final state ###############");
				printFinalState(currentState);
				System.out.println("###################################################");
			}
		}
	}
	
	protected abstract List<T> filterVisitedStates(List<T> stateList);
	protected abstract void printFinalState(T finalState);

	/*
	 *  GETTERS AND SETTERS
	 */
	public void setFinalStateChecker(FinalStateChecker<T> finalStateChecker) {
		this.finalStateChecker = finalStateChecker;
	}


	public void setStateTransitioner(StateTransitioner<T> stateTransitioner) {
		this.stateTransitioner = stateTransitioner;
	}
}
