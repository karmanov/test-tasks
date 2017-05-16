package sintez.blackjack.game;


import sintez.blackjack.game.card.Card;
import sintez.blackjack.game.card.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameContext {

    private static final int BLACKJACK = 21;

    private static final int BLACKJACK_CARDS_COUNT = 2;

    private String id;

    private int bet;

    private List<Card> playerCards;

    private List<Card> dealerCards;

    private List<Card> deck;

    private long account;

    public GameContext(int bet, List<Card> playerCards, List<Card> dealerCards, List<Card> deck, long account) {
        id = UUID.randomUUID().toString();
        this.bet = bet;
        this.playerCards = playerCards;
        this.dealerCards = dealerCards;
        this.deck = deck;
        this.account = account;
    }

    public boolean isPlayerBusted() {
        return getPlayerPoints() > BLACKJACK;
    }

    public int getPlayerPoints() {
        return getPoints(playerCards);
    }

    public boolean isDealerBusted() {
        return getDealerPoints() > BLACKJACK;
    }

    public int getDealerPoints() {
        return getPoints(dealerCards);
    }

    public int getPoints(List<Card> cards) {
        List<Card> aces = new ArrayList<Card>();
        int sum = 0;
        for (Card card : cards) {
            if (card.getRank() != Rank.ACE) {
                sum += card.getValue();
            } else {
                aces.add(card);
            }
        }

        if (!aces.isEmpty()) {
            sum += aces.size() - 1;
            if (sum + 11 <= 21) {
                sum += 11;
            } else {
                sum += 1;
            }
        }
        return sum;
    }

    public GameResult getGameResult() {
        if (isOnlyPlayerHasBlackJack() || checkPlayerBustedAndPoint()) {
            return GameResult.WIN;
        } else if (isOnlyDealerHasBlackJack() || checkDealerBustedAndPoints()) {
            return GameResult.LOOSE;
        }
        return GameResult.PUSH;
    }

    public boolean isDealerBlackjack() {
        return dealerCards.size() == BLACKJACK_CARDS_COUNT && getDealerPoints() == BLACKJACK;
    }

    public boolean isPlayerBlackjack() {
        return playerCards.size() == BLACKJACK_CARDS_COUNT && getPlayerPoints() == BLACKJACK;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public int getBet() {
        return bet;
    }

    public String getId() {
        return id;
    }

    public long getAccount() {
        return account;
    }

    private boolean isOnlyPlayerHasBlackJack() {
        return isPlayerBlackjack() && !isDealerBlackjack();
    }

    private boolean checkPlayerBustedAndPoint() {
        return !isPlayerBusted() && (isDealerBusted() || getPlayerPoints() > getDealerPoints());
    }

    private boolean checkDealerBustedAndPoints() {
        return !isDealerBusted() && (isPlayerBusted() || getDealerPoints() > getPlayerPoints());
    }

    private boolean isOnlyDealerHasBlackJack() {
        return isDealerBlackjack() && !isPlayerBlackjack();
    }
}
