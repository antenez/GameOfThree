/**
 * 
 */
package ba.enox.codesample.gameofthree.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.stereotype.Service;

import ba.enox.codesample.gameofthree.model.GameOfThreeGame;
import ba.enox.codesample.gameofthree.model.GameOfThreePlayer;
import ba.enox.codesample.gameofthree.model.GameOfThreeStepResponse;

/**
 * @author eno.ahmedspahic
 *
 */
@Service
public class GameOfThreeGameControllerTest {

	// Common shared instances for test casses
	String myNotExistingGameName = "NOT_EXISTING_GAME_NAME";
	GameOfThreePlayer myGameNotRegisteredPlayer = new GameOfThreePlayer("NOT_REGISTERED_PLAYER", false);
	GameOfThreeGame myGameNotExisting = new GameOfThreeGame(myGameNotRegisteredPlayer, null, myNotExistingGameName,
			100);

	/*
	 * Test is game configured according to input parameters. Field size Number
	 * of players Initial position
	 */
	@Test
	public void testSetupGame() {
		GameOfThreeService gameController = new GameOfThreeService();
		String myGameOneName = "myGameOne";
		GameOfThreePlayer playerOne = new GameOfThreePlayer("EnoPlayer", false);
		GameOfThreeGame game =  new GameOfThreeGame(null, null, myGameOneName, 5);
		game.addPlayerToGame(playerOne);
		gameController.createNewGame(game);
		
		

		List<String> expectedGameNames = new ArrayList<>();
		expectedGameNames.add(myGameOneName);
		Set<String> createdGameNames = gameController.getGameOfThreeGameNamesToAccess();

		Assert.assertEquals("GameController does not have configured expected games! ", true,
				createdGameNames.containsAll(expectedGameNames));

		// Test registered users
		GameOfThreeGame myGameOne;

		try {
			myGameOne = gameController.getGameOfThreeGameByNameForRegisteredPlayer(myGameNotRegisteredPlayer,
					myGameOneName);
			fail("This should fail because unregistered player to game!");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Game with key name " + myGameOneName + " does not have registered valid player "
					+ myGameNotRegisteredPlayer.getName(), e.getMessage());
		}

		try {
			myGameOne = gameController.getGameOfThreeGameByNameForRegisteredPlayer(playerOne,
					myNotExistingGameName);
			fail("This should fail because not existing game in controller!");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Game with key name " + myNotExistingGameName + " does not exists!", e.getMessage());
		}

		myGameOne = gameController.getGameOfThreeGameByNameForRegisteredPlayer(playerOne, myGameOneName);
		Assert.assertNull(
				"After first step next player should be == null  because it is not still registered on this line",
				myGameOne.getCurrentPlayer());

