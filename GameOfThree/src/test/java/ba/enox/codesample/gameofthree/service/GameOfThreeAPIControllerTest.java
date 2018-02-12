package ba.enox.codesample.gameofthree.service;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ba.enox.codesample.gameofthree.api.GameOfThreeAPIController;
import ba.enox.codesample.gameofthree.model.APICreateGameRequest;
import ba.enox.codesample.gameofthree.model.APIPlayGameStepRequest;
import ba.enox.codesample.gameofthree.model.GameOfThreePlayer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameOfThreeAPIControllerTest {

	private final Logger LOG = LoggerFactory.getLogger(GameOfThreeAPIControllerTest.class);

	GameOfThreePlayer playerOne = new GameOfThreePlayer("playerOne", false);
	GameOfThreePlayer playerTwo = new GameOfThreePlayer("playerTwo", false);

	@InjectMocks
	private GameOfThreeAPIController controller;

	@Autowired
	private MockMvc mockMvc;

	// @Before
	// public void initTests() {
	// MockitoAnnotations.initMocks(this);
	// mvc = MockMvcBuilders.webAppContextSetup(context).build();
	// }

	@Before
	public void beforeTests() {
		assertTrue("Controller need to be initialized ", controller != null);
		MockitoAnnotations.initMocks(this);

		// ConcurrentNavigableMap<Long, List<Transaction>> cnm = new
		// ConcurrentSkipListMap<>();
		//
		//
		// List<Transaction> listOneMinnuteAfter = new ArrayList<>();
		// Long oneMinAfter = ((Instant.now().getEpochSecond()+60)*1000);//*1000
		// to add three time 0 as in sample
		// listOneMinnuteAfter.add(new Transaction(10.0D, oneMinAfter));
		// listOneMinnuteAfter.add(new Transaction(20.0D, oneMinAfter));
		// cnm.put(oneMinAfter, listOneMinnuteAfter);
		//
		// System.out.println("one min after "+oneMinAfter);
		// Mockito.when(transactionsHome.getTransactions()).thenReturn(cnm);
	}

	@Test
	public void createGameCheckListDeleteGameTest() throws Exception {

		String gameName = "gameOne";
		APICreateGameRequest game = new APICreateGameRequest(5, gameName, playerOne.getName(), false,
				playerTwo.getName(), false);
		String request = asJsonString(game);
		LOG.info("Generated request " + request);

		// Check create api
		mockMvc.perform(post("/gameOfThree").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isCreated());

		// check list api
		mockMvc.perform(get("/gameOfThree").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(1))).andExpect(jsonPath("$.[0]", is(gameName)));

		String gameNameTwo = "gameTwo";
		game = new APICreateGameRequest(5, gameNameTwo, playerOne.getName(), false, playerTwo.getName(), false);
		request = asJsonString(game);
		LOG.info("Generated request " + request);

		// Check create api
		mockMvc.perform(post("/gameOfThree").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isCreated());

		// check list api
		mockMvc.perform(get("/gameOfThree").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2))).andExpect(jsonPath("$.[0]", is(gameName)))
				.andExpect(jsonPath("$.[1]", is(gameNameTwo)));

		// delete first game
		mockMvc.perform(delete("/gameOfThree/" + gameName).contentType(APPLICATION_JSON)).andExpect(status().isOk());

		// check list after delete
		mockMvc.perform(get("/gameOfThree").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(1))).andExpect(jsonPath("$.[0]", is(gameNameTwo)));

		// delete lefted game
		deleteGameByName(gameNameTwo);

	}

	@Test
	public void onePlayerGameWithAutomaticTest() throws Exception {

		String gameName = "gameOne";
		APICreateGameRequest game = new APICreateGameRequest(56, gameName, playerOne.getName(), false, null, true);
		String request = asJsonString(game);
		LOG.info("Generated request " + request);

		String automaticPalyerTwoName = "AutomaticPlayerTwo";

		// Check create api initialize game on 56 for player one and player two
		// automatically reply with step 1 to get 19 as result
		mockMvc.perform(post("/gameOfThree").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.[0].playerName", is(automaticPalyerTwoName)))
				.andExpect(jsonPath("$.[0].stepValue", is(1)))
				.andExpect(jsonPath("$.[0].gameState", is(19)));
		
		APIPlayGameStepRequest playGameStepRequest = new APIPlayGameStepRequest(-1, playerOne.getName());
		request = asJsonString(playGameStepRequest);
		//Play step
		mockMvc.perform(put("/gameOfThree/" + gameName + "/playStep").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.[0].playerName", is(playerOne.getName())))
				.andExpect(jsonPath("$.[0].stepValue", is(-1))).andExpect(jsonPath("$.[0].gameState", is(6)))

				.andExpect(jsonPath("$.[1].playerName", is(automaticPalyerTwoName)))
				.andExpect(jsonPath("$.[1].stepValue", is(0))).andExpect(jsonPath("$.[1].gameState", is(2)))		;
		
		
		playGameStepRequest = new APIPlayGameStepRequest(1, playerOne.getName());
		request = asJsonString(playGameStepRequest);
		//Play step
		mockMvc.perform(put("/gameOfThree/" + gameName + "/playStep").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isAccepted())
				.andExpect(jsonPath("$.[0].playerName", is(playerOne.getName())))
				.andExpect(jsonPath("$.[0].stepValue", is(1)))
				.andExpect(jsonPath("$.[0].gameState", is(1)))
				.andExpect(jsonPath("$.*", hasSize(1)));

		// Delete created games
		deleteGameByName(gameName);

	}
	
	
	@Test
	public void automaticPlayerGameTest() throws Exception {

		String gameName = "gameOne";
		APICreateGameRequest game = new APICreateGameRequest(56, gameName, null, true, null, true);
		String request = asJsonString(game);
		LOG.info("Generated request " + request);
		String automaticPalyerOneName = "AutomaticPlayerOne";
		String automaticPalyerTwoName = "AutomaticPlayerTwo";

		// Check create api initialize game on 56 for player one and player two
		// automatically reply with step 1 to get 19 as result
		mockMvc.perform(post("/gameOfThree").contentType(APPLICATION_JSON).content(request))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.[0].playerName", is(automaticPalyerTwoName)))
				.andExpect(jsonPath("$.[0].stepValue", is(1)))
				.andExpect(jsonPath("$.[0].gameState", is(19)))
		
		
				.andExpect(jsonPath("$.[1].playerName", is(automaticPalyerOneName)))
				.andExpect(jsonPath("$.[1].stepValue", is(-1)))
				.andExpect(jsonPath("$.[1].gameState", is(6)))

				.andExpect(jsonPath("$.[2].playerName", is(automaticPalyerTwoName)))
				.andExpect(jsonPath("$.[2].stepValue", is(0)))
				.andExpect(jsonPath("$.[2].gameState", is(2)))		
				
				.andExpect(jsonPath("$.[3].playerName", is(automaticPalyerOneName)))
				.andExpect(jsonPath("$.[3].stepValue", is(1)))
				.andExpect(jsonPath("$.[3].gameState", is(1)))
				.andExpect(jsonPath("$.*", hasSize(4)));

		// Delete created games
		deleteGameByName(gameName);

	}
	

	// TODO covver exception casses by unit tests.
	/*
	 * Exception response example:
	{
    "timestamp": 1518439952971,
    "status": 500,
    "error": "Internal Server Error",
    "exception": "java.lang.IllegalArgumentException",
    "message": "Game with key name gameOne15 already exists!",
    "path": "/gameOfThree"
	}
	 * 
	 */

	private void deleteGameByName(String gameNameToDelete) {
		// delete first game
		try {
			mockMvc.perform(delete("/gameOfThree/" + gameNameToDelete).contentType(APPLICATION_JSON))
					.andExpect(status().isOk());
		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	private byte[] toJson(Object r) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(r).getBytes();
	}

	public String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}