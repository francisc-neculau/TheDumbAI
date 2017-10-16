package game.backgammon.model;
import java.util.Random;

public class Dices {
	
	private static int firstDiceValue;
	private static int secondDiceValue;
	
	public static void roll() {
		Random random   = new Random();
		firstDiceValue  = random.nextInt(6);
		secondDiceValue = random.nextInt(6);
	}

	public static int getSecondDiceValue() {
		return secondDiceValue;
	}

	public static int getFirstDiceValue() {
		return firstDiceValue;
	}
}
