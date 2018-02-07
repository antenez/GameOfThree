/**
 * 
 */
package ba.enox.codesample.gameofthree.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import ba.enox.codesample.gameofthree.interfaces.GameOfThreeGame;
import ba.enox.codesample.gameofthree.interfaces.GameOfThreeGameController;
import ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer;
import ba.enox.codesample.gameofthree.model.GameOfThreeGameImpl;

/**
 * @author eno.ahmedspahic
 *
 */
@Service(value="GameOfThreeGameControllerImpl")
public class GameOfThreeGameControllerImpl implements GameOfThreeGameController {
	Map<String, GameOfThreeGame> createdGames;

	public GameOfThreeGameControllerImpl() {
		createdGames = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGameController#
	 * getGameOfThreeGameNamesToAccess()
	 */
	@Override
	public Set<String> getGameOfThreeGameNamesToAccess() {
		return createdGames.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGameController#
	 * createNewGame(java.lang.String, java.lang.String,
	 * ba.enox.codesample.gameofthree.interfaces.GameOfThreePlayer)
	 */
	@Override
	public void createNewGame(String newGameName, GameOfThreePlayer playerToAdd, Integer initialStep)
			throws IllegalArgumentException {
		if (createdGames.containsKey(newGameName)) {
			throw new IllegalArgumentException("Game with key name " + newGameName + " already exists!");
		}

		// TODO It should be clarified should first move be random or entered by
		// user as initial step
		int stepValue = 0;
		if (initialStep != null) {
			stepValue = initialStep;
		} else {
			Random r = new Random();
			int Low = 4;
			int High = Integer.MAX_VALUE;
			stepValue = r.nextInt(High - Low) + Low;
		}

		GameOfThreeGame game = new GameOfThreeGameImpl(playerToAdd, newGameName, stepValue);
		createdGames.put(game.getGameName(), game);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ba.enox.codesample.gameofthree.interfaces.GameOfThreeGameController#
	 * RegisterPlayerToGame(ba.enox.codesample.gameofthree.interfaces.
	 * GameOfThreePlayer, java.lang.String)
	 */
	@Override
	public void registerPlayerToGame(GameOfThreePlayer player, String gameName) throws IllegalArgumentException {
		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		createdGames.get(gameName).addPlayerToGame(player);
	}

	@Override
	public GameOfThreeGame getGameOfThreeGameByNameForRegisteredPlayer(GameOfThreePlayer player, String gameName)
			throws IllegalArgumentException {

		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}

		GameOfThreeGame game = createdGames.get(gameName);

		if (!game.verifyPlayerForGame(player)) {
			throw new IllegalArgumentException(
					"Game with key name " + gameName + " does not have registered valid player " + player.getName());
		}

		return game;
	}
}
