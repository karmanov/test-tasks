package sintez.blackjack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.model.Player;
import sintez.blackjack.model.Transaction;
import sintez.blackjack.model.TransactionType;
import sintez.blackjack.repository.PlayerRepository;
import sintez.blackjack.repository.TransactionRepository;

import java.util.List;

/**
 * Implementation of {@link sintez.blackjack.service.PlayerService} interface
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private PlayerRepository playerRepository;

    private TransactionRepository transactionRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> findAll() {
        LOGGER.info("Searching all players...");
        return (List<Player>) playerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player find(long account) throws PlayerNotFoundException {
        LOGGER.info("Finding a player with id: {}", account);

        Player player = playerRepository.findOne(account);

        if (player == null) {
            throw new PlayerNotFoundException("No player found with account id: " + account);
        }
        return player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canPlay(long account, int bet) throws PlayerNotFoundException {
        LOGGER.info("Checking is player with id {} can make a bet in amount {} and play", account, bet);
        Player player = find(account);
        int currentBalance = player.getBalance();
        if (currentBalance > bet) {
            int newBalance = currentBalance - bet;
            player.setBalance(newBalance);
            playerRepository.save(player);
            Transaction transaction = new Transaction(player.getId(), TransactionType.BET, bet);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFunds(long account, int funds, TransactionType transactionType) throws PlayerNotFoundException {
        Player player = find(account);
        int currentBalance = player.getBalance();
        int newBalance = currentBalance + funds;
        player.setBalance(newBalance);
        Transaction transaction = new Transaction(player.getId(), transactionType, funds);
        transactionRepository.save(transaction);
        playerRepository.save(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void takeFunds(long account, int funds) throws PlayerNotFoundException {
        Player player = find(account);
        int currentBalance = player.getBalance();
        int newBalance = currentBalance - funds;
        player.setBalance(newBalance);
        Transaction transaction = new Transaction(player.getId(), TransactionType.BET, funds);
        transactionRepository.save(transaction);
        playerRepository.save(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> findAllTransactionsByPlayer(long account) throws PlayerNotFoundException {
        List<Transaction> playerTransactions = (List<Transaction>) transactionRepository.findByPlayerId(account);
        if (playerTransactions != null && !playerTransactions.isEmpty()) {
            return playerTransactions;
        } else {
            throw new PlayerNotFoundException("Player not found");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> findAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }


}
