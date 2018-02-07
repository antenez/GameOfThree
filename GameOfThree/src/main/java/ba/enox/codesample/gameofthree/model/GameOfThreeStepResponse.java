package ba.enox.codesample.gameofthree.model;

public class GameOfThreeStepResponse implements ba.enox.codesample.gameofthree.interfaces.GameOfThreeStepResponse {

	private int stepValue;
	private int gameState;

	public GameOfThreeStepResponse(int stepValue, int gameState) {
		this.stepValue = stepValue;
		this.gameState = gameState;
	}

	@Override
	public int getStepValue() {
		return this.stepValue;
	}

	@Override
	public int getGameState() {
		return this.gameState;
	}

}
