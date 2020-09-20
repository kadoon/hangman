package hangman.dtos.word;

import lombok.Data;

/**
 * Internal DTO used to store words
 * 
 * @author ricardo.sampietro
 *
 */
@Data
public class Word {

	private String word;
	private int size;
	
}
