package game.backgammon.model;
import java.util.ArrayList;
import java.util.List;

public class Board {

	/*
	 *  0 ->  5 HOME WHITE
	 * 18 -> 23 HOME BLACK
	 */
	List<Slice> slices;
	Label currentPlayerLabel;
	
	public Board() {
		slices = new ArrayList<>(24);
		for (int i = 0; i < 24; i++) {
			slices.add(null);
		}
		slices.set(0, new Slice(2, Label.BlACK));
		slices.set(5, new Slice(5, Label.WHITE));
		slices.set(7, new Slice(3, Label.WHITE));
		slices.set(11, new Slice(5, Label.BlACK));
		
		slices.set(12, new Slice(5, Label.WHITE));
		slices.set(16, new Slice(3, Label.BlACK));
		slices.set(18, new Slice(5, Label.BlACK));
		slices.set(23, new Slice(2, Label.WHITE));
		for (int i = 0; i < 24; i++) {
			if(slices.get(i) == null)
				slices.set(i, new Slice());
		}
	}
	
	public void move(int fromSliceNumber, int toSliceNumber) {
		
	}
	
	@Override
	public String toString() {
		Integer max = 7;
		StringBuilder sb = new StringBuilder();
		sb.append("*- - - *- - - *");
		sb.append("\n");
		for (int i = 0; i < 7; i++) {
			sb.append("*");
			for (int j = 0; j < 12; j++) {
				if(j == 6)
					sb.append("*");
				if(slices.get(j).size() >= i+1)
					if(slices.get(j).getLabel() == Label.WHITE)
						sb.append("w");
					else
						sb.append("b");
				else
					sb.append(" ");
			}
			sb.append("*");
			sb.append("\n");
		}
		//sb.append("*      *      *\n");
		sb.append("*- - - *- - - *\n");
		//sb.append("*      *      *\n");
		for (int i = 0; i < 7; i++) {
			sb.append("*");
			for (int j = 12; j < 24; j++) {
				if(j == 18)
					sb.append("*");
				if(slices.get(j).size() >= max)
					if(slices.get(j).getLabel() == Label.WHITE)
						sb.append("w");
					else
						sb.append("b");
				else
					sb.append(" ");
			}
			max--;
			sb.append("*");
			sb.append("\n");
		}
		sb.append("*- - - *- - - *");
		
		return sb.toString();
	}
	
}