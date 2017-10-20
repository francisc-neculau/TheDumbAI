package game.queens;
public class Main {


	private static final int N = 5;
	
	public static boolean isValid(int[] q, int n) {
		for (int i = 0; i < n; i++) {
			if (q[i] == q[n]) 							  return false;
			if (Math.abs(q[n] - q[i]) == Math.abs(n - i)) return false;
		}
		return true;
	}
	
	public static void printSolution(int[] q) {
		for (int i = 0; i < q.length; i++) {
			for (int j = 0; j < q.length; j++) {
				if(q[i] == j) 
					System.out.print(" q");
				else 
					System.out.print(" x");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public static void backtracking(int[] q, int row) {
		if( row == N ){ printSolution(q);}
		else {
			for(int column = 0; column < N; column++) {
				q[row] = column;
				if(isValid(q, row)) 
					backtracking(q, row + 1);
			}
		}
	}

	public static void forwardChecking(int q[], int row) {
		if( row == N ){ printSolution(q);}
		else {
			for(int column = 0; column < N; column++) {
				q[row] = column;
				if(isValid(q, row)) 
					backtracking(q, row + 1);
			}
		}
	}
	
	public static void main(String[] args) {
		//int[] q = new int[N];
		//backtracking(q, 0);
		new QueenProblem(N).solve();
	}
	
}

