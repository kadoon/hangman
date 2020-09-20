package hangman.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import hangman.constants.HangmanConstants;
import hangman.dtos.word.HangmanXML;
import hangman.dtos.word.Word;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Singleton "Initialization-on-demand holder"
 * <p>
 * Read file and Populate a MAP with words
 * 
 * @author ricardo.sampietro
 */
@Data
@Slf4j
public class HangmanWordSingleton {
	
	private Map<Integer, Word> wordMap = new HashMap<>();
	
	/*
	 * Constructor
	 */
	/**
	 * Read a file and populate a MAP
	 */
	private HangmanWordSingleton() {
		try {
			
			XmlMapper xmlMapper = new XmlMapper();			
			
			String stringFile = readFile(HangmanConstants.WORD_FILE_PATH);
			HangmanXML hangmanXml = xmlMapper.readValue(stringFile, HangmanXML.class);
			List<String> wordList = hangmanXml.getWordList();
			
			for(int i=0; i< wordList.size(); i++) {
				String wordString = wordList.get(i);
				
				Word word = new Word();
				word.setWord(wordString);
				word.setSize(wordString.length());
				
				wordMap.put(i, word);
			}									
			
		}catch(Exception e) {
			log.error("HangmanWordSingleton() - error in build wordMap", e);
		}

	}

	/*
	 * Statics
	 */
    private static class LazyHolder {
        static final HangmanWordSingleton INSTANCE = new HangmanWordSingleton();
    }

    /**
     * Instance to singleton
     * 
     * @return
     */
    public static HangmanWordSingleton getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    /*
     * Internal
     */
    /**
     * Read File
     * 
     * @param filePath
     * @return
     * @throws IOException
     */
	private String readFile(String filePath) throws IOException {
		File input = new DefaultResourceLoader().getResource(filePath).getFile();
		return new String(Files.readAllBytes(Paths.get(input.getPath())));
	}

}