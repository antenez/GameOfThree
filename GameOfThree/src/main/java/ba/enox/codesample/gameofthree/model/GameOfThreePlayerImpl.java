package ba.enox.codesample.gameofthree.model;

import java.util.LinkedList;

import ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayStep;

/**
 * @author eno.ahmedspahic
 *
 */
public class GameOfThreePlayerImpl implements ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer {

	private String playerName;
	private LinkedList<GameOfThreePlayStep> playStepHistory;

	public GameOfThreePlayerImpl(String playerName) {
		this.playerName = playerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer#
	 * getCurrentPlayerName()
	 */
	@Override
	public String getName() {
		return this.playerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer#
	 * printStepHistory()
	 * 
	 * This would be used only as helper method
	 */
	@Override
	public void printStepHistory() {
		System.out.println("+++Print move history for player " + this.getName());
		for (GameOfThreePlayStep step : this.playStepHistory) {
			System.out.println(step.getStepValue());
		}

	}

	@Override
	public void addStepToHistory(GameOfThreePlayStep step) {
		if (playStepHistory == null) {
			playStepHistory = new LinkedList<>();
		}

		playStepHistory.add(step);
	}

	@Override
	public GameOfThreePlayStep getLastPlayedStep() {
		return playStepHistory.peek();
	}

	@Override
	public LinkedList<GameOfThreePlayStep> getStepHistory() {
		return this.playStepHistory;
	}

}
