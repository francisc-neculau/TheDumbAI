package game.labirint.model;

public class LabyrinthMatrixModel implements LabyrinthModel<MatrixCell> {

	private final int WALL;
	private final int FREE;
	private final int START;
	private final int FINISH;
	
	private int[][] matrix;

	public LabyrinthMatrixModel(LabyrinthMatrixModelConfiguration configuration) {
		this.WALL   = configuration.getWall();
		this.FREE   = configuration.getFree();
		this.FINISH = configuration.getFinish();
		this.START  = configuration.getStart();
		this.matrix = configuration.getMatrix();
	}
	
	
	@Override
	public int getRowCount() {
		return this.matrix.length;
	}

	@Override
	public int getColumnCount() {
		return this.matrix[0].length;
	}

	@Override
	public boolean isFreeAt(MatrixCell cell) {
		return (this.matrix[cell.getRowNumber()][cell.getColumnNumber()] == this.FREE);
	}

	public boolean isFreeAt(int rowNumber, int columnNumber) {
		return (this.matrix[rowNumber][columnNumber] == this.FREE);
	}
	
	@Override
	public boolean isWallAt(MatrixCell cell) {
		return (this.matrix[cell.getRowNumber()][cell.getColumnNumber()] == this.WALL);
	}
	
	public boolean isWallAt(int rowNumber, int columnNumber) {
		return (this.matrix[rowNumber][columnNumber] == this.WALL);
	}

	@Override
	public MatrixCell getStartCell() {
		for (int rowNumber = 0; rowNumber < matrix.length; rowNumber++) {
			for (int columnNumber = 0; columnNumber < matrix.length; columnNumber++) {
				if(matrix[rowNumber][columnNumber] == this.START)
					return new MatrixCell(rowNumber, columnNumber, START);
			}
		}
		return null;
	}

	@Override
	public MatrixCell getFinishCell() {
		for (int rowNumber = 0; rowNumber < matrix.length; rowNumber++) {
			for (int columnNumber = 0; columnNumber < matrix.length; columnNumber++) {
				if(matrix[rowNumber][columnNumber] == this.FINISH)
					return new MatrixCell(rowNumber, columnNumber, FINISH);
			}
		}
		return null;
	}

}
