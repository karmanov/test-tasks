package sintez.blackjack.service;

import org.springframework.stereotype.Service;
import sintez.blackjack.game.card.Card;
import sintez.blackjack.game.card.Rank;
import sintez.blackjack.game.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DeckService {

    private List<Card> deck;

    public List<Card> getNewDeck(boolean shuffled) {
        deck = new ArrayList<Card>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        if (shuffled) {
            shuffleDeck();
        }
        return deck;
    }

    /**
     * Shuffle the deck
     */
    private void shuffleDeck() {
        long seed = System.nanoTime();
        Collections.shuffle(deck, new Random(seed));
    }

    /**
     * Deal the next card from the top of the deck.
     *
     * @return the dealt card
     */
    public Card dealNextCard(List<Card> deck) {
        return deck.remove(0);
    }

}
