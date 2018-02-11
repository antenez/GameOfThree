package ba.enox.codesample.gameofthree.model;

import java.util.LinkedList;

/**
 * @author eno.ahmedspahic
 *
 */
public class GameOfThreePlayer {

	private String playerName;
	private boolean isAutomaticPlayer;

	private LinkedList<GameOfThreePlayStep> playStepHistory;

	public GameOfThreePlayer(String playerName, boolean isAutomaticPlayer) {
		this.playerName = playerName;
		this.isAutomaticPlayer = isAutomaticPlayer;
	}

	public String getName() {
		return this.playerName;
	}

	public void printStepHistory() {
		System.out.println("+++Print move history for player " + this.getName());
		for (GameOfThreePlayStep step : this.playStepHistory) {
			System.out.println(step.getStepValue());
		}

	}

	public void addStepToHistory(GameOfThreePlayStep step) {
		if (playStepHistory == null) {
			playStepHistory = new LinkedList<>();
		}

		playStepHistory.add(step);
	}

	public GameOfThreePlayStep getLastPlayedStep() {
		return playStepHistory.peek();
	}

	public LinkedList<GameOfThreePlayStep> getStepHistory() {
		return this.playStepHistory;
	}

	public boolean isAutomaticPlayer() {
		return this.isAutomaticPlayer;
	}

	public void setAutomaticPlayer(boolean isAutomaticPlayer) {
		this.isAutomaticPlayer = isAutomaticPlayer;
	}

}
