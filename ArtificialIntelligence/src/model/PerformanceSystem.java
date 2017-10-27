package model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.state.FinalStateChecker;
import model.state.SolutionStateTrace;
import model.state.State;
import model.state.StateChooser;
import model.state.StateTransitioner;

/**
 * This class must solve the given Task using the learned target function(s)
 * starting from a initial state and producing the StateTrace of the solution
 * 
 * @author franc
 *
 * @param <S>
 */
public class PerformanceSystem<S extends State>
{
	private final Logger logger = LoggerFactory.getLogger(PerformanceSystem.class);
	
	private StateTransitioner<S> transitioner;
	private StateChooser<S> chooser;
	private FinalStateChecker<S> checker;
	private EvaluationFunction<S> evaluationFunction;
	
	// FIXME : The performance system should keep track of solution performance measure parameter values
	
	public PerformanceSystem(StateTransitioner<S> transitioner)
	{
		this.transitioner = transitioner;
	}
	
	public SolutionStateTrace<S> solve(S initialState)
	{
		//LocalDateTime dtStart = LocalDateTime.now();
		SolutionStateTrace<S> stateTrace = new SolutionStateTrace<>(initialState);
		List<S> states;
		S currentState;
		S nextState;
		currentState = initialState;
		while (!checker.isFinal(currentState))
		{
//			System.out.println(currentState);
			stateTrace.add(currentState);
			states = transitioner.generateAllNextLegalStates(currentState);
			nextState = chooser.choose(states, currentState, evaluationFunction);
			currentState = nextState;
		}
		stateTrace.add(currentState);
		//LocalDateTime dtEnd = LocalDateTime.now();
		//Duration duration = Duration.between(dtStart, dtEnd);
		//logger.info(Long.toString(duration.getSeconds()));
		return stateTrace;
	}

	/**
	 * @return the chooser
	 */
	public StateChooser<S> getChooser()
	{
		return chooser;
	}

	/**
	 * @param chooser the chooser to set
	 */
	public void setChooser(StateChooser<S> chooser)
	{
		this.chooser = chooser;
	}

	/**
	 * @return the checker
	 */
	public FinalStateChecker<S> getChecker()
	{
		return checker;
	}

	/**
	 * @param checker the checker to set
	 */
	public void setChecker(FinalStateChecker<S> checker)
	{
		this.checker = checker;
	}

	/**
	 * @return the evaluationFunction
	 */
	public EvaluationFunction<S> getEvaluationFunction()
	{
		return evaluationFunction;
	}

	/**
	 * @param evaluationFunction the evaluationFunction to set
	 */
	public void setEvaluationFunction(EvaluationFunction<S> evaluationFunction)
	{
		this.evaluationFunction = evaluationFunction;
	}

}
