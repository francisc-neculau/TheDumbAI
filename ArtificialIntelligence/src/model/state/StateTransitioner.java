package model.state;

import java.util.List;

/**
 * This interface is responsible of modeling the behavior of
 * the classes that must return all legal state transitions
 * 
 * @author franc
 *
 */
public interface StateTransitioner<S extends State> /*<T extends TransitionDetails, S extends State<T>>*/
{
	public abstract List<S> generateAllNextLegalStates(S currentState);
}
