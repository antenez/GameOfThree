package ba.enox.codesample.gameofthree.interfaces;

import java.util.LinkedList;

/**
 * @author eno.ahmedspahic Implementation should hold simple player data and
 *         will keep track about player step history
 */
public interface GameOfThreePlayer {

	public String getName();

	public GameOfThreePlayStep getLastPlayedStep();

	public void printStepHistory();

	public void addStepToHistory(GameOfThreePlayStep step);

	public LinkedList<GameOfThreePlayStep> getStepHistory();

}
