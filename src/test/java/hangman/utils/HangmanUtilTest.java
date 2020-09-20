package hangman.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import hangman.constants.HangmanConstants;
import hangman.domain.GameRequest;
import hangman.domain.GameResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { HangmanUtil.class })
public class HangmanUtilTest {

	@Autowired
	private HangmanUtil hangmanUtil;
	
	@Test
	public void fillModelAndViewSuccess() {		
		
		ModelAndView model = new ModelAndView();
		
		hangmanUtil.fillModelAndView(model, new GameRequest(), new GameResponse());

		assertEquals(HangmanConstants.MODEL_AND_VIEL_GAME_HANGMAN, model.getViewName());
		assertTrue(model.getModelMap().containsKey(HangmanConstants.MODEL_AND_VIEL_GAME));
		assertTrue(model.getModelMap().containsKey(HangmanConstants.MODEL_AND_VIEL_GAME_REQUEST));		
	}
	
	@Test(expected = Exception.class)
	public void fillModelAndViewFailed() {		
		
		hangmanUtil.fillModelAndView(null, null, null);
	
	}
	
}
