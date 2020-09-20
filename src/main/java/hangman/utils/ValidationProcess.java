package hangman.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import hangman.constants.HangmanConstants;
import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.dtos.word.Word;
import hangman.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Validation class
 * 
 * @author ricardo.sampietro
 *
 */
@Component
@Slf4j
public class ValidationProcess {
	
	/**
	 * Pre-validation, verify action, letter and word in request (not null, empty or letter > 1)
	 * 
	 * @param gameRequest
	 * @return
	 * @throws ValidationException
	 */
	public boolean preValidationRequest(GameRequest gameRequest) throws ValidationException {
		
		if(StringUtils.isEmpty(gameRequest.getAction()) 
				|| StringUtils.isEmpty(gameRequest.getLetter()) 
				|| StringUtils.isEmpty(gameRequest.getWord())
				|| gameRequest.getLetter().length() > 1){
			
			log.error("preValidationRequest() - property is null, empty or letter > 1 - action:[{}] - letter:[{}] - word:[{}]", gameRequest.getAction(),
					gameRequest.getLetter(), gameRequest.getWord());
			
			throw new ValidationException();
			
		}
		
		return true;
	}
	
	/**
	 * Validate victory or defeat based in game response
	 * 
	 * @param gameResponse
	 */
	public void validateVictoryOrDefeat(GameResponse gameResponse) {
		gameResponse.setVictory(!gameResponse.getWord().contains(HangmanConstants.UNDERLINE));
		gameResponse.setDefeat(gameResponse.getMissesCount() >= HangmanConstants.MAX_MISSES);
	}
	
	/**
	 * Validate letter, build a GameResponse
	 * 
	 * @param gameRequest
	 * @return
	 * @throws ValidationException
	 */
	public GameResponse validateLetter(GameRequest gameRequest) throws ValidationException {
		log.debug("validateLetter(GameRequest gameRequest) - gameRequest:{}", gameRequest);
		//init setting letter in UpperCase
		gameRequest.setLetter(gameRequest.getLetter().toUpperCase());
		
		String word = gameRequest.getWord();
		String letter = gameRequest.getLetter();
		Integer wordId = gameRequest.getWordId();

		//Validate Character
		List<Integer> validateCharacter = validateCharacter(wordId, letter);

		//Build a initial response
		GameResponse response = new GameResponse();

		response.setWordId(wordId);
		response.setWord(word);
		response.setMisses(gameRequest.getMisses());
		response.setMissesCount(gameRequest.getMissesCount());

		//Missed letter
		if (validateCharacter.isEmpty()) {
			//add missed in response and missesCount			
			String misses = StringUtils.appendIfMissing(gameRequest.getMisses(), HangmanConstants.SPACE + letter);

			response.setMisses(misses);
			response.setMissesCount(misses.replaceAll(" ", "").length());
			
			return response;
		}

		//Success letter
		StringBuilder wordBuilder = new StringBuilder(word);

		//ajust Word in response: _ _ A _ D _
		for (Integer index : validateCharacter) {

			//how the word contains a space every "_" we do * 2
			if (index != 0) {
				index = index * 2;
			}

			wordBuilder.setCharAt(index, letter.charAt(0));
		}

		response.setWord(wordBuilder.toString());

		return response;

	}

	/**
	 * Validate character in word, using wordId to get word. Return a List of Integer with indexes
	 * 
	 * @param wordId
	 * @param character
	 * @return
	 * @throws ValidationException
	 */
	public List<Integer> validateCharacter(Integer wordId, String character) throws ValidationException {
		log.debug("validateCharacter(Integer wordId, String character) wordId:{} - character:{}", wordId, character);
		List<Integer> indexes = new ArrayList<>();

		HangmanWordSingleton hangmanWords = HangmanWordSingleton.getInstance();

		Word wordObj = hangmanWords.getWordMap().get(wordId);
		
		//not found word, throw exception
		if (wordObj == null) {
			log.error("valideCharacter() - not found word by wordId - wordId[{}]", wordId);
			throw new ValidationException();
		}

		String word = wordObj.getWord();

		/*
		 * Get indexes in word
		 */
		int index = 0;
		while (index != -1) {
			index = word.indexOf(character, index);
			if (index != -1) {
				indexes.add(index);
				index++;
			}
		}

		return indexes;

	}

}
