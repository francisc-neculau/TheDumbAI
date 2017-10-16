package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LabyrinthMatrixModelConfiguration {

	
	private int wall;
	private int free;
	private int start;
	private int finish;
	
	private int columnsNumber = 0;
	private int rowsNumber    = 0;
	
	private List<String> lines = new ArrayList<>();
	
	public LabyrinthMatrixModelConfiguration(){}
	
	public void readMatrix(String filePath) {
		
		try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
			
			rowsNumber    = Integer.parseInt(br.readLine());
			columnsNumber = Integer.parseInt(br.readLine());
			
		    for(String line; (line = br.readLine()) != null; ) {
		    	
		        lines.add(line);
		        
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getWall() {
		return wall;
	}
	public void setWall(int wall) {
		this.wall = wall;
	}
	public int getFree() {
		return free;
	}
	public void setFree(int free) {
		this.free = free;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getFinish() {
		return finish;
	}
	public void setFinish(int finish) {
		this.finish = finish;
	}

	public int[][] getMatrix() {
		int[][] result = new int[rowsNumber][columnsNumber];
		
		String[] values;
		int lineCounter = 0;
		
		for (String line : lines) {
			values = line.trim().split(" ");
			for (int i = 0; i < values.length; i++) {
				result[lineCounter][i] = Integer.parseInt(values[i]);
			}
			lineCounter++;
		}
		
		return result;
	}

	public int getColumnsNumber() {
		return columnsNumber;
	}

	public void setColumnsNumber(int columnsNumber) {
		this.columnsNumber = columnsNumber;
	}

	public int getRowsNumber() {
		return rowsNumber;
	}

	public void setRowsNumber(int rowsNumber) {
		this.rowsNumber = rowsNumber;
	}

}
