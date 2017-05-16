package sintez.blackjack.service;

import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.model.Player;
import sintez.blackjack.model.Transaction;
import sintez.blackjack.model.TransactionType;

import java.util.List;

/**
 * Interface declares public API for {@link sintez.blackjack.model.Player}
 */
public interface PlayerService {

    /**
     * Find all {@link sintez.blackjack.model.Player}
     *
     * @return list of all {@link sintez.blackjack.model.Player}
     */
    List<Player> findAll();


    /**
     * Find {@link sintez.blackjack.model.Player}
     *
     * @param account - account id of the wanted {@link sintez.blackjack.model.Player} entry
     * @return found {@link sintez.blackjack.model.Player} entry
     */
    Player find(long account) throws PlayerNotFoundException;

    /**
     * Check if player have enough money to make a bet
     *
     * @param account - {@link sintez.blackjack.model.Player} entity id
     * @param bet     - demand bet
     * @return true if can make a bet and false otherwise
     */
    boolean canPlay(long account, int bet) throws PlayerNotFoundException;

    /**
     * Add funds to {@link sintez.blackjack.model.Player} entry
     *
     * @param account         - account id of the wanted {@link sintez.blackjack.model.Player} entry
     * @param funds           - amount of funds to add
     * @param transactionType - type of transaction
     * @throws PlayerNotFoundException - if player not found
     */
    void addFunds(long account, int funds, TransactionType transactionType) throws PlayerNotFoundException;

    /**
     * Take funds from {@link sintez.blackjack.model.Player} entry
     *
     * @param account account id of the wanted {@link sintez.blackjack.model.Player} entry
     * @param funds   - amount of funds to take
     * @throws PlayerNotFoundException - if player not found
     */
    void takeFunds(long account, int funds) throws PlayerNotFoundException;

    /**
     * Find all players transactions
     *
     * @param account - player's id
     * @return list of all player's transactions
     * @throws PlayerNotFoundException - if player not found
     */
    List<Transaction> findAllTransactionsByPlayer(long account) throws PlayerNotFoundException;

    /**
     * Find all transactions
     *
     * @return list of all transactions
     */
    List<Transaction> findAllTransactions();
}
