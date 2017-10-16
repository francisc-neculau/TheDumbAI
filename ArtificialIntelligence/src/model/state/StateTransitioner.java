package model.state;

import java.util.List;

/**
 * This interface is responsible of modeling the behavior of
 * the classes that must provide all legal state transitions
 * 
 * @author franc
 *
 */
public interface StateTransitioner<S extends State>
{
	public abstract List<S> generateAllNextLegalStates(S currentState);
}