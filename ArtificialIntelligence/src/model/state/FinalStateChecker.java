package model.state;

public interface FinalStateChecker<S extends State>
{
	public abstract boolean isFinal(S state);
}
