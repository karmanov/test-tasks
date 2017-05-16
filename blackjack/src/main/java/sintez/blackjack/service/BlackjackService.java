package sintez.blackjack.service;

import sintez.blackjack.exception.BlackjackException;
import sintez.blackjack.game.GameContext;
import sintez.blackjack.game.GameResponse;

import java.util.Map;

/**
 * Declares API for Blackjack game
 */
public interface BlackjackService {

    /**
     * Execute deal command. Build and setup initial
     * {@link sintez.blackjack.game.GameContext}
     *
     * @param account - account id the {@link sintez.blackjack.model.Player} who start the game
     * @param bet     - player's bet
     */
    GameResponse deal(long account, int bet) throws BlackjackException;

    /**
     * Execute "STAND" command on existing game
     *
     * @param gameId - id of the game
     */
    GameResponse stand(String gameId) throws BlackjackException;

    /**
     * Execute "HIT" command on existing game
     *
     * @param gameId - id of the game
     * @throws BlackjackException - if command could not be executed
     */
    GameResponse hit(String gameId) throws BlackjackException;

    /**
     * Get the map of current running games
     *
     * @return map of the games
     */
    Map<String, GameContext> getActiveGames();


}
