package ba.enox.codesample.gameofthree.model;

public class GameOfThreeStepResponse {

	private String playerName;
	private int stepValue;
	private int gameState;

	public GameOfThreeStepResponse(){}
	
	public GameOfThreeStepResponse( String playerName, int stepValue, int gameState) {
		this.playerName = playerName;
		this.stepValue = stepValue;
		this.gameState = gameState;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getStepValue() {
		return this.stepValue;
	}

	public int getGameState() {
		return this.gameState;
	}

}
