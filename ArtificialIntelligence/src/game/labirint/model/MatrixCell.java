package model;

public class MatrixCell {

	private int rowNumber;
	private int columnNumber;
	private MatrixCell parent;
	
	public MatrixCell(int rowNumber,int columnNumber, int value){
		this.rowNumber    = rowNumber;
		this.columnNumber = columnNumber;
		this.parent = null;
	}
	
	@Override
    public String toString() {
        return "Cell{" + rowNumber + ", " + columnNumber + "}";
    }
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof MatrixCell)) return false;
		MatrixCell cell = (MatrixCell)obj;
		
		if(this.rowNumber != cell.rowNumber) return false;
		if(this.columnNumber != cell.columnNumber) return false;
			
		return true;
	}
	
	@Override
	public int hashCode() {
        int result = 0;
        result = 31 * this.rowNumber + result;
        result = 139 * this.columnNumber + result;
        return result;
    }

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public MatrixCell getParent() {
		return parent;
	}

	public void setParent(MatrixCell parent) {
		this.parent = parent;
	}

	public boolean hasParent() {
		return this.parent != null;
	}
	
	
}
