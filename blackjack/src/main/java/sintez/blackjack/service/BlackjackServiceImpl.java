package sintez.blackjack.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sintez.blackjack.exception.BlackjackException;
import sintez.blackjack.exception.GameContextNotFoundException;
import sintez.blackjack.exception.GameCouldNotBeStartedException;
import sintez.blackjack.exception.PlayerNotFoundException;
import sintez.blackjack.game.GameContext;
import sintez.blackjack.game.GameResponse;
import sintez.blackjack.game.GameResult;
import sintez.blackjack.game.card.Card;
import sintez.blackjack.model.TransactionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Blackjack game API
 */
@Service
public class BlackjackServiceImpl implements BlackjackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlackjackServiceImpl.class);

    private PlayerService playerService;

    private DeckService deckService;

    private Map<String, GameContext> activeGames = new HashMap<String, GameContext>();

    @Autowired
    public BlackjackServiceImpl(PlayerService playerService, DeckService deckService) {
        this.playerService = playerService;
        this.deckService = deckService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameResponse deal(long account, int bet) throws BlackjackException {
        LOGGER.info("Starting new blackjack game for account {} with bet {}", account, bet);
        if (playerService.canPlay(account, bet)) {
            GameContext gameContext = createNewGame(account, bet);
            return buildGameResponse(gameContext);
        } else {
            throw new GameCouldNotBeStartedException("Game could not be started, because player with id: " + account
                    + ", is not allowed to play");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameResponse stand(String gameId) throws BlackjackException {
        if (activeGames.containsKey(gameId)) {
            GameContext gameContext = activeGames.get(gameId);
            LOGGER.info("Found deal. Executing STAND command");
            dealerTurn(gameContext);
            GameResponse gameResponse = buildGameResponse(gameContext);
            gameResponse.setGameResult(gameContext.getGameResult());
            return gameResponse;
        } else {
            throw new GameContextNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameResponse hit(String gameId) throws BlackjackException {
        if (activeGames.containsKey(gameId)) {
            GameContext gameContext = activeGames.get(gameId);
            LOGGER.info("Found deal. Executing HIT command");
            gameContext.getPlayerCards().add(deckService.dealNextCard(gameContext.getDeck()));
            if (gameContext.isPlayerBusted()) {
                return playerLost(gameContext);
            } else if (gameContext.getPlayerPoints() == 21) {
                return dealerTurn(gameContext);
            }
            return buildGameResponse(gameContext);
        } else {
            throw new GameContextNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, GameContext> getActiveGames() {
        return activeGames;
    }

    /**
     * Simulate dealer's behavior and execute appropriated methods base of the game state
     *
     * @param gameContext - {@link sintez.blackjack.game.GameContext} of the current game
     */
    private GameResponse dealerTurn(GameContext gameContext) throws PlayerNotFoundException {
        LOGGER.info("Simulate dealer's behavior...");
        while (gameContext.getDealerPoints() < 17) {
            gameContext.getDealerCards().add(deckService.dealNextCard(gameContext.getDeck()));
        }
        if (gameContext.getGameResult() == GameResult.WIN) {
            activeGames.remove(gameContext.getId());
            return playerWins(gameContext);
        } else if (gameContext.getGameResult() == GameResult.LOOSE) {
            activeGames.remove(gameContext.getId());
            return playerLost(gameContext);
        } else {
            activeGames.remove(gameContext.getId());
            return push(gameContext);
        }
    }

    /**
     * @param gameContext - {@link sintez.blackjack.game.GameContext} of the current game
     */
    private GameResponse playerLost(GameContext gameContext) throws PlayerNotFoundException {
        GameResponse gameResponse = buildGameResponse(gameContext);
        gameResponse.setGameResult(gameContext.getGameResult());
        return gameResponse;
    }

    /**
     * Add win bonus to the players balance
     *
     * @param gameContext - {@link sintez.blackjack.game.GameContext} of the current game
     */
    private GameResponse playerWins(GameContext gameContext) throws PlayerNotFoundException {
        int bet = gameContext.getBet();
        int winBonus = bet * 2;
        playerService.addFunds(gameContext.getAccount(), winBonus, TransactionType.WIN);
        GameResponse gameResponse = buildGameResponse(gameContext);
        gameResponse.setGameResult(gameContext.getGameResult());
        return gameResponse;
    }

    /**
     * @param gameContext - {@link sintez.blackjack.game.GameContext} of the current game
     */
    private void evaluatePlayersBlackjack(GameContext gameContext) throws PlayerNotFoundException {
        int bet = gameContext.getBet();
        double blackjackBonus = bet * 1.5;
        int winBonus = (int) (blackjackBonus + bet);
        playerService.addFunds(gameContext.getAccount(), winBonus, TransactionType.WIN);
        GameResponse gameResponse = buildGameResponse(gameContext);
        gameResponse.setGameResult(gameContext.getGameResult());
    }

    /**
     * Return bet to the player
     *
     * @param gameContext - {@link sintez.blackjack.game.GameContext} of the current game
     */
    private GameResponse push(GameContext gameContext) throws PlayerNotFoundException {
        playerService.addFunds(gameContext.getAccount(), gameContext.getBet(), TransactionType.PUSH);
        GameResponse gameResponse = buildGameResponse(gameContext);
        gameResponse.setGameResult(gameContext.getGameResult());
        return gameResponse;
    }

    /**
     * Build {@link sintez.blackjack.game.GameResponse} entity from given {@link sintez.blackjack.game.GameContext}
     * instance
     *
     * @param gameContext - given {@link sintez.blackjack.game.GameContext} instance
     * @return built {@link sintez.blackjack.game.GameResponse} instance
     */
    private GameResponse buildGameResponse(GameContext gameContext) {
        LOGGER.info("Building GameResponse...");
        String id = gameContext.getId();
        List<Card> playerCards = gameContext.getPlayerCards();
        List<Card> dealerCards = gameContext.getDealerCards();
        int playerPoints = gameContext.getPlayerPoints();
        int dealerPoints = gameContext.getDealerPoints();
        int bet = gameContext.getBet();
        return new GameResponse(id, playerCards, dealerCards, playerPoints, dealerPoints, bet);
    }

    /**
     * Build and setup initial {@link sintez.blackjack.game.GameContext}
     *
     * @param bet - players bet
     * @return built {@link sintez.blackjack.game.GameContext}
     */
    private GameContext createNewGame(long account, int bet) throws PlayerNotFoundException {
        LOGGER.info("Building new GameContext...");
        List<Card> deck = deckService.getNewDeck(true);
        List<Card> playersCards = new ArrayList<Card>();
        List<Card> dealersCards = new ArrayList<Card>();
        playersCards.add(deckService.dealNextCard(deck));
        playersCards.add(deckService.dealNextCard(deck));
        dealersCards.add(deckService.dealNextCard(deck));

        GameContext gameContext = new GameContext(bet, playersCards, dealersCards, deck, account);
        activeGames.put(gameContext.getId(), gameContext);
        if (gameContext.isPlayerBlackjack()) {
            dealersCards.add(deckService.dealNextCard(deck));
            if (gameContext.isDealerBlackjack()) {
                push(gameContext);
            } else {
                evaluatePlayersBlackjack(gameContext);
            }
        }
        return gameContext;
    }
}
