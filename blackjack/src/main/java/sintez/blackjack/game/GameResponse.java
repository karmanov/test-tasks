package sintez.blackjack.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import sintez.blackjack.game.card.Card;

import java.io.Serializable;
import java.util.List;

/**
 * Game operation response. Contains all required information about current game.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameResponse implements Serializable {

    private String id;

    private int bet;

    private int playersScore;

    private List<Card> playerCards;

    private int dealersScore;

    private List<Card> dealersCards;

    private GameResult gameResult;

    public GameResponse(String id, List<Card> playerCards, List<Card> dealersCards, int playersScore, int dealersScore,
                        int bet) {
        this.id = id;
        this.playerCards = playerCards;
        this.dealersCards = dealersCards;
        this.playersScore = playersScore;
        this.dealersScore = dealersScore;
        this.bet = bet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public int getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(int playersScore) {
        this.playersScore = playersScore;
    }

    public List<Card> getDealersCards() {
        return dealersCards;
    }

    public void setDealersCards(List<Card> dealersCards) {
        this.dealersCards = dealersCards;
    }

    public int getDealersScore() {
        return dealersScore;
    }

    public void setDealersScore(int dealersScore) {
        this.dealersScore = dealersScore;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
}
