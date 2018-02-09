package ba.enox.codesample.gameofthree.interfaces;

import java.util.Set;

/**
 * @author eno.ahmedspahic Implementation of this interface should play main
 *         controller role for games and should enable all accessing
 *         functionalities to them.
 *
 */
public interface GameOfThreeGameController {

	/**
	 * @return List Of game names strings to access
	 */
	public Set<String> getGameOfThreeGameNamesToAccess();
	
	/**
	 * Add new GameOfThree to controler and enable acces functionalities trough
	 * controller to it
	 * 
	 * @param game game to create
	 * @throws IllegalArgumentException
	 */
	public void createNewGame(GameOfThreeGame game)
			throws IllegalArgumentException;

	/**
	 * Add new GameOfThree to controler and enable acces functionalities trough
	 * controller to it
	 * 
	 * @param newGameName
	 * @param gameAccessCode
	 * @param playerToAdd
	 * @param initialStep
	 *            if null it will be generated randomly //TODO clarify this
	 * @throws IllegalArgumentException
	 */
	public void createNewGame(String newGameName, GameOfThreePlayer playerToAdd, Integer initialStep)
			throws IllegalArgumentException;

	/**
	 * @param player
	 * @param gameName
	 * @throws IllegalArgumentException
	 */
	public void registerPlayerToGame(GameOfThreePlayer player, String gameName) throws IllegalArgumentException;

	/**
	 * @param player
	 *            registered player to game
	 * @param gameName
	 *            name of game with registered player
	 * @return game
	 * @throws IllegalArgumentException
	 *             in case that player is not registered to game, this can be
	 *             extended by additional credentials extension
	 * @throws IllegalArgumentException
	 *             in case that game with name does not exist
	 */
	public GameOfThreeGame getGameOfThreeGameByNameForRegisteredPlayer(GameOfThreePlayer player, String gameName)
			throws IllegalArgumentException;

}
