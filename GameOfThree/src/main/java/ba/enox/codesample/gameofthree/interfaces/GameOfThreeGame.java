package ba.enox.codesample.gameofthree.interfaces;

import java.util.List;

/**
 * @author eno.ahmedspahic
 *
 */
public interface GameOfThreeGame {
	
	
	/**
	 * Player one should be initialize trough  constructor
	 * This is just getter method
	 * @return
	 */
	public GameOfThreePlayer getPlayerOne();
	
	/**
	 * Set player Two
	 * @param playerTwo
	 * @return
	 */
	public GameOfThreePlayer setPlayerTwo(GameOfThreePlayer playerTwo);
	
	/**
	 * GetPlayer Two
	 * @return
	 */
	public GameOfThreePlayer getPlayerTwo();
	
	/**
	 * Set actual name for game used to access
	 * 
	 * @param gameName
	 */
	public void setGameName(String gameName);

	/**
	 * Return string gameName used to access to game by name
	 * 
	 * @return
	 */
	public String getGameName();

	// /**
	// this would be implemented in case of extra security for accessing game by
	// access code.
	// * Access code used to verify valid access to game.
	// * Can be set only in case thay it does not exists
	// * @param accesCode
	// * @return true / false if success or not
	// */
	// public boolean setGameAccesCode(String accesCode);

	/**
	 * Add Player to current game if possible
	 * 
	 * @param playerToAddToGame
	 * @return true false if success
	 */
	public void addPlayerToGame(GameOfThreePlayer playerToAddToGame) throws IllegalArgumentException;

	/**
	 * Return name of current player for move
	 * 
	 * @return
	 */
	public String getCurrentPlayerName();

	/**
	 * Verify is player valid for current game. Additionally this can be
	 * extended with additional credentials verification
	 * 
	 * @return true/false if player is valid for game.
	 */
	public boolean verifyPlayerForGame(GameOfThreePlayer player);

	/**
	 * @return current player for next move
	 */
	public GameOfThreePlayer getCurrentPlayer();

	/**
	 * Accept and process new game step from player Additionally veryfy
	 * 
	 * @param plaeyer
	 * @param playStep
	 * @return
	 * @throws IllegalArgumentException
	 *             in case of wrong game access code and invalid game play step
	 * @throws IllegalStateException
	 *             in case if game is done already
	 * @throws IllegalStateException
	 *             if player is not as expected by controller according to
	 *             played step counter
	 * 
	 */
	public GameOfThreeStepResponse playNext(GameOfThreePlayer plaeyer, int playStep)
			throws IllegalArgumentException, IllegalStateException;

	/**
	 * Validate is step value valid according to game rules. Value should be
	 * equal, 1 value higher or 1 value lower.
	 * 
	 * @param stepToValidate
	 * @return
	 */
	public boolean validateStep(int stepToValidate);

	/**
	 * Print valid move proposals
	 */
	public void printValidStepProposals();

	/**
	 * Get valid move proposals to get number which can be divided by 3
	 */
	public List<Integer> getValidStepProposal();

	/**
	 * Get Current game state
	 */
	public int getCurrentGameState();

	/**
	 * Check Is game completed
	 * 
	 * @return
	 */
	public boolean isGameOver();

}
