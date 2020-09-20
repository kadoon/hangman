package hangman.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.exceptions.ValidationException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ValidationProcess.class })
public class ValidationProcessTest {

	@Autowired
	private ValidationProcess validationProcess;
	
	@Test
	public void preValidationRequestSuccess() throws ValidationException {
		GameRequest gameRequest = new GameRequest();
		
		gameRequest.setAction("test");
		gameRequest.setWord("test");
		gameRequest.setLetter("test");
		
		boolean preValidationRequest = validationProcess.preValidationRequest(gameRequest);
		
		assertTrue(preValidationRequest);
		
	}
	
	@Test(expected = ValidationException.class)
	public void preValidationRequestThrow() throws ValidationException {
		GameRequest gameRequest = new GameRequest();		
		validationProcess.preValidationRequest(gameRequest);
	}
	
	@Test
	public void validateVictoryOrDefeatIsVictory() {
		GameResponse gameResponse = new GameResponse();
		
		gameResponse.setWord("A B C");
		gameResponse.setMissesCount(0);
		
		validationProcess.validateVictoryOrDefeat(gameResponse);
		
		assertTrue(gameResponse.isVictory());
		
	}
	
	@Test
	public void validateVictoryOrDefeatIsDefeat() {
		GameResponse gameResponse = new GameResponse();
		
		gameResponse.setWord("A B C");
		gameResponse.setMissesCount(6);
		
		validationProcess.validateVictoryOrDefeat(gameResponse);
		
		assertTrue(gameResponse.isDefeat());
		
	}
	
	@Test
	public void validateLetterSuccess() throws ValidationException {
		GameRequest request = new GameRequest();
		
		request.setLetter("D");
		request.setMisses("");
		request.setMissesCount(0);
		request.setWordId(0);
		request.setWord("_ _ _ _");
		
		GameResponse validateLetter = validationProcess.validateLetter(request);
		
		assertEquals("D _ _ _", validateLetter.getWord());
		
	}
	
	@Test
	public void validateLetterNotFound() throws ValidationException {
		GameRequest request = new GameRequest();
		
		request.setLetter("A");
		request.setMisses("");
		request.setMissesCount(0);
		request.setWordId(0);
		request.setWord("_ _ _ _");
		
		GameResponse validateLetter = validationProcess.validateLetter(request);
		
		assertEquals("_ _ _ _", validateLetter.getWord());
		assertEquals(new Integer(1), validateLetter.getMissesCount());
		assertEquals(" A", validateLetter.getMisses());
		
	}
	
	@Test
	public void validateCharacterSuccess() throws ValidationException {
		List<Integer> validateCharacter = validationProcess.validateCharacter(0, "D");
		
		assertTrue(!validateCharacter.isEmpty());
		
	}
	
	@Test
	public void validateCharacterNotFound() throws ValidationException {
		List<Integer> validateCharacter = validationProcess.validateCharacter(0, "A");
		
		assertTrue(validateCharacter.isEmpty());
		
	}
	
	@Test(expected = ValidationException.class)
	public void validateCharacterFailed() throws ValidationException {
		validationProcess.validateCharacter(20, "A");
	}

}

