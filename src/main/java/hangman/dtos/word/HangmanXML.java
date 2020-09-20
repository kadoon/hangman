package hangman.dtos.word;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO for the xml file
 * 
 * @author ricardo.sampietro
 *
 */
@Data
public class HangmanXML {

	@JsonProperty(value = "word_list")
	private List<String> wordList;
	
}
