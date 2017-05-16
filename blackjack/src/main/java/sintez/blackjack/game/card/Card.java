package sintez.blackjack.game.card;

import java.io.Serializable;

/**
 * Implementation of a card type.
 */
public class Card implements Serializable {

    private Suit suit;

    private Rank rank;

    /**
     * Card constructor
     *
     * @param suit
     * @param rank
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        if (rank == Rank.JACK || rank == Rank.QUEEN || rank == Rank.KING) {
            return 10;
        }
        return rank.ordinal() + 1;
    }
}
