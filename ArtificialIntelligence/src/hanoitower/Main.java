package hanoitower;

import hanoitower.model.HtFinalStateChecker;
import hanoitower.model.HtState;
import hanoitower.model.HtStateTransitioner;
import hanoitower.strategy.HtAStarFunction;
import hanoitower.strategy.HtHeuristicFunction;
import hanoitower.strategy.HtRandomFunction;
import hanoitower.strategy.HtStateChooser;
import model.PerformanceSystem;
import model.state.SolutionStateTrace;

public class Main
{

	/*
	 * INfO : 
	 * https://csce.ucmss.com/books/LFS/CSREA2017/FEC3153.pdf
	 * http://aries.ektf.hu/~gkusper/ArtificialIntelligence_LectureNotes.v.1.0.4.pdf ( page 45 )
	 */
	public static void main(String[] args)
	{
		HtState.NUMBER_OF_DISKS = 6;
		HtState.NUMBER_OF_RODS = 3;
		int[][] disksOnRodsDistribution = {{1, 2, 3, 4, 5, 6}, {0}, {0}};
		
		
		PerformanceSystem<HtState> performanceSystem = new PerformanceSystem<>(new HtStateTransitioner());
		
		performanceSystem.setChecker(new HtFinalStateChecker());
		performanceSystem.setChooser(new HtStateChooser());
		
//		performanceSystem.setEvaluationFunction(new HtRandomFunction());
//		performanceSystem.setEvaluationFunction(new HtAStarFunction());
		performanceSystem.setEvaluationFunction(new HtHeuristicFunction());
		
		HtState initialState = new HtState(disksOnRodsDistribution);
		
		SolutionStateTrace<HtState> solution = performanceSystem.solve(initialState);
		
		for (HtState state : solution.getStates())
		{
			System.out.println(state);
		}
			
	}

}
