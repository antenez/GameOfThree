/**
 * 
 */
package ba.enox.codesample.gameofthree.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;
import ba.enox.codesample.gameofthree.model.GameOfThreeGame;
import ba.enox.codesample.gameofthree.model.GameOfThreePlayer;

/**
 * @author eno.ahmedspahic GameOfThreeService is planned o be used as central
 *         point for all games stored and accessible in memory map. It is
 *         considered fact that spring beans are by default singletoon...that
 *         means that all sessions will have access to one instance of injected
 *         classes That is reason why we use map and access to particular game
 *         instances by key. This is one of requirements which can be discussed.
 *
 * 
 */
@Service(value = "GameOfThreeService")
public class GameOfThreeService {
	Map<String, GameOfThreeGame> createdGames;

	public GameOfThreeService() {
		createdGames = new HashMap<>();
	}

	public Set<String> getGameOfThreeGameNamesToAccess() {
		return createdGames.keySet();
	}

	public void createNewGame(GameOfThreeGame game) throws IllegalArgumentException {

		if (createdGames.containsKey(game.getGameName())) {
			throw new IllegalArgumentException("Game with key name " + game.getGameName() + " already exists!");
		}
		createdGames.put(game.getGameName(), game);
	}

	public void registerPlayerToGame(GameOfThreePlayer player, String gameName) throws IllegalArgumentException {
		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		GameOfThreeGame game = createdGames.get(gameName);
		if (game.getPlayerOne() == null)
			game.setPlayerOne(player);
		else if (game.getPlayerTwo() == null)
			game.setPlayerTwo(player);
		else
			throw new IllegalArgumentException("Game has initialized boath players");
	}

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
