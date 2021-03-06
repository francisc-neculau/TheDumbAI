package hanoitower;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalTime;

import hanoitower.model.HtAStar;
import hanoitower.model.HtBacktracking;
import hanoitower.model.HtFinalStateChecker;
import hanoitower.model.HtState;
import hanoitower.model.HtStateTransitioner;
import hanoitower.strategy.HtHillClimbingFunction;
import hanoitower.strategy.HtRandomFunction;
import hanoitower.strategy.chooser.HtRandomStateChooser;
import hanoitower.strategy.chooser.HtStateChooser;
import model.PerformanceSystem;
import model.state.SolutionStateTrace;

public class Main
{

	/*
	 * Deci rezum aici ce ar mai fi de facut , plus ce mi-am notat din clasa :
pentru Random si Hill Climbin trebuie sa afisam :
- durata medie executii ( evident metrica asta se va face pentru un calup de executii a programului )
- lungime medie drum
- numar mediu de tranzitii
	 */
	/*
	 * INfO : 
	 * https://csce.ucmss.com/books/LFS/CSREA2017/FEC3153.pdf
	 * http://aries.ektf.hu/~gkusper/ArtificialIntelligence_LectureNotes.v.1.0.4.pdf ( page 45 )
	 */
	static int[][] disksOnRodsDistribution = null;
	
	public static void main(String[] args) throws Exception
	{
		processConfigFile();
		
//		HtState.NUMBER_OF_DISKS = 5;
//		HtState.NUMBER_OF_RODS = 3;
//		int[][] disksOnRodsDistribution = {{ 3, 4, 5}, {1, 2,}, {0}};
		
		HtState initialState = new HtState(disksOnRodsDistribution);
		
		System.out.println(initialState);
		
		PerformanceSystem<HtState> performanceSystem = new PerformanceSystem<>(new HtStateTransitioner());
		performanceSystem.setChecker(new HtFinalStateChecker());
		
		SolutionStateTrace<HtState> solution;
		LocalTime startTime, endTime;
		Duration duration;
		long totalNanoTime, totalSolutionLength;
		long NUMBER_OF_ITERATIONS = 1000;
		
		
		totalNanoTime = 0;
		totalSolutionLength = 0;
		int totalUniqueTransitions = 0;
		for (int i = 0; i < NUMBER_OF_ITERATIONS; i++)
		{
			/*
			 * Random
			 */
			performanceSystem.setEvaluationFunction(new HtRandomFunction());
			performanceSystem.setChooser(new HtStateChooser());
			startTime = LocalTime.now();
			solution = performanceSystem.solve(initialState);
			endTime = LocalTime.now();
			duration = Duration.between(startTime, endTime);
			totalNanoTime += duration.getNano();
			totalSolutionLength += solution.getLastState().getDepth();
			totalUniqueTransitions += solution.getUniquesStates().size();
			
		}
		System.out.println("Random Performance : ");
		System.out.println("Avearage Time              : " + (totalNanoTime/NUMBER_OF_ITERATIONS)/Math.pow(10.0, 6.0));
		System.out.println("Avearage Solution Lentgh   : " + (totalSolutionLength/NUMBER_OF_ITERATIONS));
		System.out.println("Avearage Unique Transition : " + (totalUniqueTransitions/NUMBER_OF_ITERATIONS));
		
		
		totalNanoTime = 0;
		totalSolutionLength = 0;
		for (int i = 0; i < 1; i++)
		{
			/*
			 * HillClimbing
			 */
			performanceSystem.setEvaluationFunction(new HtHillClimbingFunction());
			performanceSystem.setChooser(new HtRandomStateChooser());
			startTime = LocalTime.now();
			solution = performanceSystem.solve(initialState);
			endTime = LocalTime.now();
			duration = Duration.between(startTime, endTime);
			totalNanoTime += duration.getNano();
			totalSolutionLength += solution.getLastState().getDepth();
			
		}
//		System.out.println("HillClimbing Performance : ");
//		System.out.println("Avearage Time            : " + (totalNanoTime/NUMBER_OF_ITERATIONS)/Math.pow(10.0, 6.0));
//		System.out.println("Avearage Solution Lentgh : " + (totalSolutionLength/NUMBER_OF_ITERATIONS));
//
//
//		System.out.println("============================");
//		/*
//		 * Backtracking
//		 */
//		System.out.println("Backtracking :");
//		HtBacktracking bktSystem = new HtBacktracking();
//		bktSystem.setFinalStateChecker(new HtFinalStateChecker());
//		bktSystem.setStateTransitioner(new HtStateTransitioner());
//		bktSystem.solveHt(initialState);
		
//		System.out.println();
//		/*
//		 * Best First Search
//		 */
//		System.out.println("Best First Search :");
//		HtAStar aStarSystem = new HtAStar();
//		aStarSystem.setFinalStateChecker(new HtFinalStateChecker());
//		aStarSystem.setStateTransitioner(new HtStateTransitioner());
//		aStarSystem.solve(initialState);
	}

	private static void processConfigFile() throws Exception
	{
		/*
		 * Structure:
		 * 1 - number of disks
		 * 2 - number of rods
		 * 3 - initial state
		 */
		
		BufferedReader fileReader = new BufferedReader(new FileReader("resources//hanoiTowers//inputArgs.txt"));
		String line = fileReader.readLine();
		HtState.NUMBER_OF_DISKS = Integer.parseInt(line);
		
		line = fileReader.readLine();
		HtState.NUMBER_OF_RODS = Integer.parseInt(line);
	
		disksOnRodsDistribution = new int[HtState.NUMBER_OF_RODS][];
		
		for(int counter = 0; counter < HtState.NUMBER_OF_RODS; counter++ )
		{
			line = fileReader.readLine();
			if(line == null)
				line = "";
			if(line.equals(""))
			{
				disksOnRodsDistribution[counter] = new int[0];
				continue;
			}
			String[] disks = line.split(" ");
			disksOnRodsDistribution[counter] = new int[disks.length];
			
			for (int i = 0; i < disks.length; i++)
			{
				disksOnRodsDistribution[counter][i] = Integer.parseInt(disks[i]);
			}
		}
	}

}
