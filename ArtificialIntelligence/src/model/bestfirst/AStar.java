package model.bestfirst;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import model.state.FinalStateChecker;
import model.state.State;
import model.state.StateTransitioner;

public abstract class AStar<T extends State>
{
	protected List<T> visitedStates;
	protected Deque<T> iterationDeque;

	protected FinalStateChecker<T> finalStateChecker;
	protected StateTransitioner<T> stateTransitioner;

	public AStar()
	{
		visitedStates = new ArrayList<>();
		iterationDeque = new ArrayDeque<>();
	}

	public void solve(T initialState)
	{
		iterationDeque.push(initialState);
		while (!iterationDeque.isEmpty())
		{
			T currentState = iterationDeque.pop();
			visitedStates.add(currentState);

			if (!finalStateChecker.isFinal(currentState))
			{
				List<T> childStates = stateTransitioner.generateAllNextLegalStates(currentState);
				childStates = filterVisitedStates(childStates);

				iterationDeque.addAll(childStates);
			} else
			{
				// System.out.println("############### Found the final state ###############");
				printFinalState(currentState);
				// System.out.println("###################################################");
				iterationDeque.clear();
			}
		}
	}

	protected abstract void printFinalState(T finalState);

	protected abstract List<T> filterVisitedStates(List<T> stateList);

	/*
	 * GETTERS AND SETTERS
	 */
	public void setFinalStateChecker(FinalStateChecker<T> finalStateChecker)
	{
		this.finalStateChecker = finalStateChecker;
	}

	public void setStateTransitioner(StateTransitioner<T> stateTransitioner)
	{
		this.stateTransitioner = stateTransitioner;
	}
}
