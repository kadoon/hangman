package hangman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hangman.domain.GameRequest;
import hangman.domain.GameResponse;
import hangman.services.HangmanService;
import hangman.utils.HangmanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class
 * 
 * @author ricardo.sampietro
 *
 */
@Controller
@Slf4j
public class HangmanController {

	@Autowired
	private HangmanService service;
	
	@Autowired
	private HangmanUtil hangmanUtil;
	
	/**
	 * GET route return a ModelAndView initial
	 * 
	 * @param model
	 * @param gameRequest
	 * @return
	 */
    @RequestMapping("/")
    public ModelAndView getGame(ModelAndView model, GameRequest gameRequest) {
    	log.info("getGame() - model:{} - request:{}", model, gameRequest);
        
        hangmanUtil.fillModelAndView(model, gameRequest, new GameResponse());
        
        return model;
    }
    
    /**
     * POST route, contain a core game, validated by the action passed in the request. Return a ModelAndView 
     * 
     * @param model
     * @param gameRequest
     * @return
     */
    @PostMapping("/")
    public ModelAndView postGame(ModelAndView model, GameRequest gameRequest) {
    	log.info("postGame() - model:{} - request:{}", model, gameRequest);
    	
    	return service.gameCore(model, gameRequest);
    }
	
}