		// Register player two
		GameOfThreePlayer myGameOneNamePlayerTwo = new GameOfThreePlayer("SecondPlayer", false);
		gameController.registerPlayerToGame(myGameOneNamePlayerTwo, myGameOneName);
		Assert.assertEquals(
				"Current player should be second registered player after initial first step from player one!",
				myGameOneNamePlayerTwo.getName(), myGameOne.getCurrentPlayer().getName());

	}

	@Test
	public void testGameLogic() {

		GameOfThreeService gameService = new GameOfThreeService();
		String myGameOneName = "myGameOne";
		GameOfThreePlayer myGamePlayerOne = new GameOfThreePlayer("PlayerOne", false);
		GameOfThreeGame game = new GameOfThreeGame(null, null, myGameOneName, 56);
		game.addPlayerToGame(myGamePlayerOne);
		gameService.createNewGame(game);
		// adding player two to game
		GameOfThreePlayer myGamePlayerTwo = new GameOfThreePlayer("SecondPlayer", false);
		gameService.registerPlayerToGame(myGamePlayerTwo, myGameOneName);

		GameOfThreeGame myGame = gameService.getGameOfThreeGameByNameForRegisteredPlayer(myGamePlayerOne,
				myGameOneName);
		validateCommonGameOfThreeBehaviour(myGame, myGamePlayerTwo.getName(), 56, 1, false);

		// Play Wrong step
		try {
			gameService.playGameStep(myGamePlayerTwo, -1, myGame.getGameName()) ;  //myGame.playNext(myGamePlayerTwo, -1);
			fail("-1 is not valid step and it should fail!");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("PlayStep -1 is not alowed!", e.getMessage());
		}

		// Play Wrong player
		try {
			gameService.playGameStep(myGamePlayerOne, 1, myGame.getGameName()) ;  //myGame.playNext(myGamePlayerOne, 1);
			fail("Game player one is not expected player!It should fail!");
		} catch (IllegalArgumentException e) {
			Assert.assertEquals("Expected player for current step is SecondPlayer", e.getMessage());
		}
		GameOfThreeStepResponse response;

		// Step from 56 to 19 action =1, next proposal contains -1
		response = gameService.playGameStep(myGamePlayerTwo, 1, myGame.getGameName()).get(0);   //myGame.playNext(myGamePlayerTwo, 1);
		validateCommonGameOfThreeBehaviour(myGame, myGamePlayerOne.getName(), 19, -1, false);
		validateGameOfThreeStepResponse(response,myGamePlayerTwo.getName(), 19, 1);

		// step from 19 to 6 action -1 next proposal contains 0
		response = gameService.playGameStep(myGamePlayerOne, -1, myGame.getGameName()).get(0); //myGame.playNext(myGamePlayerOne, -1);
		validateCommonGameOfThreeBehaviour(myGame, myGamePlayerTwo.getName(), 6, 0, false);
		validateGameOfThreeStepResponse(response,myGamePlayerOne.getName(), 6, -1);

		// step from 6 to 2 action 0 next proposal contains 1
		response = gameService.playGameStep(myGamePlayerTwo, 0, myGame.getGameName()).get(0);  //myGame.playNext(myGamePlayerTwo, 0);
		validateCommonGameOfThreeBehaviour(myGame, myGamePlayerOne.getName(), 2, 1, false);
		validateGameOfThreeStepResponse(response,myGamePlayerTwo.getName(), 2, 0);

		// step from 2 to 0 action 1 next proposal contains is not relevant
		response = gameService.playGameStep(myGamePlayerOne, 1, myGame.getGameName()).get(0);  //myGame.playNext(myGamePlayerOne, 1);
		validateCommonGameOfThreeBehaviour(myGame, myGamePlayerTwo.getName(), 1, -1, true);
		validateGameOfThreeStepResponse(response,myGamePlayerOne.getName(), 1, 1);

		// step after game is ended
		try {
			response = gameService.playGameStep(myGamePlayerOne, 1, myGame.getGameName()).get(0);  //myGame.playNext(myGamePlayerOne, 1);
			fail("Game is already ended, exception is epected! ");
		} catch (IllegalStateException e) {
			Assert.assertEquals("Game is already done! Winner is PlayerOne", e.getMessage());
		}

	}

	private void validateCommonGameOfThreeBehaviour(GameOfThreeGame myGame, String expectedNextPlayerName,
			int expectedGameState, int expectedNextStepProposal, boolean expectedGameOverState) {
		// expected proposals can be changet to accept list as a parameter but
		// for nove tests will cover all casses until clarification of
		// requirements
		Assert.assertEquals("Player is not expected ", expectedNextPlayerName, myGame.getCurrentPlayer().getName());
		Assert.assertEquals(
				"Invalid step proposal " + expectedNextStepProposal + " but proposals is "
						+ myGame.getValidStepProposal(),
						expectedNextStepProposal, myGame.getValidStepProposal());
		Assert.assertEquals("Game is not over jet ", expectedGameOverState, myGame.isGameOver());
		Assert.assertEquals("Game state is not as expected ", expectedGameState, myGame.getCurrentGameState());
	}

	private void validateGameOfThreeStepResponse( GameOfThreeStepResponse responseToValidate,String expectedPlayerName, int expectedState,
			int takenStepValue) {
		Assert.assertEquals("Invalid playerName in result!", expectedPlayerName, responseToValidate.getPlayerName());
		Assert.assertEquals("Invalid game state in result!", expectedState, responseToValidate.getGameState());
		Assert.assertEquals("Invalid taken last step in result!", takenStepValue, responseToValidate.getStepValue());
	}

}
