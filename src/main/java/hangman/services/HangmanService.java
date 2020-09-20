package hangman.services;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import hangman.constants.HangmanConstants;
import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.exceptions.ValidationException;
import hangman.utils.HangmanUtil;
import hangman.utils.HangmanWordSingleton;
import hangman.utils.ValidationProcess;
import lombok.extern.slf4j.Slf4j;

/**
 * Game Service
 * 
 * @author ricardo.sampietro
 *
 */
@Service
@Slf4j
public class HangmanService {

	/*
	 * Validation
	 */
	@Autowired
	private ValidationProcess validationProcess;

	/*
	 * Utils
	 */
	@Autowired
	private HangmanUtil hangmanUtil;

	/**
	 * Game core, based on the action received performs some action
	 * 
	 * @param model
	 * @param gameRequest
	 * @return
	 */
	public ModelAndView gameCore(ModelAndView model, GameRequest gameRequest) {
		log.debug("gameCore(ModelAndView model, GameRequest gameRequest) - model:{} - gameRequest:{}", model, gameRequest); 
		try {
			String action = gameRequest.getAction();

			/*
			 * NEW GAME
			 */
			if(HangmanConstants.NEW_GAME.equalsIgnoreCase(action)) {
				return newGame(model, gameRequest);				
			}
			
			/*
			 * SUBMIT_LETTER
			 */
			if(HangmanConstants.SUBMIT_LETTER.equalsIgnoreCase(action)) {
				return submitLetter(model, gameRequest);				
			}

		} catch (Exception e) {
			log.error("gameCore() - general error: {}", e);
			hangmanUtil.fillGeneralErrorModel(model, gameRequest);
		}

		return model;

	}

	/*
	 * INTERNAL
	 */
	
	/**
	 * Generate a word and fill a ModelAndView
	 * 
	 * @param model
	 * @param gameRequest
	 * @return
	 */
	protected ModelAndView newGame(ModelAndView model, GameRequest gameRequest) {
		log.debug("newGame(ModelAndView model, GameRequest gameRequest) - model:{} - gameRequest:{}", model, gameRequest);
		GameResponse generateWord = generateWord();

		hangmanUtil.fillModelAndView(model, gameRequest, generateWord);

		log.debug("newGame() - return ModelAndView: {}", model);
		
		return model;
	}
	
	/**
	 * Generate wordId, word and initial MissedCount(0)
	 * <p>
	 * WordId is randomly generated based on the total number of words
	 * <p>
	 * Word is created following the format: _ _ _ _
	 * 
	 * @return
	 */
	protected GameResponse generateWord() {
		//get singleton with all words
		HangmanWordSingleton hangmanWords = HangmanWordSingleton.getInstance();

		int totalWords = hangmanWords.getWordMap().size();
		//randomly
		int wordId = ThreadLocalRandom.current().nextInt(0, totalWords);
		int wordSize = hangmanWords.getWordMap().get(wordId).getSize();

		//build a GameResponse
		GameResponse response = new GameResponse();

		response.setWordId(wordId);
		//created Word in format:  _ _ _ _
		response.setWord(StringUtils.repeat(HangmanConstants.UNDERLINE_AND_SPACE, wordSize));
		response.setMissesCount(0);

		log.info("generateWord() - wordId: {} - word: {}", wordId, hangmanWords.getWordMap().get(wordId));

		return response;

	}

	/**
	 * Validate request, process letter, validate victory or defeat and populates the process-based ModelAndView
	 * 
	 * @param model
	 * @param gameRequest
	 * @return
	 */
	protected ModelAndView submitLetter(ModelAndView model, GameRequest gameRequest) {
		log.debug("submitLetter(ModelAndView model, GameRequest gameRequest) - model:{} - gameRequest:{}", model, gameRequest);
		try {
			//pre-validation
			validationProcess.preValidationRequest(gameRequest);

			//validation letter and populate response
			GameResponse gameResponse = validationProcess.validateLetter(gameRequest);

			//validation victory or defeat
			validationProcess.validateVictoryOrDefeat(gameResponse);

			//fill ModelAndView
			hangmanUtil.fillModelAndView(model, gameRequest, gameResponse);

		} catch (ValidationException ve) {
			hangmanUtil.fillGeneralErrorModel(model, gameRequest);
		}
		
		log.debug("submitLetter() - return ModelAndView:{}", model);

		return model;
	}


}
