package hanoitower;

import hanoitower.model.HtAStar;
import hanoitower.model.HtBacktracking;
import hanoitower.model.HtFinalStateChecker;
import hanoitower.model.HtState;
import hanoitower.model.HtStateTransitioner;
import hanoitower.strategy.HtAStarFunction;
import hanoitower.strategy.HtHeuristicFunction;
import hanoitower.strategy.HtHillClimbingFunction;
import hanoitower.strategy.HtRandomFunction;
import hanoitower.strategy.HtRandomStateChooser;
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
		HtState.NUMBER_OF_DISKS = 7;
		HtState.NUMBER_OF_RODS = 3;
		int[][] disksOnRodsDistribution = {{1, 2, 3, 4, 5, 6, 7}, {0}, {0}};
		
		
		PerformanceSystem<HtState> performanceSystem = new PerformanceSystem<>(new HtStateTransitioner());
		
		performanceSystem.setChecker(new HtFinalStateChecker());
//		performanceSystem.setChooser(new HtStateChooser());
		performanceSystem.setChooser(new HtRandomStateChooser());
		
//		performanceSystem.setEvaluationFunction(new HtRandomFunction());
		performanceSystem.setEvaluationFunction(new HtHillClimbingFunction());
//		performanceSystem.setEvaluationFunction(new HtHeuristicFunction());
		
		HtState initialState = new HtState(disksOnRodsDistribution);
		
		SolutionStateTrace<HtState> solution = performanceSystem.solve(initialState);
		
//		for (HtState state : solution.getStates())
//		{
//			System.out.println(state);
//		}
		
		HtState p = solution.getStates().get(solution.getStates().size() - 1).getPredecesor();
		int counter = 0;
		while( p.getPredecesor() != null) 
		{
			//System.out.println(p);
			p = p.getPredecesor();
			counter++;
		}
//		System.out.println(counter);
		System.out.println("Depth:" + solution.getStates().get(solution.getStates().size() - 1).getDepth());
		
		
//		HtBacktracking bktSystem = new HtBacktracking();
//		bktSystem.setFinalStateChecker(new HtFinalStateChecker());
//		bktSystem.setStateTransitioner(new HtStateTransitioner());
//		bktSystem.solveHt(initialState);
		
//		HtAStar aStarSystem = new HtAStar();
//		aStarSystem.setFinalStateChecker(new HtFinalStateChecker());
//		aStarSystem.setStateTransitioner(new HtStateTransitioner());
//		aStarSystem.solveHt(initialState);
	}

}
