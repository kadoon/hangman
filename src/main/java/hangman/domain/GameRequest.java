package hangman.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Game request received by the front end
 * 
 * @author ricardo.sampietro
 *
 */
@Data
@NoArgsConstructor
public class GameRequest {

	private String letter;
	private Integer wordId;
	private String word;
	private String misses;
	private Integer missesCount;
	private String lettersUsed;
	private String action;	

}
