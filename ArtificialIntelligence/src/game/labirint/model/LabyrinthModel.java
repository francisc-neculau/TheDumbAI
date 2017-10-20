package game.labirint.model;

public interface LabyrinthModel<T> {

	 public int getRowCount();
	 public int getColumnCount();
	 public boolean isFreeAt(T cell);
	 public boolean isWallAt(T cell);
	 public T getStartCell();
	 public T getFinishCell();
	
}
