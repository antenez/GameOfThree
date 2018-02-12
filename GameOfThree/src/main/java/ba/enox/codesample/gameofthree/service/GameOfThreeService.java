/**
 * 
 */
package ba.enox.codesample.gameofthree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ba.enox.codesample.gameofthree.model.GameOfThreeGame;
import ba.enox.codesample.gameofthree.model.GameOfThreePlayer;
import ba.enox.codesample.gameofthree.model.GameOfThreeStepResponse;

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
	
	private final Logger LOG = LoggerFactory.getLogger(GameOfThreeService.class);
	
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
	
	public void deleteGame(String gameName) throws IllegalArgumentException {

		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		createdGames.remove(gameName);
	}

	public void registerPlayerToGame(GameOfThreePlayer player, String gameName) throws IllegalArgumentException {
		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		
		if (createdGames.get(gameName).isGameOver()) {
			throw new IllegalArgumentException("Game with key name " + gameName + " is already completed!");
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
	
	public GameOfThreePlayer getGameOfThreeGamePlayer(String gameName, String playerName)
			throws IllegalArgumentException {

		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		GameOfThreeGame game = createdGames.get(gameName);
		return game.getPlayerByName(playerName);
	}
	
	
	public List<GameOfThreeStepResponse> playGameStep(GameOfThreePlayer player, Integer step, String gameName)
			throws IllegalArgumentException {
		if (!createdGames.containsKey(gameName)) {
			throw new IllegalArgumentException("Game with key name " + gameName + " does not exists!");
		}
		
		GameOfThreeGame game = createdGames.get(gameName);
		LOG.info("Play Game {}, state {}, current player {}.",game.getGameName(), game.getCurrentGameState(), game.getCurrentPlayerName());
		
		
		List<GameOfThreeStepResponse> response = new ArrayList<>();
		response.add(game.playNext(player, step));
		
		LOG.info("After PlayeGame next. Play Game {}, state {}, current player {}.",game.getGameName(), game.getCurrentGameState(), game.getCurrentPlayerName());
		
		//Play automatic cases
		while(game.getCurrentPlayer().isAutomaticPlayer() && !game.isGameOver()){
			response.add(game.playNext(game.getCurrentPlayer(),game.getValidStepProposal()));
			LOG.info("After PlayeGame next Automatic player. Play Game {}, state {}, current player .",game.getGameName(), game.getCurrentGameState(), game.getCurrentPlayer());
		}
		return response;
	}
	
	
	
}
