package sintez.blackjack.web.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sintez.blackjack.exception.BlackjackException;
import sintez.blackjack.game.GameResponse;
import sintez.blackjack.service.BlackjackService;

@RestController
@RequestMapping(value = "/blackjack")
public class BlackjackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlackjackController.class);

    private BlackjackService blackjackService;

    @Autowired
    public BlackjackController(BlackjackService blackjackService) {
        this.blackjackService = blackjackService;
    }

    /**
     * Handle 'Deal' request
     *
     * @param bet     -
     * @param account {@link sintez.blackjack.model.Player} id
     * @return {@link sintez.blackjack.game.GameContext} entity - representation of current game state:
     * 1. Players 'hand'
     * 2. Dealer visiable 'hand'
     * 3. Deal bet
     * 4. Current players score
     * 5. Game status
     * @throws BlackjackException - if new deal could not be started
     */
    @RequestMapping(value = "/deal/{account}/{bet}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse deal(@PathVariable Long account, @PathVariable int bet) throws BlackjackException {
        LOGGER.info("Receive request to start blackjack game for account {} with bet: {}", account, bet);
        return blackjackService.deal(account, bet);
    }

    /**
     * Handle 'STAND' request from player
     *
     * @param gameId - id of the deal where player wants to stand
     * @return current deal state
     */
    @RequestMapping(value = "{gameId}/stand", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse stand(@PathVariable String gameId) throws BlackjackException {
        LOGGER.info("Receive 'STAND' request for deal {}", gameId);
        return blackjackService.stand(gameId);
    }

    /**
     * Handle 'HIT' request from player
     *
     * @param gameId - id of the deal where player wants to stand
     * @return current deal state
     */
    @RequestMapping(value = "{gameId}/hit", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public GameResponse hit(@PathVariable String gameId) throws BlackjackException {
        LOGGER.info("Receive 'HIT' request for deal {}", gameId);
        return blackjackService.hit(gameId);
    }

}
