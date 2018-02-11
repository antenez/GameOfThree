package ba.enox.codesample.gameofthree.model;

public class GameOfThreeStepResponse {

	private int stepValue;
	private int gameState;

	public GameOfThreeStepResponse(int stepValue, int gameState) {
		this.stepValue = stepValue;
		this.gameState = gameState;
	}

	public int getStepValue() {
		return this.stepValue;
	}

	public int getGameState() {
		return this.gameState;
	}

}
