package hangman.domain;

import lombok.Data;

/**
 * Game response sent from the back end
 * 
 * @author ricardo.sampietro
 *
 */
@Data
public class GameResponse {

	private Integer wordId;	
	private String word;	
	private String lettersUsed;
	private String misses;
	private Integer missesCount;	
	private boolean victory;
	private boolean defeat;	
	private String errorMessage;
	
}
