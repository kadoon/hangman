package hangman.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.services.HangmanService;
import hangman.utils.HangmanUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { HangmanController.class, HangmanService.class, HangmanUtil.class})
public class HangmanControllerTest {

	@Autowired
	private HangmanController controller;
	
	@MockBean
	private HangmanService service;
	
	@MockBean
	private HangmanUtil hangmanUtil;
	
	@Test
	public void getGameSuccess() {
		ModelAndView model = new ModelAndView();
		GameRequest gameRequest = new GameRequest();
		ModelAndView game = controller.getGame(model, gameRequest);
		
		assertNotNull(game);
		verify(hangmanUtil, times(1)).fillModelAndView(model, gameRequest, new GameResponse());
		
	}
	
	@Test
	public void postGameSuccess() {
		ModelAndView model = new ModelAndView();
		GameRequest gameRequest = new GameRequest();
		controller.postGame(model, gameRequest);
		
		verify(service, times(1)).gameCore(model, gameRequest);
		
	}
	
}
