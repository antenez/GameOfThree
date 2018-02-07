/**
 * 
 */
package ba.enox.codesample.gameofthree.model;

import java.util.ArrayList;
import java.util.List;

import ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame;
import ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer;
import ba.enox.codesample.gameofthree.interfaces.GameOfThreeStepResponse;

/**
 * @author eno.ahmedspahic
 *
 */
public class GameOfThreeGameImpl implements GameOfThreeGame {

	private GameOfThreePlayer[] players = new GameOfThreePlayer[2];
	private String gameName;
	private int playedStepCounter = 0;
	private int gameState;

	public GameOfThreeGameImpl(GameOfThreePlayer initialPlayer, String gameName, int initialStep)
			throws IllegalArgumentException {
		if (initialStep < 4) {
			throw new IllegalArgumentException("Initial step should be higher then 4!");
		}
		this.gameState = initialStep;
		this.players[0] = initialPlayer;
		this.gameName = gameName;
		playedStepCounter++;// To start game
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#setGameName(
	 * java.lang.String)
	 */
	@Override
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#getGameName()
	 */
	@Override
	public String getGameName() {
		return this.gameName;
	}

	// /* (non-Javadoc)
	// * @see
	// ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#setGameAccesCode(java.lang.String)
	// */
	// @Override
	// public boolean setGameAccesCode(String accesCode) {
	//
	// return false;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#addPlayerToGame
	 * (ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer)
	 */
	@Override
	public void addPlayerToGame(GameOfThreePlayer playerToAddToGame) throws IllegalArgumentException {
		if (this.players[1] != null) {
			throw new IllegalArgumentException("game has already two players");
		}
		players[1] = playerToAddToGame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#
	 * getCurrentPlayerName()
	 */
	@Override
	public String getCurrentPlayerName() {
		return getCurrentPlayer().getName();
	}

	public String getPreviousPlayerName() {
		return getPreviousPlayer().getName();
	}

	public GameOfThreePlayer getPreviousPlayer() {
		return players[(this.playedStepCounter - 1) % this.players.length];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#
	 * getCurrentPlayer()
	 */
	@Override
	public GameOfThreePlayer getCurrentPlayer() {
		return players[this.playedStepCounter % this.players.length];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#playNext(ba.
	 * enox.codesample.gameofthree.interfaces.GameOfThreePlayer,
	 * java.lang.String,
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayStep)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#validateStep(ba
	 * .enox.codesample.gameofthree.interfaces.GameOfThreePlayStep)
	 */
	@Override
	public boolean validateStep(int stepToValidate) {

		return getValidStepProposal().contains(stepToValidate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#
	 * printValidMoveProposals()
	 */
	@Override
	public void printValidStepProposals() {
		System.out.println("+++Print valid step proposals, for player " + getCurrentPlayerName());
		System.out.println(" proposals are -1, 1, or 0");

	}

	@Override
	public List<Integer> getValidStepProposal() {
		List<Integer> proposals = new ArrayList<>();
		for (int i = -1; i < 2; i++) {
			if ((this.gameState + i) % 3 == 0) {
				proposals.add(i);
			}
		}
		return proposals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#isGameOver()
	 */
	@Override
	public boolean isGameOver() {
		return gameState == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame#
	 * verifyPlayerForGame(ba.enox.codesample.gameofthree.interfaces.
	 * GameOfThreePlayer)
	 */
	@Override
	public boolean verifyPlayerForGame(GameOfThreePlayer player) {
		for (GameOfThreePlayer gamePlayer : this.players) {
			if (gamePlayer != null && gamePlayer.getName().equals(player.getName()))
				return true;
		}
		return false;
	}

	@Override
	public int getCurrentGameState() {
		return this.gameState;
	}

}
