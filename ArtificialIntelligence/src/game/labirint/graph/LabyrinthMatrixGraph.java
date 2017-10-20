package game.labirint.graph;

import game.labirint.model.LabyrinthMatrixModel;
import game.labirint.model.MatrixCell;

public class LabyrinthMatrixGraph extends Graph<MatrixCell> {


	private MatrixCell startNode;
	private MatrixCell finishNode;
	private LabyrinthMatrixModel model;
	
	public LabyrinthMatrixGraph(LabyrinthMatrixModel model) {
		this.model = model;
		this.build();
	}


	private void build() {
		this.startNode  = model.getStartCell();
		this.finishNode = model.getFinishCell();
		this.addNode(this.startNode);
		this.addNode(this.finishNode);
		/*
		 * Add all nodes
		 */
		for (int rowCount = 0; rowCount < this.model.getRowCount(); rowCount++ ) {
			for (int columnCount = 0; columnCount < this.model.getColumnCount(); columnCount++) {
				if(this.model.isFreeAt(rowCount, columnCount))
					this.addNode(new MatrixCell(rowCount, columnCount, 0));
			}
		}
		/*
		 * Build edges nodes
		 */
		for (int rowCount = 0; rowCount < this.model.getRowCount(); rowCount++ ) {
			for (int columnCount = 0; columnCount < this.model.getColumnCount(); columnCount++) {
				if(!this.model.isWallAt(rowCount, columnCount)){
				// left
					if(nodeExists(rowCount, columnCount -1))
						this.addEdge(getNode(rowCount, columnCount), getNode(rowCount, columnCount -1));
				// up
					if(nodeExists(rowCount - 1, columnCount))
						this.addEdge(getNode(rowCount, columnCount), getNode(rowCount - 1, columnCount));
				// right
					if(nodeExists(rowCount, columnCount + 1))
						this.addEdge(getNode(rowCount, columnCount), getNode(rowCount, columnCount + 1));
				// down
					if(nodeExists(rowCount + 1, columnCount))
						this.addEdge(getNode(rowCount, columnCount), getNode(rowCount + 1, columnCount));
				}
			}
		}
		
		
	}


	private MatrixCell getNode(int rowNumber, int columnNumber) {
		for (MatrixCell cell : this.dataModel.keySet()) {
			if((cell.getRowNumber() == rowNumber)&&(cell.getColumnNumber() == columnNumber))
				return cell;
		}
		return null;
	}


	private boolean nodeExists(int rowNumber, int columnNumber) {
		for (MatrixCell cell : this.dataModel.keySet()) {
			if((cell.getRowNumber() == rowNumber)&&(cell.getColumnNumber() == columnNumber))
				return true;
		}
		return false;
	}


	
	
	public MatrixCell getStartNode() {
		return startNode;
	}


	public void setStartNode(MatrixCell startNode) {
		this.startNode = startNode;
	}


	public MatrixCell getFinishNode() {
		return finishNode;
	}


	public void setFinishNode(MatrixCell finishNode) {
		this.finishNode = finishNode;
	}
	
}
