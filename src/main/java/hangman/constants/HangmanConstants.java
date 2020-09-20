package hangman.constants;

/**
 * Utility class with constants
 * 
 * @author ricardo.sampietro
 *
 */
public class HangmanConstants {
	
	private HangmanConstants() {
		throw new IllegalStateException("HangmanConstants() - Utility class");
	}
	
	//words file path
	public static final String WORD_FILE_PATH = "classpath:/files/words.xml";
	
	//Objects to ModelAndView
	public static final String NEW_GAME = "newGame";
	public static final String SUBMIT_LETTER = "submitLetter";
	
	//General
	public static final String UNDERLINE = "_";
	public static final String SPACE = " ";
	public static final String UNDERLINE_AND_SPACE = "_ ";
	public static final Integer MAX_MISSES = 6;
	public static final String UTF_8 = "UTF-8";
	
	//Erros
	public static final String GENERAL_ERROR_MESSAGE = "An error has occurred, please try again.";
	
	//ModelAndView
	public static final String MODEL_AND_VIEL_GAME = "game";
	public static final String MODEL_AND_VIEL_GAME_REQUEST = "gameRequest";
	public static final String MODEL_AND_VIEL_GAME_HANGMAN = "hangman";
	
}
