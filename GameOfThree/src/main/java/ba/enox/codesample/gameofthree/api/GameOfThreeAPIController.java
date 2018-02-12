package ba.enox.codesample.gameofthree.api;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ba.enox.codesample.gameofthree.model.APICreateGameRequest;
import ba.enox.codesample.gameofthree.model.APIPlayGameStepRequest;
import ba.enox.codesample.gameofthree.model.GameOfThreeGame;
import ba.enox.codesample.gameofthree.model.GameOfThreePlayer;
import ba.enox.codesample.gameofthree.model.GameOfThreeStepResponse;
import ba.enox.codesample.gameofthree.service.GameOfThreeService;

@RestController
@RequestMapping(value = "/gameOfThree")
public class GameOfThreeAPIController {
	private final Logger LOG = LoggerFactory.getLogger(GameOfThreeAPIController.class);

	@Autowired
	GameOfThreeService gameOfThreeService;

	@RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<String>> getGameOfThreeGames() {

		return new ResponseEntity<Set<String>>(gameOfThreeService.getGameOfThreeGameNamesToAccess(), HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<List<GameOfThreeStepResponse>> createGameOfThreeGame(
			@RequestBody APICreateGameRequest gameRequest, HttpServletRequest request, HttpServletResponse response) {

		LOG.info("creating new game: {}", gameRequest);
		GameOfThreePlayer playerOne = null;
		GameOfThreePlayer playerTwo = null;

		if (gameRequest.getPlayerOneName() != null || gameRequest.isPlayerOneAutomatic()) {
			String playerOneName = gameRequest.getPlayerOneName() != null ? gameRequest.getPlayerOneName()
					: "AutomaticPlayerOne";
			playerOne = new GameOfThreePlayer(playerOneName, gameRequest.isPlayerOneAutomatic());
		}

		if (gameRequest.getPlayerTwoName() != null || gameRequest.isPlayerTwoAutomatic()) {
			String playerTwoName = gameRequest.getPlayerTwoName() != null ? gameRequest.getPlayerTwoName()
					: "AutomaticPlayerTwo";
			playerTwo = new GameOfThreePlayer(playerTwoName, gameRequest.isPlayerTwoAutomatic());
		}

		GameOfThreeGame game = new GameOfThreeGame(playerOne, playerTwo, gameRequest.getGameName(),
				gameRequest.getGameState());

		gameOfThreeService.createNewGame(game);

		List<GameOfThreeStepResponse> responseSteps = new ArrayList<>();
		while (!game.isGameOver() && game.getCurrentPlayer().isAutomaticPlayer()) {
			//play automatic steps while next is automatic player
			responseSteps.addAll(gameOfThreeService.playGameStep(game.getCurrentPlayer(), game.getValidStepProposal(),
					game.getGameName()));
		}

		return new ResponseEntity<List<GameOfThreeStepResponse>>(responseSteps, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{gameName}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteCreatedGame(@PathVariable("gameName") String gameName) {

		LOG.info("remove game :{}", gameName);
		gameOfThreeService.deleteGame(gameName);
	}

	@RequestMapping(value = "{gameName}/addPlayer", method = RequestMethod.POST)
	public ResponseEntity<Void> addUserToGame(@PathVariable("gameName") String gameName,
			@RequestBody GameOfThreePlayer player) {
		LOG.info("add player {} to game: {} {}", player, gameName);
		gameOfThreeService.registerPlayerToGame(player, gameName);
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.CREATED);
	}


	@RequestMapping(value = "/{gameName}/playStep", method = RequestMethod.PUT)
	public ResponseEntity<List<GameOfThreeStepResponse>> playStep(@PathVariable("gameName") String gameName,
			@RequestBody APIPlayGameStepRequest apiPlayGameStepRequest) {

		LOG.info("play game step on game {}. Step state:{}. Step player name:{}.", gameName,
				apiPlayGameStepRequest.getGameStep(), apiPlayGameStepRequest.getPlayerName());
		GameOfThreePlayer player = gameOfThreeService.getGameOfThreeGamePlayer(gameName,
				apiPlayGameStepRequest.getPlayerName());

		GameOfThreeGame game = gameOfThreeService.getGameOfThreeGameByNameForRegisteredPlayer(player, gameName);
		List<GameOfThreeStepResponse> response = new ArrayList<>();
		response.add(game.playNext(player, apiPlayGameStepRequest.getGameStep()));

		// Play automatic steps;
		while (!game.isGameOver() && game.getCurrentPlayer().isAutomaticPlayer()) {
			response.addAll(gameOfThreeService.playGameStep(game.getCurrentPlayer(), game.getValidStepProposal(),
					game.getGameName()));
		}

		return new ResponseEntity<List<GameOfThreeStepResponse>>(response, HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(value = "/{gameName}/getProposal", method = RequestMethod.POST)
	public ResponseEntity<Integer> getGameStepProposal(@PathVariable("gameName") String gameName,
			@RequestBody GameOfThreePlayer player) {

		LOG.info("get proposal for {} game. Player {} ", gameName, player);

		return new ResponseEntity<Integer>(
				gameOfThreeService.getGameOfThreeGameByNameForRegisteredPlayer(player, gameName).getValidStepProposal(),
				HttpStatus.OK);
	}

}
