package game.backgammon.model;
import java.util.ArrayList;
import java.util.List;

public class Slice {
	
	private List<Stone> stones;

	public Slice(int numberOfStones, Label label) {
		this();
		for (int j = 0; j < numberOfStones; j++) {
			stones.add(new Stone(label));
		}
	}

	public Slice() {
		this.stones = new ArrayList<>();
	}
	
	public int size() {
		return this.stones.size();
	}	
	
	public Label getLabel() {
		return stones.size() > 0 ? stones.get(0).getLabel() : null;
	}
	
}