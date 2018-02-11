/**
 * 
 */
package ba.enox.codesample.gameofthree.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author eno.ahmedspahic
 *
 */
public class GameOfThreeGame {

	private GameOfThreePlayer playerOne;
	private GameOfThreePlayer playerTwo;
	private String gameName;
	private int playedStepCounter = 0;
	private int gameState;

	public GameOfThreeGame(GameOfThreePlayer playerOne, GameOfThreePlayer playerTwo, String gameName,
			Integer initialStep) throws IllegalArgumentException {
		if (initialStep < 4) {
			throw new IllegalArgumentException("Initial step should be higher then 4!");
		}

		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.gameName = gameName;

		if (null != initialStep) {
			this.gameState = initialStep;
		} else {
			Random r = new Random();
			int Low = 4;
			int High = Integer.MAX_VALUE;
			this.gameState = r.nextInt(High - Low) + Low;
		}

		playedStepCounter++;// To start game

	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameName() {
		return this.gameName;
	}

	public void addPlayerToGame(GameOfThreePlayer playerToAddToGame) throws IllegalArgumentException {
		if (this.playerTwo != null) {
			throw new IllegalArgumentException("Game has already two players");
		}
		if (playerOne == null) {
			playerOne = playerToAddToGame;
		} else if (playerTwo == null) {
			playerTwo = playerToAddToGame;
		}
	}

	public String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	public String getPreviousPlayerName() {
		return getPreviousPlayer().getName();
	}

	public GameOfThreePlayer getPreviousPlayer() {
		return ((this.playedStepCounter - 1) % 2 == 0) ? this.playerOne : playerTwo;
	}

	public GameOfThreePlayer getCurrentPlayer() {
		return (this.playedStepCounter % 2 == 0) ? this.playerOne : playerTwo;
	}

	public GameOfThreeStepResponse playNext(GameOfThreePlayer player, int playStep)
			throws IllegalArgumentException, IllegalStateException {
		if (this.isGameOver()) {
			throw new IllegalStateException("Game is already done! Winner is " + getPreviousPlayerName());
		}

		if (!this.validateStep(playStep)) {
			throw new IllegalArgumentException("PlayStep " + playStep + " is not alowed!");
		}

		if (!this.getCurrentPlayer().getName().equals(player.getName())) {
			throw new IllegalArgumentException(
					"Expected player for current step is " + this.getCurrentPlayer().getName());
		}

		this.gameState = (this.gameState + playStep) / 3;
		playedStepCounter++;

		return new ba.enox.codesample.gameofthree.model.GameOfThreeStepResponse(playStep, this.gameState);
	}

	public boolean validateStep(int stepToValidate) {

		return getValidStepProposal() == stepToValidate;
	}

	public void printValidStepProposals() {
		System.out.println("+++Print valid step proposals, for player " + getCurrentPlayerName());
		System.out.println(" proposals are -1, 1, or 0");

	}

	public int getValidStepProposal() {

		if ((this.gameState + 1) % 3 == 0) {
			return 1;
		} else if ((this.gameState - 1) % 3 == 0) {
			return -1;
		} else {
			return 0;
		}

	}

	public boolean isGameOver() {
		return gameState == 1 ? true : false;
	}

	public boolean verifyPlayerForGame(GameOfThreePlayer player) {
		if ((this.playerOne != null && this.playerOne.getName().equals(player.getName()))
				|| (this.playerTwo != null && this.playerTwo.getName().equals(player.getName())))
			return true;
		else
			return false;
	}

	public int getCurrentGameState() {
		return this.gameState;
	}

	public void setPlayerOne(GameOfThreePlayer playerOne) {
		this.playerOne = playerOne;
	}

	public GameOfThreePlayer getPlayerOne() {
		return this.playerOne;
	}

	public GameOfThreePlayer setPlayerTwo(GameOfThreePlayer playerTwo) {
		return this.playerTwo = playerTwo;
	}

	public GameOfThreePlayer getPlayerTwo() {
		return this.playerTwo;
	}

}
