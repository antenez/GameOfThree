package ba.enox.codesample.gameofthree.api;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ba.enox.codesample.gameofthree.interfaces.GameOfThreeGameController;

@RestController
public class GameOfThreeRestAPIController {

	/**
	 * Game controller is planned o be used as centreal point for all games stored and accessible in memory map.
	 * It is considered fact that spring beans are by default singletoon...that means that all sessions will have access to one instance of injected classes
	 * That is reason why we use map and access to particular game instances by key.
	 * This is one of requirements which can be discussed.
	 */
	@Autowired
	@Qualifier("GameOfThreeGameControllerImpl")
	GameOfThreeGameController gameOfThreeGameController;

	@RequestMapping(value = "/test", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> test() {
		System.out.println("+++This is test request mapping ... TODO rest of api implementation!");
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
