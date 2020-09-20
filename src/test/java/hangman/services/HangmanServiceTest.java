package hangman.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import hangman.constants.HangmanConstants;
import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.exceptions.ValidationException;
import hangman.utils.HangmanUtil;
import hangman.utils.ValidationProcess;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { HangmanService.class, ValidationProcess.class, HangmanUtil.class})
public class HangmanServiceTest {

	@Autowired
	private HangmanService service;
	
	@MockBean
	private ValidationProcess validationProcess;
	
	@MockBean
	private HangmanUtil hangmanUtil;
	
	@Test
	public void generateWordSuccess() {
		GameResponse generateWord = service.generateWord();
				
		assertNotNull(generateWord.getWordId());
		assertTrue(StringUtils.isNotEmpty(generateWord.getWord()));		
	}
	
	@Test
	public void submitLetterSuccess() throws ValidationException {
		ModelAndView model = new ModelAndView();
		GameRequest gameRequest = new GameRequest();
		gameRequest.setAction(HangmanConstants.NEW_GAME);		
		
		ModelAndView submitLetter = service.submitLetter(model, gameRequest);
		
		assertNotNull(submitLetter);
		verify(validationProcess, times(1)).preValidationRequest(gameRequest);
		verify(validationProcess, times(1)).validateLetter(gameRequest);
		verify(validationProcess, times(1)).validateVictoryOrDefeat(null);
		verify(hangmanUtil, times(1)).fillModelAndView(model, gameRequest, null);
		
	}
	
	@Test
	public void gameCoreSuccess() {
		ModelAndView model = new ModelAndView();
		GameRequest gameRequest = new GameRequest();
		gameRequest.setAction(HangmanConstants.NEW_GAME);		
		
		ModelAndView gameCore = service.gameCore(model, gameRequest);
		
		assertNotNull(gameCore);
		
	}
	
	@Test
	public void newGameSuccess() {
		ModelAndView model = new ModelAndView();
		GameRequest gameRequest = new GameRequest();
		gameRequest.setAction(HangmanConstants.NEW_GAME);		
		
		ModelAndView newGame = service.newGame(model, gameRequest);
		
		assertNotNull(newGame);
		
	}
	
}
