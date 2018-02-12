package ba.enox.codesample.gameofthree.model;

/**
 * @author eno.ahmedspahic
 *
 */
public class APIPlayGameStepRequest {
	private int gameStep;
	private String playerName;
		
	public APIPlayGameStepRequest(){}

	public APIPlayGameStepRequest(int gameStep, String playerName) {
		super();
		this.gameStep = gameStep;
		this.playerName = playerName;
	}


	public int getGameStep() {
		return gameStep;
	}

	public void setGameStep(int gameState) {
		this.gameStep = gameState;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}
