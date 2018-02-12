package ba.enox.codesample.gameofthree.model;

public class APICreateGameRequest {

	private int gameState;
	private String gameName;
	private String playerOneName;
	private boolean isPlayerOneAutomatic;
	private String playerTwoName;
	private boolean isPlayerTwoAutomatic;
	
	public APICreateGameRequest(){}
	
	/**
	 * @param gameState
	 * @param gameName
	 * @param playerOneName
	 * @param isPlayerOneAutomatic
	 * @param playerTwoName
	 * @param isPlayerTwoAutomatic
	 */
	public APICreateGameRequest(int gameState, String gameName, String playerOneName, boolean isPlayerOneAutomatic,
			String playerTwoName, boolean isPlayerTwoAutomatic) {
		super();
		this.gameState = gameState;
		this.gameName = gameName;
		this.playerOneName = playerOneName;
		this.isPlayerOneAutomatic = isPlayerOneAutomatic;
		this.playerTwoName = playerTwoName;
		this.isPlayerTwoAutomatic = isPlayerTwoAutomatic;
	}
	public boolean isPlayerOneAutomatic() {
		return isPlayerOneAutomatic;
	}
	public void setPlayerOneAutomatic(boolean isPlayerOneAutomatic) {
		this.isPlayerOneAutomatic = isPlayerOneAutomatic;
	}
	public boolean isPlayerTwoAutomatic() {
		return isPlayerTwoAutomatic;
	}
	public void setPlayerTwoAutomatic(boolean isPlayerTwoAutomatic) {
		this.isPlayerTwoAutomatic = isPlayerTwoAutomatic;
	}
	public int getGameState() {
		return gameState;
	}
	public void setGameState(int gameState) {
		this.gameState = gameState;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getPlayerOneName() {
		return playerOneName;
	}
	public void setPlayerOneName(String playerOneName) {
		this.playerOneName = playerOneName;
	}
	public String getPlayerTwoName() {
		return playerTwoName;
	}
	public void setPlayerTwoName(String playerTwoName) {
		this.playerTwoName = playerTwoName;
	}

}
