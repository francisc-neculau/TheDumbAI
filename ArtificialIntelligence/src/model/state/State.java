package model.state;

import java.io.Serializable;

public interface State<T extends TransitionDetails> extends Serializable
{
	default T getTransitionDetails()
	{
		return null;
	}
	
}
