package sintez.blackjack.web.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.model.Player;
import sintez.blackjack.model.Transaction;
import sintez.blackjack.model.TransactionType;
import sintez.blackjack.service.PlayerService;

import java.util.List;

/**
 * Controller handles all players and transactions related requests
 */
@RestController("/blackjack/players")
public class PlayerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * Handle "Find all players" request
     *
     * @return list of all players
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Player> findAll() {
        LOGGER.info("Receive 'Find all' players request");
        return playerService.findAll();
    }

    /**
     * Handle "Add Funds" request
     *
     * @param account - account id to add funds
     * @param funds   - amount of funds to add
     * @throws PlayerNotFoundException if player could not be found by given id
     */
    @RequestMapping(value = "/blackjack/players/{account}/{funds}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addFunds(@PathVariable Long account, @PathVariable int funds) throws PlayerNotFoundException {
        LOGGER.info("Receive 'Add funds' request for account {} to add {} funds", account, funds);
        playerService.addFunds(account, funds, TransactionType.INCOME);
    }

    /**
     * Handle "Find Player by id" request
     *
     * @param account - account id
     * @throws PlayerNotFoundException if player could not be found by given id
     */
    @RequestMapping(value = "/blackjack/players/{account}", method = RequestMethod.GET)
    public Player findById(@PathVariable Long account) throws PlayerNotFoundException {
        LOGGER.info("Receive 'Find Player By ID' request for account {}", account);
        return playerService.find(account);
    }

    /**
     * Handle "Show players transactions history" request
     *
     * @param account - account id
     * @return list of all players transactions
     * @throws PlayerNotFoundException
     */
    @RequestMapping(value = "/blackjack/players/{account}/transactions", method = RequestMethod.GET)
    public List<Transaction> showTransactionsHistory(@PathVariable Long account) throws
            PlayerNotFoundException {
        LOGGER.info("Receive 'Get player transactions' request for account {}", account);
        return playerService.findAllTransactionsByPlayer(account);
    }

    /**
     * Handle "Find all transactions" request
     *
     * @return list of all transactions
     */
    @RequestMapping(value = "/blackjack/transactions", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> findAllTransactions() {
        LOGGER.info("Receive 'Find all transactions' request");
        return playerService.findAllTransactions();
    }

}
