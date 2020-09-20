package hangman.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import hangman.constants.HangmanConstants;
import hangman.domain.GameRequest;
import hangman.domain.GameResponse;

/**
 * General utilities
 * 
 * @author ricardo.sampietro
 *
 */
@Component
public class HangmanUtil {

	/**
	 * Fill ModelAndView with requests and response
	 * 
	 * @param model
	 * @param gameRequest
	 * @param gameResponse
	 */
	public void fillModelAndView(ModelAndView model, GameRequest gameRequest, GameResponse gameResponse) {		
		model.addObject(HangmanConstants.MODEL_AND_VIEL_GAME, gameResponse);
		model.addObject(HangmanConstants.MODEL_AND_VIEL_GAME_REQUEST, gameRequest);
		model.setViewName(HangmanConstants.MODEL_AND_VIEL_GAME_HANGMAN);
	}
	
	/**
	 * Fill ModelAndView with general error (setting error message  in response)
	 * 
	 * @param model
	 * @param gameRequest
	 */
	public void fillGeneralErrorModel(ModelAndView model, GameRequest gameRequest) {
		GameResponse response = new GameResponse();			
		response.setErrorMessage(HangmanConstants.GENERAL_ERROR_MESSAGE);	
		
		fillModelAndView(model, gameRequest, response);
	}	
	
}
